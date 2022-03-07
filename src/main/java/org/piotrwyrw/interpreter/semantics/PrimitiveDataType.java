package org.piotrwyrw.interpreter.semantics;

public class PrimitiveDataType {

    private PrimitiveType type;

    public PrimitiveDataType(PrimitiveType type) {
        this.type = type;
    }

    public PrimitiveType type() {
        return type;
    }

    public void setType(PrimitiveType type) {
        this.type = type;
    }
}
