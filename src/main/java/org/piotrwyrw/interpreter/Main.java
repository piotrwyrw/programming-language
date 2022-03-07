package org.piotrwyrw.interpreter;

import org.piotrwyrw.interpreter.tokenizer.Tokenizer;

public class Main {

    public static void main(String[] args) {
        String input = "int main(int argc, char **argv) { printf(\"Hello, world!\\n\"); }";
        Tokenizer tokenizer = new Tokenizer(input);
        tokenizer.analyze();
        tokenizer.tokens().forEach((item) -> {
            item.print();
        });
    }

}
