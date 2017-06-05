package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.logging.Logger;

import ckt.base.VP2;

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
        logger.info("-------testAsA");
    }
    @Test
    public void testB(){
        logger.info("-------testBcv");
    }
    @Test
    public void testC(){
        logger.info("-------testC");
    }
}

