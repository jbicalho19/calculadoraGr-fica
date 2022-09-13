package projetoCampoMinado.visao;

import javax.swing.JFrame;

import projetoCampoMinado.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame {

	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(20, 20, 40);
		
	    add(new PainelTabuleiro(tabuleiro));			
				
		setTitle("Campo Minado");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(690, 438);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}
}
