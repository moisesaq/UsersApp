package moises.com.usersapp.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

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
}
