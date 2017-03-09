package com.arno.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/*
*   UnScrollListView
*   @author arno
*   create at 2017/3/9 0009 10:51
*/

public class UnScrollListView extends ListView {
    public UnScrollListView(Context context) {
        super(context);
    }

    public UnScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
