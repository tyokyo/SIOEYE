package usa.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.page.App;

/**
 * Created by elon on 2016/10/14.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class demo extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
    }
    @Test
    public void testD1() throws UiObjectNotFoundException {
        clickByText("text");
        getObjectById("id");
        clickById("id");
        Spoon.screenshot("picture_name","这里的文字会绘制文字到图片上");
        Spoon.save(context,"your file name");
    }
}

