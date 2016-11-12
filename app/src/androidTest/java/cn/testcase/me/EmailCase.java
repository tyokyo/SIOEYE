package cn.testcase.me;

import android.app.Instrumentation;
import android.os.Environment;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;
import com.squareup.spoon.Spoon;
import org.hamcrest.Asst;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;
import bean.InfoBean;
import ckt.base.VP2;
import ckt.tools.Property;
import cn.action.AccountAction;
import cn.action.MeAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;
import cn.page.MePage;

/**
 * Created by elon on 2016/10/13.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class EmailCase extends VP2 {
    private static Logger logger=Logger.getLogger(EmailCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_EN);
        //处于登录状态，已经登录默认的账号
        AccountAction.inLogin();
    }
    //修改邮件账号
    @Test
    public void testModifyEmailAddress() throws UiObjectNotFoundException {
        //email编辑界面
        InfoBean infoBean=MeAction.navToEmail();
        clearText(MePage.SAMPLE_CONTENT);
        String email=infoBean.getEmail();
        String modifyAddress = "add"+email;
        setText(MePage.SAMPLE_CONTENT,modifyAddress);
        clickById(MePage.USER_EDIT_DONE);
        String afterModify=MeAction.getEmailAddress();
        Assert.assertEquals("email address modify success",afterModify,modifyAddress);
        //退出登录
        AccountAction.logOutAccount();
        //登录,使用新的邮件地址登录App
        AccountAction.logInAccount(modifyAddress, Constant.passwd);
        //验证登录结果
        Assert.assertEquals("login success",true,id_exists(MePage.ID_MAIN_TAB_ME));
        //Now 修改邮箱账号为默认账号
        MeAction.navToEmail();
        setText(MePage.SAMPLE_CONTENT,email);
        clickById(MePage.USER_EDIT_DONE);
        Assert.assertEquals("login success",true,text_exists(email));
        Spoon.screenshot("chang_to_default",email);
    }
    //修改为存在的邮件账号,有提示
    @Test
    public void testModifyEmailAddressAlreadyExist() throws UiObjectNotFoundException, IOException, InterruptedException {
        //email编辑界面
        InfoBean infoBean=MeAction.navToEmail();
        clearText(MePage.SAMPLE_CONTENT);
        String email=infoBean.getEmail();
        String modifyAddress = Constant.qq_mail_address;
        setText(MePage.SAMPLE_CONTENT,modifyAddress);
        //这里需要通过Android Accessbility获取event事件 获取弹出框内容
        clickById(MePage.USER_EDIT_DONE);
        waitTime(2);
        Spoon.screenshot("change_email_to_exist","不能设置成功,提示邮件账号已经存在");
        Assert.assertEquals("email address already exist",true,id_exists(MePage.USER_EDIT_DONE));
        gDevice.pressBack();
        Assert.assertEquals("email address modify fail",email,MeAction.getEmailAddress());
        Spoon.screenshot("email",email);
    }
    //修改邮件不保存
    @Test
    public void testModifyEmailAddressThenCancel() throws UiObjectNotFoundException, IOException, InterruptedException {
        //email编辑界面
        InfoBean infoBean=MeAction.navToEmail();
        clearText(MePage.SAMPLE_CONTENT);
        String email=infoBean.getEmail();
        String modifyAddress = Constant.tyokyo_qq_email;
        setText(MePage.SAMPLE_CONTENT,modifyAddress);
        gDevice.pressBack();
        String afterModify=MeAction.getEmailAddress();
        Assert.assertEquals("email address modify success",afterModify,email);

        //退出登录
        AccountAction.logOutAccount();
        //登录,使用新的邮件地址登录App
        AccountAction.logInAccount(Constant.getUserName(), Constant.getPassword());
        //验证登录结果
        Assert.assertEquals("login success",true,id_exists(MePage.ID_MAIN_TAB_ME));
        //Now 修改邮箱账号为默认账号
        Spoon.screenshot("login",email);
    }
}
