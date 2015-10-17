package com.magcomm.demos;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magcomm.ui.fragment.FloapWrapFragment;
import com.magcomm.ui.fragment.MenuFragment;
import com.magcomm.ui.fragment.UberFragment;
import com.magcomm.util.Contants;
import com.magcomm.viewgroup.MagcommViewPage;
import com.magcomm.viewgroup.MagcommViewPage.TransitionEffect;
import com.magcomm.viewgroup.OutlineContainer;

import java.util.ArrayList;

/**
 * Created by lenovo on 15-10-10.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private UberFragment mFirstFragment, mFourthFragment;
    private MenuFragment mSecondFragment;
    private FloapWrapFragment mThirdFragment;
    private ArrayList<Fragment> mFragmentList;

    private boolean mLeft = false;
    private boolean mRight = false;
    private boolean isScrolling = false;
    private int mLastValue = -1;

    private MagcommViewPage mPager;
    private LinearLayout mIndicatorLayout;
    private TextView[] mIndicatorViews = new TextView[Contants.NUM_PAGES];//绘制indicator icon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.main_layout);

        mFirstFragment = new UberFragment();
        mSecondFragment = new MenuFragment();
        mThirdFragment = new FloapWrapFragment();
        mFourthFragment = new UberFragment();

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(mFirstFragment);
        mFragmentList.add(mSecondFragment);
        mFragmentList.add(mThirdFragment);
        mFragmentList.add(mFourthFragment);

        setUpViews();
    }

    private void setUpViews() {
        mPager = (MagcommViewPage) findViewById(R.id.pager);
        mIndicatorLayout = (LinearLayout) findViewById(R.id.indicator_layout);
        setIndicatorLayout();
        mPager.setTransitionEffect(TransitionEffect.CubeIn);
        mPager.setPageMargin(30);
        mPager.setAdapter(new ScreenSlidePageAdapter(getSupportFragmentManager()));
        //mPager.setAdapter(new MainAdapter());
        setPageChangeListener(mPager);
    }

    //绘制导航icon
    private void setIndicatorLayout() {
        for (int i = 0; i < mIndicatorViews.length; i++) {
            mIndicatorViews[i] = new TextView(MainActivity.this);
            mIndicatorViews[i].setText(i + 1 + "");
            //mIndicatorViews[i].setTextColor(Color.WHITE);
            mIndicatorViews[i].setTextSize((int) getResources().getDimension(R.dimen.dimen_5_sp));
            mIndicatorViews[i].setWidth((int) getResources().getDimension(R.dimen.dimen_14));
            mIndicatorViews[i].setHeight((int) getResources().getDimension(R.dimen.dimen_14));
            mIndicatorViews[i].setGravity(Gravity.CENTER);
            mIndicatorViews[i].setBackgroundResource(R.drawable.rounded_cell_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins((int) getResources().getDimension(R.dimen.dimen_8), 0,
                    (int) getResources().getDimension(R.dimen.dimen_8), 0);
            mIndicatorViews[i].setLayoutParams(params);
            mIndicatorLayout.addView(mIndicatorViews[i]);
        }
        mIndicatorViews[0].setBackgroundResource(R.drawable.rounded_cell_red);
    }

   /* private class MainAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            TextView text = new TextView(MainActivity.this);
            text.setGravity(Gravity.CENTER);
            text.setTextSize(30);
            text.setTextColor(Color.WHITE);
            text.setText("Page " + position);
            text.setPadding(30, 30, 30, 30);
            int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
                    (int) Math.floor(Math.random() * 128) + 64,
                    (int) Math.floor(Math.random() * 128) + 64);
            text.setBackgroundColor(bg);
            container.addView(text, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mPager.setObjectForPosition(text, position);
            return text;
        }

        @Override
        public int getCount() {
            return Contants.NUM_PAGES;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object obj) {
            container.removeView(mPager.findViewFromObject(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            if (view instanceof OutlineContainer) {
                return ((OutlineContainer) view).getChildAt(0) == obj;
            } else {
                return view == obj;
            }
        }
    }*/

    private class ScreenSlidePageAdapter extends FragmentPagerAdapter {

        public ScreenSlidePageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
            //Contants.NUM_PAGES;
        }
    }

    private void setPageChangeListener(ViewPager viewPages) {
        viewPages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (isScrolling) {
                    if (mLastValue > positionOffsetPixels) {
                        // 递减，向右侧滑动
                        mRight = true;
                        mLeft = false;
                        Log.i(TAG, "right");
                    } else if (mLastValue < positionOffsetPixels) {
                        // 递减，向右侧滑动
                        Log.i(TAG, "left");
                        mRight = false;
                        mLeft = true;
                    } else if (mLastValue == positionOffsetPixels) {
                        mRight = mLeft = false;
                    }
                }
                mLastValue = positionOffsetPixels;
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mIndicatorViews.length; i++) {
                    mIndicatorViews[i].setBackgroundResource(R.drawable.rounded_cell_gray);
                }
                mIndicatorViews[position].setBackgroundResource(R.drawable.rounded_cell_red);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1) {
                    isScrolling = true;
                } else {
                    isScrolling = false;
                }

                if (state == 2) {
                    Log.i(TAG, "MagcommViewPager  onPageScrollStateChanged  direction left ? " + mLeft);
                    mRight = mLeft = false;
                }
            }
        });
    }
}
