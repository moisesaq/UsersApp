package moises.com.usersapp.ui.fragment;

import android.content.Context;

import java.util.List;

import moises.com.usersapp.R;
import moises.com.usersapp.data.ApiClientAdapter;
import moises.com.usersapp.model.Info;
import moises.com.usersapp.model.User;
import moises.com.usersapp.model.UserList;
import retrofit2.Call;
import retrofit2.Response;

public class UserListInteractor {
    private final Context mContext;

    public UserListInteractor(Context context){
        this.mContext = context;
    }

    public void getUserList(final int page, int results, String seed, final Callback callback){
        Call<UserList> listCall = ApiClientAdapter.getInstance().startConnection().getUserList(page, results, seed);
        listCall.enqueue(new retrofit2.Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if(response.isSuccessful() && response.body().getUsers().size() > 0){
                    callback.onSuccess(response.body().getUsers(), response.body().getInfo());
                }else if(page == 1){
                    callback.onEmptyList(mContext.getString(R.string.not_found_users));
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                if(page == 1)
                    callback.onError("Error " + t.toString());
            }
        });
    }

    interface Callback{
        void onEmptyList(String message);
        void onError(String error);
        void onSuccess(List<User> userList, Info info);
    }
}
