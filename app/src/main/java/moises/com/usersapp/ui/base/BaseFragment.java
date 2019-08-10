package moises.com.usersapp.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import moises.com.usersapp.R;

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
}
