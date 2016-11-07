package usa.testcase.other;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.action.AccountAction;
import usa.action.MeAction;
import usa.page.Account;
import usa.page.App;
import usa.page.Constant;
import usa.page.Me;

/**
 * Created by elon on 2016/11/4.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class AccountCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageNameInLogin(App.SIOEYE_PACKAGE_NAME_USA);
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

        //Login
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
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

        //Login
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
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
        String active_nickName=getObject2ById(Me.NAV_EDIT_NICKNAME).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        String active_email=getObject2ById(Me.NAV_EDIT_EMAIL).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        String active_eye_id=getObject2ById(Me.NAV_EDIT_SIOEYE_ID).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        Asst.assertEquals("nick name",nick_name,active_nickName);
        Asst.assertEquals("email",email_address,active_email);
        Asst.assertEquals("id",eye_id,active_eye_id);

        //注销登录
        AccountAction.logOutAccount();
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
    }
    /*
    重复Email注册
    注册失败
    * */
    @Test
    public void test_register_with_repeat_email() throws UiObjectNotFoundException {
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
        String active_nickName=getObject2ById(Me.NAV_EDIT_NICKNAME).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        String active_email=getObject2ById(Me.NAV_EDIT_EMAIL).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        String active_eye_id=getObject2ById(Me.NAV_EDIT_SIOEYE_ID).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        Asst.assertEquals("nick name",nick_name,active_nickName);
        Asst.assertEquals("email",email_address,active_email);
        Asst.assertEquals("id",eye_id,active_eye_id);

        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        //输入有效邮件地址
        setText(Account.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(Account.SIGN_UP_ACCOUNT_TV_CONTENT,10);
        //check
        String pop_message="This email address has been registered";
        Asst.assertEquals(pop_message,pop_message,getTex(Account.SIGN_UP_ACCOUNT_TV_CONTENT));

        AccountAction.logInAccount(Constant.userName,Constant.passwd);
    }
    /*
    重复Email注册
    注册失败
    * */
    @Test
    public void test_register_with_repeat_eye_id() throws UiObjectNotFoundException {
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
        String active_nickName=getObject2ById(Me.NAV_EDIT_NICKNAME).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        String active_email=getObject2ById(Me.NAV_EDIT_EMAIL).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        String active_eye_id=getObject2ById(Me.NAV_EDIT_SIOEYE_ID).findObject(By.res(Me.EDIT_CONTENT_TEXT)).getText();
        Asst.assertEquals("nick name",nick_name,active_nickName);
        Asst.assertEquals("email",email_address,active_email);
        Asst.assertEquals("id",eye_id,active_eye_id);

        //-----------------repeat to register with same sioeye id-------------------
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        email_address=getRandomEmail(3,8);
        //输入有效邮件地址
        setText(Account.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(Account.SIGN_UP_CONTINUE,10);
        clickById(Account.SIGN_UP_CONTINUE);
        //输入密码
        setText(Account.SIGN_UP_ACCOUNT_PASSWORD_INPUT,"123456789");
        clickById(Account.SIGN_UP_CONTINUE);
        //repeat id
        setText(Account.SIGN_UP_ACCOUNT_SIOEYE_ID,eye_id);
        waitUntilFind(Account.SIGN_UP_ERROR_TIP,10);
        //check
        String pop_message="The sioeye id already registered";
        Spoon.screenshot("id_repeat",pop_message);
        Asst.assertEquals(pop_message,pop_message,getTex(Account.SIGN_UP_ERROR_TIP));
        //登录系统
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
    }
    /*
    Email注册
    "1、输入无效邮件地址
    * */
    @Test
    public void test_register_with_error_email() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        String email_address=getRandomEmail(3,8)+"@";
        //输入无效邮件地址
        setText(Account.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(Account.SIGN_UP_ERROR_TIP,10);

        String error_pop="Invalid Email address";
        Asst.assertEquals(error_pop,error_pop,getTex(Account.SIGN_UP_ERROR_TIP));

        //注销登录
        AccountAction.logOutAccount();
        //登录系统
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
    }
    /*登录	1、输入正确的手机号码/Email、密码登录	成功登录*/
    @Test
    public void test_logInAccount_email() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //登录系统
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
        Asst.assertEquals("login success",true,id_exists(Me.SIOEYE_USER_ID));
        Spoon.screenshot("login_success");
    }
    /*登录	1、输入正确的手机号码/Email、密码登录	成功登录*/
    @Test
    public void test_logInAccount_error_email_password() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //登录系统
        AccountAction.logInAccount(Constant.userName,Constant.error_passwd);
        Asst.assertEquals("login fail",false,id_exists(Me.SIOEYE_USER_ID));
        Spoon.screenshot("login_fail");
        //登录系统
        AccountAction.logInAccount(Constant.userName,Constant.passwd);
    }
}
