package iris4G.testcase;

import org.hamcrest.Asst;
import org.junit.Test;

import java.util.HashSet;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;
import iris4G.page.Iris4GPage;

/**
 * @Author elon
 * @Description
 * 所有照片质量[4M169|3M43|2M169]
 */
public class PhotoCase extends VP2{
    /*testPhoto4M169()
testPhoto3M43()
testPhoto2M169()*/
    private static void  Photo(String imageSize,double expectWH) throws Exception {
        //CameraAction.configImageSize("4M(16:9)");
        CameraAction.configImageSize(imageSize);
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
                logger.info(photoPath+" -拍照成功");
            }else {
                logger.info(photoPath+" -拍照失败");
                Asst.fail("Photo-Width error");
            }
        }else {
            Asst.fail("Photo not exist");
        }
    }
    @Test
    public void testPhoto4M169() throws Exception {
        Photo(Iris4GPage.imsge_size[0],16/9);
    }
    @Test
    public void testPhoto3M43() throws Exception {
        Photo(Iris4GPage.imsge_size[1],4/3);
    }

    @Test
    public void testPhoto2M169() throws Exception {
        Photo(Iris4GPage.imsge_size[2],16/9);
    }
}
