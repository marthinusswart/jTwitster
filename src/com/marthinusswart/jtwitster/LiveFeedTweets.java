/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jtwitster;

import com.marthinusswart.jmelib.graphics.*;
import com.marthinusswart.jmelib.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author marthinus
 */
public class LiveFeedTweets extends ListCanvas implements CommandListener {

    private jTwitsterMidlet midlet;
    private Command backCommand;
    private Command selectCommand;
    private TweetOptions tweetOptions;
    private boolean showTweetOptions;
    private Image tweetOptionsBackground;

    public LiveFeedTweets(jTwitsterMidlet midlet) {
        super();
        setBackgroundColor(jTwitsterMidlet.BLACK_COLOR);
        setScrollbarColor(jTwitsterMidlet.BLACK_COLOR);
        setScrollbarBackgroundColor(jTwitsterMidlet.GRAY73_COLOR);
        setScrollbarPositionIndicatorColor(jTwitsterMidlet.BLACK_COLOR);

        this.midlet = midlet;        
        Image avatar = ImageUtil.loadImage("/images/avatar.png");
        Font font = Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN, Font.SIZE_SMALL);

        backCommand = new Command("Back", Command.BACK, 0);
        selectCommand = new Command("Select", Command.SCREEN, 0);
        addCommand(backCommand);
        addCommand(selectCommand);
        setCommandListener(this);

        currentIndex = 0;
        int tweetWidth = getWidth() - 10;

        TweetCanvas tweet1 = new TweetCanvas("1234567890 1234567890 1234567890 1234567890", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet2 = new TweetCanvas("12345 67890 12345 67890 12345 67890 12345 ", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet3 = new TweetCanvas("3 Look at this http://bit.ly/18PSBY!", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet4 = new TweetCanvas("4", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet5 = new TweetCanvas("5", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet6 = new TweetCanvas("6", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet7 = new TweetCanvas("7", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet8 = new TweetCanvas("8", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet9 = new TweetCanvas("9", tweetWidth, itemHeight, avatar, font);
        TweetCanvas tweet10 = new TweetCanvas("10", tweetWidth, itemHeight, avatar, font);

        items.addElement(tweet1);
        items.addElement(tweet2);
        items.addElement(tweet3);
        items.addElement(tweet4);
        items.addElement(tweet5);
        items.addElement(tweet6);
        items.addElement(tweet7);
        items.addElement(tweet8);
        items.addElement(tweet9);
        items.addElement(tweet10);
    }

    protected void paint(Graphics g) {
        if (showTweetOptions) {
            Graphics backgroundGraphics = tweetOptionsBackground.getGraphics();
            paintScreen(backgroundGraphics);
            paintTweetOptions(g);
        } else {
            super.paint(g);
        }


        if (tweetOptionsBackground == null) {
            tweetOptionsBackground = Image.createImage(width, height);
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == backCommand) {
            if (showTweetOptions) {
                showTweetOptions = false;
                repaint();
            } else {
                midlet.back();
            }
        } else if (c == selectCommand) {
            if (showTweetOptions) {
                tweetOptions.handleKeyPressed(jTwitsterMidlet.CENTER_KEY);
                showTweetOptions = false;
            } else {
                selectCurrentTweet();
            }
        }
    }

    protected void keyReleased(int keyCode) {
        if (showTweetOptions) {
            if (keyCode == jTwitsterMidlet.CENTER_KEY) {
                showTweetOptions = false;
            }
            tweetOptions.handleKeyPressed(keyCode);
        } else {
            if (keyCode == jTwitsterMidlet.UP_KEY) {
                scrollUp();
            } else if (keyCode == jTwitsterMidlet.DOWN_KEY) {
                scrollDown();
            } else if (keyCode == jTwitsterMidlet.RIGHT_KEY) {
                pageDown();
            } else if (keyCode == jTwitsterMidlet.LEFT_KEY) {
                pageUp();
            } else if (keyCode == jTwitsterMidlet.CENTER_KEY) {
                selectCurrentTweet();
            }
        }
        repaint();
    }

    private void paintTweetOptions(Graphics g) {
        tweetOptions.paint(g, tweetOptionsBackground);
    }

    private void selectCurrentTweet() {
        if (tweetOptions == null) {
            tweetOptions = new TweetOptions(midlet, 0, 0, width, height);
        }

        showTweetOptions = true;
        tweetOptions.setTweet((TweetCanvas) items.elementAt(currentIndex));
        tweetOptions.resetSelection();
        repaint();
    }
}
