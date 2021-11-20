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

        file.add(newFile);
        file.add(openFile);
        file.add(saveFile);
        file.add(saveAsFile);

        addListenersToMenu(file);

        menu.add(file);
    }
    private void createEditMenu(JMenuBar menu){
        JMenu edit = new JMenu("Edit");

        JMenuItem findEdit = new JMenuItem("Find");
        JMenuItem replaceEdit = new JMenuItem("Find and Replace");

        edit.add(findEdit);
        edit.add(replaceEdit);

        addListenersToMenu(edit);
        menu.add(edit);
    }
    private void createThemeMenu(JMenuBar menu){
        JMenu themes = new JMenu("Select Theme");

        JMenuItem firstTheme = new JMenuItem("Spring");
        JMenuItem secondTheme = new JMenuItem("Autumn");

        themes.add(firstTheme);
        themes.add(secondTheme);

        addListenersToMenu(themes);

        menu.add(themes);
    }
    private void updateTheme(){
        JMenuBar menubar = w.getJMenuBar();
        for(int i=0; i<menubar.getMenuCount(); ++i){
            JMenu menu = menubar.getMenu(i);
            for(int j=0; j<menu.getItemCount(); ++j){
                menu.getItem(j).setBackground(Theme.bgMenuItem);
            }
        }
        menubar.setBackground(Theme.bgMenu);
    }
    private void addListenersToMenu(JMenu menu){
        for(int j=0; j<menu.getItemCount(); ++j){
            JMenuItem item = menu.getItem(j);
            item.addActionListener(this);
            item.setActionCommand(item.getText());
        }
    }

    MainWindow() throws BadLocationException {
        w = new JFrame("notebook");
        w.setSize(800, 600);
        w.setLocationRelativeTo(null);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Theme.updateThemeXML("Spring");

        textArea = new TextArea();
        w.getContentPane().add(new JScrollPane(textArea));

        JMenuBar menu = new JMenuBar();
        createFileMenu(menu);
        createEditMenu(menu);
        createThemeMenu(menu);
        w.setJMenuBar(menu);

        updateTheme();
        w.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        switch (command){
            case "New file": {
                try {
                    textArea.newFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
                break;
            }
            case "Open file":{
                try {
                    textArea.openFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
                break;
            }
            case "Save file": {
                try {
                    textArea.saveFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
                break;
            }
            case "Save file as...": {
                try {
                    textArea.saveAsFile();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
                break;
            }
            case "Find": {
                Dialog dialog = new Dialog(w, Dialog.FIND_DIALOG);
                break;
            }
            case "Find and Replace":{
                Dialog dialog = new Dialog(w, Dialog.REPLACE_DIALOG);
                break;
            }
            case "Spring": {
            }
            case "Autumn": {
                Theme.updateThemeXML(command);
                updateTheme();
                try {
                    textArea.updateTheme();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
                break;
            }
        }
    }
}
