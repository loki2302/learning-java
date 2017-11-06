package me.loki2302;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

@SupportedAnnotationTypes("me.loki2302.GenerateFactory")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class FactoryGeneratorProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        STGroup templateGroup = new STGroupFile("processor.stg");
        for(Element elem : roundEnv.getElementsAnnotatedWith(GenerateFactory.class)) {
            if(elem.getKind() == ElementKind.CLASS) {
                Messager messager = processingEnv.getMessager();
                messager.printMessage(Diagnostic.Kind.NOTE, "**********************************");
                messager.printMessage(Diagnostic.Kind.NOTE, "Generating factory for " + elem.getSimpleName());
                messager.printMessage(Diagnostic.Kind.NOTE, "**********************************");

                TypeElement classElement = (TypeElement)elem;
                PackageElement packageElement = (PackageElement)classElement.getEnclosingElement();
                try {
                    JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(classElement.getQualifiedName() + "Factory");
                    
                    ST generatedClassTemplate = templateGroup.getInstanceOf("factoryClass");
                    generatedClassTemplate.add("packageName", packageElement.getQualifiedName());
                    generatedClassTemplate.add("className", classElement.getSimpleName());
                   
                    Writer writer = javaFileObject.openWriter();
                    writer.append(generatedClassTemplate.render());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }       
        
        return true;
    }        
}