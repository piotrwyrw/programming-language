package org.piotrwyrw.interpreter.parser;

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
    }

}
