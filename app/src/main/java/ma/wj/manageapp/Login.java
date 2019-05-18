package ma.wj.manageapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import JavaDao.LoginDao;

public class Login extends AppCompatActivity {

    private String username;
    private String password;
    private LoginDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final TextView tvUsername=findViewById(R.id.username);
        final TextView tvPassword=findViewById(R.id.password);
        Button btnLogin=findViewById(R.id.login_btn);
        Button btnRegister=findViewById(R.id.register_btn);
        dao=new LoginDao(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(Login.this,Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=tvUsername.getText().toString().trim();
                password=tvPassword.getText().toString().trim();
                String resId;
                if (!"".equals(username)&&!"".equals(password)){
                    resId = dao.login(Login.this.username, password);
                }else return;
                if (!resId.equals("")){
                    Intent intent=new Intent(Login.this,Main.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("User_Id",username);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
