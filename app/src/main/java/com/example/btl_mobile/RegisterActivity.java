package com.example.btl_mobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText username_register, password_register;
    private Button btnRegister, btnCancel;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.createUserWithEmailAndPassword(username_register.getText().toString(),password_register.getText().toString()).
                        addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(RegisterActivity.this,
                                "Register Success", Toast.LENGTH_SHORT).show();
                        DB db = new DB(RegisterActivity.this);
                        User user = new User();
                        user.setUsername(username_register.getText().toString());
                        user.setPassword(password_register.getText().toString());
                        user.setRole(1);
                        db.addUser(user);
                        final Intent data = new Intent();
                        data.putExtra("email", username_register.getText().toString());
                        data.putExtra("pass", password_register.getText().toString());
                        setResult(Activity.RESULT_OK, data);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void init(){
        username_register = findViewById(R.id.inputEmailRegister);
        password_register = findViewById(R.id.inputPasswordRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnCancel = findViewById(R.id.btnCancelRegister);
        auth = FirebaseAuth.getInstance();
    }
}