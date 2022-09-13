package projetoCampoMinado.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Tabuleiro implements CampoObservador{

	private final int linhas;
    private final int colunas;
    private final int minas;
	
    List <Campo> campos = new ArrayList<Campo>();
    
    
    //Consumer recebe 1 parâmetro e não retorna nada
    // Faz o papel de um "TabuleiroObservador"
    // Uma lista que notifica os observadores do Tabuleiro se o objetivo geral foi alcançado ou não
    private List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();
    
    
    
      public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

      public void registrarObs(Consumer<ResultadoEvento> observador) {
    	  observadores.add(observador);
    	  
      }
      
      
      
      
      public void notificarObs(boolean resultado) {
    	  observadores.stream().forEach(obs -> obs.accept(new ResultadoEvento(resultado)));
      }
      
      //método com a ideia inicial de ter acesso a todos os campos do tabuleiro
      //percorrer cada um dos campos e fazer alguma coisa
      public void acessoAosCampos(Consumer<Campo> funcao) {
    	  campos.forEach(funcao);
      }
      
	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.isMinado();

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();
		}while(minasArmadas < minas);
		
		
	}

	private void associarVizinhos() {
		for(Campo c1 : campos) {
			for(Campo c2 : campos) {
			c1.adicionarVizinhos(c2);	
			}
		}
	}

	private void gerarCampos() {
		for(int linha = 0; linha < linhas; linha++) {
			for(int coluna = 0; coluna < colunas; coluna++) {
				Campo campo = new Campo(linha, coluna);
				campo.addObservadorCampo(this);
				campos.add(campo);
			}
				
		}
		
	}
	
	public void abrir(int linha, int coluna) {
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst().ifPresent(c -> c.abrir());;
		}
    
	private void mostrarMinas() {
		
		campos.stream().filter(c -> c.isMinado()).filter(c -> !c.isMarcado()).forEach(c -> c.setAberto(true));
	}
	
	public void alternarMarcacao(int linha, int coluna) {
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
		.findFirst().ifPresent(c -> c.alternarMarcacao());;
	}
    

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
		
	}
	
	public void reiniciar(){
		campos.stream().forEach(c -> c.resetar());
		sortearMinas();
		
	}

	@Override
	public void eventoOcorreu(Campo campo, CampoEvento campoEvento) {
		if(campoEvento == CampoEvento.EXPLODIR) {
			mostrarMinas();
			notificarObs(false);
		}
		else if(objetivoAlcancado()) {
			notificarObs(true);
		}
		
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
		
	}
	
	

