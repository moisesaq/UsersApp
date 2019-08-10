package moises.com.usersapp.ui.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import moises.com.usersapp.R;
import moises.com.usersapp.tools.SavedState;
import moises.com.usersapp.tools.Utils;

public class TextImageView extends LinearLayout implements View.OnClickListener{

    private LinearLayout content;
    private ImageView imageView;
    private TextView textView1, textView2, textView3;
    private ImageButton imageButton;

    private OnTextImageViewListener onTextImageViewListener;

    public TextImageView(Context context) {
        super(context);
        setupView();
    }

    public TextImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupView();
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextImageView);

        Drawable background = typedArray.getDrawable(R.styleable.TextImageView_android_background);
        setBackground(background);

        Drawable icon = typedArray.getDrawable(R.styleable.TextImageView_iconImage);
        setIconImage(icon);

        String text1 = typedArray.getString(R.styleable.TextImageView_text1);
        setText1(text1);

        String text2 = typedArray.getString(R.styleable.TextImageView_text2);
        setText2(text2);

        String text3 = typedArray.getString(R.styleable.TextImageView_text3);
        setText3(text3);

        int color = typedArray.getColor(R.styleable.TextImageView_android_textColor, 0);
        setTextColor(color);

        int maxLines = typedArray.getInt(R.styleable.TextImageView_android_maxLines, 1);
        setTextViewMaxLines(maxLines);

        int styleText = typedArray.getInt(R.styleable.TextImageView_android_textStyle, 0);
        setTextViewStyle(styleText);

        Drawable iconButton = typedArray.getDrawable(R.styleable.TextImageView_iconButton);
        setIconButton(iconButton);

        typedArray.recycle();
    }

    private void setupView(){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.view_image_text, this, true);

        content = (LinearLayout)findViewById(R.id.content);
        imageView = (ImageView)findViewById(R.id.imageView);
        textView1 = (TextView)findViewById(R.id.textView1);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        imageButton.setOnClickListener(this);
    }

    public void setOnTextImageViewListener(OnTextImageViewListener listener){
        this.onTextImageViewListener = listener;
    }

    public void setIconImage(Drawable image){
        if(image != null){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageDrawable(image);
        }
    }

    public void setIconImage(int image){
        if(image > 0){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(image);
        }
    }

    public void setIconImagePadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom){
        imageView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    public void setIconImageMargin(int left, int top, int right, int bottom){
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(left, top, right, bottom);
        imageView.setLayoutParams(lp);
    }

    public void setIconImageWith(int with){
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.width = with;
        lp.height = with;
        imageView.setLayoutParams(lp);
    }

    public void setIconImageHeight(int height){
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.height = height;
        imageView.setLayoutParams(lp);
    }

    private void setIconButton(Drawable image){
        if(image != null){
            imageButton.setVisibility(View.VISIBLE);
            imageButton.setImageDrawable(image);
        }
    }

    public void setIconButton(int icon){
        if(icon > 0){
            imageButton.setVisibility(View.VISIBLE);
            imageButton.setImageResource(icon);
        }
    }

    public void setText1(String text){
        if(text != null && !text.isEmpty()){
            textView1.setVisibility(View.VISIBLE);
            textView1.setText(text);
        }else{
            textView1.setVisibility(View.GONE);
        }
    }

    public String getText1(){
        return textView1.getText().toString();
    }

    public void setText2(String text){
        if(text != null && !text.isEmpty()){
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(text);
        }else{
            textView2.setVisibility(View.GONE);
        }
    }

    public String getText2(){
        return textView2.getText().toString();
    }

    public void setText3(String text){
        if(text != null && !text.isEmpty()){
            textView3.setVisibility(View.VISIBLE);
            textView3.setText(text);
        }else{
            textView3.setVisibility(View.GONE);
        }
    }

    public String getText3(){
        return textView3.getText().toString();
    }


    public void setTextColor(int color){
        if(color > 0){
            textView1.setTextColor(Utils.getColor(color));
            textView2.setTextColor(Utils.getColor(color));
            textView3.setTextColor(Utils.getColor(color));
        }
    }

    public void setTextView2TextColor(int color){
        if(color > 0){
            textView2.setTextColor(Utils.getColor(color));
        }
    }
    public void setTextView3TextColor(int color){
        if(color > 0){
            textView3.setTextColor(Utils.getColor(color));
        }
    }

    private void setTextViewMaxLines(int lines){
        textView1.setMaxLines(lines);
    }

    public void setTextViewStyle(int style){
        if(style > 0)
            textView1.setTypeface(null, style);
    }

    @Override
    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(enabled)
            textView1.setTextColor(Utils.getColor(R.color.colorPrimaryDark));
        else
            textView1.setTextColor(Utils.getColor(R.color.colorAccent));
    }

    @Override
    public void onClick(View view) {
        if(onTextImageViewListener != null)
            onTextImageViewListener.onEditClick(getId());
    }

    public interface OnTextImageViewListener {
        void onEditClick(int id);
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
