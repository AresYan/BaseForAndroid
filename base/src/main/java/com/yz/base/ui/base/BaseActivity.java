package com.yz.base.ui.base;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import butterknife.ButterKnife;
import com.yz.base.R;
import com.yz.base.config.BaseContants;
import com.yz.base.event.BaseEvent;
import com.yz.base.permission.MyPermissionHelper;
import com.yz.base.permission.ZbPermission;
import com.yz.base.utils.AppManager;
import com.yz.base.utils.MyEventBus;
import com.yz.base.utils.MyMainHandler;
import com.yz.base.utils.StatusBarUtil;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    protected FragmentManager mFragmentManager;
    protected BaseDialog mLoadingDialog;
    protected boolean isAct = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        MyEventBus.register(this);
        int orientation = getIntent().getIntExtra(BaseContants.IntentKey.ORIENTATION, 0);
        if (orientation == BaseContants.Orientation.PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (orientation == BaseContants.Orientation.SENSOR_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        StatusBarUtil.setTransparentForImageViewInFragment(this, null);
        mFragmentManager = getSupportFragmentManager();
        mLoadingDialog = initLoading();
        MyPermissionHelper.storage(this, new ZbPermission.ZbPermissionCallback() {
            @Override
            public void permissionSuccess(int requsetCode) {
            }
            @Override
            public void permissionFail(int requestCode) {
                MyPermissionHelper.dialog(BaseActivity.this);
            }
        });
        initView();
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().removeActivity(this);
        MyEventBus.unregister(this);
        destroyLoading();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ZbPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isAct) {
            actRightIn();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (isAct) {
            actLeftIn();
        }
    }

    protected abstract int getContentViewId();

    protected abstract BaseDialog initLoading();

    protected abstract void initView();

    protected void showLoading() {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
                    mLoadingDialog.show();
                }
            }
        });
    }

    protected void dismissLoading() {
        MyMainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingDialog != null) {
                    mLoadingDialog.dismiss();
                }
            }
        });
    }

    private void destroyLoading() {
        dismissLoading();
        mLoadingDialog = null;
    }

    protected void actLeftIn() {
        overridePendingTransition(R.anim.translatexf100to0, R.anim.translatex0to100);
    }

    protected void actRightIn() {
        overridePendingTransition(R.anim.translatex100to0, R.anim.translatex0tof100);
    }

    protected void actBottomIn() {
        overridePendingTransition(R.anim.translatey100to0, R.anim.translatey0to0);
    }

    protected void actBottomOut() {
        overridePendingTransition(R.anim.translatey0to0, R.anim.translatey0to100);
    }

    protected void inputShow(EditText et) {
        if (et != null) {
            InputMethodManager imm = getInputMethodManager();
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    protected void inputHidden(EditText et) {
        if (et != null) {
            InputMethodManager imm = getInputMethodManager();
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    protected boolean isForeground() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && !list.isEmpty()) {
            ComponentName cpn = list.get(0).topActivity;
            if (cpn.getClassName().equals(getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    protected boolean isLand() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private InputMethodManager getInputMethodManager() {
        return (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(BaseEvent event) {
    }

    protected void replace(int layoutRes, BaseFragment fragment, String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(layoutRes, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commitAllowingStateLoss();
    }

    protected void popBackStack(String tag) {
        mFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

}
