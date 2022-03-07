package org.piotrwyrw.interpreter.tokenizer;

import java.util.Locale;

public class Token {

    private String value;
    private TokenType type;
    private int line;

    public Token(String value, TokenType type, int line) {
        this.value = value;
        this.type = type;
        this.line = line;
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

    public int line() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void print() {
        System.out.println("[" + type.toString().toUpperCase() + "] " + value);
    }

}
