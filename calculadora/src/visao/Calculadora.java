package visao;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Calculadora extends JFrame{

	public Calculadora(){
		
		organizarLayout();
		
		setLocationRelativeTo(null);
		setSize(338, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	
	private void organizarLayout() {
		//gerenciador de layout
		setLayout(new BorderLayout());
		
		Teclado teclado = new Teclado();
		add(teclado, BorderLayout.CENTER);
		
		Display display = new Display();
		display.setPreferredSize(new Dimension(338, 90));
		add(display, BorderLayout.NORTH);
		
	}


	public static void main(String[] args) {
		new Calculadora();
	}
	
}
