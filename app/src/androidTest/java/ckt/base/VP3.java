package ckt.base;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.logging.Logger;

import ckt.tools.Common;
import ckt.image.ImageTools;

import static junit.framework.Assert.assertTrue;

/**
 * Created by elon on 2016/9/6.
 */
public class VP3 extends  VP{
    // 截取制定范围的图片
    // 图片比较
    //spoon 截图
    //spoon 保持文件

    public static Logger logger = Logger.getLogger("VP3");

    // 转换格式并重命名
    private static String savePicture(Bitmap bitmap, String FileName) {
        // FileOutPut保存文件的类
        FileOutputStream out = null;
        String newName = FileName + Common.getTimeToString();
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
     * @return 保存的图片名字
     */
    public static String takeScreen(String FileName) {
        // 设置文件名
        String newName = FileName + Common.getTimeToString();
        String storePath = Environment.getExternalStorageDirectory() + "/ScreenShort/";
        String newPath = storePath + newName + ".jpg";

        // 定义文件的路径
        // 检查需要的目录是否存在，如果不存在则创建
        if (!new File(storePath).isDirectory()) {
            boolean status = new File(storePath).mkdir();
            assertTrue("创建目录失败", status);
        }
        File createFile = new File(newPath);
        if (gDevice.takeScreenshot(createFile)) {
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
    private static int getColorPicel(int x, int y, String path) {

        Bitmap mBitmap = BitmapFactory.decodeFile(path);
        int color = mBitmap.getPixel(x, y);
        return color;

    }
    // 直接截图一个区域
    /**
     * @param rect 要截取的区域坐标
     */
    public static String takeCutScreen(Rect rect) {
        String folder=Environment.getExternalStorageDirectory() + "/ScreenShort/";
        if (!new File(folder).isDirectory()) {
            boolean status = new File(folder).mkdir();
            assertTrue("create folder", status);
        }
        // 指定图片存储的路径和文件名
        String path = folder + Common.getTimeToString() + ".jpg";
        // 新建一个File对象
        File storePath = new File(path);
        // 截取一张图片,并保存在SD卡根目录下。
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).takeScreenshot(storePath);        //System.out.println("截图成功");
        // 创建Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        // 从Bitmap对象中截取指定的区域。
        bitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top,
                rect.width(), rect.height());
        // 保存图片
        String newPath = savePicture(bitmap, "");
        // 删除截取的图片
        storePath.delete();
        logger.info("cut rectangle picture:"+newPath);
        return newPath;
    }

    private static Bitmap convertToBitmap(String path, int x, int y, int h, int w) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        opts.inJustDecodeBounds = false;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createBitmap(weak.get(), x,y,w,h);
    }
    private static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);//png类型
        return baos.toByteArray();
    }
    // 直接截图一个区域
    /**
     * @param uiObject 要截取的对象
     */
    public static String takeCutScreen(UiObject uiObject) throws UiObjectNotFoundException{
        Rect rect = uiObject.getVisibleBounds();
        String folder=Environment.getExternalStorageDirectory() + "/ScreenShort/";
        if (!new File(folder).isDirectory()) {
            boolean status = new File(folder).mkdir();
            assertTrue("create folder", status);
        }
        String time = Common.getTimeToString();
        // 指定图片存储的路径和文件名
        String sourcePath = folder +time  + ".jpg";
        String cutPath = folder + "cut"+time + ".jpg";
        // 新建一个File对象
        File storePath = new File(sourcePath);
        // 截取一张图片,并保存在SD卡根目录下。
        gDevice.takeScreenshot(storePath);        //System.out.println("截图成功");
        // 创建Bitmap对象
        Bitmap bitmap = convertToBitmap(sourcePath,rect.left, rect.top,
                rect.bottom-rect.top,  rect.right-rect.left);
        byte[] bytes = Bitmap2Bytes(bitmap);
        // 保存图片
        Common.write(bytes,cutPath);
        logger.info("cut rectangle picture:"+cutPath);
        // 删除截取的图片
        //storePath.delete();
        return cutPath;
    }
    // 直接截图一个区域
    /**
     * @param uiObject 要截取的对象
     */
    public static String takeCutScreen(UiObject2 uiObject) throws UiObjectNotFoundException{
        Rect rect = uiObject.getVisibleBounds();
        String folder=Environment.getExternalStorageDirectory() + "/ScreenShort/";
        if (!new File(folder).isDirectory()) {
            boolean status = new File(folder).mkdir();
            assertTrue("create folder", status);
        }
        // 指定图片存储的路径和文件名
        String path = folder + Common.getTimeToString() + ".jpg";
        // 新建一个File对象
        File storePath = new File(path);
        // 截取一张图片,并保存在SD卡根目录下。
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).takeScreenshot(storePath);        //System.out.println("截图成功");
        // 创建Bitmap对象
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        // 从Bitmap对象中截取指定的区域。
        bitmap = Bitmap.createBitmap(bitmap, rect.left, rect.top,
                rect.width(), rect.height());
        // 保存图片
        String newPath = savePicture(bitmap, "");
        // 删除截取的图片
        storePath.delete();
        logger.info("cut rectangle picture:"+newPath);
        return newPath;
    }
    // 对比两个图像的相似度

    /**
     * @param targetImagePath 原始图片
     * @param comparePath         用来比较的图片
     * @return true/false
     * @Description:用来比较两张图片相同，相同返回true，不相同返回false
     */
    public static double takeSimilarDegree(String targetImagePath, String comparePath) {
        double diffPercent=1;
        try {
            // 通过BitmapFactory来获取两个要对比的图片
            Bitmap mBitmap1 = BitmapFactory.decodeFile(targetImagePath);
            Bitmap mBitmap2 = BitmapFactory.decodeFile(comparePath);
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
            diffPercent = numDiffPixels / totalPicels;
            // 减去不相等的百分比，得到相似度。
            System.out.println("Dff" + diffPercent);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return diffPercent;
    }

    /**
     * @param targetImagePath 原始图片
     * @param comparePath         用来比较的图片
     * @param percent         相似的比例
     * @return true/false
     * @Description:用来比较两张图片相同，相同返回true，不相同返回false
     */
    public static boolean sameAs(String targetImagePath, String comparePath, double percent) {
        try {
            // 通过BitmapFactory来获取两个要对比的图片
            Bitmap mBitmap1 = BitmapFactory.decodeFile(targetImagePath);
            Bitmap mBitmap2 = BitmapFactory.decodeFile(comparePath);
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
            e.printStackTrace();
        }
        return false;
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

    private static int compareFingerPrint(int orgin_fingerprint, int compared_fingerprint) {
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
// 截取某一区域，并和目标图片进行对比

    /**
     * @param comparePath 需要对比的图片位置
     * @param uiObject      截取的对象
     * @Description：截取图片中的一个区域，然后和目标图片进行对比
     */
    public static double takeCutCompareScreen(String comparePath,UiObject uiObject) throws  UiObjectNotFoundException {
        String targetTimeImagePath;
        targetTimeImagePath = takeCutScreen(uiObject);
        double sdegree = takeSimilarDegree(targetTimeImagePath,comparePath);
        logger.info("Two Picture Compare Result is :"+sdegree);
        // System.out.println("图片对比结果是：" + b);
        return sdegree;
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
        int origin_fingerprint = getFingerPrint(origin);// get the orgin fingerprint.

        Bitmap compared_img = ImageTools.readImage(comparedFilePath);// read the
        int compared_fingerprint = getFingerPrint(compared_img);

        int different_num = compareFingerPrint(origin_fingerprint,compared_fingerprint);
        return different_num;
    }
    public static Bitmap drawTextBitmap(Bitmap bitmap,String text){
        int x=bitmap.getWidth();
        int y=bitmap.getHeight();

        //创建一个比原来图片更大的位图
        Bitmap newBitmap=Bitmap.createBitmap(x, y+80, Bitmap.Config.ARGB_8888);
        Canvas canvans=new Canvas(newBitmap);
        Paint paint=new Paint();
        //在原图位置（0，0）叠加一张图片
        canvans.drawBitmap(bitmap, 0,0, paint);
        //画笔颜色
        paint.setColor(Color.parseColor("#FF0000"));
        paint.setTextSize(30);
        canvans.drawText(text, 20, y+55, paint);
        canvans.save(Canvas.ALL_SAVE_FLAG);
        canvans.restore();
        return newBitmap;
    }
    public static Bitmap drawTextRectBitmap(Bitmap bitmap,String text,Rect rect){
        int x=bitmap.getWidth();
        int y=bitmap.getHeight();

        //创建一个比原来图片更大的位图
        Bitmap newBitmap=Bitmap.createBitmap(x, y+80, Bitmap.Config.ARGB_8888);
        Canvas canvans=new Canvas(newBitmap);
        Paint paint=new Paint();
        //在原图位置（0，0）叠加一张图片
        canvans.drawBitmap(bitmap, 0,0, paint);
        //画笔颜色
        paint.setColor(Color.parseColor("#FF0000"));
        paint.setTextSize(30);
        canvans.drawText(text, 20, y+55, paint);

        int x1 = rect.left;
        int y1 = rect.top;
        //for  phone device not iris4G
        double w = rect.right-rect.left;
        double h = rect.bottom-rect.top;

        System.out.println(x1);
        System.out.println(y1);
        System.out.println(h);
        System.out.println(w);
        canvans.drawLine((float)x1,(float) y1,(float) (x1+w),(float) y1, paint);
        canvans.drawLine((float)x1,(float) y1,(float) (x1),(float) (y1+h), paint);
        canvans.drawLine((float)(x1+w),(float) (y1+h),(float) (x1+w),(float) y1, paint);
        canvans.drawLine((float)(x1+w),(float) (y1+h),(float) (x1),(float) (y1+h), paint);
        canvans.save(Canvas.ALL_SAVE_FLAG);
        canvans.restore();
        return newBitmap;
    }

    public static void takeScreenshotAndDrawText(String path, String imageNamePath, String text){
        initDevice();
        File file=new File(path);
        gDevice.takeScreenshot(file);
        Bitmap bitmap=BitmapFactory.decodeFile(path);
        Bitmap drawBitmap=drawTextBitmap(bitmap, text);
        saveBitMapToSdcard(drawBitmap, imageNamePath);
    }
    public void takeScreenshotAndDrawTextAndRect(String path,String imageNamePath,String text,Rect rect){
        initDevice();
        File file=new File(path);
        gDevice.takeScreenshot(file);
        Bitmap bitmap=BitmapFactory.decodeFile(path);
        Bitmap drawBitmap=drawTextRectBitmap(bitmap, text, rect);
        saveBitMapToSdcard(drawBitmap, imageNamePath);
    }
    public static void saveBitMapToSdcard(Bitmap bitmap,String imageNamePath){
        FileOutputStream out=null;
        try {
            //String fileToWrite = "/mnt/sdcard/"+newName+".jpg";
            String fileToWrite = imageNamePath;
            out=new FileOutputStream(fileToWrite);
            System.out.println(fileToWrite);
            if(out!=null){
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
