package org.cue.interpreter.parser;

import org.cue.interpreter.semantics.Signature;

public class FunctionDefinitionNode {

    private IdentifierNode identifier;
    private CompoundExpression block;
    private Signature signature;

    public FunctionDefinitionNode(IdentifierNode identifier, CompoundExpression block, Signature signature) {
        this.identifier = identifier;
        this.block = block;
        this.signature = signature;
    }

    public IdentifierNode identifier() {
        return identifier;
    }

    public void setIdentifier(IdentifierNode identifier) {
        this.identifier = identifier;
    }

    public CompoundExpression block() {
        return block;
    }

    public void setBlock(CompoundExpression block) {
        this.block = block;
    }

    public Signature signature() {
        return signature;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }
}
