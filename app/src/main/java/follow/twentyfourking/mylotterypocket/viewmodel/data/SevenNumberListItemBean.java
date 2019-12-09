package follow.twentyfourking.mylotterypocket.viewmodel.data;

import java.util.List;

public class SevenNumberListItemBean {
    private List<String> mNumberData;
    private boolean chooseState;

    public List<String> getmNumberData() {
        return mNumberData;
    }

    public void setmNumberData(List<String> mNumberData) {
        this.mNumberData = mNumberData;
    }

    public boolean isChooseState() {
        return chooseState;
    }

    public void setChooseState(boolean chooseState) {
        this.chooseState = chooseState;
    }
}
