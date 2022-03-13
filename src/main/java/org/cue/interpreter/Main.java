package org.cue.interpreter;

import org.cue.interpreter.parser.ASTTools;
import org.cue.interpreter.parser.GenericNode;
import org.cue.interpreter.parser.Parser;
import org.cue.interpreter.tokenizer.TokenStream;
import org.cue.interpreter.tokenizer.Tokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {
        File codef = new File("code.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(codef));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (reader == null) {
            System.exit(0);
        }

        AtomicReference<String> code = new AtomicReference<>();
        code.set(new String());

        reader.lines().forEach((line) -> {
            code.set(((code.get().isEmpty()) ? ("") : (code.get() + '\n')) + line);
        });

        Tokenizer tokenizer = new Tokenizer(code.get());
        tokenizer.analyze();

        tokenizer.tokens().forEach((e) -> {
            e.print();
        });


        TokenStream stream = new TokenStream(tokenizer.tokens());

        Parser parser = new Parser(stream);
        parser = new Parser(stream);

        GenericNode node = parser.parseVariableDeclaration();
        ASTTools.analyze(node);
    }

}
