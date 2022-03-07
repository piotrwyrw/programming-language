package org.piotrwyrw.interpreter.parser;

public class AssignmentNode extends StatementNode {

    private String identifier;
    private ExpressionNode value;

    public AssignmentNode(String identifier, ExpressionNode value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public ExpressionNode value() {
        return value;
    }

    public void setValue(ExpressionNode value) {
        this.value = value;
    }
}
