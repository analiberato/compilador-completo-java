package tabelaSimbolo;

public class MainTabelaDeSimbolos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//INICIALIZA TABELA DE SIMBOLOS
		TabelaSimbolos tabela = new TabelaSimbolos();

		//INSERE NA TABELA DE SIMBOLOS
		tabela.inserirTabela("var1", "var", "0", "1", "1");
		tabela.inserirTabela("var2", "var", "0", "1", "1");
		tabela.inserirTabela("Var", "var", "0", "1", "1");
		tabela.inserirTabela("abc", "var", "0", "1", "1");
		tabela.inserirTabela("pq", "var", "0", "1", "1");
		tabela.inserirTabela("par", "var", "0", "1", "1");

		//BUSCA NA TABELA


		//DELETA DA TABELA


		//ATUALIZA


		//MOSTRA TABELA
		tabela.toString();

	}

}
