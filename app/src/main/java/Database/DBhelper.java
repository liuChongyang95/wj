package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

import Util.Util;
import ma.wj.manageapp.R;

public class DBhelper extends SQLiteOpenHelper {

    private static final String CREATE_USERINFO="create table UserInfo(User_Id char(20) primary key,Password char(20) not null," +
            "Real_Name char(20) not null,Card_Num char(18) not null,Sex char(4) not null ,Phone_Num char(11) not null," +
            "Card_Type char(12) not null,User_Account float(10))";

    private static final String CREATE_HOME="create table Home(Room_Id char(20) primary key,Room_Address char(20)," +
            "Room_Type char(10),Room_User char(20),Room_Pub char(1),foreign key (Room_User) references UserInfo(User_Id)," +
            "foreign key (Room_Type) references HomeType(Room_Type))";

    private static final String CREATE_HOMETYPE="create table HomeType(Room_Type char(10) primary key,Room_Cost char(10)," +
            "Pay_Period TimeStamp NOT NULL )";

    private static final String CREATE_REPORT="create table Report(Report_Id char(10) primary key,Report_End TimeStamp" +
            ",Report_Type char(10),Report_Cont char(1000),foreign key (Report_Type) references HomeType(Room_Type))";
    private Context context;

    public DBhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERINFO);
        db.execSQL(CREATE_HOME);
        db.execSQL(CREATE_HOMETYPE);
        db.execSQL(CREATE_REPORT);
        initData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void initData(SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues=new ContentValues();
        contentValues.put("Room_Type","A");
        contentValues.put("Room_Cost","3000");
        contentValues.put("Pay_Period",3600000);
        sqLiteDatabase.insert("HomeType",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Type","B");
        contentValues.put("Room_Cost","2000");
        contentValues.put("Pay_Period",3600000);
        sqLiteDatabase.insert("HomeType",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Type","C");
        contentValues.put("Room_Cost","1000");
        contentValues.put("Pay_Period",3600000);
        sqLiteDatabase.insert("HomeType",null,contentValues);
        contentValues.clear();

        contentValues.put("User_Id","12345");
        contentValues.put("Password","1234567");
        contentValues.put("Real_Name","刘建明");
        contentValues.put("Card_Type","身份证");
        contentValues.put("Card_Num","110105**********39");
        contentValues.put("Sex","男");
        contentValues.put("Phone_Num","182******68");
        contentValues.put("User_Account",300000f);
        sqLiteDatabase.insert("UserInfo",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Id","0511");
        contentValues.put("Room_Address","HBS小鎮-02");
        contentValues.put("Room_Type","C");
        contentValues.put("Room_User","12345");
        contentValues.put("Room_Pub","1");
        sqLiteDatabase.insert("Home",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Id","0514");
        contentValues.put("Room_Address","HBS小鎮-01");
        contentValues.put("Room_Type","B");
        contentValues.put("Room_User","12345");
        contentValues.put("Room_Pub","1");
        sqLiteDatabase.insert("Home",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Id","0512");
        contentValues.put("Room_Address","HBS小鎮-05");
        contentValues.put("Room_Type","B");
        contentValues.put("Room_User","12345");
        contentValues.put("Room_Pub","1");
        sqLiteDatabase.insert("Home",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Id","0513");
        contentValues.put("Room_Address","HBS小鎮-03");
        contentValues.put("Room_Type","A");
        contentValues.put("Room_User","12345");
        contentValues.put("Room_Pub","1");
        sqLiteDatabase.insert("Home",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Id","0519");
        contentValues.put("Room_Address","HBS小鎮-09");
        contentValues.put("Room_Type","A");
        contentValues.put("Room_User","12345");
        contentValues.put("Room_Pub","0");
        sqLiteDatabase.insert("Home",null,contentValues);
        contentValues.clear();

        contentValues.put("User_Id","54321");
        contentValues.put("Password","1234567");
        contentValues.put("Real_Name","陈永仁");
        contentValues.put("Card_Type","身份证");
        contentValues.put("Card_Num","110105**********39");
        contentValues.put("Sex","男");
        contentValues.put("Phone_Num","182******69");
        contentValues.put("User_Account",100000f);
        sqLiteDatabase.insert("UserInfo",null,contentValues);
        contentValues.clear();

        contentValues.put("Room_Id","0518");
        contentValues.put("Room_Address","LBS小鎮-04");
        contentValues.put("Room_Type","B");
        contentValues.put("Room_User","54321");
        contentValues.put("Room_Pub","1");
        sqLiteDatabase.insert("Home",null,contentValues);
        contentValues.clear();

        contentValues.put("Report_Id","00001");
        contentValues.put("Report_End",1560091254821L);
        contentValues.put("Report_Type","B,C");
        contentValues.put("Report_Cont","过了今天就好了,过了今天就好了过了今天就好了过了今天就好了过了今天就好了过了今天就好了过了" +
                "今天就好了过了今天就好了过了今天就好了");
        sqLiteDatabase.insert("Report",null,contentValues);
        contentValues.clear();

        contentValues.put("Report_Id","00055");
        contentValues.put("Report_End",1557662199000L);
        contentValues.put("Report_Type","B,C");
        contentValues.put("Report_Cont","过了今天就好了");
        sqLiteDatabase.insert("Report",null,contentValues);
        contentValues.clear();
    }
}
