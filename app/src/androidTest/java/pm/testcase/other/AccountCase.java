package pm.testcase.other;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import bean.InfoBean;
import ckt.base.VP2;
import pm.action.AccountAction;
import pm.action.MeAction;
import pm.page.AccountPage;
import pm.page.App;
import pm.page.Constant;
import pm.page.MePage;

/**
 * Created by elon on 2016/11/4.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class AccountCase extends VP2{
    private Logger logger = Logger.getLogger(AccountCase.class.getName());
    @Before
    public  void setup() throws UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_CN);
        AccountAction.inLogin();
    }
    /*
    * "电话号码
    注册"	"1、选择地区，输入不合规范的手机号码进行注册
"	提示电话号码不正确
    * */
    @Test
    public void testRegErrorMobileNumber() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByMobile();
        //输入手机号码
        setText(AccountPage.SIGN_UP_TEL_ET_INPUT_PHONE,"1354794674");
        waitUntilFind(AccountPage.SIGN_UP_ERROR_TIP,10);
        Spoon.screenshot("error_mobile_number","Invalid Mobile Number");
        //check continue
        Asst.assertEquals("Invalid Mobile Number",true,getObjectById(AccountPage.SIGN_UP_ERROR_TIP).exists());

        //Login
        AccountAction.logInAccount(Constant.userName, Constant.passwd);
    }
    /*
   * "电话号码
   注册"	"1、选择地区，输入合规范的手机号码进行注册
"	提示电话号码正确
   * */
    @Test
    public void testRegMobileNumber() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByMobile();
        //输入手机号码
        String tel = getRandomTel();
        setText(AccountPage.SIGN_UP_TEL_ET_INPUT_PHONE,tel);
        waitUntilFind(AccountPage.SIGN_UP_CONTINUE,10);
        Spoon.screenshot("continue");
        //check continue
        Asst.assertEquals("continue",true,getObjectById(AccountPage.SIGN_UP_CONTINUE).exists());

        //Login
        AccountAction.logInAccount(Constant.userName, Constant.passwd);
    }
    /*
    Email注册
    "1、输入正确的Email进行注册
    2、创建密码
    3、创建Sioeye ID
    4、创建用户名"	注册成功
    * */
    @Test
    public void testRegEmail() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        String email_address=getRandomEmail(3,8);
        //输入有效邮件地址
        setText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(AccountPage.SIGN_UP_CONTINUE,10);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //输入密码
        setText(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT,"123456789");
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //id
        String eye_id=getRandomString(4);
        setText(AccountPage.SIGN_UP_ACCOUNT_SIOEYE_ID,eye_id);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //nick name
        String nick_name=getRandomString(4);
        setText(AccountPage.SIGN_UP_ACCOUNT_NICK_NAME,nick_name);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //tv select default
        clickById(AccountPage.SIGN_UP_ACCOUNT_DEFAULT_SELECT);

        //check for user info
        InfoBean infoBean = MeAction.navToUserEdit();
        MeAction.getAccountPrivacyInfo(infoBean);
        logger.info(infoBean.toString());

        String active_nickName=infoBean.getNick_name();
        String active_email=infoBean.getEmail();
        String active_eye_id=infoBean.getId();

        Asst.assertEquals("nick name",nick_name,active_nickName);
        Asst.assertEquals("email",email_address,active_email);
        Asst.assertEquals("id",eye_id,active_eye_id);

        //注销登录
        AccountAction.logOutAccount();
        AccountAction.logInAccount(Constant.userName, Constant.passwd);
    }
    /*
    重复Email注册
    注册失败
    * */
    @Test
    public void testRegRepeatEmail() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        String email_address=getRandomEmail(3,8);
        //输入有效邮件地址
        setText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(AccountPage.SIGN_UP_CONTINUE,10);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //输入密码
        setText(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT,"123456789");
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //id
        String eye_id=getRandomString(4);
        setText(AccountPage.SIGN_UP_ACCOUNT_SIOEYE_ID,eye_id);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //nick name
        String nick_name=getRandomString(4);
        setText(AccountPage.SIGN_UP_ACCOUNT_NICK_NAME,nick_name);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //tv select default
        clickById(AccountPage.SIGN_UP_ACCOUNT_DEFAULT_SELECT);

        //check for user info
        InfoBean infoBean = MeAction.navToUserEdit();
        MeAction.getAccountPrivacyInfo(infoBean);
        logger.info(infoBean.toString());

        String active_nickName=infoBean.getNick_name();
        String active_email=infoBean.getEmail();
        String active_eye_id=infoBean.getId(); Asst.assertEquals("nick name",nick_name,active_nickName);
        Asst.assertEquals("email",email_address,active_email);
        Asst.assertEquals("id",eye_id,active_eye_id);

        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        //输入有效邮件地址
        setText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(AccountPage.SIGN_UP_ACCOUNT_TV_CONTENT,10);
        //check
        String pop_message="This email address has been registered";
        Asst.assertEquals(pop_message,pop_message,getTex(AccountPage.SIGN_UP_ACCOUNT_TV_CONTENT));

        AccountAction.logInAccount(Constant.userName, Constant.passwd);
    }
    /*
    重复Email注册
    注册失败
    * */
    @Test
    public void testRegRepeatEyeID() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        String email_address=getRandomEmail(3,8);
        //输入有效邮件地址
        setText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(AccountPage.SIGN_UP_CONTINUE,10);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //输入密码
        setText(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT,"123456789");
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //id
        String eye_id=getRandomString(4);
        setText(AccountPage.SIGN_UP_ACCOUNT_SIOEYE_ID,eye_id);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //nick name
        String nick_name=getRandomString(4);
        setText(AccountPage.SIGN_UP_ACCOUNT_NICK_NAME,nick_name);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //tv select default
        clickById(AccountPage.SIGN_UP_ACCOUNT_DEFAULT_SELECT);

        //check for user info
        InfoBean infoBean = MeAction.navToUserEdit();
        MeAction.getAccountPrivacyInfo(infoBean);
        logger.info(infoBean.toString());

        String active_nickName=infoBean.getNick_name();
        String active_email=infoBean.getEmail();
        String active_eye_id=infoBean.getId();
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
        setText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(AccountPage.SIGN_UP_CONTINUE,10);
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //输入密码
        setText(AccountPage.SIGN_UP_ACCOUNT_PASSWORD_INPUT,"123456789");
        clickById(AccountPage.SIGN_UP_CONTINUE);
        //repeat id
        setText(AccountPage.SIGN_UP_ACCOUNT_SIOEYE_ID,eye_id);
        waitUntilFind(AccountPage.SIGN_UP_ERROR_TIP,10);
        //check 这个ID已被注册
        String pop_message="The Sioeye ID is registered";
        Spoon.screenshot("id_repeat",pop_message);
        Asst.assertEquals(pop_message,pop_message,getTex(AccountPage.SIGN_UP_ERROR_TIP));
        //登录系统
        AccountAction.logInAccount(Constant.userName, Constant.passwd);
    }
    /*
    Email注册
    "1、输入无效邮件地址
    * */
    @Test
    public void testRegErrorEmail() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //进入手机注册界面
        AccountAction.navToSignUp_ByEmail();
        String email_address=getRandomEmail(3,8)+"@";
        //输入无效邮件地址
        setText(AccountPage.SIGN_UP_ACCOUNT_EMAIL_ADDRESS_ET_INPUT,email_address);
        waitUntilFind(AccountPage.SIGN_UP_ERROR_TIP,10);
        //无效邮箱地址
        String error_pop="Invalid Email address";
        Asst.assertEquals(error_pop,error_pop,getTex(AccountPage.SIGN_UP_ERROR_TIP));

        //注销登录
        AccountAction.logOutAccount();
        //登录系统
        AccountAction.logInAccount(Constant.userName, Constant.passwd);
    }
    /*登录	1、输入正确的手机号码/Email、密码登录	成功登录*/
    @Test
    public void test_logInAccount_email() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //登录系统
        AccountAction.logInAccount(Constant.userName, Constant.passwd);
        Asst.assertEquals("login success",true,id_exists(MePage.SIOEYE_USER_ID));
        Spoon.screenshot("login_success");
    }
    /*登录	1、输入正确的手机号码/Email、密码登录	成功登录*/
    @Test
    public void testLogInErrorEmailPwd() throws UiObjectNotFoundException {
        //注销登录
        AccountAction.logOutAccount();
        //登录系统
        AccountAction.logInAccount(Constant.userName, Constant.error_passwd);
        Asst.assertEquals("login fail",false,id_exists(MePage.SIOEYE_USER_ID));
        Spoon.screenshot("login_fail");
        //登录系统
        AccountAction.logInAccount(Constant.userName, Constant.passwd);
    }
}
