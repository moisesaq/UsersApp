package moises.com.usersapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import moises.com.usersapp.R;
import moises.com.usersapp.model.User;
import moises.com.usersapp.tools.LayoutManagerType;
import moises.com.usersapp.ui.fragment.UserListFragment;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    private Context mContext;
    private List<User> users;
    private CallBack mCallBack;
    private LayoutManagerType layoutManagerType;

    public UserListAdapter(Context context, List<User> userList, CallBack callBack){
        this.layoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        this.mContext = context;
        this.users = userList;
        mCallBack = callBack;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (layoutManagerType){
            case LINEAR_LAYOUT_MANAGER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
                break;
            case GRID_LAYOUT_MANAGER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_grid_item, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        }
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);
        Picasso.with(mContext)
                .load(user.getPicture().getLarge())
                .placeholder(R.mipmap.default_profile)
                .error(R.mipmap.default_profile)
                .into(holder.mImage);
        holder.mTitle.setText(user.getName().getFirst());
    }

    @Override
    public int getItemCount() {
        if(users != null)
            return users.size();
        return 0;
    }

    public void addItems(List<User> userList){
        users.addAll(userList);
        notifyDataSetChanged();
    }

    public void setLayoutManagerType(LayoutManagerType layoutManagerType){
        this.layoutManagerType = layoutManagerType;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.image_user) RoundedImageView mImage;
        @BindView(R.id.name_user) TextView mTitle;

        public UserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mCallBack != null)
                mCallBack.onUserClick(users.get(getAdapterPosition()));
        }
    }

    public interface CallBack{
        void onUserClick(User user);
    }
}
