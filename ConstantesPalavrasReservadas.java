package util;

import java.util.HashMap;
import java.util.Map;

public class ConstantesPalavrasReservadas {
	public static final Map<String, Integer> PALAVRAS_RESERVADAS = new HashMap<String, Integer>();

	static {
		PALAVRAS_RESERVADAS.put("AND", Integer.valueOf(23));
		PALAVRAS_RESERVADAS.put("BEGIN", Integer.valueOf(6));
		PALAVRAS_RESERVADAS.put("CALL", Integer.valueOf(11));
		PALAVRAS_RESERVADAS.put("CASE", Integer.valueOf(29));
		PALAVRAS_RESERVADAS.put("CONST", Integer.valueOf(3));
		PALAVRAS_RESERVADAS.put("DO", Integer.valueOf(17));
		PALAVRAS_RESERVADAS.put("ELSE", Integer.valueOf(15));
		PALAVRAS_RESERVADAS.put("END", Integer.valueOf(7));
		PALAVRAS_RESERVADAS.put("FOR", Integer.valueOf(27));
		PALAVRAS_RESERVADAS.put("IF", Integer.valueOf(13));
		PALAVRAS_RESERVADAS.put("INTEGER", Integer.valueOf(8));
		PALAVRAS_RESERVADAS.put("NOT", Integer.valueOf(24));
		PALAVRAS_RESERVADAS.put("OF", Integer.valueOf(10));
		PALAVRAS_RESERVADAS.put("OR", Integer.valueOf(22));
		PALAVRAS_RESERVADAS.put("PROCEDURE", Integer.valueOf(5));
		PALAVRAS_RESERVADAS.put("PROGRAM", Integer.valueOf(1));
		PALAVRAS_RESERVADAS.put("READLN", Integer.valueOf(20));
		PALAVRAS_RESERVADAS.put("REPEAT", Integer.valueOf(18));
		PALAVRAS_RESERVADAS.put("THEN", Integer.valueOf(14));
		PALAVRAS_RESERVADAS.put("TO", Integer.valueOf(28));
		PALAVRAS_RESERVADAS.put("UNTIL", Integer.valueOf(19));
		PALAVRAS_RESERVADAS.put("VAR", Integer.valueOf(4));
		PALAVRAS_RESERVADAS.put("WHILE", Integer.valueOf(16));
		PALAVRAS_RESERVADAS.put("WRITELN", Integer.valueOf(21));
		}

}