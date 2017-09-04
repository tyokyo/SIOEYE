package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import cn.action.AccountAction;
import cn.action.MainAction;
import cn.action.MeAction;
import ckt.base.VP2;
import cn.page.App;
import cn.page.MePage;
/**
 * Created by elon on 2016/10/12.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*
*个性签名基本功能验证
* 允许输入的最大字符为60个英文字符或者30个汉字*/
public class AboutMeCase extends VP2 {
    @Before
    public  void setup() throws UiObjectNotFoundException {
        //启动被测App
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        //确保App处于登录状态
        AccountAction.inLogin();
    }
    //about me input string length 10
    @Test
    @SanityTest
    @PerformanceTest
    public void testEdit10C() throws UiObjectNotFoundException {
        MainAction.navToMe();
        //获取sio eye id，进入about me 界面
        String user_id = getObjectById(MePage.SIOEYE_USER_ID).getText();
        Spoon.screenshot("Me");
        clickById(MePage.ID_USER_EDIT);
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        //清除文本
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomString(10);
        getObjectById(MePage.ABOUT_ME_CONTENT).setText(input);
        //确认修改
        clickById(MePage.USER_EDIT_DONE);
        String me = MeAction.getAboutMe();
        //验证是否修改成功
        Assert.assertEquals("change success",input,me);
        gDevice.pressBack();
        String expect = getUiObjectById(MePage.ABOUT_ME_DISPLAY).getText();
        Assert.assertEquals("change success",expect,input);
        Spoon.screenshot(gDevice,input);
        gDevice.pressBack();
    }

    //输入最大的字符容量
    @Test
    @SanityTest
    @PerformanceTest
    public void testEdit30C() throws UiObjectNotFoundException {
        MeAction.navToUserEdit();
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomString(30);
        getObjectById(MePage.ABOUT_ME_CONTENT).setText(input);
        clickById(MePage.USER_EDIT_DONE);
        //验证个性签名
        String expect = MeAction.getAboutMe();
        Assert.assertEquals("change success",expect,input);
        Spoon.screenshot(gDevice,input);
    }
    //超过最大的字符时的处理
    @Test
    @SanityTest
    @PerformanceTest
    public void testEdit70C() throws UiObjectNotFoundException, IOException {
        MeAction.navToUserEdit();
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomString(70);
        shellInputText(input);
        clickById(MePage.USER_EDIT_DONE);
        waitTime(3);
        //gDevice.pressBack();
        //验证个性签名
        String expect = MeAction.getAboutMe();
        input = input.substring(0,60);
        Assert.assertEquals("change success",expect,input);
        Spoon.screenshot(gDevice,input);
    }
    //输入之后不保存
    @Test
    @SanityTest
    @PerformanceTest
    public void testNotSave() throws UiObjectNotFoundException {
        MainAction.navToMe();
        Spoon.screenshot(gDevice,"Me");
        waitUntilFind(MePage.ABOUT_ME_DISPLAY,10000);
        String expect="";
        if (id_exists(MePage.ABOUT_ME_DISPLAY)){
            expect = getUiObjectById(MePage.ABOUT_ME_DISPLAY).getText();
            clickById(MePage.ID_USER_EDIT);
            clickById(MePage.NAV_EDIT_ABOUT_ME);
            getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
            String input = getRandomString(50);
            getObjectById(MePage.ABOUT_ME_CONTENT).setText(input);
            gDevice.pressBack();
            //验证个人信息编辑界面的个性签名
            String active = MeAction.getAboutMe();
            Assert.assertEquals("change success",expect,active);
            //验证个人主页上面的个性签名
            gDevice.pressBack();
            active = getUiObjectById(MePage.ABOUT_ME_DISPLAY).getText();
            Assert.assertEquals("change success",expect,active);
        }
    }
    //删除功能验证
    @Test
    @SanityTest
    @PerformanceTest
    public void testDeleteToNull() throws UiObjectNotFoundException {
        MeAction.navToUserEdit();
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        clickById(MePage.USER_EDIT_DONE);
        //验证个人信息编辑界面的个性签名
        String expect = MeAction.getAboutMe();
        Assert.assertEquals("about me is null",expect,"");
        //验证个人主页上面的个性签名
        gDevice.pressBack();
        waitTime(2);
        if (id_exists(MePage.ABOUT_ME_DISPLAY)){
            expect = getUiObjectById(MePage.ABOUT_ME_DISPLAY).getText();
            Assert.assertEquals("change success",expect,"");
        }
        gDevice.pressBack();
    }
    //输入内容为特殊符号时的处理,长度=30
    @Test
    @SanityTest
    @PerformanceTest
    public void testEdi40_Symbol() throws UiObjectNotFoundException, IOException {
        MainAction.navToMe();
        Spoon.screenshot(gDevice,"Me");
        clickById(MePage.ID_USER_EDIT);
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomSymbol(10);
        shellInputText(input);
        clickById(MePage.USER_EDIT_DONE);
        String expect = MeAction.getAboutMe();
        Assert.assertEquals("about me",input,expect);
        Spoon.screenshot("symbol",input);
    }
}
