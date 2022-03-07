package org.piotrwyrw.interpreter.util;

import org.piotrwyrw.interpreter.tokenizer.Token;

public class Error {

    public static void error(Token t, String ermsg) {
        throw new IllegalStateException("[" + t.value() + " - " + t.type().toString() + " @ " + t.line() + "] " + ermsg);
    }

}
