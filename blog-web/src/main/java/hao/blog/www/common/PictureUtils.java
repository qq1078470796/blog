package hao.blog.www.common;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/*
 * 这是一个改变图片大小的实现类
 */
public class PictureUtils {
    /**
     *
     * @param srcImage 原文件目录
     * @param tarImage 新文件目录
     * @param changeWidth 新文件长
     * @param changeHeight 新文件宽
     */
    public static void compressPicture(String srcImage,String tarImage,int changeWidth,int changeHeight) {
        File srcImageFile = new File(srcImage);
        File tarImageFile = new File(tarImage);
        // 生成图片转化对象
        AffineTransform transform = new AffineTransform();
        // 通过缓存读入缓存对象
        BufferedImage image = null;
        try {
            image = ImageIO.read(srcImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int imageWidth = image.getWidth();//原图片的高度
        int imageHeight = image.getHeight();//原图片的宽度

        double scaleWidth = 0;// 定义小图片和原图片比例
        double scaleHeight = 0;
        scaleWidth = (double) changeWidth / (double) imageWidth;
        scaleHeight = (double) changeHeight / (double) imageHeight;
        // 生成转换比例
        transform.setToScale(scaleWidth, scaleHeight);
        // 生成转换操作对象
        AffineTransformOp transOp = new AffineTransformOp(transform, null);
        //生成压缩图片缓冲对象
        BufferedImage basll = new BufferedImage(changeWidth, changeHeight,
                BufferedImage.TYPE_3BYTE_BGR);
        //生成缩小图片
        transOp.filter(image, basll);
        try {
            //输出缩小图片
            ImageIO.write(basll, "jpg", tarImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
