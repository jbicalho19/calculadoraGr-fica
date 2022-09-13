package projetoCampoMinado.modelo;

public class ResultadoEvento {
private final Boolean ganhou;

public ResultadoEvento(boolean ganhou) {
this.ganhou = ganhou;
}

public boolean isGanhou() {
	return ganhou;
}
}
