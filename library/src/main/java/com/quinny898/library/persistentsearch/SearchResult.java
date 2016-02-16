package com.quinny898.library.persistentsearch;

import android.graphics.drawable.Drawable;

public class SearchResult {
    public String title;
    public Drawable icon;
    public String value;
    public boolean clickable;

    /**
     * Create a search result with text an icon and value
     * @param title
     * @param value
     * @param icon
     */
    public SearchResult(String title, String value, Drawable icon) {
        this.title = title;
        this.icon = icon;
        this.value = value;
        this.clickable = true;
    }

    /**
     * Create a search result with clickable property
     * @param title
     * @param value
     * @param icon
     * @param clickable
     */
    public SearchResult(String title, String value, Drawable icon, boolean clickable) {
        this.title = title;
        this.icon = icon;
        this.value = value;
        this.clickable = clickable;
    }

    public int viewType = 0;

    public SearchResult(String title){
        this.title = title;
    }

    public SearchResult(int viewType, String title){
        this.viewType = viewType;
        this.title = title;
    }
    
    /**
     * Return the title of the result
     */
    @Override
    public String toString() {
        return title;
    }
    
}