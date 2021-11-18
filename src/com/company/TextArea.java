package com.company;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.String;
import java.io.*;

public class TextArea extends JTextPane{
    private List<String> txt;
    private String path = "";
    private void save() throws BadLocationException {
        File file = new File(path);
        BufferedWriter output;

        try {
            output = new BufferedWriter(new FileWriter(file)) ;
            write(output);
        }
        catch (IOException ex){ex.printStackTrace();}

        String tmp = document.getText(0, document.getLength());
        String[] lines = tmp.split("\n");
        txt = Arrays.asList(lines);
    }
    private void find(String word, SimpleAttributeSet attributeSet) throws BadLocationException {
        int r = 0;
        String str = document.getText(0, document.getLength());

        int l = str.indexOf(word);
        while(l != -1 && r!=-1){
            r = l + word.length();
            document.setCharacterAttributes(l, r-l, attributeSet, false);
            l = str.indexOf(word,r);
        }
    }
    private void findInLine(String word, String line, int start, SimpleAttributeSet attributeSet) throws BadLocationException {
        int r = 0;
        int l = line.indexOf(word);
        while(l != -1 && r!=-1){
            r = l + word.length();
            document.setCharacterAttributes(start + l, r - l, attributeSet, true);
            l = line.indexOf(word,r);
        }
    }
    private void syntaxLineHighlight() throws BadLocationException {
        String txt = document.getText(0, document.getLength());
        if(txt.isEmpty()){
            return;
        }
        int start = getCaretPosition();
        while(txt.charAt(start - 1) != '\n'){
            --start;
            if(start == 0) break;
        }
        int end = getCaretPosition();
        while(txt.charAt(end-1) != '\n'){
            if(end == document.getLength()) break;
            ++end;
        }
        document.setCharacterAttributes(start, end-start+1, Theme.WA.get(Theme.DEF_WORDS), true);
        String line = txt.substring(start, end);
        for(String word : Theme.WA.get(Theme.OTHER_WORDS).words){
            findInLine(word, line,start,Theme.WA.get(Theme.OTHER_WORDS));
        }
        for(String word : Theme.WA.get(Theme.KEY_WORDS).words){
            findInLine(word, line,start,Theme.WA.get(Theme.KEY_WORDS));
        }
    }
    private void syntaxHighlight() throws BadLocationException {
        document.setCharacterAttributes(0, document.getLength(), Theme.WA.get(Theme.DEF_WORDS), true);
        for(String word : Theme.WA.get(Theme.OTHER_WORDS).words){
            find(word, Theme.WA.get(Theme.OTHER_WORDS));
        }
        for(String word : Theme.WA.get(Theme.KEY_WORDS).words){
            find(word, Theme.WA.get(Theme.KEY_WORDS));
        }
    }
    private final DefaultStyledDocument document;
    private void updateTheme(){}

    TextArea(){
        document = new DefaultStyledDocument();
        setDocument(document);
        txt = new ArrayList<>();
        setBackground(Theme.bgTextArea);
        setFont(Theme.defaultFont);
        setCaretColor(Theme.caret);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyTyped(e);
                try {
                    syntaxLineHighlight();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }
            }
        });

    }
    void newFile() throws BadLocationException {
        txt = new ArrayList<>();
        path = "";
        document.remove(0, document.getLength());
    }
    void openFile() throws BadLocationException {
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
                document.insertString(document.getLength(), tmp+"\n", null);
            }
            syntaxHighlight();
        }
        catch (IOException | BadLocationException ex){ex.printStackTrace();}
    }
    void saveFile() throws BadLocationException {
        if(path.isEmpty()) saveAsFile();
            else save();
    }
    void saveAsFile() throws BadLocationException {
        JFileChooser Save = new JFileChooser();

        Save.showSaveDialog(null);
        path = Save.getSelectedFile().toString();
        if(path.lastIndexOf(".txt") != path.length()-4){
            path += ".txt";
        }
        save();
    }

    void find(String fword) throws BadLocationException {
        find(fword, Theme.highlightAttributes);
    }
    void deleteHighlight() throws BadLocationException {
        syntaxHighlight();
    }
    void replace(String fword, String rword) throws BadLocationException {

        int r = 0;
        String str = document.getText(0, document.getLength());

        int l = str.indexOf(fword);
        while(l != -1 && r!=-1){
            r = l + fword.length();
            document.remove(l, fword.length());
            r = l +rword.length();
            document.insertString(l, rword, null);
            str = document.getText(0, document.getLength());
            l = str.indexOf(fword,r);
        }
        syntaxHighlight();
    }
}
