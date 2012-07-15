/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marthinusswart.jtwitster.utils;

import java.util.Vector;

/**
 *
 * @author marthinus
 */
public class Stack {
    Vector items = new Vector();

    public void push(Object item){
        items.addElement(item);
    }

    public Object pop(){
        Object item = items.lastElement();
        items.removeElement(item);
        return item;
    }
}
