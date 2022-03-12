package org.cue.interpreter.semantics;

public class PrimitiveDataType extends DataType {

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
