package com.example.complaintregistration.Enum;

public enum Department
{
    SL("Street Light"),
    WPL("Water Pipe Leakage"),
    RWT("Rain Water Drainage"),
    R("Road");

    private final String dept;

    Department(String dept) {
        this.dept = dept;
    }

    public String getDept() {
        return dept;
    }
}
