/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jmelib.data;

import java.io.*;
import java.util.*;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/**
 *
 * @author marthinus
 */
public class DataManagement {

    private Vector dataItems = new Vector();
    private String storeName;

    public void delete() throws RecordStoreException {
        RecordStore.deleteRecordStore(storeName);
    }

    public boolean hasDataItems() {
        return (dataItems.size() > 0);
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public DataManagement(String storeName) {
        this.storeName = storeName;
    }

    public void add(DataItem dataItem) {
        dataItems.addElement(dataItem);
    }

    public DataItem getDataItemAt(int pos){
        return (DataItem) dataItems.elementAt(pos);
    }

    public Enumeration getDataItems() {
        return dataItems.elements();
    }

    public void save() throws RecordStoreException, IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        Enumeration dataElements = dataItems.elements();

        while (dataElements.hasMoreElements()) {
            DataItem dataItem = (DataItem) dataElements.nextElement();
            dataItem.dehydrate(dout);
        }

        byte[] data = bout.toByteArray();
        RecordStore rs = RecordStore.openRecordStore(storeName, true);
        rs.addRecord(data, 0, data.length);

        rs.closeRecordStore();
        dout.close();
        bout.close();
    }

    public void load() throws RecordStoreException, IOException {
        RecordStore rs = null;
        ByteArrayInputStream bin = null;
        DataInputStream din = null;

        rs = RecordStore.openRecordStore(storeName, true);

        if (rs.getNumRecords() > 0) {
            RecordEnumeration records = rs.enumerateRecords(null, null, true);
            while (records.hasNextElement()) {
                byte[] data = records.nextRecord();
                if (data != null) {
                    bin = new ByteArrayInputStream(data);
                    din = new DataInputStream(bin);
                    DataItem dataItem = new DataItem();
                    try {
                        dataItem.hydrate(din);
                    } catch (IOException ex) {
                        throw new IOException("Failed to hydrate from input stream. " + ex.getMessage());
                    }
                    dataItems.addElement(dataItem);
                    din.close();
                    bin.close();
                }
            }
        }

        rs.closeRecordStore();
    }
}
