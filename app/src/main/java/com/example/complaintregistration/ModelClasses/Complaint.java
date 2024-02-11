package com.example.complaintregistration.ModelClasses;

import java.util.Arrays;
import java.util.Date;

public class Complaint {

    private Long complaintId;
    private Long userId;
    private String landmark;
    private Address address;
    private String complaintType;
    private String complaintDetails;
    private String complaintStatus;
    private String reason;
    private String expectDeliveryDate;
    private String resolveAt;
    private byte[] compImg;
    private byte[] resolveImg;
    private Date createAt;
    private Date updateAt;

    public void createComplaint(String name, Long userId, String landmark, Address address, String complaintType, String complaintMsg, String complaintStatus)
    {
        this.userId = userId;
        this.landmark = landmark;
        this.address = address;
        this.complaintType = complaintType;
        this.complaintDetails = complaintMsg;
        this.complaintStatus = complaintStatus;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public String getComplaintDetails() {
        return complaintDetails;
    }

    public void setComplaintDetails(String complaintDetails) {
        this.complaintDetails = complaintDetails;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExpectDeliveryDate() {
        return expectDeliveryDate;
    }

    public void setExpectDeliveryDate(String expectDeliveryDate) {
        this.expectDeliveryDate = expectDeliveryDate;
    }

    public String getResolveAt() {
        return resolveAt;
    }

    public void setResolveAt(String resolveAt) {
        this.resolveAt = resolveAt;
    }

    public byte[] getCompImg() {
        return compImg;
    }

    public void setCompImg(byte[] compImg) {
        this.compImg = compImg;
    }

    public byte[] getResolveImg() {
        return resolveImg;
    }

    public void setResolveImg(byte[] resolveImg) {
        this.resolveImg = resolveImg;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "complaintId=" + complaintId +
                ", userId=" + userId +
                ", landmark='" + landmark + '\'' +
                ", address='" + address + '\'' +
                ", complaintType='" + complaintType + '\'' +
                ", complaintDetails='" + complaintDetails + '\'' +
                ", complaintStatus='" + complaintStatus + '\'' +
                ", reason='" + reason + '\'' +
                ", expectDeliveryDate='" + expectDeliveryDate + '\'' +
                ", resolveAt='" + resolveAt + '\'' +
                ", compImg=" + Arrays.toString(compImg) +
                ", resolveImg=" + Arrays.toString(resolveImg) +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
