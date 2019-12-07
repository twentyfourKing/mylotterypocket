package follow.twentyfourking.mylotterypocket.view.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import follow.twentyfourking.mylotterypocket.R;
import follow.twentyfourking.mylotterypocket.view.delegate.MainActivityDelegate;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_container)
    RelativeLayout mParent;


    private MainActivityDelegate mDelegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mDelegate = new MainActivityDelegate();
        mDelegate.setDelegateInit(this, mParent);



    }
}
