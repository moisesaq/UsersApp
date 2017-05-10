package moises.com.usersapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import moises.com.usersapp.R;
import moises.com.usersapp.model.User;

public class UserGridAdapter extends BaseAdapter{

    private Context mContext;
    private List<User> users;

    public UserGridAdapter(Context context, List<User> userList){
        this.mContext = context;
        this.users = userList;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public User getItem(int i) {
        return users.get(i);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    public void addItems(List<User> userList){
        users.addAll(userList);
        notifyDataSetChanged();
    }

    public void addItems(User user){
        users.add(user);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.user_grid_item, viewGroup, false);
        }

        User user = getItem(i);
        if(user.getPicture() != null && user.getPicture().getLarge() != null){
            ImageView mImageView = (ImageView)view.findViewById(R.id.image_user);
            Picasso.with(mContext)
                    .load(user.getPicture().getLarge())
                    .placeholder(R.mipmap.default_profile)
                    .error(R.mipmap.default_profile)
                    .into(mImageView);
        }
        if(user.getName() != null)
            ((TextView)view.findViewById(R.id.name_user)).setText(user.getName().getFirst());
        return view;
    }
}
