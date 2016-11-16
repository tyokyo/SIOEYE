package usa.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import usa.page.App;
import usa.testcase.discover.Demo;

/**
 * Created by caibing.yin on 2016/11/16.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class RegisterCase extends VP2 {
    Logger logger = Logger.getLogger(Demo.class.getName());
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /**
     *case19:
     *电话号码注册
     *1、选择地区，输入不合规范的手机号码进行注册
     *   Result：提示电话号码不正确
     */
    @Test
    public void testWrongPhoneNumberRegisterCase(){

    }
    /**
     * case20:
     *电话号码注册
     *1、选择地区，输入合规范的手机号码进行注册
     *2、发送验证码，输入错误验证码
     *  Result：提示验证码错误，可重新发送验证码短信
     * */
    @Test
    public void testWrongPWRegisterCase()  {

    }
    /**
     *case21:
     *Email注册
     *1、输入正确的Email进行注册
     *2、创建密码
     *3、创建Sioeye ID
     *4、创建用户名
     *  Result：注册成功
     * */
    @Test
    public void testEmailCase()  {

    }
}
