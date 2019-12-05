package follow.twentyfourking.mylotterypocket.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import follow.twentyfourking.mylotterypocket.R;

public class NumberShowAdapter extends RecyclerView.Adapter<NumberShowAdapter.NumberViewHolder> {
    private List<String> mData;

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_number_item, parent, false);
        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.tvNum.setText(mData.get(position));
        if (position == 0) {
            holder.tvNum.setBackgroundResource(R.drawable.red_bg_num);
        } else if (position == 1) {
            holder.tvNum.setBackgroundResource(R.drawable.orange_bg_num);
        } else if (position == 2) {
            holder.tvNum.setBackgroundResource(R.drawable.yellow_bg_num);
        } else if (position == 3) {
            holder.tvNum.setBackgroundResource(R.drawable.green_bg_num);
        } else if (position == 4) {
            holder.tvNum.setBackgroundResource(R.drawable.cyan_bg_num);
        } else if (position == 5) {
            holder.tvNum.setBackgroundResource(R.drawable.blue_bg_num);
        } else if (position == 6) {
            holder.tvNum.setBackgroundResource(R.drawable.violet_bg_num);
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNum;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_num);
        }
    }

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void updateItem(List<String> data, int position) {
        mData = data;
        notifyItemChanged(position);
    }
}
