package model;

public class Usuario {

    public static final String FUNCIONARIO = "FUNCIONARIO";
    public static final String TECNICO = "TECNICO";
    public static final String ADMINISTRADOR = "ADMINISTRADOR";

    private int idUsuario;
    private int idSetor;
    private String nome;
    private String login;
    private String senhaHash;
    private String sexo;
    private String cargo;
    private String perfil;

    public Usuario() {
    }

    public Usuario(int idSetor, String nome, String login, String senhaHash,
                   String sexo, String cargo, String perfil) {
        this.idSetor = idSetor;
        this.nome = nome;
        this.login = login;
        this.senhaHash = senhaHash;
        this.sexo = sexo;
        this.cargo = cargo;
        this.perfil = perfil;
    }

    public Usuario(int idUsuario, int idSetor, String nome, String login, String senhaHash,
                   String sexo, String cargo, String perfil) {
        this.idUsuario = idUsuario;
        this.idSetor = idSetor;
        this.nome = nome;
        this.login = login;
        this.senhaHash = senhaHash;
        this.sexo = sexo;
        this.cargo = cargo;
        this.perfil = perfil;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return nome;
    }
}