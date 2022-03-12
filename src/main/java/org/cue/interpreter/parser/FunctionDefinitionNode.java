package org.cue.interpreter.parser;

import org.cue.interpreter.semantics.Signature;

public class FunctionDefinitionNode {

    private String identifier;
    private CompoundExpression block;
    private Signature signature;

    public FunctionDefinitionNode(String identifier, CompoundExpression block, Signature signature) {
        this.identifier = identifier;
        this.block = block;
        this.signature = signature;
    }

    public String identifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
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
