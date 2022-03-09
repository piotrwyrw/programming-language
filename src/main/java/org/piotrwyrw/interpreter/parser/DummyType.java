package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.semantics.DataType;

public class DummyType extends DataType {

    private String identifier;

    public DummyType(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
