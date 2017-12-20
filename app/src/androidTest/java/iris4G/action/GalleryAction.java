package iris4G.action;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import com.squareup.spoon.Spoon;

import org.junit.Assert;

import java.util.List;
import java.util.Random;

import ckt.base.VP2;
import iris4G.page.GalleryPage;

/**
 * Created by user on 2017/12/06   .
 */
public class GalleryAction extends VP2 {
    public static Boolean checkLiveBottom() throws Exception {
        waitTime(2);
        if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_live_bottom)).exists()) {
            clickByPonit(60,60);;//点击屏幕
            waitTime(2);
            if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_live_bottom)).exists()){
                Spoon.screenshot("absentLiveBottom");
                return false;
            }
            return true;
        }
        return true;
    }
    /*
    检查相册发起直播，发起成功后即停止
     */
    public static Boolean makeGalleryLive() throws UiObjectNotFoundException {
        if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_delete_bottom)).exists()){
            clickByPonit(60,60);
        }
        clickById(GalleryPage.gallery_live_bottom);
        waitTime(2);
        clickById(GalleryPage.gallery_live);
        waitTime(1);
        clickById(GalleryPage.gallery_skip);
        waitUntilFindText("broadcasting",15000);
        if (!text_exists("broadcasting")){
            clickById(GalleryPage.gallery_live_bottom);
            waitUntilFindText("broadcasting",15000);
            if (!text_exists("broadcasting")){
                return false;
            }
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);//结束直播
            clickByText("Yes");
            gDevice.pressBack();
            gDevice.pressBack();
            return true;
        }
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);//结束直播
        clickByText("Yes");
        gDevice.pressBack();
        gDevice.pressBack();
        return true;
    }
    /*
    发起相册直播，不停止；失败会尝试两次
     */
    public static void startGalleryLive() throws UiObjectNotFoundException {
        clickById(GalleryPage.gallery_live_bottom);
        waitTime(2);
        clickById(GalleryPage.gallery_live);
        waitTime(1);
        clickById(GalleryPage.gallery_skip);
        waitUntilFindText("broadcasting",15000);
        if (!text_exists("broadcasting")){
            clickById(GalleryPage.gallery_live_bottom);
            waitUntilFindText("broadcasting",15000);
            if (!text_exists("broadcasting")){
                Spoon.screenshot("GalleyLiveFailed");
            }
        }
    }
    /*
    结束相册直播
     */
    public static void stopGalleryLive() throws UiObjectNotFoundException {
        waitTime(1);
        if (text_exists("broadcasting")) {
            gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
            clickByText("Yes");
            gDevice.pressBack();
            gDevice.pressBack();
        }
        if (text_exists("Confirm to exit live?")){
            clickByText("Yes");
            gDevice.pressBack();
            gDevice.pressBack();
        }
        if (text_exists("Already Lived")){
            logger.info("galleryLiveHasStopped");
        }
    }
    /*
    在相册直播中检查按键功能
     */
    public static void checkKeyDuringGalleryLive() throws UiObjectNotFoundException, RemoteException {
        if (!text_exists("broadcasting")) {
            Spoon.screenshot("GalleryLiveHasStopped");
            startGalleryLive();
        }
        gDevice.pressBack();//返回键弹出结束提示
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        gDevice.pressBack();//返回键消除结束直播提示
        if (gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse2DuringGalleryLive");}
        gDevice.pressBack();
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        clickByText("No");//cancel
        if (!text_exists("broadcasting")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        gDevice.pressBack();
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        clickByText("Yes");//cancel
        waitTime(2);
        if (!text_exists("Already Lived")){
            Assert.fail("backKeyStopGalleryLive");}
        startGalleryLive();
        gDevice.pressMenu();//menu键
        if (!text_exists("broadcasting")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        gDevice.pressKeyCode(KeyEvent.KEYCODE_CAMERA);
        if (!gDevice.findObject(new UiSelector().text("Confirm to exit live?")).exists()){
            Assert.fail("backKeyNoResponse1DuringGalleryLive");}
        clickByText("No");//cancel
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(1);
        if (gDevice.isScreenOn()) {
            Assert.fail("powerKeyOffFailed");}
        gDevice.pressKeyCode(KeyEvent.KEYCODE_POWER);
        waitTime(1);
        if (!gDevice.isScreenOn()) {
            Assert.fail("powerKeyOnFailed");}
        gDevice.pressKeyCode(276);//S键
        if (!text_exists("Confirm to exit live?")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        clickByText("No");//cancel
        gDevice.pressKeyCode(276);
        if (!text_exists("Confirm to exit live?")) {
            Assert.fail("cancelStopGalleryLiveFailed");}
        clickByText("Yes");//通过S键结束
    }

    /*
    检查相册中位于第一和第二的两个视频的live标志，第一个应该没有直播标志，第二个有，并且发起相册直播
     */
    public static void checkTowVideoLiveButtonAndLive() throws Exception {
        Iris4GAction.startGallery();
        if (!GalleryAction.checkLiveBottom()){
            getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);
            if (GalleryAction.checkLiveBottom()){
                if (!GalleryAction.makeGalleryLive()){
                    Assert.fail("makeGalleryLiveFailed");
                }
            }else {Assert.fail("VideoMoreThan10sButAbsentLiveBottom");}
        }else {Assert.fail("VideoLessThan10sButExistLiveBottom");}
    }
    //如720P25FPS右边“支持直播”的标志 -  右边的值
    public static Boolean checkResolutionRightString(String resolution) throws Exception {
        Iris4GAction.ScrollViewByText(resolution);
        String resolutionRightString = "";
        gDevice.wait(Until.findObject(By.clazz(android.widget.ListView.class)), 10000);
        UiObject2 scrollView = getObject2ByClass(android.widget.ListView.class);
        List<UiObject2> relativeLayouts = scrollView.findObjects(By.clazz(android.widget.RelativeLayout.class));
        for (int a=0;a < relativeLayouts.size();a++) {
            UiObject2 relativeLayout = relativeLayouts.get(a);
            List<UiObject2> texts = relativeLayout.findObjects(By.clazz(android.widget.TextView.class));
            logger.info("texts.size():"+texts.size());
            if (texts.size() == 2) {
                String key = texts.get(0).getText();
                logger.info("keyIs:"+key);
                if (key.equals(resolution)) {
                    resolutionRightString = texts.get(1).getText();
                    logger.info("valueIs:"+resolutionRightString);
                    if (resolutionRightString.equals("support live")){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /*
    获取相册中右上角，字符串第几张和总数之间 间隔在该字符串中第几位；用来后期获取总数；第几张数据
     */
    public static int getGalleryIndex() throws UiObjectNotFoundException {
        String indexText=getTex(GalleryPage.gallery_indexText);
        int rankingInt=0;
        char indexTextB[]=indexText.toCharArray();
        for (int i=0;i<indexText.length();i++){
            if (indexTextB[i]=='/'){
                rankingInt=i;
                return rankingInt;
            }
        }
        return rankingInt;
    }
    public static int getRankOfGallery() throws UiObjectNotFoundException {
        String indexText=getTex(GalleryPage.gallery_indexText);
        int rank=Integer.valueOf(indexText.substring(0,getGalleryIndex()));
        logger.info("rank:"+rank);
        return rank;
    }
    public static int getTotalOfGallery() throws UiObjectNotFoundException {
        String indexText=getTex(GalleryPage.gallery_indexText);
        int total=Integer.valueOf(indexText.substring(getGalleryIndex()+1,indexText.length()));
        logger.info("total:"+total);
        return total;
    }
    public static void deleteOneVideo(){
        if (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_delete_bottom)).exists()){
            clickByPonit(60,60);
        }
        clickById(GalleryPage.gallery_delete_bottom);
        waitTime(1);
        if (gDevice.findObject(new UiSelector().text("Do you want to delete this video ?")).exists()){
            clickByText("Yes");
        }else {
            Spoon.screenshot("deletePromptError");
            Assert.fail("deletePromptError");
        }
    }
    public static String getRandomBitrate(int min,int max){
        int randomInt=new Random().nextInt(max-min)+min;
        String random= String.valueOf(randomInt);
        logger.info("randomInt"+randomInt);
        logger.info("random:"+random);
        return random;
    }
    public static int getGalleryBitrateMin() throws UiObjectNotFoundException {
        String bitrateTips=getTex(GalleryPage.gallery_bitrate_tips);
        logger.info("bitrateMin:"+bitrateTips.substring(19,getAppointCharRankInString('k',bitrateTips)));
        logger.info("bitMinInt:"+Integer.parseInt(bitrateTips.substring(19,getAppointCharRankInString('k',bitrateTips))));
        return Integer.parseInt(bitrateTips.substring(19,getAppointCharRankInString('k',bitrateTips)));
    }
    public static int getGalleryBitrateMax() throws UiObjectNotFoundException {
        String bitrateTips=getTex(GalleryPage.gallery_bitrate_tips);
        logger.info("bitrateMaxInt: "+Integer.parseInt(bitrateTips.substring(getAppointCharRankInString('~',bitrateTips)+1,bitrateTips.length()-5)));
        return Integer.parseInt(bitrateTips.substring(getAppointCharRankInString('~',bitrateTips)+1,bitrateTips.length()-5));
    }
    public static int getAppointCharRankInString(char appointChar,String appointString){
        int rankingInt=0;
        char indexTextB[]=appointString.toCharArray();
        for (int i=0;i<appointString.length();i++){
            if (indexTextB[i]==appointChar){
                rankingInt=i;
//                logger.info("rankingTnt:"+rankingInt);
                return rankingInt;
            }
        }
        return rankingInt;
    }
    public static void navToGalleryBitrateSetting() throws Exception {
        Iris4GAction.startGallery();
        while (!gDevice.findObject(new UiSelector().resourceId(GalleryPage.gallery_live_bottom)).exists()) {
            getObjectById(GalleryPage.gallery_root_view).swipeLeft(60);
            waitTime(1);
        }
        clickById(GalleryPage.gallery_live_bottom);
        clickById(GalleryPage.gallery_live);
    }

}
