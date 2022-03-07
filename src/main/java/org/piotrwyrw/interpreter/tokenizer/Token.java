package org.piotrwyrw.interpreter.tokenizer;

import java.util.Locale;

public class Token {

    private String value;
    private TokenType type;

    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public String value() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType type() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public void print() {
        System.out.println("[" + type.toString().toUpperCase() + "] " + value);
    }

}
