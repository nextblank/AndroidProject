package com.nextblank.sdk.tools.app;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.nextblank.sdk.tools.Tools;

/**
 * 软键盘工具类
 */
public final class InputMethodTool {

    private static final Object lock = new Object();
    private static volatile InputMethodTool instance;

    private InputMethodTool() {
    }

    public static InputMethodTool instance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new InputMethodTool();
                }
            }
        }
        return instance;
    }

    /**
     * 显示输入法
     *
     * @param context
     * @param view
     */
    public void showInputSoftFromWindowMethod(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断输入法是否处于激活状态
     *
     * @param context
     * @return
     */
    public boolean isActiveSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }

    /**
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    public void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 动态隐藏软键盘
     *
     * @param context 上下文
     * @param view    视图
     */
    public void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 动态显示软键盘
     *
     * @param edit 输入框
     */
    public void showSoftInput(EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) Tools.app().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(edit, 0);
    }

    /**
     * 切换键盘显示与否状态
     */
    public void toggleSoftInput() {
        InputMethodManager imm = (InputMethodManager) Tools.app().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
