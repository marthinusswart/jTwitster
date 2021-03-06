/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.marthinusswart.jtwitster;

import javax.microedition.lcdui.*;

/**
 *
 * @author marthinus
 */
public class WebAddressList extends List implements CommandListener{
    
    private jTwitsterMidlet midlet;
    private Command okCommand;
    private Command cancelCommand;

    public WebAddressList(String title, jTwitsterMidlet midlet){
        super(title, Choice.IMPLICIT);
        this.midlet = midlet;

        okCommand = new Command("OK", Command.OK, 1);
        cancelCommand = new Command("Cancel", Command.CANCEL, 2);
        this.addCommand(okCommand);
        this.addCommand(cancelCommand);

        this.setCommandListener(this);
    }

    public void commandAction(Command c, Displayable d) {
        if(c==okCommand) {
            midlet.gotoAdress(this.getString(this.getSelectedIndex()));
        }

        midlet.back();
    }

}
