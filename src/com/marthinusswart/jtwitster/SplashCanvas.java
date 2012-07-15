/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jtwitster;

import com.marthinusswart.jmelib.graphics.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author marthinus
 */
public class SplashCanvas extends Canvas implements CommandListener {

    jTwitsterMidlet midlet;
    Command exitCommand;
    Command loginCommand;
    Command homeCommand;
    Image image = ImageUtil.loadImage("/images/jtwitster-logo.png");
    static SplashCanvas instance;

    public SplashCanvas(jTwitsterMidlet midlet) {
        this.midlet = midlet;
        exitCommand = new Command("Exit", Command.EXIT, 0);
        homeCommand = new Command("Home", Command.SCREEN, 1);
        loginCommand = new Command("Login", Command.SCREEN, 2);
        addCommand(homeCommand);
        addCommand(loginCommand);
        addCommand(exitCommand);
        setCommandListener(this);
    }

    public static SplashCanvas getInstance(jTwitsterMidlet midlet) {
        if (instance == null) {
            instance = new SplashCanvas(midlet);
        }

        return instance;
    }

    protected void paint(Graphics g) {
        paintScreen(g);
    }

    private void paintScreen(Graphics g) {
        g.setColor(jTwitsterMidlet.WHITE_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(image, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
    }

    public void commandAction(Command c, Displayable s) {
        if (c == exitCommand) {
            midlet.quit();
        } else if (c == loginCommand) {
            LoginForm loginForm = new LoginForm(midlet);
            try {
                loginForm.loadCredentials();
                midlet.getDisplay().setCurrent(loginForm);
            } catch (Exception ex) {
                Alert alert = new Alert("Error", "Failed to initialise the DataManagement: " + ex.toString(),
                        null, AlertType.ERROR);
                midlet.getDisplay().setCurrent(alert, this);
            }
            
        } else if (c == homeCommand) {
            midlet.createHome();
        }
    }
}
