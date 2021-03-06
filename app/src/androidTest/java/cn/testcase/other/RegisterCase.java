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

import ckt.annotation.PerformanceTest;
import ckt.annotation.SanityTest;
import ckt.base.VP2;
import cn.action.AccountAction;
import cn.action.MainAction;
import cn.page.AccountPage;
import cn.page.App;
import cn.page.Constant;
import cn.page.MePage;

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
        AccountAction.logOutAccount();
    }

    /**
     * case1:
     * 电话号码注册
     * 1、选择地区，输入合规范的手机号码进行注册
     * Result：弹出Continue
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testInvalidTelNumberSignUp() throws UiObjectNotFoundException, IOException {
        AccountAction.navToSignUp_ByMobile();
        String InvalidTelNumber = AccountAction.getRandomTel();
        for (int i = 0; i < 11; i++) {
            shellInputText(String.valueOf(InvalidTelNumber.charAt(i)));
            waitTime(4);
            if (i == 10) {
                boolean continue_btn = AccountAction.id_exists(AccountPage.SIGN_UP_CONTINUE);
                if (!continue_btn) {
                    Asst.fail("InvalidTelNumber_fail");
                }
            } else {
                if (!AccountAction.id_exists(AccountPage.SIGN_UP_ERROR_TIP)) {
                    Spoon.screenshot("InvalidTelNumber_fail");
                    Asst.fail("InvalidTelNumber_fail");
                }
            }
        }
    }

    /**
     * case2:
     * 电话号码注册
     * 1、选择地区，输入长度大于11位的手机号码进行注册
     * Result：提示电话号码无效
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testInvalidTelNumber1SignUp() throws UiObjectNotFoundException, IOException {
        AccountAction.navToSignUp_ByMobile();
        String InvalidTelNumber = getRandomTel().concat(getRandomTel());//得到22位的字符串
        shellInputText(getRandomTel());
        for (int i = 0; i < 5; i++) {
            shellInputText(String.valueOf(InvalidTelNumber.charAt(i)));
            waitTime(4);
            if (!AccountAction.id_exists(AccountPage.SIGN_UP_ERROR_TIP)) {
                Spoon.screenshot("InvalidTelNumber_fail");
                Asst.fail("InvalidTelNumber_fail");
            }
            //AccountAction.clearText(Account.ACCOUNT_INPUT_PHONE);
        }
    }

    /**
     * case3:
     * Email注册
     * 1、输入正确的Email进行注册
     * 2、创建密码
     * 3、创建Sioeye ID
     * 4、创建用户名
     * Result：注册成功
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testValidEmail() throws UiObjectNotFoundException, IOException {
        AccountAction.navToSignUp_ByEmail();
        String ValidEmail = AccountAction.getRandomEmail(3, 15);
        shellInputText(ValidEmail);
        waitTime(3);
        if (!id_exists(AccountPage.SIGN_UP_CONTINUE)) {
            Spoon.screenshot("ValidEmailCase_fail");
            Asst.fail("ValidEmail");
        }
    }

    /**
     * case4:
     * Email注册
     * 1、输入错误的Email进行注册
     * Result：提示邮箱错误
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testInvalidEmail() throws UiObjectNotFoundException, IOException {
        AccountAction.navToSignUp_ByEmail();
        String ValidEmail = AccountAction.getRandomEmail(3, 11);
        shellInputText(ValidEmail);
        waitTime(3);
        if (!id_exists(AccountPage.SIGN_UP_CONTINUE)) {
            Spoon.screenshot("ValidEmail_fail");
            Asst.fail("ValidEmail_fail");
        }

        AccountAction.clearText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT);
        String InValidEmail = ValidEmail.replace("@", "!");
        shellInputText(InValidEmail);
        waitTime(4);
        if (!id_exists(AccountPage.SIGN_UP_ERROR_TIP)) {
            Spoon.screenshot("InValidEmailCase_fail");
            Asst.fail("InValidEmailCase_fail");
        }
    }

    /**
     * case5:
     * 2016/12/29 徐瑞香
     * 检查注册时输入12位不合法的电话号码
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testErrMobileReg() throws UiObjectNotFoundException {
        AccountAction.navToSignUp_ByMobile();
        //到手机注册界面
        clickById(AccountPage.SIGN_UP_TEL_ET_INPUT_PHONE);
        //点击输入手机号码文本框
        int i = 12;
        setText(AccountPage.SIGN_UP_TEL_ET_INPUT_PHONE, Constant.randomErrPhoneNumber(i));
        //输入12位不合法的随机手机号码
        waitTime(2);
        Assert.assertEquals("注册无效的手机号码", true, getObjectById(AccountPage.SIGN_UP_ERROR_TIP).exists());
        Spoon.screenshot("Err_mobileNumber_Reg");
    }

    /**
     * By yun.yang 2017.1.2
     * case6:
     * Email注册,检查输入密码可见按钮是否有效
     * 1、输入随机Email进行注册
     * 2.在密码输入框中输入一串随机字符串（可见状态输入）
     * Result：可见
     * 3.点击可见按钮
     * Result：不可见
     * 4.重新在密码输入框中输入一串随机字符串（不可见状态输入）
     * Result：不可见
     * 5.点击可见按钮
     * Result：可见
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testUnVisiblePasswordInRegisterByEmail() throws UiObjectNotFoundException, IOException {
        AccountAction.navToInputPassword_ByEmailRegister();
        AccountAction.checkUnVisiblePassword();
    }

    @Test
    @SanityTest
    @PerformanceTest
    public void testVisiblePasswordInRegisterByEmail() throws UiObjectNotFoundException, IOException {
        AccountAction.navToInputPassword_ByEmailRegister();
        AccountAction.checkVisiblePassword();
    }

    /**
     * By yun.yang 2017.1.2
     * case7:
     * Email注册,检查输入密码达到6位字符后有提示继续按钮
     * 1、输入随机Email进行注册
     * 2.输入密码达到6位时
     * Result：提示继续
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testPasswordDigitInRegisterByEmail() throws UiObjectNotFoundException, IOException {
        AccountAction.navToInputPassword_ByEmailRegister();
        String password = Constant.randomFixedLengthStringGenerator(5);
        AccountAction.inputPassword(password);
        if (getObjectByTextStartsWith("Continue").exists()) {
            Spoon.screenshot("ItCanContinueWith5DigitPassword");
            Assert.fail("ItCanContinueWith5DigitPassword");
        } else {
            clearText(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT);
            password = Constant.randomFixedLengthStringGenerator(6);
            getObjectById(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT).setText(password);
            waitTime(2);
            if (!getObjectByTextStartsWith("Continue").exists()) {
                Spoon.screenshot("ItCannotContinueWith6DigitPassword");
                Assert.fail("ItCan'tContinueWith6DigitPassword");
            }
        }
    }
    /**
     * case10:
     * Created by 徐瑞香 on 2017/01/16
     * 检查默认的注册方式：手机号码
     */
    @Test
    @SanityTest
    @PerformanceTest
    public void testDefaultRegisterMode() throws UiObjectNotFoundException {
        //先执行before中的logout方法，会出现两种界面：登陆界面，me界面，所以用if判断一下
        if (id_exists(MePage.ID_MAIN_TAB_ME)) {
            MainAction.clickMe();
        }
        clickByText("Sign Up");
        waitTime(2);
        //根据文本内容，判断默认的注册方式是否是手机号码注册
        //请输入手机号码
        Boolean phoneRegisterText1 = text_exists("Enter your phone number");
        Boolean phoneRegisterText2 = text_exists("Enter your phone number");
        Boolean Actual = phoneRegisterText1 && phoneRegisterText2;
        Asst.assertFalse(!Actual);

    }
}
