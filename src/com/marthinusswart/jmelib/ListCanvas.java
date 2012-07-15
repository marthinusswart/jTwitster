/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marthinusswart.jmelib;

import java.util.Vector;
import javax.microedition.lcdui.*;

/**
 *
 * @author marthinus
 */
public class ListCanvas extends Canvas{

    protected int width;
    protected int height;
    protected int scrollerBackgroundWidth;
    protected int scrollerBackgroundHeight;
    protected Vector items;
    protected int currentIndex;
    protected int itemHeight = 75;
    protected int[] currentWindow = new int[4];
    protected int itemSpacing = 1;
    protected int backgroundColor;
    protected int scrollbarColor;
    protected int scrollbarBackgroundColor;
    protected int scrollbarPositionIndicatorColor;

    public int getScrollbarPositionIndicatorColor() {
        return scrollbarPositionIndicatorColor;
    }

    public void setScrollbarPositionIndicatorColor(int scrollbarPositionIndicatorColor) {
        this.scrollbarPositionIndicatorColor = scrollbarPositionIndicatorColor;
    }

    public int getScrollbarBackgroundColor() {
        return scrollbarBackgroundColor;
    }

    public void setScrollbarBackgroundColor(int scrollbarBackgroundColor) {
        this.scrollbarBackgroundColor = scrollbarBackgroundColor;
    }

    public int getScrollbarColor() {
        return scrollbarColor;
    }

    public void setScrollbarColor(int scrollbarColor) {
        this.scrollbarColor = scrollbarColor;
    }

    public ListCanvas(){
        items = new Vector();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    protected void paint(Graphics g) {
        if ((width == 0) && (height == 0)) {
            width = g.getClipWidth();
            height = g.getClipHeight();
            setCurrentWindow(0, 0, width, height);
        }

        paintScreen(g);
    }

    protected void paintScreen(Graphics g) {

        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);

        g.setColor(scrollbarColor);
        g.drawRect(width - 10, 0, 9, height - 1);

        g.setColor(scrollbarBackgroundColor);
        scrollerBackgroundWidth = 8;
        scrollerBackgroundHeight = height - 2;
        g.fillRect(width - 9, 1, scrollerBackgroundWidth, scrollerBackgroundHeight);

        drawPositionIndicator(g);
        drawItems(g);
    }

    protected int getDistanceToLastItem(int currentItemY) {
        int currentWindowEndY = currentWindow[1] + currentWindow[3];
        int currentItemVisibleHeight = (currentWindowEndY - currentItemY);
        int distance = (itemHeight + itemSpacing) - currentItemVisibleHeight;
        return distance;
    }

    private void drawItems(Graphics g) {
        boolean shouldScrollToTweet = false;
        int currentItemY = getItemPosY(currentIndex, false);

        shouldScrollToTweet = (!itemFitsInCanvas(currentItemY));

        if (shouldScrollToTweet) {
            if (currentItemY > currentWindow[1]) {
                if (currentIndex < items.size() - 1) {
                    moveCurrentWindowDown((itemHeight + itemSpacing));
                } else {
                    int moveDownAmount = getDistanceToLastItem(currentItemY);
                    moveCurrentWindowDown(moveDownAmount);
                }
            } else {
                if (currentIndex != 0) {
                    moveCurrentWindowUp((itemHeight + itemSpacing));
                } else {
                    currentWindow[1] = 0;
                }

            }
        }

        int painted = 0;
        for (int i = getStartItem(); i < items.size(); i++) {
            ItemCanvas tweet = (ItemCanvas) items.elementAt(i);
            tweet.setIsSelected(currentIndex == i);
            currentItemY = getItemPosY(i, true);
            if (!tweet.paintItem(g, currentItemY)) {
                break;
            }
            painted++;
        }

    }

     protected int getStartItem() {
        int result = currentIndex;

        int currentItemY = currentIndex * (itemHeight + itemSpacing);
        if (currentItemY > currentWindow[1]) {
            result = currentWindow[1] / (itemHeight + itemSpacing);
        }

        return result;
    }

    protected void moveCurrentWindowDown(int amount) {
        currentWindow[1] += amount;
    }

    protected void moveCurrentWindowUp(int amount) {
        currentWindow[1] -= amount;
    }

    protected boolean itemFitsInCanvas(int currentItemY) {
        boolean result = false;

        int currentWindowLowBound = currentWindow[1] + currentWindow[3];
        int currentWindowUpperBound = currentWindow[1];

        if ((currentItemY < currentWindowLowBound) && (currentItemY >= currentWindowUpperBound)) {
            if (currentItemY + itemHeight < currentWindowLowBound) {
                result = true;
            }
        }

        return result;
    }

    protected int getItemPosY(int index, boolean relativeToCurrentWindow) {
        int posY = 0;
        if (relativeToCurrentWindow) {
            posY = (index * (itemHeight + itemSpacing)) - currentWindow[1];
        } else {
            posY = (index * (itemHeight + itemSpacing));
        }
        return posY;
    }

    protected void drawPositionIndicator(Graphics g) {
        int itemCount = items.size();

        if (itemCount == 0) {
            itemCount = 1;
        }

        int indicatorHeight = height / itemCount;
        int startY = getIndicatorStartY(currentIndex, indicatorHeight);
        if (startY + indicatorHeight >= height) {
            int difference = ((startY + indicatorHeight) - height) + 2;
            startY -= difference;
        }

        g.setColor(scrollbarPositionIndicatorColor);
        g.fillRect(width - 8, startY, scrollerBackgroundWidth - 2, indicatorHeight);
    }

     private int getIndicatorStartY(int currentIndex, int indicatorHeight) {
        int startY = 2;

        if (currentIndex > 0) {
            startY += (indicatorHeight * currentIndex);
        }

        return startY;
    }

    protected void setCurrentWindow(int x, int y, int width, int height) {
        currentWindow[0] = x;
        currentWindow[1] = y;
        currentWindow[2] = width;
        currentWindow[3] = height;
    }

    public void pageUp() {
        int available = height / (itemHeight + itemSpacing);
        currentIndex = getEndItem() - available;

        if (currentIndex < available) {
            currentIndex = 0;
        }

        if (currentIndex != 0) {
            moveCurrentWindowUp(currentWindow[1] - getItemPosY(currentIndex, false));
        } else {
            currentWindow[1] = 0;
        }
    }

    public void pageDown() {
        int available = height / (itemHeight + itemSpacing);
        currentIndex = getStartItem() + available;

        if (currentIndex >= items.size() - available) {
            currentIndex = items.size() - 1;
        }

        if (currentIndex < items.size() - 1) {
            moveCurrentWindowDown(getItemPosY(currentIndex, false) - currentWindow[1]);
        } else {
            int moveDownAmount = getDistanceToLastItem(getItemPosY(currentIndex, false));
            moveCurrentWindowDown(moveDownAmount);
        }

    }

     public void scrollDown() {
        currentIndex++;
        if (currentIndex >= items.size()) {
            currentIndex = items.size() - 1;
        }
    }

    public void scrollUp() {
        currentIndex--;
        if (currentIndex < 0) {
            currentIndex = 0;
        }
    }

    private int getEndItem() {
        int result = currentIndex;

        int currentItemY = currentIndex * (itemHeight + itemSpacing);
        if ((currentItemY + itemHeight + itemSpacing) > (currentWindow[1] + currentWindow[3])) {
            result = (currentWindow[1] + currentWindow[3]) / (itemHeight + itemSpacing);
        }

        return result;
    }
}
