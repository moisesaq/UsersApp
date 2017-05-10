package moises.com.usersapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import moises.com.usersapp.R;
import moises.com.usersapp.model.User;
import moises.com.usersapp.ui.fragment.DetailUserFragment;

public class DetailUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        if(!getResources().getBoolean(R.bool.isTablet)){
            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null)
                actionBar.setDisplayHomeAsUpEnabled(true);
        }

        User user = (User)getIntent().getExtras().getSerializable(DetailUserFragment.ARG_PARAM);
        if(user != null)
            showFragment(DetailUserFragment.newInstance(user), false);
    }

    public void showFragment(Fragment fragment, boolean stack){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if(stack)
            ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.replace(R.id.content_user_detail, fragment);
        ft.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
