package com.salon.mysalon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.salon.mysalon.widgets.ServiceListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SelectServiceActivity extends AppCompatActivity {

    ServiceListAdapter serviceListAdapter;
    LinearLayoutManager linearLayoutManager;
    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<Service> services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_service);
        ButterKnife.bind(this);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        serviceListAdapter = new ServiceListAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(serviceListAdapter);

        String[] servicesString = new String[]{"Haircut", "Deep Cleanser Facial", "Organic Facial", "Himalayan Slat Glow", "Sugar Scrub"};
        String[] servicesTimeString = new String[]{"20 min", "1:10 hrs", "1:30 hrs", "55 min", "45 min"};
        services = new ArrayList<>();

        for (int i = 0; i < servicesString.length; i++) {
            Service service = new Service(i, servicesString[i], servicesTimeString[i], false);
            services.add(service);
        }

        serviceListAdapter.addAll(services);
    }

    public void reviewingSlots(View view) {
        String data = "";
        List<Service> stList = serviceListAdapter.getService();

        for (int i = 0; i < stList.size(); i++) {
            Service service = stList.get(i);
            if (service.isSelected() == true)
                data = data + "\n" + service.getName().toString();
        }

        Toast.makeText(SelectServiceActivity.this,
                "Selected Students: \n" + data, Toast.LENGTH_LONG)
                .show();
    }
}
