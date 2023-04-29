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
import com.example.techpythons2023.HodappdetailsActivity;
import com.example.techpythons2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class HodappsAdapter extends RecyclerView.Adapter<HodappsAdapter.ViewHolder> {

    Context context;
    ArrayList<ApplicationItem> moduleItemItemArrayList;
    DatabaseReference databaseReference;

    public HodappsAdapter(Context context, ArrayList<ApplicationItem> moduleItemItemArrayList) {
        this.context = context;
        this.moduleItemItemArrayList = moduleItemItemArrayList;
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hodapp_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApplicationItem modules = moduleItemItemArrayList.get(position);

        holder.txtmodnam.setText(modules.getModname());
        holder.txtreason.setText(modules.getStatus());
        holder.txtqual.setText(modules.getCosname());


        holder.appdetailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Selected.value1 = modules.getAppid();

                Intent intent = new Intent(context, HodappdetailsActivity.class);
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

        Button appdetailbtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtmodnam = itemView.findViewById(R.id.txtmodname);
            txtreason = itemView.findViewById(R.id.txtreson);
            txtqual = itemView.findViewById(R.id.txtqual);

            appdetailbtn = itemView.findViewById(R.id.appdetailbtn);

        }
    }
}






