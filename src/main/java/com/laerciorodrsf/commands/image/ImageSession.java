package com.laerciorodrsf.commands.image;

import java.util.List;

public class ImageSession {

    private List<String> images;
    private int currentIndex;
    private String query;
    private final long userId;

    public ImageSession(List<String> images, String query,  long userId) {
        this.images = images;
        this.currentIndex = 0;
        this.query = query;
        this.userId = userId;
    }

    public String getCurrentImage() {
        return images.get(currentIndex);
    }

    public String next() {
        if (currentIndex < images.size() - 1) {
            currentIndex++;
        }

        return getCurrentImage();
    }

    public String previous() {
        if (currentIndex > 0) {
            currentIndex--;
        }

        return getCurrentImage();
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getTotalImages() {
        return images.size();
    }

    public String getQuery() {
        return query;
    }

    public long getUserId() {
        return userId;
    }
}
