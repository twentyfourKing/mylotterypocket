package follow.twentyfourking.mylotterypocket.model.repo;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import follow.twentyfourking.mylotterypocket.model.AppExecutor;
import follow.twentyfourking.mylotterypocket.viewmodel.db.LotteryDatabase;
import follow.twentyfourking.mylotterypocket.viewmodel.db.LotteryEntity;
import follow.twentyfourking.mylotterypocket.viewmodel.viewmodel.MainViewModel;

public class MainRepository<T> {

    private T mViewModel;
    private AppExecutor mExecutor;
    private LotteryDatabase mDatabase;

    public MainRepository(T viewModel, AppExecutor executor, LotteryDatabase database) {
        this.mViewModel = viewModel;
        this.mExecutor = executor;
        this.mDatabase = database;
    }

    public void getLotteryByTime(long start, long end, String type) {
        mExecutor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<LotteryEntity> data = mDatabase.getDao().queryByTime(start, end, type);
                if (mViewModel instanceof MainViewModel) {
                    ((MainViewModel) mViewModel).getLotteryDataLive().postValue(data);
                }
            }
        });
    }

    public void getLotteryAll() {
        mExecutor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<LotteryEntity> data = mDatabase.getDao().queryAll();
                if (mViewModel instanceof MainViewModel) {
                    ((MainViewModel) mViewModel).getLotteryDataLiveAll().postValue(data);
                }
            }
        });
    }

    public void saveLotteryNumber(LotteryEntity entity) {
        mExecutor.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDatabase.getDao().insertLotteryNumber(entity);
            }
        });
    }
}
