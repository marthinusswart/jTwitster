/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jmelib.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author marthinus
 */
public class DataItem {

    public static final int TYPE_STRING = 1;
    public static final int TYPE_INTEGER = 2;
    private Hashtable data = new Hashtable();
    private String className = null;

    public DataItem(String className){
        this.className = className;
    }

    public DataItem(){
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void add(String name, String value) {
        Vector dataElement = new Vector();
        dataElement.addElement(name);
        dataElement.addElement(new Integer(TYPE_STRING));
        dataElement.addElement(value);
        data.put(name, dataElement);
    }

    public void add(String name, int value) {
        Vector dataElement = new Vector();
        dataElement.addElement(name);
        dataElement.addElement(new Integer(TYPE_INTEGER));
        dataElement.addElement(new Integer(value));
        data.put(name, dataElement);
    }

    public String getString(String name) {
        String value = null;
        Vector element = (Vector)data.get(name);
        value = (String) element.elementAt(2);
        return value;
    }

    public int getInt(String name){
        int value = -1;
        Vector element = (Vector)data.get(name);
        value = ((Integer) element.elementAt(2)).intValue();
        return value;
    }

    public void dehydrate(DataOutputStream dout) throws IOException {
        dout.writeUTF(className);
        Enumeration elements = data.elements();
        while (elements.hasMoreElements()) {
            Vector element = (Vector) elements.nextElement();
            String name = (String) element.elementAt(0);
            int type = ((Integer) element.elementAt(1)).intValue();
            String sValue = null;
            int intValue = 0;

            switch (type) {
                case TYPE_STRING:
                    sValue = (String) element.elementAt(2);
                    break;
                case TYPE_INTEGER:
                    intValue = ((Integer) element.elementAt(2)).intValue();
            }

            dout.writeUTF(name);
            dout.writeInt(type);

            if (type == TYPE_STRING) {
                dout.writeUTF(sValue);
            } else if (type == TYPE_INTEGER) {
                dout.writeInt(intValue);
            }
        }
    }

    public void hydrate(DataInputStream din) throws IOException {
        className = din.readUTF();

        while (din.available() > 0) {
            String name = din.readUTF();
            int type = din.readInt();
            String sValue = null;
            int intValue = 0;

            switch (type) {
                case TYPE_STRING:
                    sValue = din.readUTF();
                    break;
                case TYPE_INTEGER:
                    intValue = din.readInt();
                    break;
            }

            if (type == TYPE_STRING) {
                add(name, sValue);
            } else if (type == TYPE_INTEGER) {
                add(name, intValue);
            }
        }
    }
}
