package org.cue.interpreter.parser;

import org.cue.interpreter.entity.Pair;
import org.cue.interpreter.semantics.DataType;
import org.cue.interpreter.semantics.PrimitiveDataType;
import org.cue.interpreter.semantics.PrimitiveType;
import org.cue.interpreter.tokenizer.TokenStream;
import org.cue.interpreter.tokenizer.TokenType;
import org.cue.interpreter.util.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public VariableDeclarationNode parseVariableDeclaration() {
        if (stream.current().type() != TokenType.IDENTIFIER) {
            Error.error(stream.current(), "Expected identifier");
        }

        DataType type = null;
        String types = stream.current().value();
        if (PrimitiveType.fromString(types) == null) {
            type = new DummyType(stream.current().toIdentifierNode());
        } else {
            type = new PrimitiveDataType(PrimitiveType.fromString(types));
        }

        stream.consume();

        IdentifierNode iden = stream.current().toIdentifierNode();

        stream.consume();

        if (stream.current().type() != TokenType.EQUALS) {
            Error.error(stream.current(), "Expected '='");
        }

        stream.consume();

        ExpressionNode xpr = parseExpression();

        stream.consume();

        if (stream.current().type() != TokenType.SEMICOLON) {
            Error.error(stream.current(), "Expected ';'");
        }

        return new VariableDeclarationNode(iden, type, xpr);
    }

    public ExpressionNode parseExpression() {
        ExpressionNode left = parseAdditiveExpression();
        if (stream.next() == null) {
            return left;
        }
        if (stream.next().isPointerOperator()) {
            stream.consume();
        }
        while (stream.current().isPointerOperator()) {
            BinaryOperator op = BinaryOperator.fromTokenType(stream.current().type());
            stream.consume();
            ExpressionNode right = parseExpression();
            left = new BinaryExpression(op, left, right);
            if (stream.next() == null) {
                break;
            }
            if (!stream.next().isPointerOperator()) {
                break;
            } else {
                stream.consume();
                continue;
            }
        }
        return left;
    }

    public ExpressionNode parseAdditiveExpression() {
        ExpressionNode left = parseMultiplicativeExpression();
        if (stream.next() == null) {
            return left;
        }
        if (stream.next().isAdditiveOperator()) {
            stream.consume();
        }
        while (stream.current().isAdditiveOperator()) {
            BinaryOperator op = BinaryOperator.fromTokenType(stream.current().type());
            stream.consume();
            ExpressionNode right = parseAdditiveExpression();
            left = new BinaryExpression(op, left, right);
            if (stream.next() == null) {
                break;
            }
            if (!stream.next().isAdditiveOperator()) {
                break;
            } else {
                stream.consume();
                continue;
            }
        }
        return left;
    }

    public ExpressionNode parseMultiplicativeExpression() {
        ExpressionNode left = parsePrimaryExpression();
        if (stream.next() == null) {
            return left;
        }
        if (stream.next().isMultiplicativeOperator()) {
            stream.consume();
        }
        while (stream.current().isMultiplicativeOperator()) {
            BinaryOperator op = BinaryOperator.fromTokenType(stream.current().type());
            stream.consume();
            ExpressionNode right = parseMultiplicativeExpression();
            left = new BinaryExpression(op, left, right);
            if (stream.next() == null) {
                break;
            }
            if (!stream.next().isMultiplicativeOperator()) {
                break;
            } else {
                stream.consume();
                continue;
            }
        }
        return left;
    }

    public ExpressionNode parsePrimaryExpression() {
        if (shouldParseLiteral()) {
            return parseLiteral();
        } else if (stream.current().type() == TokenType.IDENTIFIER) {
            return parseIdentifier();
        } else if (stream.current().type() == TokenType.LPAREN) {
            return parseSubexpression();
        } else if (stream.current().type() == TokenType.LBRACKET) {
            return parseComplexInitializer();
        } else {
            Error.error(stream.current(), "Bad primary expression.");
        }
        return null;
    }

    private boolean shouldParseLiteral() {
        TokenType t = stream.current().type();
        return
                t == TokenType.STRING_LITERAL || t == TokenType.INTEGER_LITERAL ||
                t == TokenType.TRUE || t == TokenType.FALSE || t == TokenType.DASH;
    }

    public LiteralNode<?> parseLiteral() {
        if (stream.current().type() == TokenType.STRING_LITERAL) {
            return new LiteralNode<String>(stream.current().value());
        } else if (stream.current().type() == TokenType.INTEGER_LITERAL) {
            return new LiteralNode<Integer>(Integer.parseInt(stream.current().value()));
        } else if (stream.current().type() == TokenType.TRUE) {
            return new LiteralNode<Boolean>(true);
        } else if (stream.current().type() == TokenType.FALSE) {
            return new LiteralNode<Boolean>(false);
        } else if (stream.current().type() == TokenType.DASH) {
            return parseNegativeLiteral();
        } else {
            Error.error(stream.current(), "Bad literal.");
        }
        return null;
    }

    public LiteralNode<?> parseNegativeLiteral() {
        if (stream.next() == null) {
            Error.error(stream.current(), "Expected integer literal after '-'");
        }
        if (stream.next().type() != TokenType.INTEGER_LITERAL) {
            Error.error(stream.current(), "Expected integer literal.");
        }
        stream.consume(); // Skip '-'
        ExpressionNode xpr = parsePrimaryExpression();
        if (!(xpr instanceof LiteralNode<?>)) {
            Error.error("Not a literal node.");
        }
        LiteralNode<?> n = ((LiteralNode<?>) xpr);
        if (!(n.value() instanceof Integer)) {
            Error.error(stream.current(), "Not an integer literal.");
        }
        int i = ((Integer) n.value()).intValue();
        i = (i > 0) ? i * -1 : i;
        ((LiteralNode<Integer>) n).setValue(i);
        return n;
    }

    public IdentifierNode parseIdentifier() {
        if (stream.current().type() != TokenType.IDENTIFIER) {
            Error.error(stream.current(), "Expected identifier.");
        }
        return new IdentifierNode(stream.current());
    }

    public ExpressionNode parseSubexpression() {
        if (stream.current().type() != TokenType.LPAREN) {
            Error.error(stream.current(), "Expected '('");
        }

        // Skip '('
        stream.consume();

        ExpressionNode node = parseAdditiveExpression();

        // Skip last char of expr. and get to ')'
        stream.consume();

        if (stream.current().type() != TokenType.RPAREN) {
            Error.error(stream.current(), "Expected ')' after expression.");
        }

        return node;
    }

    public ComplexInitializer parseComplexInitializer() {
        if (stream.current().type() != TokenType.LBRACKET) {
            Error.error(stream.current(), "Expoected '['");
        }

        List<Pair<IdentifierNode, ExpressionNode>> xprs = new ArrayList<>();

        stream.consume();

        while (stream.current().type() != TokenType.RBRACKET) {
            if (stream.current().type() != TokenType.IDENTIFIER) {
                Error.error(stream.current(), "Expected identifier");
            }
            IdentifierNode iden = stream.current().toIdentifierNode();
            stream.consume();
            if (stream.current().type() != TokenType.COLON) {
                Error.error(stream.current(), "Expected ';'");
            }
            stream.consume();
            ExpressionNode xpr = parseExpression();
            stream.consume();
            xprs.add(new Pair<>(iden, xpr));
            if (stream.current().type() == TokenType.RBRACKET) {
                break;
            } else if (stream.current().type() == TokenType.COMMA) {
                stream.consume();
                continue;
            } else {
                Error.error("Expected ']' or ',' and more elements");
            }
        }
        return new ComplexInitializer(xprs);
    }


}
