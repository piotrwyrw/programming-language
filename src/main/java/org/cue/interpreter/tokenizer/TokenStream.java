package org.cue.interpreter.tokenizer;

import java.util.List;

public class TokenStream {

    private List<Token> tokens;
    private int pos;
    private Token current;
    private Token next;

    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
        this.current = null;
        this.next = null;
        consume(); consume();
    }

    public List<Token> tokens() {
        return tokens;
    }

    public int pos() {
        return pos;
    }

    public Token consume() {
        current = next;
        if (pos < tokens.size()) {
            next = tokens.get(pos);
            pos ++;
        } else {
            next = null;
        }
        return current;
    }

    public Token current() {
        return current;
    }

    public Token next() {
        return next;
    }

    public boolean isLast() {
        return pos + 1 >= tokens.size();
    }

    public boolean hasNext() {
        return next != null;
    }

    public boolean hasCurrent() {
        return current != null;
    }

    public void rewind() {
        pos = 0;
        current = null;
        next = null;
        consume(); consume();
    }
}
