package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class showContact extends AppCompatActivity {
    TextView dName,dPhone;
    ImageView mImageView;
    String getName,getPhone;
    private static final int REQUEST_CALL = 1;
    ImageView call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        dName = findViewById(R.id.displayName);
        dPhone = findViewById(R.id.phoneNumber);
        mImageView = findViewById(R.id.image);
        call = findViewById(R.id.call);

        getName = getIntent().getStringExtra("name");
        getPhone = getIntent().getStringExtra("phone");

        dName.setText(getName);
        dPhone.setText(getPhone);
        if(!(TextUtils.isEmpty(getName) || TextUtils.isEmpty(getPhone))) {
            dName.setText(getName);
            dPhone.setText(getPhone);

        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });
    }

   public void makePhoneCall() {
        if(getPhone.trim().length() > 0) {
            if(ContextCompat.checkSelfPermission(showContact.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(showContact.this,
                        new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);
            } else {
                String dial = "tel: " + getPhone;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        } else {
            Toast.makeText(getApplicationContext(),"Please Enter Phone number",Toast.LENGTH_SHORT).show();
        }
   }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CALL) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
