package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Theme {
    static List<WordsAttributes> WA;
    static int KEY_WORDS = 0;
    static int OTHER_WORDS = 1;
    static int DEF_WORDS = 2;

    static SimpleAttributeSet highlightAttributes;
    
    static Font defaultFont = new Font("Verdana", Font.PLAIN,18);
    static Color Caret = new Color(139, 12, 84);
    
    static Color bgTextArea = new Color(106, 37, 135);
    static Color bgMenu = new Color(39, 168, 121);
    static Color bgMenuItem = new Color(39, 168, 121);

    Theme(){
//        keyWords = new ArrayList<>();
//        keyWords.add("private");
//        keyWords.add("cat");
//
//        otherWords = new ArrayList<>();
//        otherWords.add("type");
//        otherWords.add("ty");
//
//        keyWordsAttributes = new SimpleAttributeSet();
//        StyleConstants.setBold(keyWordsAttributes, true);
//        StyleConstants.setItalic(keyWordsAttributes, true);
//        StyleConstants.setForeground(keyWordsAttributes, new Color(13, 80, 5));
//        StyleConstants.setBackground(keyWordsAttributes, new Color(217, 215, 164));
//
//        otherWordsAttributes = new SimpleAttributeSet();
//        StyleConstants.setBold(otherWordsAttributes, true);
//        StyleConstants.setItalic(otherWordsAttributes, true);
//        StyleConstants.setForeground(otherWordsAttributes, new Color(31, 177, 25));
//        StyleConstants.setBackground(otherWordsAttributes, new Color(49, 7, 45));
//
//        defAttributes = new SimpleAttributeSet();
//        highlightAttributes = new SimpleAttributeSet();
//        StyleConstants.setBackground(highlightAttributes, new Color(161,227,206));
    }

    private static Color getColor(String str){
        String[] rgb = str.split(",");
        int r = Integer.getInteger(rgb[0]);
        int g = Integer.getInteger(rgb[1]);
        int b = Integer.getInteger(rgb[2]);

        return new Color(r,g,b);
    }
    private boolean getBoolean(String str){
        if(str.matches("true")) return true;
        return false;
    }

    static void updateThemeXML(String name){
        try {
            File inputFile = new File("themes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nlThemes = doc.getElementsByTagName("theme");
            Node nTheme = nlThemes.item(0);
            Element eTheme = (Element) nTheme;

            for (int tmpThemes = 0; tmpThemes < nlThemes.getLength(); tmpThemes++) {
                if (nTheme.getNodeType() == Node.ELEMENT_NODE) {
                    eTheme = (Element) nTheme;
                    if (eTheme.getAttribute("name").matches(name)) {
                        break;
                    }
                }
                nTheme = nlThemes.item(tmpThemes);
            }

            NodeList wordsAttributes = eTheme.getElementsByTagName("WordsAttributes");
            for(int tmp = 0; tmp < wordsAttributes.getLength(); tmp++){
                Node nWA = wordsAttributes.item(tmp);
                if (nWA.getNodeType() == Node.ELEMENT_NODE) {
                    Element eWA = (Element) nWA;
                            Color color;
                            color = getColor(eWA.getElementsByTagName("bg").item(0).getTextContent());
                            StyleConstants.setBackground(keyWordsAttributes, color);
                            color = getColor(eWA.getElementsByTagName("fg").item(0).getTextContent());
                            //StyleConstants.setBackground(keyWordsAttributes, color);
                            color = getColor(eWA.getElementsByTagName("fg").item(0).getTextContent());
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
