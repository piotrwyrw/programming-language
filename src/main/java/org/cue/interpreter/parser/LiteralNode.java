package org.cue.interpreter.parser;

import org.cue.interpreter.semantics.PrimitiveType;

public class LiteralNode<T> extends ExpressionNode {

    private T value;
    private PrimitiveType type;

    public LiteralNode(T value) {
        this.value = value;
    }

    public T value() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
