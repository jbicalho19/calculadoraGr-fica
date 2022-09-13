package projetoCampoMinado.modelo;

import java.util.ArrayList;
import java.util.List;



public class Campo {
	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();
    
	private List<CampoObservador> observadores = new ArrayList<>();
	//private List<BiConsumer<Campo, CampoEvento>> observadores2 = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	
	public void addObservadorCampo(CampoObservador campoObs) {
		observadores.add(campoObs);
	}
	
	//public void addObservadorCampo2(C) {
    //	observadores.add(campoObs);
    //}
	
	private void notificarObservadores(CampoEvento evento) {
		observadores.stream().forEach(obs -> obs.eventoOcorreu(this, evento));
	}
	
	
	

	    boolean adicionarVizinhos(Campo vizinho) {
		int distanciaLinha = Math.abs(linha - vizinho.linha);
		int distanciaColuna = Math.abs(coluna - vizinho.coluna);
		int distanciaTotal = distanciaLinha + distanciaColuna;

		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		if (diagonal && distanciaTotal == 2) {
			vizinhos.add(vizinho);
			return true;
		} else if (!diagonal && distanciaTotal == 1) {
			vizinhos.add(vizinho);
			return true;
		} else
			return false;

	}

	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	public boolean abrir() {
		if (!aberto && !marcado) {

			
			if (minado) {
				notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}
				
			
			setAberto(true);
 
		if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}
		return false;
	}

	public void alternarMarcacao() {
		if (!aberto) 
			marcado = !marcado;
		
		else
			return;
		
		if(marcado) {
			notificarObservadores(CampoEvento.MARCAR);
		}else {
			notificarObservadores(CampoEvento.DESMARCAR);
		}
	}

	void minar() {
		minado = true;
	}

	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !aberto;
	}

	public boolean isMinado() {
		return minado;
	}

	public boolean isMarcado() {
		return marcado;
	}

	//fazendo isso, a notificação vai chegar em BotaoCampo
	void resetar() {
		aberto = false;
		marcado = false;
		minado = false;
		notificarObservadores(CampoEvento.REINICIAR);
	}
	
	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}
	
	boolean objetivoAlcancado() {
		boolean obj1 = aberto && !minado;
		boolean obj2 = marcado && minado;
		return obj1 || obj2;
		
	}
	
	
	
	public void setAberto(boolean aberto) {
		this.aberto = aberto;
		
		if(aberto) {
			notificarObservadores(CampoEvento.ABRIR);
		}
	}

	

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	
	
}
