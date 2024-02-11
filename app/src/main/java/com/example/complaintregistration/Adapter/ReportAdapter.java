package com.example.complaintregistration.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.Util.MessageDialog;
import com.example.complaintregistration.databinding.ListItemReportsBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder>
{
    Map<String, List<Complaint>> map;
    List<Map.Entry<String, List<Complaint>>> entries;
    ReportDataAdapter reportDataAdapter;

    public ReportAdapter()
    {

    }

    public void reportList(Map<String, List<Complaint>> map)
    {
        this.map = map;
        entries = new ArrayList<>(map.entrySet());
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReportsBinding binding = ListItemReportsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReportViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position)
    {
        holder.binding.tvMonth.setText(entries.get(position).getKey());

        String key = entries.get(position).getKey();
        List<Complaint> complaints = map.get(key);
        reportDataAdapter = new ReportDataAdapter();
        holder.binding.recyclerviewComplaintReportData.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        reportDataAdapter.reportDataList(complaints);
        holder.binding.recyclerviewComplaintReportData.setAdapter(reportDataAdapter);

        holder.binding.cardComplaint.setOnClickListener(v -> {
            if (complaints == null || complaints.size() == 0)
            {
                MessageDialog.showToast("There is no data found!", holder.itemView.getContext());
            }
            if (holder.binding.recyclerviewComplaintReportData.getVisibility() == View.VISIBLE)
            {
                holder.binding.recyclerviewComplaintReportData.setVisibility(View.GONE);
            }
            else
            {
                holder.binding.recyclerviewComplaintReportData.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return map != null ? map.size() : 0;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder
    {
        ListItemReportsBinding binding;

        public ReportViewHolder(@NonNull ListItemReportsBinding itemView)
        {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
