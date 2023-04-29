package com.example.techpythons2023.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techpythons2023.ApplyActivity;
import com.example.techpythons2023.LecturerequestdetailsActivity;
import com.example.techpythons2023.R;
import com.example.techpythons2023.UseropeningsActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserOpenignsAdapter extends RecyclerView.Adapter<UserOpenignsAdapter.ViewHolder> {

    Context context;
    ArrayList<TaopeningItem> moduleItemItemArrayList;
    DatabaseReference databaseReference;
    public UserOpenignsAdapter(Context context, ArrayList<TaopeningItem> moduleItemItemArrayList) {
        this.context = context;
        this.moduleItemItemArrayList = moduleItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.useropening_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TaopeningItem modules = moduleItemItemArrayList.get(position);

        holder.txtmodnam.setText(modules.getModname());
        holder.txtreason.setText(modules.Minmark +" %");
        holder.txtqual.setText(modules.getMinqual());


        holder.reqdetailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Selected.value1 = modules.getJobid();

                Intent intent = new Intent(context, ApplyActivity.class);
                context.startActivity(intent);
            }
        });





    }







    @Override
    public int getItemCount() {
        return moduleItemItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtmodnam;
        TextView txtreason,txtqual;

        Button reqdetailbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmodnam = itemView.findViewById(R.id.txtmodname);
            txtreason = itemView.findViewById(R.id.txtreson);
            txtqual = itemView.findViewById(R.id.txtqual);

            reqdetailbtn = itemView.findViewById(R.id.reqdetailbtn);

        }
    }






}