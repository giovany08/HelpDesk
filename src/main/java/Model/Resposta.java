package model;

public class Resposta {

    private int idResposta;
    private int idChamado;
    private int idTecnico;
    private String texto;
    private String dataResposta;    // dd/MM/yyyy HH:mm

    public Resposta() {
    }

    public Resposta(int idChamado, int idTecnico, String texto) {
        this.idChamado = idChamado;
        this.idTecnico = idTecnico;
        this.texto = texto;
    }

    public Resposta(int idResposta, int idChamado, int idTecnico, String texto, String dataResposta) {
        this.idResposta = idResposta;
        this.idChamado = idChamado;
        this.idTecnico = idTecnico;
        this.texto = texto;
        this.dataResposta = dataResposta;
    }

    public int getIdResposta() {
        return idResposta;
    }

    public void setIdResposta(int idResposta) {
        this.idResposta = idResposta;
    }

    public int getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(int idChamado) {
        this.idChamado = idChamado;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(String dataResposta) {
        this.dataResposta = dataResposta;
    }

    @Override
    public String toString() {
        return texto;
    }
}