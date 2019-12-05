package follow.twentyfourking.mylotterypocket.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import follow.twentyfourking.mylotterypocket.R;
import follow.twentyfourking.mylotterypocket.view.adapter.NumberShowAdapter;

public class SevenLotteryFragment extends Fragment {

    RecyclerView mRecyclerViewNumShow;
    private Context mContext;
    private NumberShowAdapter mNumAdapter;

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
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerViewNumShow = view.findViewById(R.id.rcv_seven_num);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewNumShow.setLayoutManager(manager);
        mNumAdapter = new NumberShowAdapter();
        mNumAdapter.setData(createData());
        mRecyclerViewNumShow.setAdapter(mNumAdapter);

    }

    private List<String> createData() {
        List<String> data = new ArrayList<>();
        data.add("?");
        data.add("?");
        data.add("?");
        data.add("?");
        data.add("?");
        data.add("?");
        data.add("?");
        return data;
    }
}
