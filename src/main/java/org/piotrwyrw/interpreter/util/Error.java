package org.piotrwyrw.interpreter.util;

import org.piotrwyrw.interpreter.tokenizer.Token;

public class Error {

    public static void error(Token t, String ermsg) {
        throw new IllegalStateException("[" + t.value() + " - " + t.type().toString() + " @ " + t.line() + "] " + ermsg);
    }

    public static void error(String ermsg) {
        throw new IllegalStateException("[ ERROR ] " + ermsg);
    }

}
