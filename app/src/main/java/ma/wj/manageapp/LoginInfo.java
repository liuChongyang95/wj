package ma.wj.manageapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;

import JavaDao.LoginDao;
import Util.Util;
import de.hdodenhof.circleimageview.CircleImageView;

public class LoginInfo extends AppCompatActivity {
    private LoginDao dao;
    private Bundle bundle;
    private String[] userInfo;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //相机文件路径 大于7.0
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        setContentView(R.layout.activity_logininfo);
        dao = new LoginDao(this);
        Intent intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            userId = bundle.getString("User_Id");
        }
        userInfo = dao.getUserInfo(userId);

        TextView name = findViewById(R.id.user_info_real_name);
        TextView cardType = findViewById(R.id.user_info_card_type);
        TextView cardNum = findViewById(R.id.user_info_card_num);
        TextView sex = findViewById(R.id.user_info_sex);
        TextView phone = findViewById(R.id.user_info_phone_num);
        CircleImageView photo = findViewById(R.id.user_info_edit_photo);
        Toolbar toolbar = findViewById(R.id.user_info_edit_toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginInfo.this.finish();
            }
        });
        name.setText(userInfo[2]);
        cardType.setText(userInfo[3]);
        cardNum.setText(userInfo[4]);
        sex.setText(userInfo[5]);
        phone.setText(userInfo[6]);

    }
}
