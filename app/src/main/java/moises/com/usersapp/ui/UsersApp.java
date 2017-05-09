package moises.com.usersapp.ui;

import android.app.Application;
import android.content.Context;

public class UsersApp extends Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
