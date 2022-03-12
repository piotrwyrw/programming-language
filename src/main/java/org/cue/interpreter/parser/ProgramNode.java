package org.cue.interpreter.parser;

import java.util.List;

public class ProgramNode extends GenericNode {

    private List<GenericNode> nodes;

    public ProgramNode(List<GenericNode> nodes) {
        this.nodes = nodes;
    }

    public List<GenericNode> nodes() {
        return nodes;
    }

    public void setNodes(List<GenericNode> nodes) {
        this.nodes = nodes;
    }
}
