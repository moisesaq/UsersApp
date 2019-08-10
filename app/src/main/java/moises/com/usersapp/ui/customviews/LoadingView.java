package moises.com.usersapp.ui.customviews;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import moises.com.usersapp.R;
import moises.com.usersapp.tools.SavedState;

public class LoadingView extends LinearLayout{
    private ProgressBar pbLoading;
    private TextView tvMessage;
    private ImageView ivImage;

    public LoadingView(Context context) {
        super(context);
        setupView();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
    }

    private void setupView(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_loading, this, true);
        ivImage = (ImageView)findViewById(R.id.iv_image);
        pbLoading = (ProgressBar)findViewById(R.id.pb_loading);
        tvMessage = (TextView)findViewById(R.id.tv_message);
    }

    public void showLoading(View hideView){
        tvMessage.setVisibility(View.GONE);
        ivImage.setVisibility(View.GONE);
        pbLoading.setVisibility(View.VISIBLE);
        if(hideView != null)
            hideView.setVisibility(View.VISIBLE);
    }

    public void hideLoading(String message, View view){
        pbLoading.setVisibility(View.GONE);
        ivImage.setVisibility(View.GONE);
        if(!message.isEmpty()){
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.GONE);
        }
    }

    public void hideLoading(String message, View view, int imageId){
        pbLoading.setVisibility(View.GONE);
        ivImage.setVisibility(View.GONE);
        if(!message.isEmpty()){
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
            view.setVisibility(View.GONE);
            if(imageId > 0){
                ivImage.setVisibility(VISIBLE);
                ivImage.setImageResource(imageId);
            }
        }else{
            view.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.GONE);
        }
    }

    /*SAVE STATE OF THE VIEWS*/
    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.childrenStates = new SparseArray();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).saveHierarchyState(ss.childrenStates);
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).restoreHierarchyState(ss.childrenStates);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
        dispatchFreezeSelfOnly(container);
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }
}
