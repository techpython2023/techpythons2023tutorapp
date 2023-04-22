package com.example.techpythons2023.Model;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techpythons2023.AdddepActivity;
import com.example.techpythons2023.DepartmentsActivity;
import com.example.techpythons2023.LoginActivity;
import com.example.techpythons2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LectureModulesRecyclerAdapter extends RecyclerView.Adapter<LectureModulesRecyclerAdapter.ViewHolder> {

    LoginActivity l = new LoginActivity();
    Context context;
    ArrayList<ModuleItem> moduleItemItemArrayList;
    DatabaseReference databaseReference;
    public LectureModulesRecyclerAdapter(Context context, ArrayList<ModuleItem> moduleItemItemArrayList) {
        this.context = context;
        this.moduleItemItemArrayList = moduleItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lecturemodule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModuleItem modules = moduleItemItemArrayList.get(position);

        holder.textcozname.setText(modules.getModname());
        holder.textmodulename.setText(modules.getCosname());

        holder.btnrequestta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Selected.value1 = modules.getModname().toString();
                Selected.value1 = modules.getModname().toString();

            }
        });


    }







    @Override
    public int getItemCount() {
        return moduleItemItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textcozname;
        TextView textmodulename;



        Button buttonDelete;
        Button btnrequestta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textcozname = itemView.findViewById(R.id.textcozname);
            textmodulename = itemView.findViewById(R.id.textmodulename);


            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            btnrequestta = itemView.findViewById(R.id.btnrequestta);
        }
    }






}



