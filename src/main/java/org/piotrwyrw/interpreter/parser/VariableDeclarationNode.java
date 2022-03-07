package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.semantics.DataType;

public class VariableDeclarationNode extends StatementNode {

    private String identifier;
    private DataType type;
    private ExpressionNode value;

    public VariableDeclarationNode(String identifier, DataType type, ExpressionNode value) {
        this.identifier = identifier;
        this.type = type;
        this.value = value;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public DataType type() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public ExpressionNode value() {
        return value;
    }

    public void setValue(ExpressionNode value) {
        this.value = value;
    }
}
