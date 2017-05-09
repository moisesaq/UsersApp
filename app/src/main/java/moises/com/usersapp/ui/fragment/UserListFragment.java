package moises.com.usersapp.ui.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.Utils;
import moises.com.usersapp.R;
import moises.com.usersapp.model.User;
import moises.com.usersapp.ui.adapter.UserGridAdapter;
import moises.com.usersapp.ui.base.BaseFragment;
import moises.com.usersapp.ui.view.LoadingView;

public class UserListFragment extends BaseFragment implements AdapterView.OnItemClickListener, UserListContract.View{

    private UserListContract.Presenter mPresenter;
    private OnUserListFragmentListener mListener;

    private View view;
    @BindView(R.id.loading_view) protected LoadingView mLoadingView;
    @BindView(R.id.gv_users) protected GridView mGridUsers;

    private UserGridAdapter mUserGridAdapter;

    public UserListFragment() {
    }

    public static UserListFragment newInstance(){
        return new UserListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserListInteractor mInteractor = new UserListInteractor(getContext());
        new UserListPresenter(this, mInteractor);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_user_list, container, false);
            ButterKnife.bind(this, view);
            setupGrid();
        }
        return view;
    }

    private void setupGrid(){
        setTitle("User list");
        mUserGridAdapter = new UserGridAdapter(getContext(), new ArrayList<User>());
        mGridUsers.setAdapter(mUserGridAdapter);
        mPresenter.loadUsers(1, 10, "");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        User user = (User)adapterView.getAdapter().getItem(i);
        if(user != null)
            mListener.onUserClick(user);
    }

    @Override
    public void showLoading(boolean show) {
        if(show){
            mLoadingView.showLoading(mGridUsers);
        }else{
            mLoadingView.hideLoading("", mGridUsers);
        }
    }

    @Override
    public void showUsers(List<User> users) {
        mUserGridAdapter.addItems(users);
    }

    @Override
    public void showEmptyList(String message) {
        mLoadingView.hideLoading(message, mGridUsers);
    }

    @Override
    public void showError(String error) {
        mLoadingView.hideLoading(error, mGridUsers);
    }

    @Override
    public void setPresenter(UserListContract.Presenter presenter) {
        if(presenter != null){
            mPresenter = presenter;
        }else {
            throw new RuntimeException("Presenter can not to be null");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnUserListFragmentListener){
            mListener = (OnUserListFragmentListener)context;
        }else {
            throw new RuntimeException(context.toString() + "must implement OnUserListFragmentListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mListener = null;
    }

    public interface OnUserListFragmentListener{
        void onUserClick(User user);
    }
}
