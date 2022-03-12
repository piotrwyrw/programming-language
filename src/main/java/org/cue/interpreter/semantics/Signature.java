package org.cue.interpreter.semantics;

import java.util.List;

public class Signature {

    private List<Variable> parameters;
    private DataType type;

    public Signature(List<Variable> parameters, DataType type) {
        this.parameters = parameters;
        this.type = type;
    }

    public List<Variable> parameters() {
        return parameters;
    }

    public void setParameters(List<Variable> parameters) {
        this.parameters = parameters;
    }

    public DataType type() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }
}
