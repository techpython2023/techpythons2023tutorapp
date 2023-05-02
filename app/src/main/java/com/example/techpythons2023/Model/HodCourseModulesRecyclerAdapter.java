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

import com.example.techpythons2023.AllocateModuletoLectureActivity;
import com.example.techpythons2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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

        holder.cozname.setText(modules.getCosname());
        holder.modname.setText(modules.getModname());
        holder.btnasignlec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Selected.value1 = modules.getModname();
                Selected.value2 = modules.getCosname();

                Intent intent = new Intent(context, AllocateModuletoLectureActivity.class);
                context.startActivity(intent);
            }
        });




    }







    @Override
    public int getItemCount() {
        return moduleItemItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView cozname;
        TextView modname;
        Button btnasignlec;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cozname = itemView.findViewById(R.id.cozname);
            modname = itemView.findViewById(R.id.modname);
            btnasignlec = itemView.findViewById(R.id.btnasignlec);
        }
    }






}
