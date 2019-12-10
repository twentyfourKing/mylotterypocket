package follow.twentyfourking.mylotterypocket.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import follow.twentyfourking.mylotterypocket.R;
import follow.twentyfourking.mylotterypocket.model.repo.MainRepository;
import follow.twentyfourking.mylotterypocket.view.adapter.NumberListAdapter;
import follow.twentyfourking.mylotterypocket.view.delegate.IFragmentCallback;
import follow.twentyfourking.mylotterypocket.view.widget.ChooseDialog;
import follow.twentyfourking.mylotterypocket.viewmodel.data.SevenNumberListItemBean;
import follow.twentyfourking.mylotterypocket.viewmodel.db.LotteryEntity;

public class DoubleLotteryFragment extends Fragment implements ChooseDialog.IChooseDialogCallback,
        NumberListAdapter.IAdapterCallback {
    @BindView(R.id.tv_ball1)
    TextView mTvBall1;
    @BindView(R.id.tv_ball2)
    TextView mTvBall2;
    @BindView(R.id.tv_ball3)
    TextView mTvBall3;
    @BindView(R.id.tv_ball4)
    TextView mTvBall4;
    @BindView(R.id.tv_ball5)
    TextView mTvBall5;
    @BindView(R.id.tv_ball6)
    TextView mTvBall6;
    @BindView(R.id.tv_ball7)
    TextView mTvBall7;

    @BindView(R.id.tv_create)
    TextView mTvCreate;

    @BindView(R.id.rcv_ball_list)
    RecyclerView mRecyclerViewBallList;
    @BindView(R.id.tv_clear)
    TextView mTvClear;
    @BindView(R.id.ll_data_list_container)
    LinearLayout mDataListContainer;


    private Context mContext;
    private int mCurrentPosition = 0;
    private List<String> mBallNumData;
    private ObjectAnimator mWaitingView;
    private SparseArray<View> mAllBallView = new SparseArray<>();

    private NumberListAdapter mAdapter;
    private List<SevenNumberListItemBean> mData;
    private Random mRandom ;
    private IFragmentCallback mCallback;

    public static Fragment newInstance(IFragmentCallback callback) {
        DoubleLotteryFragment fragment = new DoubleLotteryFragment();
        fragment.setCallback(callback);
        return fragment;
    }

    private void setCallback(IFragmentCallback callback) {
        this.mCallback = callback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mBallNumData = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_double, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @OnClick({
            R.id.tv_clear,
            R.id.tv_create
    })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_clear) {
            mAdapter.clearData();
            setInitValue();
        } else if (id == R.id.tv_create) {
            setInitValue();
            mTvCreate.setEnabled(false);
            mTvCreate.setAlpha(0.3f);
            createBallNumber(mCurrentPosition);
        }
    }

    private void initView() {
        initData();

        mAdapter = new NumberListAdapter(this, 1);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(mRecyclerViewBallList.getContext(),
                manager.getOrientation());
        itemDecoration.setDrawable(mContext.getResources().getDrawable(R.color.colorPrimaryDark));
        mRecyclerViewBallList.addItemDecoration(itemDecoration);
        mRecyclerViewBallList.setLayoutManager(manager);
        mRecyclerViewBallList.setAdapter(mAdapter);
        mDataListContainer.setVisibility(View.GONE);
    }

    private void initData() {
        mData = new ArrayList<>();
        mRandom = new Random();

        setInitValue();
        mAllBallView.put(0, mTvBall1);
        mAllBallView.put(1, mTvBall2);
        mAllBallView.put(2, mTvBall3);
        mAllBallView.put(3, mTvBall4);
        mAllBallView.put(4, mTvBall5);
        mAllBallView.put(5, mTvBall6);
        mAllBallView.put(6, mTvBall7);
    }

    private void setInitValue(){
        mTvBall1.setText("?");
        mTvBall2.setText("?");
        mTvBall3.setText("?");
        mTvBall4.setText("?");
        mTvBall5.setText("?");
        mTvBall6.setText("?");
        mTvBall7.setText("?");
    }

    private void startAnimation(int position) {
        View view = mAllBallView.get(position);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0, -200);
        objectAnimator1.setDuration(100);
        objectAnimator1.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(view, View.ROTATION, 0, 360, Animation.RELATIVE_TO_SELF, 1);
        objectAnimator2.setDuration(100);
        objectAnimator2.setInterpolator(new AccelerateInterpolator());
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0, -200, 0, 200, 0);
        objectAnimator3.setDuration(100);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -200, 0);
        objectAnimator4.setDuration(100);
        objectAnimator4.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playSequentially(objectAnimator1, objectAnimator2, objectAnimator3, objectAnimator4);
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ((TextView) (mAllBallView.get(position))).setText(mBallNumData.get(position));
                mCurrentPosition++;
                if (mCurrentPosition == 7) {
                    mCurrentPosition = 0;
                    mTvCreate.setEnabled(true);
                    mTvCreate.setAlpha(1.0f);
                    showDialog();
                    return;
                }
                createBallNumber(mCurrentPosition);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentPosition = 0;
                mTvCreate.setEnabled(true);
                mTvCreate.setAlpha(1.0f);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void createBallNumber(final int position) {
        waitingAnimation(position);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mBallNumData.add(position, String.valueOf(createRandomValue()));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWaitingView.cancel();
                        startAnimation(position);
                    }
                });
            }
        });
        thread.start();
    }

    private void waitingAnimation(int position) {
        View view = mAllBallView.get(position);
        mWaitingView = ObjectAnimator.ofFloat(view, View.ROTATION, 0, 360, Animation.RELATIVE_TO_SELF, 1);
        mWaitingView.setDuration(1000);
        mWaitingView.setInterpolator(new AccelerateInterpolator());
        mWaitingView.setRepeatCount(-1);
        mWaitingView.start();
    }

    private int createRandomValue() {
        int number;
        if (mCurrentPosition == 6) {
            number = mRandom.nextInt(17);

        } else {
            number = mRandom.nextInt(34);
        }
        if (number == 0) {
            return createRandomValue();
        }
        boolean have = false;
        if (mBallNumData != null && mBallNumData.size() > 0) {
            for (String str : mBallNumData) {
                if (Integer.valueOf(str) == number) {
                    have = true;
                    break;
                }
            }
        }
        if (have) {
            return createRandomValue();
        } else {
            return number;
        }
    }

    private void showDialog() {
        ChooseDialog chooseDialog = new ChooseDialog(mContext, this);
        chooseDialog.show();
    }

    @Override
    public void onSatisfaction() {
        SevenNumberListItemBean bean = new SevenNumberListItemBean();
        List<String> data = new ArrayList<>();
        for(String str : mBallNumData){
            data.add(str);
        }
        bean.setmNumberData(data);
        bean.setChooseState(false);
        mData.add(bean);
        mAdapter.setData(mData);
        mBallNumData.clear();
        if (mDataListContainer.getVisibility() == View.GONE) {
            mDataListContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setVisibility() {
        mDataListContainer.setVisibility(View.GONE);
    }

    @Override
    public void saveLottery(LotteryEntity entity) {
        if(mCallback.onGetRepository() instanceof MainRepository){
            ((MainRepository) mCallback.onGetRepository()).saveLotteryNumber(entity);
        }
    }
}
