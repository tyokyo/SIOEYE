package cn.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import cn.action.AccountAction;
import cn.action.MainAction;
import cn.action.MeAction;
import ckt.base.VP2;
import cn.action.WatchAction;
import cn.page.App;
import cn.page.MePage;
import cn.page.WatchPage;
/**
 * Created by elon on 2016/10/12.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
/*个性签名基本功能验证*/
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
    //修改内容之后到watch搜索对应id号 查看about me 内容是否修改成功
    @Test
    public void testMeSearch() throws UiObjectNotFoundException {
        MainAction.navToMe();
        //获取 id
        String user_id = getTex(MePage.SIOEYE_USER_ID);
        Spoon.screenshot("Me");
        clickById(MePage.ID_USER_EDIT);
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomString(40);
        //设置个性签名
        setText(MePage.ABOUT_ME_CONTENT,input);
        clickById(MePage.USER_EDIT_DONE);
        //获取个性签名显示内容
        String expect = MeAction.getAboutMe();
        if (!expect.equals(input)){
            Assert.fail(expect+" not equal "+input);
        }
        Spoon.screenshot("about_me",input);
        gDevice.pressBack();
        //搜索账号 查看个性签名是否显示正确
        WatchAction.navToWatchSearch();
        getObjectById(MePage.SEARCH_BTN_WATCH_FILTER).setText(user_id);
        waitUntilFind(WatchPage.WATCH_SEARCH_TYPE_CONTACTS,30);
        waitTime(3);
        clickById(WatchPage.WATCH_USER_AVATAR);
        Spoon.screenshot("about_me",input);
        Asst.assertTrue(input+" save success",getUiObjectByText(input).exists());
    }
    //输入最大的字符容量
    @Test
    public void testEdit60C() throws UiObjectNotFoundException {
        MeAction.navToUserEdit();
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomString(60);
        getObjectById(MePage.ABOUT_ME_CONTENT).setText(input);
        clickById(MePage.USER_EDIT_DONE);
        //验证个性签名
        String expect = MeAction.getAboutMe();
        Assert.assertEquals("change success",expect,input);
        Spoon.screenshot(gDevice,input);
    }
    //超过最大的字符时的处理
    @Test
    public void testEdit61C() throws UiObjectNotFoundException, IOException {
        MeAction.navToUserEdit();
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomString(100);
        shellInputText(input);
        clickById(MePage.USER_EDIT_DONE);
        gDevice.pressBack();
        //验证个性签名
        String expect = MeAction.getAboutMe();
        input = input.substring(0,60);
        Assert.assertEquals("change success",expect,input);
        Spoon.screenshot(gDevice,input);
    }
    //输入之后不保存
    @Test
    public void testNotSave() throws UiObjectNotFoundException {
        MainAction.navToMe();
        Spoon.screenshot(gDevice,"Me");
        String expect = getUiObjectById(MePage.ABOUT_ME_DISPLAY).getText();
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
    //删除功能验证
    @Test
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
        expect = getUiObjectById(MePage.ABOUT_ME_DISPLAY).getText();
        Assert.assertEquals("change success",expect,"");
        gDevice.pressBack();
    }
    //输入内容为特殊符号时的处理,长度=60
    @Test
    public void testEdit61_Symbol() throws UiObjectNotFoundException, IOException {
        MainAction.navToMe();
        Spoon.screenshot(gDevice,"Me");
        clickById(MePage.ID_USER_EDIT);
        clickById(MePage.NAV_EDIT_ABOUT_ME);
        getObjectById(MePage.ABOUT_ME_CONTENT).clearTextField();
        String input = getRandomSymbol(100);
        shellInputText(input);
        clickById(MePage.USER_EDIT_DONE);
        String expect = MeAction.getAboutMe();
        input = input.substring(0,60);
        Assert.assertEquals("about me",input,expect);
        Spoon.screenshot("symbol",input);
    }
}
