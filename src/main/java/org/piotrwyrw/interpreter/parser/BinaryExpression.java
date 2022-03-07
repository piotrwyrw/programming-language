package org.piotrwyrw.interpreter.parser;

public class BinaryExpression extends ExpressionNode {

    private BinaryOperator op;
    private ExpressionNode left;
    private ExpressionNode right;

    public BinaryExpression(BinaryOperator op, ExpressionNode left, ExpressionNode right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public BinaryOperator op() {
        return op;
    }

    public void setOp(BinaryOperator op) {
        this.op = op;
    }

    public ExpressionNode left() {
        return left;
    }

    public void setLeft(ExpressionNode left) {
        this.left = left;
    }

    public ExpressionNode right() {
        return right;
    }

    public void setRight(ExpressionNode right) {
        this.right = right;
    }

}
