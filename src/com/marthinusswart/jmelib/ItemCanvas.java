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
public class ItemCanvas extends Canvas{

    protected int width;
    protected int height;
    protected boolean isSelected;
    protected int selectedColor;

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.selectedColor = selectedColor;
    }
    protected int backgroundColor;

     public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public ItemCanvas(int width, int height){
        this.width = width;
        this.height = height;
    }

    protected void paint(Graphics g) {
    }

    public boolean paintItem(Graphics g, int y) {
        boolean fullyPainted = true;
        if (!isSelected) {
            g.setColor(backgroundColor);
        } else {
            g.setColor(selectedColor);
        }
        int paintHeight = height;

        if (y + height > g.getClipHeight()) {
            fullyPainted = false;
            paintHeight = g.getClipHeight() - y;
        }

        g.fillRect(0, y, width, paintHeight);

        return fullyPainted;
    }

    public int getHeight() {
        return height;
    }

}
