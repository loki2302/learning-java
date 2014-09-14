package me.loki2302;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DummyTest {
    @Test
    public void dummy () throws NoSuchMethodException {
        JavaReflectionControllerReader reader = new JavaReflectionControllerReader();
        ControllerInfo controllerInfo = reader.getControllerInfo(MyController1.class);
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
}
