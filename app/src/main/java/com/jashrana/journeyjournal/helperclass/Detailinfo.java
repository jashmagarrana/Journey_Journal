package com.jashrana.journeyjournal.helperclass;

public class Detailinfo {
    public String id, title, address,details, date;
    public byte[] image;

    public Detailinfo() {
    }

    public Detailinfo(String id, String title, String address, String details, byte[] image, String date) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.details = details;
        this.date = date;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
