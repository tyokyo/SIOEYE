package iris4G.testcase;

import org.hamcrest.Asst;

import java.util.HashSet;

import ckt.base.VP2;
import iris4G.action.CameraAction;
import iris4G.action.Iris4GAction;

/**
 * @Author
 * @Description
 */
/*所有照片质量*/
public class PhotoCase extends VP2{
    /*testPhoto4M169()
testPhoto3M43()
testPhoto2M169()*/
    public void testPhoto4M169() throws Exception {
        CameraAction.configImageSize("4M(16:9)");
        HashSet<String> beforeTakePhotoList = Iris4GAction.FileList("/sdcard/Photo");
        Iris4GAction.cameraKey();
        waitTime(3);
        HashSet<String> afterTakePhotoList = Iris4GAction.FileList("/sdcard/Photo");
        HashSet<String> resultHashSet = Iris4GAction.result(afterTakePhotoList, beforeTakePhotoList);
        if (resultHashSet.size()==1) {
            String photoPath = resultHashSet.iterator().next();
            double hw = Iris4GAction.getPicHeightWidth(photoPath);
            double exp= 16/9;
            if (hw==exp) {
                logger.info(photoPath+" -拍照成功");
            }else {
                logger.info(photoPath+" -拍照失败");
                Asst.fail("Photo-Width error");
            }
        }else {
            Asst.fail("Photo not exist");
        }
    }
}
