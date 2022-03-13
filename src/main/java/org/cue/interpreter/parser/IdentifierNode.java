package org.cue.interpreter.parser;

import org.cue.interpreter.tokenizer.Token;

public class IdentifierNode extends ExpressionNode {

    private Token identifier;

    public IdentifierNode(Token identifier) {
        this.identifier = identifier;
    }

    public Token identifier() {
        return identifier;
    }

    public void setIdentifier(Token identifier) {
        this.identifier = identifier;
    }
}
