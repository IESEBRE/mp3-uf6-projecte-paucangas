package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Vista extends JFrame {
    private JPanel panelPrincipal;
    private JTabbedPane tabbedPaneRandom;
    private JTable taulaRandom;
    private JTable informacioAdicionalTable;
    private JTable taula;
    private JButton borrarButton;
    private JButton modificarButton;
    private JButton insertarButton;
    private JTextField titol;
    private JTextField autor;
    private JTextField anyPublicacio;
    private JTextField editorial;
    private JTextField genere;
    private JTextField preu;
    private JTextField numVentes;
    private JTextField numPagines;
    private JCheckBox conteDibuixos;
    private JCheckBox estaEnStock;
    private JButton buida;
    private JButton desaButton;
    private JButton informacioAdicionalButton;
    private JScrollPane scrollPane1;

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public void setPanelPrincipal(JPanel panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
    }

    public JTabbedPane getTabbedPaneRandom() {
        return tabbedPaneRandom;
    }

    public void setTabbedPaneRandom(JTabbedPane tabbedPaneRandom) {
        this.tabbedPaneRandom = tabbedPaneRandom;
    }

    public JTable getTaulaRandom() {
        return taulaRandom;
    }

    public void setTaulaRandom(JTable taulaRandom) {
        this.taulaRandom = taulaRandom;
    }


    public JTable getTaula() {
        return taula;
    }

    public void setTaula(JTable taula) {
        this.taula = taula;
    }

    public JButton getBorrarButton() {
        return borrarButton;
    }

    public void setBorrarButton(JButton borrarButton) {
        this.borrarButton = borrarButton;
    }

    public JButton getModificarButton() {
        return modificarButton;
    }

    public void setModificarButton(JButton modificarButton) {
        this.modificarButton = modificarButton;
    }

    public JButton getInsertarButton() {
        return insertarButton;
    }

    public void setInsertarButton(JButton insertarButton) {
        this.insertarButton = insertarButton;
    }

    public JTextField getTitol() {
        return titol;
    }

    public void setTitol(JTextField titol) {
        this.titol = titol;
    }

    public JTextField getAutor() {
        return autor;
    }

    public void setAutor(JTextField autor) {
        this.autor = autor;
    }

    public JTextField getAnyPublicacio() {
        return anyPublicacio;
    }

    public void setAnyPublicacio(JTextField anyPublicacio) {
        this.anyPublicacio = anyPublicacio;
    }

    public JTextField getEditorial() {
        return editorial;
    }

    public void setEditorial(JTextField editorial) {
        this.editorial = editorial;
    }

    public JTextField getGenere() {
        return genere;
    }

    public void setGenere(JTextField genere) {
        this.genere = genere;
    }

    public JTextField getPreu() {
        return preu;
    }

    public void setPreu(JTextField preu) {
        this.preu = preu;
    }

    public JTextField getNumVentes() {
        return numVentes;
    }

    public void setNumVentes(JTextField numVentes) {
        this.numVentes = numVentes;
    }

    public JTextField getNumPagines() {
        return numPagines;
    }

    public void setNumPagines(JTextField numPagines) {
        this.numPagines = numPagines;
    }

    public JCheckBox getConteDibuixos() {
        return conteDibuixos;
    }

    public void setConteDibuixos(JCheckBox conteDibuixos) {
        this.conteDibuixos = conteDibuixos;
    }

    public JCheckBox getEstaEnStock() {
        return estaEnStock;
    }

    public void setEstaEnStock(JCheckBox estaEnStock) {
        this.estaEnStock = estaEnStock;
    }

    public JButton getBuida() {
        return buida;
    }

    public void setBuida(JButton buida) {
        this.buida = buida;
    }

    public JButton getDesaButton() {
        return desaButton;
    }

    public void setDesaButton(JButton desaButton) {
        this.desaButton = desaButton;
    }

    public JButton getInformacioAdicionalButton() {
        return informacioAdicionalButton;
    }

    public void setInformacioAdicionalButton(JButton informacioAdicionalButton) {
        this.informacioAdicionalButton = informacioAdicionalButton;
    }

    public JTable getInformacioAdicionalTable() {
        return informacioAdicionalTable;
    }

    public void setInformacioAdicionalTable(JTable informacioAdicionalTable) {
        this.informacioAdicionalTable = informacioAdicionalTable;
    }

    public Vista(){
//Per poder vore la finestra
        this.setContentPane(panelPrincipal);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        autor.setText("Si no saps l'autor, pots deixar aquest camp en blanc");
        autor.setSelectionStart(0);
        autor.setSelectionEnd(autor.getText().length());
        autor.requestFocus();
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
        scrollPane1 = new JScrollPane();
        taula = new JTable();
        tabbedPaneRandom = new JTabbedPane();
        taula.setModel(new DefaultTableModel());
        scrollPane1.setViewportView(taula);
    }
}
