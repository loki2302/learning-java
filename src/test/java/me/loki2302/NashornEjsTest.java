package me.loki2302;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.Assert.assertEquals;

public class NashornEjsTest {
    @Test
    public void canUseEjs() throws ScriptException {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.eval("window = {}");
        scriptEngine.eval("load('classpath:META-INF/resources/webjars/ejs/2.4.1/ejs-v2.4.1/ejs.js')");
        String result = (String)scriptEngine.eval("window.ejs.render('<p><%= name %></p>', { name: 'loki2302' })");
        assertEquals("<p>loki2302</p>", result);
    }
}
