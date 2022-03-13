package org.cue.interpreter.parser;

import org.cue.interpreter.semantics.DataType;

public class VariableDeclarationNode extends StatementNode {

    private IdentifierNode identifier;
    private DataType type;
    private ExpressionNode value;

    public VariableDeclarationNode(IdentifierNode identifier, DataType type, ExpressionNode value) {
        this.identifier = identifier;
        this.type = type;
        this.value = value;
    }

    public IdentifierNode identifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierNode identifier) {
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
