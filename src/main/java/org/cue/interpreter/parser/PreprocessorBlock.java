package org.cue.interpreter.parser;

import java.util.List;

public class PreprocessorBlock extends PreprocessorNode {

    private List<PreprocessorStatement> statements;

    public PreprocessorBlock(List<PreprocessorStatement> statements) {
        this.statements = statements;
    }

    public List<PreprocessorStatement> statements() {
        return statements;
    }

    public void setStatements(List<PreprocessorStatement> statements) {
        this.statements = statements;
    }
}
