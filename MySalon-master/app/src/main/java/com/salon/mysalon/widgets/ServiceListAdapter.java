package com.salon.mysalon.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.salon.mysalon.R;
import com.salon.mysalon.model.Service;
import com.salon.mysalon.utils.ReusableClass;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by anirbanjana on 23/11/2016.
 */
public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Service> services;

    public ServiceListAdapter(Context context) {
        this.context = context;
        services = new ArrayList<>();
    }

    @Override
    public ServiceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_list_row, parent, false);
        ServiceListAdapter.MyViewHolder myViewHolder = new ServiceListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ServiceListAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(services.get(position).getName());
        holder.checkBox.setTag(services);
        holder.subTitle.setText("Approx time - " + ReusableClass.getHoursMin(services.get(position).getTime()) + " hrs");

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                services.get(position).setSelected(buttonView.isChecked());
                services.get(position).setSelected(buttonView.isChecked());
            }
        });

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked())
                    holder.checkBox.setChecked(false);
                else
                    holder.checkBox.setChecked(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void addAll(List<Service> serviceList) {
        services.addAll(serviceList);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.subTitle)
        TextView subTitle;
        @Bind(R.id.mainLayout)
        LinearLayout mainLayout;
        @Bind(R.id.checkBox)
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    // method to access in activity after updating selection
    public List<Service> getService() {
        return services;
    }
}
