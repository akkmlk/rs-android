package com.latihan.rs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {

    ImageView ivBack;
    EditText etName, etUsername, etAlamat, etPassword, etPasswordConfirmation;
    Button btnRegister;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        ivBack = findViewById(R.id.iv_back);

        etName = findViewById(R.id.et_name);
        etUsername = findViewById(R.id.et_username);
        etAlamat = findViewById(R.id.et_alamat);
        etPassword = findViewById(R.id.et_password);
        etPasswordConfirmation = findViewById(R.id.et_password_confirm);

        btnRegister = findViewById(R.id.btnDatfarReg);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String alamat = etAlamat.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String passwordConfirmation = etPasswordConfirmation.getText().toString().trim();

                if (name.isEmpty() || username.isEmpty() || alamat.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                    FailedMsg("Semua kolom harus diisi");
                } else if (!password.equals(passwordConfirmation)) {
                    FailedMsg("Password dan konfirmasi password tidak cocok");
                } else {
                    RegisterSend();
                }

            }
        });

    }

    private void RegisterSend() {

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(etName.getText().toString().trim());
        registerRequest.setUsername(etUsername.getText().toString().trim());
        registerRequest.setAlamat(etAlamat.getText().toString().trim());
        registerRequest.setPassword(etPassword.getText().toString().trim());
        registerRequest.setPasswordConfirmation(etPasswordConfirmation.getText().toString().trim());

        Call <RegisterResponse> registerResponseCall = ApiClient.getRegisterService().userRegister(registerRequest);
        registerResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                String message = response.body().getMessage();
                SuccessMsg(message);
//                if (response.isSuccessful()) {
////                    startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
////                    finish();
//                    FailedMsg("Data terdaftar : " + response.body().getData());
//
////                    Toast.makeText(DaftarActivity.this, "Data terdaftar : " + response.body().getData(), Toast.LENGTH_LONG).show();
////                } else {
//                    Toast.makeText(DaftarActivity.this, "Gagal mendaftar : " + response.body().getMessage(), Toast.LENGTH_LONG).show();
//                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
//                Toast.makeText(DaftarActivity.this, "Throwable : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                FailedMsg("Throwable : " + t.getLocalizedMessage());
            }
        });

    }

    private void SuccessMsg(String s) {

        AlertDialog.Builder alertBuild = new AlertDialog.Builder(DaftarActivity.this);
        alertBuild.setTitle("Oke! Berhasil Mendaftar")
                .setMessage(s)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startActivity(new Intent(DaftarActivity.this, LoginActivity.class));
                        finish();
                    }
                });
        AlertDialog alert = alertBuild.create();
        alert.show();

    }

    private void FailedMsg(String s) {

        AlertDialog.Builder alertBuild = new AlertDialog.Builder(DaftarActivity.this);
        alertBuild.setTitle("Oops! Terjadi Kesalahan")
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