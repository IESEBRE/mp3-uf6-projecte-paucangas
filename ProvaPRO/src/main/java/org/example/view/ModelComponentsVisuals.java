// En teoria tot fet
package org.example.view;

import javax.swing.table.DefaultTableModel;

public class ModelComponentsVisuals {

    private DefaultTableModel model;
    private DefaultTableModel modelRandom;

    public DefaultTableModel getModel() {
        return model;
    }

    public DefaultTableModel getModelRandom() {
        return modelRandom;
    }

    public ModelComponentsVisuals(){
        model = new DefaultTableModel(new Object[]{"Títol", "Autor", "Any de publicació", "Editorial", "Gènere", "Preu", "Nombre de ventes", "Nombre de pàgines", "Dibuixos", "Stock","ID"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
//                if (row==0 && column==1){
//                    return true;
//            }
                // Fem que totes les caselles de la taula no es puguen editar
                return false;
            }

            public Class getColumnClass(int column) {
                switch (column) {
                    case 0: case 1: case 3: case 4:
                        return String.class;
                    case 2: case 6: case 7:
                        return Integer.class;
                    case 5:
                        return Double.class;
                    case 8: case 9:
                        return Boolean.class;
                    case 10:
                        return Long.class;
                    default:
                        return Object.class;
                }
            }
        };

        modelRandom = new DefaultTableModel(new Object[]{"Id Random", "Numero de files de informacio Adicional"},0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };


    }

}
