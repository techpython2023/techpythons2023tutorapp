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
import java.util.Locale;

public class AdddepActivity extends AppCompatActivity {



    DatabaseReference databaseReference;
    Button  deps,submitbtn;
    EditText Depname;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddep);
        deps = (Button) findViewById(R.id.backtodepbtn);
        Depname =(EditText) findViewById((R.id.depName));
        spinner = findViewById((R.id.facultiesspiner));
        submitbtn = findViewById((R.id.submitbtn));


        deps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(AdddepActivity.this, DepartmentsActivity.class);
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

        listfaculties();

    }

    public void listfaculties(){
        databaseReference.child("Faculties").addValueEventListener(new ValueEventListener()
        {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final List<String> Faculties = new ArrayList<String>();

                for ( DataSnapshot suggestionSnap : dataSnapshot.getChildren() )
                {
                    String suggestion = suggestionSnap.child("Facultyname").getValue(String.class);
                   Faculties.add(suggestion);

                }//End For()

                //XML TextView variable
                spinner = (Spinner) findViewById(R.id.facultiesspiner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdddepActivity.this, android.R.layout.simple_list_item_1, Faculties);
                spinner.setAdapter(adapter);

            }//End onDataChange()

            public void onCancelled(DatabaseError databaseError)
            {

            }//End onCancelled()

        });

    }


    public void adddep(){
        String facname = spinner.getSelectedItem().toString();
        String depname = Depname.getText().toString();


        if (TextUtils.isEmpty(facname))
        {
            Toast.makeText(this, "Please select faculty", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(depname))
        {
            Toast.makeText(this, "Please write dep name", Toast.LENGTH_SHORT).show();
        }

        else
        {

            Validatedepname(depname,facname);
        }
    }

    public  void Validatedepname(final String depname,final String facname){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (!(dataSnapshot.child("Deparments").child(depname).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("Depname",depname);
                    userdataMap.put("Facname",facname);



                    RootRef.child("Departments").child(depname).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(AdddepActivity.this, "Deparment has been added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AdddepActivity.this, AdddepActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(AdddepActivity.this, "Error occured please check your network", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(AdddepActivity.this,AdddepActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(AdddepActivity.this, "This " + depname + " already exists.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(AdddepActivity.this, AdddepActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   }

}