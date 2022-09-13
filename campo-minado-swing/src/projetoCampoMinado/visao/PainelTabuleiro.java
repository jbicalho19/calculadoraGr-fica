package projetoCampoMinado.visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import projetoCampoMinado.modelo.Tabuleiro;





//JPanel é um agrupador ou "container" de componentes. Neste caso de botões.
@SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {
public PainelTabuleiro (Tabuleiro tabuleiro) {
	

	
	
	//Definir como os componentes visuais serão dispostos na tela
	//Nesse caso em linhas e colunas
	setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
	
	
	//cada um dos campos vai ser associado a um JButton
	tabuleiro.acessoAosCampos(c -> add(new BotaoCampo(c)));
	
	
	
	
	
	//nesse método que eu recebo um evento que diz se ganhei ou perdi
			tabuleiro.registrarObs(e ->  {
				SwingUtilities.invokeLater(()->{
					
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "VOCÊ É FERA! DESCOBRIU TODAS AS MINAS");
				}
				else {
					JOptionPane.showMessageDialog(this, "DESSA VEZ NÃO DEU! GAME OVER :( ");
				}
	//vai garantir que o campo minado seja atualizado e só depois a mensagem apareça
	
				tabuleiro.reiniciar();
		
				}	);
	});



	
}
}
