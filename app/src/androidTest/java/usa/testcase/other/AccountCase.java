package usa.testcase.other;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ckt.base.VP2;
import usa.action.AccountAction;
import usa.action.MeAction;
import usa.page.App;

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
        AccountAction.logOutAccount();
        AccountAction.navToSignUp();

    }
}
