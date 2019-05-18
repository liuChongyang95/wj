package JavaDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.IOError;
import java.util.Arrays;

import Database.DBhelper;
import Util.Staticfinal_Value;
import ma.wj.manageapp.Register;

public class LoginDao {
    private DBhelper dBhelper;
    private String userInfo;
    private Staticfinal_Value sfv;

    public LoginDao(Context context){
        sfv=new Staticfinal_Value();
        dBhelper=new DBhelper(context,"ma.db",null,sfv.staticVersion());
    }

    public String login(String username,String password){
        SQLiteDatabase sqLiteDatabase = dBhelper.getWritableDatabase();
        String sql="select * from UserInfo where User_Id=? and Password=?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{username,password});
        if (cursor.moveToFirst()){
            cursor.close();
            sqLiteDatabase.close();
            dBhelper.close();
            return username;
        }else {
            sqLiteDatabase.close();
            dBhelper.close();
            return "";
        }
    }

    public String getRealname(String userId){
        SQLiteDatabase sqLiteDatabase = dBhelper.getWritableDatabase();
        String sql="select Real_Name from UserInfo where User_Id=?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql, new String[]{userId});
        String res=null;
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                 res = cursor.getString(cursor.getColumnIndex("Real_Name"));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();
        dBhelper.close();
        return res;
    }

    public float getAccount(String userId){
        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
        String sql="select User_Account from UserInfo where User_Id=?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql, new String[]{userId});
        float res=0;
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                res = cursor.getFloat(cursor.getColumnIndex("User_Account"));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        sqLiteDatabase.close();
        dBhelper.close();
        return res;
    }

    public boolean changeAccount(String userId,String addAccount){
        SQLiteDatabase sqLiteDatabase=dBhelper.getWritableDatabase();
        String sql="select User_Account from UserInfo where User_Id=?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql, new String[]{userId});
        float res=0;
        if (cursor != null && cursor.getCount() != 0) {
            while (cursor.moveToNext()) {
                res = cursor.getFloat(cursor.getColumnIndex("User_Account"));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        ContentValues values=new ContentValues();
        values.put("User_Account",String.valueOf(Float.parseFloat(addAccount)+res));
        sqLiteDatabase.update("UserInfo",values,"User_Id = ?",new String[]{userId});
        values.clear();
        sqLiteDatabase.close();
        dBhelper.close();
        return true;
    }

    public String[] getUserInfo(String userId){
        String[] userInfo=new String[9];
        byte[] userPhoto;
        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
        String sql="select * from UserInfo where User_Id = ?";
        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{userId});
        if (cursor!=null && cursor.getCount()!=0){
         while (cursor.moveToNext()){
             userInfo[0]=cursor.getString(cursor.getColumnIndex("User_Id"));
             userInfo[1]=cursor.getString(cursor.getColumnIndex("Password"));
             userInfo[2]=cursor.getString(cursor.getColumnIndex("Real_Name"));
             userInfo[3]=cursor.getString(cursor.getColumnIndex("Card_Type"));
             userInfo[4]=cursor.getString(cursor.getColumnIndex("Card_Num"));
             userInfo[5]=cursor.getString(cursor.getColumnIndex("Sex"));
             userInfo[6]=cursor.getString(cursor.getColumnIndex("Phone_Num"));
             userInfo[7]=cursor.getString(cursor.getColumnIndex("User_Account"));
         }
         cursor.close();
        }
        dBhelper.close();
        sqLiteDatabase.close();
        return userInfo;
    }

//    public byte[] getPhoto(String userId){
//        byte[] userPhoto=null;
//        SQLiteDatabase sqLiteDatabase=dBhelper.getReadableDatabase();
//        String sql="select User_Photo from UserInfo where User_Id = ?";
//        Cursor cursor=sqLiteDatabase.rawQuery(sql,new String[]{userId});
//        if (cursor!=null&&cursor.getCount()!=0){
//            while (cursor.moveToNext()){
//                userPhoto=cursor.getBlob(cursor.getColumnIndex("User_Photo"));
//            }
//        }
//        if (cursor!=null){
//            cursor.close();
//        }
//        sqLiteDatabase.close();
//        dBhelper.close();
//        return userPhoto;
//    }

    public boolean changeAccountFromCost(String userId,float account){
        SQLiteDatabase sqLiteDatabase=dBhelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("User_Account",account);
        sqLiteDatabase.update("UserInfo",values,"User_Id = ?",new String[]{userId});
        values.clear();
        sqLiteDatabase.close();
        dBhelper.close();
        return true;
    }


}
