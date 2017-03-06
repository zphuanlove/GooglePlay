package com.itheima_zphuan.googleplay.holder;

import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itheima_zphuan.googleplay.R;
import com.itheima_zphuan.googleplay.base.BaseHolder;
import com.itheima_zphuan.googleplay.base.MyApplication;
import com.itheima_zphuan.googleplay.conf.Constants;
import com.itheima_zphuan.googleplay.utils.UIUtils;
import com.itheima_zphuan.googleplay.views.ChildViewPager;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 轮播图的ViewHolder
 */
public class HomePicturesHolder extends BaseHolder<List<String>> implements ViewPager.OnPageChangeListener {

    @BindView(R.id.item_home_picture_pager)
    ChildViewPager mItemHomePicturePager;
    @BindView(R.id.item_home_picture_container_indicator)
    LinearLayout mItemHomePictureContainerIndicator;
    private List<String> mPictureUrls;

    /**
     * 自动轮播的Task
     */
    private AutoScrollTask mAutoScrollTask;

    /**
     * 决定HomePicturesHolder所能提供的视图长什么样子
     */
    @Override
    public View initHolderView() {
        View holderView = View.inflate(UIUtils.getContext(), R.layout.item_home_pictures, null);
        ButterKnife.bind(this, holderView);
        SharedPreferences xxx = UIUtils.getContext().getSharedPreferences("xxx", 0);
        SharedPreferences.Editor edit = xxx.edit();
        edit.apply();
        return holderView;
    }

    /**
     * 数据和视图的绑定
     */
    @Override
    public void refreshHolderView(List<String> pictureUrls) {
        //保存数据集到成员变量中
        mPictureUrls = pictureUrls;

        /*--------------- mItemHomePicturePager绑定 ---------------*/
        //view->mItemHomePicturePager
        //data-->局部变量
        //data+view
        mItemHomePicturePager.setAdapter(new HomePicturePagerAdapter());

        /*---------------mItemHomePictureContainerIndicator绑定  ---------------*/
        for (int i = 0; i < mPictureUrls.size(); i++) {
            ImageView ivIndicator = new ImageView(UIUtils.getContext());
            //设置默认时候的点的src
            ivIndicator.setImageResource(R.drawable.indicator_normal);

            //选择默认选中第一个点
            if (i == 0) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
//            int sixDp = (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
//                    UIUtils.getResources().getDisplayMetrics()) + .5f);

            int width = UIUtils.dip2Px(6);//6dp
            int height = UIUtils.dip2Px(6);//6dp
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            params.leftMargin = UIUtils.dip2Px(6);//6dp
            params.bottomMargin = UIUtils.dip2Px(6);//6dp

            mItemHomePictureContainerIndicator.addView(ivIndicator, params);
        }

        //监听ViewPager的页面切换操作
        mItemHomePicturePager.setOnPageChangeListener(this);

        //设置viewPager页面的初始位置
        int curItem = Integer.MAX_VALUE / 2;
        //对curItem做偏差处理
        int diff = Integer.MAX_VALUE / 2 % mPictureUrls.size();
        curItem = curItem - diff;
        mItemHomePicturePager.setCurrentItem(curItem);
//        mItemHomePicturePager.setCurrentItem(mPictureUrls.size() * 1000);

//        实现自动轮播
        if (mAutoScrollTask == null) {
            mAutoScrollTask = new AutoScrollTask();
            mAutoScrollTask.start();
        }

        //按下去的时候停止轮播
        mItemHomePicturePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mAutoScrollTask.stop();
                        break;
                    case MotionEvent.ACTION_UP:
                        mAutoScrollTask.start();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //处理position
        position = position % mPictureUrls.size();

        //控制Indicator选中效果
        for (int i = 0; i < mPictureUrls.size(); i++) {
            ImageView ivIndicator = (ImageView) mItemHomePictureContainerIndicator.getChildAt(i);
            //1.还原默认效果
            ivIndicator.setImageResource(R.drawable.indicator_normal);
            //2.选中应该选中的
            if (position == i) {
                ivIndicator.setImageResource(R.drawable.indicator_selected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class HomePicturePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            if (mPictureUrls != null) {
//                return mPictureUrls.size();
                return Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //处理position
            position = position % mPictureUrls.size();

            //view
            ImageView iv = new ImageView(UIUtils.getContext());
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            //data
            String url = mPictureUrls.get(position);

            //data+view
            Picasso.with(UIUtils.getContext()).load(Constants.URLS.IMGBASEURL + url).into(iv);

            //把view加入到容器中
            container.addView(iv);

            //返回具体的view
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 实现自动轮播的Runnable对象
     */
    class AutoScrollTask implements Runnable {
        /**
         * 开始滚动
         */
        public void start() {
//            stop();
            MyApplication.getMainThreadHandler().postDelayed(this, 3000);
        }

        /**
         * 结束滚动
         */
        public void stop() {
            MyApplication.getMainThreadHandler().removeCallbacks(this);
        }

        @Override
        public void run() {
            //切换ViewPager
            int currentItem = mItemHomePicturePager.getCurrentItem();
            currentItem++;
            mItemHomePicturePager.setCurrentItem(currentItem);
            start();
        }
    }
}
