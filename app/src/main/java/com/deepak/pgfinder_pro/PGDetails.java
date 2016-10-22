package com.deepak.pgfinder_pro;

/**
 * Created by Deepak on 19-Oct-16.
 */
public class PGDetails {
    private String advertisername, pgname, contact, pgcity, pgarea, negotiable, gender, typeoffood, moredetails, dateofposting, latitude, longitude;
    private String pgrent;
    private byte[] image1, image2;

    public PGDetails(String advertisername, String pgname, String contact, String pgcity, String pgarea, String pgrent,
                     String negotiable, String gender, String typeoffood, String moredetails, String dateofposting,
                     byte[] image1, byte[] image2, String latitude, String longitude) {
        this.advertisername = advertisername;
        this.pgname = pgname;
        this.contact = contact;
        this.pgcity = pgcity;
        this.pgarea = pgarea;
        this.negotiable = negotiable;
        this.gender = gender;
        this.typeoffood = typeoffood;
        this.moredetails = moredetails;
        this.dateofposting = dateofposting;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pgrent = pgrent;
        this.image1 = image1;
        this.image2 = image2;
    }

    public String getAdvertisername() {
        return advertisername;
    }

    public void setAdvertisername(String advertisername) {
        this.advertisername = advertisername;
    }

    public String getPgname() {
        return pgname;
    }

    public void setPgname(String pgname) {
        this.pgname = pgname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPgcity() {
        return pgcity;
    }

    public void setPgcity(String pgcity) {
        this.pgcity = pgcity;
    }

    public String getPgarea() {
        return pgarea;
    }

    public void setPgarea(String pgarea) {
        this.pgarea = pgarea;
    }

    public String getNegotiable() {
        return negotiable;
    }

    public void setNegotiable(String negotiable) {
        this.negotiable = negotiable;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTypeoffood() {
        return typeoffood;
    }

    public void setTypeoffood(String typeoffood) {
        this.typeoffood = typeoffood;
    }

    public String getMoredetails() {
        return moredetails;
    }

    public void setMoredetails(String moredetails) {
        this.moredetails = moredetails;
    }

    public String getDateofposting() {
        return dateofposting;
    }

    public void setDateofposting(String dateofposting) {
        this.dateofposting = dateofposting;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPgrent() {
        return pgrent;
    }

    public void setPgrent(String pgrent) {
        this.pgrent = pgrent;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }
}
