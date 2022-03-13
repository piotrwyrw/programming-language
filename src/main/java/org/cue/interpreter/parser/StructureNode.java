package org.cue.interpreter.parser;

import org.cue.interpreter.semantics.Variable;

import java.util.List;

public class StructureNode extends StatementNode {

    private IdentifierNode identifier;
    private List<Variable> elements;

    public StructureNode(IdentifierNode identifier, List<Variable> elements) {
        this.identifier = identifier;
        this.elements = elements;
    }

    public IdentifierNode identifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierNode identifier) {
        this.identifier = identifier;
    }

    public List<Variable> elements() {
        return elements;
    }

    public void setElements(List<Variable> elements) {
        this.elements = elements;
    }
}
