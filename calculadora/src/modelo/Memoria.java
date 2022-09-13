package modelo;

import java.util.ArrayList;
import java.util.List;

//Padrão singleton: a classe tem apenas uma instância, sendo ela em si mesma
public class Memoria {

	private enum TipoComando {
		NUM, DIV, MULT, SOM, SUB, IGUAL, VIR, LIMPAR, ZERAR
	};

	private TipoComando ultimaOperacao = null;
	private boolean substituir = false;
	private String textoAtual = "";
	private String textoBuffer = "";

	private static final Memoria instancia = new Memoria();

	public List<ObservadorMemoria> observadores = new ArrayList<>();

	private Memoria() {
	}

	public static Memoria getInstancia() {
		return instancia;
	}

	public void addObservadores(ObservadorMemoria obs) {
		observadores.add(obs);
	}

	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}

	
	// Aqui é passado o texto vinculado ao botão apertado
	public void processarComandoTeclado(String texto) {

		TipoComando tipoComando = detectarTipoComando(texto);

		if (tipoComando == null)
			return;

		else if (tipoComando == TipoComando.ZERAR) {
			textoAtual = "";
			textoBuffer = "";
			substituir = false;
			ultimaOperacao = null;
		}
		
		
		else if (tipoComando == TipoComando.LIMPAR) {
			if(textoAtual.length() > 0) {
			textoAtual = textoAtual.substring(0, textoAtual.length() - 1);
		}
		
			
		}

		else if (tipoComando == TipoComando.NUM || tipoComando == TipoComando.VIR) {
			textoAtual = substituir ? texto : textoAtual + texto;
			substituir = false;

		} else {
			
			substituir = true;
			textoAtual = obterResultado(); 
			textoBuffer = textoAtual; 
			ultimaOperacao = tipoComando; 
			
			
			
			}
			
		

		observadores.forEach(obs -> obs.valorAlterado(getTextoAtual()));

	}

	private String obterResultado() {
		
		if (ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}

		double valorAtual = Double.parseDouble(textoAtual.replace(",","."));
		double valorBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double valorResultado = 0;
		
		if(ultimaOperacao == TipoComando.SOM) {
			valorResultado = valorBuffer + valorAtual;
		}
		
		else if(ultimaOperacao == TipoComando.SUB) {
			valorResultado = valorBuffer - valorAtual;
		}
		
		else if(ultimaOperacao == TipoComando.DIV) {
			valorResultado = valorBuffer / valorAtual;
		}
		
		else if(ultimaOperacao == TipoComando.MULT) {
			valorResultado = valorBuffer * valorAtual;
		}
		

		String texto = Double.toString(valorResultado).replace(".", ",");
		boolean inteiro = texto.endsWith(",0");
		return inteiro ? texto.replace(",0", "") : texto;
	}

	private TipoComando detectarTipoComando(String texto) {
		if (textoAtual.isEmpty() && texto.equals("0"))
			return null;

		try {
			Float.parseFloat(texto);
			return TipoComando.NUM;

		} catch (NumberFormatException e) {

			if (texto.equals("/"))
				return TipoComando.DIV;

			else if (texto.equals("*"))
				return TipoComando.MULT;

			else if (texto.equals("="))
				return TipoComando.IGUAL;

			else if (texto.equals("C"))
				return TipoComando.ZERAR;

			else if (texto.equals("+"))
				return TipoComando.SOM;

			else if (texto.equals("-"))
				return TipoComando.SUB;

			else if (texto.equals(",") && !textoAtual.contains(","))
				return TipoComando.VIR;

			else if (texto.equals("<-"))
				return TipoComando.LIMPAR;
		}

		return null;
	}

}
