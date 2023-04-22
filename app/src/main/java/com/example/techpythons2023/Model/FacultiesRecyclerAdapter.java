package com.example.techpythons2023.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techpythons2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FacultiesRecyclerAdapter extends RecyclerView.Adapter<FacultiesRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<FacultyItem> facultyItemItemArrayList;
    DatabaseReference databaseReference;

    public FacultiesRecyclerAdapter(Context context, ArrayList<FacultyItem> facultyItemItemArrayList) {
        this.context = context;
        this.facultyItemItemArrayList = facultyItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.faculty_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FacultyItem faculties = facultyItemItemArrayList.get(position);

        holder.textName.setText("Name : " + faculties.getFacultyname());

    }

    @Override
    public int getItemCount() {
        return facultyItemItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;


        Button buttonDelete;
        Button buttonUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.textName);

            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            buttonUpdate = itemView.findViewById(R.id.buttonUpdate);
        }
    }


}

