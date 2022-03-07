package org.piotrwyrw.interpreter.parser;

public class CompoundExpression extends ExpressionNode {

    private StatementNode[] nodes;

    public CompoundExpression(StatementNode[] nodes) {
        this.nodes = nodes;
    }

    public StatementNode[] nodes() {
        return nodes;
    }

    public void setNodes(StatementNode[] nodes) {
        this.nodes = nodes;
    }
}
