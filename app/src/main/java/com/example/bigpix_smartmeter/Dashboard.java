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
import android.widget.Toast;

import java.io.IOException;

public class Dashboard extends AppCompatActivity {

    RecyclerView rv_retrieveOpenDocuments;
    SwipeRefreshLayout sl_refresh;
    Bitmap bitmap;

    WebService.Dashboard onlineDB = new WebService.Dashboard(Dashboard.this, Dashboard.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        VariablesCasting();
        SettingUpRecyclerView();
        RefreshLayoutFunction();
        AddingOpenDocumentsToRecyclerView();

    }

    private void RefreshLayoutFunction() {
        sl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sl_refresh.setColorSchemeColors(Color.BLUE, Color.BLUE, Color.BLUE);
                AddingOpenDocumentsToRecyclerView();
                sl_refresh.setRefreshing(false);

            }
        });
    }


    public void SettingUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_retrieveOpenDocuments.setLayoutManager(linearLayoutManager);

    }

    private void VariablesCasting() {
        rv_retrieveOpenDocuments = findViewById(R.id.rv_retrieveOpenDocuments);
        sl_refresh = findViewById(R.id.sl_refresh);
    }

    public void AddingOpenDocumentsToRecyclerView() {
        onlineDB.RetrieveOpenDocuments(rv_retrieveOpenDocuments, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}