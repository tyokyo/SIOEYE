package com.sioeye;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.os.Parcelable;
import android.view.accessibility.AccessibilityEvent;

public class ToastInfo extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // TODO Auto-generated method stub
        // System.out.println("Enter->onAccessibilityEvent");
        //判断是否是通知事件
        if (event.getEventType() != AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED)
            return;
        //获取消息来源
        String sourcePackageName = (String) event.getPackageName();
        //获取事件具体信息
        Parcelable parcelable = event.getParcelableData();
        //如果是下拉通知栏消息
        if (parcelable instanceof Notification) {
        } else {
            //其它通知信息，包括Toast
            String toastMsg = (String) event.getText().get(0);
            String log = "Latest Toast Message: " + toastMsg + " [Source: " + sourcePackageName + "]";
            System.out.println(log);
        }
    }
    @Override
    public void onInterrupt() {
        // TODO Auto-generated method stub
    }
}