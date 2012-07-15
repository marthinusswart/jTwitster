/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jtwitster;

import com.marthinusswart.jmelib.graphics.*;
import javax.microedition.lcdui.*;
import com.marthinusswart.jmelib.*;

/**
 *
 * @author marthinus
 */
public class TweetCanvas extends ItemCanvas {
    
    private String text;
    private int avatarX = 1;
    private int avatarY = 1;
    private int avatarSpacing = 2;
    private int avatarSize = 35;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    private Image avatar;
    private Font font;
  

    public TweetCanvas(String text, int width, int height, Image avatar, Font font) {
        super(width, height);
        this.text = text;
        this.avatar = ImageUtil.scale(avatar, avatarSize, avatarSize);
        this.font = font;

        setBackgroundColor(jTwitsterMidlet.GRAY93_COLOR);
        setSelectedColor(jTwitsterMidlet.GRAY85_COLOR);
    }

    protected void paint(Graphics g) {
    }

    public boolean paintItem(Graphics g, int y) {
        boolean fullyPainted = true;

        fullyPainted = super.paintItem(g, y);

        drawText(g, y);

        g.drawImage(avatar, avatarX, y + avatarY, 0);

        return fullyPainted;
    }

    private void drawText(Graphics g, int y) {
        String[] originalText = {text};
        String[] textLines = StringUtil.formatMessage(originalText,
                width - font.getHeight() * 2 - font.getHeight() / 2, font);

        g.setFont(font);
        g.setColor(jTwitsterMidlet.BLACK_COLOR);
        int textRow = y + 2 + font.getHeight();

        for (int line = 0; line < textLines.length; line++) {
            g.drawString(textLines[line], avatarX + avatarSize + avatarSpacing, textRow, Graphics.LEFT | Graphics.BOTTOM);
            textRow += font.getHeight();
            if (textRow >= y + height) {
                break;
            }
        }
    }
}
