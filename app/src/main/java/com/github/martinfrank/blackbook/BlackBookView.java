package com.github.martinfrank.blackbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;
import androidx.annotation.Nullable;


public class BlackBookView extends WebView {

    public BlackBookView(Context context) {
        super(context);
        init();
    }

    public BlackBookView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlackBookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BlackBookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }


}
