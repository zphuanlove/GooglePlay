package com.itheima_zphuan.googleplay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStripExtends;
import com.itheima_zphuan.googleplay.factory.FragmentFactory;
import com.itheima_zphuan.googleplay.utils.UIUtils;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_drawerLayout)
    DrawerLayout mainDrawerLayout;
    @BindView(R.id.main_tabs)
    PagerSlidingTabStripExtends mainTabs;
    @BindView(R.id.main_viewpager)
    ViewPager mainViewpager;

    private ActionBarDrawerToggle toggle;
    private String[] mMainTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActionBar();
        initActionBarDrawerToggle();
        initData();
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

    /**
     * 初始化ActionBarDrawerToggle
     */
    private void initActionBarDrawerToggle() {
        toggle = new ActionBarDrawerToggle(this, mainDrawerLayout, R.string.open, R.string.close);
        //同步状态方法-->替换默认回退部分的UI效果
        toggle.syncState();
        //设置drawerLayout的监听 --> DrawerLayout拖动的时候,toggle可以跟着改变ui
        mainDrawerLayout.setDrawerListener(toggle);
    }

    /**
     * 点击ActionBar的home按钮会执行该方法，所以在该方法中做判断
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //点击返回键
//                Toast.makeText(this, "点击了返回键", Toast.LENGTH_SHORT).show();
                toggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        //模拟数据集
        mMainTitles = UIUtils.getStrings(R.array.main_titles);

        //为viewPager设置适配器
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager());
        mainViewpager.setAdapter(adapter);

        // Bind the tabs to the ViewPager 绑定SlidingTab和ViewPager
        mainTabs.setViewPager(mainViewpager);
    }

    /*
      PagerAdapter-->View
      FragmentStatePagerAdapter-->Fragment
      FragmentPagerAdapter-->Fragment
     */
    class MainFragmentPagerAdapter extends FragmentStatePagerAdapter{

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {//决定ViewPager页数的总和
            KLog.d("创建了-->"+mMainTitles[position]);
            Fragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        @Override
        public int getCount() {
            if(null!=mMainTitles){
                return mMainTitles.length;
            }
            return 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(null!=mMainTitles){
                return mMainTitles[position];
            }
            return super.getPageTitle(position);
        }
    }

}
