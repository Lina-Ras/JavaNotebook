package com.company;

import javax.swing.*;
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

        edit.add(findEdit);
        edit.add(replaceEdit);

        findEdit.addActionListener(this);
        replaceEdit.addActionListener(this);

        findEdit.setActionCommand("findEdit");
        replaceEdit.setActionCommand("replaceEdit");

        menu.add(edit);
    }

    MainWindow() {
        w = new JFrame("notebook");
        w.setSize(800, 600);
        w.setLocationRelativeTo(null);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new TextArea(w);

        JMenuBar menu = new JMenuBar();
        createFileMenu(menu);
        createEditMenu(menu);
        w.setJMenuBar(menu);

        w.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        switch (command){
            case "newFile" -> textArea.newFile();
            case "openFile" -> textArea.openFile();
            case "saveFile" -> textArea.saveFile();
            case "saveAsFile" -> textArea.saveAsFile();
            case "findEdit" -> {
                Dialog dialog = new Dialog(w, Dialog.FIND_DIALOG);
            }
            case "replaceEdit" -> {
                Dialog dialog = new Dialog(w, Dialog.REPLACE_DIALOG);
            }
        }
    }
}
