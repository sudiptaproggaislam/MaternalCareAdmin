package com.progga.maternalcareadmin;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterClass extends  RecyclerView.Adapter<AdapterClass.MyViewHolder>{

    ArrayList<deal> list;
    private Context context;
    private String booksCategory;
    // private TextView textView;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    String currentUserId;

    public AdapterClass(Context context) {
        this.context = context;
    }

    public AdapterClass(ArrayList<deal> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_holder,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.ques.setText(list.get(position).getQuestion());
        if(list.get(position).getAnswers().equals("new")){
            holder.av.setVisibility(View.VISIBLE);
        }else{
            holder.av.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView ques;
        Button button;
        CircleImageView av;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ques=itemView.findViewById(R.id.nameDoc);
            button=itemView.findViewById(R.id.btnrv);
            av=itemView.findViewById(R.id.imgBtnAvailablity);

            button.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(),answerActivity.class);
            intent.putExtra("uid",list.get(getAdapterPosition()).getUid());
            intent.putExtra("key",list.get(getAdapterPosition()).getKey());
            v.getContext().startActivity(intent);
        }
    }

}


