/**
 * ImageLib,图像相关的操作都放置到这里面来。
 */

package ckt.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertTrue;

public class ImageLib {


    // 转换格式并重命名
    public static String savePicture(Bitmap bitmap, String FileName) {
        // FileOutPut保存文件的类
        FileOutputStream out = null;

        String newName = FileName + getTimetoString();
        // 定义图片的路劲
        String storePath = Environment.getExternalStorageDirectory() + "/TempFiles/";
        String newPath = storePath + newName + ".jpg";
        // 检查需要的目录是否存在，如果不存在则创建
        if (!new File(storePath).isDirectory()) {
            assertTrue(new File(storePath).mkdirs());
        }
        // 用try来处理IO的异常
        try {
            // 指定保存的路径和后缀名
            out = new FileOutputStream(newPath);
            if (out != null) {
                // 图片格式的转换
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                out.close();
                // System.out.println("The file storage at:" + newPath);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return newPath;

    }

    /**
     * @param FileName 要保存的文件名字
     */

    public static String TakeScreen(String FileName) {
        // 设置文件名
        String newName = FileName + getTimetoString();
        // 定义文件的路径
        String storePath = Environment.getExternalStorageDirectory() + "/ScreenShort/";

        String newPath = storePath + newName + ".jpg";

        // 检查需要的目录是否存在，如果不存在则创建
        if (!new File(storePath).isDirectory()) {
            boolean dirstatus = new File(storePath).mkdir();
            assertTrue("创建目录失败", dirstatus);
        }
        File createfile = new File(newPath);
        if (UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).takeScreenshot(createfile)) {
            return newPath;
        }
        return null;

    }

    /**
     * @param x    X坐标
     * @param y    Y坐标
     * @param path 需要采样的图片
     * @return 结果，真/假
     * @Description 用来获取某个点的颜色值
     */
    public static int getColorPicel(int x, int y, String path) {

        Bitmap mBitmap = BitmapFactory.decodeFile(path);
        int color = mBitmap.getPixel(x, y);
        return color;

    }

    // 直接截图一个区域

    /**
     * @param rect 要截取的区域坐标
     */
    public static String getPircureZone(Rect rect) {

        // 指定图片存储的路径和文件名
        String path = Environment.getExternalStorageDirectory() + "/" + getTimetoString() + ".jpg";
        // 新建一个File对象
        File storePath = new File(path);
        // 截取一张图片,并保存在SD卡根目录下。
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).takeScreenshot(storePath);        //System.out.println("截图成功");
        // 创建Bitmap对象
        Bitmap mbitmap = BitmapFactory.decodeFile(path);
        // 从Bitmap对象中截取指定的区域。
        mbitmap = Bitmap.createBitmap(mbitmap, rect.left, rect.top,
                rect.width(), rect.height());

        // 保存图片
        String newPath = savePicture(mbitmap, "");

        // 删除截取的图片
        assertTrue(storePath.delete());
        return newPath;

    }

    // 对比两个图像的相似度

    /**
     * @param targetImagepath 原始图片
     * @param compath         用来比较的图片
     * @param percent         相似的比例
     * @return true/false
     * @Description:用来比较两张图片相同，相同返回true，不相同返回false
     */
    public static boolean sameAs(String targetImagepath,
                                 String compath, double percent) {

        try {
            // 通过BitmapFactory来获取两个要对比的图片
            Bitmap mBitmap1 = BitmapFactory.decodeFile(targetImagepath);
            Bitmap mBitmap2 = BitmapFactory.decodeFile(compath);
            // 获取对比图像的宽度和高度
            int width = mBitmap2.getWidth();
            int height = mBitmap2.getHeight();
            // 默认的像素点
            int numDiffPixels = 0;
            // 循环对比X轴和Y轴上的像素点是否相等
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // 找出图片里不相等的像素。
                    if (mBitmap2.getPixel(x, y) != mBitmap1.getPixel(x, y)) {
                        numDiffPixels++;
                    }
                }
            }
            // 计算出图片中所包含的像素
            double totalPicels = height * width;
            // 计算出不相等的像素个数
            double diffPercent = numDiffPixels / totalPicels;
            // 减去不相等的百分比，得到相似度。
            System.out.println("Dff" + diffPercent);
            return percent <= 1.0 - diffPercent;

        } catch (Exception e) {

        }

        return false;
    }

    public static boolean sameAs(String originFilePath, String comparedFilePath) {
        int result = getPictureCompareResult(originFilePath, comparedFilePath);
        if (result > 5) {
            //System.out.println("AAAAAAAAAA" + result);
            return false;

        }
        //System.out.println("AAAAAAAAAA" + result);
        return true;
    }

    /**
     * This method is use for get the picture compare Result.the result is a number.
     * if result >=10, means the two image is total different.
     * if result >=7 && result <10 means, the two image most part is different,but has a little same.
     * if result >=3 && result<7 means, The two image most part is same,has a  little different.
     * If result >=1 && result <3 means, May be the two image is same.
     * if result ==0 means, the two image is same.
     *
     * @param originFilePath   The Origin File path,if you picture location at Root of sdcard,you just need the file name,eg:example.jpg.
     * @param comparedFilePath The Compared File path.
     * @return The result is form 0 to 10.
     */
    public static int getPictureCompareResult(String originFilePath, String comparedFilePath) {

        Bitmap origin = ImageTools.readImage(originFilePath);// Read the origin image.
        int orgin_fingerprint = getFingerPrint(origin);// get the orgin fingerprint.

        Bitmap compared_img = ImageTools.readImage(comparedFilePath);// read the
        int compared_fingerprint = getFingerPrint(compared_img);

        int different_num = compareFingerPrint(orgin_fingerprint,compared_fingerprint);
        return different_num;

    }

    // 截取某一区域，并和目标图片进行对比

    /**
     * @param comPath 需要对比的图片位置
     * @param x       x坐标
     * @param top     x点的y坐标
     * @param y       Y坐标
     * @param bottom  Y点的X坐标
     * @Description：截取图片中的一个区域，然后和目标图片进行对比， 注意：坐标格式为：矩形两个点对角的坐标.
     */
    public static boolean cutAndcompare(String comPath, int x, int top, int y,
                                        int bottom) {

        Rect rect = new Rect(x, top, y, bottom);
        String targetimagePath = ImageLib.getPircureZone(rect);

        boolean b = ImageLib.sameAs(targetimagePath, comPath, 0.8d);
        // System.out.println("图片对比结果是：" + b);
        return b;
    }

    // 得到一个时间的字符串

    public static String getTimetoString() {
        SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        // 设置时间对象的格式
        String Stime = time.format(date);
        return Stime;

    }

    /**
     * @param path   对比图片的路径
     * @param x      矩形区域的x坐标
     * @param top    x点的顶点坐标
     * @param y      矩形的Y坐标
     * @param bottom Y顶点的bottom坐标
     * @param min    下一次对比的时间（分）
     * @throws UiObjectNotFoundException
     * @Description:截取一个图片区域并进行比较
     */
    public static void imagecutandcompare(String path, int x, int top,
                                          int y, int bottom, int min) throws UiObjectNotFoundException {

        for (; ; ) {
            if (ImageLib.cutAndcompare(path, x, top, y, bottom) == true) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }

                break;
            } else {
                try {
                    Thread.sleep(min * 60000);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
                continue;
            }

        }


    }


    /**
     * @param image
     * @return
     */
    private static int getFingerPrint(Bitmap image) {
        final int WIDTH = 8;
        final int HEIGHT = 8;
        image = ImageTools.reduceSize(image, WIDTH, HEIGHT);// reduce image
        // size.
        double[][] pixels = ImageTools.getGrayValue(image);// get the image gary
        // value.
        double avg = ImageTools.getAverage(pixels);// get the image average
        // pixels.
        int fingerprint = ImageTools.getFingerPrint(pixels, avg);// get the
        // fingerprint.
        return fingerprint;
    }

    private static int compareFingerPrint(int orgin_fingerprint,
                                          int compared_fingerprint) {
        int count = 0;
        for (int i = 0; i < 64; i++) {
            byte orgin = (byte) (orgin_fingerprint & (1 << i));
            byte compared = (byte) (compared_fingerprint & (1 << i));
            if (orgin != compared) {
                count++;
            }
        }
        return count;
    }


}
