package ui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import lexico.Lexico;
import lexico.Token;
import semantico.Semantico;
import sintatico.Sintatico;

/**
 * Compilador LMS
 * Tradução de linguagens - 2017/2
 * @author Ana
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Main extends JFrame {
	private static final long serialVersionUID = 3367102647784384931L;
	private static Frame tela;
	private JScrollPane jScrollPanelTableTokens;
	private JScrollPane jScrollPanelTableCodigo;
	private JScrollPane jScrollPanelLog;
	private JTextArea jTextLog;
	private JTable jTableResultadoTokens;
	private JTable jTableResultadoCodigo;
	private JTable jTableResultadoLiterais;
	private JTextArea jTextArea;
	private JTabbedPane jTableLog;
	private JMenuItem menuItemAnaliseLexica;
	private JMenuItem menuItemAnaliseSintatica;
	private JMenuItem menuItemAnaliseSemantica;
	private JMenuItem menuItemExecutar;
	private JMenu jProjeto;
	private JSeparator jSeparator;
	private JMenuItem menuItemNovo;
	private JMenuItem menuItemAbrir;
	private JMenuItem menuItemSalvar;
	private JMenu jArquivo;
	private JMenuBar jMenuBar;
	private JMenuItem menuItemSair;
	private JTabbedPane jTableArea;
	private JTabbedPane jTableAbas;
	private JScrollPane jScrollPaneTextArea;
	JFileChooser jfileChooser;

	private boolean sucessoAnaliseLexica = false;
	private boolean sucessoAnaliseSintatica = false;
	private boolean sucessoAnaliseSemantica = false;

	private Sintatico analisadorSintatico;
	private Lexico analisadorLexico;
	private Semantico analisadorSemantico;

	private List<Token> tokens;
	private JScrollPane jScrollPanelTableLiterais;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main.tela = new Main();
				Main.tela.setLocationRelativeTo(null);
				Main.tela.setVisible(true);
			}
		});
	}

	private void criarInterfaceGrafica() {
		try {
			this.jTableArea = new JTabbedPane();
			getContentPane().add(this.jTableArea, "Center");
			this.jTableArea.setPreferredSize(new Dimension(156, 274));
			this.jScrollPaneTextArea = new JScrollPane();
			this.jTableArea.addTab("Código Fonte", this.jScrollPaneTextArea);
			this.jTextArea = new JTextArea();
			this.jTextArea.setLineWrap(true);
			this.jScrollPaneTextArea.setViewportView(this.jTextArea);
			this.jScrollPaneTextArea.setVerticalScrollBarPolicy(20);

			this.jTableLog = new JTabbedPane();
			getContentPane().add(this.jTableLog, "South");
			this.jTableLog.setPreferredSize(new Dimension(787, 105));
			this.jScrollPanelLog = new JScrollPane();
			this.jTableLog.addTab("Console", this.jScrollPanelLog);
			this.jTextLog = new JTextArea();
			this.jScrollPanelLog.setViewportView(this.jTextLog);
			this.jTextLog.setEditable(false);

			this.jTableAbas = new JTabbedPane();
			getContentPane().add(this.jTableAbas, "East");
			this.jTableAbas.setPreferredSize(new Dimension(428, 274));
			this.jScrollPanelTableTokens = new JScrollPane();
			this.jScrollPanelTableCodigo = new JScrollPane();
			this.jScrollPanelTableLiterais = new JScrollPane();
			this.jTableAbas.addTab("Tokens", this.jScrollPanelTableTokens);
			this.jTableAbas.addTab("Código Gerado", this.jScrollPanelTableCodigo);
			this.jTableAbas.addTab("Literais", this.jScrollPanelTableLiterais);
			this.jTableResultadoTokens = new JTable();
			this.jTableResultadoCodigo = new JTable();
			this.jTableResultadoLiterais = new JTable();
			this.jTableResultadoTokens.setModel(new DefaultTableModel(new Object[0][], new String[] { "Código", "Token", "Descrição" }));
			this.jTableResultadoCodigo.setModel(new DefaultTableModel(new Object[0][], new String[] { "Instrução", "Operador 1", "Operador 2" }));
			this.jTableResultadoLiterais.setModel(new DefaultTableModel(new Object[0][], new String[] { "Literal"}));
			defineRenderersTokens();
			this.jScrollPanelTableTokens.setViewportView(this.jTableResultadoTokens);
			this.jScrollPanelTableCodigo.setViewportView(this.jTableResultadoCodigo);
			this.jScrollPanelTableLiterais.setViewportView(this.jTableResultadoLiterais);




			setTitle("Compilador LMS - Ana Espindola");
			setSize(883, 440);
			this.jMenuBar = new JMenuBar();
			setJMenuBar(this.jMenuBar);
			this.jArquivo = new JMenu();
			this.jMenuBar.add(this.jArquivo);
			this.jArquivo.setText("Arquivo");
			this.menuItemNovo = new JMenuItem();
			this.menuItemNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
			this.jArquivo.add(this.menuItemNovo);
			this.menuItemNovo.setText("Novo");
			this.menuItemNovo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Main.this.jTextArea.setText("");
					Main.this.jTextLog.setText("");
					Main.this.jTableResultadoTokens.setModel(new DefaultTableModel(new Object[0][], new String[] { "Código", "Token", "Descrição" }));
					Main.this.jTableResultadoCodigo.setModel(new DefaultTableModel(new Object[0][], new String[] { "Instrução", "Operador 1", "Operador 2" }));
					Main.this.jTableResultadoLiterais.setModel(new DefaultTableModel(new Object[0][], new String[] { "Literal" }));
					Main.this.defineRenderersTokens();
				}
			});
			this.menuItemAbrir = new JMenuItem();
			this.menuItemAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
			this.jArquivo.add(this.menuItemAbrir);
			this.menuItemAbrir.setText("Abrir");
			this.menuItemAbrir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Main.this.jfileChooser = new JFileChooser("testes");
					Main.this.jfileChooser.setDialogTitle("Selecione um Arquivo");
					Main.this.escolherExtensao();
					int retorno = Main.this.jfileChooser.showOpenDialog(Main.tela);
					if (retorno == 0)
						try {
							@SuppressWarnings("resource")
							BufferedReader br = new BufferedReader(new FileReader(new File(Main.this.jfileChooser.getSelectedFile().getAbsolutePath())));
							String textoAbrir = "";
							String linha;
							while ((linha = br.readLine()) != null) {
								textoAbrir = textoAbrir + linha + "\n";
							}
							Main.this.jTextArea.setText(textoAbrir);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			});
			this.menuItemSalvar = new JMenuItem();
			this.menuItemSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
			this.jArquivo.add(this.menuItemSalvar);
			this.menuItemSalvar.setText("Salvar");
			this.menuItemSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Main.this.jfileChooser = new JFileChooser("testes");
					Main.this.escolherExtensao();
					int retorno = Main.this.jfileChooser.showSaveDialog(null);
					if (retorno == 0) {
						String caminho = Main.this.jfileChooser.getSelectedFile().getAbsolutePath();
						File file = new File(caminho + ".txt");
						try {
							PrintWriter pw = new PrintWriter(new FileWriter(file));
							pw.println(Main.this.jTextArea.getText());
							pw.close();
						} catch (IOException e1) {
							Main.this.jTextLog.setText(Main.this.jTextLog.getText() + "Erro ao salvar arquivo.");
						}
					}
				}
			});
			this.jSeparator = new JSeparator();
			this.jArquivo.add(this.jSeparator);
			this.menuItemSair = new JMenuItem();
			this.menuItemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
			this.jArquivo.add(this.menuItemSair);
			this.menuItemSair.setText("Sair");
			this.menuItemSair.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Main.tela.dispose();
				}
			});
			this.jProjeto = new JMenu();
			this.jMenuBar.add(this.jProjeto);
			this.jProjeto.setText("Projeto");

			this.menuItemAnaliseLexica = new JMenuItem();
			this.menuItemAnaliseSintatica = new JMenuItem();
			this.menuItemAnaliseSemantica = new JMenuItem();
			this.menuItemExecutar = new JMenuItem();

			this.jProjeto.add(this.menuItemAnaliseLexica);
			this.jProjeto.add(this.menuItemAnaliseSintatica);
			this.jProjeto.add(this.menuItemAnaliseSemantica);
			this.jProjeto.add(this.menuItemExecutar);

			this.menuItemAnaliseLexica.setText("Análise Léxica");
			this.menuItemAnaliseSintatica.setText("Análise Sintática");
			this.menuItemAnaliseSemantica.setText("Análise Semântica / Geração de Código");
			this.menuItemExecutar.setText("Executar");

			this.menuItemAnaliseLexica.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
			this.menuItemAnaliseLexica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					limpaDados();
					// Chamada pra análise léxica
					analiseLexica(jTextArea);
					jTableAbas.setSelectedIndex(0); // seleciona a aba de tokens
				}
			});

			this.menuItemAnaliseSintatica.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
			this.menuItemAnaliseSintatica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					limpaDados();
					// Chamda para análise sintática
					analiseSintatica();
					jTableAbas.setSelectedIndex(0); // seleciona a aba de tokens
				}
			});

			this.menuItemAnaliseSemantica.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
			this.menuItemAnaliseSemantica.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					limpaDados();
					analiseSemantica();
					jTableAbas.setSelectedIndex(1); // seleciona a aba de codigo
				}
			});

			this.menuItemExecutar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
			this.menuItemExecutar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					limpaDados();
					analiseSemantica();
					jTableAbas.setSelectedIndex(1); // seleciona a aba de codigo
					executar();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void escolherExtensao() {
		this.jfileChooser.setFileFilter(new FileFilter()
		{
			public boolean accept(File f) {
				return (f.getName().toLowerCase().endsWith(".txt")) || (f.isDirectory());
			}

			public String getDescription() {
				return "Arquivos de texto (.txt)";
			}
		});
	}

	private void defineRenderersTokens() {
		DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
		rendererCentro.setHorizontalAlignment(0);
		DefaultTableCellRenderer rendererDireita = new DefaultTableCellRenderer();
		rendererDireita.setHorizontalAlignment(4);
		DefaultTableCellRenderer rendererEsquerda = new DefaultTableCellRenderer();
		rendererEsquerda.setHorizontalAlignment(2);
		JTableHeader header = this.jTableResultadoTokens.getTableHeader();
		header.setPreferredSize(new Dimension(0, 20));
		TableColumnModel modeloDaColuna = this.jTableResultadoTokens.getColumnModel();
		modeloDaColuna.getColumn(0).setCellRenderer(rendererCentro);
		modeloDaColuna.getColumn(1).setCellRenderer(rendererCentro);
		modeloDaColuna.getColumn(2).setCellRenderer(rendererCentro);
		modeloDaColuna.getColumn(0).setMaxWidth(70);
		modeloDaColuna.getColumn(1).setMaxWidth(260);
		modeloDaColuna.getColumn(1).setMinWidth(175);
		modeloDaColuna.getColumn(2).setMaxWidth(165);
		modeloDaColuna.getColumn(2).setMinWidth(125);
	}

	private void analiseLexica(JTextArea jTextArea)	{
		if (jTextArea.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Favor informar o código fonte a ser analisado!", "Erro!", 0);
			jTextArea.requestFocus();
			return;
		}
		DefaultTableModel tableModel = (DefaultTableModel)this.jTableResultadoTokens.getModel();
		tableModel.setNumRows(0);

		analisadorLexico = new Lexico();

		//remove símbolos especiais
		String cadeia = jTextArea.getText().replaceAll("\n", " ")
										   .replaceAll("\r", " ")
										   .replaceAll("\t", " ");

		tokens = analisadorLexico.analisar(cadeia + "$");

		if (tokens == null) {
			JOptionPane.showMessageDialog(null, "Erro na análise léxica.");
			return;
		}

		for (Token token : tokens) {
			adicionarTabela(token.getCodigo(), token.getToken(), token.getTipo());
		}

		sucessoAnaliseLexica = true;
		jTextLog.setText("Análise Léxica executada com sucesso");
	}

	//EXECUTA A ANALISE SINTATICA
	private void analiseSintatica()	{
		analiseLexica(jTextArea);

		// se a análise léxica deu certo
		if (sucessoAnaliseLexica) {

			analisadorSintatico = new Sintatico();
			List suporte = new ArrayList();
			for (Token token : tokens) {
				if ((token.getCodigo() != 0) && (token.getCodigo() != 51)) {
					Token tok = new Token(token.getCodigo(), token.getToken(), token.getTipo());
					suporte.add(tok);
				}
			}

			analisadorSintatico.analisarInteiro(suporte);
			sucessoAnaliseSintatica = !analisadorSintatico.isErroSintatico();

			jTextLog.setText(analisadorSintatico.getMsgRetornoSintatico());

		}
	}

	//EXECUTA A ANALISE SEMANTICA
	private void analiseSemantica()	{

		analiseSintatica();

		//se a análise sintatica deu certo
		if (sucessoAnaliseSintatica) {

			analisadorSemantico = analisadorSintatico.getSemantico();

			if (!analisadorSemantico.getErro().isEmpty()) {
				System.out.println(analisadorSemantico.getTabelaSimbolos().toString());
				jTextLog.setText(analisadorSemantico.getErro());
				sucessoAnaliseSemantica = false;
			} else {
				System.out.println(analisadorSemantico.getTabelaSimbolos().toString());
				jTextLog.setText("Análise semântica executada com sucesso!");
				sucessoAnaliseSemantica = true;
			}


			this.jTableResultadoCodigo.setModel(analisadorSemantico.getInstrucoes().getModTab());

			DefaultTableCellRenderer rendererCentro = new DefaultTableCellRenderer();
			rendererCentro.setHorizontalAlignment(0);
			DefaultTableCellRenderer rendererDireita = new DefaultTableCellRenderer();
			rendererDireita.setHorizontalAlignment(4);
			DefaultTableCellRenderer rendererEsquerda = new DefaultTableCellRenderer();
			rendererEsquerda.setHorizontalAlignment(2);

			JTableHeader header = this.jTableResultadoCodigo.getTableHeader();
			header.setPreferredSize(new Dimension(0, 20));
			TableColumnModel modeloDaColuna = this.jTableResultadoCodigo.getColumnModel();

			modeloDaColuna.getColumn(0).setCellRenderer(rendererCentro);
			modeloDaColuna.getColumn(1).setCellRenderer(rendererCentro);
			modeloDaColuna.getColumn(2).setCellRenderer(rendererCentro);

			modeloDaColuna.getColumn(0).setMaxWidth(70);
			modeloDaColuna.getColumn(1).setMaxWidth(260);
			modeloDaColuna.getColumn(1).setMinWidth(175);
			modeloDaColuna.getColumn(2).setMaxWidth(165);
			modeloDaColuna.getColumn(2).setMinWidth(125);

			//System.out.println(analisadorSemantico.getAreaLiterais());
			this.jTableResultadoLiterais.setModel(analisadorSemantico.getAreaLiterais().getModTab());

			DefaultTableCellRenderer rendererCentroL = new DefaultTableCellRenderer();
			rendererCentroL.setHorizontalAlignment(0);

			JTableHeader headerL = this.jTableResultadoLiterais.getTableHeader();
			headerL.setPreferredSize(new Dimension(0, 20));
			TableColumnModel modeloDaColunaL = this.jTableResultadoLiterais.getColumnModel();

			modeloDaColunaL.getColumn(0).setCellRenderer(rendererCentroL);
		    

		} else {
			sucessoAnaliseSemantica = false;
		}



	}

	public void executar() {
		analiseSemantica();

		if (sucessoAnaliseSemantica) {
			analisadorSemantico.interpreta();
		}
	}

	public Main() {
		criarInterfaceGrafica();
	}

	public void adicionarTabela(int codigo, String token, String descricao) {
		DefaultTableModel dtm = (DefaultTableModel)this.jTableResultadoTokens.getModel();
		dtm.addRow(new Object[] { Integer.valueOf(codigo), token, descricao });
	}

	public void limpaDados() {
		jTextLog.setText("");
	}
}