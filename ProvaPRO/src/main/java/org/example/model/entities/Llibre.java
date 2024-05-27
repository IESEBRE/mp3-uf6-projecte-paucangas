//Tot fet
package org.example.model.entities;

import org.example.model.exceptions.DAOException;

import java.io.Serializable;
import java.sql.*;

public class Llibre implements Serializable {

    private long ID;
    private String titol;
    private String autor;
    private int anyPublicacio;
    private String editorial;
    private String genere;
    private double preu;
    private int numVentes;
    private int numPagines;
    private boolean conteDibuixos;
    private boolean estaEnStock;


    public Llibre(String titol, String autor, int anyPublicacio, String editorial, String genere, double preu, int numVentes, int numPagines, boolean conteDibuixos, boolean estaEnStock,long ID) {
        this.ID = ID;
        this.titol = titol;
        this.autor = autor;
        this.anyPublicacio = anyPublicacio;
        this.editorial = editorial;
        this.genere = genere;
        this.preu = preu;
        this.numVentes = numVentes;
        this.numPagines = numPagines;
        this.conteDibuixos = conteDibuixos;
        this.estaEnStock = estaEnStock;

    }
    public Llibre(String titol, String autor, int anyPublicacio, String editorial, String genere, double preu, int numVentes, int numPagines, boolean conteDibuixos, boolean estaEnStock) {
        this.titol = titol;
        this.autor = autor;
        this.anyPublicacio = anyPublicacio;
        this.editorial = editorial;
        this.genere = genere;
        this.preu = preu;
        this.numVentes = numVentes;
        this.numPagines = numPagines;
        this.conteDibuixos = conteDibuixos;
        this.estaEnStock = estaEnStock;

    }
    public Long getIdBD(String titol) throws DAOException {
        String obtIdSQL = "SELECT id FROM LLIBRE WHERE titol = ?";
        Long id = null;

        try (Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@//localhost:1521/xe",
                "C##HR",
                "HR"
        );
             PreparedStatement st = con.prepareStatement(obtIdSQL)
        ) {
            st.setString(1, titol);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    id = rs.getLong("id");
                }
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throw new DAOException(1);
        }

        return id;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(int anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public int getNumVentes() {
        return numVentes;
    }

    public void setNumVentes(int numVentes) {
        this.numVentes = numVentes;
    }

    public int getNumPagines() {
        return numPagines;
    }

    public void setNumPagines(int numPagines){
        this.numPagines = numPagines;
    }

    public Boolean isConteDibuixos() {
        return conteDibuixos;
    }

    public Boolean isEstaEnStock() {
        return estaEnStock;
    }

    public void setConteDibuixos(boolean conteDibuixos) {
        this.conteDibuixos = conteDibuixos;
    }

    public void setEstaEnStock(boolean estaEnStock) {
        this.estaEnStock = estaEnStock;
    }

    //ComboBox
    public static class ColorLlibre{
        public enum color {
            ROIG("Roig"), BLAU("Blau"), VERD("Verd"), GROC("Groc"), TARONJA("Taronja"),
            LILA("Lila"), ROSA("Rosa"), GRIS("Gris"), NEGRE("Negre"), BLANC("Blanc");

            private String color;

            color(String escuderia) {
                this.color = escuderia;
            }

            public String getColor() {
                return color;
            }

            @Override
            public String toString() {
                return  color;
            }
        }
    }


}

