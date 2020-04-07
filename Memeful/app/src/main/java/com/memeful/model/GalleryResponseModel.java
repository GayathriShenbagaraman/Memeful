package com.memeful.model;

import java.util.ArrayList;
import java.util.List;

public class GalleryResponseModel {
    private String link;
    private int points;
    private int views;
    private int images_count;
    private String title;

    private List<Images> images = new ArrayList<>();

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getImages_count() {
        return images_count;
    }

    public void setImages_count(int images_count) {
        this.images_count = images_count;
    }

    public String getDescription() {
        return title;
    }

    public void setDescription(String description) {
        this.title = description;
    }
}
