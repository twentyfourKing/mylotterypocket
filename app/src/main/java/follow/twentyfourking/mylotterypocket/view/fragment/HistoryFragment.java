package follow.twentyfourking.mylotterypocket.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import follow.twentyfourking.mylotterypocket.R;
import follow.twentyfourking.mylotterypocket.model.repo.MainRepository;
import follow.twentyfourking.mylotterypocket.view.delegate.IFragmentCallback;

public class HistoryFragment extends Fragment {
    @BindView(R.id.tv_choose_time)
    TextView mTvTime;
    @BindView(R.id.cb_qixingcai)
    CheckBox mCbQi;
    @BindView(R.id.cb_shuangseqiu)
    CheckBox mCbShuang;

    private Context mContext;
    private TimePickerView mTimePicker;
    private IFragmentCallback mCallback;

    private String mTimeStr;
    private String mType;

    public static Fragment newInstance(IFragmentCallback callback) {
        HistoryFragment fragment = new HistoryFragment();
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @OnClick({
            R.id.tv_choose_time,
            R.id.tv_query
    })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_choose_time) {
            mTimePicker.show();
        } else if (id == R.id.tv_query) {
            //根据时间查询
            String type;
            if (isType() == 0) {
                type = "qixingcai";
            } else if (isType() == 1) {
                type = "shuangseqiu";
            } else {
                type = null;
            }
            if (TextUtils.isEmpty(type)) {
                ToastUtils.showLong("请选择查询的彩票类别");
                return;
            }
            long startTime = timeToLongNo(mTimeStr);
            if (startTime == -1) {
                ToastUtils.showLong("请重新选择查询时间");
                return;
            }
            String endStr = mTimeStr + " 23:59:59";
            long endTime = timeToLong(endStr);
            ((MainRepository) (mCallback.onGetRepository())).getLotteryByTime(startTime, endTime, type);
        }
    }

    private int isType() {
        if (mCbQi.isChecked()) {
            return 0;
        }
        if (mCbShuang.isChecked()) {
            return 1;
        }
        return -1;
    }


    private long timeToLong(String timeStr) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(timeStr).getTime();
        } catch (Exception e) {
            Log.d("TTT", "");
            return -1;
        }
    }

    private long timeToLongNo(String timeStr) {
        String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(timeStr).getTime();
        } catch (Exception e) {
            Log.d("TTT", "");
            return -1;
        }
    }

    private String getTime(long time) {
        final String format = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date(time));
    }

    private String getTimeAll(long time) {
        final String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(time);
    }

    private void initView() {
        mTimeStr = getTime(System.currentTimeMillis());
        mTvTime.setText(mTimeStr);

        mCbShuang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCbQi.setChecked(false);
                }
            }
        });
        mCbQi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCbShuang.setChecked(false);
                }
            }
        });
        initTimePicker();
    }

    private void initTimePicker() {
        mTimePicker = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mTimeStr = getTime(date.getTime());
                mTvTime.setText(mTimeStr);
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                Log.i("TTT", "onTimeSelectChanged");
            }
        }).setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("TTT", "onCancelClickListener");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();


        Dialog mDialog = mTimePicker.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            mTimePicker.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }
}
