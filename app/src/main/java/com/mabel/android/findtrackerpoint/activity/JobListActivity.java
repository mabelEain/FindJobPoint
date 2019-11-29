package com.mabel.android.findtrackerpoint.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mabel.android.findtrackerpoint.R;
import com.mabel.android.findtrackerpoint.adapter.JobRecyclerAdapter;
import com.mabel.android.findtrackerpoint.model.Job;
import com.mabel.android.findtrackerpoint.api.ApiClient;
import com.mabel.android.findtrackerpoint.api.ApiService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JobListActivity extends AppCompatActivity {

    private static final String TAG = "JobListActivity";

    RecyclerView recyclerView;
    JobRecyclerAdapter adapter;
    ApiService apiService;

    List<Job> mJobs = new ArrayList<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.ic_left_menu_24dp);
        setSupportActionBar(toolbar);

        apiService = ApiClient.getClient().create(ApiService.class);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d(TAG, "onCreate: started.");
        LoadAllJob();
    }

    private void LoadAllJob() {

        compositeDisposable.add(apiService.getJobs().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Job>>() {
                    @Override
                    public void accept(List<Job> jobs) throws Exception {
                        displayListJob(jobs);
                    }
                }));
    }


    private void displayListJob(List<Job> contacts) {
        mJobs = contacts;
        adapter = new JobRecyclerAdapter(this,mJobs);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_toolbar, menu);
        //menu.findItem(R.id.menuLogout).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.menuLogout:

                break;

        }
        return true;
    }
}
