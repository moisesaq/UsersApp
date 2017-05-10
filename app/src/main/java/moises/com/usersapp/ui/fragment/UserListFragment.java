package moises.com.usersapp.ui.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import moises.com.usersapp.R;
import moises.com.usersapp.model.User;
import moises.com.usersapp.tools.EndlessRecyclerOnScrollListener;
import moises.com.usersapp.tools.LayoutManagerType;
import moises.com.usersapp.ui.adapter.UserListAdapter;
import moises.com.usersapp.ui.base.BaseFragment;
import moises.com.usersapp.ui.view.LoadingView;

public class UserListFragment extends BaseFragment implements UserListContract.View, UserListAdapter.CallBack{

    private UserListContract.Presenter mPresenter;
    private OnUserListFragmentListener mListener;

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;

    protected LayoutManagerType mCurrentLayoutManagerType;
    private RecyclerView.LayoutManager mLayoutManager;

    private View view;
    @BindView(R.id.loading_view) protected LoadingView mLoadingView;
    @BindView(R.id.gv_users) protected GridView mGridUsers;
    @BindView(R.id.recycler_view) protected RecyclerView mRecyclerView;

    private UserListAdapter mUserListAdapter;
    private DividerItemDecoration dividerItemDecoration;

    private MenuItem menuChangeView;
    private int page = 1;
    private static final int RESULTS = 10;

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
            setupRecyclerView(savedInstanceState);
        }
        return view;
    }

    private void setupRecyclerView(Bundle savedInstanceState){
        mUserListAdapter = new UserListAdapter(getContext(), new ArrayList<User>(), this);
        mLayoutManager = new LinearLayoutManager(getContext());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        if(savedInstanceState != null)
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER);

        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mPresenter.loadUsers(page, RESULTS, "");
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), ((LinearLayoutManager)mLayoutManager).getOrientation());
                mRecyclerView.addItemDecoration(dividerItemDecoration);
                break;
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), isTablet() || isLandScape() || isPhoneLarge() ? 3 : 2);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                if(dividerItemDecoration != null)
                    mRecyclerView.removeItemDecoration(dividerItemDecoration);
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
        mUserListAdapter.setLayoutManagerType(mCurrentLayoutManagerType);
        mRecyclerView.setAdapter(mUserListAdapter);

        if(menuChangeView != null)
            menuChangeView.setIcon(mCurrentLayoutManagerType.getIcon());

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) mLayoutManager, page) {
            @Override
            public void onLoadMore(int currentPage) {
                mPresenter.loadUsers(currentPage, RESULTS, "");
                page = currentPage;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menuChangeView = menu.findItem(R.id.action_change_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_change_view:
                setRecyclerViewLayoutManager(mCurrentLayoutManagerType == LayoutManagerType.LINEAR_LAYOUT_MANAGER ?
                        LayoutManagerType.GRID_LAYOUT_MANAGER : LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading(boolean show) {
        if(show){
            mLoadingView.showLoading(mRecyclerView);
        }else{
            mLoadingView.hideLoading("", mRecyclerView);
        }
    }

    @Override
    public void showUsers(List<User> users) {
        mUserListAdapter.addItems(users);
        if(isAdded() && isTablet() && users != null && users.get(0) != null && page == 1){
            mListener.onLoadCompleted(users.get(0));
        }
    }

    @Override
    public void showEmptyList(String message) {
        mLoadingView.hideLoading(message, mRecyclerView);
    }

    @Override
    public void showError(String error) {
        mLoadingView.hideLoading(error, mRecyclerView);
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

    @Override
    public void onUserClick(User user) {
        if(user != null)
            mListener.onUserClick(user);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }

    public interface OnUserListFragmentListener{
        void onLoadCompleted(User user);
        void onUserClick(User user);
    }
}
