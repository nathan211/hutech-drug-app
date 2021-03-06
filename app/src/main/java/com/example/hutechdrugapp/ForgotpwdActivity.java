package com.example.hutechdrugapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.flatdialoglibrary.dialog.FlatDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import ir.androidexception.andexalertdialog.AndExAlertDialog;
import ir.androidexception.andexalertdialog.AndExAlertDialogListener;
import ir.androidexception.andexalertdialog.Font;

public class ForgotpwdActivity extends AppCompatActivity {
    private ImageButton btn_reset, btn_back;
    private EditText edtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd);
        btn_reset = (ImageButton) findViewById(R.id.btn_send);
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        edtEmail = (EditText) findViewById(R.id.editTextEmailForgot);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ForgotpwdActivity.this, SigninActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);;
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=edtEmail.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(ForgotpwdActivity.this,"Vui lòng nhập email muốn reset",Toast.LENGTH_LONG).show();
                }
                else if (!isValid(email)){
                    Toast.makeText(ForgotpwdActivity.this,"Vui lòng nhập email hợp lệ",Toast.LENGTH_LONG).show();
                }
                else {
                    ForgotPass(email);
                    final FlatDialog flatDialog = new FlatDialog(ForgotpwdActivity.this);
                    flatDialog.setTitle("Success")
                            .setSubtitle(String.format("An email has been sent to %s with further instructions.", email))
                            .setFirstButtonText("OK")
                            .withFirstButtonListner(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent myIntent = new Intent(ForgotpwdActivity.this, SigninActivity.class);
                                    myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(myIntent);
                                }
                            })
                            .show();
                }
            }
        });
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    //=======================================================================================================================================
    private void ForgotPass(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("getpass", "Email sent.");
                        }
                    }
                });
    }
}