package io.agibalov;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void canUseEjsConvenientlyViaSerialization() throws ScriptException, JsonProcessingException {
        Person person = new Person("loki2302");
        String personJson = new ObjectMapper().writeValueAsString(person);

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.put("modelJson", personJson);
        scriptEngine.eval("window = {}");
        scriptEngine.eval("load('classpath:META-INF/resources/webjars/ejs/2.4.1/ejs-v2.4.1/ejs.js')");
        String result = (String)scriptEngine.eval("window.ejs.render('<p><%= name %></p>', JSON.parse(modelJson))");
        assertEquals("<p>loki2302</p>", result);
    }

    @Test
    public void canUseEjsConvenientlyViaObjectBindProperties() throws ScriptException, JsonProcessingException {
        Person person = new Person("loki2302");

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.put("person", person);
        scriptEngine.eval("var personProxy = Object.bindProperties({}, person)");
        scriptEngine.eval("window = {}");
        scriptEngine.eval("load('classpath:META-INF/resources/webjars/ejs/2.4.1/ejs-v2.4.1/ejs.js')");
        String result = (String)scriptEngine.eval("window.ejs.render('<p><%= name %></p>', personProxy)");
        assertEquals("<p>loki2302</p>", result);
    }

    public static class Person {
        public String name;

        public Person(String name) {
            this.name = name;
        }
    }
}
