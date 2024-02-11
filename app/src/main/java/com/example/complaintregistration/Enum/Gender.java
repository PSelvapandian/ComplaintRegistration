package com.example.complaintregistration.Enum;

public enum Gender
{
    M("Male"),
    F("Female"),
    TG("TransGender");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
