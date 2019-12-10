package follow.twentyfourking.mylotterypocket.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marquee.dingrui.marqueeviewlib.MarqueeView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import follow.twentyfourking.mylotterypocket.R;
import follow.twentyfourking.mylotterypocket.view.adapter.NumberListAdapter;
import follow.twentyfourking.mylotterypocket.view.adapter.NumberShowAdapter;
import follow.twentyfourking.mylotterypocket.view.delegate.IFragmentCallback;
import follow.twentyfourking.mylotterypocket.viewmodel.data.SevenNumberListItemBean;

public class SevenLotteryFragment extends Fragment implements NumberListAdapter.IAdapterCallback {
    @BindView(R.id.tv_marquee)
    MarqueeView mMarquee;
    @BindView(R.id.tv_create_state)
    TextView mTvCreateNub;
    @BindView(R.id.tv_num)
    TextView mTvNm;
    @BindView(R.id.rcv_seven_num)
    RecyclerView mRecyclerNumber;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_arrow)
    TextView mTvArrow;
    @BindView(R.id.ll_choose_container)
    LinearLayout mLlChooseContainer;
    @BindView(R.id.ll_list)
    LinearLayout mLlListContainer;
    @BindView(R.id.rcv_number_list)
    RecyclerView mRecyclerViewList;

    public static int CREATE_NUMBER_TIME = 200;//计算号码时间
    public static int TRANSITION_NUMBER_TIME = 200;//号码动画时间

    private TranslateAnimation mTranslateAnimation;
    private Context mContext;
    private NumberShowAdapter mNumAdapter;
    private NumberListAdapter mListAdapter;
    private int mCurrentPosition = 0;
    private List<String> mInitData;
    private int lastTransitionX;
    private int lastTransitionY;
    int[] locationFrom = new int[2];
    private float origX = 0.0f;
    private float origY = 0.0f;

    private List<SevenNumberListItemBean> mNumberData;

    public static Fragment newInstance(IFragmentCallback callback) {
        SevenLotteryFragment fragment = new SevenLotteryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seven, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @OnClick({
            R.id.tv_create_state,
            R.id.tv_save,
            R.id.no_save,
            R.id.tv_clear
    })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_create_state) {
            mTvCreateNub.setEnabled(false);
            mNumAdapter.setData(createData());
            startChooseNumber(mCurrentPosition);
        } else if (id == R.id.tv_save) {
            hideChoose();
            mTvCreateNub.setEnabled(true);
            mTvCreateNub.setText("开始");
            List<String> data = new ArrayList<>();
            for (String str : mNumAdapter.getData()) {
                data.add(str);
            }
            SevenNumberListItemBean bean = new SevenNumberListItemBean();
            bean.setmNumberData(data);
            bean.setChooseState(false);
            mNumberData.add(bean);
            mListAdapter.setData(mNumberData);
            if (mLlListContainer.getVisibility() == View.GONE
                    && mNumberData != null && mNumberData.size() > 0) {
                mLlListContainer.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.no_save) {
            hideChoose();
            mTvCreateNub.setEnabled(true);
            mTvCreateNub.setText("开始");
        }else if(id == R.id.tv_clear){
            mListAdapter.clearData();
            mNumAdapter.setData(createData());
        }
    }

    private void showChoose() {
        mTranslateAnimation = new TranslateAnimation(0, 200, 0, 0);
        mTranslateAnimation.setDuration(200);
        mTranslateAnimation.setInterpolator(new AccelerateInterpolator());
        mTranslateAnimation.setRepeatCount(-1);
        mTranslateAnimation.setRepeatMode(ValueAnimator.REVERSE);
        mTranslateAnimation.start();
        mTvArrow.setAnimation(mTranslateAnimation);
        mTvArrow.setVisibility(View.VISIBLE);
        mLlChooseContainer.setVisibility(View.VISIBLE);
    }

    private void hideChoose() {
        if (mTranslateAnimation != null) {
            mTranslateAnimation.cancel();
        }
        mTvArrow.setVisibility(View.GONE);
        mLlChooseContainer.setVisibility(View.GONE);
    }

    private void initView() {
        mInitData = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerNumber.setLayoutManager(manager);
        mNumAdapter = new NumberShowAdapter();
        mNumAdapter.setData(createData());
        mRecyclerNumber.setAdapter(mNumAdapter);
        mMarquee.setContent(createInitData());
        mMarquee.setTextSpeed(7);
        mMarquee.setTextColor(R.color.colorAccent);
        mMarquee.setTextSize(17);
        mMarquee.setVisibility(View.GONE);

        mTvNm.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);


        mNumberData = new ArrayList<>();
        mListAdapter = new NumberListAdapter(this, 0);
        LinearLayoutManager manager1 = new LinearLayoutManager(mContext);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerViewList.getContext(),
                manager1.getOrientation());
        divider.setDrawable(mContext.getResources().getDrawable(R.color.colorPrimaryDark));
        mRecyclerViewList.addItemDecoration(divider);
        mRecyclerViewList.setLayoutManager(manager1);
        mRecyclerViewList.setAdapter(mListAdapter);
        mLlListContainer.setVisibility(View.GONE);
    }

    private int number;

    public void startChooseNumber(int position) {
        if (position > 6) {
            mTvCreateNub.setText("完成");
            mTvTitle.setVisibility(View.GONE);
            mCurrentPosition = 0;
            showChoose();
            return;
        }
        int whichOne = position + 1;
        mTvTitle.setText("第" + whichOne + "个号码");
        if (mTvTitle.getVisibility() == View.GONE) {
            mTvTitle.setVisibility(View.VISIBLE);
        }
        if (mMarquee.getVisibility() == View.GONE) {
            mMarquee.setVisibility(View.VISIBLE);
        }
        mTvNm.setText("?");
        mTvNm.setVisibility(View.VISIBLE);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(CREATE_NUMBER_TIME);
                    Random random = new Random();
                    number = random.nextInt(10);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMarquee.setVisibility(View.GONE);
                            mTvNm.setText("" + number);
                            startAnimation(mTvNm, mRecyclerNumber);
                        }
                    });

                } catch (Exception e) {
                }
            }
        });
        thread.start();
    }

    private void startAnimation(final View fromView, final View toView) {
        if (origX == 0.0f) {
            origX = fromView.getX();
        }
        if (origY == 0.0f) {
            origY = fromView.getY();
        }
        fromView.getLocationOnScreen(locationFrom);

        int[] locationTo = new int[2];
        toView.getLocationOnScreen(locationTo);

        int toW = ((RecyclerView) toView).getChildAt(mCurrentPosition).getWidth();
        int toH = ((RecyclerView) toView).getChildAt(mCurrentPosition).getHeight();
        int fromW = mTvNm.getWidth();
        int fromH = mTvNm.getHeight();
        int adjustPadX = toW / 2 + toW * mCurrentPosition - fromW / 2;
        int adjustPadY = toH / 2 - fromH / 2;
        lastTransitionX = locationFrom[0] - locationTo[0] - adjustPadX;
        lastTransitionY = locationFrom[1] - locationTo[1] - adjustPadY;

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(fromView,
                "translationX", -lastTransitionX);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(fromView,
                "translationY", -lastTransitionY);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(TRANSITION_NUMBER_TIME);
        animatorSet.playTogether(animator1, animator2);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fromView.setX(origX);
                fromView.setY(origY);
                ((TextView) fromView).setText("");
                fromView.setVisibility(View.GONE);
                updatePositionItem(number, mCurrentPosition);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    private List<String> createData() {
        mInitData.clear();
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        return mInitData;
    }

    private List<String> createInitData() {
        List<String> data = new ArrayList<>();
        data.add("0");
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");
        data.add("9");
        return data;
    }

    private void updatePositionItem(int value, int position) {
        mCurrentPosition++;
        mInitData.set(position, String.valueOf(value));
        mNumAdapter.updateItem(mInitData, position);
        startChooseNumber(mCurrentPosition);
    }

    @Override
    public void setVisibility() {
        mLlListContainer.setVisibility(View.GONE);
    }
}
