package com.example.bigpix_smartmeter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    SQLite.MainActivity offlineDB = new SQLite.MainActivity(MainActivity.this);
    WebService.MainActivity onlineDB = new WebService.MainActivity(MainActivity.this, MainActivity.this);

    Button btn_login;
    Button btn_changeip;
    EditText et_username;
    EditText et_password;

    LayoutInflater layoutinflater;
    AlertDialog alertDialog;
    View viewinflater;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VariableCasting();

    }

    private void VariableCasting() {

        btn_login = findViewById(R.id.btn_login);
        btn_changeip = findViewById(R.id.btn_changeip);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);

    }

    public void ChangeIPAddress(View view) {

        layoutinflater = LayoutInflater.from(this);
        viewinflater = layoutinflater.inflate(R.layout.dialog_changeip, null);
        alertDialog = new AlertDialog.Builder(viewinflater.getContext(),
                R.style.CustomAlertDialog).setView(viewinflater).show();

        btn_changeip = viewinflater.findViewById(R.id.btn_changeip);


        btn_changeip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "hehe", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void LoginAccount(View view) {



        String username = et_username.getText().toString();
        String password = et_password.getText().toString();


        onlineDB.LoginAccount(username, password);


    }
}