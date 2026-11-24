package Model;

public class Setor {

    private int idSetor;
    private int idEmpresa;
    private String nome;

    public Setor() {
    }

    public Setor(int idEmpresa, String nome) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
    }

    public Setor(int idSetor, int idEmpresa, String nome) {
        this.idSetor = idSetor;
        this.idEmpresa = idEmpresa;
        this.nome = nome;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}