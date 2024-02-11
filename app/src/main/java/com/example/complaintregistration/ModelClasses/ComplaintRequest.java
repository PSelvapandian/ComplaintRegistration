package com.example.complaintregistration.ModelClasses;

public class ComplaintRequest
{
    private String from;
    private String to;
    private Long userId;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ComplaintRequest{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", userId=" + userId +
                '}';
    }
}
