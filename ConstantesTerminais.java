package util;

import java.util.HashMap;
import java.util.Map;

public class ConstantesTerminais {
	public static final Map<String, Integer> TERMINAIS = new HashMap<String, Integer>();

	static {
	TERMINAIS.put("î", Integer.valueOf(0));
	TERMINAIS.put("PROGRAM", Integer.valueOf(1));
	TERMINAIS.put("INTEIRO", Integer.valueOf(2));
	TERMINAIS.put("CONST", Integer.valueOf(3));
	TERMINAIS.put("VAR", Integer.valueOf(4));
	TERMINAIS.put("PROCEDURE", Integer.valueOf(5));
	TERMINAIS.put("BEGIN", Integer.valueOf(6));
	TERMINAIS.put("END", Integer.valueOf(7));
	TERMINAIS.put("INTEGER", Integer.valueOf(8));
	TERMINAIS.put("OF", Integer.valueOf(10));
	TERMINAIS.put("CALL", Integer.valueOf(11));
	TERMINAIS.put("IF", Integer.valueOf(13));
	TERMINAIS.put("THEN", Integer.valueOf(14));
	TERMINAIS.put("ELSE", Integer.valueOf(15));
	TERMINAIS.put("WHILE", Integer.valueOf(16));
	TERMINAIS.put("DO", Integer.valueOf(17));
	TERMINAIS.put("REPEAT", Integer.valueOf(18));
	TERMINAIS.put("UNTIL", Integer.valueOf(19));
	TERMINAIS.put("READLN", Integer.valueOf(20));
	TERMINAIS.put("WRITELN", Integer.valueOf(21));
	TERMINAIS.put("OR", Integer.valueOf(22));
	TERMINAIS.put("AND", Integer.valueOf(23));
	TERMINAIS.put("NOT", Integer.valueOf(24));
	TERMINAIS.put("ID", Integer.valueOf(25));
	TERMINAIS.put("INTEIRO", Integer.valueOf(26));
	TERMINAIS.put("FOR", Integer.valueOf(27));
	TERMINAIS.put("TO", Integer.valueOf(28));
	TERMINAIS.put("CASE", Integer.valueOf(29));
	TERMINAIS.put("+", Integer.valueOf(30));
	TERMINAIS.put("-", Integer.valueOf(31));
	TERMINAIS.put("*", Integer.valueOf(32));
	TERMINAIS.put("/", Integer.valueOf(33));
	TERMINAIS.put("(", Integer.valueOf(36));
	TERMINAIS.put(")", Integer.valueOf(37));
	TERMINAIS.put(":=", Integer.valueOf(38));
	TERMINAIS.put(":", Integer.valueOf(39));
	TERMINAIS.put("=", Integer.valueOf(40));
	TERMINAIS.put(">", Integer.valueOf(41));
	TERMINAIS.put(">=", Integer.valueOf(42));
	TERMINAIS.put("<", Integer.valueOf(43));
	TERMINAIS.put("<=", Integer.valueOf(44));
	TERMINAIS.put("<>", Integer.valueOf(45));
	TERMINAIS.put(",", Integer.valueOf(46));
	TERMINAIS.put(";", Integer.valueOf(47));
	TERMINAIS.put("LITERAL", Integer.valueOf(48));
	TERMINAIS.put(".", Integer.valueOf(49));
	TERMINAIS.put("$", Integer.valueOf(51));
	}
}