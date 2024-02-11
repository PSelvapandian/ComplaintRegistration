package com.example.complaintregistration.ResponseClass;

import com.example.complaintregistration.ModelClasses.Complaint;

import java.util.List;
import java.util.Map;

public class ReportResponse
{
    private String timeStamp;
    private Map<String, List<Complaint>> payload;
    private String message;
    private Integer status;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Map<String, List<Complaint>> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, List<Complaint>> payload) {
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
        return "ReportResponse{" +
                "timeStamp='" + timeStamp + '\'' +
                ", payload=" + payload +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
