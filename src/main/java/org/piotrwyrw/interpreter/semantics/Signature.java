package org.piotrwyrw.interpreter.semantics;

public class Signature {

    private Variable[] parameters;
    private DataType type;

    public Signature(Variable[] parameters, DataType type) {
        this.parameters = parameters;
        this.type = type;
    }

    public Variable[] parameters() {
        return parameters;
    }

    public void setParameters(Variable[] parameters) {
        this.parameters = parameters;
    }

    public DataType type() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }
}
