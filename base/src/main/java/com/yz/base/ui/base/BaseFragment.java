package com.yz.base.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.yz.base.R;
import com.yz.base.event.BaseEvent;
import com.yz.base.utils.MyEventBus;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;
    protected FragmentManager mFragmentManager;
    protected Bundle mBundle;
    protected Unbinder mUnbinder;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity=(BaseActivity) activity;
        mFragmentManager=getChildFragmentManager();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle=getArguments();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(getContentViewId(),container,false);
        mUnbinder=ButterKnife.bind(this,view);
        initView(view, savedInstanceState);
        MyEventBus.register(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        MyEventBus.unregister(this);
    }

    protected abstract int getContentViewId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    protected void showLoading(){
        mActivity.showLoading();
    }

    protected void dismissLoading(){
        mActivity.dismissLoading();
    }

    protected void add(int layoutRes, BaseFragment fragment, String tag){
        add(layoutRes,fragment,tag,false);
    }

    protected void add(int layoutRes, BaseFragment fragment, String tag, boolean isAnim){
        FragmentTransaction transaction=mFragmentManager.beginTransaction();
        if(isAnim){
            transaction.setCustomAnimations(R.anim.translatex100to0, R.anim.translatex0tof100,
                    R.anim.translatexf100to0, R.anim.translatex0to100);
        }
        transaction.add(layoutRes,fragment,tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    protected void replace(int layoutRes, BaseFragment fragment, String tag){
        replace(layoutRes,fragment,tag,false);
    }

    protected void replace(int layoutRes, BaseFragment fragment, String tag, boolean isAnim){
        FragmentTransaction transaction=mFragmentManager.beginTransaction();
        if(isAnim){
            transaction.setCustomAnimations(R.anim.translatex100to0, R.anim.translatex0tof100,
                    R.anim.translatexf100to0, R.anim.translatex0to100);
        }
        transaction.replace(layoutRes,fragment,tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    protected void popBackStack(){
        mFragmentManager.popBackStack();
    }

    protected void popBackStack(String tag){
        mFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    protected boolean isLand(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(BaseEvent event) {
    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        try {
//            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
//            childFragmentManager.setAccessible(true);
//            childFragmentManager.set(this, null);
//        } catch (NoSuchFieldException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
