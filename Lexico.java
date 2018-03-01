package lexico;

import java.util.LinkedList;
import java.util.List;

import util.ConstantesPalavrasReservadas;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Lexico {

	/**
	 * verifica cada token que foi colocado pelo usuário
	 * verificando palavras reservadas, sinais, inteiros, literais, identificadores
	 * para que cada um se enquadre nas suas respectivas regras
	 * como exemplo um inteiro ser um numero menor que 32767
	 */

	public int linha;

	public List<Token> analisar(String sequencia) {
		this.linha = 0;
		List returnList = new LinkedList();

		int estadoAtual = 0;
		StringBuilder sequenciaAtual = null;

		int sequenciaLength = sequencia.length();
		for (int i = 0; i < sequenciaLength; i++)
		{
			char charAtual = sequencia.charAt(i);

			if (charAtual == '\n') {
				this.linha += 1;
			}

			switch (estadoAtual)
			{
			case 0:
				sequenciaAtual = new StringBuilder();
				estadoAtual = estadoInicial(charAtual);

				break;
			case 1:
				estadoAtual = estado1(charAtual);

				if (estadoAtual == 0)
				{
					if (ConstantesPalavrasReservadas.PALAVRAS_RESERVADAS.containsKey(sequenciaAtual.toString().toUpperCase()))
					{
						returnList.add(new Token(((Integer)ConstantesPalavrasReservadas.PALAVRAS_RESERVADAS.get(sequenciaAtual.toString().toUpperCase())).intValue(), sequenciaAtual.toString(), "Palavra-reservada"));
					}
					else if (sequenciaAtual.toString().length() <= 30)
						returnList.add(new Token(25, sequenciaAtual.toString(), "ID"));
					else {
						returnList.add(new Token(0, sequenciaAtual.toString(), "Um Identificador não pode conter mais de 30 caracteres! Linha: " + this.linha));
					}

					i--;
					decrementaLinha(charAtual);
				} else if (estadoAtual < 0) {
					returnList.add(new Token(0, sequenciaAtual.toString(), "Caractere não reconhecido! Linha: " + this.linha));
					return returnList;
				}

				break;
			case 2:
				estadoAtual = estado2(charAtual);
				if (estadoAtual < 0) {
					returnList.add(new Token(0, sequenciaAtual.toString(), "Um identificador deve terminar com letra ou numero! Linha: " + this.linha));
					return returnList;
				}

				break;
			case 3:
				estadoAtual = estado3(charAtual);
				break;
			case 4:
				estadoAtual = estado4(charAtual);

				if (estadoAtual == 0)
				{
					if (sequenciaAtual.toString().length() < 257)
						returnList.add(new Token(48, sequenciaAtual.toString(), "LITERAL"));
					else {
						returnList.add(new Token(0, sequenciaAtual.toString(), "ILEGAL, valor fora da escala! Linha:" + this.linha));
					}

					i--;
					decrementaLinha(charAtual);
				}
				break;
			case 5:
				estadoAtual = estado5(charAtual);

				if (estadoAtual == 0)
				{
					returnList.add(new Token(43, sequenciaAtual.toString(), "Sinal-de-Menor"));
					i--;
					decrementaLinha(charAtual);
				}

				break;
			case 6:
				estadoAtual = estado6(charAtual);

				if (estadoAtual == 0)
				{
					returnList.add(new Token(41, sequenciaAtual.toString(), "Sinal-de-Maior"));
					i--;
					decrementaLinha(charAtual);
				}
				break;
			case 7:
				estadoAtual = estado7(charAtual);

				if (estadoAtual == 0)
				{
					if (sequenciaAtual.toString().equals("-"))
					{
						returnList.add(new Token(31, sequenciaAtual.toString(), "Sinal-de-Subtração"));
					}
					else if (sequenciaAtual.toString().equals("="))
					{
						returnList.add(new Token(40, sequenciaAtual.toString(), "Sinal-de-Igualdade"));
					}
					else if (sequenciaAtual.toString().equals("<>"))
					{
						returnList.add(new Token(45, sequenciaAtual.toString(), "Sinal-de-Diferente"));
					}
					else if (sequenciaAtual.toString().equals(">="))
					{
						returnList.add(new Token(42, sequenciaAtual.toString(), "Sinal-de-Maior-igual"));
					}
					else if (sequenciaAtual.toString().equals("<="))
					{
						returnList.add(new Token(44, sequenciaAtual.toString(), "Sinal-de-Menor-igual"));
					}
					else if (sequenciaAtual.toString().equals("+"))
					{
						returnList.add(new Token(30, sequenciaAtual.toString(), "Sinal-de-Adição"));
					}
					else if (sequenciaAtual.toString().equals("*"))
					{
						returnList.add(new Token(32, sequenciaAtual.toString(), "Sinal-de-Multiplicação"));
					}
					else if (sequenciaAtual.toString().equals(")"))
					{
						returnList.add(new Token(37, sequenciaAtual.toString(), "Fechamento-de-parênteses"));
					}
					else if (sequenciaAtual.toString().equals("["))
					{
						returnList.add(new Token(34, sequenciaAtual.toString(), "Abertura-de-colchetes"));
					}
					else if (sequenciaAtual.toString().equals("]"))
					{
						returnList.add(new Token(35, sequenciaAtual.toString(), "Fechamento-de-colchetes"));
					}
					else if (sequenciaAtual.toString().equals(","))
					{
						returnList.add(new Token(46, sequenciaAtual.toString(), "Vírgula"));
					}
					else if (sequenciaAtual.toString().equals(";"))
					{
						returnList.add(new Token(47, sequenciaAtual.toString(), "Ponto-e-vírgula"));
					}
					else if (sequenciaAtual.toString().equals(".."))
					{
						returnList.add(new Token(50, sequenciaAtual.toString(), "Ponto-ponto"));
					}
					else if (sequenciaAtual.toString().equals(":="))
					{
						returnList.add(new Token(38, sequenciaAtual.toString(), "Sinal-de-atribuição"));
					}
					else if (sequenciaAtual.toString().equals("$"))
					{
						returnList.add(new Token(51, sequenciaAtual.toString(), "Fim-de-Arquivo"));
						System.err.println("[WARNING] Ignorou Token Inválido! Linha:" + this.linha);

						i = sequenciaLength;
						break;
					}

					i--;
					decrementaLinha(charAtual);
				}
				break;
			case 8:
				estadoAtual = estado8(charAtual);

				if (estadoAtual == 0)
				{
					returnList.add(new Token(49, sequenciaAtual.toString(), "Ponto-final"));
					i--;
					decrementaLinha(charAtual);
				}
				break;
			case 9:
				estadoAtual = estado9(charAtual);

				if (estadoAtual == 0)
				{
					returnList.add(new Token(39, sequenciaAtual.toString(), "Dois-pontos"));
					i--;
					decrementaLinha(charAtual);
				}
				break;
			case 10:
				estadoAtual = estadi15(charAtual);

				if (estadoAtual == 0)
				{
					returnList.add(new Token(33, sequenciaAtual.toString(), "Sinal-de-divisão"));
					i--;
					decrementaLinha(charAtual);
				}
				break;
			case 11:
				estadoAtual = estado11(charAtual);
				if (estadoAtual < 0) {
					returnList.add(new Token(0, sequenciaAtual.toString().replaceAll("\n", "/n").replaceAll("\t", "/t"), "Um identificador deve terminar com letra ou numero! Linha: " + this.linha));
					return returnList;
				}

				break;
			case 12:
				estadoAtual = estado12(charAtual);
				break;
			case 13:
				estadoAtual = estado13(charAtual);

				if (estadoAtual == 0)
				{
					i--;
					decrementaLinha(charAtual);
				}

				break;
			case 14:
				estadoAtual = estado14(charAtual);

				if (estadoAtual <= 0)
				{
					if (estadoAtual == 0)
						i--;
					try
					{
						int numInteiro = Integer.parseInt(sequenciaAtual.toString());
						if ((numInteiro > 32767) || (numInteiro < -32767))
							returnList.add(new Token(0, sequenciaAtual.toString(), "ILEGAL, valor fora da escala! Linha: " + this.linha));
						else
							returnList.add(new Token(26, numInteiro+"", "INTEIRO"));
					}
					catch (Exception e)
					{
						returnList.add(new Token(0, sequenciaAtual.toString(), "ILEGAL, não aceita ponto decimal nem outros caracteres! Linha: " + this.linha));
					}

				}

				break;
			case 15:
				estadoAtual = estado10(charAtual);

				if (estadoAtual == 0)
				{
					returnList.add(new Token(36, sequenciaAtual.toString(), "Abertura-de-Parenteses"));
					i--;
					decrementaLinha(charAtual);
				}
				break;
			case -1:
			}

			if (estadoAtual >= 0)
			{
				sequenciaAtual.append(charAtual);
			} else {
				returnList.add(new Token(0, charAtual+"", "[WARNING] Ignorou Token Inválido! Linha: " + this.linha));
				estadoAtual = 0;
			}

		}

		return returnList;
	}

	private int estadoInicial(char c)
	{
		if (isLetra(c))
		{
			return 1;
		}
		if (c == '_')
		{
			return 2;
		}
		if (c == '"')
		{
			return 3;
		}
		if (c == '<')
		{
			return 5;
		}
		if (c == '>')
		{
			return 6;
		}
		if ((c == '-') || (c == '=') || (c == '+') || (c == '*') ||
				(c == ')') || (c == '[') || (c == ']') || (c == ',') || (c == ';') || (c == '$'))
		{
			return 7;
		}
		if (c == '.')
		{
			return 8;
		}
		if (c == ':')
		{
			return 9;
		}
		if (c == '/')
		{
			return 10;
		}
		if (c == '(')
		{
			return 15;
		}
		if (isNumero(c))
		{
			return 14;
		}if (c == ' ')
			return 0;
		if (c == '\n') {
			return 0;
		}

		return -1;
	}

	private int estado1(char c)
	{
		if ((isLetra(c)) || (isNumero(c)))
		{
			return 1;
		}

		return 0;
	}

	private int estado2(char c)
	{
		if ((isLetra(c)) || (isNumero(c)))
		{
			return 1;
		}

		return -1;
	}

	private int estado3(char c)
	{
		if (c == '"')
		{
			return 4;
		}

		return 3;
	}

	private int estado4(char c)
	{
		return 0;
	}

	private int estado5(char c)
	{
		if ((c == '=') || (c == '>'))
		{
			return 7;
		}

		return 0;
	}

	private int estado6(char c)
	{
		if (c == '=')
		{
			return 7;
		}

		return 0;
	}

	private int estado7(char c)
	{
		return 0;
	}

	private int estado8(char c)
	{
		if (c == '.')
		{
			return 7;
		}

		return 0;
	}

	private int estado9(char c)
	{
		if (c == '=')
		{
			return 7;
		}

		return 0;
	}

	private int estado10(char c)
	{
		if (c == '*')
		{
			return 11;
		}

		return 0;
	}

	private int estado11(char c)
	{
		if (c == '*')
		{
			return 12;
		}

		return 11;
	}

	private int estado12(char c)
	{
		if (c == '*')
		{
			return 12;
		}
		if (c == ')')
		{
			return 13;
		}
		if (c == '$')
		{
			return -1;
		}

		return 11;
	}

	private int estado13(char c)
	{
		return 0;
	}

	private int estado14(char c)
	{
		if (isNumero(c))
		{
			return 14;
		}

		if (estadoInicial(c) < 0) {
			return -1;
		}

		return 0;
	}

	private int estadi15(char c)
	{
		return 0;
	}

	private boolean isNumero(char c)
	{
		String cc = c+"";
		return cc.matches("[0-9]");
	}

	private boolean isLetra(char c)
	{
		String cc = c+"";
		return cc.matches("[a-zA-Z]");
	}

	private void decrementaLinha(char c) {
		if (c == '\n')
			this.linha -= 1;
	}
}