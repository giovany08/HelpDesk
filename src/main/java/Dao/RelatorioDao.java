package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import model.Relatorio;

public class RelatorioDao {

    private Conexaobanco conexao;

    private static final DateTimeFormatter FMT_TELA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    private static final String COLUNAS = "id_administrador=?, conteudo=?";

    public RelatorioDao() {
        this.conexao = new Conexaobanco();
    }

    public int inserir(Relatorio r) {
        String sql = "INSERT INTO relatorio SET " + COLUNAS;

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

    public Relatorio buscarPorId(int idRelatorio) {
        String sql = "SELECT * FROM relatorio WHERE id_relatorio=?";
        Relatorio r = null;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idRelatorio);
                ResultSet rs = sentenca.executeQuery();

                if (rs.next()) r = mapear(rs);

                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return r;
    }

    public ArrayList<Relatorio> listar() {
        ArrayList<Relatorio> lista = new ArrayList<>();
        String sql = "SELECT * FROM relatorio ORDER BY data_relatorio DESC";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
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

    public int proximoId() {
        String sql = "SELECT COALESCE(MAX(id_relatorio), 0) + 1 AS prox FROM relatorio";

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

    private void preencher(PreparedStatement sentenca, Relatorio r) throws SQLException {
        sentenca.setInt(1, r.getIdAdministrador());
        sentenca.setString(2, r.getConteudo());
    }

    private Relatorio mapear(ResultSet rs) throws SQLException {
        Relatorio r = new Relatorio();
        r.setIdRelatorio(rs.getInt("id_relatorio"));
        r.setIdAdministrador(rs.getInt("id_administrador"));
        r.setConteudo(rs.getString("conteudo"));
        r.setDataRelatorio(rs.getTimestamp("data_relatorio").toLocalDateTime().format(FMT_TELA));
        return r;
    }
}