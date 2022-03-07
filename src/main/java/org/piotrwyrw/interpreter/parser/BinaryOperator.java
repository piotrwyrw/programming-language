package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.tokenizer.TokenType;

public enum BinaryOperator {

    ADD,
    SUB,
    MUL,
    DIV,
    LSHIFT,
    RSHIFT,
    UNKNOWN;

    public static BinaryOperator fromTokenType(TokenType type) {
        switch (type) {
            case PLUS: return ADD;
            case DASH: return SUB;
            case ASTERISK: return MUL;
            case SLASH: return DIV;
            default: return UNKNOWN;

        }
    }

}
