package me.loki2302;

import org.junit.Test;

import javax.script.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NashornTest {
    @Test
    public void canUseNashorn() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        int result = (Integer)scriptEngine.eval("2 + 3");
        assertEquals(5, result);
    }

    @Test
    public void canExportObjectsFromJavaToJavaScript() throws ScriptException {
        List<String> myList = new ArrayList<>();

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.put("myList", myList);
        scriptEngine.eval("myList.add('hello'); myList.add('there');");

        assertEquals(2, myList.size());
        assertEquals("hello", myList.get(0));
        assertEquals("there", myList.get(1));
    }
}
