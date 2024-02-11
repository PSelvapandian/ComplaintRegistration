package com.example.complaintregistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.complaintregistration.Adapter.ComplaintListAdapter;
import com.example.complaintregistration.Enum.RolesEnum;
import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityViewAllComplaintsBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewAllComplaintsActivity extends AppCompatActivity implements ComplaintListAdapter.CompliantInterface
{
    ActivityViewAllComplaintsBinding binding;
    SharedPreferenceHelper sharedPreferenceHelper;
    RetrofitViewModel retrofitViewModel;
    ComplaintListAdapter complaintListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllComplaintsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(this);
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        getAllComplaints();

        retrofitViewModel.complaintList.observe(this, list -> {
            if (list != null)
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                setComplaintList(list.size() > 0 ? list : new ArrayList<>());
            }
        });

        retrofitViewModel.errorMessage.observe(this, s -> {
            if (s != null && !s.isEmpty())
            {
                binding.constraintProgressBar.setVisibility(View.GONE);
                MessageDialog.showAlertDialog(s, ViewAllComplaintsActivity.this);
            }
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void getAllComplaints()
    {
        binding.constraintProgressBar.setVisibility(View.VISIBLE);
        retrofitViewModel.getAllComplaints();
    }

    private void setComplaintList(List<Complaint> list)
    {
        complaintListAdapter = new ComplaintListAdapter();
        binding.recyclerviewComplaintStatus.setLayoutManager(new LinearLayoutManager(this));
        complaintListAdapter.complaintList(list, this);
        binding.recyclerviewComplaintStatus.setAdapter(complaintListAdapter);
        binding.emptyString.setVisibility(list.size() > 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void passComplaintId(Long complaintId)
    {
        if (sharedPreferenceHelper.getRole().equalsIgnoreCase(RolesEnum.OFFICER.name()))
        {
            startActivity(new Intent(this, ViewAndUpdateComplaintActivity.class).putExtra("COMPLAINT_ID", String.valueOf(complaintId)));
        }
    }
}
