package me.loki2302;

import java.lang.reflect.Method;
import java.util.List;

public class ActionInfo {
    public Method actionMethod;
    public String requestMapping;
    public List<ParameterInfo> parameterInfos;
}
