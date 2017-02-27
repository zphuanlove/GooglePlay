package com.itheima_zphuan.googleplay;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //设置标题
        actionBar.setTitle("谷歌电子市场");
        actionBar.setSubtitle("zphuan");
        //设置图标
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setLogo(R.drawable.ic_about);

        //显示logo/icon(图标)，默认是false,默认是隐藏图标
        actionBar.setDisplayShowHomeEnabled(false);
        //修改icon和logo显示的优先级，默认是false,默认是没用logo,用的icon
        actionBar.setDisplayUseLogoEnabled(false);

        //显示回退部分，默认是false,默认隐藏了回退部分
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                //点击返回键
                Toast.makeText(this, "点击了返回键", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
