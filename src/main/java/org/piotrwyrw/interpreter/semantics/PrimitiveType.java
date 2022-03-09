package org.piotrwyrw.interpreter.semantics;

public enum PrimitiveType {

    STRING,
    INT;

    public static PrimitiveType fromString(String str) {
        if (str.equals("string")) {
            return STRING;
        } else if (str.equals("int")) {
            return INT;
        } else {
            return null;
        }
    }

}
