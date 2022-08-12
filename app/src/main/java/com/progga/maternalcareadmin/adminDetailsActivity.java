package com.progga.maternalcareadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminDetailsActivity extends AppCompatActivity {
    private TextView name,email,status;
    private Button accept,decline;
    String currentUserId,gname,gemail,gstatus;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference ref,refaccecpt,refdenied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_details);

        initialization();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gname=snapshot.child("name").getValue().toString();
                gemail=snapshot.child("email").getValue().toString();
                gstatus=snapshot.child("access").getValue().toString();

                name.setText(gname);
                email.setText(gemail);
                status.setText(gstatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUserId.equals("wb5iuo6bX5OJ2oTzNnDlgCqOeBj1")){
                    Toast.makeText(adminDetailsActivity.this,"Can't Change",Toast.LENGTH_LONG).show();
                }else{
                    refaccecpt.setValue("active").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(adminDetailsActivity.this,"Set active",Toast.LENGTH_SHORT).show();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(adminDetailsActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUserId.equals("wb5iuo6bX5OJ2oTzNnDlgCqOeBj1")){
                    Toast.makeText(adminDetailsActivity.this,"Can't Change",Toast.LENGTH_LONG).show();
                }else{
                    refdenied.setValue("inactive").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(adminDetailsActivity.this,"Set inactive",Toast.LENGTH_SHORT).show();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(adminDetailsActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initialization() {
        mToolbar=(Toolbar)findViewById(R.id.adminsDetails_activity_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Maternal Care");

        mAuth=FirebaseAuth.getInstance();
        currentUserId=getIntent().getExtras().getString("uid");

        name=(TextView)findViewById(R.id.tvNameofAdmin);
        email=(TextView)findViewById(R.id.tvemailOfAdmin);
        status=(TextView)findViewById(R.id.tvCheckAdmin);

        accept=(Button)findViewById(R.id.acceptAdmin);
        decline=(Button)findViewById(R.id.declineAdmin);


        ref= FirebaseDatabase.getInstance("https://maternal-care-fa0e8-default-rtdb.firebaseio.com/").getReference().child("Admin").child(currentUserId);
        refaccecpt= FirebaseDatabase.getInstance("https://maternal-care-fa0e8-default-rtdb.firebaseio.com/").getReference().child("Admin").child(currentUserId).child("access");
        refdenied= FirebaseDatabase.getInstance("https://maternal-care-fa0e8-default-rtdb.firebaseio.com/").getReference().child("Admin").child(currentUserId).child("access");
    }
}