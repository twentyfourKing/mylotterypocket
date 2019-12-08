package follow.twentyfourking.mylotterypocket.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import follow.twentyfourking.mylotterypocket.R;

public class NumberListAdapter extends RecyclerView.Adapter<NumberListAdapter.SaveViewHolder> {
    private List<List<String>> mData;
    private IAdapterCallback mCallback;
    public NumberListAdapter(IAdapterCallback callback){
        mCallback = callback;
    }

    @NonNull
    @Override
    public SaveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_save_number_item, parent,false);
        return new SaveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveViewHolder holder, int position) {
        List<String> str = mData.get(position);
        holder.tvNum1.setText(str.get(0));
        holder.tvNum2.setText(str.get(1));
        holder.tvNum3.setText(str.get(2));
        holder.tvNum4.setText(str.get(3));
        holder.tvNum5.setText(str.get(4));
        holder.tvNum6.setText(str.get(5));
        holder.tvNum7.setText(str.get(6));
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.remove(position);
                if(mData.size()==0){
                    mCallback.setVisibility();
                }
                notifyDataSetChanged();
            }
        });
        holder.imgBug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存到数据库
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    class SaveViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNum1;
        private TextView tvNum2;
        private TextView tvNum3;
        private TextView tvNum4;
        private TextView tvNum5;
        private TextView tvNum6;
        private TextView tvNum7;
        private ImageView imgDelete;
        private ImageView imgBug;

        public SaveViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNum1 = itemView.findViewById(R.id.tv_number1);
            tvNum2 = itemView.findViewById(R.id.tv_number2);
            tvNum3 = itemView.findViewById(R.id.tv_number3);
            tvNum4 = itemView.findViewById(R.id.tv_number4);
            tvNum5 = itemView.findViewById(R.id.tv_number5);
            tvNum6 = itemView.findViewById(R.id.tv_number6);
            tvNum7 = itemView.findViewById(R.id.tv_number7);
            imgDelete = itemView.findViewById(R.id.img_delete);
            imgBug = itemView.findViewById(R.id.img_bug);
        }
    }

    public void setData(List<List<String>> data){
        mData = data;
        notifyDataSetChanged();
    }

    public interface IAdapterCallback{
        void setVisibility();
    }
}
