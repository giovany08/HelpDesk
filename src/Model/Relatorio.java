package Model;

public class Relatorio {

    private int idRelatorio;
    private int idAdministrador;
    private String conteudo;
    private String dataRelatorio;   // dd/MM/yyyy HH:mm

    public Relatorio() {
    }

    public Relatorio(int idAdministrador, String conteudo) {
        this.idAdministrador = idAdministrador;
        this.conteudo = conteudo;
    }

    public Relatorio(int idRelatorio, int idAdministrador, String conteudo, String dataRelatorio) {
        this.idRelatorio = idRelatorio;
        this.idAdministrador = idAdministrador;
        this.conteudo = conteudo;
        this.dataRelatorio = dataRelatorio;
    }

    public int getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(int idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataRelatorio() {
        return dataRelatorio;
    }

    public void setDataRelatorio(String dataRelatorio) {
        this.dataRelatorio = dataRelatorio;
    }

    @Override
    public String toString() {
        return "Relatório #" + idRelatorio + " - " + dataRelatorio;
    }
}