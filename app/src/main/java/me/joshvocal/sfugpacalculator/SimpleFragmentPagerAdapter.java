package me.joshvocal.sfugpacalculator;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by jvocal on 2017-01-09.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CgpaCalculatorFragment();
            case 1:
                return new GpaForTargetCgpaFragment();
            case 2:
                return new UnitsForTargetCgpaFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.CGPAcalculator);
            case 1:
                return mContext.getString(R.string.GPAForTarget);
            case 2:
                return mContext.getString(R.string.UnitsForTarget);
            default:
                return null;
        }
    }
}
