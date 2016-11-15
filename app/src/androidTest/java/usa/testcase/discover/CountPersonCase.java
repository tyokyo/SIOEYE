package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.RelativeLayout;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.logging.Logger;
import ckt.base.VP;
import ckt.base.VP2;
import usa.action.DiscoverAction;
import usa.page.App;
import usa.page.Discover;
import usa.testcase.me.ActivityCase;

import static usa.action.DiscoverAction.getPersonNumber;

/**
 * Created by caibing.yin on 2016/11/7.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CountPersonCase extends VP2 {
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /**
     * case10:
     *历史观看人数统计
     *1.反复进入退出视频播放界面后回到封面检查历史观看人数
     *  Result：每次进入播放视频界面后退出视频封面界面，历史观看人数加1
     * */
    @Test
    public void testCountPerson() throws UiObjectNotFoundException {
        String ClickBeforeNumber =getPersonNumber();
        UiObject2 u1=gDevice.findObject(By.res("com.sioeye.sioeyeapp:id/swipe_target"));
        List<UiObject2> views=u1.findObjects(By.clazz(android.view.View.class));
        UiObject2 u3=views.get(5);
        logger.info("点击观看前的人数是"+ClickBeforeNumber+"人");
        Spoon.screenshot(gDevice,ClickBeforeNumber);
        u3.click();
        waitTime(10);
        gDevice.pressBack();
        waitTime(8);
        String ClickAfterNumber =getPersonNumber();
        logger.info("点击观看后的人数"+ClickAfterNumber+"人");
        Spoon.screenshot(gDevice,ClickAfterNumber);
        Boolean Result = (Integer.parseInt(ClickAfterNumber)==(Integer.parseInt(ClickBeforeNumber))+1);
        if(!Result){
            Spoon.screenshot(gDevice,"CountPersonCase_was_fail");
            logger.info("点击观看前后人数不一致");
            Assert.fail("CountPersonCase_was_fail");
        }
    }
}
