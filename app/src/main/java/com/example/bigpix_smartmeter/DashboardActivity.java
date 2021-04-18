package com.example.bigpix_smartmeter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;

import Adapter.RetrieveOpenDocuments;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView rv_retrieveOpenDocuments;
    SwipeRefreshLayout sl_refresh;


    WebService.DashboardActivity onlineDB = new WebService.DashboardActivity(DashboardActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        VariablesCasting();
        SettingUpRecyclerView();
        RefreshLayoutFunction();


    }

    private void RefreshLayoutFunction() {
        sl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sl_refresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
                onlineDB.RetrieveOpenDocuments(rv_retrieveOpenDocuments, DashboardActivity.this);
                sl_refresh.setRefreshing(false);

            }
        });
    }


    public void SettingUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_retrieveOpenDocuments.setLayoutManager(linearLayoutManager);
        onlineDB.RetrieveOpenDocuments(rv_retrieveOpenDocuments, this);

    }

    private void VariablesCasting() {
        rv_retrieveOpenDocuments = findViewById(R.id.rv_retrieveOpenDocuments);
        sl_refresh = findViewById(R.id.sl_refresh);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == Activity.RESULT_OK) {


            if (GlobalVariables.isAttachment1 == true) {

                try {
                    GlobalVariables.listOfAttachments.remove(0);
                } catch (Exception e) {
                }

                try {
                    GlobalVariables.listOfAttachments.add(ProcessBitmap(GlobalVariables.outputFileUri));
                } catch (Exception e) {

                }

                RetrieveOpenDocuments.cb_attachment1.setChecked(true);
                GlobalVariables.isAttachment1 = false;

            }
            if (GlobalVariables.isAttachment2 == true) {

                try {
                    GlobalVariables.listOfAttachments.remove(1);
                } catch (Exception e) {
                }

                try {
                    GlobalVariables.listOfAttachments.add(ProcessBitmap(GlobalVariables.outputFileUri));
                } catch (Exception e) {

                }

                RetrieveOpenDocuments.cb_attachment2.setChecked(true);
                GlobalVariables.isAttachment2 = false;
            }
            if (GlobalVariables.isAttachment3 == true) {

                try {
                    GlobalVariables.listOfAttachments.remove(2);
                } catch (Exception e) {
                }

                try {
                    GlobalVariables.listOfAttachments.add(ProcessBitmap(GlobalVariables.outputFileUri));
                } catch (Exception e) {
                }

                RetrieveOpenDocuments.cb_attachment3.setChecked(true);
                GlobalVariables.isAttachment3 = false;
            }
        }
    }


    public String ProcessBitmap(Uri path) {

        String image = "";

        try {

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            image = Base64.encodeToString(imgBytes, Base64.DEFAULT);

            return image;

        } catch (Exception e) {

        }

        return image;


    }





    public void GoToReports(View view) {
        Intent intent = new Intent(this, ReportsActivity.class);
        startActivity(intent);
    }
}