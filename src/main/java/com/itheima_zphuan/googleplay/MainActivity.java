package com.itheima_zphuan.googleplay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.socks.library.KLog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KLog.d();
        KLog.i();
        KLog.e();
        KLog.a();
        KLog.v();
        KLog.w();
    }
}
