package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
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
public class BurstUpToDown extends VP2{
    Logger logger = Logger.getLogger(BurstUpToDown.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }

}
