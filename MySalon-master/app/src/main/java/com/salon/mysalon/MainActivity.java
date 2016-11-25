package com.salon.mysalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.salon.mysalon.model.Franchise;
import com.salon.mysalon.widgets.FranchiseListAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Bind(R.id.progress)
    ProgressBar progress;
    @Bind(R.id.toolBar)
    Toolbar toolBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.textViewNoData)
    TextView textViewNoData;
    @Bind(R.id.listLayout)
    FrameLayout listLayout;
    private GoogleMap mMap;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = mDatabase.child("franchises");
    FranchiseListAdapter franchiseListAdapter;
    LinearLayoutManager linearLayoutManager;
    private Menu menu;
    private MenuItem mapMenuItem;
    private MenuItem listMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);

        franchiseListAdapter = new FranchiseListAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(franchiseListAdapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        progress.setVisibility(View.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Toast.makeText(MainActivity.this, dataSnapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
                ArrayList<Franchise> franchiseArrayList = new ArrayList<>();
                for (DataSnapshot singleFranchiseSnapshot : dataSnapshot.getChildren()) {
                    String address = (String) singleFranchiseSnapshot.child("address").getValue();
                    double lat = Double.parseDouble(singleFranchiseSnapshot.child("lat").getValue(String.class));
                    double lng = Double.parseDouble(
                            singleFranchiseSnapshot.child("lng").getValue(String.class));
                    String name = (String) singleFranchiseSnapshot.child("name").getValue();
                    int availableSlots = singleFranchiseSnapshot.child("available_slots").getValue(Integer.class);

                    Franchise franchise = new Franchise();
                    franchise.setAddress(address);
                    franchise.setLat(lat);
                    franchise.setLng(lng);
                    franchise.setName(name);
                    franchise.setAvailableSlots(availableSlots);

                    franchiseArrayList.add(franchise);
                }
                franchiseListAdapter.addAll(franchiseArrayList);

                addMarker(franchiseArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progress.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void addMarker(ArrayList<Franchise> franchiseArrayList) {
        mMap.clear();
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for (int i = 0; i < franchiseArrayList.size(); i++) {
            Franchise franchise = franchiseArrayList.get(i);
            LatLng latLng = new LatLng(franchise.getLat(), franchise.getLng());
            mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cut_24dp))
                    .position(latLng)
                    .title(franchise.getName())
                    .snippet("Available slots - " + franchise.getAvailableSlots()));
            builder.include(latLng);

//            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }

        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 12% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

        mMap.moveCamera(cu);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(MainActivity.this, FranchiseDetailsActivity.class);
                startActivity(intent);
            }
        });

        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        mapMenuItem = menu.findItem(R.id.action_map);
        listMenuItem = menu.findItem(R.id.action_list);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_map) {
            listLayout.setVisibility(View.INVISIBLE);
            mapMenuItem.setVisible(false);
            listMenuItem.setVisible(true);
            return true;
        }
        if (id == R.id.action_list) {
            listLayout.setVisibility(View.VISIBLE);
            mapMenuItem.setVisible(true);
            listMenuItem.setVisible(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
