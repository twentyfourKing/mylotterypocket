package follow.twentyfourking.mylotterypocket.model.repo;

import follow.twentyfourking.mylotterypocket.model.AppExecutor;
import follow.twentyfourking.mylotterypocket.viewmodel.db.LotteryDatabase;

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
        mDatabase.getDao().queryByTime(start, end, type);
    }
}
