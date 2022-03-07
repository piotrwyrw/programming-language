package org.piotrwyrw.interpreter;

import org.piotrwyrw.interpreter.parser.*;
import org.piotrwyrw.interpreter.tokenizer.TokenStream;
import org.piotrwyrw.interpreter.tokenizer.Tokenizer;

public class Main {

    public static void main(String[] args) {
        String input = "2+2*2";
        Tokenizer tokenizer = new Tokenizer(input);
        tokenizer.analyze();
        TokenStream stream = new TokenStream(tokenizer.tokens());
        Parser parser = new Parser(stream);
        ExpressionNode node = parser.parseAdditiveExpression();
        ASTTools.analyze(node);
    }

}
