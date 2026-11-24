package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import Model.Resposta;

public class RespostaDao {

    private Conexaobanco conexao;

    private static final DateTimeFormatter FMT_TELA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private static final String COLUNAS = "id_chamado=?, id_tecnico=?, texto=?";

    public RespostaDao() {
        this.conexao = new Conexaobanco();
    }

    public int inserir(Resposta r) {
        String sql = "INSERT INTO resposta SET " + COLUNAS;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection()
                        .prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

                preencher(sentenca, r);
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

    // Detalhe do chamado: histórico de atendimento em ordem cronológica
    public ArrayList<Resposta> listarPorChamado(int idChamado) {
        ArrayList<Resposta> lista = new ArrayList<>();
        String sql = "SELECT * FROM resposta WHERE id_chamado=? ORDER BY data_resposta";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idChamado);
                ResultSet rs = sentenca.executeQuery();

                while (rs.next()) {
                    Resposta r = new Resposta();
                    r.setIdResposta(rs.getInt("id_resposta"));
                    r.setIdChamado(rs.getInt("id_chamado"));
                    r.setIdTecnico(rs.getInt("id_tecnico"));
                    r.setTexto(rs.getString("texto"));
                    r.setDataResposta(rs.getTimestamp("data_resposta").toLocalDateTime().format(FMT_TELA));
                    lista.add(r);
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

    public int proximoId() {
        String sql = "SELECT COALESCE(MAX(id_resposta), 0) + 1 AS prox FROM resposta";

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

    private void preencher(PreparedStatement sentenca, Resposta r) throws SQLException {
        sentenca.setInt(1, r.getIdChamado());
        sentenca.setInt(2, r.getIdTecnico());
        sentenca.setString(3, r.getTexto());
    }
}