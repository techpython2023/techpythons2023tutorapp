package com.example.techpythons2023.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techpythons2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class DepartmentsRecyclerAdapter extends RecyclerView.Adapter<DepartmentsRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<DepartmentItem> departmentItemItemArrayList;
    DatabaseReference databaseReference;

    public DepartmentsRecyclerAdapter(Context context, ArrayList<DepartmentItem> departmentItemItemArrayList) {
        this.context = context;
        this.departmentItemItemArrayList = departmentItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.department_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DepartmentItem departments = departmentItemItemArrayList.get(position);

        holder.textName.setText("Dep-name : " + departments.getDepname());
        holder.textName2.setText("Fac-name : " + departments.getFacname());


    }

    @Override
    public int getItemCount() {
        return departmentItemItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textName2;



        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);
            textName2 = itemView.findViewById(R.id.textName2);


            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }


}

