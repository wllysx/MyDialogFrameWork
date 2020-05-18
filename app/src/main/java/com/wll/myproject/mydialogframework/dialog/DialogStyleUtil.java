package com.wll.myproject.mydialogframework.dialog;
/*
    Create by WLL on 2020/5/11 DATA: 11:45
*/

import androidx.annotation.StyleRes;

import com.wll.myproject.mydialogframework.R;

public class DialogStyleUtil {
    private static int mStyle = R.style.EasyDialogStyle;
    private static int mListStyle = R.style.EasyListDialogStyle;
    public static void initStyle(@StyleRes int style) {
        mStyle = style;
    }

    public static void initListStyle(@StyleRes int style) {
        mListStyle = style;
    }

    public static int getStyle() {
        return mStyle;
    }

    public static int getListStyle() {
        return mListStyle;
    }
}
