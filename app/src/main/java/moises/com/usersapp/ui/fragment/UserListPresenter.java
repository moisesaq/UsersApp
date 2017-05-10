package moises.com.usersapp.ui.fragment;

import android.util.Log;

import java.util.List;

import moises.com.usersapp.model.Info;
import moises.com.usersapp.model.User;

public class UserListPresenter implements UserListContract.Presenter, UserListInteractor.Callback{

    private final static String TAG = "USER LIST PRESENTER";

    private final UserListContract.View mUserListView;
    private final UserListInteractor mUserListInteractor;

    public UserListPresenter(UserListContract.View userListView, UserListInteractor userListInteractor){
        this.mUserListView = userListView;
        mUserListView.setPresenter(this);
        this.mUserListInteractor = userListInteractor;
    }

    @Override
    public void start() {
        //Verify anything data
    }

    @Override
    public void loadUsers(int page, int results, String seed) {
        if(page == 1)
            mUserListView.showLoading(true);
        //Log.d(TAG, "PAGE -> " + page);
        mUserListInteractor.getUserList(page, results, seed, this);
    }

    @Override
    public void onEmptyList(String message) {
        mUserListView.showLoading(false);
        mUserListView.showEmptyList(message);
    }

    @Override
    public void onError(String error) {
        mUserListView.showLoading(false);
        mUserListView.showError(error);
    }

    @Override
    public void onSuccess(List<User> userList, Info info) {
        mUserListView.showLoading(false);
        mUserListView.showUsers(userList);
    }
}
