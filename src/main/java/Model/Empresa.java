package model;

public class Empresa {

    private int idEmpresa;
    private String razaoSocial;
    private String cnpj;

    public Empresa() {
    }

    public Empresa(String razaoSocial, String cnpj) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

    public Empresa(int idEmpresa, String razaoSocial, String cnpj) {
        this.idEmpresa = idEmpresa;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return razaoSocial;
    }
}