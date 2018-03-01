package semantico;

import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Objeto para salvar os literais
 *
 */
public class Literais
{
	private JTable table;
	private DefaultTableModel abas;
	public String[] areaLiterais = new String[30];		//ARMAZENA OS LITERAIS
	public int literais;
	private int pt;

	//CONSTRUTOR LITERAIS, CRIA O JTABLE
	public Literais() {

		this.abas = new DefaultTableModel();
		this.abas.setColumnCount(1);
		this.abas.setRowCount(400);
		this.abas.setColumnIdentifiers(new String[] { "Literal"});

		this.table = new JTable();
		this.table.setModel(this.abas);
	}

	//INSERE LITERAL NA TABELA
	public void insereLiteral(String literal) {
		this.table.setValueAt((Object)literal, this.pt, 0);
		this.pt += 1;
	}

	@Override
	public String toString() {
		return "Literais [areaLiterais=" + Arrays.toString(areaLiterais) + ", literais=" + literais + "]";
	}

	//ENVIA A TABELA
	public DefaultTableModel getModTab() {
		return abas;
	}



}