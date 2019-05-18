package ma.wj.manageapp;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;

import Database.DBhelper;
import JavaDao.LoginDao;
import Util.Staticfinal_Value;
import Util.Util;

public class Register extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText passwordconf;
    private RadioGroup radioGroup_sex;
    private RadioButton radioButton_male;
    private RadioButton radioButton_female;
    private EditText phone;
    private EditText realname;
    private EditText cardcode;
    private EditText cardtype;
    private DBhelper dbHelper;
    private LoginDao loginDao;
    private Staticfinal_Value sfv;
    private String sex;
    private Drawable tempPic;

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
        setContentView(R.layout.activity_register);
        Toolbar toolbar = findViewById(R.id.register_toolBar);
        username = findViewById(R.id.username_register);
        password = findViewById(R.id.password_register);
        passwordconf = findViewById(R.id.password_confirm);
        radioGroup_sex = findViewById(R.id.rg_sex);
        radioButton_male = findViewById(R.id.rb_male);
        radioButton_female = findViewById(R.id.rb_female);
        phone = findViewById(R.id.phone_number);
        cardcode = findViewById(R.id.card_code);
        realname = findViewById(R.id.user_real_name);
        cardtype = findViewById(R.id.card_type);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register.this.finish();
            }
        });

        tempPic = this.getResources().getDrawable(R.drawable.tpic);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //版本号
        sfv = new Staticfinal_Value();
        loginDao = new LoginDao(Register.this);
        dbHelper = new DBhelper(this, "ma.db", null, sfv.staticVersion());
        //设置性别
        selectedSex();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.register_sure:
                String usernameStr = username.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();
                String passwordConf = passwordconf.getText().toString().trim();
                String realName = realname.getText().toString().trim();
                String phoneStr = phone.getText().toString().trim();
                String cardCode = cardcode.getText().toString().trim();
                String cardType = cardtype.getText().toString().trim();
                ContentValues contentValues = new ContentValues();
                sex = selectedSex();
                if (!"".equals(usernameStr) && !"".equals(passwordStr) &&
                        passwordStr.length() >= 6 && passwordStr.length() <= 30 && !"".equals(sex)
                        && passwordStr.equals(passwordConf) && !"".equals(realName) && !"".equals(phoneStr)
                        && !"".equals(cardCode) && !"".equals(cardType)) {
                    SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
                    sqLiteDatabase.beginTransaction();
                    sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
                    contentValues = new ContentValues();
                    contentValues.put("User_Id", usernameStr);
                    contentValues.put("Password", passwordStr);
                    contentValues.put("Real_Name", realName);
                    contentValues.put("Card_Num", cardCode);
                    contentValues.put("Phone_Num", phoneStr);
                    contentValues.put("Card_Type", cardType);
                    contentValues.put("Sex", sex);
                    try {
                        sqLiteDatabase.insertOrThrow("UserInfo", null, contentValues);
                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();
                        sqLiteDatabase.close();
                        Toast.makeText(this, "注冊成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(this, "注册失败,用户名重复", Toast.LENGTH_SHORT).show();
                    } finally {
                        contentValues.clear();
                    }
                } else {
                    Toast.makeText(this, "信息不全或密码过短", Toast.LENGTH_SHORT).show();
                    contentValues.clear();
                }
                break;
            default:
                break;
        }
        return true;
    }

    private String selectedSex() {
        radioGroup_sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButton_male.getId() == i) {
                    sex = "男";
                }
                if (radioButton_female.getId() == i) {
                    sex = "女";
                }
            }
        });
        return sex;
    }

    //toolbar加载菜单项
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }
}
