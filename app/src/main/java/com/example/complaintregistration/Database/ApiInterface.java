package com.example.complaintregistration.Database;

import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.ModelClasses.ComplaintRequest;
import com.example.complaintregistration.ModelClasses.CustomUser;
import com.example.complaintregistration.ModelClasses.FeedBack;
import com.example.complaintregistration.ModelClasses.ReportRequest;
import com.example.complaintregistration.ModelClasses.Roles;
import com.example.complaintregistration.ModelClasses.UpdateRoleModel;
import com.example.complaintregistration.ResponseClass.AllComplaintResponse;
import com.example.complaintregistration.ResponseClass.AllFeedBackModel;
import com.example.complaintregistration.ResponseClass.AllUsers;
import com.example.complaintregistration.ResponseClass.ComplaintResponse;
import com.example.complaintregistration.ResponseClass.FeedbackResponse;
import com.example.complaintregistration.ResponseClass.ReportResponse;
import com.example.complaintregistration.ResponseClass.UpdateRoleResponse;
import com.example.complaintregistration.ResponseClass.UserResponse;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface
{
    @POST("user")
    Call<UserResponse> createUser(@Body CustomUser customUser);

    @GET("user/{id}")
    Call<UserResponse> getUserData(@Path("id") String userId);

    @GET("user")
    Call<AllUsers> getAllUsers();

    @POST("login")
    Call<UserResponse> loginUser(@Body CustomUser customUser);

    @POST("complaint")
    Call<ComplaintResponse> createComplaint(@Body Complaint complaint);

    @GET("complaint")
    Call<AllComplaintResponse> getAllComplaints();

    @GET("complaint/{id}")
    Call<ComplaintResponse> getComplaintByComplaintId(@Path("id") String complaintId);

    @POST("complaint/filter")
    Call<AllComplaintResponse> getComplaintByUser(@Body ComplaintRequest complaintRequest);

    @POST("feedBack")
    Call<FeedbackResponse> insertFeedback(@Body FeedBack feedBack);

    @GET("feedBack")
    Call<AllFeedBackModel> getAllFeedback();

    @GET("role")
    Call<List<Roles>> findAllRole();

    @PUT("role")
    Call<UpdateRoleResponse> updateRole(@Body UpdateRoleModel updateRoleModel);

    @PUT("complaint")
    Call<UpdateRoleResponse> updateComplaintStatus(@Body Complaint complaint);

    @GET("complaint/report")
    Call<ReportResponse> getAllReports(@Query("from") String fromDate, @Query("to") String toDate);
}