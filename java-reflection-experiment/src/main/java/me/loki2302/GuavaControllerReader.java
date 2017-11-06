package me.loki2302;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.Invokable;
import com.google.common.reflect.Parameter;
import com.google.common.reflect.TypeToken;
import me.loki2302.annotations.Path;
import me.loki2302.annotations.Query;
import me.loki2302.annotations.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GuavaControllerReader implements ControllerReader {
    @Override
    public ControllerInfo getControllerInfo(Class<?> controllerClass) {
        ControllerInfo controllerInfo = new ControllerInfo();
        controllerInfo.controllerClass = controllerClass;

        RequestMapping requestMapping = controllerClass.getAnnotation(RequestMapping.class);
        controllerInfo.requestMapping = requestMapping.value();

        TypeToken controllerTypeToken = TypeToken.of(controllerClass);
        List<ActionInfo> actionsInfos = new ArrayList<ActionInfo>();
        Method[] methods = controllerClass.getMethods();
        for(Method method : methods) {
            Invokable invokable = Invokable.from(method);
            ActionInfo actionInfo = getActionInfo(invokable);
            if(actionInfo == null) {
                continue;
            }

            actionsInfos.add(actionInfo);
        }

        controllerInfo.actionInfos = actionsInfos;

        return controllerInfo;
    }

    private ActionInfo getActionInfo(Invokable invokable) {
        if(!invokable.isAnnotationPresent(RequestMapping.class)) {
            return null;
        }

        ActionInfo actionInfo = new ActionInfo();
        actionInfo.requestMapping = invokable.getAnnotation(RequestMapping.class).value();
        actionInfo.actionMethod = null;

        List<ParameterInfo> parameterInfos = new ArrayList<ParameterInfo>();
        ImmutableList<Parameter> parameters = invokable.getParameters();
        for(Parameter parameter : parameters) {
            ParameterInfo parameterInfo = getParameterInfo(parameter);
            parameterInfos.add(parameterInfo);
        }

        actionInfo.parameterInfos = parameterInfos;

        return actionInfo;
    }

    private ParameterInfo getParameterInfo(Parameter parameter) {
        ParameterInfo parameterInfo = new ParameterInfo();
        Path path = parameter.getAnnotation(Path.class);
        if(path != null) {
            parameterInfo.isPathParam = true;
            parameterInfo.name = path.value();
            parameterInfo.type = parameter.getType().getRawType();
        } else {
            Query query = parameter.getAnnotation(Query.class);
            parameterInfo.isPathParam = false;
            parameterInfo.name = query.value();
            parameterInfo.type = parameter.getType().getRawType();
        }

        return parameterInfo;
    }
}
