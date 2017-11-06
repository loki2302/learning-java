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

    @Test
    public void canExportObjectsFromJavaScriptToJava() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.eval("function add(a, b) { return a + b; }");
        scriptEngine.eval("function sub(a, b) { return a - b; }");
        JSCalculator jsCalculator = ((Invocable)scriptEngine).getInterface(JSCalculator.class);
        assertEquals(5, jsCalculator.add(2, 3));
        assertEquals(-1, jsCalculator.sub(2, 3));
    }

    public static interface JSCalculator {
        int add(int a, int b);
        int sub(int a, int b);
    }
}
