package moises.com.usersapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import moises.com.usersapp.R;
import moises.com.usersapp.model.User;
import moises.com.usersapp.ui.fragment.DetailUserFragment;
import moises.com.usersapp.ui.fragment.UserListFragment;

public class UserListActivity extends AppCompatActivity implements UserListFragment.OnUserListFragmentListener{

    private boolean twoPanels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showListFragment();
    }

    public void showListFragment(){
        if(findViewById(R.id.content_user_detail) != null){
            twoPanels = true;
        }

        FragmentManager fm = getSupportFragmentManager();
        UserListFragment userListFragment = (UserListFragment)fm.findFragmentByTag(UserListFragment.TAG);

        if(userListFragment == null){
            userListFragment = UserListFragment.newInstance();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.content_user_list, userListFragment, UserListFragment.TAG);
            ft.commit();
        }
    }

    public void showDetailFragment(User user){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_user_detail, DetailUserFragment.newInstance(user));
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }

    @Override
    public void onLoadCompleted(User user) {
        if(twoPanels){
            showDetailFragment(user);
        }
    }

    @Override
    public void onUserClick(User user) {
        if(twoPanels){
            showDetailFragment(user);
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable(DetailUserFragment.ARG_PARAM, user);
            Intent intent = new Intent(this, DetailUserActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
