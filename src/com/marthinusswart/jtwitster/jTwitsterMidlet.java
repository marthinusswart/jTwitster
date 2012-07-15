package com.marthinusswart.jtwitster;

import com.marthinusswart.jtwitster.utils.DisplayStack;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class jTwitsterMidlet extends MIDlet implements CommandListener {

    private Display display;
    private SplashCanvas splashCanvas;
    private HomeCanvas homeCanvas;
    private LiveFeedTweets liveFeedTweets;
    public static final int TWITTER_BLUE_COLOR = 0x6DE8E4;
    public static final int LIGHT_BLUE_COLOR = 0xD1E8E4;
    public static final int WHITE_COLOR = 0xFFFFFF;
    public static final int BLACK_COLOR = 0x000000;
    public static final int GRAY73_COLOR = 0xBABABA;
    public static final int GRAY85_COLOR = 0xD8D8D8;
    public static final int GRAY93_COLOR = 0xEDEDED;
    public static final int RED_COLOR = 0xAE0000;
    public static final int UP_KEY = -1;
    public static final int DOWN_KEY = -2;
    public static final int LEFT_KEY = -3;
    public static final int RIGHT_KEY = -4;
    public static final int CENTER_KEY = -5;
    private DisplayStack displayStack = new DisplayStack();
    private Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public jTwitsterMidlet() {
        display = Display.getDisplay(this);
    }

    public void startApp() {
        splashCanvas = SplashCanvas.getInstance(this);
        display.setCurrent(splashCanvas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable s) {
    }

    public Display getDisplay() {
        return display;
    }

    public SplashCanvas getSplashCanvas() {
        return splashCanvas;
    }

    public void quit() {
        notifyDestroyed();
    }

    public void createHome() {
        homeCanvas = HomeCanvas.getInstance(this);
        displayStack.pushDisplay(display.getCurrent());
        display.setCurrent(homeCanvas);
    }

    public void createLiveFeed() {
        if (liveFeedTweets == null) {
            liveFeedTweets = new LiveFeedTweets(this);
        }

        displayStack.pushDisplay(display.getCurrent());
        display.setCurrent(liveFeedTweets);
    }

    public void back() {
        Displayable newDisplay = displayStack.popDisplay();

        if (newDisplay != null) {
            display.setCurrent(newDisplay);
        }
    }

    void gotoAdress(String string) {
        
    }

    void postTweet(String string) {
    }

    public void display(Displayable d) {
        displayStack.pushDisplay(display.getCurrent());
        display.setCurrent(d);
    }
}
