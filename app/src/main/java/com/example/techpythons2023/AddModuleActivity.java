package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class AddModuleActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button mods,submitbtn;
    EditText Modulename;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);
        mods = (Button) findViewById(R.id.backtomodbtn);
        Modulename =(EditText) findViewById((R.id.moduleName));
        spinner = findViewById((R.id.coursesspiner));
        submitbtn = findViewById((R.id.submitbtn));


        mods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AddModuleActivity.this, ModulesActivity.class);
                startActivity(i);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                adddep();

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        listcourses();

    }

    public void listcourses(){
        databaseReference.child("Courses").addValueEventListener(new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final List<String> Courses = new ArrayList<String>();

                for ( DataSnapshot suggestionSnap : dataSnapshot.getChildren() )
                {
                    String suggestion = suggestionSnap.child("Cosname").getValue(String.class);
                    Courses.add(suggestion);

                }//End For()

                //XML TextView variable
                spinner = (Spinner) findViewById(R.id.coursesspiner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddModuleActivity.this, android.R.layout.simple_list_item_1, Courses);
                spinner.setAdapter(adapter);

            }//End onDataChange()

            public void onCancelled(DatabaseError databaseError)
            {

            }//End onCancelled()

        });

    }


    public void adddep(){
        String cosname = spinner.getSelectedItem().toString();
        String modname = Modulename.getText().toString();


        if (TextUtils.isEmpty(cosname))
        {
            Toast.makeText(this, "Please select course", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(modname))
        {
            Toast.makeText(this, "Please write module name", Toast.LENGTH_SHORT).show();
        }

        else
        {

            Validatedepname(modname,cosname);
        }
    }

    public  void Validatedepname(final String modname,final String cosname){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!(dataSnapshot.child("Modules").child(modname).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Cosname",cosname);
                    userdataMap.put("Modname",modname);



                    RootRef.child("Modules").child(modname).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(AddModuleActivity.this, "Module has been added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AddModuleActivity.this, AdddepActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(AddModuleActivity.this, "Error occured please check your network", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AddModuleActivity.this,AddModuleActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(AddModuleActivity.this, "This " + modname + " already exists.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AddModuleActivity.this, AddModuleActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}