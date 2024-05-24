package org.example.controller;


import org.example.model.entities.Llibre;
import org.example.model.exceptions.DAOException;
import org.example.model.impls.LlibreDAOJDBCOracleImpl;
import org.example.view.ModelComponentsVisuals;
import org.example.view.Vista;
import org.example.model.impls.LlibreDAOJDBCOracleImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

public class Controller implements PropertyChangeListener { //1. Implementació de interfície PropertyChangeListener

    //2. Propietat lligada per controlar quan genro una excepció
    public static final String PROP_EXCEPCIO = "excepcio";
    private DAOException excepcio;

    public DAOException getExcepcio() {
        return excepcio;
    }

    public void setExcepcio(DAOException excepcio) {
        DAOException valorVell = this.excepcio;
        this.excepcio = excepcio;
        canvis.firePropertyChange(PROP_EXCEPCIO, valorVell, excepcio);
    }


    //3. Propietat PropertyChangesupport necessària per poder controlar les propietats lligades
    PropertyChangeSupport canvis = new PropertyChangeSupport(this);


    //4. Mètode on posarem el codi de tractament de les excepcions --> generat per la interfície PropertyChangeListener

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        DAOException rebuda = (DAOException) evt.getNewValue();

        try {
            throw rebuda;
        } catch (DAOException e) {
            //Aquí farem el tractament de les excepcions de l'aplicació
            switch (evt.getPropertyName()) {
                case PROP_EXCEPCIO:
                    switch (rebuda.getTipo()){
                        case 0:
                            JOptionPane.showMessageDialog(null, rebuda.getMessage());
                            System.exit(1);
                            break;
                        case 1:
                            JOptionPane.showMessageDialog(null, rebuda.getMessage());
                            break;
                        case 2:
                            JOptionPane.showMessageDialog(null, rebuda.getMessage());
                            //this.view.getCampNom().setText(rebuda.getMissatge());
                            this.vista.getTitol().setSelectionStart(0);
                            this.vista.getTitol().setSelectionEnd(this.vista.getTitol().getText().length());
                            this.vista.getTitol().requestFocus();

                            break;
                    }
            }
        }
    }

    private LlibreDAOJDBCOracleImpl dadesLlibre;
    private Vista vista;

    private ModelComponentsVisuals modelComponentsVisuals = new ModelComponentsVisuals();

    public Controller(LlibreDAOJDBCOracleImpl dadesLlibre, Vista vista) {
        this.dadesLlibre = dadesLlibre;
        this.vista = vista;

        //5. Necessari per a que Controller reaccione davant de canvis a les propietats lligades
        canvis.addPropertyChangeListener(this);
        //Mètode per lligar la vista i el model
        lligarVistaModel();
        //Assigno el codi dels listeners
        assignarCodiListeners();

        vista.setVisible(true);
    }

    private void assignarCodiListeners() {
        ModelComponentsVisuals modelo = this.modelComponentsVisuals;

        DefaultTableModel model = this.modelComponentsVisuals.getModel();


        JButton insertarButton = vista.getInsertarButton();
        JButton borrarButton = vista.getBorrarButton();
        JButton modificarButton = vista.getModificarButton();
        JButton desaButton = vista.getDesaButton();
        JButton buida = vista.getBuida();
        JButton informacioAdicionalButton = vista.getInformacioAdicionalButton();

        JTextField titol = vista.getTitol();
        JTextField autor = vista.getAutor();
        JTextField anyPublicacio = vista.getAnyPublicacio();
        JTextField editorial = vista.getEditorial();
        JTextField genere = vista.getGenere();
        JTextField preu = vista.getPreu();
        JTextField numVentes = vista.getNumVentes();
        JTextField numPagines = vista.getNumPagines();
        JCheckBox conteDibuixos = vista.getConteDibuixos();
        JCheckBox estaEnStock = vista.getEstaEnStock();

        JTable taula = vista.getTaula();
        JTable informacioAdicionalTaula = vista.getInformacioAdicionalTable();

        // Define una bandera para controlar si los listeners ya se han agregado
        final boolean[] listenersAgregados = {false};


        vista.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Si", "No"};
                int confirm = JOptionPane.showOptionDialog(
                        null, "Vols desar al tancar?", "Confirmació de tancament",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                if (confirm == 0) {
                    System.exit(0);
                } else {
                    System.exit(0);
                }
            }
        });
        insertarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (titol.getText().isBlank() || anyPublicacio.getText().isBlank() || editorial.getText().isBlank() || genere.getText().isBlank() || preu.getText().isBlank() || numVentes.getText().isBlank() || numPagines.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "Has d'omplir els camps imprescindibles");
                    canviarVora(titol);
                    canviarVora(anyPublicacio);
                    canviarVora(editorial);
                    canviarVora(genere);
                    canviarVora(preu);
                    canviarVora(numVentes);
                    canviarVora(numPagines);

                    vista.getTabbedPaneRandom().setEnabledAt(1, false);
                    vista.getTabbedPaneRandom().setTitleAt(1, "Random");
                } else {
                    autor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    titol.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    anyPublicacio.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    editorial.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    genere.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    preu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    numVentes.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                    numPagines.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                    if (autor.getText().isBlank() || autor.getText().equals("Si no saps l'autor, pots deixar aquest camp en blanc")) {
                        autor.setText("Anònim");
                    }
                    // Mirem si a la taula hi ha alguna fila amb el mateix títol
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).equals(titol.getText())) {
                            JOptionPane.showMessageDialog(null, "Aquest títol ja existeix a la taula");
                            titol.setText("");
                            titol.requestFocus();
                            return;
                        }
                    }
                    try {
                        NumberFormat num = NumberFormat.getInstance(Locale.getDefault()); // Creem un número que entén la cultura que utilitza l'aplicació
                        int campAnyPublicacio = num.parse(anyPublicacio.getText().trim()).intValue(); // Intentem convertir el text a double
                        double campPreu = num.parse(preu.getText().trim()).doubleValue(); // Intentem convertir el text a double
                        int campNumVentes = num.parse(numVentes.getText().trim()).intValue();
                        int campNumPagines = num.parse(numPagines.getText().trim()).intValue();
                        //-----------------------------------------------------------------
                        // Creem un instància de Llibre amb les dades dels camps
                        Llibre llibre = new Llibre(titol.getText(), autor.getText(), campAnyPublicacio, editorial.getText(), genere.getText(), campPreu, campNumVentes, campNumPagines, conteDibuixos.isSelected(), estaEnStock.isSelected(), 0);
                        dadesLlibre.save(new Llibre(titol.getText(), autor.getText(), campAnyPublicacio, editorial.getText(), genere.getText(), campPreu, campNumVentes, campNumPagines, conteDibuixos.isSelected(), estaEnStock.isSelected(), 0));
                        model.addRow(new Object[]{titol.getText(), autor.getText(), campAnyPublicacio, editorial.getText(), genere.getText(), campPreu, campNumVentes, campNumPagines, conteDibuixos.isSelected(), estaEnStock.isSelected(), llibre.getIdBD(titol.getText()), llibre});
                        autor.setSelectionStart(0);
                        autor.setSelectionEnd(autor.getText().length());
                        autor.requestFocus();
                        borrarCamp();
                    } catch (NumberFormatException | ParseException ne) {
                        anyPublicacio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                        preu.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                        numVentes.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                        numPagines.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    } catch (DAOException ex) {
                        System.out.println(ex.getMessage());
                        throw new RuntimeException(ex);
                    }
                }

            }
        });


        insertarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                insertarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSel = taula.getSelectedRow();
                if (filaSel != -1) {
                    if (titol.getText().isBlank() || anyPublicacio.getText().isBlank() || editorial.getText().isBlank() || genere.getText().isBlank() || preu.getText().isBlank() || numVentes.getText().isBlank() || numPagines.getText().isBlank()) {
                        JOptionPane.showMessageDialog(null, "Has d'omplir els camps imprescindibles");
                        canviarVora(titol);
                        canviarVora(anyPublicacio);
                        canviarVora(editorial);
                        canviarVora(genere);
                        canviarVora(preu);
                        canviarVora(numVentes);
                        canviarVora(numPagines);



                        vista.getTabbedPaneRandom().setEnabledAt(1, false);
                        vista.getTabbedPaneRandom().setTitleAt(1, "Random");
                    } else{
                        autor.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                        titol.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                        anyPublicacio.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                        editorial.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                        genere.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                        preu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                        numVentes.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                        numPagines.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

                        if (autor.getText().isBlank() || autor.getText().equals("Si no saps l'autor, pots deixar aquest camp en blanc")) {
                            autor.setText("Anònim");
                        }
                        // Mirem que quans es modifique, no hi hagi cap columna extactament igual
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 0).equals(titol.getText()) && i != filaSel) {
                                JOptionPane.showMessageDialog(null, "Hi ha una altra fila exactament igual a la que vols modificar");
                                titol.setText("");
                                titol.requestFocus();
                                return;
                            }
                        }
                        //També mirem que al modificar, no hi hagi cap fila amb el mateix títol menys a la que esteim modificant
                        for (int i = 0; i < model.getRowCount(); i++) {
                            if (model.getValueAt(i, 0).equals(titol.getText()) && i != filaSel) {
                                JOptionPane.showMessageDialog(null, "Aquest títol ja existeix a la taula");
                                titol.setText("");
                                titol.requestFocus();
                                return;
                            }
                        }

                        if (model.getValueAt(filaSel, 2).equals(Integer.parseInt(anyPublicacio.getText())) && model.getValueAt(filaSel, 5).equals(Double.parseDouble(preu.getText().trim().replace(",", "."))) && model.getValueAt(filaSel, 6).equals(Integer.parseInt(numVentes.getText())) && model.getValueAt(filaSel, 7).equals(Integer.parseInt(numPagines.getText())) && model.getValueAt(filaSel, 8).equals(conteDibuixos.isSelected()) && model.getValueAt(filaSel, 9).equals(estaEnStock.isSelected())){
                            JOptionPane.showMessageDialog(null, "No has fet cap canvi");
                            return;
                        }
                        try {
                            NumberFormat num = NumberFormat.getInstance(Locale.getDefault()); // Creem un número que entén la cultura que utilitza l'aplicació
                            int campAnyPublicacio = num.parse(anyPublicacio.getText().trim()).intValue(); // Intentem convertir el text a double
                            double campPreu = num.parse(preu.getText().trim()).doubleValue(); // Intentem convertir el text a double
                            int campNumVentes = num.parse(numVentes.getText().trim()).intValue();
                            int campNumPagines = num.parse(numPagines.getText().trim()).intValue();
                            //-----------------------------------------------------------------
                            model.removeRow(filaSel);
                            model.insertRow(filaSel, new Object[]{titol.getText(), autor.getText(), campAnyPublicacio, editorial.getText(), genere.getText(), campPreu, campNumVentes, campNumPagines, conteDibuixos.isSelected(), estaEnStock.isSelected()});
                            // Posem els camps en blanc

                            borrarCamp();
                            autor.setSelectionStart(0);
                            autor.setSelectionEnd(autor.getText().length());
                            autor.requestFocus();

                        } catch (NumberFormatException | ParseException pe) {
                            anyPublicacio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            preu.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            numVentes.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            numPagines.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            setExcepcio(new DAOException(2));
                        }
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "Has de seleccionar una fila per a modificar-la");
                }
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mirem si tenim una fila seleccionada
                int filaSel = taula.getSelectedRow();
                if (filaSel != -1) {
                    Llibre llibre = new Llibre(titol.getText(), autor.getText(), Integer.parseInt(anyPublicacio.getText()), editorial.getText(), genere.getText(), Double.parseDouble(preu.getText().trim().replace(",", ".")), Integer.parseInt(numVentes.getText()), Integer.parseInt(numPagines.getText()), conteDibuixos.isSelected(), estaEnStock.isSelected(), 0);
                    model.removeRow(filaSel);
                    // Posem els camps en blanc
                    borrarCamp();
                    try {
                        dadesLlibre.delete(llibre);
                    } catch (DAOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Has de seleccionar una fila per a borrar-la");

                    vista.getTabbedPaneRandom().setEnabledAt(1, false);
                }
            }
        });
        taula.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int filaSel = taula.getSelectedRow();
                if (filaSel == -1) return;
                // No hi ha cap fila seleccionada
                // Posem els valors de la fila seleccionada als camps respectius
                titol.setText(model.getValueAt(filaSel, 0).toString());
                autor.setText(model.getValueAt(filaSel, 1).toString());
                anyPublicacio.setText(model.getValueAt(filaSel, 2).toString());
                editorial.setText(model.getValueAt(filaSel, 3).toString());
                genere.setText(model.getValueAt(filaSel, 4).toString());
                preu.setText(model.getValueAt(filaSel, 5).toString().replace(".", ","));
                numVentes.setText(model.getValueAt(filaSel, 6).toString());
                numPagines.setText(model.getValueAt(filaSel, 7).toString());
                conteDibuixos.setSelected((boolean) model.getValueAt(filaSel, 8));
                estaEnStock.setSelected((boolean) model.getValueAt(filaSel, 9));
                vista.getTabbedPaneRandom().setEnabledAt(1, true);
                vista.getTabbedPaneRandom().setTitleAt(1, "Random");



            }
        });

        buida.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarCamp();
            }
        });
        modificarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                super.mouseEntered(e);

                modificarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        borrarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                super.mouseEntered(e);

                borrarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });
        preu.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {

                super.focusLost(e);

                try {
                    if (!preu.getText().isBlank()) {
                        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault()); // Creem un número que entén la cultura que utilitza l'aplicació
                        double campPreu = num.parse(preu.getText().trim()).doubleValue(); // Intentem convertir el text a double
                        int comptComa = 0;
                        int comptPunt = 0;
                        if (campPreu <= 0) {
                            preu.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                            JOptionPane.showMessageDialog(null, "No hi ha res gratis a la vida");
                            preu.setText("");
                            preu.requestFocus();
                        }
                        //anem a buscar si hi han lletres dins del número davant i detras de la coma
                        String preuFinal = campPreu + "";
                        for (int i = 0; i < preu.getText().length(); i++) {
                            if (preu.getText().charAt(i) != '0' && preu.getText().charAt(i) != '1' && preu.getText().charAt(i) != '2' && preu.getText().charAt(i) != '3' && preu.getText().charAt(i) != '4' && preu.getText().charAt(i) != '5' && preu.getText().charAt(i) != '6' && preu.getText().charAt(i) != '7' && preu.getText().charAt(i) != '8' && preu.getText().charAt(i) != '9' && preu.getText().charAt(i) != '.' && preu.getText().charAt(i) != ',') {
                                throw new NumberFormatException();
                            }
                            if (preu.getText().charAt(i) == ',') {
                                comptComa++;
                            }
                            if (preu.getText().charAt(i) == '.') {
                                comptPunt++;
                            }
                            if (comptComa > 1 || comptPunt > 1) {
                                throw new NumberFormatException();
                            }
                        }

                    }
                    preu.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                } catch (ParseException | NumberFormatException pe) {

                    preu.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    setExcepcio(new DAOException(2));
                    preu.setText("");
                    preu.requestFocus();
                }
            }
        });
        anyPublicacio.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (!anyPublicacio.getText().isBlank()) {

                        Integer.valueOf(anyPublicacio.getText().trim());

                        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault()); // Creem un número que entén la cultura que utilitza l'aplicació
                        int campAnyPublicacio = num.parse(anyPublicacio.getText().trim()).intValue();

                        if (campAnyPublicacio <= 0) {
                            throw new NumberFormatException();
                        }
                    }
                    anyPublicacio.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                } catch (NumberFormatException | ParseException pe) {
                    anyPublicacio.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    setExcepcio(new DAOException(2));
                    anyPublicacio.setText("");
                    anyPublicacio.requestFocus();
                }
            }
        });
        numVentes.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (!numVentes.getText().isBlank()) {
                        Integer.valueOf(numVentes.getText().trim());

                        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault()); // Creem un número que entén la cultura que utilitza l'aplicació
                        int campNumVentes = num.parse(numVentes.getText().trim()).intValue();

                        if (campNumVentes <= 0) {
                            throw new NumberFormatException();
                        }
                    }
                    numVentes.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                } catch (NumberFormatException | ParseException pe) {
                    numVentes.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    setExcepcio(new DAOException(2));
                    numVentes.setText("");
                    numVentes.requestFocus();
                }
            }
        });
        numPagines.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                try {
                    if (!numPagines.getText().isBlank()) {
                        Integer.valueOf(numPagines.getText().trim());

                        NumberFormat num = NumberFormat.getNumberInstance(Locale.getDefault()); // Creem un número que entén la cultura que utilitza l'aplicació
                        int campNumPagines = num.parse(numPagines.getText().trim()).intValue();

                        if (campNumPagines <= 0) {
                            throw new NumberFormatException();
                        }
                    }

                    numPagines.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                } catch (NumberFormatException | ParseException pe) {
                    numPagines.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
                    setExcepcio(new DAOException(2));
                    numPagines.setText("");
                    numPagines.requestFocus();
                }
            }
        });
    }

    public void canviarVora(JTextField camp) {
        if (camp.getText().isBlank()) {
            camp.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        }
        if (!camp.getText().isBlank()) {
            camp.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
    }

    public void borrarCamp() {
        vista.getTitol().setText("");
        vista.getAutor().setText("Si no saps l'autor, pots deixar aquest camp en blanc");
        vista.getAnyPublicacio().setText("");
        vista.getEditorial().setText("");
        vista.getGenere().setText("");
        vista.getPreu().setText("");
        vista.getNumVentes().setText("");
        vista.getNumPagines().setText("");
        vista.getConteDibuixos().setSelected(false);
        vista.getEstaEnStock().setSelected(false);
    }

    private void lligarVistaModel(){
        try {
            setModelTaulaLlibre(this.modelComponentsVisuals.getModel(), this.dadesLlibre.getAll());
        }catch (DAOException e){
            System.out.println(e.getMessage());
            setExcepcio(e);
        }
        //Obtinc els objectes de la vista
        JTable taula = vista.getTaula();
        taula.setModel(this.modelComponentsVisuals.getModel());
        //Forcem a que només se pugue seleccionar una fila de la taula
        taula.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void setModelTaulaLlibre(DefaultTableModel modelTaulaLlibre, List<Llibre> all) throws DAOException {
        for (Llibre llibre : all) {
            modelTaulaLlibre.addRow(new Object[]{llibre.getTitol(), llibre.getAutor(),llibre.getAnyPublicacio(),llibre.getEditorial(),llibre.getGenere(),llibre.getPreu(),llibre.getNumVentes(),llibre.getNumPagines(),llibre.isConteDibuixos(),llibre.isEstaEnStock(), llibre.getIdBD(llibre.getTitol()), llibre});
        }
    }


}


