package moises.com.usersapp.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import moises.com.usersapp.R;
import moises.com.usersapp.model.User;
import moises.com.usersapp.ui.customviews.TextImageView;

public class DetailUserFragment extends Fragment {
    public static final String TAG = DetailUserFragment.class.getSimpleName();
    public static final String ARG_PARAM = "user";
    private User user;

    protected @BindView(R.id.iv_user) ImageView mImageView;
    protected @BindView(R.id.tiv_user_name) TextImageView tivUserName;
    protected @BindView(R.id.tiv_name) TextImageView tivName;
    protected @BindView(R.id.tiv_last_name) TextImageView tivLastName;
    protected @BindView(R.id.tiv_email) TextImageView tivEmail;

    public static DetailUserFragment newInstance(@NonNull User user){
        DetailUserFragment detailPetFragment = new DetailUserFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PARAM, user);
        detailPetFragment.setArguments(bundle);
        return detailPetFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null)
            user = (User) getArguments().getSerializable(ARG_PARAM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_user, container, false);
        ButterKnife.bind(this, view);
        showDescription();
        return view;
    }

    private void showDescription(){
        try{
            if(user == null)
                return;
            Picasso.with(getContext())
                    .load(user.getPicture().getLarge())
                    .placeholder(R.mipmap.default_profile)
                    .error(R.mipmap.default_profile)
                    .into(mImageView);
            tivUserName.setText1(user.getLogin().getUserName());
            tivName.setText1(user.getName().getFirst());
            tivLastName.setText1(user.getName().getLast());
            tivEmail.setText1(user.getEmail());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
