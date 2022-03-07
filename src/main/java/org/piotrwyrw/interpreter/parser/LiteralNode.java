package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.semantics.PrimitiveType;

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
