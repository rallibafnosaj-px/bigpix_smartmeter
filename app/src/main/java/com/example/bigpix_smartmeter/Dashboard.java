package com.example.bigpix_smartmeter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    RecyclerView rv_retrieveOpenDocuments;
    SwipeRefreshLayout sl_refresh;

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


    public void SettingUpRecyclerView()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_retrieveOpenDocuments.setLayoutManager(linearLayoutManager);

    }

    private void VariablesCasting() {
        rv_retrieveOpenDocuments = findViewById(R.id.rv_retrieveOpenDocuments);
        sl_refresh = findViewById(R.id.sl_refresh);
    }
    public void AddingOpenDocumentsToRecyclerView()
    {
        onlineDB.RetrieveOpenDocuments(rv_retrieveOpenDocuments, this);
    }

}