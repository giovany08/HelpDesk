package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Setor;

public class SetorDao {

    private Conexaobanco conexao;

    private static final String COLUNAS = "id_empresa=?, nome=?";

    public SetorDao() {
        this.conexao = new Conexaobanco();
    }

    public int inserir(Setor s) {
        String sql = "INSERT INTO setor SET " + COLUNAS;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection()
                        .prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

                preencher(sentenca, s);
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

    public void alterar(Setor s) {
        String sql = "UPDATE setor SET " + COLUNAS + " WHERE id_setor=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);

                preencher(sentenca, s);
                sentenca.setInt(3, s.getIdSetor());
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void excluir(int idSetor) {
        String sql = "DELETE FROM setor WHERE id_setor=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idSetor);
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Setor buscarPorId(int idSetor) {
        String sql = "SELECT * FROM setor WHERE id_setor=?";
        Setor s = null;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idSetor);
                ResultSet rs = sentenca.executeQuery();

                if (rs.next()) s = mapear(rs);

                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return s;
    }

    public ArrayList<Setor> listar() {
        ArrayList<Setor> lista = new ArrayList<>();
        String sql = "SELECT * FROM setor ORDER BY nome";

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

    public ArrayList<Setor> listarPorEmpresa(int idEmpresa) {
        ArrayList<Setor> lista = new ArrayList<>();
        String sql = "SELECT * FROM setor WHERE id_empresa=? ORDER BY nome";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idEmpresa);
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
        String sql = "SELECT COALESCE(MAX(id_setor), 0) + 1 AS prox FROM setor";

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

    private void preencher(PreparedStatement sentenca, Setor s) throws SQLException {
        sentenca.setInt(1, s.getIdEmpresa());
        sentenca.setString(2, s.getNome());
    }

    private Setor mapear(ResultSet rs) throws SQLException {
        Setor s = new Setor();
        s.setIdSetor(rs.getInt("id_setor"));
        s.setIdEmpresa(rs.getInt("id_empresa"));
        s.setNome(rs.getString("nome"));
        return s;
    }
}