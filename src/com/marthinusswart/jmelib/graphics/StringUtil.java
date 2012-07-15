package com.marthinusswart.jmelib.graphics;

import java.util.*;
import javax.microedition.lcdui.Font;

public class StringUtil {

    public static String getURL(String string) {

        if (string.endsWith("!")) {
            return string.substring(0, string.length() - 1);
        } else {
            return string;
        }
    }

    public static String[] split(String original, String separator) {
        Vector nodes = new Vector();

        int index = original.indexOf(separator);
        while (index >= 0) {
            nodes.addElement(original.substring(0, index));
            original = original.substring(index + separator.length());
            index = original.indexOf(separator);
        }
        nodes.addElement(original);

        String[] result = new String[nodes.size()];
        if (nodes.size() > 0) {
            for (int loop = 0; loop < nodes.size(); loop++) {
                result[loop] = (String) nodes.elementAt(loop);
            }
        }
        return result;
    }

    public static String replace(String s, String f, String r) {
        if (s == null) {
            return s;
        }
        if (f == null) {
            return s;
        }
        if (r == null) {
            r = "";
        }
        int index01 = s.indexOf(f);
        while (index01 != -1) {
            s = s.substring(0, index01) + r + s.substring(index01 + f.length());
            index01 += r.length();
            index01 = s.indexOf(f, index01);
        }
        return s;
    }

    public static String removeHtml(String text) {
        try {
            int idx = text.indexOf("<");
            if (idx == -1) {
                text = decodeEntities(text);
                return text;
            }

            String plainText = "";
            String htmlText = text;
            int htmlStartIndex = htmlText.indexOf("<", 0);
            if (htmlStartIndex == -1) {
                return text;
            }
            htmlText = StringUtil.replace(htmlText, "</p>", "\r\n");
            htmlText = StringUtil.replace(htmlText, "<br/>", "\r\n");
            htmlText = StringUtil.replace(htmlText, "<br>", "\r\n");
            while (htmlStartIndex >= 0) {
                plainText += htmlText.substring(0, htmlStartIndex);
                int htmlEndIndex = htmlText.indexOf(">", htmlStartIndex);
                htmlText = htmlText.substring(htmlEndIndex + 1);
                htmlStartIndex = htmlText.indexOf("<", 0);
            }
            plainText = plainText.trim();
            plainText = decodeEntities(plainText);
            return plainText;
        } catch (Exception e) {
            return text;
        }
    }

    private static String decodeEntities(String html) {
        String result = StringUtil.replace(html, "&lt;", "<");
        result = StringUtil.replace(result, "&gt;", ">");
        result = StringUtil.replace(result, "&nbsp;", " ");
        result = StringUtil.replace(result, "&amp;", "&");
        result = StringUtil.replace(result, "&auml;", "ä");
        result = StringUtil.replace(result, "&ouml;", "ö");
        result = StringUtil.replace(result, "&quot;", "'");
        result = StringUtil.replace(result, "&#xd;", "\r");
        return result;
    }

    public static String[] formatMessage(String[] message, int width, Font font) {
        Vector result = new Vector(message.length);
        for (int i = 0; i < message.length; i++) {
            if (font.stringWidth(message[i]) <= width) {
                result.addElement(message[i]);
            } else {
                String[] splitUp = StringUtil.chopStrings(message[i], " ", font, width);
                for (int j = 0; j < splitUp.length; j++) {
                    result.addElement(splitUp[j]);
                }
            }
        }

        String[] finalResult = new String[result.size()];
        for (int i = 0; i < finalResult.length; i++) {
            finalResult[i] = (String) result.elementAt(i);
        }
        return finalResult;
    }

    public static String[] chopStrings(String origional, String separator, Font font, int width) {
        final String[] words = split(origional, separator);
        final Vector result = new Vector();
        final StringBuffer currentLine = new StringBuffer();
        String currentToken;

        int currentWidth = 0;
        for (int i = 0; i < words.length; i++) {
            currentToken = words[i];

            if (currentWidth == 0 || currentWidth + font.stringWidth(" " + currentToken) <= width) {
                if (currentWidth == 0) {
                    currentLine.append(currentToken);
                    currentWidth += font.stringWidth(currentToken);
                } else {
                    currentLine.append(' ').append(currentToken);
                    currentWidth += font.stringWidth(" " + currentToken);
                }
            } else {
                result.addElement(currentLine.toString());
                currentLine.delete(0, currentLine.length());
                currentLine.append(currentToken);
                currentWidth = font.stringWidth(currentToken);
            }
        }
        if (currentLine.length() != 0) {
            String[] lines = split(currentLine.toString(), "\n");
            for (int line = 0; line < lines.length; line++) {
                result.addElement(lines[line]);
            }
        }

        String[] finalResult = new String[result.size()];
        for (int i = 0; i < finalResult.length; i++) {
            finalResult[i] = (String) result.elementAt(i);
        }

        return finalResult;
    }

    public static String urlEncode(String s) {
        if (s != null) {
            StringBuffer tmp = new StringBuffer();
            int i = 0;
            try {
                while (true) {
                    int b = (int) s.charAt(i++);
                    if ((b >= 0x30 && b <= 0x39) || (b >= 0x41 && b <= 0x5A) || (b >= 0x61 && b <= 0x7A)) {
                        tmp.append((char) b);
                    } else {
                        tmp.append("%");
                        if (b <= 0xf) {
                            tmp.append("0");
                        }
                        tmp.append(Integer.toHexString(b));
                    }
                }
            } catch (Exception e) {
            }
            return tmp.toString();
        }
        return null;
    }
}
