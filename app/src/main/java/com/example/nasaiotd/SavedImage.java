package com.example.nasaiotd;

public class SavedImage {

    private long imageID;
    private String imageTitle;
    private String imageDate;
    private String imageURL;

    public SavedImage(long imageID, String imageTitle, String imageDate, String imageURL) {
        this.imageID = imageID;
        this.imageTitle = imageTitle;
        this.imageDate = imageDate;
        this.imageURL = imageURL;
    }

    public long getImageID() {
        return imageID;
    }
    public String getImageTitle() {
        return imageTitle;
    }
    public String getImageDate() {
        return imageDate;
    }
    public String getImageURL() {
        return imageURL;
    }
}
