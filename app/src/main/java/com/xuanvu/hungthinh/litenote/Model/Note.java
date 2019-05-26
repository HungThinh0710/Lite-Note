package com.xuanvu.hungthinh.litenote.Model;

import android.graphics.Color;

import java.io.Serializable;

public class Note implements Serializable {

    private int ID;
    private String mTitle;
    private String mContent;
    private String mCreated_at;
    private String mColor;
    public Note(){ }

    public Note(String mTitle, String mContent, String mColor, String mCreated_at){
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mColor = mColor;
        this.mCreated_at = mCreated_at;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }

    public String getmCreated_at() {
        return mCreated_at;
    }

    public void setmCreated_at(String mCreated_at) {
        this.mCreated_at = mCreated_at;
    }
}
