package me.loki2302;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class JavaReflectionControllerReader {
    public ControllerInfo getControllerInfo(Class<?> controllerClass) {
        ControllerInfo controllerInfo = new ControllerInfo();
        controllerInfo.controllerClass = controllerClass;

        RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
        controllerInfo.requestMapping = requestMapping.value();

        List<ActionInfo> actionInfos = new ArrayList<ActionInfo>();
        Method[] methods = controllerClass.getMethods();
        for (Method method : methods) {
            ActionInfo actionInfo = getActionInfo(method);
            if (actionInfo == null) {
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
        for (Annotation annotation : methodAnnotations) {
            if (annotation.annotationType().equals(RequestMapping.class)) {
                requestMapping = (RequestMapping) annotation;
                break;
            }
        }

        if (requestMapping == null) {
            return null;
        }

        ActionInfo actionInfo = new ActionInfo();
        actionInfo.actionMethod = method;
        actionInfo.requestMapping = requestMapping.value();

        Class<?>[] parameterTypes = method.getParameterTypes();
        int numberOfParameters = parameterTypes.length;

        Annotation[][] parameterAnnotationArrays = method.getParameterAnnotations();

        List<ParameterInfo> parameterInfos = new ArrayList<ParameterInfo>();
        for (int i = 0; i < numberOfParameters; ++i) {
            ParameterInfo parameterInfo = new ParameterInfo();
            parameterInfo.type = parameterTypes[i];

            Annotation[] parameterAnnotations = parameterAnnotationArrays[i];
            for (Annotation parameterAnnotation : parameterAnnotations) {
                Class<?> annotationType = parameterAnnotation.annotationType();
                if (annotationType.equals(Path.class)) {
                    Path path = (Path) parameterAnnotation;
                    parameterInfo.name = path.value();
                    parameterInfo.isPathParam = true;
                } else if (annotationType.equals(Query.class)) {
                    Query query = (Query) parameterAnnotation;
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
}
