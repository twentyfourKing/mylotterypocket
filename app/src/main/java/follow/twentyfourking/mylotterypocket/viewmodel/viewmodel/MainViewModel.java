package follow.twentyfourking.mylotterypocket.viewmodel.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import follow.twentyfourking.mylotterypocket.viewmodel.db.LotteryEntity;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<LotteryEntity>> lotteryDataLive = new MutableLiveData<>();
    private MutableLiveData<List<LotteryEntity>> lotteryDataLiveAll = new MutableLiveData<>();

    public MutableLiveData<List<LotteryEntity>> getLotteryDataLiveAll() {
        return lotteryDataLiveAll;
    }

    public MutableLiveData<List<LotteryEntity>> getLotteryDataLive() {
        return lotteryDataLive;
    }

}
