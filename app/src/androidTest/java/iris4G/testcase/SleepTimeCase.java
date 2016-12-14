package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;


import org.hamcrest.Asst;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.Iris4GAction;

/**
 * Created by di.ke on 2016/12/13.
 * 灭屏时间默认设置
 * 设置灭屏时间15S
 * 设置灭屏时间60S
 * 设置灭屏时间600S
 * 设置灭屏时间Never
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class SleepTimeCase extends VP2 {
    Logger logger = Logger.getLogger(SleepTimeCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.makeScreenOn();
        Iris4GAction.startSettings();
        clickByText("Device");
        clickByText("Display");
        clickByText("Sleep");
    }
    //进入sleepTime设置界面
    @Test
    public void SleepDefault() throws Exception{
        checkButton(0);
    }
    @Test
    public void Time15s() throws Exception{
        clickByText("15 seconds");//点击15s按钮
        checkButton(0);//按钮状态验证
        checkSleep(15);//睡眠时间验证
    }
    @Test
    public void Time60s() throws Exception{
        clickByText("60 seconds");
        checkSleep(60);//由于checkButton方法原因，暂时无法对60s开关状态进行准确验证
    }
    @Test
    public void Time600s() throws Exception{
        clickByText("10 minutes");
        checkButton(2);
        checkSleep(600);
    }
    @Test
    public void TimeNever() throws Exception{
        clickByText("Never");
        checkButton(3);
        waitTime(600);
        if (!gDevice.isScreenOn())
        {
            Asst.fail("TimeNever功能失败");
        }
        else
        {
            logger.info("TimeNever功能通过");
        }
        //单独对Never功能进行验证
    }
    @AfterClass
    public static void AfterSleep() throws Exception{
        Iris4GAction.makeScreenOn();
        clickByText("Never");
        VP2.logger.info("重置睡眠时间Never");
    }


    //SleepTimeCase中使用的方法
    public void checkSleep(int i) throws Exception{
        waitTime(i+1);
        if (!gDevice.isScreenOn())
        {
            logger.info(i+"SleepTime功能通过");
        }
        else
        {
            Asst.fail(i+"SleepTime功能失败");
        }
    }
    //SleepTime功能验证
    public void checkButton(int i) throws Exception{
        UiObject SD1 = new UiObject(new UiSelector().resourceId("android:id/list"));
        UiObject SD2 = SD1.getChild(new UiSelector().className("android.widget.LinearLayout").index(i));
        UiObject Button = SD2.getChild(new UiSelector().resourceId("android:id/checkbox"));
        boolean keyofBT = Button.isChecked();
        logger.info("开关状态"+keyofBT);
        if (keyofBT == false)
        {
            Asst.fail("开关尚未打开");
        }
        else
        {
            logger.info("按钮验证通过");
        }
    }
    //SleepTime开关验证

}
