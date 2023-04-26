package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.Users;
import com.example.techpythons2023.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {


    private TextView textregister;

    private EditText Email, Password;
    private Button LoginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        textregister = findViewById(R.id.textregister);


        LoginButton = (Button) findViewById(R.id.loginbtn);
        Email = (EditText) findViewById(R.id.email);
        Password = (EditText) findViewById(R.id.password);




        textregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LoginUser();
            }
        });


    }



    private void LoginUser()
    {
        String email = Email.getText().toString().toLowerCase(Locale.ROOT).replace(".", ",");
        String password = Password.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {

            AllowAccessToAccount(email, password);
        }
    }



    private void AllowAccessToAccount(final String email, final String password)
    {



        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if (dataSnapshot.child("Users").child(email).exists())
                {

                    Users usersData = dataSnapshot.child("Users").child(email).getValue(Users.class);

                    if (usersData.getEmail().equals(email))
                    {
                        if (usersData.getPassword().equals(password))
                        {

                            Toast.makeText(LoginActivity.this, usersData.getRole(), Toast.LENGTH_SHORT).show();


                            if (usersData.getRole().equals("Tutor"))
                            {
                                Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }

                            if (usersData.getRole().equals("Admin"))
                            {
                                Toast.makeText(LoginActivity.this, "Admin logged in Successfully...", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }

                            if (usersData.getRole().equals("Hod"))
                            {
                                Toast.makeText(LoginActivity.this, "Hod logged in Successfully...", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, HodActivity.class);
                                Prevalent.currentOnlineUser = usersData;
                                startActivity(intent);
                            }

                            if (usersData.getRole().equals("Lecture"))
                            {
                                Toast.makeText(LoginActivity.this, "Lecture logged in Successfully...", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, LectureActivity.class);
                                Prevalent.currentOnlineUser = usersData;

                                startActivity(intent);
                            }
                        }
                        else
                        {
                            Intent i = new Intent(LoginActivity.this, LoginActivity.class);
                            startActivity(i);
                            Toast.makeText(LoginActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Account with this " + email + " do not exists.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }
}