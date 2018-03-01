package tabelaSimbolo;

/**
 * Tabela de simbolos para o analisador semântico
 *
 */
public class TabelaSimbolos {
	private String[][] tabela;	//ARMAZENA A TABELA
	private int qtdLinhas;	//ARMAZENA A QUANTIDADE DE LINHAS USADAS NA TABELA
	private int nivelMax;	//ARMAZENA O MAIOR NÍVEL QUE EXISTE NA TABELA

	//INICIALIZA TABELA DE SIMBOLOS
	public TabelaSimbolos() {
		this.nivelMax = 0;
		this.qtdLinhas = 0;
		this.tabela = new String[2000][5];
	}

	//BUSCA ITEM NA TABELA PELO NOME
	public int buscar(String nome) {
		for (int i = this.qtdLinhas - 1; i >= 0; i--) {
			if ((this.tabela[i][0] != null) &&
					(nome.equals(this.tabela[i][0]))) {
				return i;
			}

		}

		return -1;
	}

	//INSERE ITEM NA TABELA
	public boolean inserirTabela(String nome, String categoria, String nivel, String aux, String aux2) {
		int index = buscar(nome);

		if (index == -1) {
			this.tabela[this.qtdLinhas][0] = nome;
			this.tabela[this.qtdLinhas][1] = categoria;
			this.tabela[this.qtdLinhas][2] = nivel;
			this.tabela[this.qtdLinhas][3] = aux;
			this.tabela[this.qtdLinhas][4] = aux2;
			this.qtdLinhas += 1;

			int n = Integer.parseInt(nivel);
			if (n > this.nivelMax)
				this.nivelMax = n;
		} else {
			if (this.tabela[index][2].compareTo(nivel) == 0) {
				return false;
			}
			this.tabela[this.qtdLinhas][0] = nome;
			this.tabela[this.qtdLinhas][1] = categoria;
			this.tabela[this.qtdLinhas][2] = nivel;
			this.tabela[this.qtdLinhas][3] = aux;
			this.tabela[this.qtdLinhas][4] = aux2;
			this.qtdLinhas += 1;
		}

		return true;
	}

	//ALTERA TABELA ITEM DA TABELA
	public void alterar(int indice, String nome, String categoria, String nivel, String aux, String aux2) {
		this.tabela[indice][0] = nome;
		this.tabela[indice][1] = categoria;
		this.tabela[indice][2] = nivel;
		this.tabela[indice][3] = aux;
		this.tabela[indice][4] = aux2;

		int n = Integer.parseInt(nivel);
		if (n > this.nivelMax)
			this.nivelMax = n;
	}

	//REMOVE ITEM DA TABELA
	public void remover() {
		int nivelTab = 0;

		for (int i = this.qtdLinhas - 1; i >= 0; i--)
			if (this.tabela[i][0] != null) {
				nivelTab = Integer.parseInt(this.tabela[i][2]);
				if ((nivelTab == this.nivelMax) && (nivelTab != 0)) {
					this.tabela[i][0] = null;
					this.tabela[i][1] = null;
					this.tabela[i][2] = null;
					this.tabela[i][3] = null;
					this.tabela[i][4] = null;
				}
			}
	}

	public int getPt_linhas() {
		return this.qtdLinhas;
	}

	public void setPt_linhas(int pt_linhas) {
		this.qtdLinhas = pt_linhas;
	}

	public String[][] getTabela() {
		return this.tabela;
	}

	public void setTabela(String[][] tabela) {
		this.tabela = tabela;
	}

	public String toString() {
		String aux = "TABELA DE SÍMBOLOS: \n";

		for (int i = 0; i < this.tabela.length; i++) {
			if (this.tabela[i][0] == null) break;
			for (int j = 0; j < 5; j++) {
				if(j == 2)
					aux = aux + "nivel";
				if(j == 3)
					aux = aux + "geralA";
				if(j == 4)
					aux = aux + "geralB";
				aux = aux + this.tabela[i][j] + " ";
			}
			aux = aux + "\n";
		}

		return aux;
	}
}