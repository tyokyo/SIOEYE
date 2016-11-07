package usa.testcase.discover;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ckt.base.VP;
import ckt.base.VP2;
import usa.page.App;
import usa.page.Discover;

/**
 * Created by caibing.yin on 2016/11/7.
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class CountPersonCase extends VP2 {
    @Before
    public void setup() {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
    }
    /**
     * case9:
     *历史观看人数显示
     *1.检查历史观看人数显示字体颜色尺寸位置
     *  Result：与给出的UI符合(不做判断，先写如何得到观看人数)
     */
    @Test
    public void testCountPerson(){
        clickById(Discover.ID_MAIN_TAB_DISCOVER);
        waitTime(2);
      // gDevice Relativelayout = getUiObjectByText("android.widget.RelativeLayout").;
    }

    /**
     * case10:
     *历史观看人数统计
     *1.反复进入退出视频播放界面后回到封面检查历史观看人数
     *  Result：每次进入播放视频界面后退出视频封面界面，历史观看人数加1
     * */

}
