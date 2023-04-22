
package com.example.techpythons2023;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;

        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;



        import java.util.HashMap;
        import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputEmail,  InputPassword;
    private EditText InputStudentnum;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputEmail = (EditText) findViewById(R.id.register_username_input);
        InputStudentnum = (EditText) findViewById(R.id.register_studentnum);
        InputPassword = (EditText) findViewById(R.id.register_password_input);


        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
    }



    private void CreateAccount()
    {
        String studentnum = InputStudentnum.getText().toString();
        String email = InputEmail.getText().toString().toLowerCase(Locale.ROOT).replace(".", ",");
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(studentnum))
        {
            Toast.makeText(this, "Please write your student number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {


            ValidatephoneNumber(studentnum,email,password);
        }
    }



    private void ValidatephoneNumber(final String studentnum,final String email, final String password)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                String r = "";
                if (!(dataSnapshot.child("Users").child(email).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("studentnumber",studentnum);
                    userdataMap.put("email", email);
                    userdataMap.put("password", password);


                    if(email.equals("admin@gmail,com"))
                    {
                        r = "Admin";
                    }
                    else
                    {
                        r = "Tutor";
                    }
                    userdataMap.put("role", r);


                    RootRef.child("Users").child(email).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(i);
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this, "Network Error: Sorry we couldn't create your account", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegisterActivity.this, RegisterActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "This " + email + " already exists.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterActivity.this, RegisterActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
