package org.piotrwyrw.interpreter.tokenizer;

import org.piotrwyrw.interpreter.util.Error;

import java.util.List;

public class TokenStream {

    private List<Token> tokens;
    private int pos;

    public TokenStream(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    public List<Token> tokens() {
        return tokens;
    }

    public int pos() {
        return pos;
    }

    public Token next() {
        if (pos + 1 < tokens.size()) {
            return tokens.get(++ pos);
        }
        Error.error(get(), "Ran out of tokens.");
        return null;
    }

    public Token back() {
        if (pos - 1 >= 0) {
            return tokens.get(-- pos);
        }
        Error.error(get(), "Can't go back a token.");
        return null;
    }

    public Token get() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return null;
    }

    public boolean isLast() {
        return pos + 1 >= tokens.size();
    }

    public boolean hasNext() {
        return !isLast();
    }

    public boolean hasNextFew(int c) {
        return pos + c < tokens.size();
    }

    public void rewind() {
        pos = 0;
    }

}
