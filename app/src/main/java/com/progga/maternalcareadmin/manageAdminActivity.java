package com.progga.maternalcareadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class manageAdminActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference queryref,ref,ref2;
    private RecyclerView listView;
    String currentUserId;
    ArrayList<admin> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admin);

        initialization();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    list=new ArrayList<>();
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        list.add(ds.getValue(admin.class));
                    }
                    AdapterClassForAdmin adapterClass=new AdapterClassForAdmin(list);
                    listView.setAdapter(adapterClass);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(manageAdminActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialization() {
        mToolbar=(Toolbar)findViewById(R.id.admins_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Maternal Care");

        mAuth=FirebaseAuth.getInstance();
        currentUserId=mAuth.getCurrentUser().getUid();

        listView=(RecyclerView)findViewById(R.id.lvAdmins);

        ref= FirebaseDatabase.getInstance("https://maternal-care-fa0e8-default-rtdb.firebaseio.com/").getReference().child("Admin");
    }
}