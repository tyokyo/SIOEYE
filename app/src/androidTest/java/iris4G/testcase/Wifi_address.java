package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.Iris4GAction;

/**
 * @Author caiBin
 * @Description
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class Wifi_address extends VP2{
    Logger logger = Logger.getLogger(Wifi_address.class.getName());
    @Before
    public void setup() throws Exception {
        initDevice();
    }
    @Test
    public void testA(){
        logger.info("-------testA");
    }
    @Test
    public void testB(){
        logger.info("-------testB");
    }
    @Test
    public void testC(){
        logger.info("-------testC");
    }
}

