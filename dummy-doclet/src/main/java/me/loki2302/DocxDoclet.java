package me.loki2302;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sun.javadoc.*;
import com.sun.tools.doclets.standard.Standard;
import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocxDoclet {
    public static int optionLength(String s) {
        return Standard.optionLength(s);
    }

    public static boolean start(RootDoc rootDoc) throws IOException, ScriptException, URISyntaxException, Docx4JException {
        String docDirectoryPath = Stream.of(rootDoc.options()).filter(option -> option[0].equals("-d")).findFirst().get()[1];
        File docFile = Paths.get(docDirectoryPath, "index.docx").toFile();
        System.out.printf("Will write docs to %s\n", docFile);

        URL templateUrl = Resources.getResource("docx-template.ejs");
        String template = Resources.toString(templateUrl, Charsets.UTF_8);

        List<ClassModel> classModels = Stream.of(rootDoc.classes()).map(classDoc -> new ClassModel(
                classDoc.name(),
                classDoc.commentText(),
                Stream.of(classDoc.methods()).map(methodDoc -> new MethodModel(
                        methodDoc.name(),
                        methodDoc.commentText(),
                        Stream.of(methodDoc.paramTags()).map(parameterTag -> new ParameterModel(
                                parameterTag.parameterName(),
                                parameterTag.parameterComment())
                        ).collect(Collectors.toList()))
                ).collect(Collectors.toList()))
        ).collect(Collectors.toList());

        DocModel docModel = new DocModel(classModels);
        String docModelJson = new ObjectMapper().writeValueAsString(docModel);

        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("nashorn");
        scriptEngine.put("template", template);
        scriptEngine.put("docModelJson", docModelJson);
        scriptEngine.eval("window = {}");
        scriptEngine.eval("load('classpath:META-INF/resources/webjars/ejs/2.4.1/ejs-v2.4.1/ejs.js')");
        String html = (String)scriptEngine.eval("window.ejs.render(template, JSON.parse(docModelJson))");

        System.out.println(html);

        WordprocessingMLPackage wordprocessingMLPackage = WordprocessingMLPackage.createPackage();
        XHTMLImporter xhtmlImporter = new XHTMLImporterImpl(wordprocessingMLPackage);
        wordprocessingMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(html, null));
        wordprocessingMLPackage.save(docFile);

        return true;
    }

    public static class DocModel {
        public List<ClassModel> classes;

        public DocModel(List<ClassModel> classes) {
            this.classes = classes;
        }
    }

    public static class ClassModel {
        public String name;
        public String comment;
        public List<MethodModel> methods;

        public ClassModel(String name, String comment, List<MethodModel> methods) {
            this.name = name;
            this.comment = comment;
            this.methods = methods;
        }
    }

    public static class MethodModel {
        public String name;
        public String comment;
        public List<ParameterModel> parameters;

        public MethodModel(String name, String comment, List<ParameterModel> parameters) {
            this.name = name;
            this.comment = comment;
            this.parameters = parameters;
        }
    }

    public static class ParameterModel {
        public String name;
        public String comment;

        public ParameterModel(String name, String comment) {
            this.name = name;
            this.comment = comment;
        }
    }

    public static boolean validOptions(String[][] options, DocErrorReporter docErrorReporter) {
        return Standard.validOptions(options, docErrorReporter); // TODO: how do I get rid of this?
    }

    public static LanguageVersion languageVersion() {
        return Standard.languageVersion(); // TODO: how do I get rid of this?
    }
}
