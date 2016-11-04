package usa.testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObjectNotFoundException;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import usa.action.FollowingAction;
import usa.action.MeAction;
import usa.action.Nav;
import usa.page.App;
import usa.page.Me;
import ckt.base.VP2;
import usa.page.App;
import usa.page.Me;

/**
 * Created by elon on 2016/10/18.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class FollowerCase extends VP2{
    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    //搜索Followers用户
    @Test
    public void testSearchFollowers() throws UiObjectNotFoundException, IOException {
        Nav.navToFollowers();
        int followers = gDevice.findObjects(By.res(Me.FOLLOWERING_AVATAR)).size();
        if (followers>=1){
            clickById(Me.FOLLOWERING_AVATAR);
            String user_id=getObjectById(Me.SIOEYE_USER_ID).getText().replace("@","");
            String user_name=getObjectById(Me.FOLLOWERING_USERNAME).getText().replace("@","");
            gDevice.pressBack();
            gDevice.pressBack();
            Nav.navToWatchSearch();
            FollowingAction.searchFollowingUser(user_id);
            boolean find_name=getUiObjectByText(user_name).exists();
            int find_user=gDevice.findObjects(By.res(Me.FOLLOWERING_AVATAR)).size();
            Asst.assertEquals("search user",1,find_user);
            Asst.assertEquals("search user",true,find_name);
            Spoon.screenshot("find_user",""+user_name);
        }
    }

}
