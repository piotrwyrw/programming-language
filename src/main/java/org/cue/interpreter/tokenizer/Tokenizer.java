package org.cue.interpreter.tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private String input;
    private int position;
    private List<Token> tokens;

    public Tokenizer(String input) {
        this.input = input;
        this.tokens = new ArrayList<>();
    }

    public String input() {
        return input;
    }

    public int position() {
        return position;
    }

    public List<Token> tokens() {
        return tokens;
    }

    private boolean isDigit(char c) {
        return (c >= '0' && c <= '9');
    }

    private boolean isLetter(char c) {
        return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '_');
    }

    private TokenType typeOfChar(char c) {
        switch (c) {
            case '(': return TokenType.LPAREN;
            case ')': return TokenType.RPAREN;
            case '{': return TokenType.LBRACE;
            case '}': return TokenType.RBRACE;
            case '[': return TokenType.LBRACKET;
            case ']': return TokenType.RBRACKET;

            case '*': return TokenType.ASTERISK;
            case '-': return TokenType.DASH;
            case '+': return TokenType.PLUS;
            case '/': return TokenType.SLASH;

            case ':': return TokenType.COLON;
            case ';': return TokenType.SEMICOLON;
            case '?': return TokenType.QUESTIONMARK;
            case '>': return TokenType.LGREATER;
            case '<': return TokenType.RGREATER;
            case '=': return TokenType.EQUALS;

            case '.': return TokenType.DOT;
            case ',': return TokenType.COMMA;

            case '#': return TokenType.HASH;

            default: return TokenType.NOT_CLASSIFIED;
        }
    }

    private TokenType extendedType(TokenType type, String value) {
        if (type != TokenType.IDENTIFIER) {
            return type;
        }
        if (value.equals("true")) {
            return TokenType.TRUE;
        } else if (value.equals("false")) {
            return TokenType.FALSE;
        } else {
            return type;
        }
    }

    private boolean isSpecialCharacter(TokenType type) {
        return type != TokenType.IDENTIFIER && type != TokenType.INTEGER_LITERAL && type != TokenType.STRING_LITERAL;
    }

    private TokenType determineType(char c) {
        if (isDigit(c)) {
            return TokenType.INTEGER_LITERAL;
        } else if (isLetter(c)) {
            return TokenType.IDENTIFIER;
        } else {
            return typeOfChar(c);
        }
    }

    private boolean isReduntant(char c) {
        return (c == ' ' || c == '\n' || c == '\t');
    }

    public void analyze() {
        TokenType type = TokenType.NOT_CLASSIFIED;
        String value = "";
        int line = 1;
        boolean str = false;
        boolean chr = false;
        for (int i = 0; i < input.length(); i ++) {
            char c = input.charAt(i);

            if (c == '\n') {
                line ++;
            }

            if (c == '"') {
                type = (type == TokenType.STRING_LITERAL) ? TokenType.NOT_CLASSIFIED : TokenType.STRING_LITERAL;
                if (type == TokenType.NOT_CLASSIFIED) {
                    tokens.add(new Token(value, TokenType.STRING_LITERAL, line));
                    value = "";
                    type = TokenType.NOT_CLASSIFIED;
                }
                continue;
            }

            if (type == TokenType.STRING_LITERAL) {
                value += c;
                if (i + 1 >= input.length()) {
                    throw new IllegalStateException("Reached end of file while tokenizing a " + type.toString().toUpperCase());
                }
                continue;
            }

            // Skip some weird characters
            if (isReduntant(c)) {
                // Put the token on the list and reset
                if (type != TokenType.NOT_CLASSIFIED) {
                    tokens.add(new Token(value, extendedType(type, value), line));
                    type = TokenType.NOT_CLASSIFIED;
                    value = "";
                }
                continue;
            }

            // Perform type classification here
            if (value.length() == 0) {
                type = determineType(c);
            }

            // Append to string if type hasn't changed
            TokenType t = determineType(c);
            if ((type == t || (type == TokenType.IDENTIFIER && t == TokenType.INTEGER_LITERAL)) && type != TokenType.NOT_CLASSIFIED) {
                value += c;
                if (isSpecialCharacter(type)) {
                    // Check if the next token is also a special character in order to form
                    // complex Tokens
                    if (i + 1 < input.length()) {
                        TokenType nt = determineType(input.charAt(i + 1));
                        if (t == TokenType.DASH && nt == TokenType.LGREATER) {
                            type = TokenType.POINT_RIGHT;
                            i ++;
                        } else if (t == TokenType.RGREATER && nt == TokenType.DASH) {
                            type = TokenType.POINT_LEFT;
                            i ++;
                        } else if (t == TokenType.RGREATER && nt == TokenType.RGREATER) {
                            type = TokenType.SHIFT_LEFT;
                            i ++;
                        } else if (t == TokenType.LGREATER && nt == TokenType.LGREATER) {
                            type = TokenType.SHIFT_RIGHT;
                            i ++;
                        }
                    }
                    tokens.add(new Token(value, extendedType(type, value), line));
                    type = TokenType.NOT_CLASSIFIED;
                    value = "";
                    continue;
                }
            }

            // else put this token on the buffer, reset control
            // variables, and go one char back.
            else {
                tokens.add(new Token(value, extendedType(type, value), line));
                type = TokenType.NOT_CLASSIFIED;
                value = "";
                i --;
                continue;
            }

            // Lastly, check if this was the last char of the input string.
            // Clearing control variables is redundant, since this is the last interation.
            if (i + 1 >= input.length()) {
                tokens.add(new Token(value, extendedType(type, value), line));
            }
        }
    }

}
