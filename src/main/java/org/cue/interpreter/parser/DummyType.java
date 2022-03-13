package org.cue.interpreter.parser;

import org.cue.interpreter.semantics.DataType;

public class DummyType extends DataType {

    private IdentifierNode identifier;

    public DummyType(IdentifierNode identifier) {
        this.identifier = identifier;
    }

    public IdentifierNode identifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierNode identifier) {
        this.identifier = identifier;
    }
}
