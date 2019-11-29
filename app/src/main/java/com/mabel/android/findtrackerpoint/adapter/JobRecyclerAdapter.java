package com.mabel.android.findtrackerpoint.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mabel.android.findtrackerpoint.R;
import com.mabel.android.findtrackerpoint.activity.ProfileActivity;
import com.mabel.android.findtrackerpoint.model.Job;

import java.util.List;

public class JobRecyclerAdapter extends RecyclerView.Adapter<JobRecyclerAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private List<Job> mJobs;
    private Context mContext;

    public JobRecyclerAdapter(Context context, List<Job> jobs) {
        mJobs = jobs;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.jobId.setText("Job Number:"+mJobs.get(position).getJobid());
        holder.company.setText("Company: "+mJobs.get(position).getCompany());
        holder.address.setText("Address: "+mJobs.get(position).getAddress());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("JobObj", mJobs.get(position));
                intent.putExtra("JobId",mJobs.get(position).getJobid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJobs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView jobId,company,address;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            jobId = itemView.findViewById(R.id.txt_jobId);
            company = itemView.findViewById(R.id.txt_company);
            address = itemView.findViewById(R.id.txt_address);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

    }
}