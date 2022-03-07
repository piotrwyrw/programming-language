package org.piotrwyrw.interpreter.tokenizer;

public enum TokenType {

    NOT_CLASSIFIED,

    IDENTIFIER,
    STRING_LITERAL,
    INTEGER_LITERAL,

    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,

    ASTERISK,
    DASH,
    PLUS,
    SLASH,

    COLON,
    SEMICOLON,
    QUESTIONMARK,
    LGREATER,
    RGREATER,

    POINT_RIGHT,
    POINT_LEFT,
    SHIFT_RIGHT,
    SHIFT_LEFT,

    DOT,
    COMMA

}