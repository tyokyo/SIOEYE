package ckt.tools;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.UiWatcher;

import java.util.logging.Logger;

import ckt.base.VP2;

/**
 * Created by elon on 2016/11/30.
 */
public class Watcher extends VP2{
    private static Logger logger = Logger.getLogger(Watcher.class.getName());
    public static UiWatcher watcherPermission(){
        UiWatcher watcher = new UiWatcher() {
            @Override
            public boolean checkForCondition() throws UiObjectNotFoundException {
                // TODO Auto-generated method stub
                //logger.info("start to run watcherPermission");
                if ("com.android.packageinstaller".equals(gDevice.getCurrentPackageName())) {
                    logger.info("watcherPermission matched");
                    //权限请求确认-pkg com.google.android.packageinstaller
                    if (id_exists(PERMISSION_ALLOW)) {
                        clickById(PERMISSION_ALLOW);
                        logger.info("click Allow permission");
                    }
                    return true;
                }
                return false;
            }
        };
        return watcher;
    }
}
