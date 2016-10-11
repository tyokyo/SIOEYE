package testcase;

import android.app.Instrumentation;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.util.Log;

import com.sioeye.MainActivity;
import com.squareup.spoon.Spoon;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.logging.Logger;

/**
 * Created by jikexueyuan on 2015/12/5.
 */
@RunWith(AndroidJUnit4.class)
public class SpoonTest {
    Instrumentation instrumentation;
    UiDevice device;


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp(){
        instrumentation= InstrumentationRegistry.getInstrumentation();
        device= UiDevice.getInstance(instrumentation);
    }

    @Test
    public void test1A(){
        Assert.assertEquals("assertEquals很抱歉,发生崩溃了",true,false);
        device.pressBack();
    }
    @Test
    public void test2B(){
        Spoon.screenshot(mActivityRule.getActivity(),"test2_flag_start");
        device.pressBack();
        device.pressMenu();
        device.pressMenu();
        device.pressMenu();
        Spoon.screenshot(mActivityRule.getActivity(), "test2_flag_start");


        Assert.fail("很抱歉,发生崩溃了");
    }
    @Test
    public void test3C(){
        device.pressBack();
    }
    @Test
    public void test4D(){
        device.pressBack();
    }
    @Test
    public void test5E(){
        device.pressBack();
        device.pressMenu();
        device.pressMenu();
        device.pressMenu();
        Spoon.save(instrumentation.getContext(),Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+ "spoon.txt");
        Logger.getLogger("TEST_LOGGER").info("this is test5E test");
    }
    @Test
    public void test6F(){
        StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
        String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
        String methodName = testClass.getMethodName();
        Logger.getLogger("TEST_LOGGER").info(className+"-"+methodName);

        device.pressBack();
        Logger.getLogger("TEST_LOGGER").info("this is test6F test");
        UiObject2 list=device.findObject(By.clazz("android.widget.ListView"));
        list.scroll(Direction.UP, 0.8f, 5000);
    }
    @Test
    public void test7G(){
        device.pressBack();
    }
    @Test
    public void test8H(){
        device.pressBack();
    }
    @Test
    public void test9I(){
        device.pressBack();
        device.pressMenu();
    }
    @Test
    public void test10J(){
        device.pressBack();
        device.pressMenu();
    }

    public void screenshot(String tag){
        StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
        String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
        String methodName = testClass.getMethodName();
        File dir=new File(Environment.getExternalStorageDirectory()
                +File.separator+"app_spoon-screenshots"+File.separator
                +className+File.separator+methodName);
        Log.v("TEST", dir.getAbsolutePath());
        if(!dir.exists()){
            //dir.delete();
            dir.mkdirs();
        }

        String screenshotName = System.currentTimeMillis() + "_" + tag + ".png";
        File screenshotFile = new File(dir, screenshotName);
        device.takeScreenshot(screenshotFile);
        sleep(2000);

    }



    static StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
        for(int i = trace.length - 1; i >= 0; --i) {
            StackTraceElement element = trace[i];
            if("android.test.InstrumentationTestCase".equals(element.getClassName()) && "runMethod".equals(element.getMethodName())) {
                return trace[i - 3];
            }

            if("org.junit.runners.model.FrameworkMethod$1".equals(element.getClassName()) && "runReflectiveCall".equals(element.getMethodName())) {
                return trace[i - 3];
            }
        }

        throw new IllegalArgumentException("Could not find test class!");
    }
    public void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
