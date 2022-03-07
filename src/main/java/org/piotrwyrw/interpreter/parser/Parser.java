package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.tokenizer.Token;
import org.piotrwyrw.interpreter.tokenizer.TokenStream;
import org.piotrwyrw.interpreter.tokenizer.TokenType;
import org.piotrwyrw.interpreter.util.Error;

public class Parser {

    private TokenStream stream;

    public Parser(TokenStream stream) {
        this.stream = stream;
    }

    public TokenStream stream() {
        return stream;
    }

    public void setStream(TokenStream stream) {
        this.stream = stream;
    }

    public ExpressionNode parseAdditiveExpression() {
        ExpressionNode left = parseMultiplicativeExpression();
        if (stream.isLast()) {
            return left;
        }
        BinaryOperator op = BinaryOperator.UNKNOWN;
        while (stream.get().type() == TokenType.PLUS || stream.get().type() == TokenType.DASH) {
            op = BinaryOperator.fromTokenType(stream.get().type());
            stream.next();
            ExpressionNode right = parseMultiplicativeExpression();
            left = new BinaryExpression(op, left, right);
        }
        return left;
    }

    public ExpressionNode parseMultiplicativeExpression() {
        ExpressionNode left = parsePrimaryExpression();
        if (stream.isLast()) {
            return left;
        }
        stream.next();
        BinaryOperator op = BinaryOperator.UNKNOWN;
        while (stream.get().type() == TokenType.ASTERISK || stream.get().type() == TokenType.SLASH) {
            op = BinaryOperator.fromTokenType(stream.get().type());
            stream.next();
            ExpressionNode right = parseMultiplicativeExpression();
            left = new BinaryExpression(op, left, right);
        }
        return left;
    }

    public ExpressionNode parsePrimaryExpression() {
        Token t = stream.get();
        ExpressionNode node = null;
        if (t.type() == TokenType.INTEGER_LITERAL) {
            node = new LiteralNode<Integer>(Integer.parseInt(t.value()));
        } else if (t.type() == TokenType.STRING_LITERAL) {
            node = new LiteralNode<String>(t.value());
        } else if (t.type() == TokenType.LPAREN) {
            ExpressionNode expr = parseAdditiveExpression();
            if (t.type() != TokenType.RPAREN) {
                Error.error(t, "Expected a ')' after the expression.");
            }
            node = expr;
        } else {
            Error.error(t, "Expected literal or subexpression.");
        }
        return node;
    }


}
