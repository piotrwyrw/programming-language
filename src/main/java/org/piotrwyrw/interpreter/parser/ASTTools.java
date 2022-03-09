package org.piotrwyrw.interpreter.parser;

import org.piotrwyrw.interpreter.semantics.DataType;
import org.piotrwyrw.interpreter.semantics.PrimitiveDataType;
import org.piotrwyrw.interpreter.semantics.PrimitiveType;

public class ASTTools {

    public static void analyze(GenericNode node) {
        analyze(node, 0);
    }

    private static String spacer(int d) {
        String str = "";
        for (int i = 0; i < d; i ++) {
            str += "\t";
        }
        return str;
    }

    private static void print(int d, String str) {
        print(d, str, true);
    }

    private static void print(int d, String str, boolean endl) {
        System.out.print(spacer(d) + str + (endl ? "\n" : ""));
    }

    public static void analyzeType(DataType type, int depth) {
        if (type instanceof DummyType) {
            DummyType dt = ((DummyType) type);
            print(depth, "Dummy Type => " + dt.identifier());
        }
        if (type instanceof PrimitiveDataType) {
            PrimitiveDataType pdt = ((PrimitiveDataType) type);
            print(depth, "Primitive Type => " + pdt.type().toString());
        }
    }

    private static void analyze(GenericNode node, int d) {
        int depth = d;
        if (node instanceof ProgramNode) {
            ProgramNode n = ((ProgramNode) node);
            print(depth, "Program ->");
            depth ++;
            for (GenericNode genericNode : n.nodes()) {
                analyze(genericNode, depth);
            }
            depth --;
        }
        if (node instanceof BinaryExpression) {
            BinaryExpression expr = ((BinaryExpression) node);
            print(depth, "Binary Expression [" + expr.op().toString() + "] ->");
            depth ++;
            print(depth, "Left =>");
            depth ++;
            analyze(expr.left(), depth);
            depth --;
            print(depth, "Right =>");
            depth ++;
            analyze(expr.right(), depth);
            depth --;
        }
        if (node instanceof LiteralNode<?>) {
            LiteralNode<?> n = ((LiteralNode<?>) node);
            print(depth, "Literal node => ", false);
            if (n.value() instanceof String) {
                System.out.println("(String) " + n.value());
            }
            if (n.value() instanceof Integer) {
                System.out.println("(Integer) " + n.value());
            }
        }
        if (node instanceof VariableDeclarationNode) {
            VariableDeclarationNode n = ((VariableDeclarationNode) node);
            print(depth, "Variable declaration ->");
            depth ++;
            print(depth, "Type -> ", true);
            depth ++;
            analyzeType(n.type(), depth);
            depth --;
            print(depth, "Name => " + n.identifier());
            print(depth, "Value ->");
            depth ++;
            analyze(n.value(), depth);
            depth -= 2;
        }
        if (node instanceof IdentifierNode) {
            IdentifierNode id = ((IdentifierNode) node);
            print(depth, "Identifier node => " + id.identifier());
        }
        if (node instanceof ListExpression) {
            ListExpression lxp = ((ListExpression) node);
            print(depth, "List Expression ->");
            depth ++;
            for (int i = 0; i < lxp.expressions().size(); i ++) {
                print(depth, "Element " + i + " ->");
                depth ++;
                analyze(lxp.expressions().get(i), depth);
                depth --;
            }
            depth --;
        }
    }

}
