package com.example.complaintregistration.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.complaintregistration.Database.ApiClient;
import com.example.complaintregistration.Database.ApiInterface;
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
import com.example.complaintregistration.ResponseClass.ErrorResponse;
import com.example.complaintregistration.ResponseClass.FeedbackResponse;
import com.example.complaintregistration.ResponseClass.ReportResponse;
import com.example.complaintregistration.ResponseClass.UpdateRoleResponse;
import com.example.complaintregistration.ResponseClass.UserResponse;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitViewModel extends AndroidViewModel
{
    private final SharedPreferenceHelper sharedPreferenceHelper = SharedPreferenceHelper.getInstance(getApplication());
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    public MutableLiveData<CustomUser> loginResult = new MutableLiveData<>(null);
    public MutableLiveData<List<CustomUser>> userList = new MutableLiveData<>(null);
    public MutableLiveData<String> complaintResult = new MutableLiveData<>("");
    public MutableLiveData<List<Complaint>> complaintList = new MutableLiveData<>(null);
    public MutableLiveData<Complaint> complaintData = new MutableLiveData<>(null);
    public MutableLiveData<List<FeedBack>> feedbackList = new MutableLiveData<>(null);
    public MutableLiveData<Map<String, List<Complaint>>> allReportData = new MutableLiveData<>(null);
    public MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    public MutableLiveData<String> feedBackSuccess = new MutableLiveData<>("");
    public MutableLiveData<String> successResponse = new MutableLiveData<>("");

    public RetrofitViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void createUser(CustomUser customUser)
    {
        Call<UserResponse> call = apiInterface.createUser(customUser);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.body() != null)
                {
                    UserResponse userResponse = response.body();
                    if (userResponse.getStatus() == 200)
                    {
                        loginResult.setValue(userResponse.getPayload());
                    }
                    else
                    {
                        errorMessage.setValue(userResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t)
            {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void loginAccount(CustomUser customUser)
    {
        Call<UserResponse> call = apiInterface.loginUser(customUser);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response) {
                if (response.body() != null)
                {
                    UserResponse userResponse = response.body();
                    if (userResponse.getStatus() == 200)
                    {
                        sharedPreferenceHelper.setUserId(String.valueOf(userResponse.getPayload().getUserId()));
                        sharedPreferenceHelper.setUserName(userResponse.getPayload().getName());
                        sharedPreferenceHelper.setUserEmail(userResponse.getPayload().getEmail());
                        sharedPreferenceHelper.setRole(userResponse.getPayload().getRole().trim());
                        loginResult.setValue(userResponse.getPayload());
                    }
                    else
                    {
                        errorMessage.setValue(userResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t)
            {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void createComplaint(Complaint complaint)
    {
        Call<ComplaintResponse> call = apiInterface.createComplaint(complaint);
        call.enqueue(new Callback<ComplaintResponse>() {
            @Override
            public void onResponse(@NonNull Call<ComplaintResponse> call, @NonNull Response<ComplaintResponse> response) {
                if (response.body() != null)
                {
                    ComplaintResponse complaintResponse = response.body();
                    if (complaintResponse.getStatus() == 200)
                    {
                        complaintResult.setValue(complaintResponse.getMessage());
                    }
                    else
                    {
                        errorMessage.setValue(complaintResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ComplaintResponse> call, @NonNull Throwable t)
            {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void getComplaintByUser(String userId, String fromDate, String toDate)
    {
        ComplaintRequest complaintRequest = new ComplaintRequest();
        complaintRequest.setFrom(fromDate);
        complaintRequest.setTo(toDate);
        complaintRequest.setUserId(Long.valueOf(userId));
        Call<AllComplaintResponse> call = apiInterface.getComplaintByUser(complaintRequest);
        call.enqueue(new Callback<AllComplaintResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllComplaintResponse> call, @NonNull Response<AllComplaintResponse> response) {
                if (response.body() != null)
                {
                    AllComplaintResponse list = response.body();
                    complaintList.setValue(list.getPayload());
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllComplaintResponse> call, @NonNull Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void insertFeedback(long userId, String feedback)
    {
        FeedBack feedBack = new FeedBack();
        feedBack.setUserId(userId);
        feedBack.setFeedbackMsg(feedback);

        Call<FeedbackResponse> call = apiInterface.insertFeedback(feedBack);
        call.enqueue(new Callback<FeedbackResponse>() {
            @Override
            public void onResponse(@NonNull Call<FeedbackResponse> call, @NonNull Response<FeedbackResponse> response)
            {
                if (response.body() != null)
                {
                    FeedbackResponse feedbackResponse = response.body();
                    if (feedbackResponse.getStatus() == 200)
                    {
                        feedBackSuccess.setValue(feedbackResponse.getMessage());
                    }
                    else
                    {
                        errorMessage.setValue(feedbackResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FeedbackResponse> call, @NonNull Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void getUserData(String userId)
    {
        Call<UserResponse> call = apiInterface.getUserData(userId);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(@NonNull Call<UserResponse> call, @NonNull Response<UserResponse> response)
            {
                if (response.body() != null)
                {
                    UserResponse userResponse = response.body();
                    if (userResponse.getStatus() == 200)
                    {
                        loginResult.setValue(userResponse.getPayload());
                    }
                    else
                    {
                        errorMessage.setValue(userResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserResponse> call, @NonNull Throwable t)
            {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void getAllComplaints()
    {
        Call<AllComplaintResponse> call = apiInterface.getAllComplaints();
        call.enqueue(new Callback<AllComplaintResponse>() {
            @Override
            public void onResponse(@NonNull Call<AllComplaintResponse> call, @NonNull Response<AllComplaintResponse> response) {
                if (response.body() != null)
                {
                    AllComplaintResponse list = response.body();
                    complaintList.setValue(list.getPayload());
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllComplaintResponse> call, @NonNull Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void getAllUsers()
    {
        Call<AllUsers> call = apiInterface.getAllUsers();
        call.enqueue(new Callback<AllUsers>() {
            @Override
            public void onResponse(@NonNull Call<AllUsers> call, @NonNull Response<AllUsers> response) {
                if (response.body() != null)
                {
                    AllUsers users = response.body();
                    if (users.getStatus() == 200)
                    {
                        userList.setValue(users.getPayload());
                    }
                    else
                    {
                        errorMessage.setValue(users.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllUsers> call, @NonNull Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void getAllFeedBacks()
    {
        Call<AllFeedBackModel> call = apiInterface.getAllFeedback();
        call.enqueue(new Callback<AllFeedBackModel>() {
            @Override
            public void onResponse(@NonNull Call<AllFeedBackModel> call, @NonNull Response<AllFeedBackModel> response) {
                if (response.body() != null)
                {
                    AllFeedBackModel response1 = response.body();
                    if (response1.getStatus() == 200)
                    {
                        feedbackList.setValue(response1.getPayload());
                    }
                    else
                    {
                        errorMessage.setValue(response1.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllFeedBackModel> call, @NonNull Throwable t)
            {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void getComplaintByComplaintId(String complaintId)
    {
        Call<ComplaintResponse> call = apiInterface.getComplaintByComplaintId(complaintId);
        call.enqueue(new Callback<ComplaintResponse>() {
            @Override
            public void onResponse(@NonNull Call<ComplaintResponse> call, @NonNull Response<ComplaintResponse> response) {
                if (response.body() != null)
                {
                    ComplaintResponse complaintResponse = response.body();
                    if (complaintResponse.getStatus() == 200)
                    {
                        complaintData.setValue(complaintResponse.getPayload());
                    }
                    else
                    {
                        errorMessage.setValue(complaintResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ComplaintResponse> call, @NonNull Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void updateUser(String userId, String roleName)
    {
        UpdateRoleModel updateRoleModel = new UpdateRoleModel();
        Roles roles = new Roles();
        roles.setRoleName(roleName);
        updateRoleModel.setUserId(userId);
        updateRoleModel.setRoles(roles);
        Call<UpdateRoleResponse> call = apiInterface.updateRole(updateRoleModel);
        call.enqueue(new Callback<UpdateRoleResponse>()
        {
            @Override
            public void onResponse(@NonNull Call<UpdateRoleResponse> call, @NonNull Response<UpdateRoleResponse> response)
            {
                if (response.body() != null)
                {
                    UpdateRoleResponse updateRoleResponse = response.body();
                    if (updateRoleResponse.getStatus() == 200)
                    {
                        successResponse.setValue(updateRoleResponse.getMessage());
                    }
                    else
                    {
                        errorMessage.setValue(updateRoleResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateRoleResponse> call, @NonNull Throwable t)
            {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void updateComplaint(Complaint updatedComplaint)
    {
        Call<UpdateRoleResponse> call = apiInterface.updateComplaintStatus(updatedComplaint);
        call.enqueue(new Callback<UpdateRoleResponse>() {
            @Override
            public void onResponse(@NonNull Call<UpdateRoleResponse> call, @NonNull Response<UpdateRoleResponse> response) {
                if (response.body() != null)
                {
                    UpdateRoleResponse updateRoleResponse = response.body();
                    if (updateRoleResponse.getStatus() == 200)
                    {
                        successResponse.setValue(updateRoleResponse.getMessage());
                    }
                    else
                    {
                        errorMessage.setValue(updateRoleResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UpdateRoleResponse> call, @NonNull Throwable t) {
                errorMessage.setValue(t.getMessage());
            }
        });
    }

    public void getAllReport(String from, String to)
    {
        Call<ReportResponse> call = apiInterface.getAllReports(from, to);
        call.enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReportResponse> call, @NonNull Response<ReportResponse> response)
            {
                if (response.body() != null)
                {
                    ReportResponse reportResponse = response.body();
                    if (reportResponse.getStatus() == 200)
                    {
                        allReportData.setValue(reportResponse.getPayload());
                    }
                    else
                    {
                        errorMessage.setValue(reportResponse.getMessage());
                    }
                }
                if (!response.isSuccessful())
                {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse;
                    try
                    {
                        errorResponse = gson.fromJson(Objects.requireNonNull(response.errorBody()).string(), ErrorResponse.class);
                        errorMessage.setValue(errorResponse.getError());
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReportResponse> call, @NonNull Throwable t)
            {
                errorMessage.setValue(t.getMessage());
            }
        });
    }
}