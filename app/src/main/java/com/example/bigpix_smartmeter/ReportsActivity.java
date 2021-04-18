package com.example.bigpix_smartmeter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;

public class ReportsActivity extends AppCompatActivity {

    RecyclerView rv_reports;
    SwipeRefreshLayout sl_refresh;

    WebService.ReportsActivity onlineDB = new WebService.ReportsActivity(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        VariableCasting();
        SettingUpRecyclerView();
        RefreshLayoutFunction();


    }

    private void RefreshLayoutFunction() {
        sl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sl_refresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW, Color.GREEN);
                onlineDB.RetrieveReports(rv_reports);
                sl_refresh.setRefreshing(false);

            }
        });
    }

    private void SettingUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_reports.setLayoutManager(linearLayoutManager);
        onlineDB.RetrieveReports(rv_reports);
    }

    private void VariableCasting() {
        rv_reports = findViewById(R.id.rv_reports);
        sl_refresh = findViewById(R.id.sl_refresh);
    }
}