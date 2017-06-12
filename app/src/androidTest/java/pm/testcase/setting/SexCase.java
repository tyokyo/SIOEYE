package pm.testcase.setting;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import pm.action.AccountAction;
import pm.action.MeAction;
import pm.page.App;
import pm.page.MePage;

/**
 * Created by elon on 2016/11/16.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class SexCase extends VP2 {
    Logger logger = Logger.getLogger(SexCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    @Test
    public void testMale() throws UiObjectNotFoundException {
        //修改性别为-male
        MeAction.navToSex();
        clickById(MePage.SETTINGS_SEX_MALE);
        //验证
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToSex();
        gDevice.pressBack();
        String sex = MeAction.getSex();
        Asst.assertEquals("change to male","Male",sex);
        gDevice.pressBack();
        Spoon.screenshot("male");
    }
    @Test
    public void testFemale() throws UiObjectNotFoundException {
        //修改性别为-female
        MeAction.navToSex();
        clickById(MePage.SETTINGS_SEX_FEMALE);
        //验证
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToSex();
        gDevice.pressBack();
        String sex = MeAction.getSex();
        Asst.assertEquals("change to female","Female",sex);
        gDevice.pressBack();
        Spoon.screenshot("female");
    }
    @Test
    public void testSecret() throws UiObjectNotFoundException {
        //修改性别为-female
        MeAction.navToSex();
        clickById(MePage.SETTINGS_SEX_SECRET);
        //验证
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        MeAction.navToSex();
        gDevice.pressBack();
        String sex = MeAction.getSex();
        Asst.assertEquals("change to secret","Privacy",sex);
        gDevice.pressBack();
        Spoon.screenshot("secret");
    }
}
