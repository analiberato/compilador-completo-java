package util;

import java.util.HashMap;
import java.util.Map;

public class ConstantesNaoTerminais {
	public static final Map<String, Integer> NAOTERMINAIS = new HashMap<String, Integer>();

	static {
	NAOTERMINAIS.put("<PROGRAMA>", Integer.valueOf(52));
	NAOTERMINAIS.put("<BLOCO>", Integer.valueOf(53));
	NAOTERMINAIS.put("<LID>", Integer.valueOf(54));
	NAOTERMINAIS.put("<REPID>", Integer.valueOf(55));
	NAOTERMINAIS.put("<DCLCONST>", Integer.valueOf(56));
	NAOTERMINAIS.put("<LDCONST>", Integer.valueOf(57));
	NAOTERMINAIS.put("<DCLVAR>", Integer.valueOf(58));
	NAOTERMINAIS.put("<LDVAR>", Integer.valueOf(59));
	NAOTERMINAIS.put("<TIPO>", Integer.valueOf(60));
	NAOTERMINAIS.put("<DCLPROC>", Integer.valueOf(61));
	NAOTERMINAIS.put("<DEFPAR>", Integer.valueOf(62));
	NAOTERMINAIS.put("<CORPO>", Integer.valueOf(63));
	NAOTERMINAIS.put("<REPCOMANDO>", Integer.valueOf(64));
	NAOTERMINAIS.put("<COMANDO>", Integer.valueOf(65));
	NAOTERMINAIS.put("<PARAMETROS>", Integer.valueOf(66));
	NAOTERMINAIS.put("<REPPAR>", Integer.valueOf(67));
	NAOTERMINAIS.put("<ELSEPARTE>", Integer.valueOf(68));
	NAOTERMINAIS.put("<VARIAVEL>", Integer.valueOf(69));
	NAOTERMINAIS.put("<REPVARIAVEL>", Integer.valueOf(70));
	NAOTERMINAIS.put("<ITEMSAIDA>", Integer.valueOf(71));
	NAOTERMINAIS.put("<REPITEM>", Integer.valueOf(72));
	NAOTERMINAIS.put("<EXPRESSAO>", Integer.valueOf(73));
	NAOTERMINAIS.put("<REPEXPSIMP>", Integer.valueOf(74));
	NAOTERMINAIS.put("<EXPSIMP>", Integer.valueOf(75));
	NAOTERMINAIS.put("<REPEXP>", Integer.valueOf(76));
	NAOTERMINAIS.put("<TERMO>", Integer.valueOf(77));
	NAOTERMINAIS.put("<REPTERMO>", Integer.valueOf(78));
	NAOTERMINAIS.put("<FATOR>", Integer.valueOf(79));
	NAOTERMINAIS.put("<CONDCASE>", Integer.valueOf(80));
	NAOTERMINAIS.put("<CONTCASE>", Integer.valueOf(81));
	NAOTERMINAIS.put("<RPINTEIRO>", Integer.valueOf(82));
	}
}