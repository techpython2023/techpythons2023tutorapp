package com.example.techpythons2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.techpythons2023.Model.Selected;
import com.example.techpythons2023.Model.TaopeningItem;
import com.example.techpythons2023.Model.TarequestItem;
import com.example.techpythons2023.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ApplyActivity extends AppCompatActivity {
    private ImageView idcopy,qual;
    private StorageReference ProductImagesRef,ProductImagesRef2;
    private DatabaseReference ProductsRef;
    private String saveCurrentDate, saveCurrentTime;
    private Button submitbtn;
    private Uri ImageUri,ImageUri2;
    private String productRandomKey, downloadImageUrl,downloadImageUrl2;
    private static final int GalleryPick = 1;
    private static final int GalleryPick2 = 2;

    EditText Modmark,Fullname;

    TextView modnamev,cosnamev;

    Integer modm = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);

        idcopy = findViewById(R.id.imageView);
        qual = findViewById(R.id.imageView2);

        submitbtn = findViewById(R.id.submitbtn);
        Modmark = findViewById(R.id.minmark);
        Fullname = findViewById(R.id.minqual);
        modnamev = findViewById(R.id.modnamev);
        cosnamev = findViewById(R.id.cosnamev);

        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Applicationids");
        ProductImagesRef2 = FirebaseStorage.getInstance().getReference().child("Applicationquals");

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Applications");

        idcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }

        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateApplicationData();
            }
        });


        qual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery2();
            }

        });






        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                TaopeningItem lecmod = dataSnapshot.child("Taoppenings").child(Selected.value1).getValue(TaopeningItem.class);
                if (lecmod != null)
                {
                    modnamev.setText(lecmod.getModname());
                    cosnamev.setText(lecmod.getLecmodcos());
                    modm = Integer.parseInt(lecmod.getMinmark());

                }
                else
                {
                    Toast.makeText(ApplyActivity.this, "job doesnt exist", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ApplyActivity.this, UseropeningsActivity.class);
                    startActivity(i);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");


        startActivityForResult(galleryIntent, GalleryPick);
    }

    private void OpenGallery2()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            idcopy.setImageURI(ImageUri);
        }

        if (requestCode==GalleryPick2  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri2 = data.getData();
            qual.setImageURI(ImageUri2);
        }
    }

    private void ValidateApplicationData()
    {

        String modmark = Modmark.getText().toString();
        String fullname = Fullname.getText().toString();

        if (ImageUri == null || ImageUri2 == null)
        {
            Toast.makeText(this, "Please upload all documents", Toast.LENGTH_SHORT).show();
        }

        if (modmark.isEmpty() || fullname.isEmpty())
        {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }


        else
        {
            StoreAppInformation();
        }
    }

    private void StoreAppInformation()
    {


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;


        if(Integer.parseInt(Modmark.getText().toString()) <modm)
        {
            Toast.makeText(ApplyActivity.this, "Disqualified : do not meet requirements", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ApplyActivity.this, ApplyActivity.class);
            startActivity(intent);
            stat ="Disqualified : do not meet requirements";
        }


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey);

        final UploadTask uploadTask = filePath.putFile(ImageUri);




        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(ApplyActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(ApplyActivity.this, "Id copy uploaded", Toast.LENGTH_SHORT).show();

                            SaveAppInfoToDatabase();
                        }
                    }
                });
            }
        });






        final StorageReference filePath2 = ProductImagesRef2.child(ImageUri2.getLastPathSegment() + productRandomKey);

        final UploadTask uploadTask2 = filePath2.putFile(ImageUri2);

        uploadTask2.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(ApplyActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {

                Task<Uri> urlTask = uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl2 = filePath2.getDownloadUrl().toString();
                        return filePath2.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl2 = task.getResult().toString();

                            Toast.makeText(ApplyActivity.this, "Qualification uploaded", Toast.LENGTH_SHORT).show();

                            SaveAppInfoToDatabase();
                        }
                    }
                });
            }
        });



    }
    String  stat = "application submited";

    private void SaveAppInfoToDatabase()
    {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                TaopeningItem lecmod = dataSnapshot.child("Taoppenings").child(Selected.value1).getValue(TaopeningItem.class);



                HashMap<String, Object> productMap = new HashMap<>();
                productMap.put("Appid", productRandomKey);
                productMap.put("Date", saveCurrentDate);
                productMap.put("Time", saveCurrentTime);
                productMap.put("Idcopy", downloadImageUrl);
                productMap.put("Qualcopy", downloadImageUrl2);
                productMap.put("Jobid", lecmod.getJobid());
                productMap.put("Applimark", Modmark.getText().toString());
                productMap.put("Applifullname", Fullname.getText().toString());
                productMap.put("Lecemail", lecmod.getLecemail());
                productMap.put("Appliemail", Prevalent.currentOnlineUser.getEmail());
                productMap.put("Status", "application sent");
                productMap.put("Modname",lecmod.getModname());
                productMap.put("Cosname",lecmod.getLecmodcos());
                productMap.put("Hodemail", lecmod.getHodemail());
                productMap.put("Studentnumber", Prevalent.currentOnlineUser.getStudentnumber());


                if(Integer.parseInt(Modmark.getText().toString()) <Integer.parseInt(lecmod.getMinmark()))
                {
                    Toast.makeText(ApplyActivity.this, "Disqualified : do not meet requirements", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ApplyActivity.this, ApplyActivity.class);
                    startActivity(intent);
                    stat ="Disqualified : do not meet requirements";
                }

                ProductsRef.child(productRandomKey).updateChildren(productMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(ApplyActivity.this, "Application submited successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ApplyActivity.this, ApplyActivity.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    String message = task.getException().toString();
                                    Toast.makeText(ApplyActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
        });
            }
    }


