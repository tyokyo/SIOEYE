package ckt.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;


public class ImageTools {
    /**
     * 读取图片文件
     *
     * @param filePath
     * @return
     */
    public static Bitmap readImage(String filePath) {


        Bitmap  image = BitmapFactory.decodeFile(filePath);
        System.out.println("BBBBBBB+" + image.toString());
        return image;
    }

    /**
     * 获得信息指纹
     *
     * @param pixels 灰度值矩阵
     * @param avg    平均值
     * @return 返回01串的十进制表示
     */
    public static int getFingerPrint(double[][] pixels, double avg) {

        int width = pixels[0].length;
        int height = pixels.length;
        byte[] bytes = new byte[height * width];
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixels[i][j] >= avg) {
                    bytes[i * height + j] = 1;
                } else {
                    bytes[i * height + j] = 0;
                }
            }
        }
        int fingerprint = 0;
        for (int i = 0; i < bytes.length; i++) {
            fingerprint += (bytes[bytes.length - i - 1] << i);
        }
        return fingerprint;
    }

    /**
     * This method is use for reduce picture size.
     *
     * @param image
     * @param w
     * @param h
     * @return
     */
    public static Bitmap reduceSize(Bitmap image, int w,
                                    int h) {

        Bitmap BitmapOrg = image;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    /**
     * 得到平均值
     *
     * @param pixels 灰度值矩阵
     * @return
     */
    public static double getAverage(double[][] pixels) {
        int width = pixels[0].length;
        int height = pixels.length;
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                count += pixels[i][j];
            }
        }
        return count / (width * height);
    }

    /**
     * 得到灰度值
     *
     * @param image
     * @return
     */
    public static double[][] getGrayValue(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][] pixels = new double[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = computeGrayValue(image.getPixel(i, j));
            }
        }
        return pixels;
    }

    /**
     * This method is use fot get the picture Gray Value.
     *
     * @param pixel
     * @return
     */
    public static double computeGrayValue(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = (pixel) & 255;
        return 0.3 * red + 0.59 * green + 0.11 * blue;
    }
}
