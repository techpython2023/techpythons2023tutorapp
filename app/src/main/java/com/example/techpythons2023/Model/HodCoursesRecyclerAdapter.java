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

import com.example.techpythons2023.HodcozmodulesActivity;
import com.example.techpythons2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HodCoursesRecyclerAdapter extends RecyclerView.Adapter<HodCoursesRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<HodCourseItem> hodcourseItemItemArrayList;
    DatabaseReference databaseReference;

    public HodCoursesRecyclerAdapter(Context context, ArrayList<HodCourseItem> hodcourseItemItemArrayList) {
        this.context = context;
        this.hodcourseItemItemArrayList = hodcourseItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hodcourse_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        HodCourseItem hodcourses = hodcourseItemItemArrayList.get(position);

        holder.textcozname.setText(hodcourses.getCosname());
        holder.textdepname.setText( hodcourses.getDepname());


        holder.btncosmods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HodcozmodulesActivity.class);
                Selected.value1 = hodcourses.getCosname();
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return hodcourseItemItemArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textcozname;
        TextView textdepname;



        Button btncosmods;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textcozname = itemView.findViewById(R.id.textcozname);
            textdepname = itemView.findViewById(R.id.textdepname);
            btncosmods = itemView.findViewById(R.id.btncosmods);

        }
    }


}



