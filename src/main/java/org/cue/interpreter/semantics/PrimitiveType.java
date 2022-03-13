package org.cue.interpreter.semantics;

public enum PrimitiveType {

    STRING,
    INT,
    CHAR,
    BOOL;

    public static PrimitiveType fromString(String str) {
        if (str.equals("string")) return STRING;
        if (str.equals("int")) return INT;
        if (str.equals("char")) return CHAR;
        if (str.equals("bool")) return BOOL;
        return null;
    }

}
