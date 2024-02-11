package com.example.complaintregistration;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.complaintregistration.Adapter.ComplaintListAdapter;
import com.example.complaintregistration.Adapter.UserListAdapter;
import com.example.complaintregistration.Enum.RolesEnum;
import com.example.complaintregistration.Interface.UserInterface;
import com.example.complaintregistration.ModelClasses.CustomUser;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityViewAllUsersBinding;

import java.util.List;

public class ViewUsersActivity extends AppCompatActivity implements UserInterface
{
    ActivityViewAllUsersBinding binding;
    SharedPreferenceHelper sharedPreferenceHelper;
    RetrofitViewModel retrofitViewModel;
    UserListAdapter userListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(this);
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        getAllUsers();

        retrofitViewModel.userList.observe(this, list -> {
            if (list != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                if (list.size() > 0)
                {
                    setUserList(list);
                }
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, ViewUsersActivity.this);
            }
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void setUserList(List<CustomUser> list)
    {
        userListAdapter = new UserListAdapter();
        binding.recyclerviewAllUsers.setLayoutManager(new LinearLayoutManager(this));
        userListAdapter.userList(list, this);
        binding.recyclerviewAllUsers.setAdapter(userListAdapter);
        binding.emptyString.setVisibility(list.size() > 0 ? View.GONE : View.VISIBLE);
    }

    private void getAllUsers()
    {
        binding.constraintProgressBar.setVisibility(View.VISIBLE);
        retrofitViewModel.getAllUsers();
    }

    @Override
    public void passUserId(long userId)
    {
        if (sharedPreferenceHelper.getRole().equalsIgnoreCase(RolesEnum.ADMIN.name()))
        {
            startActivity(new Intent(this, ViewAndUpdateUserData.class).putExtra("USER_ID", String.valueOf(userId)));
        }
    }
}
