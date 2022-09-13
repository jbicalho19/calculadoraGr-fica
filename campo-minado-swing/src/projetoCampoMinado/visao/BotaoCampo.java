package projetoCampoMinado.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import projetoCampoMinado.modelo.Campo;
import projetoCampoMinado.modelo.CampoEvento;
import projetoCampoMinado.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener{

	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCADO = new Color(200, 200, 50);
	private final Color BG_ABERTO = new Color(0, 100, 0);
	private final Color BG_EXPLOSAO = new Color(216, 216, 191);
	
	

	private Campo campo;
	
	public BotaoCampo(Campo campo) {
		this.campo = campo;
		setBackground(BG_PADRAO);
		
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));
		
		campo.addObservadorCampo(this);
		addMouseListener(this);
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento campoEvento) {
		switch (campoEvento) {
		case ABRIR: 
			alterarVisualAbrir();
			break;
		case EXPLODIR:
			alterarVisualExplodir();
			break;
		case MARCAR:
			alterarVisualMarcar();
			break;
		default:
			alterarVisualPadrao();
		
		}
		
	// mantém o botão 100% atualizado	
	SwingUtilities.invokeLater(() -> {
		repaint();
		validate();
	});
}

	private void alterarVisualAbrir() {
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		if(campo.isMinado()) {
			setBackground(BG_EXPLOSAO);
			setForeground(Color.RED);
			setText("X");
			//alterarVisualExplodir();
			return;
		}
		
		setBackground(BG_PADRAO);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
	
		switch (campo.minasNaVizinhanca()) {
		case 1:
			setForeground(BG_ABERTO);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.PINK);
			break;
			
		default:
			setForeground(Color.RED);
			
		
		}
		
		String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
		
		setText(valor);
		
	}
	
private void alterarVisualExplodir() {
		setBackground(BG_EXPLOSAO);
		setForeground(Color.RED);
		setText("-X-");
	}

private void alterarVisualMarcar() {
	setBackground(BG_MARCADO);
	setText("!");
}


private void alterarVisualPadrao() {
	setBorder(BorderFactory.createBevelBorder(0));
	setBackground(BG_PADRAO);
	setText("");
}



//Interface dos eventos do mouse
//O click do mouse gera a notificação do BotaoCampo
//Isso dispara a abertura de um campo
//Após a abertura do campo são feitos testes para saber qual evento foi disparado
public void mouseClicked(MouseEvent e) {
	if(e.getButton() == 1) {
		campo.abrir();
	}else {
		campo.alternarMarcacao();
	}
	
	
}

public void mousePressed(MouseEvent e) {}
public void mouseReleased(MouseEvent e) {}
public void mouseEntered(MouseEvent e) {}
public void mouseExited(MouseEvent e) {}
	
}


//O click do mouse -> Evento em campo -> BotaoCampo noticado ->