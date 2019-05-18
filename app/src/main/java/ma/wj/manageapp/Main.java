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
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import Adapter.HomeAdapter;
import JavaBean.Home;
import JavaDao.HomeDao;
import JavaDao.LoginDao;

public class Main extends AppCompatActivity {

    public Bundle bundle;
    private String userId;
    private PopupWindow popupWindow;
    private ListView listView;
    private String[] iconName = {"我的房屋", "缴费", "物业通知","充值"};
    private List<Map<String, Object>> data_list = new ArrayList<>();
    // 图片封装为一个数组
    private int[] icon = {R.drawable.homemessage, R.drawable.pay,
            R.drawable.message, R.drawable.input};
    private List<Home> homeList;
    private List<Home> homeList_new;
    private HomeDao homeDao=new HomeDao(this);
    private HomeAdapter homeAdapter;
    private LoginDao loginDao;
    private Button userInfo;
    private TextView bar_username;

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
        setContentView(R.layout.activity_main);
        loginDao=new LoginDao(this);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        if (bundle!=null){
            userId=bundle.getString("User_Id");

        }
        Toolbar toolbar=findViewById(R.id.main_toolbar);
        listView=findViewById(R.id.search_result);
        userInfo=findViewById(R.id.user_info_change);
        bar_username=findViewById(R.id.bar_username);
        bar_username.setText("欢迎, "+loginDao.getRealname(userId));
        toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        getData();
        final GridView gridView = findViewById(R.id.gridView);
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        final SimpleAdapter sim_adapter = new SimpleAdapter(this, data_list, R.layout.main_all_item, from, to);
        gridView.setAdapter(sim_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent myHomes=new Intent();
                        myHomes.putExtras(bundle);
                        myHomes.setClass(Main.this,MyHomes.class);
                        startActivity(myHomes);
                        break;
                    case 1:
                        final String[] items = {"缴费"};
                        final int[] yourChoice = {-1};
                        AlertDialog.Builder singleChoiceDialog =
                                new AlertDialog.Builder(Main.this);
                        singleChoiceDialog.setTitle("需缴纳费用 "+homeDao.getTotalCost(userId)+" 元");
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
                                            float result=loginDao.getAccount(userId)-homeDao.getTotalCost(userId);
                                            loginDao.changeAccountFromCost(userId,result);
                                            Toast.makeText(Main.this,
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
                        break;
                    case 2:
                        Intent reportIntent=new Intent(Main.this,ReportMain.class);
                        reportIntent.putExtras(bundle);
                        startActivity(reportIntent);
                        break;
                    case 3:
                        final EditText editText=new EditText(Main.this);
                        float account=loginDao.getAccount(userId);
                        editText.setHint("账户余额:"+String.valueOf(account));
                        editText.setMaxLines(1);
                        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                        final AlertDialog.Builder builder=new AlertDialog.Builder(Main.this);
                        builder.setTitle("账户充值").setView(editText);
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                onResume();
                            }
                        });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.length()!=0){
                                    boolean sign =loginDao.changeAccount(userId,editText.getText().toString());
                                    if (sign){
                                        Toast.makeText(Main.this,"金额 "+editText.getText().toString()+"元，充值成功",
                                                Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Main.this,"充值失败.",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).show();
                        break;
                }
            }});
    }

    @Override
    protected void onResume() {
        super.onResume();
        homeList=new ArrayList<>();
        homeList_new=new ArrayList<>();


        homeList=homeDao.getHomeList();
        Iterator homeIterator=homeList.iterator();
        while (homeIterator.hasNext()){
            Home home= (Home) homeIterator.next();
            if (!home.getRoomPub().equals("1")){
                homeIterator.remove();
            }
        }
        for (Home home:homeList){
            String roomUser = home.getRoomUser();
            String realName=loginDao.getRealname(roomUser);
            home.setRoomUser(realName);
            String cost=homeDao.getHomeCost(home.getRoomType());
            home.setRoomCost(cost);
            homeList_new.add(home);
        }
        homeAdapter=new HomeAdapter(this,R.layout.home_item,homeList_new);
        listView.setAdapter(homeAdapter);
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userInfo=new Intent();
                userInfo.putExtras(bundle);
                userInfo.setClass(Main.this,LoginInfo.class);
                startActivity(userInfo);
            }
        });
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.date_popupwindow, null);
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        TextView logout= contentView.findViewById(R.id.logout);
        TextView cancel = contentView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Main.this.finish();
               popupWindow.dismiss();
            }
        });
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }

    public List<Map<String, Object>> getData() {
        //icon和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }
}
