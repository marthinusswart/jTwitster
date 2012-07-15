/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marthinusswart.jtwitster.utils;

import javax.microedition.lcdui.Displayable;

/**
 *
 * @author marthinus
 */
public class DisplayStack extends Stack{

    public void pushDisplay(Displayable display){
        super.push(display);
    }

    public Displayable popDisplay(){
        Displayable display = (Displayable)super.pop();
        return display;
    }
}
