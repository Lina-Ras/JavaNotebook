package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dialog {
    private JDialog dialog;
    private int type;
    private int width;
    private int height;

    private JButton button;
    private JTextField textFind;
    private JTextField textReplace;


    final static int FIND_DIALOG = 1;
    final static int REPLACE_DIALOG = 2;

    private void loadFindDialog(){
        dialog.setName("Find");


        JLabel findLabel = new JLabel("Find: ");
        findLabel.setBounds((int)(width*0.1), (int)(height*0.0),(int)(width*0.8),(int)(height*0.15));
        dialog.add(findLabel);

        textFind = new JTextField();
        textFind.setBounds((int)(width*0.1),(int)(height*0.15),(int)(width*0.8),(int)(height*0.15));
        dialog.add(textFind);

        button = new JButton("find");
        button.setActionCommand("find");
        button.setBounds((int)(width*0.4), (int)(height*0.65), (int)(width*0.2), (int)(height*0.15));
        dialog.add(button);

        dialog.setLayout(null);
        dialog.setVisible(true);

    }
    private void loadReplaceDialog(){
        dialog.setName("Find and replace");

        JLabel findLabel = new JLabel("Find: ");
        findLabel.setBounds((int)(width*0.1), (int)(height*0.0),(int)(width*0.8),(int)(height*0.15));
        dialog.add(findLabel);

        textFind = new JTextField();
        textFind.setBounds((int)(width*0.1),(int)(height*0.15),(int)(width*0.8),(int)(height*0.15));
        dialog.add(textFind);

        JLabel replaceLabel = new JLabel("Replace with: ");
        replaceLabel.setBounds((int)(width*0.1), (int)(height*0.3),(int)(width*0.8),(int)(height*0.15));
        dialog.add(replaceLabel);

        textReplace = new JTextField();
        textReplace.setBounds((int)(width*0.1),(int)(height*0.45),(int)(width*0.8),(int)(height*0.15));
        dialog.add(textReplace);

        button = new JButton("replace");
        button.setActionCommand("replace");
        button.setBounds((int)(width*0.4), (int)(height*0.65), (int)(width*0.2), (int)(height*0.15));
        dialog.add(button);


        dialog.setLayout(null);
        dialog.setVisible(true);

    }

    public Dialog(JFrame w, int typet, int wt, int ht){
        dialog = new JDialog(w);

        width = wt;
        height = ht;
        dialog.setSize(width,height);

        type = typet;
        switch (type) {
            case FIND_DIALOG -> {
                loadFindDialog();
            }
            case REPLACE_DIALOG -> {
                loadReplaceDialog();
            }
        }
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (e.getActionCommand()){
                    case "find" -> {
                        if(textFind.getText() != null)
                            MainWindow.textArea.find(textFind.getText());
                    }
                    case "replace" ->{
                        if(textFind.getText() != null)
                            MainWindow.textArea.replace(textFind.getText(), textReplace.getText());
                    }
                }
            }
        });
    }

    public Dialog(JFrame w, int typet){ new Dialog(w, typet, 400, 200); }
}
