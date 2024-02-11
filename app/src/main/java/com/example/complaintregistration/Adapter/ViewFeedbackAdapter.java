package com.example.complaintregistration.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.complaintregistration.ModelClasses.FeedBack;
import com.example.complaintregistration.databinding.ListItemViewFeedbackBinding;

import java.util.List;

public class ViewFeedbackAdapter extends RecyclerView.Adapter<ViewFeedbackAdapter.ViewFeedbackViewHolder>
{
    List<FeedBack> list;

    public ViewFeedbackAdapter()
    {

    }

    public void feedbackList(List<FeedBack> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewFeedbackAdapter.ViewFeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemViewFeedbackBinding binding = ListItemViewFeedbackBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewFeedbackViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewFeedbackAdapter.ViewFeedbackViewHolder holder, int position) {
        holder.binding.feedback.setText(list.get(position).getFeedbackMsg());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewFeedbackViewHolder extends RecyclerView.ViewHolder
    {
        ListItemViewFeedbackBinding binding;

        public ViewFeedbackViewHolder(@NonNull ListItemViewFeedbackBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
