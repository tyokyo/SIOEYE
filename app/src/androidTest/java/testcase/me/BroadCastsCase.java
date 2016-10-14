package testcase.me;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.EventCondition;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.widget.TextView;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import ckt.base.VP2;
import page.App;
import page.Me;

/**
 * Created by elon on 2016/10/13.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class BroadCastsCase extends VP2{

    @Before
    public  void setup(){
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME);
    }

    public void testViewVideo(){

    }
    public void testEditTitle(){

    }
    public void testDeleteBroadcastsVideo(){

    }
    public void testPlayVideoViewers()
    {

    }
    public void testComments(){

    }
    @Test
    public void testZan() throws UiObjectNotFoundException, IOException {

        clickByText("Me");
        clickByText("Broadcasts");
        gDevice.wait(Until.gone(By.res(Me.BROADCAST_VIEW)),20000);

        getObjectById(Me.BROADCAST_VIEW,0).swipeLeft(2);


        UiObject2 listView = gDevice.findObject(By.res(Me.BROADCAST_VIEW));
        List<UiObject2> lisCollect = gDevice.findObjects(By.clazz(TextView.class));
        for (UiObject2 ui:lisCollect) {
            makeToast(ui.getText(),1);
        }

        listView.swipe(Direction.LEFT, 0.8f, 3000);
        listView.scroll(Direction.DOWN, 0.8f, 3000);

        /*clickById(Me.BROADCAST_VIEW,1);
        getObjectById(Me.BROAD_TIP_TEXT).setText("test");

        int w = gDevice.getDisplayWidth()-100;
        int h =gDevice.getDisplayHeight()-120;*/
        //gDevice.click(w,h);
    }

}
