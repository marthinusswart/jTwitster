/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jtwitster;

import com.marthinusswart.jmelib.graphics.*;
import com.marthinusswart.jmelib.*;
import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 *
 * @author marthinus
 */
public class HomeCanvas extends Canvas implements CommandListener {

    private jTwitsterMidlet midlet;
    static HomeCanvas instance;
    private ButtonCanvas liveFeedButton;
    private ButtonCanvas repliesButton;
    private ButtonCanvas privateButton;
    private ButtonCanvas sentButton;
    private int currentSelected;
    private Vector buttons = new Vector();
    private Command backCommand;
    private Command selectCommand;
    private Image twitBird;
    private int buttonSpacing = 2;

    public HomeCanvas(jTwitsterMidlet midlet) {
        this.midlet = midlet;
        selectCommand = new Command("Select", Command.SCREEN, 0);
        backCommand = new Command("Back", Command.BACK, 1);
        addCommand(backCommand);
        addCommand(selectCommand);
        setCommandListener(this);

        twitBird = ImageUtil.loadImage("/images/twit-bird.png");
        int startY = getHeight() / 2;

        Image liveFeed = ImageUtil.loadImage("/images/live-feed-unselected.png");

        liveFeedButton = new ButtonCanvas(liveFeed,
                ImageUtil.loadImage("/images/live-feed-selected.png"), startY);
        liveFeedButton.setIsSelected(true);
        currentSelected = 0;

        startY += liveFeed.getHeight() + buttonSpacing;
        repliesButton = new ButtonCanvas(ImageUtil.loadImage("/images/replies-unselected.png"),
                ImageUtil.loadImage("/images/replies-selected.png"), startY);

        startY += liveFeed.getHeight() + buttonSpacing;
        privateButton = new ButtonCanvas(ImageUtil.loadImage("/images/private-unselected.png"),
                ImageUtil.loadImage("/images/private-selected.png"), startY);

        startY += liveFeed.getHeight() + buttonSpacing;
        sentButton = new ButtonCanvas(ImageUtil.loadImage("/images/sent-unselected.png"),
                ImageUtil.loadImage("/images/sent-selected.png"), startY);

        buttons.addElement(liveFeedButton);
        buttons.addElement(repliesButton);
        buttons.addElement(privateButton);
        buttons.addElement(sentButton);
    }

    public static HomeCanvas getInstance(jTwitsterMidlet midlet) {
        if (instance == null) {
            instance = new HomeCanvas(midlet);
        }

        return instance;
    }

    protected void paint(Graphics g) {
        g.setColor(jTwitsterMidlet.LIGHT_BLUE_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.drawImage(twitBird, 10, 10, 0);

        liveFeedButton.paint(g);
        repliesButton.paint(g);
        privateButton.paint(g);
        sentButton.paint(g);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            midlet.back();
        } else if (c == selectCommand) {
            showSelectedDisplay();
        }
    }

    protected void keyReleased(int keyCode) {
        if (keyCode == jTwitsterMidlet.UP_KEY) {
            selectNextUp();
        } else if (keyCode == jTwitsterMidlet.DOWN_KEY) {
            selectNextDown();
        } else if (keyCode == jTwitsterMidlet.CENTER_KEY) {
            showSelectedDisplay();
        }

        repaint();
    }

    private void selectCurrent() {
        ButtonCanvas current = (ButtonCanvas) buttons.elementAt(currentSelected);
        current.setIsSelected(true);
    }

    private void selectNextUp() {
        if (currentSelected != 0) {
            unSelectCurrent();
            currentSelected--;
            selectCurrent();
        }
    }

    private void selectNextDown() {
        if (currentSelected != buttons.size() - 1) {
            unSelectCurrent();
            currentSelected++;
            selectCurrent();
        }
    }

    private void showSelectedDisplay() {
        if (currentSelected == 0) {
            midlet.createLiveFeed();
        }
    }

    private void unSelectCurrent() {
        ButtonCanvas current = (ButtonCanvas) buttons.elementAt(currentSelected);
        current.setIsSelected(false);
    }
}
