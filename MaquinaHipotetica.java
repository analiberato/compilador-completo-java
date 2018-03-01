package semantico;

import javax.swing.JOptionPane;

/**
 * área de instruções da máquina hipotética
 *
 */
class MaquinaHipotetica
{
	public static int MaxInst = 1000;
	public static int MaxList = 30;
	public static int b;
	public static int topo;
	public static int p;
	public static int l;
	public static int a;
	public static int nv;
	public static int np;
	public static int operador;
	public static int k;
	public static int i;
	public static int num_impr;
	public static int[] S = new int[1000];

	//CONSTRUTOR
	MaquinaHipotetica()
	{
		nv = MaquinaHipotetica.np = MaquinaHipotetica.num_impr = 0;
	}

	//INICIALIZA A AREA DE INSTRUÇÕES(CODIGO DA INSTRUÇÃO + OP1 + OP2)
	public static void inicializarInstrucoes(Instrucoes areaInstrucoes)
	{
		for (int i = 0; i < MaxInst; i++) {
			areaInstrucoes.areaInstrucoes[i].codigo = -1;
			areaInstrucoes.areaInstrucoes[i].op1 = -1;
			areaInstrucoes.areaInstrucoes[i].op2 = -1;
		}
		areaInstrucoes.LC = 0;
	}

	//INICIALIZA A AREA DE LITERAIS
	public static void inicicalizaLiterais(Literais AL)
	{
		for (int i = 0; i < MaxList; i++) {
			AL.areaLiterais[i] = "";
			AL.literais = 0;
		}
	}

	//INCLUI UMA INSTRUÇÃO NA AREA DE INSTRUÇÕES
	public boolean incluir(Instrucoes AI, int c, int o1, int o2)
	{
		boolean aux;
		if (AI.LC >= MaxInst) {
			aux = false;
		} else {
			aux = true;
			AI.areaInstrucoes[AI.LC].codigo = c;

			if (o1 != -1) {
				AI.areaInstrucoes[AI.LC].op1 = o1;
			}

			if (c == 24) {
				AI.areaInstrucoes[AI.LC].op2 = o2;
			}

			if (o2 != -1) {
				AI.areaInstrucoes[AI.LC].op2 = o2;
			}

			AI.LC += 1;
		}
		return aux;
	}

	//ALTERA UMA INSTRUÇÃO NA AREA DE INSTRUÇÕES
	public static void alterar(Instrucoes AI, int s, int o1, int o2)
	{
		if (o1 != -1) {
			AI.areaInstrucoes[s].op1 = o1;
		}

		if (o2 != -1)
			AI.areaInstrucoes[s].op2 = o2;
	}

	//INCLUI UM LITERAL NA AREA DE LITERAS
	public static boolean incluirAreaLiterais(Literais AL, String literal)
	{
		boolean aux;		//verifica se literal foi incluido na area de literais
		if (AL.literais >= MaxList) {	//verifica se lista de literais chegou ao maximo
			aux = false;
		} else {	// se não adiciona literal a area e literais
			aux = true;
			AL.areaLiterais[AL.literais] = literal;
			AL.literais += 1;
		}
		return aux;
	}

	//indica endereço do segmento de dados (base)
	public static int baseLiteral()
	{
		int b1 = b;
		while (l > 0) {
			b1 = S[b1];
			l -= 1;
		}
		return b1;
	}

	//analisa e executa o procedimento referente a cada uma das Instruções
	public static void interpretar(Instrucoes AI, Literais AL)
	{
		topo = 0;
		b = 0;
		p = 0;
		S[1] = 0;
		S[2] = 0;
		S[3] = 0;
		operador = 0;

		while (operador != 26)
		{
			operador = AI.areaInstrucoes[p].codigo;

			l = AI.areaInstrucoes[p].op1;
			a = AI.areaInstrucoes[p].op2;
			p += 1;

			switch (operador) {
			case 1:	//RETU
				p = S[(b + 2)];
				topo = b - a;
				b = S[(b + 1)];
				break;
			case 2: //CRVL
				topo += 1;
				S[topo] = S[(baseLiteral() + a)];
				break;
			case 3:	//CRCT
				topo += 1;
				S[topo] = a;
				break;
			case 4:	//ARMZ
				S[(baseLiteral() + a)] = S[topo];
				topo -= 1;
				break;
			case 5:	//SOMA
				S[(topo - 1)] += S[topo];
				topo -= 1;
				break;
			case 6:	//SUBT
				S[(topo - 1)] -= S[topo];
				topo -= 1;
				break;
			case 7:	//MULT
				S[(topo - 1)] *= S[topo];
				topo -= 1;
				break;
			case 8:	//DIVI
				if (S[topo] == 0) {
					JOptionPane.showMessageDialog(null, "Divisão por zero.", "Erro durante a execução", 0);
				} else {
					S[(topo - 1)] /= S[topo];
					topo -= 1;
				}
				break;
			case 9:	//INVR
				S[topo] = (-S[topo]);
				break;
			case 10:	//NEGA
				S[topo] = (1 - S[topo]);
				break;
			case 11:	//CONJ
				if ((S[(topo - 1)] == 1) && (S[topo] == 1)) {
					S[(topo - 1)] = 1;
				} else {
					S[(topo - 1)] = 0;
					topo -= 1;
				}
				break;
			case 12:	//DISJ
				if ((S[(topo - 1)] == 1) || (S[topo] == 1)) {
					S[(topo - 1)] = 1;
				} else {
					S[(topo - 1)] = 0;
					topo -= 1;
				}
				break;
			case 13:	//CMME
				if (S[(topo - 1)] < S[topo])
					S[(topo - 1)] = 1;
				else {
					S[(topo - 1)] = 0;
				}
				topo -= 1;
				break;
			case 14:	//CMMA
				if (S[(topo - 1)] > S[topo])
					S[(topo - 1)] = 1;
				else {
					S[(topo - 1)] = 0;
				}
				topo -= 1;
				break;
			case 15:	//CMIG
				if (S[(topo - 1)] == S[topo])
					S[(topo - 1)] = 1;
				else {
					S[(topo - 1)] = 0;
				}
				topo -= 1;
				break;
			case 16:	//CMDF
				if (S[(topo - 1)] != S[topo])
					S[(topo - 1)] = 1;
				else {
					S[(topo - 1)] = 0;
				}
				topo -= 1;
				break;
			case 17:	//CMEI
				if (S[(topo - 1)] <= S[topo])
					S[(topo - 1)] = 1;
				else {
					S[(topo - 1)] = 0;
				}
				topo -= 1;
				break;
			case 18:	//CMAI
				if (S[(topo - 1)] >= S[topo])
					S[(topo - 1)] = 1;
				else {
					S[(topo - 1)] = 0;
				}
				topo -= 1;
				break;
			case 19:	//DSVS
				p = a;
				break;
			case 20:	//DSVF
				if (S[topo] == 0) {
					p = a;
				}

				topo -= 1;

				break;
			case 21:	//LEIT
				topo += 1;
				String leitura = JOptionPane.showInputDialog(null, "Informe o valor:", "Leitura", 3);

				S[topo] = Integer.parseInt(leitura);
				break;
			case 22:	//IMPR
				JOptionPane.showMessageDialog(null, S[topo], "Informação",  1);

				topo -= 1;
				break;
			case 23:	//IMPRL
				if (a >= AL.literais)
					JOptionPane.showMessageDialog(null,
							"Literal não encontrado na área dos literais.",
							"Erro durante a execução",
							0);
				else {
					JOptionPane.showMessageDialog(null, AL.areaLiterais[a],
							"Informação", 1);
				}
				break;
			case 24:	//AMEM
				topo += a;
				break;
			case 25:	//CALL
				S[(topo + 1)] = baseLiteral();
				S[(topo + 2)] = b;
				S[(topo + 3)] = p;
				b = topo + 1;
				p = a;
				break;
			case 26:	//PARA
				break;
			case 27:	//NADA
				break;
			case 28:	//COPI
				topo += 1;
				S[topo] = S[(topo - 1)];
				break;
			case 29:	//DSVT
				if (S[topo] == 1) {
					p = a;
				}

				topo -= 1;
			}
		}
	}
}