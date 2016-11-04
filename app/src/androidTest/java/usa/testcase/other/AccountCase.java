package usa.testcase.other;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import ckt.tools.Constant;
import usa.action.AccountAction;
import usa.action.MeAction;
import usa.action.Nav;
import usa.page.Account;
import usa.page.App;
import usa.page.Me;

/**
 * Created by elon on 2016/11/4.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AccountCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /*
    * "电话号码
    注册"	"1、选择地区，输入不合规范的手机号码进行注册
"	提示电话号码不正确
    * */
    @Test
    public void test_register_with_error_mobile_number() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByMobile();
        //输入手机号码
        setText(Account.SIGN_UP_TEL_ET_INPUT_PHONE,"1354794674");
        waitUntilFind(Account.SIGN_UP_ERROR_TIP,10);
        Spoon.screenshot("error_mobile_number","Invalid Mobile Number");
        //check continue
        Asst.assertEquals("Invalid Mobile Number",true,getObjectById(Account.SIGN_UP_ERROR_TIP).exists());
    }
    /*
   * "电话号码
   注册"	"1、选择地区，输入合规范的手机号码进行注册
"	提示电话号码正确
   * */
    @Test
    public void test_register_with_mobile_number() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByMobile();
        //输入手机号码
        String tel = getRandomTel();
        setText(Account.SIGN_UP_TEL_ET_INPUT_PHONE,tel);
        waitUntilFind(Account.SIGN_UP_CONTINUE,10);
        Spoon.screenshot("continue");
        //check continue
        Asst.assertEquals("continue",true,getObjectById(Account.SIGN_UP_CONTINUE).exists());

    }
    /*
    Email注册
    "1、输入正确的Email进行注册
    2、创建密码
    3、创建Sioeye ID
    4、创建用户名"	注册成功
    * */
    @Test
    public void test_register_with_email() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        String email_address=getRandomEmail(3,8);
        //输入有效邮件地址
        setText(Account.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(Account.SIGN_UP_CONTINUE,10);
        clickById(Account.SIGN_UP_CONTINUE);
        //输入密码
        setText(Account.SIGN_UP_ACCOUNT_PASSWORD_INPUT,"123456789");
        clickById(Account.SIGN_UP_CONTINUE);
        //id
        String eye_id=getRandomString(4);
        setText(Account.SIGN_UP_ACCOUNT_SIOEYE_ID,eye_id);
        clickById(Account.SIGN_UP_CONTINUE);
        //nick name
        String nick_name=getRandomString(4);
        setText(Account.SIGN_UP_ACCOUNT_NICK_NAME,nick_name);
        clickById(Account.SIGN_UP_CONTINUE);
        //tv select default
        clickById(Account.SIGN_UP_ACCOUNT_DEFAULT_SELECT);

        //check for user info
        MeAction.navToUserEdit();
        String active_nickName=getText(Me.NAV_EDIT_NICKNAME);
        String active_email=getText(Me.NAV_EDIT_EMAIL);
        String active_eye_id=getText(Me.NAV_EDIT_SIOEYE_ID);
        Asst.assertEquals("nick name",email_address,active_email);
        Asst.assertEquals("email",nick_name,active_nickName);
        Asst.assertEquals("id",eye_id,active_eye_id);

        //注销登录
        AccountAction.logOutAccount();
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
    }
}
