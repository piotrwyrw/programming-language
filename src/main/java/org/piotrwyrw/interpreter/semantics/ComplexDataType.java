package org.piotrwyrw.interpreter.semantics;

public class ComplexDataType extends DataType {

    private String identifier;
    private Variable[] variables;

    public ComplexDataType(String identifier, Variable[] variables) {
        this.identifier = identifier;
        this.variables = variables;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Variable[] variables() {
        return variables;
    }

    public void setVariables(Variable[] variables) {
        this.variables = variables;
    }
}
