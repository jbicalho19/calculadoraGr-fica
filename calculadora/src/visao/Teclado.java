package visao;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import modelo.Memoria;


//Panel serve para organizar componentes 
// A grosso modo Frame é a janela e o Panel é a área onde os componentes são posicionados
@SuppressWarnings("serial")
public class Teclado extends JPanel implements ActionListener{

	private final Color CINZA_ESCURO = new Color(68,68,68);
	private final Color CINZA_CLARO = new Color(99,99,99);
	private final Color AMARELO = new Color(242, 163, 60);
	
	

	public Teclado() {
		
		GridBagConstraints c = new GridBagConstraints();
		
		//gerenciador de layout que permite personalizar tamanho das linhas e colunas em estilo de grade
		GridBagLayout layout = new GridBagLayout();
		
		setLayout(layout);
		
		//peso para vertical e horizontal
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		
		//quantidade de campos relativos preenchidos
		c.gridwidth = 2;
		addBotao("C", CINZA_ESCURO,c, 0,0 );
		c.gridwidth = 1;
		addBotao("<-", CINZA_ESCURO,c, 2,0 );
		addBotao("/", AMARELO,c, 3, 0);
		
		
		addBotao("7", CINZA_CLARO,c, 0, 1);
		addBotao("8", CINZA_CLARO,c, 1, 1);
		addBotao("9", CINZA_CLARO,c, 2, 1);
		addBotao("*", AMARELO,c, 3, 1);
		
		
		addBotao("4", CINZA_CLARO,c, 0, 2);
		addBotao("5", CINZA_CLARO,c, 1, 2);
		addBotao("6", CINZA_CLARO,c, 2, 2);
		addBotao("-", AMARELO,c, 3, 2);
		
		
		addBotao("1", CINZA_CLARO,c, 0, 3);
		addBotao("2", CINZA_CLARO,c, 1, 3);
		addBotao("3", CINZA_CLARO,c, 2, 3);
		addBotao("+", AMARELO,c, 3,3);
		
		
		c.gridwidth = 2;
		addBotao("0", CINZA_CLARO,c, 0, 4);
		c.gridwidth = 1;
		addBotao(",", CINZA_CLARO,c, 2, 4);
		addBotao("=", AMARELO,c, 3, 4);
	}
	
	private void addBotao (String texto, Color cor, GridBagConstraints c ,int linha, int coluna) {
		
		c.gridx = linha;
		c.gridy = coluna;
		Botao botao = new Botao(texto, cor);
		botao.addActionListener(this);
		add(botao,c);
	
	}


	//"Ação performada"
	//Para escutar os botões do teclado
	//getSouce() pega o objeto no qual o evento ocorreu
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton botao = (JButton) e.getSource();
			
			Memoria.getInstancia().processarComandoTeclado(botao.getText());
		}
		
	}
}
