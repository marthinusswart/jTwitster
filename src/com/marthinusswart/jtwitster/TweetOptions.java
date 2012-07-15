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
public class TweetOptions extends TintedCanvas {

    private ButtonCanvas replyButton;
    private ButtonCanvas retweetButton;
    private ButtonCanvas directButton;
    private ButtonCanvas gotoButton;
    private Vector buttons = new Vector();
    private int currentSelected;
    private int buttonSpacing = 2;

    public int getCurrentSelected() {
        return currentSelected;
    }

    public void setCurrentSelected(int currentSelected) {
        this.currentSelected = currentSelected;
        selectCurrent();
    }
    private jTwitsterMidlet midlet;
    private TweetCanvas tweet;

    public TweetCanvas getTweet() {
        return tweet;
    }

    public void setTweet(TweetCanvas tweet) {
        this.tweet = tweet;
    }

    public TweetOptions(jTwitsterMidlet midlet, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.midlet = midlet;
        Image replyImage = ImageUtil.loadImage("/images/reply-unselected.png");
        setMeshColor(jTwitsterMidlet.LIGHT_BLUE_COLOR);
        currentSelected = 0;
        int startY = height / 2;
        int buttonHeight = replyImage.getHeight();
        startY -= (buttonHeight+buttonSpacing) * 2;

        replyButton = new ButtonCanvas(replyImage, ImageUtil.loadImage("/images/reply-selected.png"), startY);

        startY += buttonHeight+buttonSpacing;
        retweetButton = new ButtonCanvas(ImageUtil.loadImage("/images/retweet-unselected.png"),
                ImageUtil.loadImage("/images/retweet-selected.png"), startY);

        startY += buttonHeight+buttonSpacing;
        gotoButton = new ButtonCanvas(ImageUtil.loadImage("/images/goto-unselected.png"),
                ImageUtil.loadImage("/images/goto-selected.png"), startY);

        startY += buttonHeight+buttonSpacing;
        directButton = new ButtonCanvas(ImageUtil.loadImage("/images/direct-unselected.png"),
                ImageUtil.loadImage("/images/direct-selected.png"), startY);        

        buttons.addElement(replyButton);
        buttons.addElement(retweetButton);
        buttons.addElement(gotoButton);
        buttons.addElement(directButton);        

        selectCurrent();
    }

    public void paint(Graphics g, Image background){
        super.paint(g, background);

        replyButton.paint(g);
        retweetButton.paint(g);
        directButton.paint(g);
        gotoButton.paint(g);
    }

    public void handleKeyPressed(int keyCode){
        if (keyCode == jTwitsterMidlet.UP_KEY) {
            selectNextUp();
        } else if (keyCode == jTwitsterMidlet.DOWN_KEY) {
            selectNextDown();
        } else if (keyCode == jTwitsterMidlet.CENTER_KEY){
            handleSelection();
        }
        repaint();
    }

    void resetSelection() {
        for (int i=0; i<buttons.size(); i++){
            ButtonCanvas button = (ButtonCanvas)buttons.elementAt(i);
            button.setIsSelected(false);
        }

        setCurrentSelected(0);
    }

    private void handleDirect() {
        TweetBox tweetBox = new TweetBox(midlet, "Direct Message", "@");
        midlet.display(tweetBox);
    }

    private void handleGoto() {
        WebAddressList webAddressList = new WebAddressList("Available web addresses", midlet);
        String tweetText = tweet.getText();
        String[] parts = StringUtil.split(tweetText, " ");
        webAddressList.append("@"+parts[0], null);

        if (parts.length > 1){
            for (int i=1; i<parts.length; i++){
                if (parts[i].startsWith("http://")){
                    webAddressList.append(StringUtil.getURL(parts[i]), null);
                }
            }
        }

        midlet.display(webAddressList);
    }

    private void handleReply() {
        String tweetText = tweet.getText();
        String[] parts = StringUtil.split(tweetText, " ");
        TweetBox tweetBox = new TweetBox(midlet, "Reply", "@"+parts[0]+" ");
        midlet.display(tweetBox);
    }

    private void handleRetweet() {
        String tweetText = tweet.getText();

        if (tweetText.length() > 140){
            tweetText = tweetText.substring(0, 130);
        }

        TweetBox tweetBox = new TweetBox(midlet, "Retweet", "RT @"+tweetText);
        midlet.display(tweetBox);
    }

    private void handleSelection() {
        switch (currentSelected){
            case 0: handleReply();break;
            case 1: handleRetweet(); break;
            case 2: handleGoto(); break;
            case 3: handleDirect();break;
        }
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
        if (currentSelected != buttons.size()-1) {
            unSelectCurrent();
            currentSelected++;
            selectCurrent();
        }
    }

    private void unSelectCurrent() {
        ButtonCanvas current = (ButtonCanvas) buttons.elementAt(currentSelected);
        current.setIsSelected(false);
    }

}
