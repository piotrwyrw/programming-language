package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.semantics.Variable;

import java.util.List;

public class StructureNode extends StatementNode {

    private String identifier;
    private List<Variable> elements;

    public StructureNode(String identifier, List<Variable> elements) {
        this.identifier = identifier;
        this.elements = elements;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Variable> elements() {
        return elements;
    }

    public void setElements(List<Variable> elements) {
        this.elements = elements;
    }
}
