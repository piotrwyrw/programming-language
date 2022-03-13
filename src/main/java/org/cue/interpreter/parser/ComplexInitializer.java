package org.cue.interpreter.parser;

import org.cue.interpreter.entity.Pair;

import java.util.List;

public class ComplexInitializer extends ExpressionNode {

    private List<Pair<IdentifierNode, ExpressionNode>> expressions;

    public ComplexInitializer(List<Pair<IdentifierNode, ExpressionNode>> expressions) {
        this.expressions = expressions;
    }

    public List<Pair<IdentifierNode, ExpressionNode>> expressions() {
        return expressions;
    }

    public void setExpressions(List<Pair<IdentifierNode, ExpressionNode>> expressions) {
        this.expressions = expressions;
    }
}
