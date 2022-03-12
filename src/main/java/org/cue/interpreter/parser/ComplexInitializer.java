package org.cue.interpreter.parser;

import org.cue.interpreter.Pair;

import java.util.List;

public class ComplexInitializer extends ExpressionNode {

    private List<Pair<String, ExpressionNode>> expressions;

    public ComplexInitializer(List<Pair<String, ExpressionNode>> expressions) {
        this.expressions = expressions;
    }

    public List<Pair<String, ExpressionNode>> expressions() {
        return expressions;
    }

    public void setExpressions(List<Pair<String, ExpressionNode>> expressions) {
        this.expressions = expressions;
    }
}
