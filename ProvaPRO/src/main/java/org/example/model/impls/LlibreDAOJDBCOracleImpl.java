package org.example.model.impls;

import org.example.model.daos.DAO;
import org.example.model.entities.Llibre;
import org.example.model.exceptions.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LlibreDAOJDBCOracleImpl implements DAO<Llibre> {

    public LlibreDAOJDBCOracleImpl() {
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
                    "jdbc:oracle:thin:@//localhost:1521/xe",
                    "C##HR",
                    "HR"
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
                        rs.getInt(8), rs.getBoolean(9), rs.getBoolean(10), rs.getLong(11));
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
                "jdbc:oracle:thin:@//localhost:1521/xe",
                "C##HR",
                "HR"
        );
             PreparedStatement st = con.prepareStatement("SELECT * FROM LLIBRE ORDER BY ID");
             ResultSet rs = st.executeQuery();
        ) {

            while (rs.next()) {
                estudiants.add(new Llibre(rs.getString("TITOL"), rs.getString("AUTOR"),
                        rs.getInt("ANYPUBLICACIO"), rs.getString("EDITORIAL"), rs.getString("GENERE"),
                        rs.getDouble("PREU"), rs.getInt("NUMVENTES"), rs.getInt("NUMPAGINES"),
                        rs.getBoolean("CONTEDIBUIXOS"), rs.getBoolean("ESTAENSTOCK"), rs.getLong("ID")));
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
        String insertarSQL = "INSERT INTO LLIBRE (titol, autor, anyPublicacio, editorial, genere, preu, numVentes, numPagines, conteDibuixos, estaEnStock, ID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        String selectMaxID = "SELECT NVL(MAX(id),0 ) +1 FROM LLIBRE";

        try(Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/xe",
                "C##HR",
                "HR"
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
                st.setLong(11, id);
                st.executeUpdate();
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new DAOException(1);
        }
    }

    public void delete(Llibre obj) throws DAOException {
        String deleteSQL = "DELETE FROM LLIBRE WHERE id = ?";
        try(Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/xe",
                "C##HR",
                "HR"
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
        String updateSQL = "UPDATE LLIBRE SET titol = ?, autor = ?, anyPublicacio = ?, editorial = ?, genere = ?, preu = ?, numVentes = ?, numPagines = ?, conteDibuixos = ?, estaEnStock = ? WHERE id = ?";
        try(Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/xe",
                "C##HR",
                "HR"
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
            st.setLong(11, obj.getID());
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
                "            id NUMBER PRIMARY KEY\n" +
                "        )';\n" +
                "    END IF;\n" +
                "END;";

        try (Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/xe",
                "C##HR",
                "HR"
        );
             PreparedStatement st = con.prepareStatement(CrearTaulaLlibre);
        ) {
            st.execute();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new DAOException(1);
        };
    }

    // Ara farem un mètode que cree un trigger per assignar una id a cada llibre que s'afegeixi a la taula



}
