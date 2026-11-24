package Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Empresa;

public class EmpresaDao {

    private Conexaobanco conexao;

    private static final String COLUNAS = "razao_social=?, cnpj=?";

    public EmpresaDao() {
        this.conexao = new Conexaobanco();
    }

    public int inserir(Empresa e) {
        String sql = "INSERT INTO empresa SET " + COLUNAS;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection()
                        .prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

                preencher(sentenca, e);
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

    public void alterar(Empresa e) {
        String sql = "UPDATE empresa SET " + COLUNAS + " WHERE id_empresa=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);

                preencher(sentenca, e);
                sentenca.setInt(3, e.getIdEmpresa());
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void excluir(int idEmpresa) {
        String sql = "DELETE FROM empresa WHERE id_empresa=?";

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idEmpresa);
                sentenca.execute();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Empresa buscarPorId(int idEmpresa) {
        String sql = "SELECT * FROM empresa WHERE id_empresa=?";
        Empresa e = null;

        try {
            if (this.conexao.conectar()) {
                PreparedStatement sentenca = this.conexao.getConnection().prepareStatement(sql);
                sentenca.setInt(1, idEmpresa);
                ResultSet rs = sentenca.executeQuery();

                if (rs.next()) e = mapear(rs);

                rs.close();
                sentenca.close();
                this.conexao.getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return e;
    }

    public ArrayList<Empresa> listar() {
        ArrayList<Empresa> lista = new ArrayList<>();
        String sql = "SELECT * FROM empresa ORDER BY razao_social";

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
        String sql = "SELECT COALESCE(MAX(id_empresa), 0) + 1 AS prox FROM empresa";

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

    private void preencher(PreparedStatement sentenca, Empresa e) throws SQLException {
        sentenca.setString(1, e.getRazaoSocial());
        sentenca.setString(2, e.getCnpj());
    }

    private Empresa mapear(ResultSet rs) throws SQLException {
        Empresa e = new Empresa();
        e.setIdEmpresa(rs.getInt("id_empresa"));
        e.setRazaoSocial(rs.getString("razao_social"));
        e.setCnpj(rs.getString("cnpj"));
        return e;
    }
}