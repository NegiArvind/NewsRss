package com.example.arvind.newsrss;

public class News  {

    private String title;
    private String discription;
    private String pubDates;
    private String image;

    public News(String title, String discription, String pubDates, String image) {
        this.title = title;
        this.discription = discription;
        this.pubDates = pubDates;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getPubDates() {
        return pubDates;
    }

    public void setPubDates(String pubDates) {
        this.pubDates = pubDates;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
