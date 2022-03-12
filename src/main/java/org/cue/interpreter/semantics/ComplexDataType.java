package org.cue.interpreter.semantics;

import java.util.List;

public class ComplexDataType extends DataType {

    private String identifier;
    private List<Variable> variables;

    public ComplexDataType(String identifier, List<Variable> variables) {
        this.identifier = identifier;
        this.variables = variables;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Variable> variables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }
}
