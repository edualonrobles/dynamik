package com.edualonso.dynamik.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edualonso.dynamik.R;

/**
 * Created by edu_g on 07/07/2017.
 */


public class CompetitionFragment extends Fragment{



    private ViewPager pager;
    private TabLayout tabLayout;

    public static CompetitionFragment newInstance(int page, String title) {
        CompetitionFragment compFragment = new CompetitionFragment();
        Bundle args = new Bundle();
        compFragment.setArguments(args);
        return compFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tl_competitions);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(this.getContext()));





        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                tabLayout.getTabAt(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        tabLayout.addTab(tabLayout.newTab().setText(R.string.competition));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.matches));
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(pager);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager, container, false);
        return view;

    }


    public static class MyPagerAdapter extends PagerAdapter {
        private static int NUM_ITEMS = 2;
        Context context;

        public MyPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

//            if(position==0){
//                RelativeLayout rl = new RelativeLayout(context);
//                TextView tv = new TextView(context);
//                tv.setText("tab1");
//                rl.addView(tv);
//                return rl;
//            }else{
//                RelativeLayout rl = new RelativeLayout(context);
//                TextView tv = new TextView(context);
//                tv.setText("tab2");
//                rl.addView(tv);
//                return rl;
//            }
            LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewGroup one =(ViewGroup) layoutInflater.inflate(R.layout.fragment_competition,container,false);
            ViewGroup two =(ViewGroup) layoutInflater.inflate(R.layout.fragment_game,container,false);
            View viewarr[]={one,two};
            container.addView(viewarr[position]);
            return viewarr[position];



        }

        @Override
        public CharSequence getPageTitle(int position) {

            return position == 0 ? "Competicion" : "Partido";
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


}
