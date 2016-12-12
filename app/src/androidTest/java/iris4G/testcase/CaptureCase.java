package iris4G.testcase;

import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Asst;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.logging.Logger;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;
import iris4G.page.NavPage;

/**
 * @Author elon
 * @Description
 * 所有照片质量[4M169|3M43|2M169]
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 16)
public class CaptureCase extends VP2{
    Logger logger = Logger.getLogger(CaptureCase.class.getName());
    @Before
    public void setup() throws Exception {
        Iris4GAction.initIris4G();
    }
    private void  Photo(String imageSize,double expectWH) throws Exception {
        //CameraAction.configImageSize("4M(16:9)");
        CameraAction.configImageSize(NavPage.navConfig_Capture,imageSize);

        String screen_size = imageSize.substring(0,2);
        //更改成功，相机左上角显示?M
        Asst.assertEquals(imageSize,screen_size,getTex(Iris4GPage.info).trim());

        HashSet<String> beforeTakePhotoList = Iris4GAction.FileList("/sdcard/Photo");
        Iris4GAction.cameraKey();
        waitTime(3);
        HashSet<String> afterTakePhotoList = Iris4GAction.FileList("/sdcard/Photo");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakePhotoList, beforeTakePhotoList);
        if (resultHashSet.size()==1) {
            String photoPath = resultHashSet.iterator().next();
            double activeWH = Iris4GAction.getPicHeightWidth(photoPath);
            //double expectWH= 16/9;
            if (activeWH==expectWH) {
                logger.info(photoPath+" -take picture success");
            }else {
                logger.info(photoPath+" -take picture fail");
                Asst.fail("Photo-Width error");
            }
        }else {
            Asst.fail("Photo not exist");
        }
    }
    @Test
    public void testPhoto4M169() throws Exception {
        Photo(NavPage.imageSize4M,16/9);
    }
    @Test
    public void testPhoto3M43() throws Exception {
        Photo(NavPage.imageSize3M,4/3);
    }
    @Test
    public void testPhoto2M169() throws Exception {
        Photo(NavPage.imageSize2M,16/9);
    }

    @Test
    public void testPhoto8M169() throws Exception {
        Photo(NavPage.imageSize8M,4/3);
    }
    @Test
    public void testSwitchOver() throws Exception {
        Photo(NavPage.imageSize4M,16/9);
        Photo(NavPage.imageSize3M,4/3);
        Photo(NavPage.imageSize2M,16/9);
        Photo(NavPage.imageSize8M,4/3);
    }
    /**
     * 设置照片尺寸过程中按电源键
     * 第一次熄屏，第二次按仍然停留在照片尺寸切换界面
     * */
    @Test
    public void testSetSizeToPower() throws Exception {
        CameraAction.navConfig(NavPage.navConfig_Capture);
        CameraAction.cameraSetting();
        Iris4GAction.clickByText("Image Size");
        Iris4GAction.ScrollViewByText(NavPage.imageSize2M);
        Iris4GAction.powerKey();
        waitTime(2);
        Iris4GAction.powerKey();
        waitTime(2);
        clickByText(NavPage.imageSize2M);
    }
}
