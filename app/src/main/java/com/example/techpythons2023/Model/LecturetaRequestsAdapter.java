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

import com.example.techpythons2023.LecturerequestTAActivity;
import com.example.techpythons2023.LecturerequestdetailsActivity;
import com.example.techpythons2023.LoginActivity;
import com.example.techpythons2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LecturetaRequestsAdapter extends RecyclerView.Adapter<LecturetaRequestsAdapter.ViewHolder> {

    Context context;
    ArrayList<TarequestItem> moduleItemItemArrayList;
    DatabaseReference databaseReference;
    public LecturetaRequestsAdapter(Context context, ArrayList<TarequestItem> moduleItemItemArrayList) {
        this.context = context;
        this.moduleItemItemArrayList = moduleItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lecrequest_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TarequestItem modules = moduleItemItemArrayList.get(position);

        holder.txtmodnam.setText(modules.getModname());
        holder.txtreason.setText(modules.getReason());

        holder.reqdetailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Selected.value1 = modules.getRequestid();

                Intent intent = new Intent(context, LecturerequestdetailsActivity.class);
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
        TextView txtreason;

        Button reqdetailbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmodnam = itemView.findViewById(R.id.txtmodname);
            txtreason = itemView.findViewById(R.id.txtreson);
            reqdetailbtn = itemView.findViewById(R.id.reqdetailbtn);

        }
    }






}

