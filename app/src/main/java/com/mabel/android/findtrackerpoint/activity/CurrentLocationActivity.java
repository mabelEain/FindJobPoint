package com.mabel.android.findtrackerpoint.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mabel.android.findtrackerpoint.R;
import com.mabel.android.findtrackerpoint.adapter.JobRecyclerAdapter;
import com.mabel.android.findtrackerpoint.api.ApiClient;
import com.mabel.android.findtrackerpoint.api.ApiService;
import com.mabel.android.findtrackerpoint.model.Job;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CurrentLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int RESQUEST_CODE = 101;
    Double lat,lng;

    List<String> suggetList = new ArrayList<>();
    List<Job> jobList = new ArrayList<>();
    MaterialSearchBar searchBar;

    ApiService apiService;
    RecyclerView recycler_Search;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    JobRecyclerAdapter jobAdapter, adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);


        lat = getIntent().getDoubleExtra("lat",0.0);
        lng = getIntent().getDoubleExtra("lng",0.0);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        //For Search

        apiService = ApiClient.getClient().create(ApiService.class);

        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint("Search your Company");

        LoadAllJob();

        searchBar.setCardViewElevation(10);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<>();
                for(String search: suggetList){
                    if(search.toLowerCase().contains(searchBar.getText().toLowerCase())){
                        suggest.add(search);
                    }
                }
                searchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if(!enabled){

                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    private void fetchLastLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},RESQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),currentLocation.getLatitude()+"-"+currentLocation.getLongitude(),Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(CurrentLocationActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I'm here");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        mMap.addMarker(markerOptions);
        LatLng latLng2 = new LatLng(lat,lng);
        MarkerOptions markerOptions2 = new MarkerOptions().position(latLng2).title("My Job");
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2,5));
        mMap.addMarker(markerOptions2);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case RESQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    fetchLastLocation();
                }
             break;
        }
    }

    private void startSearch(CharSequence text) {
        List<Job> result = new ArrayList<>();
        for(Job job: jobList)
            if(job.getCompany().contains(text)){
                result.add(job);
                displayOnMap(job);
            }
    }

    private void LoadAllJob() {

        compositeDisposable.add(apiService.getJobs().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Job>>() {
                    @Override
                    public void accept(List<Job> contacts) throws Exception {
                        jobList = contacts;
                        buildSuggestListJ(contacts);
                    }
                }));
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
    private void buildSuggestListJ(List<Job> contacts) {
        for(Job contact: contacts)
            suggetList.add(contact.getCompany());

        searchBar.setLastSuggestions(suggetList);
    }

    private void displayOnMap(Job job){
        mMap.clear();
        lat = job.getGeolocation().latitude;
        lng = job.getGeolocation().longitude;
        LatLng latLng2 = new LatLng(lat,lng);
        mMap.addMarker( new MarkerOptions().position(latLng2).title("New Point"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2, 12.0f));
    }

}
