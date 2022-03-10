package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.Pair;
import org.piotrwyrw.interpreter.semantics.DataType;
import org.piotrwyrw.interpreter.semantics.PrimitiveDataType;
import org.piotrwyrw.interpreter.semantics.PrimitiveType;
import org.piotrwyrw.interpreter.semantics.Variable;
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

    public PreprocessorBlock parsePreprocessors() {
        List<PreprocessorStatement> statements = new ArrayList<>();
        int flag_line = -1;
        while (stream.hasNext()) {
            Token current = stream.current();

            if (current.type() != TokenType.HASH) {
                flag_line = current.line();
            }

            if (current.line() == flag_line) {
                if (stream.hasNext()) stream.consume();
                else break;
                continue;
            }

            statements.add(parsePreprocessorStatement());
        }
        return new PreprocessorBlock(statements);
    }

    // # COMMAND "PARAMETER"
    public PreprocessorStatement parsePreprocessorStatement() {
        // Get line
        int line = stream.current().line();

        // Skip '#'
        stream.consume();

        if (stream.current().line() != line) {
            Error.error(stream.current(), "The full preprocessor statement must fit in a single line.");
        }

        if (stream.current().type() != TokenType.IDENTIFIER) {
            Error.error(stream.current(), "Expected preprocessor command after '#'.");
        }

        String command = stream.current().value();
        String parameter = null;

        // Get optional parameter

        if (stream.hasNext()) {
            stream.consume();
            if (stream.current().line() == line && stream.current().type() == TokenType.STRING_LITERAL) {
                parameter = stream.current().value();
            }
        }

        return new PreprocessorStatement(PreprocessorStatementType.valueOf(command.toUpperCase()), parameter);
    }

    // Parse the whole program, hopefully
    public ProgramNode parseProgram() {
        List<GenericNode> nodes = new ArrayList<>();
        int flag_line = -1;

        while (stream.hasCurrent()) {
            Token current = stream.current();
            Token next = stream.next();

            // Ignore any preprocessor statements
            if (current.type() == TokenType.HASH) {
                flag_line = current.line();
            }

            if (current.line() == flag_line) {
                if (stream.hasNext()) stream.consume();
                else break;
                continue;
            }

            else if (current.type() == TokenType.IDENTIFIER && !current.value().equals("structure") && next.type() == TokenType.IDENTIFIER) {
                nodes.add(parseVariableDeclarationMode());
            }

            else if (current.type() == TokenType.IDENTIFIER && current.value().equals("structure") && next.type() == TokenType.IDENTIFIER) {
                nodes.add(parseStructureNode());
            }

            // If the two first tokens don't match any syntax, just assume it's
            // an expression.
            else {
                nodes.add(parseAdditiveExpression());

            }

            if (stream.hasNext()) stream.consume();
        }
        return new ProgramNode(nodes);
    }

    // Type name = expression;
    public StatementNode parseVariableDeclarationMode() {

        String types = "";
        DataType type = null;
        String identifier = "";
        ExpressionNode xpr = null;

        if (stream.current().type() == TokenType.IDENTIFIER) {
            types = stream.current().value();
        } else {
            Error.error(stream.current(), "Expected type.");
        }

        if (types.equals("string") || types.equals("int")) {
            type = new PrimitiveDataType(PrimitiveType.valueOf(types.toUpperCase()));
        } else {
            type = new DummyType(types);
        }

        stream.consume();

        if (stream.current().type() != TokenType.IDENTIFIER) {
            Error.error(stream.current(), "Expected variable name.");
        }

        identifier = stream.current().value();

        stream.consume();

        if (stream.current().type() != TokenType.EQUALS) {
            Error.error(stream.current(), "Expected '='");
        }

        stream.consume();

        xpr = parseAdditiveExpression();

//        stream.consume();

        if (stream.current().type() != TokenType.SEMICOLON) {
            Error.error(stream.current(), "Expected ';'");
        }

        return new VariableDeclarationNode(identifier, type, xpr);
    }

    public StructureNode parseStructureNode() {
        stream.consume(); // Skip 'structure'
        if (stream.current().type() != TokenType.IDENTIFIER) {
            Error.error(stream.current(), "Expected identifier.");
        }
        String id = stream.current().value();
        List<Variable> vars = new ArrayList<>();
        stream.consume();
        if (stream.current().type() != TokenType.LBRACE) {
            Error.error(stream.current(), "Expected '{'");
        }
        stream.consume();

        // type name;
        while (stream.current().type() != TokenType.RBRACE) {
            if (stream.current().type() != TokenType.IDENTIFIER) {
                Error.error(stream.current(), "Expected type name or '}'");
            }
            String vtp = stream.current().value();
            stream.consume();
            if (stream.current().type() != TokenType.IDENTIFIER) {
                Error.error(stream.current(), "Expected field name.");
            }
            String vid = stream.current().value();
            stream.consume();
            DataType type = null;
            if (PrimitiveType.valueOf(vtp.toUpperCase()) != null) {
                type = new PrimitiveDataType(PrimitiveType.valueOf(vtp.toUpperCase()));
            } else {
                type = new DummyType(vtp);
            }
            vars.add(new Variable(vid, type));
            if (stream.current().type() == TokenType.RBRACE) {
                break;
            } else if (stream.current().type() != TokenType.COMMA) {
                stream.consume();
                continue;
            } else {
                Error.error(stream.current(), "Expected ',' and more elements or '}'");
            }
        }
        return new StructureNode(id, vars);
    }

    public ExpressionNode parseAdditiveExpression() {
        ExpressionNode left = parseMultiplicativeExpression();
        if (stream.hasNext()) {
            return left;
        }
        stream.consume();
        while (stream.current().type() == TokenType.PLUS || stream.current().type() == TokenType.DASH || stream.current().type() == TokenType.POINT_LEFT || stream.current().type() == TokenType.POINT_RIGHT) {
            BinaryOperator op = BinaryOperator.fromTokenType(stream.current().type());
            stream.consume();
            left = new BinaryExpression(op, left, parseAdditiveExpression());
        }
        return left;
    }

    public ExpressionNode parseMultiplicativeExpression() {
        ExpressionNode left = parsePrimaryExpression();
        if (stream.hasNext()) {
            return left;
        }
        stream.consume();
        while (stream.current().type() == TokenType.ASTERISK || stream.current().type() == TokenType.SLASH) {
            BinaryOperator op = BinaryOperator.fromTokenType(stream.current().type());
            stream.consume();
            left = new BinaryExpression(op, left, parseMultiplicativeExpression());
        }
        return left;
    }

    public ExpressionNode parsePrimaryExpression() {
        Token t = stream.current();
        ExpressionNode node = null;
        if (t.type() == TokenType.INTEGER_LITERAL) {
            node = new LiteralNode<Integer>(Integer.parseInt(t.value()));
        } else if (t.type() == TokenType.STRING_LITERAL) {
            node = new LiteralNode<String>(t.value());
        } else if (t.type() == TokenType.LPAREN) {
            stream.consume();
            ExpressionNode expr = parseAdditiveExpression();
            t = stream.current();
            stream.consume();
            t = stream.current();
            if (t.type() != TokenType.RPAREN) {
                Error.error(t, "Expected a ')' after the expression.");
            }
            node = expr;
        } else if (t.type() == TokenType.IDENTIFIER) {
            node = new IdentifierNode(t.value());
        }

        // Complex initializer [id: expr, id: expr, id: expr ..]
        else if (t.type() == TokenType.LBRACKET) {
            List<Pair<String, ExpressionNode>> xps = new ArrayList<>();
            stream.consume();
            while (stream.current().type() != TokenType.RBRACKET) {
                if (stream.current().type() != TokenType.IDENTIFIER) {
                    Error.error(stream.current(), "Expected identifier");
                }
                String id = stream.current().value();
                stream.consume();
                if (stream.current().type() != TokenType.COLON) {
                    Error.error(stream.current(), "Expected ':'");
                }
                stream.consume();
                xps.add(new Pair(id, parseAdditiveExpression()));
                if (stream.current().type() == TokenType.COMMA) {
                    stream.consume();
                } else if (stream.current().type() == TokenType.RBRACKET) {
                    break;
                } else {
                    Error.error(stream.current(),"Expected ']' or ',' and more elements.");
                }
            }
            stream.consume();
            return new ComplexInitializer(xps);
        } else {
            Error.error(t, "Expected literal, identifier or subexpression.");
        }
        return node;
    }


}
