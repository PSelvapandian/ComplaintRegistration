package com.example.complaintregistration;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.complaintregistration.Adapter.ComplaintListAdapter;
import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.Util.DateTimeHelper;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.Util.SharedPreferenceHelper;
import com.example.complaintregistration.ViewModel.RetrofitViewModel;
import com.example.complaintregistration.databinding.ActivityViewComplaintStatusBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewComplaintStatusActivity extends AppCompatActivity implements ComplaintListAdapter.CompliantInterface
{
    ActivityViewComplaintStatusBinding binding;
    SharedPreferenceHelper sharedPreferenceHelper;
    RetrofitViewModel retrofitViewModel;
    ComplaintListAdapter complaintListAdapter;

    public static final Integer YESTERDAY = -1;
    public static final Integer WEEK = -7;
    public static final Integer MONTH = -29;
    public static final Integer YEAR = -365;
    private int periodTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewComplaintStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(this);
        retrofitViewModel = new ViewModelProvider(this).get(RetrofitViewModel.class);

        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.time_period));
        a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTimePeriod.setAdapter(a);

        binding.spinnerTimePeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        periodTime = 0;
                        getComplaints();
                        break;
                    case 1:
                        periodTime = YESTERDAY;
                        getComplaints();
                        break;
                    case 2:
                        periodTime = WEEK;
                        getComplaints();
                        break;
                    case 3:
                        periodTime = MONTH;
                        getComplaints();
                        break;
                    case 4:
                        periodTime = YEAR;
                        getComplaints();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                MessageDialog.showAlertDialog(s, ViewComplaintStatusActivity.this);
            }
        });

        binding.btnBack.setOnClickListener(v -> finish());
    }

    private void getComplaints()
    {
        int secondPeriodTime = (periodTime == YESTERDAY) ? -1 : 0;
        binding.constraintProgressBar.setVisibility(View.VISIBLE);
        String userId = sharedPreferenceHelper.getUserId();
        retrofitViewModel.getComplaintByUser(userId, DateTimeHelper.getCalculatedDate(periodTime), DateTimeHelper.getCalculatedDate(secondPeriodTime));
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

    }
}
