package follow.twentyfourking.mylotterypocket.model.repo;

public class MainRepository<T> {

    private T mViewModel;

    public MainRepository(T viewModel) {
        mViewModel = viewModel;
    }
}
