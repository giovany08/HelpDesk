package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import Model.Chamado;

public class ChamadoDao {

    private Conexaobanco conexao;

    private static final DateTimeFormatter FMT_TELA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    // data_abertura é preenchida pelo próprio banco (DEFAULT CURRENT_TIMESTAMP)
    private static final String COLUNAS =
            "id_funcionario=?, id_tecnico=?, id_setor=?, descricao=?, prioridade=?, status=?";

    public ChamadoDao() {
        this.conexao = new Conexaobanco();
    }

    // Abrir chamado
    public int inserir(Chamado c) {
        String sql = "INSERT INTO chamado SET " + COLUNAS;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection()
                        .prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

                preencher(sentenca, c);
                sentenca.execute();

                int novoId = 0;
                ResultSet rs = sentenca.getGeneratedKeys();
                if (rs.next()) novoId = rs.getInt(1);
                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
                return novoId;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return 0;
    }

    public void alterar(Chamado c) {
        String sql = "UPDATE chamado SET " + COLUNAS + ", resolucao=? WHERE id_chamado=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);

                preencher(sentenca, c);
                sentenca.setString(7, c.getResolucao());
                sentenca.setInt(8, c.getIdChamado());
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Chamado buscarPorId(int idChamado) {
        ArrayList<Chamado> lista = consultar("SELECT * FROM chamado WHERE id_chamado=?", idChamado);
        return lista.isEmpty() ? null : lista.get(0);
    }

    // ---------- listagens (tela Chamados) ----------

    public ArrayList<Chamado> listar() {
        return consultar("SELECT * FROM chamado ORDER BY data_abertura DESC", null);
    }

    public ArrayList<Chamado> listarPorFuncionario(int idFuncionario) {
        return consultar("SELECT * FROM chamado WHERE id_funcionario=? ORDER BY data_abertura DESC",
                idFuncionario);
    }

    public ArrayList<Chamado> listarPorTecnico(int idTecnico) {
        return consultar("SELECT * FROM chamado WHERE id_tecnico=? ORDER BY data_abertura DESC",
                idTecnico);
    }

    public ArrayList<Chamado> listarPorStatus(String status) {
        return consultar("SELECT * FROM chamado WHERE status=? ORDER BY data_abertura DESC", status);
    }

    // ---------- ações sobre o chamado ----------

    public void definirStatus(int idChamado, String status) {
        executar("UPDATE chamado SET status=? WHERE id_chamado=?", status, idChamado);
    }

    public void definirPrioridade(int idChamado, String prioridade) {
        executar("UPDATE chamado SET prioridade=? WHERE id_chamado=?", prioridade, idChamado);
    }

    public void reatribuirTecnico(int idChamado, int idTecnico) {
        executar("UPDATE chamado SET id_tecnico=? WHERE id_chamado=?", idTecnico, idChamado);
    }

    public void encerrar(int idChamado, String resolucao) {
        executar("UPDATE chamado SET status='ENCERRADO', resolucao=?, data_fechamento=NOW() "
                + "WHERE id_chamado=?", resolucao, idChamado);
    }

    public void cancelar(int idChamado) {
        definirStatus(idChamado, Chamado.CANCELADO);
    }

    public void arquivar(int idChamado) {
        definirStatus(idChamado, Chamado.ARQUIVADO);
    }

    // ---------- Dashboard ----------

    public Map<String, Integer> contarPorStatus() {
        Map<String, Integer> mapa = new LinkedHashMap<>();
        mapa.put(Chamado.ABERTO, 0);
        mapa.put(Chamado.EM_ATENDIMENTO, 0);
        mapa.put(Chamado.ENCERRADO, 0);
        mapa.put(Chamado.CANCELADO, 0);
        mapa.put(Chamado.ARQUIVADO, 0);

        String sql = "SELECT status, COUNT(*) AS total FROM chamado GROUP BY status";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                ResultSet rs = sentenca.executeQuery();

                while (rs.next()) {
                    mapa.put(rs.getString("status"), rs.getInt("total"));
                }
                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return mapa;
    }

    public int proximoId() {
        String sql = "SELECT COALESCE(MAX(id_chamado), 0) + 1 AS prox FROM chamado";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                ResultSet rs = sentenca.executeQuery();

                int prox = 1;
                if (rs.next()) prox = rs.getInt("prox");

                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
                return prox;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return 1;
    }

    // ---------- auxiliares ----------

    private void executar(String sql, Object... params) {
        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                for (int i = 0; i < params.length; i++) {
                    sentenca.setObject(i + 1, params[i]);
                }
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private ArrayList<Chamado> consultar(String sql, Object param) {
        ArrayList<Chamado> lista = new ArrayList<>();

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                if (param != null) {
                    sentenca.setObject(1, param);
                }
                ResultSet rs = sentenca.executeQuery();

                while (rs.next()) {
                    lista.add(mapear(rs));
                }
                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return lista;
    }

    private void preencher(PreparedStatement sentenca, Chamado c) throws SQLException {
        sentenca.setInt(1, c.getIdFuncionario());
        if (c.getIdTecnico() > 0) {
            sentenca.setInt(2, c.getIdTecnico());
        } else {
            sentenca.setNull(2, Types.INTEGER);
        }
        sentenca.setInt(3, c.getIdSetor());
        sentenca.setString(4, c.getDescricao());
        sentenca.setString(5, c.getPrioridade());
        sentenca.setString(6, c.getStatus());
    }

    private Chamado mapear(ResultSet rs) throws SQLException {
        Chamado c = new Chamado();
        c.setIdChamado(rs.getInt("id_chamado"));
        c.setIdFuncionario(rs.getInt("id_funcionario"));
        c.setIdTecnico(rs.getInt("id_tecnico"));
        c.setIdSetor(rs.getInt("id_setor"));
        c.setDescricao(rs.getString("descricao"));
        c.setPrioridade(rs.getString("prioridade"));
        c.setStatus(rs.getString("status"));
        c.setDataAbertura(formatar(rs.getTimestamp("data_abertura")));
        c.setDataFechamento(formatar(rs.getTimestamp("data_fechamento")));
        c.setResolucao(rs.getString("resolucao"));
        return c;
    }

    private String formatar(Timestamp ts) {
        return ts == null ? "" : ts.toLocalDateTime().format(FMT_TELA);
    }
}