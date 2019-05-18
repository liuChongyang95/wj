package ma.wj.manageapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Adapter.ReportAdapter;
import JavaBean.Report;
import JavaDao.ReportDao;

public class ReportMain extends AppCompatActivity {
    private String userId;
    private List<Report> reports;
    private ReportDao reportDao;
    private ReportAdapter reportAdapter;
    private ListView listView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_report);
        Toolbar toolbar =findViewById(R.id.report_toolBar);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if (bundle != null) {
            userId=bundle.getString("User_Id");
        }
        reportDao=new ReportDao(this);
        reports=reportDao.getReport();
        Date now=new Date();
        long nowTimeStamp=now.getTime();
        List<Report> aliveReport=new ArrayList<>();
        for (Report report:reports){
            long liveTime=report.getReportEnd();
            System.out.println("++++++++++++++"+liveTime);
            if (liveTime>=nowTimeStamp){
                aliveReport.add(report);
            }
        }
        reportAdapter=new ReportAdapter(this,R.layout.report_item,aliveReport);
        listView=findViewById(R.id.report_list);
        listView.setAdapter(reportAdapter);
    }
}
