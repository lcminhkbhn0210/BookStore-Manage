package com.example.btl_mobile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl_mobile.database.DB;
import com.example.btl_mobile.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    private final static int REQUEST_CODE_REGISTER=10000;
    private EditText username, password;
    private Button btnLogin;
    private FirebaseAuth auth;
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    auth.signInWithEmailAndPassword(username.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(LoginActivity.this,"Ban da dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                            DB db = new DB(LoginActivity.this);
                            User user = db.getUserByUsername(username.getText().toString());
                            if(user.getRole()==1){
                                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }
                            else{
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                intent.putExtra("user",user);
                                startActivity(intent);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,"Ban da dang nhap that bai",Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent,REQUEST_CODE_REGISTER);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_REGISTER){
            if(resultCode==RESULT_OK){
                final String email = data.getStringExtra("email");
                final String txtpassword = data.getStringExtra("pass");
                username.setText(email);
                password.setText(txtpassword);
            }else {

            }
        }
    }

    private void init(){
        username = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        auth = FirebaseAuth.getInstance();
        register = findViewById(R.id.gotoRegister);
    }
}