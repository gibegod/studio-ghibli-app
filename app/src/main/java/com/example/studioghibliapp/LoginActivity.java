package com.example.studioghibliapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studioghibliapp.models.User;

public class LoginActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button btnLogin;
    TextView tvSignIn;
    EditText etUsername;
    EditText etPassword;
    CheckBox cbRememberUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getApplicationContext().getSharedPreferences(Consts.SP_CREDENTIALS, MODE_PRIVATE);
        String savedUser = sp.getString(Consts.USER, null);
        String savedPassword = sp.getString(Consts.PASSWORD, null);

        if(savedUser != null && savedPassword != null) {
            startMainActivity();
        }

        initialSetup();

        handleOnClickTvSignin();
        handleOnClickBtnLogin();
    }

    private void initialSetup() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inicio de sesión");

        btnLogin = findViewById(R.id.btn_login);
        tvSignIn = findViewById(R.id.tv_sign_in);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        cbRememberUser = findViewById(R.id.cb_remember_user);
    }

    private void handleOnClickTvSignin() {
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInActivity = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(signInActivity);
            }
        });
    }

    private void handleOnClickBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Complete ambos campos para iniciar sesión", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                try {
                    boolean logInUser = UserManager.getInstance(LoginActivity.this).logInUser(user);

                    if(logInUser == false) {
                        Toast.makeText(LoginActivity.this, "El usuario y/o la contraseña son incorrectos", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if(cbRememberUser.isChecked()) {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences(Consts.SP_CREDENTIALS, MODE_PRIVATE);
                        SharedPreferences.Editor spEditor = sp.edit();
                        spEditor.putString(Consts.USER, username);
                        spEditor.putString(Consts.PASSWORD, password);
                        spEditor.apply();
                    }

                    startMainActivity();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void startMainActivity() {
        Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }
}