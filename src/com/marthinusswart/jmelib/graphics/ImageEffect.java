/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jmelib.graphics;

/**
 *
 * @author marthinus
 */
public class ImageEffect {

    // raw is the image array.
    public static void blend(int[] raw, int alphaValue, int maskColor, int dontmaskColor) {
        int len = raw.length;

        // Start loop through all the pixels in the image.
        for (int i = 0; i < len; i++) {
            int a = 0;
            int color = (raw[i] & 0x00FFFFFF); // get the color of the pixel.
            if (maskColor == color) {
                a = 0;
            } else if (dontmaskColor == color) {
                a = 255;
            } else if (alphaValue > 0) {
                a = alphaValue;     // set the alpha value we vant to use 0-255.
            }

            a = (a << 24);    // left shift the alpha value 24 bits.
            // if color = 00000000 11111111 11111111 00000000
            // and alpha= 01111111 00000000 00000000 00000000
            // then c+a = 01111111 11111111 11111111 00000000
            // and the pixel will be blended.
            color += a;
            raw[i] = color;
        }
    }

    public static void blend(int[] raw, int alphaValue) {
        blend(raw, alphaValue, 0xFFFFFFFF, 0xFFFFFFFF);
    }

    public static void grayScale(int[] raw) {
        int len = raw.length;

        // Start loop through all the pixels in the image.
        for (int i = 0; i < len; i++) {
            int a = (raw[i] & 0xFF000000);
            int red = (raw[i] & 0x00FF0000);
            int green = (raw[i] & 0x0000FF00);
            int blue = (raw[i] & 0x000000FF);
            int gred = red + green + blue / 3;
            int ggreen = gred;
            int gblue = gred;

            int gray = gblue;
            gray += (ggreen << 8);
            gray += (gred << 16);
            gray += (a << 24);

            raw[i] = gray;
        }
    }

    public static void mesh(int[] raw, int width, int color) {
        int len = raw.length;
        int isSecondPixel = 0;        
        int widthCounter = 0;

        // Start loop through all the pixels in the image.
        for (int i = 0; i < len; i++) {
            if (isSecondPixel == 0) {
                raw[i] = color;
                isSecondPixel = 1;
            } else {
                isSecondPixel = 0;
            }
            widthCounter++;
            if (widthCounter == width){
                if (isSecondPixel == 1){
                    isSecondPixel=0;
                } else {
                    isSecondPixel = 1;
                }
                widthCounter = 0;
            }
        }
    }
}