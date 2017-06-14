package pm.action;

import android.graphics.Point;
import android.graphics.Rect;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.squareup.spoon.Spoon;

import org.hamcrest.Asst;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import ckt.base.VP2;
import pm.page.App;
import pm.page.MePage;
import pm.page.WatchPage;


/**
 * Created by elon on 2016/11/7.
 */
public class WatchAction extends VP2 {
    public static Logger logger = Logger.getLogger(WatchAction.class.getName());

    /*
    视频的视频长度对应的-Object
    * */
    public static List<UiObject2> getDurationObjects(){
        List<UiObject2> dus=new ArrayList<>();
        List<UiObject2> frameLayouts= gDevice.findObjects(By.clazz(android.widget.FrameLayout.class));
        for (UiObject2 obj:frameLayouts){
            Rect obj_rect=obj.getVisibleBounds();
            logger.info("obj_rect-"+obj_rect.toString());
            int childCount = obj.getChildCount();
            List<UiObject2> children=obj.getChildren();
            if (children.size()==2){
                Rect c1=children.get(0).getVisibleBounds();
                Rect c2=children.get(1).getVisibleBounds();
                if (obj_rect.equals(c1)&&obj_rect.equals(c2)){
                    logger.info("c1-"+c1.toString());
                    logger.info("c2-"+c2.toString());
                    boolean duration=obj.hasObject(By.clazz(android.widget.TextView.class));
                    if (duration){
                        UiObject2 time=obj.findObject(By.clazz(android.widget.TextView.class));
                        dus.add(time);
                    }
                }
            }
            logger.info(dus.size()+"");
        }
        return  dus;
    }

    /**
     * 点击Watch界面任意主播的头像
     */
    public static void ClickImageObjects() {
        //取出所有的imageview
        List<UiObject2> LinearLayouts = gDevice.findObjects(By.clazz(android.widget.LinearLayout.class));
        //从imageviews中找出其父容器内有textview和lsinearlayout的需要的imageview，并且点击该对象
        for (UiObject2 obj : LinearLayouts) {
                //获取父容器
                if (obj.hasObject(By.clazz(android.widget.TextView.class).depth(1)) &&
                        obj.hasObject(By.clazz(android.widget.LinearLayout.class).depth(1))&&
                        obj.hasObject(By.clazz(android.widget.ImageView.class).depth(1))) {
                    obj.getChildren().get(0).click();
                    break;
                }
        }
        waitTime(5);
    }

    /**
     * 判断迷你界面是否打开
     */
    public static boolean isOpenedMiniPage() throws Exception {
        List<UiObject2> framelayouts = gDevice.findObjects(By.clazz(android.widget.FrameLayout.class));
        List<UiObject2> list =new ArrayList<>();
        for (UiObject2 object:framelayouts){
            if (object.hasObject(By.clazz(android.widget.FrameLayout.class))&&
                    object.hasObject(By.clazz(android.view.View.class))&&
                    object.hasObject(By.clazz(android.widget.ImageView.class))){
                UiObject2 imageView = object.findObject(By.clazz(android.widget.ImageView.class));
                list.add(imageView);
                break;
            }else if(object.hasObject(By.clazz(android.widget.FrameLayout.class))&&
                    object.hasObject(By.clazz(android.widget.ImageView.class))){
                UiObject2 imageView = object.findObject(By.clazz(android.widget.ImageView.class));
                list.add(imageView);
                break;
            }
        }
        if (list.size()==1&&list.get(0).getClassName().equals("android.widget.ImageView")){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 点击迷你界面的直播
     */
    public static void  clickMiniLive() throws UiObjectNotFoundException {
        UiObject radiogroup = gDevice.findObject(new UiSelector().className("android.widget.RadioGroup"));
        logger.info(radiogroup.toString());
        if (radiogroup.getChildCount()==3) {
            UiObject MiniLive = radiogroup.getChild(new UiSelector().className("android.widget.LinearLayout").instance(0));
            Asst.assertEquals(true, MiniLive.exists());
            if (MiniLive.getChildCount() == 2) {
                MiniLive.click();
            } else {
                Asst.assertEquals(true, MiniLive.exists());
            }
        }
    }
    /**
     * 点击迷你界面的关注
     */
    public static void  clickMiniFollow() throws UiObjectNotFoundException {
        UiObject radiogroup = gDevice.findObject(new UiSelector().className("android.widget.RadioGroup"));
        logger.info(radiogroup.toString());
        if (radiogroup.getChildCount()==3){
            UiObject MiniFollow = radiogroup.getChild(new UiSelector().className("android.widget.LinearLayout").instance(1));
            Asst.assertEquals(true,MiniFollow.exists());
            if (MiniFollow.getChildCount()==2){
                MiniFollow.click();
            }else{
                Asst.assertEquals(true,MiniFollow.exists());
            }
        }
    }
    /**
     * 点击迷你界面的粉丝
     */
    public static void  clickMiniFans() throws UiObjectNotFoundException {
        UiObject radiogroup = gDevice.findObject(new UiSelector().className("android.widget.RadioGroup"));
        if (radiogroup.getChildCount()==3){
            UiObject MiniFans = radiogroup.getChild(new UiSelector().className("android.widget.LinearLayout").instance(2));
            Asst.assertEquals(true,MiniFans.exists());
            MiniFans.click();
        }
    }
    /**
     * 检查mini界面直播视频列表是否显示
     */
    public static boolean LiveList_isExist() {
        List<UiObject2> linearlayouts = gDevice.findObjects(By.clazz(android.widget.LinearLayout.class));
        List<UiObject2> list = new ArrayList<>();
        for (UiObject2 object : linearlayouts) {
            if (object.getChildCount() == 4) {
                List<UiObject2> children = object.getChildren();
                if (children.get(0).getClassName().equals("android.widget.ImageView") &&
                        children.get(1).getClassName().equals("android.widget.TextView") &&
                        children.get(2).getClassName().equals("android.widget.ImageView") &&
                        children.get(3).getClassName().equals("android.widget.TextView")) {
                    list.add(object);
                }
            }
        }
        if (!list.isEmpty()){
            return true;
        }else{
            return  false;
        }
    }
    /**
     * 检查mini界面关注列表是否显示
     */
    public static boolean FollowList_isExist(){
        List<UiObject2> list = new ArrayList<>();
        UiObject NobodyFollow = gDevice.findObject(new UiSelector().text("没有关注任何人"));
        List<UiObject2> ralativelayouts = gDevice.findObjects(By.clazz(android.widget.RelativeLayout.class));
        for (UiObject2 obj:ralativelayouts){
            List<UiObject2> children = obj.getChildren();
            if (obj.getChildCount()==2){
                if (children.get(0).getClassName().equals("android.widget.ImageView")&&
                        children.get(1).getClassName().equals("android.widget.LinearLayout")){
                    list.add(obj);
                }
            }else if (obj.getChildCount()==3){
                if (children.get(0).getClassName().equals("android.widget.ImageView")&&
                        children.get(1).getClassName().equals("android.widget.LinearLayout")^
                                children.get(2).getClassName().equals("android.widget.ImageView")){
                    list.add(obj);
                }
            }
        }
        if (!list.isEmpty()||NobodyFollow.exists()){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 检查mini界面粉丝列表是否显示
     */
    public static boolean FansList_isExist(){
        List<UiObject2> list = new ArrayList<>();
        UiObject NobodyFollow = gDevice.findObject(new UiSelector().text("还没有粉丝，关注成为ta的第一个粉丝吧"));
        List<UiObject2> ralativelayouts = gDevice.findObjects(By.clazz(android.widget.RelativeLayout.class));
        for (UiObject2 obj:ralativelayouts){
            List<UiObject2> children = obj.getChildren();
            if (obj.getChildCount()==2){
                if (children.get(0).getClassName().equals("android.widget.ImageView")&&
                        children.get(1).getClassName().equals("android.widget.LinearLayout")){
                    list.add(obj);
                }
            }else if (obj.getChildCount()==3){
                if (children.get(0).getClassName().equals("android.widget.ImageView")&&
                        children.get(1).getClassName().equals("android.widget.LinearLayout")^
                                children.get(2).getClassName().equals("android.widget.ImageView")){
                    list.add(obj);
                }
            }
        }
        if (!list.isEmpty()||NobodyFollow.exists()){
            return true;
        }else {
            return false;
        }
    }
        /**
         *搜索指定sioeye id 的用户
         */
    public static void searchFollowingUser(String sioEyeId) throws IOException, UiObjectNotFoundException {
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);
        Point p = MeAction.getPointToDoComment();
        openAppByPackageName(App.SIOEYE_PACKAGE_NAME_USA);

        clickByText("Watch");
        clickById(WatchPage.WATCH_SEARCH_BTN);
        gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
        if (!getUiObjectById(WatchPage.WATCH_SEARCH_TRENDING_LIST).exists()){
            clickById(WatchPage.WATCH_SEARCH_FILTER_INPUT);
            waitTime(2);
            gDevice.click(p.x,p.y);
            gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
        }
        Spoon.screenshot(gDevice,"trending_list");
        clickById(WatchPage.WATCH_SEARCH_FILTER_INPUT);
        shellInputText(sioEyeId);
        gDevice.click(p.x,p.y);
        gDevice.wait(Until.gone(By.res(WatchPage.WATCH_SEARCH_DATA_LOADING)),60000);
        Spoon.screenshot(gDevice,"search_list_"+sioEyeId);
    }
    /**
     *关注->搜索
     */
    public static void navToWatchSearch(){
        clickById(MePage.ID_MAIN_TAB_LIVE);
        clickById(WatchPage.WATCH_SEARCH_BTN);
        waitTime(2);
        gDevice.wait(Until.findObject(By.res(WatchPage.WATCH_SEARCH_TRENDING_TITLE)),40000);
    }
    /**
     *关注->搜索
     */
    public static void navToWatch(){
        clickById(MePage.ID_MAIN_TAB_LIVE);
        waitTime(2);
    }
}
