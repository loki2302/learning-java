package me.loki2302;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DummyTest {
    @Test
    public void dummy () throws NoSuchMethodException {
        ControllerInfo controllerInfo = getControllerInfo(MyController1.class);
        assertEquals(MyController1.class, controllerInfo.controllerClass);
        assertEquals("/api", controllerInfo.requestMapping);
        assertEquals(1, controllerInfo.actionInfos.size());

        ActionInfo actionInfo = controllerInfo.actionInfos.get(0);
        assertEquals(MyController1.class.getDeclaredMethod("myMethod", int.class, String.class), actionInfo.actionMethod);
        assertEquals("/myMethod", actionInfo.requestMapping);
        assertEquals(2, actionInfo.parameterInfos.size());

        List<ParameterInfo> parameterInfos = actionInfo.parameterInfos;
        assertEquals(int.class, parameterInfos.get(0).type);
        assertEquals("num", parameterInfos.get(0).name);
        assertTrue(parameterInfos.get(0).isPathParam);

        assertEquals(String.class, parameterInfos.get(1).type);
        assertEquals("str", parameterInfos.get(1).name);
        assertFalse(parameterInfos.get(1).isPathParam);
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Controller {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Path {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Query {
        String value();
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface RequestMapping {
        String value();
    }

    private static ControllerInfo getControllerInfo(Class<?> controllerClass) {
        ControllerInfo controllerInfo = new ControllerInfo();
        controllerInfo.controllerClass = controllerClass;

        RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
        controllerInfo.requestMapping = requestMapping.value();

        List<ActionInfo> actionInfos = new ArrayList<ActionInfo>();
        Method[] methods = controllerClass.getMethods();
        for(Method method : methods) {
            ActionInfo actionInfo = getActionInfo(method);
            if(actionInfo == null) {
                continue;
            }

            actionInfos.add(actionInfo);
        }
        controllerInfo.actionInfos = actionInfos;

        return controllerInfo;
    }

    private static ActionInfo getActionInfo(Method method) {
        RequestMapping requestMapping = null;
        Annotation[] methodAnnotations = method.getDeclaredAnnotations();
        for(Annotation annotation : methodAnnotations) {
            if(annotation.annotationType().equals(RequestMapping.class)) {
                requestMapping = (RequestMapping)annotation;
                break;
            }
        }

        if(requestMapping == null) {
            return null;
        }

        ActionInfo actionInfo = new ActionInfo();
        actionInfo.actionMethod = method;
        actionInfo.requestMapping = requestMapping.value();

        Class<?>[] parameterTypes = method.getParameterTypes();
        int numberOfParameters = parameterTypes.length;

        Annotation[][] parameterAnnotationArrays = method.getParameterAnnotations();

        List<ParameterInfo> parameterInfos = new ArrayList<ParameterInfo>();
        for(int i = 0; i < numberOfParameters; ++i) {
            ParameterInfo parameterInfo = new ParameterInfo();
            parameterInfo.type = parameterTypes[i];

            Annotation[] parameterAnnotations = parameterAnnotationArrays[i];
            for(Annotation parameterAnnotation : parameterAnnotations) {
                Class<?> annotationType = parameterAnnotation.annotationType();
                if(annotationType.equals(Path.class)) {
                    Path path = (Path)parameterAnnotation;
                    parameterInfo.name = path.value();
                    parameterInfo.isPathParam = true;
                } else if(annotationType.equals(Query.class)) {
                    Query query = (Query)parameterAnnotation;
                    parameterInfo.name = query.value();
                    parameterInfo.isPathParam = false;
                } else {
                    throw new RuntimeException();
                }
            }

            parameterInfos.add(parameterInfo);
        }

        actionInfo.parameterInfos = parameterInfos;

        return actionInfo;
    }

    public static class ControllerInfo {
        public Class<?> controllerClass;
        public String requestMapping;
        public List<ActionInfo> actionInfos;
    }

    public static class ActionInfo {
        public Method actionMethod;
        public String requestMapping;
        public List<ParameterInfo> parameterInfos;
    }

    public static class ParameterInfo {
        public Class<?> type;
        public boolean isPathParam;
        public String name;
    }

    @Controller
    @RequestMapping("/api")
    public static class MyController1 {
        @RequestMapping("/myMethod")
        public void myMethod(
                @Path("num") int numParameter,
                @Query("str") String stringParameter) {
        }
    }
}
