package com.mabel.android.findtrackerpoint.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mabel.android.findtrackerpoint.R;
import com.mabel.android.findtrackerpoint.model.Job;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageView;
    TextView txtName,txtJobId;
    Button btnMap;

    GoogleSignInClient mGoogleSignInClient;
    String mJobId;
    Job mJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.ic_left_menu_24dp);
        setSupportActionBar(toolbar);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Object myJob = getIntent().getSerializableExtra("JobObj");
        mJob = (Job) myJob;
        mJobId = getIntent().getStringExtra("JobId");
        imageView = findViewById(R.id.imageView);
        txtName = findViewById(R.id.textView);
        txtJobId = findViewById(R.id.textView2);
        btnMap = findViewById(R.id.btn_map);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_map:
                       // signOut();
                        ShowMeMap();
                        break;

                }
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(ProfileActivity.this);
        if (acct != null) {
            //String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            //String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            //String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            txtName.setText(personGivenName);
            txtJobId.setText("Job Number:"+mJob.getJobid());
            Glide.with(this).load(personPhoto)
                    .apply(new RequestOptions().override(200, 200))
                    .into(imageView);
        }

    }

    private void ShowMeMap(){

        Toast.makeText(this,mJob.getGeolocation().latitude+"-"+mJob.getGeolocation().latitude,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CurrentLocationActivity.class);
        intent.putExtra("Lat", mJob.getGeolocation().latitude);
        intent.putExtra("Lng", mJob.getGeolocation().longitude);
        startActivity(intent);
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ProfileActivity.this,"Sign Out Successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileActivity.this, GoogleSignInActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_toolbar, menu);
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
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                signOut();
                break;

        }
        return true;
    }
}
