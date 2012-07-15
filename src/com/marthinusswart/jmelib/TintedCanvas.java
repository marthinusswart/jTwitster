/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marthinusswart.jmelib;

import javax.microedition.lcdui.*;
import com.marthinusswart.jmelib.graphics.*;

/**
 *
 * @author marthinus
 */
public class TintedCanvas extends Canvas{

    protected int width;
    protected int height;
    protected int x;
    protected int y;
    protected int meshColor;

    public int getMeshColor() {
        return meshColor;
    }

    public void setMeshColor(int meshColor) {
        this.meshColor = meshColor;
    }

    public TintedCanvas(int x, int y, int width, int height){
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    protected void paint(Graphics g) {
    }

    public void paint(Graphics g, Image background){
        int[]rawInt;
        rawInt = new int[width * height];
        background.getRGB(rawInt, 0, width, x, y, width, height);

        ImageEffect.mesh(rawInt, width, meshColor);
        Image newImage = Image.createRGBImage(rawInt, width, height, false);

        g.fillRect(x, y, width, height);
        g.drawImage(newImage, x, y, 0);
    }
}
