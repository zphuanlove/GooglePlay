package com.itheima_zphuan.googleplay;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStripExtends;
import com.itheima_zphuan.googleplay.base.BaseFragment;
import com.itheima_zphuan.googleplay.base.LoadingPager;
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
        initListener();
        requestPermission();
		
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
        mainViewpager.setOffscreenPageLimit(7);

        // Bind the tabs to the ViewPager 绑定SlidingTab和ViewPager
        mainTabs.setViewPager(mainViewpager);
    }

    private void initListener() {
        final MyOnpageChangeListener myOnpageChangeListener = new MyOnpageChangeListener();
        //监听ViewPager页面的切换
        mainTabs.setOnPageChangeListener(myOnpageChangeListener);
        mainViewpager.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //ViewPager已经展示给用户看-->说明HomeFragment和AppFragment已经创建好了
                //手动选中第一页，触发加载数据的方法
                myOnpageChangeListener.onPageSelected(0);
                mainViewpager.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    class MyOnpageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
//            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().getFragments().get(position);
            //根据position找到对应页面的Fragment
            BaseFragment fragment = FragmentFactory.mCacheFragments.get(position);
            // 拿到Fragment里面的LoadingPager
            LoadingPager loadingPager = fragment.getLoadingPager();
            // 触发加载数据
            loadingPager.triggerLoadData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    /*
     add  replace  show hide
      PagerAdapter-->View
      FragmentStatePagerAdapter-->Fragment---没有缓存
      FragmentPagerAdapter-->Fragment   ---做了缓存
     */
    class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {

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

    /**
     * 6.0手机->请求读写SD卡权限
     */
    private void requestPermission() {
        //步骤：1 检查权限  2 没有授权：申请授权   3 处理回调
        //1 检查权限
        int checkSelfPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //拒绝
        if(checkSelfPermission == PackageManager.PERMISSION_DENIED){
            KLog.d("没有操作sd卡权限");
            //2 申请权限(弹出一个申请权限的对话框)
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE},100);
        }
        //申请了权限
        else if(checkSelfPermission == PackageManager.PERMISSION_GRANTED){
            KLog.d("具备操作sd卡权限");
            Toast.makeText(this, "恭喜你，手机具备操作sd卡权限", Toast.LENGTH_SHORT).show();
        }
    }

}
