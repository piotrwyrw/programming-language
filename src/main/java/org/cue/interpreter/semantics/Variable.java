package org.cue.interpreter.semantics;

public class Variable {

    private String identifier;
    private DataType type;

    public Variable(String identifier, DataType type) {
        this.identifier = identifier;
        this.type = type;
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
}
