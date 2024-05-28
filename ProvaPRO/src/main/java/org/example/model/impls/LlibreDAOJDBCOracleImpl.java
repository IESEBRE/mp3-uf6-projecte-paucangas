package org.example.model.impls;

import org.example.model.daos.DAO;
import org.example.model.entities.Llibre;
import org.example.model.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LlibreDAOJDBCOracleImpl implements DAO<Llibre> {

    private DatabaseProperties dbProperties;
    public LlibreDAOJDBCOracleImpl() {
        dbProperties = new DatabaseProperties();
        try {
            crearTaulaSiNoExisteix();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Llibre get(Long id) throws DAOException {

        //Declaració de variables del mètode
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        Llibre estudiant = null;

        //Accés a la BD usant l'API JDBC
        try {

            con = DriverManager.getConnection(
                    dbProperties.getUrl(),
                    dbProperties.getUser(),
                    dbProperties.getPassword()
            );
//            st = con.prepareStatement("SELECT * FROM estudiant WHERE id=?;");
            st = con.createStatement();
//            st = con.prepareStatement("SELECT * FROM estudiant WHERE id=?;");
//            st.setLong(1, id);
            rs = st.executeQuery("SELECT * FROM LLIBRE;");
//            estudiant = new Alumne(rs.getLong(1), rs.getString(2));
//            st.close();
            if (rs.next()) {
                estudiant = new Llibre(rs.getString(1), rs.getString(2),
                        rs.getInt(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getInt(7),
                        rs.getInt(8), rs.getBoolean(9), rs.getBoolean(10), rs.getString(11),rs.getLong(12));
            }
        } catch (SQLException throwables) {
            throw new DAOException(1);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                throw new DAOException(1);
            }

        }
        return estudiant;
    }

    @Override
    public List<Llibre> getAll() throws DAOException {
        //Declaració de variables del mètode
        List<Llibre> estudiants = new ArrayList<>();

        //Accés a la BD usant l'API JDBC
        try (Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
             PreparedStatement st = con.prepareStatement("SELECT * FROM LLIBRE ORDER BY ID");
             ResultSet rs = st.executeQuery();
        ) {

            while (rs.next()) {
                estudiants.add(new Llibre(rs.getString("TITOL"), rs.getString("AUTOR"),
                        rs.getInt("ANYPUBLICACIO"), rs.getString("EDITORIAL"), rs.getString("GENERE"),
                        rs.getDouble("PREU"), rs.getInt("NUMVENTES"), rs.getInt("NUMPAGINES"),
                        rs.getBoolean("CONTEDIBUIXOS"), rs.getBoolean("ESTAENSTOCK"), rs.getString("COLOR"),rs.getLong("ID")));
            }
        } catch (SQLException throwables) {
            int tipoError = throwables.getErrorCode();
            //System.out.println(tipoError+" "+throwables.getMessage());
            switch(throwables.getErrorCode()){
                case 17002: //l'he obtingut posant un sout en el throwables.getErrorCode()
                    tipoError = 0;
                    break;
                default:
                    tipoError = 1;  //error desconegut
            }
            System.out.println(tipoError+" "+throwables.getMessage());
            throw new DAOException(tipoError);
        }


        return estudiants;
    }

    @Override
    public void save(Llibre obj) throws DAOException {
        String insertarSQL = "INSERT INTO LLIBRE (titol, autor, anyPublicacio, editorial, genere, preu, numVentes, numPagines, conteDibuixos, estaEnStock,color, ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        String selectMaxID = "SELECT NVL(MAX(id),0 ) +1 FROM LLIBRE";

        try(Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
            PreparedStatement stSave = con.prepareStatement(selectMaxID);
            PreparedStatement st = con.prepareStatement(insertarSQL);
        ) {
            ResultSet rs = stSave.executeQuery();
            if (rs.next()) {
                long id = rs.getLong(1);
                st.setString(1, obj.getTitol());
                st.setString(2, obj.getAutor());
                st.setInt(3, obj.getAnyPublicacio());
                st.setString(4, obj.getEditorial());
                st.setString(5, obj.getGenere());
                st.setDouble(6, obj.getPreu());
                st.setInt(7, obj.getNumVentes());
                st.setInt(8, obj.getNumPagines());
                st.setString(9, obj.isConteDibuixos()? "T" : "F");
                st.setString(10, obj.isEstaEnStock()? "T" : "F");
                st.setString(11, obj.getColorL());
                st.setLong(12, id);
                st.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new DAOException(1);
        }
    }
    public void insertaVentes (Llibre obj) throws DAOException {
        String insertarSQL = "INSERT INTO VENTES (id, preuPerVenda) VALUES (?,?)";
        try(Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
            PreparedStatement st = con.prepareStatement(insertarSQL);
        ) {
            st.setLong(1, obj.getIdBD(obj.getTitol()));
            st.setDouble(2, 0);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(1);
        }
    }

    public void delete(Llibre obj) throws DAOException {
        String deleteSQL = "DELETE FROM LLIBRE WHERE id = ?";
        try(Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
            PreparedStatement st = con.prepareStatement(deleteSQL);
        ) {
            st.setLong(1, obj.getIdBD(obj.getTitol()));
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(1);
        }
    }

    public void update(Llibre obj) throws DAOException {
        String updateSQL = "UPDATE LLIBRE SET titol = ?, autor = ?, anyPublicacio = ?, editorial = ?, genere = ?, preu = ?, numVentes = ?, numPagines = ?, conteDibuixos = ?, estaEnStock = ?, color = ? WHERE id = ?";
        try(Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
            PreparedStatement st = con.prepareStatement(updateSQL);
        ) {
            st.setString(1, obj.getTitol());
            st.setString(2, obj.getAutor());
            st.setInt(3, obj.getAnyPublicacio());
            st.setString(4, obj.getEditorial());
            st.setString(5, obj.getGenere());
            st.setDouble(6, obj.getPreu());
            st.setInt(7, obj.getNumVentes());
            st.setInt(8, obj.getNumPagines());
            st.setString(9, obj.isConteDibuixos()? "T" : "F");
            st.setString(10, obj.isEstaEnStock()? "T" : "F");
            st.setString(11, obj.getColorL());
            st.setLong(12, obj.getID());
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(1);
        }
    }
    public void updatePreuPerVenda(long id) throws DAOException {
        String call = "{call update_preuPerVenda(?)}";

        try (Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
             CallableStatement stmt = con.prepareCall(call)) {

            stmt.setLong(1, id);

            stmt.execute();
        } catch (SQLException e) {
            throw new DAOException(1);
        }
    }
    public void borrarVentes (long id) throws DAOException {
        String deleteSQL = "DELETE FROM VENTES WHERE id = ?";
        try(Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
            PreparedStatement st = con.prepareStatement(deleteSQL);
        ) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException throwables) {
            throw new DAOException(1);
        }
    }
    public void crearTaulaSiNoExisteix() throws DAOException {
        String CrearTaulaLlibre = "DECLARE\n" +
                "    v_count NUMBER;\n" +
                "BEGIN\n" +
                "    SELECT COUNT(*) INTO v_count FROM user_tables WHERE table_name = 'LLIBRE';\n" +
                "    IF v_count = 0 THEN\n" +
                "        EXECUTE IMMEDIATE 'CREATE TABLE LLIBRE (\n" +
                "            titol VARCHAR2(100) NOT NULL,\n" +
                "            autor VARCHAR2(100) NOT NULL,\n" +
                "            anyPublicacio NUMBER NOT NULL,\n" +
                "            editorial VARCHAR2(100) NOT NULL,\n" +
                "            genere VARCHAR2(100) NOT NULL,\n" +
                "            preu NUMBER NOT NULL,\n" +
                "            numVentes NUMBER NOT NULL,\n" +
                "            numPagines NUMBER NOT NULL,\n" +
                "            conteDibuixos CHAR(1 CHAR) NOT NULL,\n" +
                "            estaEnStock CHAR(1 CHAR) NOT NULL,\n" +
                "            color VARCHAR2(100) NOT NULL,\n" +
                "            id NUMBER PRIMARY KEY\n" +
                "        )';\n" +
                "    END IF;\n" +
                "END;";

        // Ara crearem una taula que agafe els camp id de la taula llibre i un altre camp que sigui el nombre de ventes per el preu per cada un dels id
        String CrearTaulaVentes = "DECLARE\n" +
                "    v_count NUMBER;\n" +
                "BEGIN\n" +
                "    SELECT COUNT(*) INTO v_count FROM user_tables WHERE table_name = 'VENTES';\n" +
                "    IF v_count = 0 THEN\n" +
                "        EXECUTE IMMEDIATE 'CREATE TABLE VENTES (\n" +
                "            id NUMBER NOT NULL,\n" +
                "            preuPerVenda NUMBER NOT NULL,\n" +
                "            FOREIGN KEY (id) REFERENCES LLIBRE(id)\n" +
                "        )';\n" +
                "    END IF;\n" +
                "END;";

        try (Connection con = DriverManager.getConnection(
                dbProperties.getUrl(),
                dbProperties.getUser(),
                dbProperties.getPassword()
        );
             PreparedStatement st = con.prepareStatement(CrearTaulaLlibre);
             PreparedStatement st2 = con.prepareStatement(CrearTaulaVentes);
        ) {
            st.execute();
            st2.execute();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new DAOException(1);
        };
    }
}
