package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;

public class UsuarioDao {

    private Conexaobanco conexao;

    // a senha fica fora do COLUNAS: só é gravada no inserir e no alterarSenha
    private static final String COLUNAS = "id_setor=?, nome=?, login=?, sexo=?, cargo=?, perfil=?";

    public UsuarioDao() {
        this.conexao = new Conexaobanco();
    }

    public Usuario autenticar(String login, String senhaHash) {
        String sql = "SELECT * FROM usuario WHERE login=? AND senha_hash=?";
        Usuario u = null;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setString(1, login);
                sentenca.setString(2, senhaHash);
                ResultSet rs = sentenca.executeQuery();

                if (rs.next()) u = mapear(rs);

                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return u;
    }

    public int inserir(Usuario u) {
        String sql = "INSERT INTO usuario SET " + COLUNAS + ", senha_hash=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection()
                        .prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

                preencher(sentenca, u);
                sentenca.setString(7, u.getSenhaHash());
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

    public void alterar(Usuario u) {
        String sql = "UPDATE usuario SET " + COLUNAS + " WHERE id_usuario=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);

                preencher(sentenca, u);
                sentenca.setInt(7, u.getIdUsuario());
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void alterarSenha(int idUsuario, String novaSenhaHash) {
        String sql = "UPDATE usuario SET senha_hash=? WHERE id_usuario=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setString(1, novaSenhaHash);
                sentenca.setInt(2, idUsuario);
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void excluir(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE id_usuario=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idUsuario);
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Usuario buscarPorId(int idUsuario) {
        String sql = "SELECT * FROM usuario WHERE id_usuario=?";
        Usuario u = null;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idUsuario);
                ResultSet rs = sentenca.executeQuery();

                if (rs.next()) u = mapear(rs);

                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return u;
    }

    public ArrayList<Usuario> listar() {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY nome";

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

    public ArrayList<Usuario> listarPorPerfil(String perfil) {
        ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE perfil=? ORDER BY nome";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setString(1, perfil);
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
        String sql = "SELECT COALESCE(MAX(id_usuario), 0) + 1 AS prox FROM usuario";

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

    private void preencher(PreparedStatement sentenca, Usuario u) throws SQLException {
        sentenca.setInt(1, u.getIdSetor());
        sentenca.setString(2, u.getNome());
        sentenca.setString(3, u.getLogin());
        sentenca.setString(4, u.getSexo());
        sentenca.setString(5, u.getCargo());
        sentenca.setString(6, u.getPerfil());
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setIdSetor(rs.getInt("id_setor"));
        u.setNome(rs.getString("nome"));
        u.setLogin(rs.getString("login"));
        u.setSenhaHash(rs.getString("senha_hash"));
        u.setSexo(rs.getString("sexo"));
        u.setCargo(rs.getString("cargo"));
        u.setPerfil(rs.getString("perfil"));
        return u;
    }
}