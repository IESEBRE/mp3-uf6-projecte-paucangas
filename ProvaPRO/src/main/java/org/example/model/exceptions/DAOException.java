package org.example.model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DAOException extends Exception{

    private static final Map<Integer, String> missatges = new HashMap<>();
    //num i retorna string, el map
    static {
        missatges.put(0, "Error al connectar a la BD!!");
        missatges.put(2, "Error al Inserir les dades a la BD!!");
        missatges.put(3, "Error al Actualitzar les dades a la BD!!");
        missatges.put(4, "Error al amb el format de les dades del preu!!");
        missatges.put(5, "Error al amb el format de les dades de l'any de publicació!!");
        missatges.put(6, "Error al amb el format de les dades del nombre de ventes!!");
        missatges.put(7, "Error al amb el format de les dades del nombre de pàgines!!");
        missatges.put(8, "Error amb nulls en les dades!!");
        missatges.put(9, "Error al insertar les dades a la BD de Llibre!!");
        missatges.put(10, "Error al insertar les dades a la BD de Ventes!!");
        missatges.put(11, "Error al borrar les dades a la BD de Llibre!!");
        missatges.put(12, "Error al borrar les dades a la BD de Ventes!!");
        missatges.put(13, "Error al actualitzar les dades a la BD de Llibre!!");
        missatges.put(14, "Error al actualitzar les dades a la BD de Ventes!!");
        missatges.put(15, "Error al crear les taules a la BD!!");
        missatges.put(16, "Dades de connexió a la BD incorrectes!!");
        missatges.put(1, "Restricció d'integritat violada - clau primària duplicada");
        missatges.put(904, "Nom de columna no vàlid");
        missatges.put(936, "Falta expressió en l'ordre SQL");
        missatges.put(942, "La taula o la vista no existeix");
        missatges.put(1000, "S'ha superat el nombre màxim de cursors oberts");
        missatges.put(1400, "Inserció de valor nul en una columna que no permet nuls");
        missatges.put(1403, "No s'ha trobat cap dada");
        missatges.put(1722, "Ha fallat la conversió d'una cadena de caràcters a un número");
        missatges.put(1747, "El nombre de columnes de la vista no coincideix amb el nombre de columnes de les taules subjacents");
        missatges.put(4091, "Modificació d'un procediment o funció en execució actualment");
        missatges.put(6502, "Error numèric o de valor durant l'execució del programa");
        missatges.put(12154, "No s'ha pogut resoldre el nom del servei de la base de dades Oracle o l'identificador de connexió");
        missatges.put(2292, "S'ha violat la restricció d'integritat -  s'ha trobat un registre fill");
    }

    //atribut
    private int tipo;

    //constructor al q pasem tipo
    public DAOException(int tipo){
        this.tipo=tipo;
    }

    //sobreescrivim el get message
        @Override
    public String getMessage(){
        return missatges.get(this.tipo); //el missatge del tipo
    }

    public int getTipo() {
        return tipo;
    }
}
