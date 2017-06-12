package iris4G.action;

import android.support.test.uiautomator.UiObjectNotFoundException;

import ckt.base.VP2;
import iris4G.page.SettingPage;

/**
 * Created by yyun on 2017/6/11.
 */

public class SettingAction extends VP2 {
    public static void navToStorage() throws Exception {
        clickByText("Device");
        Iris4GAction.ScrollViewByText("Storage");
        clickByText("Storage");
    }
    public static float getUesd() throws UiObjectNotFoundException {
        String originalUsed=getTex(SettingPage.storage_used_tx);
        String originalUsedValue=originalUsed.substring(0,originalUsed.length()-1);
        float valueOriginalUsed=Float.valueOf(originalUsedValue).floatValue();
        logger.info("Used:"+valueOriginalUsed+" G");
        return valueOriginalUsed;
    }
    public static float getFree() throws UiObjectNotFoundException {
        String originalFree=getTex(SettingPage.storage_avial_tx);
        String originalFreeValue =originalFree.substring(0,originalFree.length()-1);
        float valuerOriginalFree=Float.valueOf(originalFreeValue).floatValue();
        logger.info("Free:"+valuerOriginalFree+" G");
        return valuerOriginalFree;
    }
    public static float floatAbs(float valueUpUsed,float valueOriginalUsed,float valueUpFree,float valuerOriginalFree){
        float result=valueUpUsed-valueOriginalUsed+valueUpFree-valuerOriginalFree;
        logger.info(valueUpUsed+"-"+valueOriginalUsed+"+"+valueUpFree+"-"+valuerOriginalFree+"="+result);
        float resultAbS=Math.abs(result);
        logger.info("resultAbS："+resultAbS);
        return resultAbS;
    }
}
