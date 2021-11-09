package com.company;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Font;
import java.io.*;

public class TextArea extends JTextArea{
    private List<String> txt;
    private String path;
    private void save(){
        File file = new File(path);
        BufferedWriter output = null;

        try {
            output = new BufferedWriter(new FileWriter(file)) ;
            write(output);
        }
        catch (IOException ex){ex.printStackTrace();}

        String tmp = getText();
        String[] lines = tmp.split("\n");
        txt = Arrays.asList(lines);
    }

    TextArea(JFrame frame){
        txt = new ArrayList<>();
        path = new String();
        setFont(new Font("Verdana", Font.PLAIN,15));
        frame.add(this);
    }
    void newFile(){
        txt = new ArrayList<>();
        setText(null);
        path = "";
    }
    void openFile(){
        JFileChooser Open = new JFileChooser();

        Open.showOpenDialog(null);
        path = Open.getSelectedFile().toString();
        File file = new File(path);
        BufferedReader input;
        newFile();

        try {
            input = new BufferedReader(new FileReader(file));
            String tmp;
            while ((tmp = input.readLine()) != null){
                txt.add(tmp);
                append(tmp + "\n");
            }
        }
        catch (IOException ex){ex.printStackTrace();}
    }
    void saveFile(){
        if(path.isEmpty()) saveAsFile();
            else save();
    }
    void saveAsFile(){
        JFileChooser Save = new JFileChooser();

        Save.showSaveDialog(null);
        path = Save.getSelectedFile().toString();
        if(path.lastIndexOf(".txt") != path.length()-4){
            path += ".txt";
        }
        save();
    }

    void find(String fword){
        setText(fword);
    }
    void replace(String fword, String rword){
        setText(fword + rword);
    }
}
