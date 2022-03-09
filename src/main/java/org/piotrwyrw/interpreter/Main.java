package org.piotrwyrw.interpreter;

import org.piotrwyrw.interpreter.parser.*;
import org.piotrwyrw.interpreter.tokenizer.TokenStream;
import org.piotrwyrw.interpreter.tokenizer.Tokenizer;

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
            code.set(code.get() + line);
        });

        Tokenizer tokenizer = new Tokenizer(code.get());
        tokenizer.analyze();

//        tokenizer.tokens().forEach((e) -> {
//            e.print();
//        });


        TokenStream stream = new TokenStream(tokenizer.tokens());
        Parser parser = new Parser(stream);
        ProgramNode program = parser.parseProgram();
        ASTTools.analyze(program);
    }

}
