package com.soubu.crmproject.delegate;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.soubu.crmproject.R;
import com.soubu.crmproject.base.mvp.view.AppDelegate;
import com.soubu.crmproject.widget.TitleFragmentPagerAdapter;

import java.util.List;

/**
 * Created by dingsigang on 17-1-17.
 */
public class DamnCustomerActivityDelegate extends AppDelegate{

    TabLayout tabLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_damn_customer_spec;
    }

    public void initFragment(List<Fragment> fragmentList, String[] titles) {
        ViewPager viewPager = get(R.id.vp_content);
        tabLayout = get(R.id.tl_title);

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getActivity().getSupportFragmentManager(),
                fragmentList, titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


    public void setOnTabClickListener(TabLayout.OnTabSelectedListener listener){
        tabLayout.addOnTabSelectedListener(listener);
    }

}
