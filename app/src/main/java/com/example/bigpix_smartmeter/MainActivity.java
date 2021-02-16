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

import WebService.URL;

public class MainActivity extends AppCompatActivity {

    SQLite.MainActivity offlineDB = new SQLite.MainActivity(MainActivity.this);
    WebService.MainActivity onlineDB = new WebService.MainActivity(MainActivity.this, MainActivity.this);
    StringValidation validation = new StringValidation();

    Button btn_login;
    Button btn_changeip;
    EditText et_username;
    EditText et_password;

    LayoutInflater layoutInflater;
    AlertDialog alertDialog;
    View viewInflater;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        VariableCasting();
        IPAddressChecking();

    }

    private void IPAddressChecking() {

        if (offlineDB.RetrieveIPAddress().equals("")) {
            offlineDB.InsertIPAddress("192.168.1.89");
        } else {
            String ipAddress = offlineDB.RetrieveIPAddress();

            URL.ROOT_URL = "http://" + ipAddress + "/bigpix_smartmeter/";
            URL.LOGINACCOUNT = URL.ROOT_URL + "LoginAccount.php";
            URL.RETRIEVEOPENDOCUMENTS = URL.ROOT_URL + "RetrieveOpenDocuments.php";
            URL.PROCESSTRANSACTION = URL.ROOT_URL + "ProcessTransaction.php";
            URL.UPLOADPROOFOFPAYMENT = URL.ROOT_URL + "UploadProofOfPayment.php";
            Toast.makeText(this, "" + ipAddress, Toast.LENGTH_SHORT).show();
        }

    }

    public void ChangeIPAddress(View view) {

        layoutInflater = LayoutInflater.from(this);
        viewInflater = layoutInflater.inflate(R.layout.dialog_changeip, null);
        alertDialog = new AlertDialog.Builder(viewInflater.getContext(),
                R.style.CustomAlertDialog).setView(viewInflater).show();

        Button btn_changeip = viewInflater.findViewById(R.id.btn_changeip);
        EditText et_changeip = viewInflater.findViewById(R.id.et_changeip);


        btn_changeip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ipaddress = et_changeip.getText().toString();

                if (validation.HasSpecialCharacters(ipaddress)) {
                    Toast.makeText(MainActivity.this, "Special Characters not allowed.", Toast.LENGTH_SHORT).show();
                } else if (validation.HasNull(ipaddress)) {
                    Toast.makeText(MainActivity.this, "Enter IP Address.", Toast.LENGTH_SHORT).show();
                } else {
                    offlineDB.UpdateIPAddress(ipaddress);
                    alertDialog.dismiss();
                    finish();
                    startActivity(MainActivity.this.getIntent());
                    Toast.makeText(MainActivity.this, "Changed IP Address to: " + ipaddress, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void VariableCasting() {

        btn_login = findViewById(R.id.btn_login);
        btn_changeip = findViewById(R.id.btn_changeip);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);

    }


    public void LoginAccount(View view) {


        String username = et_username.getText().toString();
        String password = et_password.getText().toString();


        onlineDB.LoginAccount(username, password);


    }
}