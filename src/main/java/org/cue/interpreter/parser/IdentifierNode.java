package org.cue.interpreter.parser;

public class IdentifierNode extends ExpressionNode {

    private String identifier;

    public IdentifierNode(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
