package me.loki2302.debugger;

import com.sun.jdi.*;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import com.sun.jdi.event.*;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.MethodEntryRequest;
import com.sun.jdi.request.MethodExitRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, IllegalConnectorArgumentsException, InterruptedException, IncompatibleThreadStateException {
        String dummyAppJarPath = System.getProperty("dummyAppJarPath");
        String javaCommand = String.format("java -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=y -jar %s", dummyAppJarPath);
        Process dummyAppProcess = Runtime.getRuntime().exec(javaCommand);

        Thread.sleep(1000);

        try {
            VirtualMachineManager virtualMachineManager = Bootstrap.virtualMachineManager();
            AttachingConnector attachingConnector = null;
            for (Connector connector : virtualMachineManager.attachingConnectors()) {
                System.out.printf("[%s] %s (%s)\n",
                        connector.transport(),
                        connector.name(),
                        connector.description());

                if (connector.name().equals("com.sun.jdi.SocketAttach")) {
                    attachingConnector = (AttachingConnector)connector;
                }
            }

            if (attachingConnector == null) {
                throw new RuntimeException();
            }

            Map<String, Connector.Argument> attachArgs = attachingConnector.defaultArguments();
            attachArgs.get("port").setValue("8000");

            VirtualMachine virtualMachine = attachingConnector.attach(attachArgs);

            EventRequestManager eventRequestManager = virtualMachine.eventRequestManager();
            ClassPrepareRequest classPrepareRequest = eventRequestManager.createClassPrepareRequest();
            classPrepareRequest.addClassFilter("me.loki2302.dummy.*");
            classPrepareRequest.setEnabled(true);

            List<String> classFilters = Arrays.asList("me.loki2302.dummy.*", "java.io.PrintStream");
            Arrays.asList("me.loki2302.dummy.*", "java.io.PrintStream").forEach(classFilter -> {
                MethodEntryRequest methodEntryRequest = eventRequestManager.createMethodEntryRequest();
                methodEntryRequest.addClassFilter(classFilter);
                methodEntryRequest.setEnabled(true);

                MethodExitRequest methodExitRequest = eventRequestManager.createMethodExitRequest();
                methodExitRequest.addClassFilter(classFilter);
                methodExitRequest.setEnabled(true);
            });

            virtualMachine.resume();

            EventQueue eventQueue = virtualMachine.eventQueue();
            while(true) {
                EventSet eventSet = eventQueue.remove();
                for(Event event : eventSet) {
                    if(event instanceof VMDeathEvent || event instanceof VMDisconnectEvent) {
                        return;
                    }

                    if(event instanceof ClassPrepareEvent) {
                        ClassPrepareEvent classPrepareEvent = (ClassPrepareEvent)event;
                        ReferenceType referenceType = classPrepareEvent.referenceType();
                        System.out.printf("ClassPrepareEvent: %s\n", referenceType.name());
                        continue;
                    }

                    if(event instanceof MethodEntryEvent) {
                        MethodEntryEvent methodEntryEvent = (MethodEntryEvent)event;
                        Method method = methodEntryEvent.method();
                        System.out.printf(
                                "ENTER: %s#%s()\n",
                                method.declaringType().name(),
                                method.name());
                        continue;
                    }

                    if(event instanceof MethodExitEvent) {
                        MethodExitEvent methodExitEvent = (MethodExitEvent)event;
                        Method method = methodExitEvent.method();
                        System.out.printf(
                                "LEAVE: %s#%s()\n",
                                method.declaringType().name(),
                                method.name());
                        continue;
                    }
                }
                eventSet.resume();
            }
        } finally {
            dummyAppProcess.destroy();
        }
    }
}
