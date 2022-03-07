package org.piotrwyrw.interpreter.tokenizer;

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
            return tokens.get(pos ++);
        }
        throw new IllegalStateException("Ran out of tokens.");
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

    public void rewind() {
        pos = 0;
    }

}
