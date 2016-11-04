package testcase.usa.me;

import android.os.Environment;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import ckt.base.VP2;
import page.App;

/**
 * Created by elon on 2016/10/14.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestDemo extends VP2{
    public String takeBugReport(String crashType, String currentTime)
            throws IOException {
        Logger.getLogger("TestDemo").info("开始抓取崩溃日志");
        try {
            // Executes the command.
            String dic= "/sdcard/CktTest/" + "screen";
            File dicFile = new File(dic);
            if (!dicFile.exists()){
                new File(dic).mkdirs();
            }
            String logName = dic+ "/Crash_"
                    + crashType + "_" + currentTime + ".txt";
            File file = new File(logName);
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file, true);
            Process process = Runtime.getRuntime()
                    .exec("/system/bin/bugreport");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            while ((read = reader.read(buffer)) > 0) {
                StringBuffer output = new StringBuffer();
                output.append(buffer, 0, read);
                out.write(output.toString().getBytes("utf-8"));
            }
            // Waits for the command to finish.
            process.waitFor();
            reader.close();
            out.close();
            Logger.getLogger("TestDemo").info("崩溃日志文件抓取成功-"+logName);
            return logName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void writeFileToSD() {
        String sdStatus = Environment.getExternalStorageState();
        if(!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Log.d("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }
        try {
            String pathName="/sdcard/log1/";
            String fileName="file.txt";
            File path = new File(pathName);
            File file = new File(pathName + fileName);
            if( !path.exists()) {
                Log.d("TestFile", "Create the path:" + pathName);
                path.mkdir();
            }
            if( !file.exists()) {
                Log.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file);
            String s = "this is a test string writing to file.";
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.close();

        } catch(Exception e) {
            Log.e("TestFile", e.getMessage());
            e.printStackTrace();
        }
    }

    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
    }
    @Test
    @LargeTest
    public void testA() throws IOException, InterruptedException, UiObjectNotFoundException {
        //takeBugReport("ct",System.currentTimeMillis()+"");
        //writeFileToSD();
        /*initDevice();

        Common.writeToFile("/mnt/sdcard/log.txt",System.currentTimeMillis()+"ssssssssssssssss",true);
        //gDevice.executeShellCommand("echo \"=============================\" >>"+"/mnt/sdcard/log.txt");
        logStart();
        waitTime(120);
        logStop();*/
        Spoon.screenshot("tag","Spoon.screenshot",getUiObjectByText("我"));
       // clickByText("CKT");
        //clickById(Me.ID_CAMERA_SELECT);
        Spoon.screenshot("tag","绘制文字到图片上绘制文字到图片上绘制文字到图片上绘制文字到图片上绘制文字到图片上绘制文字到图片上绘制文字到图片上绘制文字到图片上绘制文字到图片上");
        //Asst.assertTrue("验证点描述信息",false);

        initDevice();
        //Common.writeToFile("/mnt/sdcard/log/11.txt","test",true);
        //waitTime(5);
        //makeToast("make toast test",10);
        //Spoon.screenshot(gDevice,"test_screenshot");
        //clickByText("Me");
        //Spoon.screenshot("Me");
        //Asst.assertThat("Spoon.screenshot", is(equalTo("")));

    }
}



