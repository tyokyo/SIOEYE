package cn.testcase.other;

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
import java.util.logging.Logger;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;

/**
 * Created by caibing.yin on 2016/11/16.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class RegisterCase extends VP2 {
    Logger logger = Logger.getLogger(RegisterCase.class.getName());
    @Before
    public void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /**
     * case20:
     *电话号码注册
     *1、选择地区，输入合规范的手机号码进行注册
     *  Result：弹出Continue
     * */
    @Test
    public void testInvalidTelNumberSignUp() throws UiObjectNotFoundException, IOException {
        AccountAction.logOutAccount();
        AccountAction.navToSignUp_ByMobile();
        String InvalidTelNumber = AccountAction.getRandomTel();
        for (int i = 0; i <11 ; i++) {
            shellInputText(String.valueOf(InvalidTelNumber.charAt(i)));
            waitTime(4);
            if (i == 10){
                boolean continue_btn=AccountAction.id_exists(AccountPage.SIGN_UP_CONTINUE);
                if (!continue_btn){
                    Asst.fail("InvalidTelNumber_fail");
                }
            }else{
                if (!AccountAction.id_exists(AccountPage.SIGN_UP_ERROR_TIP)){
                    Spoon.screenshot("InvalidTelNumber_fail");
                    Asst.fail("InvalidTelNumber_fail");
                }
            }
        }
    }
    /**
     *case20:
     *电话号码注册
     *1、选择地区，输入长度大于11位的手机号码进行注册
     *   Result：提示电话号码无效
     */
    @Test
    public void testInvalidTelNumber1SignUp() throws UiObjectNotFoundException, IOException {
        AccountAction.logOutAccount();
        AccountAction.navToSignUp_ByMobile();
        String InvalidTelNumber = getRandomTel().concat(getRandomTel()) ;//得到22位的字符串
        shellInputText(getRandomTel());
        for (int i=0;i<5;i++) {
            shellInputText(String.valueOf(InvalidTelNumber.charAt(i)));
            waitTime(4);
            if (!AccountAction.id_exists(AccountPage.SIGN_UP_ERROR_TIP)){
                Spoon.screenshot("InvalidTelNumber_fail");
                Asst.fail("InvalidTelNumber_fail");
            }
            //AccountAction.clearText(Account.ACCOUNT_INPUT_PHONE);
        }
    }
    /**
     *case22:
     *Email注册
     *1、输入正确的Email进行注册
     *2、创建密码
     *3、创建Sioeye ID
     *4、创建用户名
     *  Result：注册成功
     * */
    @Test
    public void testValidEmail() throws UiObjectNotFoundException, IOException {
        AccountAction.logOutAccount();
        AccountAction.navToSignUp_ByEmail();
        String ValidEmail = AccountAction.getRandomEmail(3,15);
        shellInputText(ValidEmail);
        waitTime(3);
        if(!id_exists(AccountPage.SIGN_UP_CONTINUE)){
            Spoon.screenshot("ValidEmailCase_fail");
            Asst.fail("ValidEmail");
        }
    }
    /**
     *case22:
     *Email注册
     *1、输入错误的Email进行注册
     *  Result：提示邮箱错误
     * */
    @Test
    public void testInvalidEmail() throws UiObjectNotFoundException, IOException {
        AccountAction.logOutAccount();
        AccountAction.navToSignUp_ByEmail();
        String ValidEmail = AccountAction.getRandomEmail(3, 11);
        shellInputText(ValidEmail);
        waitTime(3);
        if (!id_exists(AccountPage.SIGN_UP_CONTINUE)) {
            Spoon.screenshot("ValidEmail_fail");
            Asst.fail("ValidEmail_fail");
        }

        AccountAction.clearText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT);
        String InValidEmail = ValidEmail.replace("@","!");
        shellInputText(InValidEmail);
        waitTime(4);
        if (!id_exists(AccountPage.SIGN_UP_ERROR_TIP)) {
            Spoon.screenshot("InValidEmailCase_fail");
            Asst.fail("InValidEmailCase_fail");
        }
    }

    /*
     *    2016/12/29 徐瑞香
     *    注册时输入12位不合法的电话号码
     */

    @Test
    public void err_mobile_reg() throws UiObjectNotFoundException {
        AccountAction.logOutAccount();
        //注销账号
        AccountAction.navToSignUp_ByMobile();
        //到手机注册界面
        clickById(AccountPage.SIGN_UP_TEL_ET_INPUT_PHONE);
        //点击输入手机号码文本框
        int i=12;
        setText(AccountPage.SIGN_UP_TEL_ET_INPUT_PHONE, Constant.randomErrPhoneNumber(i));
        //输入12位不合法的随机手机号码
        waitTime(2);
        Assert.assertEquals("注册无效的手机号码",true,getObjectById(AccountPage.SIGN_UP_ERROR_TIP).exists());
        Spoon.screenshot("Err_mobileNumber_Reg");


    }



}
