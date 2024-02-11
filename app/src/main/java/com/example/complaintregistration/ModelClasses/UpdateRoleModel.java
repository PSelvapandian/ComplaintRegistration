package com.example.complaintregistration.ModelClasses;

public class UpdateRoleModel
{
    private String userId;
    private Roles roles;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UpdateRoleModel{" +
                "userId='" + userId + '\'' +
                ", roles=" + roles +
                '}';
    }
}
