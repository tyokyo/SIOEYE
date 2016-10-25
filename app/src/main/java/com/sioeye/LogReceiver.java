package com.sioeye;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by elon on 2016/10/24.
 */
public class LogReceiver extends BroadcastReceiver {
    private String TAG = BroadcastReceiver.class.getName();
    public static boolean isStop=false;
    public static String logAbsPath;
    public static String logName;
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
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        String TAG = bundle.getString("TAG");
        setToastBytTime(context,TAG,3);

        isStop=Boolean.parseBoolean(TAG);
        logName ="log_"+ System.currentTimeMillis();
        //setToastBytTime(context,logName,3);
        String logDir = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"log";
        logAbsPath=logDir+ File.separator+logName+".txt";
        setToastBytTime(context,logAbsPath,5);
        if (!new File(logDir).exists()){
            new File(logDir).mkdirs();
        }
        if (!new File(logAbsPath).exists()){
            try {
                new File(logAbsPath).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> cmdLine=new ArrayList<String>();   //设置命令   logcat -d 读取日志
        cmdLine.add("logcat");
        cmdLine.add("-d");
        ArrayList<String> clearLog=new ArrayList<String>();  //设置命令  logcat -c 清除日志
        clearLog.add("logcat");
        clearLog.add("-c");
        Process process= null;   //捕获日志
        try {
            process = Runtime.getRuntime().exec(cmdLine.toArray(new String[cmdLine.size()]));
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(process.getInputStream()));    //将捕获内容转换为BufferedReader
            // Runtime.runFinalizersOnExit(true);
            String str=null;
            while((str=bufferedReader.readLine())!=null)    //开始读取日志，每次读取一行
            {
                Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));  //清理日志....这里至关重要，不清理的话，任何操作都将产生新的日志，代码进入死循环，直到bufferreader满
                if (!isStop){
                    setToastBytTime(context,"log stop",3000);
                    Logger.getLogger("stop logcat").info(logName);
                    break;
                }else{
                    FileUtil.writeToFile(logAbsPath,str,true);
                }
                //System.out.println(str);    //输出，在logcat中查看效果，也可以是其他操作，比如发送给服务器..
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
