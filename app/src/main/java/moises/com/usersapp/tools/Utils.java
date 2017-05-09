package moises.com.usersapp.tools;

import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import moises.com.usersapp.ui.UsersApp;

public class Utils {

    public static int getColor(int id){
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(UsersApp.getContext(), id);
        } else {
            return UsersApp.getContext().getResources().getColor(id);
        }
    }

    public static void showToastMessage(int messageId){
        Toast.makeText(UsersApp.getContext(), UsersApp.getContext().getString(messageId), Toast.LENGTH_SHORT).show();
    }

    public static void showToastMessage(String message){
        Toast.makeText(UsersApp.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
