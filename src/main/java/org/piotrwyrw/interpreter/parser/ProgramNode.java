package org.piotrwyrw.interpreter.parser;

public class ProgramNode extends GenericNode {

    private GenericNode[] nodes;

    public ProgramNode(GenericNode[] nodes) {
        this.nodes = nodes;
    }

    public GenericNode[] nodes() {
        return nodes;
    }

    public void setNodes(GenericNode[] nodes) {
        this.nodes = nodes;
    }

}
