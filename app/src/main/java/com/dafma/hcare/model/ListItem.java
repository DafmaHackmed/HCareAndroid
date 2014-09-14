package com.dafma.hcare.model;

/**
 * Created by antonioscardigno on 14/09/14.
 */
public class ListItem {

    private int image;
    private String text;
    private boolean switchValue;
    private int type;

    public ListItem(int image, String text, boolean switchValue, int type) {
        this.image = image;
        this.text = text;
        this.switchValue = switchValue;
        this.type = type;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSwitchValue() {
        return switchValue;
    }

    public void setSwitchValue(boolean switchValue) {
        this.switchValue = switchValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
