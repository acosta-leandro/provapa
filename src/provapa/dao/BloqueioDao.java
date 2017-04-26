package provapa.dao;

import provapa.apoio.ConexaoBD;
import provapa.entidades.Bloqueio;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by leandro on 12/07/16.
 */
public class BloqueioDao implements IDAO{

    @Override
    public int salvar(Object o) {
        Bloqueio bloqueio = (Bloqueio) o;
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "INSERT INTO bloqueio VALUES"
                    + "(DEFAULT, "
                    + "'" + bloqueio.getBloqueio() + "',"
                    + "'" + bloqueio.getEstado()
                    + "') RETURNING id";
            System.out.println("sql: " + sql);

            ResultSet rs = st.executeQuery(sql);
            int id = 0;
            if (rs.next()) {
                id = rs.getInt("id");
            }
            return id;
        } catch (Exception e) {
            System.out.println("Erro ao salvar Bloqueio = " + e);
            return 0;
        }
    }

    @Override
    public boolean atualizar(Object o) {
        Bloqueio bloqueio = (Bloqueio) o;
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();
            String sql = "UPDATE bloqueio SET "
                    + "bloqueio = '" + bloqueio.getBloqueio() + "',"
                    + "estado = '" + bloqueio.getEstado()
                    + "' WHERE id = " + bloqueio.getId();
            System.out.println("sql: " + sql);
            st.executeUpdate(sql);;
            return true;
        } catch (Exception e) {
            System.out.println("Erro Atualizar Bloqueio = " + e);
            return false;
        }
    }

    @Override
    public boolean excluir(int id) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();
            String sql = "DELETE FROM bloqueio WHERE "
                    + "id  = " + id + "";
            //  System.out.println("sql: " + sql);
            st.execute(sql);
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao excliuir bloqueio = " + e);
            return false;
        }
    }

    @Override
    public ArrayList<Object> consultarTodos() {
        ArrayList bloqueios = new ArrayList();
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM Bloqueio ORDER BY 1";
            // System.out.println("sql: " + sql);

            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                Bloqueio tmpBloqueio = new Bloqueio();
                tmpBloqueio.setId(String.valueOf(resultado.getInt("id")));
                tmpBloqueio.setBloqueio(resultado.getString("bloqueio"));
                tmpBloqueio.setEstado(resultado.getString("estado"));
                bloqueios.add(tmpBloqueio);
            }
        } catch (Exception e) {
            System.out.println("Erro consultar Bloqueio= " + e);
            return null;
        }
        return bloqueios;
    }

    @Override
    public ArrayList<Object> consultar(String bloqueio) {
        ArrayList bloqueios = new ArrayList();
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM Bloqueio WHERE "
                    + "bloqueio iLIKE '%" + bloqueio + "%' ORDER BY 1;";
            System.out.println("sql: " + sql);

            ResultSet resultado = st.executeQuery(sql);
            while (resultado.next()) {
                Bloqueio tmpBloqueio = new Bloqueio();
                tmpBloqueio.setId(String.valueOf(resultado.getInt("id")));
                tmpBloqueio.setBloqueio(resultado.getString("bloqueio"));
                tmpBloqueio.setEstado(resultado.getString("estado"));
                bloqueios.add(tmpBloqueio);
            }
        } catch (Exception e) {
            System.out.println("Erro consultar Bloqueio= " + e);
            return null;
        }
        return bloqueios;
    }

    @Override
    public Object consultarId(int id) {
        try {
            Statement st = ConexaoBD.getInstance().getConnection().createStatement();

            String sql = "SELECT * FROM Bloqueio WHERE "
                    + "id = " + id + " ORDER BY 1;";

            // System.out.println("sql: " + sql);
            ResultSet resultado = st.executeQuery(sql);

            if (resultado.next()) {
                Bloqueio tmpBloqueio = new Bloqueio();
                tmpBloqueio.setId(String.valueOf(resultado.getInt("id")));
                tmpBloqueio.setBloqueio(resultado.getString("bloqueio"));
                tmpBloqueio.setEstado(resultado.getString("estado"));
                return tmpBloqueio;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Erro consultar Bloqueio= " + e);
            return e.toString();
        }
    }

    @Override
    public Object consultarNome(String nome) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
