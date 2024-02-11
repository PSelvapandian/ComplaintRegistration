package com.example.complaintregistration.ModelClasses;

public class Address
{
    private String street;
    private String area;
    private String taluk;
    private String state;
    private String doorNo;
    private String post;
    private String district;
    private String pin;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", area='" + area + '\'' +
                ", taluk='" + taluk + '\'' +
                ", state='" + state + '\'' +
                ", doorNo='" + doorNo + '\'' +
                ", post='" + post + '\'' +
                ", district='" + district + '\'' +
                ", pin='" + pin + '\'' +
                '}';
    }
}
