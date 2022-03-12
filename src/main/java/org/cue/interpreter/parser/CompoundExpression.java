package org.cue.interpreter.parser;

import java.util.List;

public class CompoundExpression extends ExpressionNode {

    private List<StatementNode> nodes;

    public CompoundExpression(List<StatementNode> nodes) {
        this.nodes = nodes;
    }

    public List<StatementNode> nodes() {
        return nodes;
    }

    public void setNodes(List<StatementNode> nodes) {
        this.nodes = nodes;
    }
}
