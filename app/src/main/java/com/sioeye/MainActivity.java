package com.sioeye;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sioeye.toast.AccessibilityServiceListener;
import com.sioeye.toast.ToasterService;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    Logger logger = Logger.getLogger(MainActivity.class.getName());
    static final String LOG_TAG = "MainActivity";
    Thread testRunnerThread = null;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    /* private void requestMultiplePermissions() {
         String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,Manifest.permission.READ_EXTERNAL_STORAGE};
         ActivityCompat.requestPermissions(MainActivity.this,permissions,REQUEST_CODE);
     }*/
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                logger.info("Allow");
            } else {
                // Permission Denied
                logger.info("Denied");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }
    /**
     * Checks if the app is set as accessibility
     *
     * @param context current context
     * @return true, if set
     */
    private boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        final String service = BuildConfig.APPLICATION_ID + "/" + ToasterService.class.getName();

        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(),
                    android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ex) {
            Log.e(LOG_TAG, "Error finding setting, default accessibility to not found: " + ex.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String serviceName = mStringColonSplitter.next();
                    if (serviceName.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    //@Override
    protected void onResume() {
        super.onResume();

        if (!isAccessibilitySettingsOn(getApplicationContext())) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.toaster_service_header)
                    .setMessage(R.string.toaster_service_message)
                    .setCancelable(true)
                    .setPositiveButton(android.R.string.ok, new AccessibilityServiceListener(this))
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button launcherButton = (Button) findViewById(R.id.ok);
        launcherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommandExecution commandExecution = new CommandExecution();
                CommandExecution.execCommand("sh /sdcard/run.sh ",false);


              /*  logger.info("click launcherButton");
                Intent intent = new Intent();
                *//* 指定intent要启动的类 *//*
                intent.setClass(MainActivity.this, JUnitActivity.class);
                *//* 启动一个新的Activity *//*
                startActivity(intent);
                *//* 关闭当前的Activity *//*
                MainActivity.this.finish();*/
            }
        });

        /*//开启辅助功能
        Intent intent = new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivityForResult(intent, 0);*/

        /*//启动Toast监控服务
        Intent toastIntent = new Intent(MainActivity.this,ToastInfo.class);
        startService(toastIntent);*/

        //版本大于6.0的情况
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.RECORD_AUDIO};
            //验证是否许可权限
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    logger.info(str);
                    //this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请CAMERA权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},-1);
        }*/
    }
}