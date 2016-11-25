package com.salon.mysalon.widgets;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salon.mysalon.FranchiseDetailsActivity;
import com.salon.mysalon.R;
import com.salon.mysalon.model.Franchise;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirbanjana on 22/11/2016.
 */
public class FranchiseListAdapter extends RecyclerView.Adapter<FranchiseListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Franchise> franchiseArrayList;


    public FranchiseListAdapter(Context context) {
        this.context = context;
        franchiseArrayList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.franchise_list_row, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Franchise franchise = franchiseArrayList.get(position);
        holder.title.setText(franchise.getName());
        holder.subTitle.setText(franchise.getAddress());
        holder.textViewAvailability.setText("23.41 km away");

        if (franchise.getAvailableSlots() > 0) {
            holder.textViewAvailability.setText("Slots - " + franchise.getAvailableSlots());
            holder.availability_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.round_green));
        } else {
            holder.textViewAvailability.setText("No Slots");
            holder.availability_bg.setBackground(ContextCompat.getDrawable(context, R.drawable.round_red));
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FranchiseDetailsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return franchiseArrayList.size();
    }

    public void addAll(ArrayList<Franchise> responses) {
        clearAll();
        franchiseArrayList.addAll(responses);
        notifyDataSetChanged();
    }

    public void clearAll() {
        franchiseArrayList.clear();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.subTitle)
        TextView subTitle;
        @Bind(R.id.subSubTitle)
        TextView subSubTitle;
        @Bind(R.id.textViewAvailability)
        TextView textViewAvailability;
        @Bind(R.id.mainLayout)
        LinearLayout mainLayout;
        @Bind(R.id.availability_bg)
        FrameLayout availability_bg;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
