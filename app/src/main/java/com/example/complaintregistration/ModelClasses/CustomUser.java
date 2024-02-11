package com.example.complaintregistration.ModelClasses;


import com.example.complaintregistration.Enum.Department;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CustomUser
{
    private Long userId;
    private String name;
    private String userName;
    private String email;
    private String dob;
    private String gender;
    private String mobile;
    private String password;
    private Department department;
    private byte[] profile;
    private Address address;
    private Date createAt;
    private Date updateAt;
    private List<Roles> roles;
    private String role;

    public void insertUser(String name, String email, String dob, String gender, String contactNumber, String password, Address addressModel, List<Roles> rolesList)
    {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.mobile = contactNumber;
        this.password = password;
        this.address = addressModel;
        this.roles = rolesList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public byte[] getProfile() {
        return profile;
    }

    public void setProfile(byte[] profile) {
        this.profile = profile;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CustomUser{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                ", department=" + department +
                ", profile=" + Arrays.toString(profile) +
                ", address=" + address +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", roles=" + roles +
                ", role='" + role + '\'' +
                '}';
    }
}
