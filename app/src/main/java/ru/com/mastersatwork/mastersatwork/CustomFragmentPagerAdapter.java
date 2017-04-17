package ru.com.mastersatwork.mastersatwork;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Текущие заказы", "Хроника"};
    private Context context;
    private String uId;

    public CustomFragmentPagerAdapter(FragmentManager fm, Context context, String userId) {
        super(fm);
        this.context = context;
        uId = userId;

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return CurrentTasksFragment.newInstance(uId);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}