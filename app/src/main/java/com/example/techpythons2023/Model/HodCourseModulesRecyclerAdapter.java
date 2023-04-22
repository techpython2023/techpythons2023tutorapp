package com.example.techpythons2023.Model;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techpythons2023.AddModuleActivity;
import com.example.techpythons2023.AdddepActivity;
import com.example.techpythons2023.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class HodCourseModulesRecyclerAdapter extends RecyclerView.Adapter<HodCourseModulesRecyclerAdapter.ViewHolder> {


    Context context;
    ArrayList<ModuleItem> moduleItemItemArrayList;
    DatabaseReference databaseReference;

    public HodCourseModulesRecyclerAdapter(Context context, ArrayList<ModuleItem> moduleItemItemArrayList) {
        this.context = context;
        this.moduleItemItemArrayList = moduleItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hodcozmodule_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModuleItem modules = moduleItemItemArrayList.get(position);

        holder.textcozname.setText(modules.getCosname());
        holder.textmodname.setText(modules.getModname());

        holder.btnasignlec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Selected.value1 = modules.getModname();
                Selected.value2 = modules.getCosname();

            }
        });


    }







    @Override
    public int getItemCount() {
        return moduleItemItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textcozname;
        TextView textmodname;



        Button btnasignlec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textcozname = itemView.findViewById(R.id.textcozname);
            textmodname = itemView.findViewById(R.id.textmodname);


            btnasignlec = itemView.findViewById(R.id.btnasignlec);
        }
    }






}
