package follow.twentyfourking.mylotterypocket.view.delegate;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import follow.twentyfourking.mylotterypocket.R;
import follow.twentyfourking.mylotterypocket.model.repo.MainRepository;
import follow.twentyfourking.mylotterypocket.view.activity.MainActivity;
import follow.twentyfourking.mylotterypocket.view.adapter.MainPagerAdapter;
import follow.twentyfourking.mylotterypocket.view.fragment.DoubleLotteryFragment;
import follow.twentyfourking.mylotterypocket.view.fragment.HistoryFragment;
import follow.twentyfourking.mylotterypocket.view.fragment.SevenLotteryFragment;
import follow.twentyfourking.mylotterypocket.viewmodel.factory.MainFactory;
import follow.twentyfourking.mylotterypocket.viewmodel.viewmodel.MainViewModel;

/**
 * activity_main.xml
 */
public class MainActivityDelegate {
    @BindView(R.id.tab_main)
    TabLayout mTabLayout;
    @BindView(R.id.vp_main)
    ViewPager mViewPager;
    private TabLayout.Tab[] mTabs;

    private MainActivity mActivity;
    private MainViewModel mViewModel;
    private MainRepository mMainRepository;
    private MainPagerAdapter mAdapter;

    public MainActivityDelegate() {

    }

    public void setDelegateInit(MainActivity mainActivity, View view) {
        ButterKnife.bind(this, view);
        mActivity = mainActivity;
        initView();
        initViewModel();
    }

    private void setTab() {
        mTabs = new TabLayout.Tab[3];
        TabLayout.Tab tab1 = mTabLayout.newTab().setCustomView(createTabItemView("七星彩"));
        mTabLayout.addTab(tab1);
        mTabs[0] = tab1;

        TabLayout.Tab tab2 = mTabLayout.newTab().setCustomView(createTabItemView("双色球"));
        mTabLayout.addTab(tab2);
        mTabs[1] = tab2;

        TabLayout.Tab tab3 = mTabLayout.newTab().setCustomView(createTabItemView("数据中心"));
        mTabLayout.addTab(tab3);
        mTabs[2] = tab3;

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private View createTabItemView(String str) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.view_tab_item, null, false);
        ((TextView) (view.findViewById(R.id.tv_tab_item_tag))).setText(str);
        return view;
    }

    private void initView() {
        setTab();
        setViewPager();
    }

    private void setViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SevenLotteryFragment.newInstance());
        fragments.add(DoubleLotteryFragment.newInstance());
        fragments.add(HistoryFragment.newInstance());
        mAdapter = new MainPagerAdapter(mActivity.getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabs[position].select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(mAdapter);
    }

    private void initViewModel() {
        ViewModelProvider.Factory factory = new MainFactory();
        ViewModelStore store = mActivity.getViewModelStore();
        ViewModelProvider provider = new ViewModelProvider(store, factory);
        mViewModel = provider.get(MainViewModel.class);
        mMainRepository = new MainRepository(mViewModel);

    }

    private void setObserveData() {

    }
}
