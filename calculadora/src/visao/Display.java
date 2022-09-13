package visao;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;


import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.Memoria;
import modelo.ObservadorMemoria;

@SuppressWarnings("serial")
public class Display extends JPanel implements ObservadorMemoria {
	
	
	//Label é um "rótulo", um componente que exibe uma string ou um ícone
	private final JLabel label;
	
	
	public Display() {
		Memoria.getInstancia().addObservadores(this);
		
		
		setBackground(new Color(0, 0, 0));
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		label.setFont(new Font("courier", Font.PLAIN ,30));
		label.setForeground(Color.white);
		//gerenciador de layout
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));
		
		
		add(label);
	
	}


	@Override
	public void valorAlterado(String novoValor) {
		label.setText(novoValor);
		
		
	}
	
}
