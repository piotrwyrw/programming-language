package org.piotrwyrw.interpreter.parser;

public class PreprocessorStatement extends PreprocessorNode {

    private PreprocessorStatementType type;
    private String parameter;

    public PreprocessorStatement(PreprocessorStatementType type, String parameter) {
        this.type = type;
        this.parameter = parameter;
    }

    public PreprocessorStatementType type() {
        return type;
    }

    public void setType(PreprocessorStatementType type) {
        this.type = type;
    }

    public String parameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
