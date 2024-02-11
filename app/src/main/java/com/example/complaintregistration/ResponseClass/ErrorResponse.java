package com.example.complaintregistration.ResponseClass;

public class ErrorResponse
{
   private String timeStamp;
   private String error;
   private String status;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timeStamp='" + timeStamp + '\'' +
                ", error='" + error + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
