/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marthinusswart.jmelib.graphics;

import javax.microedition.lcdui.Image;

/**
 *
 * @author marthinus
 */
public class ImageUtil {

    /** Creates a new instance of ImageUtil */
    private ImageUtil() {
    }

    /** Load an image
     * @param   filename
     * @return
     */
    public static Image loadImage(String filename) {
        Image image = null;
        try {
            image = Image.createImage(filename);
        } catch(Exception e) {
            System.err.println("Error while loading image: " + filename);
            System.out.println("Description: " + e.toString());
            // Use null
        }
        return image;
    }

    /** Scale image */
    public static Image scale(Image src, int width, int height) {

        int scanline = src.getWidth();
        int srcw = src.getWidth();
        int srch = src.getHeight();
        int buf[] = new int[srcw * srch];
        src.getRGB(buf, 0, scanline, 0, 0, srcw, srch);
        int buf2[] = new int[width*height];
        for (int y=0;y<height;y++) {
            int c1 = y*width;
            int c2 = (y*srch/height)*scanline;
            for (int x=0;x<width;x++) {
                buf2[c1 + x] = buf[c2 + x*srcw/width];
            }
        }
        Image img = Image.createRGBImage(buf2, width, height, true);
        return img;
    }

}

