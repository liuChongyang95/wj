package ma.wj.manageapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapter.HomeAdapter;
import JavaBean.Home;
import JavaDao.HomeDao;
import JavaDao.LoginDao;

public class MyHomes extends AppCompatActivity {
    private LoginDao loginDao;
    private HomeDao homeDao;
    private Bundle bundle;
    private String userId;
    private ListView listViewOn;
    private ListView listViewOff;
    private List<Home> onhomeList;
    private List<Home> offhomeList;
    private List<Home> homeList;


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
        setContentView(R.layout.activity_myhomes);
        loginDao=new LoginDao(this);
        homeDao=new HomeDao(this);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        if (bundle != null) {
            userId=bundle.getString("User_Id");
        }
        Toolbar toolbar=findViewById(R.id.myhomes_toolbal);
        listViewOn=findViewById(R.id.myhomes_list_on);
        listViewOff=findViewById(R.id.myhomes_list_off);
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView homesOwner=findViewById(R.id.homesowner);
        homesOwner.setText(loginDao.getRealname(userId)+"所拥有的房屋");

        listViewOn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Home home=onhomeList.get(position);
                final String[] items = {"撤销发布", "进行发布"};
                final int[] yourChoice = {-1};
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(MyHomes.this);
                singleChoiceDialog.setTitle("房屋 "+home.getRoomAddress()+" 是否发布");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog.setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yourChoice[0] = which;
                            }
                        });
                singleChoiceDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //不为-1 则证明进行了选择，否则直接取消操作
                                if (yourChoice[0] != -1) {
                                    //借用参数
                                    homeDao.changeRoomPub(home.getRoomUser(),items[yourChoice[0]]);
                                    Toast.makeText(MyHomes.this,
                                            items[yourChoice[0]],
                                            Toast.LENGTH_SHORT).show();
                                } else dialog.dismiss();
                            }
                        });
                singleChoiceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onResume();
                    }
                });
                singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                singleChoiceDialog.show();
            }
        });

        listViewOff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Home home=offhomeList.get(position);
                final String[] items = {"撤销发布", "进行发布"};
                final int[] yourChoice = {-1};
                AlertDialog.Builder singleChoiceDialog =
                        new AlertDialog.Builder(MyHomes.this);
                singleChoiceDialog.setTitle("房屋 "+home.getRoomAddress()+" 是否发布");
                // 第二个参数是默认选项，此处设置为0
                singleChoiceDialog.setSingleChoiceItems(items, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                yourChoice[0] = which;
                            }
                        });
                singleChoiceDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //不为-1 则证明进行了选择，否则直接取消操作
                                if (yourChoice[0] != -1) {
                                    //借用参数
                                    homeDao.changeRoomPub(home.getRoomUser(),items[yourChoice[0]]);
                                    Toast.makeText(MyHomes.this,
                                            items[yourChoice[0]],
                                            Toast.LENGTH_SHORT).show();
                                } else dialog.dismiss();
                            }
                        });
                singleChoiceDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onResume();
                    }
                });
                singleChoiceDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                singleChoiceDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        onhomeList =new ArrayList<>();
        offhomeList = new ArrayList<>();
        homeList=homeDao.getUserHome(userId);
        for (Home home : homeList){
            System.out.println(home.getRoomUser());
            String homeCost=homeDao.getHomeCost(home.getRoomType());
            home.setRoomCost(homeCost);
            String pub=home.getRoomPub();
            if ("1".equals(pub)){
                onhomeList.add(home);
            }else if ("0".equals(pub)){
                offhomeList.add(home);
            }
        }
        HomeAdapter homeAdapter=new HomeAdapter(this,R.layout.home_item,onhomeList);
        listViewOn.setAdapter(homeAdapter);
        HomeAdapter homeAdapter1=new HomeAdapter(this,R.layout.home_item,offhomeList);
        listViewOff.setAdapter(homeAdapter1);

    }
}
