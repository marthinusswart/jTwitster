/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jmelib;

import javax.microedition.lcdui.*;

/**
 *
 * @author marthinus
 */
public class ButtonCanvas extends Canvas {

    private Image selectedImage;
    private Image normalImage;
    private String text;
    private int width;
    private int height;
    private int x;
    private int y;
    private int color;
    private boolean isSelected = false;

    public ButtonCanvas(String text, int x, int y,
            int width, int height, int color) {
        this.text = text;
        this.width = width;
        this.height = height;
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public ButtonCanvas(Image normalState, Image selectedState, int y) {
        setDetails(normalState, selectedState, getWidth() / 2, y);
    }

    public ButtonCanvas(Image normalState, Image selectedState, int x, int y) {
        setDetails(normalState, selectedState, x, y);
    }

    private void setDetails(Image normal, Image selected, int x, int y) {
        this.normalImage = normal;
        this.selectedImage = selected;
        this.x = x;
        this.y = y;
    }

    public void setIsSelected(boolean value) {
        isSelected = value;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void paint(Graphics g) {

        if (normalImage == null) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        } else {
            if (isSelected) {
                g.drawImage(selectedImage, x, y, Graphics.HCENTER | Graphics.TOP);
            } else {
                g.drawImage(normalImage, x, y, Graphics.HCENTER | Graphics.TOP);
            }
        }
    }
}
