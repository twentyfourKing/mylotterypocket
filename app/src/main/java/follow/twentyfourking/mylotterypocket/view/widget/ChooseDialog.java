package follow.twentyfourking.mylotterypocket.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import follow.twentyfourking.mylotterypocket.R;

public class ChooseDialog {
    @BindView(R.id.img_congratulation)
    ImageView mImgNotify;

    private Context mContext;
    private IChooseDialogCallback mCallback;
    private Dialog mDialog;

    public ChooseDialog(Context context, IChooseDialogCallback callback) {
        this.mCallback = callback;
        this.mContext = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.widget_choose_dialog,
                null, false);
        ButterKnife.bind(this, view);
        mDialog = new Dialog(mContext, R.style.dialogStyle);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        mDialog.setContentView(view);

        Window window = mDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setWindowAnimations(R.style.UIAnimBottom);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.gravity = Gravity.CENTER;
        wl.width = ScreenUtils.getScreenWidth() - 200;
        wl.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(wl);

    }

    public void show() {
        mDialog.show();
    }


    @OnClick({
            R.id.tv_no_satisfaction,
            R.id.tv_satisfaction,
            R.id.img_close
    })
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_satisfaction) {
            mCallback.onSatisfaction();
            mDialog.dismiss();
        } else if (id == R.id.tv_no_satisfaction) {
            mDialog.dismiss();
        } else if (id == R.id.img_close) {
            mDialog.dismiss();
        }
    }

    public interface IChooseDialogCallback {
        void onSatisfaction();
    }
}
