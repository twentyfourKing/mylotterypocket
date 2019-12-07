package follow.twentyfourking.mylotterypocket.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import follow.twentyfourking.mylotterypocket.view.adapter.NumberShowAdapter;

public class SevenLotteryFragment extends Fragment {
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
    private Context mContext;
    private NumberShowAdapter mNumAdapter;
    private int mCurrentPosition = 0;
    private List<String> mInitData;

    public static Fragment newInstance() {
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

    @OnClick(R.id.tv_create_state)
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_create_state) {
            startChooseNumber(mCurrentPosition);
        }
    }

    private void initView() {
        mInitData = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerNumber.setLayoutManager(manager);
        mNumAdapter = new NumberShowAdapter();
        mNumAdapter.setData(createData());
        mRecyclerNumber.setAdapter(mNumAdapter);
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
        mMarquee.setContent(data);
        mMarquee.setTextSpeed(7);
        mMarquee.setTextColor(R.color.colorAccent);
        mMarquee.setTextSize(17);
        mMarquee.setVisibility(View.GONE);

        mTvNm.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);

    }

    private int number;

    public void startChooseNumber(int position) {
        if (position > 6) {
            mTvTitle.setVisibility(View.GONE);
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
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    Random random = new Random();
                    number = random.nextInt(10);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMarquee.setVisibility(View.GONE);
                            mTvNm.setText("" + number);
                            mTvNm.setVisibility(View.VISIBLE);
                            startAnimation(mTvNm, mRecyclerNumber);
                        }
                    });

                } catch (Exception e) {
                }
            }
        });
        thread.start();
    }

    private int lastTransitionX;
    private int lastTransitionY;
    int[] locationFrom = new int[2];
    private float origX = 0.0f;
    private float origY = 0.0f;

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
        animatorSet.setDuration(2000);
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
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        mInitData.add("?");
        return mInitData;
    }

    private void updatePositionItem(int value, int position) {
        mCurrentPosition++;
        mInitData.set(position, String.valueOf(value));
        mNumAdapter.updateItem(mInitData, position);
        startChooseNumber(mCurrentPosition);
    }
}
