package uk.co.myapplication.ui.main;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import uk.co.myapplication.DailyActivity.Friday;
import uk.co.myapplication.DailyActivity.Monday;
import uk.co.myapplication.DailyActivity.Saturday;
import uk.co.myapplication.DailyActivity.Sunday;
import uk.co.myapplication.DailyActivity.Thursday;
import uk.co.myapplication.DailyActivity.Tuesday;
import uk.co.myapplication.DailyActivity.Wednesday;
import uk.co.myapplication.Model.Dates;
import uk.co.myapplication.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Dates date;
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
        date = new Dates(mContext);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){
            case 0:
                return new Monday();
            case 1:
                return new Tuesday();
            case 2:
                return new Wednesday();
            case 3:
                return new Thursday();
            case 4:
                return new Friday();
            case 5:
                return new Saturday();
            case 6:
                return new Sunday();
            default:
                return null;

        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return date.TabTitles(position);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 7;
    }
}