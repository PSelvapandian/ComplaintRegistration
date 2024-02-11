package com.example.complaintregistration.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintregistration.Enum.ComplaintStatus;
import com.example.complaintregistration.ModelClasses.Complaint;
import com.example.complaintregistration.databinding.ListItemReportDataBinding;

import java.util.List;

public class ReportDataAdapter extends RecyclerView.Adapter<ReportDataAdapter.ReportDataViewHolder>
{
    List<Complaint> list;

    public ReportDataAdapter()
    {

    }

    public void reportDataList(List<Complaint> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public ReportDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemReportDataBinding binding = ListItemReportDataBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReportDataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportDataViewHolder holder, int position)
    {
        holder.binding.complaint.setText(list.get(position).getComplaintDetails() != null && !list.get(position).getComplaintDetails().isEmpty() ? list.get(position).getComplaintDetails() : "");
        holder.binding.complaintStatus.setText(list.get(position).getComplaintStatus() != null && !list.get(position).getComplaintStatus().isEmpty() ? list.get(position).getComplaintStatus() : ComplaintStatus.PENDING.name());
        holder.binding.address.setText(list.get(position).getLandmark() != null && !list.get(position).getLandmark().isEmpty() ? list.get(position).getLandmark() : "");
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ReportDataViewHolder extends RecyclerView.ViewHolder
    {
        ListItemReportDataBinding binding;

        public ReportDataViewHolder(@NonNull ListItemReportDataBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
