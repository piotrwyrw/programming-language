package org.cue.interpreter.semantics;

import org.cue.interpreter.parser.IdentifierNode;

public class Variable {

    private IdentifierNode identifier;
    private DataType type;

    public Variable(IdentifierNode identifier, DataType type) {
        this.identifier = identifier;
        this.type = type;
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

}
