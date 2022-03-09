package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.semantics.DataType;
import org.piotrwyrw.interpreter.semantics.PrimitiveDataType;
import org.piotrwyrw.interpreter.semantics.PrimitiveType;
import org.piotrwyrw.interpreter.tokenizer.Token;
import org.piotrwyrw.interpreter.tokenizer.TokenStream;
import org.piotrwyrw.interpreter.tokenizer.TokenType;
import org.piotrwyrw.interpreter.util.Error;

import java.util.ArrayList;
import java.util.List;

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

    // Parse the whole program, hopefully
    public ProgramNode parseProgram() {
        List<GenericNode> nodes = new ArrayList<>();
        while (stream.hasNextFew(2)) {
            Token current = stream.get();
            Token next = stream.next();
            stream.back();

            if (current.type() == TokenType.IDENTIFIER && next.type() == TokenType.IDENTIFIER) {
                nodes.add(parseVariableDeclarationMode());
            }

            // If the two first tokens don't match any syntax, just assume it's
            // an expression.
            else {
                nodes.add(parseAdditiveExpression());
            }

            if (stream.hasNext()) stream.next();
        }
        return new ProgramNode(nodes);
    }

    // Type name = expression;
    public StatementNode parseVariableDeclarationMode() {

        String types = "";
        DataType type = null;
        String identifier = "";
        ExpressionNode xpr = null;

        if (stream.get().type() == TokenType.IDENTIFIER) {
            types = stream.get().value();
        } else {
            Error.error(stream.get(), "Expected type.");
        }

        if (types.equals("string") || types.equals("int")) {
            type = new PrimitiveDataType(PrimitiveType.fromString(types));
        } else {
            type = new DummyType(types);
        }

        stream.next();

        if (stream.get().type() != TokenType.IDENTIFIER) {
            Error.error(stream.get(), "Expected variable name.");
        }

        identifier = stream.get().value();

        stream.next();

        if (stream.get().type() != TokenType.EQUALS) {
            Error.error(stream.get(), "Expected '='");
        }

        stream.next();

        xpr = parseAdditiveExpression();

//        stream.next();

        if (stream.get().type() != TokenType.SEMICOLON) {
            Error.error(stream.get(), "Expected ';'");
        }

        return new VariableDeclarationNode(identifier, type, xpr);
    }

    public ExpressionNode parseAdditiveExpression() {
        ExpressionNode left = parseMultiplicativeExpression();
        if (stream.isLast()) {
            return left;
        }
        while (stream.get().type() == TokenType.PLUS || stream.get().type() == TokenType.DASH) {
            BinaryOperator op = BinaryOperator.fromTokenType(stream.get().type());
            stream.next();
            left = new BinaryExpression(op, left, parseAdditiveExpression());
        }
        return left;
    }

    public ExpressionNode parseMultiplicativeExpression() {
        ExpressionNode left = parsePrimaryExpression();
        if (stream.isLast()) {
            return left;
        }
        stream.next();
        while (stream.get().type() == TokenType.ASTERISK || stream.get().type() == TokenType.SLASH) {
            BinaryOperator op = BinaryOperator.fromTokenType(stream.get().type());
            stream.next();
            left = new BinaryExpression(op, left, parseMultiplicativeExpression());
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
            stream.next();
            ExpressionNode expr = parseAdditiveExpression();
            t = stream.get();
            if (t.type() != TokenType.RPAREN) {
                Error.error(t, "Expected a ')' after the expression.");
            }
            node = expr;
        } else if (t.type() == TokenType.IDENTIFIER) {
            node = new IdentifierNode(t.value());
        }

        // Complex list [expr, expr, expr ...]
        else if (t.type() == TokenType.LBRACKET) {
            List<ExpressionNode> xps = new ArrayList<>();
            stream.next();
            while (stream.get().type() != TokenType.RBRACKET) {
                xps.add(parseAdditiveExpression());
                if (stream.get().type() == TokenType.COMMA) {
                    stream.next();
                } else if (stream.get().type() == TokenType.RBRACKET) {
                    break;
                } else {
                    Error.error(stream.get(),"Expected ']' or ',' and more elements.");
                }
            }
            return new ListExpression(xps);
        } else {
            Error.error(t, "Expected literal, identifier or subexpression.");
        }
        return node;
    }


}
