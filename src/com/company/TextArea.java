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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextArea extends JTextPane{
    private List<String> txt;
    private String path = "";
    private final DefaultStyledDocument document;

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

    private void find(String word, String line, int start, SimpleAttributeSet attributeSet) throws BadLocationException {
        Pattern pattern = Pattern.compile(word);
        Matcher matcher = pattern.matcher(line);

        while(matcher.find()){
            document.setCharacterAttributes(start + matcher.start(), matcher.end() - matcher.start(), attributeSet, false);
        }
    }
    private void syntaxLineHighlight() throws BadLocationException {
        String txt = document.getText(0, document.getLength());
        if(txt.isEmpty()){
            return;
        }
        int start = getCaretPosition();
        int counter = 0;
        while (start != 0 && (txt.charAt(start - 1) != '\n' || counter == 0)) {
            if (txt.charAt(start - 1) != '\n') counter = 1;
            --start;
        }
        int end = getCaretPosition();
        counter = 0;
        while(end!= document.getLength() && (txt.charAt(end-1) != '\n' || counter==0)){
            ++end;
            if(txt.charAt(end-1) != '\n') counter = 1;
        }

        document.setCharacterAttributes(start, end-start+1, Theme.WA.get(Theme.DEF_WORDS), true);
        String line = txt.substring(start, end);
        for(String word : Theme.WA.get(Theme.OTHER_WORDS).words){
            word = "\\b"+word+"\\b";
            find(word, line,start,Theme.WA.get(Theme.OTHER_WORDS));
        }
        for(String word : Theme.WA.get(Theme.KEY_WORDS).words){
            word = "\\b"+word+"\\b";
            find(word, line,start,Theme.WA.get(Theme.KEY_WORDS));
        }
    }
    private void syntaxHighlight() throws BadLocationException {
        document.setCharacterAttributes(0, document.getLength(), Theme.WA.get(Theme.DEF_WORDS), true);
        for(String word : Theme.WA.get(Theme.OTHER_WORDS).words){
            word = "\\b"+word+"\\b";
            find(word, document.getText(0, document.getLength()), 0 ,Theme.WA.get(Theme.OTHER_WORDS));
        }
        for(String word : Theme.WA.get(Theme.KEY_WORDS).words){
            word = "\\b"+word+"\\b";
            find(word, document.getText(0, document.getLength()), 0,Theme.WA.get(Theme.KEY_WORDS));
        }
    }

    TextArea() throws BadLocationException {
        document = new DefaultStyledDocument();
        setDocument(document);
        txt = new ArrayList<>();
        updateTheme();

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
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown()) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_V ->{
                        try {
                            syntaxHighlight();
                        } catch (BadLocationException badLocationException) {
                            badLocationException.printStackTrace();
                        }
                    }
                    case KeyEvent.VK_O ->{
                        try {
                            openFile();
                        } catch (BadLocationException badLocationException) {
                            badLocationException.printStackTrace();
                        }
                    }
                    case KeyEvent.VK_S ->{
                        try {
                            saveFile();
                        } catch (BadLocationException badLocationException) {
                            badLocationException.printStackTrace();
                        }
                    }
                    case KeyEvent.VK_N ->{
                        try {
                            newFile();
                        } catch (BadLocationException badLocationException) {
                            badLocationException.printStackTrace();
                        }
                    }
                }}
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
        deleteHighlight();
        find(fword, document.getText(0, document.getLength()), 0,Theme.highlightAttributes);
    }
    void deleteHighlight() throws BadLocationException {
        syntaxHighlight();
    }
    void replace(String fword, String rword) throws BadLocationException {
        String line = document.getText(0, document.getLength());

        Pattern pattern = Pattern.compile(fword);
        Matcher matcher = pattern.matcher(line);
        String str = matcher.replaceAll(rword);
        document.remove(0, document.getLength());
        document.insertString(0, str, null);

        syntaxHighlight();
    }

    void updateTheme() throws BadLocationException {
        setBackground(Theme.bgTextArea);
        setFont(Theme.defaultFont);
        setCaretColor(Theme.caret);
        syntaxHighlight();
    }
}
