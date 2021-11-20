package com.company;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.*;


class MainWindow implements ActionListener {
    static JFrame w;
    static TextArea textArea;

    private void createFileMenu(JMenuBar menu){
        JMenu file = new JMenu("File");

        JMenuItem newFile = new JMenuItem("New file");
        JMenuItem openFile = new JMenuItem("Open file");
        JMenuItem saveFile = new JMenuItem("Save file");
        JMenuItem saveAsFile = new JMenuItem("Save file as...");

        newFile.setBackground(Theme.bgMenuItem);
        openFile.setBackground(Theme.bgMenuItem);
        saveFile.setBackground(Theme.bgMenuItem);
        saveAsFile.setBackground(Theme.bgMenuItem);

        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAsFile);

        newFile.addActionListener(this);
        openFile.addActionListener(this);
        saveFile.addActionListener(this);
        saveAsFile.addActionListener(this);

        newFile.setActionCommand("newFile");
        openFile.setActionCommand("openFile");
        saveFile.setActionCommand("saveFile");
        saveAsFile.setActionCommand("saveAsFile");

        menu.add(file);
    }
    private void createEditMenu(JMenuBar menu){
        JMenu edit = new JMenu("Edit");

        JMenuItem findEdit = new JMenuItem("Find");
        JMenuItem replaceEdit = new JMenuItem("Find and Replace");

        findEdit.setBackground(Theme.bgMenuItem);
        replaceEdit.setBackground(Theme.bgMenuItem);

        edit.add(findEdit);
        edit.add(replaceEdit);

        findEdit.addActionListener(this);
        replaceEdit.addActionListener(this);

        findEdit.setActionCommand("findEdit");
        replaceEdit.setActionCommand("replaceEdit");

        menu.add(edit);
    }
    private void createThemeMenu(JMenuBar menu){
        JMenu edit = new JMenu("Select Theme");

        JMenuItem firstTheme = new JMenuItem("Spring");
        JMenuItem secondTheme = new JMenuItem("Autumn");

        firstTheme.setBackground(Theme.bgMenuItem);
        secondTheme.setBackground(Theme.bgMenuItem);

        edit.add(firstTheme);
        edit.add(secondTheme);

        firstTheme.addActionListener(this);
        secondTheme.addActionListener(this);

        firstTheme.setActionCommand(firstTheme.getText());
        secondTheme.setActionCommand(secondTheme.getText());

        menu.add(edit);
    }

    MainWindow() throws BadLocationException {
        w = new JFrame("notebook");
        w.setSize(800, 600);
        w.setLocationRelativeTo(null);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Theme theme = new Theme();
        Theme.updateThemeXML("Autumn");

        textArea = new TextArea();
        w.getContentPane().add(new JScrollPane(textArea));

        JMenuBar menu = new JMenuBar();
        createFileMenu(menu);
        createEditMenu(menu);
        createThemeMenu(menu);
        menu.setBackground(Theme.bgMenu);
        w.setJMenuBar(menu);

        w.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        switch (command){
            case "newFile" -> {
                try {
                    textArea.newFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
            case "openFile" -> {
                try {
                    textArea.openFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
            case "saveFile" -> {
                try {
                    textArea.saveFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
            case "saveAsFile" -> {
                try {
                    textArea.saveAsFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
            case "findEdit" -> {
                Dialog dialog = new Dialog(w, Dialog.FIND_DIALOG);
            }
            case "replaceEdit" -> {
                Dialog dialog = new Dialog(w, Dialog.REPLACE_DIALOG);
            }
            case "Spring" -> {
                Theme.updateThemeXML("Spring");
                try {
                    textArea.updateTheme();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
            case "Autumn" -> {
                Theme.updateThemeXML("Autumn");
                try {
                    textArea.updateTheme();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
        }
    }
}
