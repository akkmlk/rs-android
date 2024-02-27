package com.latihan.rs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin, btnRegister;
    LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        localStorage = new LocalStorage(LoginActivity.this);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btnMasuk);
        btnRegister = findViewById(R.id.btnDaftar);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, DaftarActivity.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    FailedMsg("Username dan password tidak boleh kosong");
                } else {
                    LoginSend();
                }
            }
        });
    }

    private void LoginSend() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(etUsername.getText().toString().trim());
        loginRequest.setPassword(etPassword.getText().toString().trim());

        Call <LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                FailedMsg(response.body().getSuccess());
                String status = response.body().getSuccess();
                if (status.equals("true")) {
                    String token = response.body().getToken();
                    localStorage.setData(token);
                    etUsername.setText("");
                    etPassword.setText("");
                    startActivity(new Intent(LoginActivity.this, ContainerActivity.class));
//                    Toast.makeText(LoginActivity.this, "Berhasil Login dengan token : " + localStorage.getData(), Toast.LENGTH_LONG).show();
                } else {
                    FailedMsg("Username atau password salah");
                }

//                if (response.isSuccessful()) {
//                    String token = response.body().getToken();
//                    localStorage.setData(token);
//                    etUsername.setText("");
//                    etPassword.setText("");
//                    if (token != null && !token.isEmpty()) {
////                        startActivity(new Intent(LoginActivity.this, TransactionActivity.class));
////                        finish();
//                        Toast.makeText(LoginActivity.this, "Berhasil Login dengan token : " + localStorage.getData(), Toast.LENGTH_LONG).show();
//                    }
////                    if (response.body().getMessage().equals("Login Berhasil")) {
////                        AlertDialog.Builder alertBuild = new AlertDialog.Builder(LoginActivity.this);
////                        alertBuild.setTitle("Login Berhasil")
////                                .setMessage("Selamat Datang " + etUsername.getText().toString().trim())
////                                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) {
////                                        dialog.dismiss();
////                                    }
////                                });
////                        AlertDialog alert = alertBuild.create();
////                        alert.show();
////                    } else {
////                        FailedMsg(response.body().getMessage());
////                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "Gagal Login", Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void FailedMsg(String s) {
        AlertDialog.Builder alertBuild = new AlertDialog.Builder(LoginActivity.this);
        alertBuild.setTitle("Login Gagal")
                .setMessage(s)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = alertBuild.create();
        alert.show();
    }
}