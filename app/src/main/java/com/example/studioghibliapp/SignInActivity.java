package com.example.studioghibliapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studioghibliapp.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class SignInActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvLogin;
    Button btnSignIn;
    EditText etUsername;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initialSetup();

        handleOnClickTvLogin();
        handleOnClickBtnSignIn();
    }

    private void initialSetup() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro");

        btnSignIn = findViewById(R.id.btn_sign_in);
        tvLogin = findViewById(R.id.tv_login);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
    }

    private void handleOnClickBtnSignIn() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                Log.i("username", username);
                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Complete ambos campos para registrarse", Toast.LENGTH_SHORT).show();
                    return;
                } else if(password.length() < 4) {
                    Toast.makeText(SignInActivity.this, "La contraseña debe tener mínimo 4 caracteres", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                try {
                    boolean userExistsInDB = UserManager.getInstance(SignInActivity.this).userExistsInDB(user.getUsername());

                    if(userExistsInDB == false) {
                        Toast.makeText(SignInActivity.this, "Ya existe un usuario con ese nombre", Toast.LENGTH_LONG).show();
                        return;
                    }

                    UserManager.getInstance(SignInActivity.this).createUser(user);

                    Intent mainActivity = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleOnClickTvLogin() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(loginActivity);
            }
        });
    }
}