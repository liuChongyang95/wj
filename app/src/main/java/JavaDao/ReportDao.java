package JavaDao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import Database.DBhelper;
import JavaBean.Report;
import Util.Staticfinal_Value;
import ma.wj.manageapp.ReportMain;

public class ReportDao {
    private DBhelper dBhelper;
    private Staticfinal_Value sfv;
    private String reportId;
    private Long reportEnd;
    private String reportType;
    private String reportCont;
    private Report report;

    public ReportDao(Context context){
        sfv=new Staticfinal_Value();
        dBhelper = new DBhelper(context, "ma.db", null, sfv.staticVersion());
    }

    public List<Report> getReport(){
        List<Report> reports=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
        String sql="select * from Report";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        if (cursor!=null && cursor.getCount()!=0){
            while (cursor.moveToNext()){
                reportId=cursor.getString(cursor.getColumnIndex("Report_Id"));
                reportEnd=cursor.getLong(cursor.getColumnIndex("Report_End"));
                reportType=cursor.getString(cursor.getColumnIndex("Report_Type"));
                reportCont=cursor.getString(cursor.getColumnIndex("Report_Cont"));
                report=new Report(reportId,reportEnd,reportType,reportCont);
                reports.add(report);
            }
        }
        if (cursor!=null){
            cursor.close();
        }
        sqLiteDatabase.close();
        return reports;
    }
}
