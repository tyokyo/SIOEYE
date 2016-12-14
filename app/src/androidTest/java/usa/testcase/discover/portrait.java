package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import com.squareup.spoon.Spoon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.logging.Logger;

import ckt.base.VP2;
import usa.page.App;
import usa.page.Me;
import usa.testcase.me.ActivityCase;

/**
 * Created by admin on 2016/11/10   .
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class portrait extends VP2 {
    Logger logger = Logger.getLogger(ActivityCase.class.getName());
    @Before
    public void setup(){openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);}

    @Test
   //输入数据点击保存
    public void clickOneportrait() throws IOException {
        clickById(Me.ID_MAIN_TAB_ME);
        waitTime(1);
        clickById(Me.ID_USER_EDIT);
        waitTime(1);
        clickById(Me.GETNICKNAMECONTENT);
        waitTime(2);
        getObject2ById(Me.ABOUT_ME_CONTENT).clear();
        waitTime(1);
        getObject2ById(Me.ABOUT_ME_CONTENT).setText("zhaojian");
        waitTime(2);
        clickById(Me.LIVE_CONFIGURATION_DONE_TITLE);
        if (!getUiObjectByText("zhaojian").exists()) {
            Spoon.screenshot("change fail", "修改失败");
            makeToast("修改失败", 5);
            Assert.fail("修改失败");
        } else {
            makeToast("change successful", 5);
        }
    }
        @Test
        //输入数据点击返回
        public void clickOneportrait1() throws IOException{
        clickById(Me. ID_MAIN_TAB_ME);
        waitTime(1);
        clickById(Me.ID_USER_EDIT);
        waitTime(1);
        clickById(Me.GETNICKNAMECONTENT);
        waitTime(1);
        getObject2ById(Me.ABOUT_ME_CONTENT).clear();
        waitTime(1);
        getObject2ById(Me.ABOUT_ME_CONTENT).setText("zhaojian");
        waitTime(2);
        clickById(Me.HELP_BACK);
        if(!getUiObjectByText("zhaojian").exists()){
            Spoon.screenshot("change fail","修改失败");
            makeToast("修改失败",5);
            Assert.fail("修改失败");
        }
        else{
            makeToast("change successful",5);
        }
    }


}
