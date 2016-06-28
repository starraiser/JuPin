package com.example.administrator.jupin;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * ui相关工具包
 * Created by Administrator on 2016/3/24.
 */
public final class UIUtils {
    private UIUtils(){}

    /**
     * 获得application context
     * @return
     */
    public static Context getAppContext(){
        return App.sContext;
    }

    /**
     *
     * <b>此方法过时，请不要在调用。请使用{@link UIUtils#jump(FragmentManager, Fragment, String, boolean)}<br/></b>
     *<br/>
     * Fragment跳转。保存回退栈
     * @param manager FragmentManager
     * @param destFragment 要跳转的目标fragment
     * @param backStackTag  保存在回退栈中的标签。如果为Null，则不保存到回退栈
     */
//    @Deprecated
//    public static void jump(@NonNull FragmentManager manager, @NonNull Fragment destFragment, String backStackTag){
//
//       jump(manager,destFragment,backStackTag,backStackTag != null);
//    }

//    /**
//     * Fragment跳转。
//     * @param manager FragmentManager
//     * @param destFragment 要跳转的目标fragment
//     * @param backStackTag  保存在回退栈中的标签。设置为null也会被保存到回退栈。只不过标签名为null
//     *  @param saveToBackStack 是否需要保存到回退栈。当调用{@link FragmentManager#popBackStack()}时会返回到最近设置的backStackTag栈处
//     */
//    public static void jump(@NonNull FragmentManager manager, @NonNull Fragment destFragment, String backStackTag, boolean saveToBackStack){
//
//        if( destFragment.isAdded()){
//
//            manager.beginTransaction().remove(destFragment).commitAllowingStateLoss();
//        }
//        final FragmentTransaction tran = manager.beginTransaction();
////       tran.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
////               android.R.anim.slide_in_left
////               , android.R.anim.slide_out_right);
//        tran.replace(R.id.main_content, destFragment, destFragment.getClass().getSimpleName());
//        if (saveToBackStack){
//            tran.addToBackStack(backStackTag);
//        }
//        tran.commit();
//    }
//
//    //带参数传递的跳转
//    public static void jump(@NonNull FragmentManager manager, @NonNull Fragment destFragment, String backStackTag, boolean saveToBackStack,
//                            String title, String content){
//
//        if( destFragment.isAdded()){
//            return;
//            // manager.beginTransaction().remove(destFragment).commitAllowingStateLoss();
//        }
//        final FragmentTransaction tran = manager.beginTransaction();
////       tran.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
////               android.R.anim.slide_in_left
////               , android.R.anim.slide_out_right);
//        Bundle bundle = new Bundle();
//        bundle.putString("title",title);
//        bundle.putString("content",content);
//        destFragment.setArguments(bundle);
//        tran.replace(R.id.main_content, destFragment, destFragment.getClass().getSimpleName());
//        if (saveToBackStack){
//            tran.addToBackStack(backStackTag);
//        }
//        tran.commit();
//    }



    /**
     * 获取textview设置的maxLen
     * @param textView
     * @return
     */
    public static int getMaxLen(TextView textView){
        int num =0;
        //获得最大长度
        final InputFilter[] filters = textView.getFilters();
        for (int i = 0; i < filters.length; i++) {
            final InputFilter filter = filters[i];

            if (filter instanceof InputFilter.LengthFilter) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    num = ((InputFilter.LengthFilter) filter).getMax();
                    break;
                }

                try {
                    final Field field = filter.getClass().getDeclaredField("mMax");
                    field.setAccessible(true);
                    num = field.getInt(filter);
                    break;
                } catch (NoSuchFieldException e) {

                } catch (IllegalAccessException e) {

                }
            }
        }
        return num;
    }

    /**
     * LayoutInflater布局
     * @param layId
     * @return
     */
    public static View inflate(int layId){
        return LayoutInflater.from(getAppContext()).inflate(layId,null);
    }

    /**
     * 关闭输入法键盘
     *
     * @param editText
     */
    public static void closeInputMethod(View editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    /**
     * 打开输入法键盘
     *
     * @param editText
     */
    public static void openInputMethod(View editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInputFromInputMethod(editText.getWindowToken(),0);
    }
}
