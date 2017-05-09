package moises.com.usersapp.ui.fragment;

import java.util.List;

import moises.com.usersapp.model.User;
import moises.com.usersapp.ui.base.BasePresenter;
import moises.com.usersapp.ui.base.BaseView;

public interface UserListContract {

    interface View extends BaseView<Presenter>{
        void showLoading(boolean show);
        void showUsers(List<User> users);
        void showEmptyList(String message);
        void showError(String error);
    }

    interface Presenter extends BasePresenter{
        void loadUsers(int page, int results, String seed);
    }
}
