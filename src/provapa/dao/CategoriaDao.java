package provapa.dao;

import provapa.apoio.ConexaoBD;
import provapa.entidades.Categoria;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by leandro on 12/07/16.
 */
public class CategoriaDao implements IDAO {

    @Override
    public int salvar(Object o) {
        Categoria categoria = (Categoria) o;
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "INSERT INTO categoria VALUES"
                    + "(DEFAULT, "
                    + "'" + categoria.getCategoria() + "',"
                    + "'" + categoria.getEstado()
                    + "') RETURNING id";
            //System.out.println("sql: " + sql);

            ResultSet rs = st.executeQuery(sql);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id");
            }
            return id;
        } catch (Exception e) {
            System.out.println("Erro ao salvar Categoria = " + e);
            return 0;
        }
    }

    @Override
    public boolean atualizar(Object o) {
        Categoria categoria = (Categoria) o;
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();
            String sql = "UPDATE categoria SET "
                    + "categoria = '" + categoria.getCategoria() + "',"
                    + "estado = '" + categoria.getEstado()
                    + "' WHERE id = " + categoria.getId();
            System.out.println("sql: " + sql);
            st.executeUpdate(sql);;
            return true;
        } catch (Exception e) {
            System.out.println("Erro Atualizar Categoria = " + e);
            return false;
        }
    }

    @Override
    public boolean excluir(int id) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();
            String sql = "DELETE FROM categoria WHERE "
                    + "id  = " + id + "";
            //  System.out.println("sql: " + sql);
            st.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao excliuir categoria = " + e);
            return false;
        }
    }

    @Override
    public ArrayList<Object> consultarTodos() {
        ArrayList categorias = new ArrayList();
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM Categoria ORDER BY 1";
            // System.out.println("sql: " + sql);

            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                Categoria tmpCategoria = new Categoria();
                tmpCategoria.setId(String.valueOf(resultado.getInt("id")));
                tmpCategoria.setCategoria(resultado.getString("categoria"));
                tmpCategoria.setEstado(resultado.getString("estado"));
                categorias.add(tmpCategoria);
            }
        } catch (Exception e) {
            System.out.println("Erro consultar Categoria= " + e);
            return null;
        }
        return categorias;
    }

    @Override
    public ArrayList<Object> consultar(String categoria) {
        ArrayList categorias = new ArrayList();
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM Categoria WHERE "
                    + "categoria iLIKE '%" + categoria + "%' ORDER BY 1;";
            System.out.println("sql: " + sql);

            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                Categoria tmpCategoria = new Categoria();
                tmpCategoria.setId(String.valueOf(resultado.getInt("id")));
                tmpCategoria.setCategoria(resultado.getString("categoria"));
                tmpCategoria.setEstado(resultado.getString("estado"));
                categorias.add(tmpCategoria);
            }
        } catch (Exception e) {
            System.out.println("Erro consultar Categoria= " + e);
            return null;
        }
        return categorias;
    }

    @Override
    public Object consultarId(int id) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM Categoria WHERE "
                    + "id = " + id + " ORDER BY 1;";

            // System.out.println("sql: " + sql);
            ResultSet resultado = st.executeQuery(sql);

            if (resultado.next()) {
                Categoria tmpCategoria = new Categoria();
                tmpCategoria.setId(String.valueOf(resultado.getInt("id")));
                tmpCategoria.setCategoria(resultado.getString("categoria"));
                tmpCategoria.setEstado(resultado.getString("estado"));
                return tmpCategoria;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro consultar Categoria= " + e);
            return e.toString();
        }
    }

    public ArrayList<Object> consultarTodosAtivos() {
        ArrayList categorias = new ArrayList();
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM Categoria WHERE estado = 'Ativo' ORDER BY 1";
            // System.out.println("sql: " + sql);

            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                Categoria tmpCategoria = new Categoria();
                tmpCategoria.setId(String.valueOf(resultado.getInt("id")));
                tmpCategoria.setCategoria(resultado.getString("categoria"));
                tmpCategoria.setEstado(resultado.getString("estado"));
                categorias.add(tmpCategoria);
            }
        } catch (Exception e) {
            System.out.println("Erro consultar Categoria= " + e);
            return null;
        }
        return categorias;
    }
    
    @Override
    public Object consultarNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
