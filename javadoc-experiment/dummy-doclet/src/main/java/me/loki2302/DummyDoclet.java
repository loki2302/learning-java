package me.loki2302;

import com.sun.javadoc.*;
import com.sun.tools.doclets.standard.Standard;

import java.util.stream.Stream;

public class DummyDoclet {
    public static int optionLength(String s) {
        if(s.equals("-just")) {
            return 2;
        }

        return Standard.optionLength(s); // TODO: how do I get rid of this?
    }

    public static boolean start(RootDoc rootDoc) {
        for(String[] optionArray : rootDoc.options()) {
            for(String option : optionArray) {
                System.out.printf("{%s} ", option);
            }
            System.out.println();
        }

        System.out.println();

        for(ClassDoc classDoc : rootDoc.classes()) {
            System.out.printf("%s: %s\n", classDoc.name(), classDoc.commentText());

            for(MethodDoc methodDoc : classDoc.methods()) {
                System.out.printf("  %s: %s\n", methodDoc.name(), methodDoc.commentText());
            }
        }

        return true;
    }

    public static boolean validOptions(String[][] options, DocErrorReporter docErrorReporter) {
        String[][] standardOptions = Stream.of(options)
                .filter(option -> !option[0].equals("-just"))
                .toArray(size -> new String[size][]);

        return Standard.validOptions(standardOptions, docErrorReporter); // TODO: how do I get rid of this?
    }

    public static LanguageVersion languageVersion() {
        return Standard.languageVersion(); // TODO: how do I get rid of this?
    }
}
