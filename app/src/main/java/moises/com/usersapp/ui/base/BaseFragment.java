package moises.com.usersapp.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import moises.com.usersapp.R;
import moises.com.usersapp.tools.Utils;
import moises.com.usersapp.ui.UsersApp;

public class BaseFragment extends Fragment {
    private Toolbar toolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        toolbar = (Toolbar)getActivity().findViewById(R.id.toolbar);
    }

    protected void setTitle(@NonNull String title){
        if(toolbar != null)
            toolbar.setTitle(title);
    }

    protected boolean isLandScape(){
        int currentOrientation = getResources().getConfiguration().orientation;
        return currentOrientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    protected boolean isTablet(){
        return getActivity().getResources().getBoolean(R.bool.isTablet);
    }

    protected boolean isPhoneLarge(){
        return getActivity().getResources().getBoolean(R.bool.isPhoneLarge);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
    }
}
