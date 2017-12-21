package com.kosi0917.textandfacerecognitionapp.Model.VK;

/**
 * Created by vibo0917 on 12/14/2017.
 */

public class Photo {

    private int id;
    private int owner_id;
    private String photo_75;
    private String photo_130;
    private String photo_604;
    private String photo_807;
    private String photo_1280;
    private int date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getPhoto_75() {
        return photo_75;
    }

    public void setPhoto_75(String photo_75) {
        this.photo_75 = photo_75;
    }

    public String getPhoto_130() {
        return photo_130;
    }

    public void setPhoto_130(String photo_130) {
        this.photo_130 = photo_130;
    }

    public String getPhoto_604() {
        return photo_604;
    }

    public void setPhoto_604(String photo_604) {
        this.photo_604 = photo_604;
    }

    public String getPhoto_807() {
        return photo_807;
    }

    public void setPhoto_807(String photo_807) {
        this.photo_807 = photo_807;
    }

    public String getPhoto_1280() {
        return photo_1280;
    }

    public void setPhoto_1280(String photo_1280) {
        this.photo_1280 = photo_1280;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", photo_75='" + photo_75 + '\'' +
                ", photo_130='" + photo_130 + '\'' +
                ", photo_604='" + photo_604 + '\'' +
                ", photo_807='" + photo_807 + '\'' +
                ", photo_1280='" + photo_1280 + '\'' +
                ", date=" + date +
                '}';
    }
}
