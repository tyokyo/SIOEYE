package com.sioeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.logging.Logger;

public class AlertReceiver extends BroadcastReceiver {

	private String TAG = BroadcastReceiver.class.getName();
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle=intent.getExtras();
		String message=bundle.getString("message");
		int time = bundle.getInt("time");
		Logger.getLogger(TAG).info(message+"-"+time+" seconds");
		setToastBytTime(context,message,3000);
	}
	/**
	 * 根据自己的时间去定义一个Toast 输入时间为毫秒
	 *
	 * @param c
	 * @param info
	 * @param time
	 */
	public static void setToastBytTime(Context c, String info, int time) {
		final Toast toast = Toast.makeText(c, info, Toast.LENGTH_SHORT);
		toast.show();
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				toast.cancel();
			}
		}, time);
	}
}
