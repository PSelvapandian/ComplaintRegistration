package com.example.complaintregistration.ResponseClass;

import com.example.complaintregistration.ModelClasses.CustomUser;

import java.util.List;

public class AllUsers
{
    private String timeStamp;
    private List<CustomUser> payload;
    private String message;
    private String error;
    private Integer status;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<CustomUser> getPayload() {
        return payload;
    }

    public void setPayload(List<CustomUser> payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AllUsers{" +
                "timeStamp='" + timeStamp + '\'' +
                ", payload=" + payload +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", status=" + status +
                '}';
    }
}
