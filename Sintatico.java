package sintatico;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lexico.Token;
import semantico.Semantico;
import util.ConstantesNaoTerminais;
import util.ConstantesSemanticas;
import util.ConstantesTerminais;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Sintatico {
	/**
	 * classe que CONTÉM os métodos necessários para a analise sintática, procurando as regras da gramatica
	 * *e verificando se são validas ou não
	 * *colocando as na pilha da analise sintatica e as expandindo
	**/

	private String msgSintatico = "";
	private Integer[][] gramatica;
	private Integer[][] matrizParse;
	private boolean erro;
	private Semantico semantico;


	public String getMsgRetornoSintatico() {
		if (!isErroSintatico())
			msgSintatico = "Análise Sintática executada com sucesso!";
		return msgSintatico;
	}

	public boolean isErroSintatico() {
		return erro;
	}

	public Semantico getSemantico() {
		return semantico;
	}


	public Sintatico() {
		this.gramatica = carregaGramatica(74, 14);
		this.matrizParse = carregaMatrizParse(32, 47);
	}

	//METODO QUE IMPLEMENTA A PILHA, VERIFICANDO OS TERMINAIS E NÃO TERMINAIS
	public void analisarInteiro(List<Token> tokensLexico) {
		ArrayList<Integer> reconhecidos = new ArrayList<Integer>();
		ArrayList<Integer> pilha = new ArrayList<Integer>();
		pilha.add(Integer.valueOf(51));
		pilha.add(Integer.valueOf(52));
		Integer topoPilha = (Integer)pilha.get(pilha.size() - 1);

		
		semantico = new Semantico();
		int i = 0;
		for (i = 0; i < tokensLexico.size(); i++) {

			Token token = (Token)tokensLexico.get(i);
			while ((ConstantesNaoTerminais.NAOTERMINAIS.containsValue(topoPilha)) || (ConstantesSemanticas.ACOES_SEMANTICAS.containsValue(topoPilha))) {
				if (ConstantesNaoTerminais.NAOTERMINAIS.containsValue(topoPilha)) {
					List regra = buscaRegraGramatical(topoPilha, token);
					if (regra == null)
						return;

					pilha.remove(pilha.size() - 1);
					Collections.reverse(regra);
					pilha.addAll(regra);

					topoPilha = (Integer)pilha.get(pilha.size() - 1);

					if (topoPilha.equals(Integer.valueOf(0))) {
						pilha.remove(pilha.size() - 1);
						topoPilha = (Integer)pilha.get(pilha.size() - 1);
					}
				}

				if (ConstantesSemanticas.ACOES_SEMANTICAS.containsValue(topoPilha)) {

					semantico.setAntePenultimo(((Token)tokensLexico.get(i - 2)).getToken().toLowerCase());
					semantico.setPenultimo(((Token)tokensLexico.get(i - 1)).getToken().toLowerCase());
					semantico.semantico(topoPilha.intValue());
					pilha.remove(pilha.size() - 1);
					topoPilha = (Integer)pilha.get(pilha.size() - 1);
				}
			}

			if (ConstantesTerminais.TERMINAIS.containsValue(topoPilha)) {
				if (topoPilha.equals(Integer.valueOf(token.getCodigo()))) {
					reconhecidos.add(topoPilha);
					pilha.remove(pilha.size() - 1);
				} else {
					msgSintatico = "Erro sintático, gramática não reconhece sequencia de tokens\nTopo da Pilha= " +
							topoPilha + "\n" +
							"Código do Token esperado= " + token.getCodigo() + "\n" +
							"Tipo do Token esperado= " + token.getToken() + "\n";
					erro = true;
				}

			} else {
				msgSintatico = "Erro inesperado!";
				erro = true;
			}

			topoPilha = (Integer)pilha.get(pilha.size() - 1);
		}
		if (topoPilha.equals(Integer.valueOf(101))) {
			semantico.setAntePenultimo(((Token)tokensLexico.get(i - 2)).getToken().toLowerCase());
			semantico.setPenultimo(((Token)tokensLexico.get(i - 1)).getToken().toLowerCase());
			semantico.semantico(topoPilha.intValue());
			pilha.remove(pilha.size() - 1);
			topoPilha = (Integer)pilha.get(pilha.size() - 1);
		} else {
			msgSintatico = "Erro sintático, ainda existem elementos na pilha!";
			erro = true;
		}

	}

	//METODO QUE PROCURA A REGRA QUANDO É NÃO TERMINAL
	private List<Integer> buscaRegraGramatical(Integer naoTerminal, Token token) {
		List retorno = null;
		for (int i = 0; i < this.matrizParse.length; i++) {
			if ((this.matrizParse[i][0] != null) && (this.matrizParse[i][0].equals(naoTerminal))) {
				int j = 1;
				while (j < 47) {
					
					Integer cabecalho = this.matrizParse[0][j];
					
					if (cabecalho.equals(Integer.valueOf(token.getCodigo()))) {
						Integer regraGramatica = this.matrizParse[i][j];
						if (regraGramatica.intValue() != -1) {
							retorno = retornaRegra(regraGramatica.intValue());
							break;
						}
						msgSintatico = "Erro sintático:\n Regra " + naoTerminal + " não pode ser derivada próximo a: \""+semantico.getPenultimo()+"\"";
						erro = true;
					}
					j++;
				}
			}
		}
		return retorno;
	}

	//RETORNA AS REGRAS REFERENTES AO TERMINAL
	private List<Integer> retornaRegra(int regra) {
		List regras = new ArrayList();
		int i = 0;
		Integer regraG = this.gramatica[regra][i];
		while (i < 13) {
			i++;
			regraG = this.gramatica[regra][i];
			if (regraG == null) break;
			regras.add(regraG);
		}
		return regras;
	}
	//CARREGA O ARQUIVO .TXT COM A MATRIZ DE PARSE
	public static Integer[][] carregaMatrizParse(int linhas, int colunas) {
		File file = new File("LMS/matriz_parse.txt");
		LeitorArquivos rf = new LeitorArquivos();
		String[][] matrizTemp = rf.toArray(linhas, colunas, file);
		Integer[][] result = new Integer[matrizTemp.length][matrizTemp[0].length];
		for (int i = 0; i < matrizTemp.length; i++) {
			for (int j = 0; j < matrizTemp[0].length; j++) {
				if ((i != 0) || (j != 0)) {
					if (i == 0)
						result[i][j] = Integer.valueOf(((Integer)ConstantesTerminais.TERMINAIS.get(matrizTemp[i][j])).intValue());
					else if (j == 0)
						result[i][j] = Integer.valueOf(((Integer)ConstantesNaoTerminais.NAOTERMINAIS.get(matrizTemp[i][j])).intValue());
					else
						result[i][j] = Integer.valueOf(Integer.parseInt(matrizTemp[i][j]));
				}
			}
		}
		return result;
	}

	//CARREGA O ARQUIVO .TXT COM A MATRIZ DE PARSE
	public static Integer[][] carregaGramatica(int linhas, int colunas) {
		System.out.println("CARREGANDO GRAMATICA...");
		File file = new File("LMS/gramaticaSemantico.txt");
		LeitorArquivos rf = new LeitorArquivos();
		String[][] matrizTemp = rf.toArray(linhas, colunas, file);
		Integer[][] result = new Integer[matrizTemp.length][matrizTemp[0].length];

		for (int i = 0; i < matrizTemp.length; i++) {
			for (int j = 0; j < matrizTemp[0].length; j++) {
				if (matrizTemp[i][j] != null) {
					if (isNaoTerminal(matrizTemp[i][j])) 
						result[i][j] = Integer.valueOf(((Integer)ConstantesNaoTerminais.NAOTERMINAIS.get(matrizTemp[i][j])).intValue());
					else if (isTerminal(matrizTemp[i][j]))
						result[i][j] = Integer.valueOf(((Integer)ConstantesTerminais.TERMINAIS.get(matrizTemp[i][j])).intValue());
					else if (isAcaoSemantica(matrizTemp[i][j])) {
						result[i][j] = Integer.valueOf(((Integer)ConstantesSemanticas.ACOES_SEMANTICAS.get(matrizTemp[i][j])).intValue());
					}
				}
			}
		}

		return result;
	}
	
	public void lergramatica() {
		System.out.println("LENDO GRAMATICA..............");
		for (int i = 0; i < 8; i++) {
			String aux = null;
			for (int j = 0; j < 5; j++) {
				aux += " " + String.valueOf(matrizParse[i][j]);
			}
			System.out.println(aux);
		}
	}

	private static boolean isTerminal(String topoDaPilha) {
		return (topoDaPilha != null) && (ConstantesTerminais.TERMINAIS.containsKey(topoDaPilha));
	}

	private static boolean isNaoTerminal(String topoDaPilha) {
		return (topoDaPilha != null) && (ConstantesNaoTerminais.NAOTERMINAIS.containsKey(topoDaPilha));
	}

	private static boolean isAcaoSemantica(String topoDaPilha) {
		return (topoDaPilha != null) && (ConstantesSemanticas.ACOES_SEMANTICAS.containsKey(topoDaPilha));
	}

	public boolean verificaTerminal(int cdSimbolo) {
		return ConstantesTerminais.TERMINAIS.containsValue(Integer.valueOf(cdSimbolo));
	}

	public boolean verificaNaoTerminal(int cdSimbolo) {
		return ConstantesNaoTerminais.NAOTERMINAIS.containsValue(Integer.valueOf(cdSimbolo));
	}
}