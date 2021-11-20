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
import java.util.Arrays;
import java.util.List;

public class Theme {
    static String name;
    static List<WordsAttributes> WA;
    static int KEY_WORDS = 0;
    static int OTHER_WORDS = 1;
    static int DEF_WORDS = 2;

    static SimpleAttributeSet highlightAttributes;
    
    static Font defaultFont;
    static Color caret;
    static Color bgTextArea;
    static Color bgMenu;
    static Color bgMenuItem;

    Theme() {
        WA = new ArrayList<WordsAttributes>();
        for(int i = 0; i <3; ++i){
            WA.add(new WordsAttributes());
        }

        highlightAttributes = new SimpleAttributeSet();
    }

    private static Color getColor(String str){
        if(str.matches("none")){
            return bgTextArea;
        }
        String[] rgb = str.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);

        return new Color(r,g,b);
    }
    private static boolean getBoolean(String str){
        return str.matches("true");
    }
    private static int getFontType(String str){
        switch(str){
            case("Font.BOLD") -> {return Font.BOLD;}
            case("Font.ITALIC") -> {return Font.ITALIC;}
            default -> {return Font.PLAIN;}
        }
    }

    static void updateThemeXML(String themeName){
        WA = new ArrayList<WordsAttributes>();
        for(int i = 0; i <3; ++i){
            WA.add(new WordsAttributes());
        }
        try {
            File inputFile = new File("src/com/company/themes.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nlThemes = doc.getElementsByTagName("theme");
            Node nTheme = nlThemes.item(0);
            Element eTheme = (Element) nTheme;

            for (int tmpThemes = 0; tmpThemes < nlThemes.getLength(); tmpThemes++) {
                nTheme = nlThemes.item(tmpThemes);
                if (nTheme.getNodeType() == Node.ELEMENT_NODE) {
                    eTheme = (Element) nTheme;
                    if (eTheme.getAttribute("name").matches(themeName)) {
                        name = themeName;
                        break;
                    }
                }
            }
            Node nColors = eTheme.getElementsByTagName("colors").item(0);
            if(nColors.getNodeType() == Node.ELEMENT_NODE){
                Element eColors = (Element) nColors;
                caret = getColor(eColors.getElementsByTagName("caret").item(0).getTextContent());
                bgTextArea = getColor(eColors.getElementsByTagName("bgTextArea").item(0).getTextContent());
                bgMenu = getColor(eColors.getElementsByTagName("bgMenu").item(0).getTextContent());
                bgMenuItem = getColor(eColors.getElementsByTagName("bgMenuItem").item(0).getTextContent());
                Color color = getColor(eColors.getElementsByTagName("highlight").item(0).getTextContent());
                StyleConstants.setBackground(highlightAttributes, color);
            }

            NodeList wordsAttributes = eTheme.getElementsByTagName("WordsAttributes");
            for(int tmp = 0; tmp < wordsAttributes.getLength(); tmp++) {
                Node nWA = wordsAttributes.item(tmp);
                if (nWA.getNodeType() == Node.ELEMENT_NODE) {
                    Element eWA = (Element) nWA;
                    Color color;
                    color = getColor(eWA.getElementsByTagName("bg").item(0).getTextContent());
                    StyleConstants.setBackground(WA.get(tmp), color);
                    color = getColor(eWA.getElementsByTagName("fg").item(0).getTextContent());
                    StyleConstants.setForeground(WA.get(tmp), color);
                    boolean bool;
                    bool = getBoolean(eWA.getElementsByTagName("bold").item(0).getTextContent());
                    StyleConstants.setBold(WA.get(tmp), bool);
                    bool = getBoolean(eWA.getElementsByTagName("italic").item(0).getTextContent());
                    StyleConstants.setItalic(WA.get(tmp), bool);

                    if (!eWA.getAttribute("type").matches("default")) {
                        String[] strTmp = eWA.getElementsByTagName("words").item(0).getTextContent().split(",");
                        WA.get(tmp).words = Arrays.asList(strTmp);
                    }
                }
            }

            Node nFont =  eTheme.getElementsByTagName("defaultFont").item(0);
            if(nFont.getNodeType() == Node.ELEMENT_NODE){
                Element eFont = (Element) nFont;
                String fontName = eFont.getElementsByTagName("name").item(0).getTextContent();
                int fontType = getFontType(eFont.getElementsByTagName("type").item(0).getTextContent());
                int fontSize = Integer.parseInt(eFont.getElementsByTagName("size").item(0).getTextContent());
                defaultFont = new Font(fontName, fontType, fontSize);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
