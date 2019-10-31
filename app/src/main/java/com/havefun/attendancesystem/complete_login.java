package com.havefun.attendancesystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.havefun.attendancesystem.R;

import java.io.IOException;
import java.util.HashMap;

public class complete_login extends AppCompatActivity {
EditText username,email,phone,address,dateofbirth;
Button submit;
ImageView image_profile;
//image values
final int imagePcode=50;
final int prCode=51;//must be final for onRequestPermissionsResult
//end of image vals
final HashMap<String,String>hash=new HashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_login);
        initializeVars();
        //submit listener
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String phoon=phone.getText().toString();
                String emailf=email.getText().toString();
                String userAddress=address.getText().toString();
                if(!userOK()){
                    username.setError("please check your user name");
                }
                if(!emailOK()){
                    email.setError("please check your email");
                }
                if(!phoneOk()){
                    phone.setError("please check your phone number");
                }
                if(allOk()){hash.put("UserAddress",userAddress);
                hash.put("UserEmail",emailf);
                hash.put("UserName",user);
                hash.put("UserPhoneNumber",phoon);
                Toast.makeText(getApplicationContext(),hash.get("UserName"),Toast.LENGTH_LONG).show();

                }

            }
        });
        //image listner
        image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String [] per={Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(per,prCode);
                }
                else {
                    pickM();
                }
            }else{
                pickM();

            }

            }
        });

    }
/*
** Initialization vars and objects part
 */
    private void initializeVars() {
        username=(EditText)findViewById(R.id.username);
        email=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        address=(EditText)findViewById(R.id.address);
        dateofbirth=(EditText)findViewById(R.id.dateofbirth);
        submit=findViewById(R.id.submit);
        image_profile=findViewById(R.id.image_profile);

    }
    /*
    checkers methods
     */
    public boolean userOK(){
        String user=username.getText().toString();
        String pat="^[a-zA-Z]*$";
        if(user.matches(pat)){
            return true;
        }else {return  false;}
    }
    public boolean phoneOk(){
        String patt ="^[0-9]*$";
        String phoon=phone.getText().toString();
        if(phoon.matches(patt)){
            return true;
        }else {return false;}
    }
    public boolean emailOK(){
        String pattt="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String emailf=email.getText().toString();
        if(emailf.matches(pattt)){
            return true;
        }else {
            return false;
        }
    }
    public boolean allOk(){
        if(emailOK()&&userOK()&&phoneOk()){
            return true;
        }else {return false;}
    }
    /*
 end of checkers methods
     */
    /*
    image intent section
     */
    private void pickM(){
        Intent intt=new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intt.setType("image/*");
        startActivityForResult(intt,imagePcode);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case prCode:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickM();
                }
                else {
                    Toast.makeText(this,"maenfashe with ahmed beder sound",Toast.LENGTH_LONG);
                }
            }break;

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == imagePcode && resultCode == RESULT_OK && null != data) {//the IF statement was WRONG
            Uri i=data.getData();
            try {
                Bitmap ii= MediaStore.Images.Media.getBitmap(this.getContentResolver(),i);

                image_profile.setImageBitmap(ii);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    /*end of image intent section

     */






}

