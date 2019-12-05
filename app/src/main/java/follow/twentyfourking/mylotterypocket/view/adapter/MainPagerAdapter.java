package follow.twentyfourking.mylotterypocket.view.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentData;


    public MainPagerAdapter(@NonNull FragmentManager fm, int behavior, List<Fragment> fragmentData) {
        super(fm, behavior);
        this.mFragmentData = fragmentData;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentData.get(position);
    }

    @Override
    public int getCount() {
        if (mFragmentData != null) {
            return mFragmentData.size();
        }
        return 0;
    }
}
