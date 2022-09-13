package projetoCampoMinado.modelo;


//simula um BiConsumer: recebe dois tipos e não retorna nada
public interface CampoObservador {
public void eventoOcorreu(Campo campo, CampoEvento campoEvento);
}
