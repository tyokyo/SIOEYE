package cn.testcase.me;

import ckt.base.VP2;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.App;
import cn.page.MePage;

/**
 * Created by chendaofa on 2017/07/18.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)



public class VipRightCase extends VP2 {
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /**
     * 跳转到会员权益
     * @author chendaofa
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testToVipRight() throws UiObjectNotFoundException {
        MeAction.navToVipRights();
            waitTime(5);
            String active_name2=getObjectById(MePage.VIP_RIGHT_TITLE).getText();
            String expect_name2="VIP Rights";
            Asst.assertEquals("是否跳转到会员权益界面", expect_name2, active_name2);
        Spoon.screenshot("testSetCoverPlot");

    }
    /**
     * 开通会员检查弹出框
     * @author chendaofa
     */

    @Test
    @SanityTest
    @PerformanceTest
    public void testPurchase() throws UiObjectNotFoundException {
        MeAction.navToVipRights();
        getObject2ById(MePage.VIP_RIGHT).swipe(Direction.UP,0.5f);
        clickById(MePage.VIP_RIGHT_TV_OPEN);
        String expect_name1="WeChat Pay";
        String active_name1=getObjectById(MePage.VIP_RIGHT_TV_CONTENT).getText();
        String expect_name3="Cancel";
        String active_name3=getObjectById(MePage.VIP_RIGHT_TV_CHOOSE_CANCEL).getText();
        Asst.assertEquals("点击的信息是否一致", expect_name1+expect_name3, active_name1+active_name3);
        clickById(MePage.VIP_RIGHT_TV_CHOOSE_CANCEL);
        Spoon.screenshot("testPurchase");

    }
}
