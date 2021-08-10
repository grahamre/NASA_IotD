package com.example.nasaiotd;

public class SavedImage {

    private long imageID;
    private String imageTitle;
    private String imageDate;
    private String imageDescription;
    private String imageURL;
    private String imageHDURL;

    public SavedImage(long imageID, String imageTitle, String imageDate, String imageDescription, String imageURL, String imageHDURL) {
        this.imageID = imageID;
        this.imageTitle = imageTitle;
        this.imageDate = imageDate;
        this.imageDescription = imageDescription;
        this.imageURL = imageURL;
        this.imageHDURL = imageHDURL;
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
    public String getImageDescription() {
        return imageDescription;
    }
    public String getImageURL() {
        return imageURL;
    }
    public String getImageHDURL() {
        return imageHDURL;
    }
}
