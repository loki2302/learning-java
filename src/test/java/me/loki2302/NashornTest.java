package me.loki2302;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;

public class NashornTest {
    @Test
    public void canUseNashorn() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        int result = (Integer)scriptEngine.eval("2 + 3");
        assertEquals(5, result);
    }
}
