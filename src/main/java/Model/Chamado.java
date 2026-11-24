package model;

public class Chamado {

    public static final String ABERTO = "ABERTO";
    public static final String EM_ATENDIMENTO = "EM ATENDIMENTO";
    public static final String ENCERRADO = "ENCERRADO";
    public static final String CANCELADO = "CANCELADO";
    public static final String ARQUIVADO = "ARQUIVADO";

    public static final String BAIXA = "BAIXA";
    public static final String MEDIA = "MEDIA";
    public static final String ALTA = "ALTA";

    private int idChamado;
    private int idFuncionario;
    private int idTecnico;          // 0 = ainda sem técnico
    private int idSetor;
    private String descricao;
    private String prioridade;
    private String status;
    private String dataAbertura;    // dd/MM/yyyy HH:mm
    private String dataFechamento;  // dd/MM/yyyy HH:mm ou vazio
    private String resolucao;

    public Chamado() {
    }

    // usado para abrir chamado
    public Chamado(int idFuncionario, int idSetor, String descricao, String prioridade) {
        this.idFuncionario = idFuncionario;
        this.idSetor = idSetor;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = ABERTO;
    }

    public Chamado(int idChamado, int idFuncionario, int idTecnico, int idSetor, String descricao,
                   String prioridade, String status, String dataAbertura,
                   String dataFechamento, String resolucao) {
        this.idChamado = idChamado;
        this.idFuncionario = idFuncionario;
        this.idTecnico = idTecnico;
        this.idSetor = idSetor;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.resolucao = resolucao;
    }

    public int getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(int idChamado) {
        this.idChamado = idChamado;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public int getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(int idTecnico) {
        this.idTecnico = idTecnico;
    }

    public int getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(int idSetor) {
        this.idSetor = idSetor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(String dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(String dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    @Override
    public String toString() {
        return "#" + idChamado + " - " + descricao;
    }
}