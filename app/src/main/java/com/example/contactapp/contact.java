package com.example.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class contact extends AppCompatActivity {
    EditText name,company,title,phone,email;
    String getName,getCompany,getTitle,getPhone,getEmail;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        name = findViewById(R.id.name);
        company = findViewById(R.id.company);
        title = findViewById(R.id.title);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);


    }
    public void close(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
    public void check(View view) {
        getName = name.getText().toString();
        getCompany = company.getText().toString();
        getTitle = title.getText().toString();
        getPhone = phone.getText().toString();
        getEmail = email.getText().toString();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if(TextUtils.isEmpty(getName) && TextUtils.isEmpty(getCompany)
           && TextUtils.isEmpty(getTitle) && TextUtils.isEmpty(getPhone) && TextUtils.isEmpty(getEmail)) {
            Toast.makeText((getApplicationContext()), "Contact is Empty", Toast.LENGTH_SHORT).show();
            return;
        } else {
            AddDetialsInDatabase(getName, getCompany, getTitle, getPhone, getEmail);
        }
    }
    private void AddDetialsInDatabase(final String getName, final String getCompany, String getTitle, final String getPhone, String getEmail) {

       String id = mDatabase.push().getKey();
       Details details = new Details(id,getName,getCompany,getTitle,getPhone,getEmail);
       mDatabase.child("Details").child(id).setValue(details).
               addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()) {
                             Toast.makeText((getApplicationContext()), "Contact Saved", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(getApplicationContext(),showContact.class);
                             intent.putExtra("name",getName);
                             intent.putExtra("phone",getPhone);
                             startActivity(intent);
                             finish();
                         } else {
                             Toast.makeText((getApplicationContext()), "Try Again", Toast.LENGTH_SHORT).show();

                         }
                   }
               });
    }

}
