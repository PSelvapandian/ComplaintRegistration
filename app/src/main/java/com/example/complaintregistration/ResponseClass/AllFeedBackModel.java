package com.example.complaintregistration.ResponseClass;

import com.example.complaintregistration.ModelClasses.FeedBack;

import java.util.List;

public class AllFeedBackModel
{
    private String timeStamp;
    private List<FeedBack> payload;
    private String message;
    private Integer status;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<FeedBack> getPayload() {
        return payload;
    }

    public void setPayload(List<FeedBack> payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AllFeedBackModel{" +
                "timeStamp='" + timeStamp + '\'' +
                ", payload=" + payload +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
