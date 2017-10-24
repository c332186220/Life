package com.cxl.life.app.function.permission;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.cxl.life.R;
import com.cxl.life.app.BaseTitleActivity;
import com.cxl.life.util.AUtil;
import com.cxl.life.util.ToastUtil;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * 权限管理
 * compile 'com.yanzhenjie:permission:1.1.2'
 */
public class PermissionActivity extends BaseTitleActivity implements View.OnClickListener {
    private Button grant1;//授权

    private static final int REQUEST_CODE_PERMISSION1 = 101;
    private static final int REQUEST_CODE_PERMISSION2 = 102;
    private static final int REQUEST_CODE_PERMISSION3 = 103;

    private PermissionRequest permissionRequest;//任意位置授权

    private static final int REQUEST_CODE_SETTING = 300;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_permission);
    }

    @Override
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar(toolbar, "权限管理", true);

        grant1 = (Button) findViewById(R.id.permission_grant1);//日历
        grant1.setOnClickListener(this);
        findViewById(R.id.permission_grant2).setOnClickListener(this);//麦克风 存储卡
        findViewById(R.id.permission_grant3).setOnClickListener(this);//手机状态 定位
        findViewById(R.id.permission_grant4).setOnClickListener(this);//传感器
        findViewById(R.id.permission_grant5).setOnClickListener(this);//检查相机权限
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.permission_grant1://单个授权
                grantPermission(1);
                break;
            case R.id.permission_grant2://分组授权
                grantPermission(2);
                break;
            case R.id.permission_grant3://任意位置授权
                grantPermission(3);
                break;
            case R.id.permission_grant4://重复请求权限时，弹出说明框
                grantPermission(4);
                break;
            case R.id.permission_grant5://检查相机权限有没有
                if (AUtil.lacksPermissions(this, Permission.CAMERA)) {
                    ToastUtil.showToast("缺少相机权限");
                } else {
                    ToastUtil.showToast("拥有相机权限");
                }
                break;
        }
    }

    /**
     * 权限申请
     * 其它权限 CAMERA相机 CONTACTS联系人 SMS短信
     *
     * @param type
     */
    private void grantPermission(int type) {
        switch (type) {
            case 1:
                // 申请单个权限。
                AndPermission.with(this)
                        .requestCode(REQUEST_CODE_PERMISSION1)
                        .permission(Permission.CALENDAR)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(PermissionActivity.this, rationale).show();
                            }
                        })
                        .start();
                break;
            case 2:
                // 申请多个权限。
                AndPermission.with(this)
                        .requestCode(REQUEST_CODE_PERMISSION2)
                        .permission(Permission.MICROPHONE, Permission.STORAGE)
                        .callback(this)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(PermissionActivity.this, rationale).show();
                            }
                        })
                        .start();
                break;
            case 3:
                if (permissionRequest == null) {
                    permissionRequest = new PermissionRequest(this, new PermissionRequest.PermissionCallback() {
                        @Override
                        public void onSuccessful() {
                            ToastUtil.showToast(R.string.permission_successfully);
                        }

                        @Override
                        public void onFailure() {
                            ToastUtil.showToast(R.string.permission_successfully);
                        }
                    });
                }
                permissionRequest.request();
                break;
            case 4:
                // 申请权限。
                AndPermission.with(PermissionActivity.this)
                        .requestCode(REQUEST_CODE_PERMISSION3)
                        .permission(Permission.SENSORS)
                        .callback(permissionListener)
                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                        // 这样避免用户勾选不再提示，导致以后无法申请权限。
                        // 你也可以不设置。
                        .rationale(rationaleListener)
                        .start();
                break;
        }
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION1:
                    // TODO do something.
                    ToastUtil.showToast(R.string.permission_successfully);
                    break;
                case REQUEST_CODE_PERMISSION2:
                    ToastUtil.showToast(R.string.permission_successfully);
                    break;
                case REQUEST_CODE_PERMISSION3:
                    ToastUtil.showToast(R.string.permission_successfully);
                    break;
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION1:
                    // TODO The strategy after failure.
                    ToastUtil.showToast(R.string.permission_failure);
                    break;
                case REQUEST_CODE_PERMISSION2:
                    ToastUtil.showToast(R.string.permission_failure);
                    break;
                case REQUEST_CODE_PERMISSION3:
                    ToastUtil.showToast(R.string.permission_failure);
                    break;
            }
            // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
            if (AndPermission.hasAlwaysDeniedPermission(PermissionActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(PermissionActivity.this, REQUEST_CODE_SETTING).show();

                // 第二种：用自定义的提示语。
//             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
//                     .setTitle("权限申请失败")
//                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
//                     .setPositiveButton("好，去设置")
//                     .show();

//            第三种：自定义dialog样式。
//            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
//            你的dialog点击了确定调用：
//            settingService.execute();
//            你的dialog点击了取消调用：
//            settingService.cancel();
            }
        }
    };

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();

            // 自定义对话框。
            AlertDialog.newBuilder(PermissionActivity.this)
                    .setTitle(R.string.title_dialog)
                    .setMessage("我们需要的一些必要权限被禁止，请授权给我们。")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("就不", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SETTING: {
                ToastUtil.showToast("设置返回");
                break;
            }
        }
    }
}
