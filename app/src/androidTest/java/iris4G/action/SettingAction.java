package iris4G.action;

import com.squareup.spoon.Spoon;

import ckt.base.VP2;
import iris4G.page.Iris4GPage;

/**
 * Created by elon on 2016/12/8.
 */
public class SettingAction extends VP2{
    public static void clickLiveAndSave() throws Exception {
        CameraAction.navConfig(Iris4GPage.nav_menu[0]);
        CameraAction.cameraSetting();
        Iris4GAction.ScrollViewByText("Live&Save");
        CameraAction.openCompoundButton("Live&Save");
        Spoon.screenshot("live_save","liveSave");
        gDevice.pressBack();
    }
}
