package JavaDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.DBhelper;
import JavaBean.Home;
import Util.Staticfinal_Value;

public class HomeDao {
    private DBhelper dBhelper;
    private String userInfo;
    private Staticfinal_Value sfv;
    private String homeAddress;
    private String homeType;
    private String homeUser;
    private String homePub;
    private String homeCost;
    private String homeId;
    private Home home;

    public HomeDao(Context context) {
        sfv = new Staticfinal_Value();
        dBhelper = new DBhelper(context, "ma.db", null, sfv.staticVersion());
    }


    public List<Home> getHomeList(){
        List<Home> homes=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
        String sql="select * from Home";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,null);
        if (cursor!=null && cursor.getCount()!=0){
            while (cursor.moveToNext()){
                homeAddress=cursor.getString(cursor.getColumnIndex("Room_Address"));
                homeType=cursor.getString(cursor.getColumnIndex("Room_Type"));
                homeUser=cursor.getString(cursor.getColumnIndex("Room_User"));
                homePub=cursor.getString(cursor.getColumnIndex("Room_Pub"));
                home=new Home(homeAddress,homeType,homeUser,homePub,null);
                homes.add(home);
            }
        }
        if (cursor!=null){
            cursor.close();
        }
        sqLiteDatabase.close();
        return homes;
    }

    public List<Home> getUserHome(String userId){
        List<Home> homes=new ArrayList<>();
        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
        String sql="select Room_Id, Room_Address,Room_Type,Room_Pub from Home where Room_User = ?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{userId});
        if (cursor!=null && cursor.getCount()!=0){
            while (cursor.moveToNext()){
                homeId=cursor.getString(cursor.getColumnIndex("Room_Id"));
                homeAddress=cursor.getString(cursor.getColumnIndex("Room_Address"));
                homeType=cursor.getString(cursor.getColumnIndex("Room_Type"));
                homePub=cursor.getString(cursor.getColumnIndex("Room_Pub"));
                home=new Home(homeAddress,homeType,homeId,homePub,null);
                homes.add(home);
            }
        }
        if (cursor!=null){
            cursor.close();
        }
        sqLiteDatabase.close();
        dBhelper.close();
        return homes;
    }

    public String getHomeCost(String homeType){
        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
        String sql = "select Room_Cost from HomeType where Room_Type = ?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{homeType});
        if (cursor!= null && cursor.getCount()!=0){
            while (cursor.moveToNext()) {
                homeCost = cursor.getString(cursor.getColumnIndex("Room_Cost"));
            }
        }
        if (cursor!=null){
            cursor.close();
        }
        sqLiteDatabase.close();
        dBhelper.close();
        return homeCost;
    }

    public void changeRoomPub(String roomId,String roomPub){
        SQLiteDatabase sqLiteDatabase=dBhelper.getWritableDatabase();
        if (roomPub.equals("撤销发布")){
            roomPub="0";
        }else if (roomPub.equals("进行发布")){
            roomPub="1";
        }
        ContentValues values=new ContentValues();
        values.put("Room_Pub",roomPub);
        sqLiteDatabase.update("Home",values,"Room_Id = ?",new String[]{roomId});
        values.clear();
        sqLiteDatabase.close();
        dBhelper.close();
    }

    public float getTotalCost(String userId){
        List<String> idList=new ArrayList<>();
        List<String> costList=new ArrayList<>();
        float costResult=0f;
        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
        String sql = "select Room_Type from Home where Room_User = ?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{userId});
        if (cursor!=null && cursor.getCount()!=0){
            while (cursor.moveToNext()){
                idList.add(cursor.getString(cursor.getColumnIndex("Room_Type")));
            }
        }
        String sql2="select Room_Cost from HomeType where Room_Type = ?";
        for (String roomType:idList){
            Cursor cursor1=sqLiteDatabase.rawQuery(sql2,new String[]{roomType});
            if (cursor1!=null && cursor1.getCount()!=0){
                while (cursor1.moveToNext()){
                    costList.add(cursor1.getString(cursor1.getColumnIndex("Room_Cost")));
                }
            }
            if (cursor1!=null){
                cursor1.close();
            }
        }
        for (String cost:costList){
            costResult+=Float.parseFloat(cost);
        }
        if (cursor!=null){
            cursor.close();
        }
        sqLiteDatabase.close();
        dBhelper.close();

        return costResult;
    }

}