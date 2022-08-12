package com.progga.maternalcareadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private Toolbar mToolbar;
    ActionBarDrawerToggle toggle;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference ref,refAdminAccess;
    private RelativeLayout adminsLayout;
    public String UserStatus,stUserName,category,currentUserId,currentUserGmail;

    private TextView tvUserName,tvUserEmail;
    private LinearLayout queryans,manageUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();

        mAuth=FirebaseAuth.getInstance();
        ref= FirebaseDatabase.getInstance("https://maternal-care-fa0e8-default-rtdb.firebaseio.com/").getReference();
        currentUser=mAuth.getCurrentUser();

        mToolbar=(Toolbar)findViewById(R.id.main_activity_toolbar);
        drawerLayout= findViewById(R.id.drawer);
        navigationView=findViewById(R.id.navigationView);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Maternal Care");
        toggle=new ActionBarDrawerToggle(this,drawerLayout,mToolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser != null && currentUser.isEmailVerified()) {
            VerifyUserExistance();
        } else {
            LoginActivity();
        }
    }

    private void initialization() {
        adminsLayout=(RelativeLayout)findViewById(R.id.adminUI);
        queryans=(LinearLayout)findViewById(R.id.btnQuery);
        manageUser=(LinearLayout)findViewById(R.id.btnManageUser);

        tvUserEmail=(TextView)findViewById(R.id.tvUserEmail);
        tvUserName=(TextView)findViewById(R.id.tvUserName);
    }

    private void VerifyUserExistance() {
        currentUserId=mAuth.getCurrentUser().getUid();
        currentUserGmail=mAuth.getCurrentUser().getEmail();

        ref.child("Admin").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("name").exists()){
                    if(dataSnapshot.child("access").getValue().toString().equals("active")) {
                        stUserName = dataSnapshot.child("name").getValue().toString();
                        tvUserName.setText(stUserName);
                        tvUserEmail.setText(currentUserGmail);

                        queryans.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent queryIntent= new Intent(MainActivity.this,queryActivity.class);
                                startActivity(queryIntent);                            }
                        });
                        manageUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent queryIntent= new Intent(MainActivity.this,manageAdminActivity.class);
                                startActivity(queryIntent);
                            }
                        });
                    }else
                    {
                        adminsLayout.setVisibility(View.INVISIBLE);
                        Toast.makeText(MainActivity.this,"Your Current Status is Inactive",Toast.LENGTH_LONG).show();
                    }

                }else{
                    SendUserToSettingsActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendUserToSettingsActivity() {
        Intent settingsIntent= new Intent(MainActivity.this,settingsActivity.class);
        settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(settingsIntent);
        finish();
    }

    private void LoginActivity() {
        Intent loginIntent= new Intent(MainActivity.this,loginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.menuQuery){
            Intent queryIntent= new Intent(MainActivity.this,queryActivity.class);
            startActivity(queryIntent);
        }
        if(menuItem.getItemId() == R.id.menuSttings){
            Intent queryIntent= new Intent(MainActivity.this,manageAdminActivity.class);
            startActivity(queryIntent);
        }
        if(menuItem.getItemId() == R.id.menuLogOut){
            mAuth.signOut();
            LoginActivity();
        }
        return false;
    }
}