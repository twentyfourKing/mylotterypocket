package follow.twentyfourking.mylotterypocket.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import follow.twentyfourking.mylotterypocket.R;
import follow.twentyfourking.mylotterypocket.viewmodel.db.LotteryEntity;

public class HistoryAdapter extends RecyclerView.Adapter {

    private List<LotteryEntity> mData;
    private static int TYPE_QI = 0;
    private static int TYPE_SHUANG = 1;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_QI) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_history_qi_item, null);
            return new HistoryQiViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_history_shuang_item, null);
            return new HistoryShuangViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getType().equals("qixingcai")) {
            return TYPE_QI;
        } else {
            return TYPE_SHUANG;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HistoryQiViewHolder) {
            ((HistoryQiViewHolder) holder).number1.setText(String.valueOf(mData.get(position).getNumber().charAt(0)));
            ((HistoryQiViewHolder) holder).number2.setText(String.valueOf(mData.get(position).getNumber().charAt(1)));
            ((HistoryQiViewHolder) holder).number3.setText(String.valueOf(mData.get(position).getNumber().charAt(2)));
            ((HistoryQiViewHolder) holder).number4.setText(String.valueOf(mData.get(position).getNumber().charAt(3)));
            ((HistoryQiViewHolder) holder).number5.setText(String.valueOf(mData.get(position).getNumber().charAt(4)));
            ((HistoryQiViewHolder) holder).number6.setText(String.valueOf(mData.get(position).getNumber().charAt(5)));
            ((HistoryQiViewHolder) holder).number7.setText(String.valueOf(mData.get(position).getNumber().charAt(6)));
            ((HistoryQiViewHolder) holder).tag.setText(getTimeStr(mData.get(position).getTime()));
        } else if (holder instanceof HistoryShuangViewHolder) {
            ((HistoryShuangViewHolder) holder).number1.setText(String.valueOf(mData.get(position).getNumber().charAt(0)));
            ((HistoryShuangViewHolder) holder).number2.setText(String.valueOf(mData.get(position).getNumber().charAt(1)));
            ((HistoryShuangViewHolder) holder).number3.setText(String.valueOf(mData.get(position).getNumber().charAt(2)));
            ((HistoryShuangViewHolder) holder).number4.setText(String.valueOf(mData.get(position).getNumber().charAt(3)));
            ((HistoryShuangViewHolder) holder).number5.setText(String.valueOf(mData.get(position).getNumber().charAt(4)));
            ((HistoryShuangViewHolder) holder).number6.setText(String.valueOf(mData.get(position).getNumber().charAt(5)));
            ((HistoryShuangViewHolder) holder).number7.setText(String.valueOf(mData.get(position).getNumber().charAt(6)));
            ((HistoryShuangViewHolder) holder).tag.setText(getTimeStr(mData.get(position).getTime()));
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    class HistoryQiViewHolder extends RecyclerView.ViewHolder {
        public TextView number1;
        public TextView number2;
        public TextView number3;
        public TextView number4;
        public TextView number5;
        public TextView number6;
        public TextView number7;
        private TextView tag;

        public HistoryQiViewHolder(@NonNull View itemView) {
            super(itemView);
            number1 = itemView.findViewById(R.id.tv_number1);
            number2 = itemView.findViewById(R.id.tv_number2);
            number3 = itemView.findViewById(R.id.tv_number3);
            number4 = itemView.findViewById(R.id.tv_number4);
            number5 = itemView.findViewById(R.id.tv_number5);
            number6 = itemView.findViewById(R.id.tv_number6);
            number7 = itemView.findViewById(R.id.tv_number7);
            tag = itemView.findViewById(R.id.tv_time_tag);
        }
    }

    class HistoryShuangViewHolder extends RecyclerView.ViewHolder {
        public TextView number1;
        public TextView number2;
        public TextView number3;
        public TextView number4;
        public TextView number5;
        public TextView number6;
        public TextView number7;
        public TextView tag;

        public HistoryShuangViewHolder(@NonNull View itemView) {
            super(itemView);
            number1 = itemView.findViewById(R.id.tv_number1);
            number2 = itemView.findViewById(R.id.tv_number2);
            number3 = itemView.findViewById(R.id.tv_number3);
            number4 = itemView.findViewById(R.id.tv_number4);
            number5 = itemView.findViewById(R.id.tv_number5);
            number6 = itemView.findViewById(R.id.tv_number6);
            number7 = itemView.findViewById(R.id.tv_number7);
            tag = itemView.findViewById(R.id.tv_time_tag);
        }
    }

    public void setData(List<LotteryEntity> data) {
        mData = data;
        notifyDataSetChanged();
    }


    private String getTimeStr(long time) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(time);
    }
}
