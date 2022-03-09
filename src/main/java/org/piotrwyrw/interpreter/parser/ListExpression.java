package org.piotrwyrw.interpreter.parser;

import java.util.List;

public class ListExpression extends ExpressionNode {

    private List<ExpressionNode> expressions;

    public ListExpression(List<ExpressionNode> expressions) {
        this.expressions = expressions;
    }

    public List<ExpressionNode> expressions() {
        return expressions;
    }

    public void setExpressions(List<ExpressionNode> expressions) {
        this.expressions = expressions;
    }
}
