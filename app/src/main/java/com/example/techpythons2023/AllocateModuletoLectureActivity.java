package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.Selected;
import com.example.techpythons2023.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllocateModuletoLectureActivity extends AppCompatActivity {

    Spinner spinner;
    DatabaseReference databaseReference;


    TextView txtmodname;
    Button assignlecbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_moduleto_lecture);
        txtmodname = findViewById(R.id.txtmodname);
        spinner = findViewById((R.id.lecturesspiner));
        assignlecbtn = findViewById(R.id.assignlecbtn);



        txtmodname.setText(Selected.value1);

        databaseReference = FirebaseDatabase.getInstance().getReference();


        listselectedmodule();


        assignlecbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String lecemail = spinner.getSelectedItem().toString();
                String modname = Selected.value1;

                assignlec(lecemail,modname);

            }
        });

    }




    public void listselectedmodule(){
        databaseReference.child("Users").addValueEventListener(new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final List<String> Users = new ArrayList<String>();

                for ( DataSnapshot suggestionSnap : dataSnapshot.getChildren() )
                {
                    String suggestion = suggestionSnap.child("email").getValue(String.class);
                    Users.add(suggestion);

                }//End For()

                //XML TextView variable
                spinner = (Spinner) findViewById(R.id.lecturesspiner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllocateModuletoLectureActivity.this, android.R.layout.simple_list_item_1, Users);
                spinner.setAdapter(adapter);

            }//End onDataChange()

            public void onCancelled(DatabaseError databaseError)
            {

            }//End onCancelled()

        });

    }




    public  void assignlec(final String lecemail,final String modname){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!(dataSnapshot.child("Lecturemodules").child(lecemail + modname).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Modname",modname);
                    userdataMap.put("Cosname",Selected.value2);
                    userdataMap.put("Lecemail",lecemail);
                    userdataMap.put("Lecmodid",lecemail + modname);


                    RootRef.child("Lecturemodules").child(lecemail + modname).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(AllocateModuletoLectureActivity.this, "Deparment has been added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AllocateModuletoLectureActivity.this, AdddepActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(AllocateModuletoLectureActivity.this, "Error occured please check your network", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AllocateModuletoLectureActivity.this,AdddepActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(AllocateModuletoLectureActivity.this, "This " + modname + " already exists.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AllocateModuletoLectureActivity.this, AdddepActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}