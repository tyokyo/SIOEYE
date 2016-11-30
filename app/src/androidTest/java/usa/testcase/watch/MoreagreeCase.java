package usa.testcase.watch;
import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.logging.Logger;
import ckt.base.VP2;
import usa.action.Nav;
import usa.action.WatchAction;
import usa.page.App;
import usa.testcase.me.HelpCase;




/**
 * Created by admin on 2016/10/28.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class MoreagreeCase extends VP2 {
    Logger logger = Logger.getLogger(HelpCase.class.getName());
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /**
     * case1：点赞
     *1、进入Watch->观看直播/重播视频
     *2、点赞
     * */
    @Test
    public void testD1() throws UiObjectNotFoundException, RemoteException {
        Nav.navToWatch();
        WatchAction.tovedio();
        Spoon.screenshot("moreagree", "进入关注界面");
        int zannum_a = WatchAction.zan();
        logger.info("初始点赞数" + zannum_a);
        int i;
        for (i = 0; i < WatchAction.zan_number1; i++) {
            clickById("com.sioeye.sioeyeapp:id/watch_player_portrait_like");
        }
        int zannum_b = WatchAction.zan();
        int zannum = zannum_b - zannum_a;
        logger.info(WatchAction.zan_number1 + "赞后人数增加" + zannum + "人");
        Spoon.screenshot("after_zan", "" + zannum_b);
        if ((zannum_a + WatchAction.zan_number1) != zannum_b) {
            String error = zannum_a + "-点击" + WatchAction.zan_number1 + "次前赞后-" + zannum_b;
            logger.info(error);
            Spoon.screenshot("fail", error);
            Assert.fail(zannum_a + "|" + zannum_b);
        }
    }
    /**
     * case1：点赞
     *键盘点赞
     * */
    @Test
    public void testD2() throws UiObjectNotFoundException, RemoteException {
        Nav.navToWatch();
        WatchAction.tovedio();
        Spoon.screenshot("moreagree", "进入关注界面");
        int zannum_a = WatchAction.zan();
        logger.info("初始点赞数" + zannum_a);
        int i;
        for (i=0;i<WatchAction.zan_number2;i++) {
            clickById("com.sioeye.sioeyeapp:id/tipText");
            waitTime(1);
            clickById("com.sioeye.sioeyeapp:id/float_like");
        }
        int zannum_b = WatchAction.zan();
        int zannum = zannum_b - zannum_a;
        logger.info(WatchAction.zan_number2 + "赞后人数增加" + zannum + "人");
        Spoon.screenshot("after_zan", "" + zannum_b);
        if ((zannum_a + WatchAction.zan_number2) != zannum_b) {
            String error = zannum_a + "-点击" + WatchAction.zan_number2 + "次前赞后-" + zannum_b;
            logger.info(error);
            Spoon.screenshot("fail", error);
            Assert.fail(zannum_a + "|" + zannum_b);
        }
    }
    /**
     * case1：点赞
     *点击区域随机点赞
     * */
    @Test
    public void testD3() throws UiObjectNotFoundException, RemoteException {
        Nav.navToWatch();
        WatchAction.tovedio();
        Spoon.screenshot("moreagree", "进入关注界面");
        int zannum_a = WatchAction.zan();
        logger.info("初始点赞数" + zannum_a);
        UiObject sb = gDevice.findObject(new UiSelector().resourceId("com.sioeye.sioeyeapp:id/click_view"));
        Rect r =sb.getBounds();
        int x0 = r.left;
        int y0 = r.top;
        int x1 = r.right;
        int y1 = r.bottom;
        logger.info(x0+"*"+y0+"*"+x1+"*"+y1+"*");
        int i;
        for (i=0;i<WatchAction.zan_number3;i++) {
            int xx0 = (int) (Math.random() * (x1 - x0));
            int yy0 = (int) (Math.random() * (y1 - y0));
            clickByPonit(xx0+x0, yy0+y0);
        }
        waitTime(1);
        int zannum_b = WatchAction.zan();
        int zannum = zannum_b - zannum_a;
        logger.info(WatchAction.zan_number3 + "赞后人数增加" + zannum + "人");
        Spoon.screenshot("after_zan", "" + zannum_b);
        if ((zannum_a + WatchAction.zan_number3) != zannum_b) {
            String error = zannum_a + "-点击" + WatchAction.zan_number3 + "次前赞后-" + zannum_b;
            logger.info(error);
            Spoon.screenshot("fail", error);
            Assert.fail(zannum_a + "|" + zannum_b);
        }
    }



    }
