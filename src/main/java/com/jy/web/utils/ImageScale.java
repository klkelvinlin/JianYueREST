package com.jy.web.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;

public class ImageScale {

	private static final int destBigPicWidth = 570;
	private static final int destBigPicHeight = 380;
	private static final int destSmallPicWidth = 225;
	private static final int destSmallPicHeight = 150;

	public static void scaleImage(InputStream inputStream, String destFilePath,
			String destSmallFilePath) {
		BufferedImage destBigPic = null;
		BufferedImage destSmallPic = null;
		try {
			BufferedImage src = ImageIO.read(inputStream);
			if (src != null) {
				int srcWidth = src.getWidth();
				int srcHeight = src.getHeight();

				if (srcWidth == destBigPicWidth
						&& srcHeight == destBigPicHeight) {
					destBigPic = src;
				} else if (srcWidth <= destBigPicWidth
						&& srcHeight <= destBigPicHeight) {

					destBigPic = new BufferedImage(destBigPicWidth,
							destBigPicHeight, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = destBigPic.createGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, destBigPicWidth, destBigPicHeight);
					g.drawImage(src, (destBigPicWidth - srcWidth) / 2,
							(destBigPicHeight - srcHeight) / 2, srcWidth,
							srcHeight, null);
					g.dispose();
				} else {
					float scale = Math.min(destBigPicWidth * 1.0f / srcWidth,
							destBigPicHeight * 1.0f / srcHeight);
					ResampleOp resampleOp = new ResampleOp(
							(int) (srcWidth * scale), (int) (srcHeight * scale));
					resampleOp
							.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
					BufferedImage mediaBigPic = resampleOp.filter(src, null);

					int mediaWidth = mediaBigPic.getWidth();
					int mediaHeight = mediaBigPic.getHeight();

					destBigPic = new BufferedImage(destBigPicWidth,
							destBigPicHeight, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = destBigPic.createGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, destBigPicWidth, destBigPicHeight);
					g.drawImage(mediaBigPic,
							(destBigPicWidth - mediaWidth) / 2,
							(destBigPicHeight - mediaHeight) / 2, mediaWidth,
							mediaHeight, null);
					g.dispose();
				}

			} else {
				System.out.println("file not found...");
			}
			// step 2: generate the small pic
			if (destBigPic != null) {
				ResampleOp resampleOp = new ResampleOp(destSmallPicWidth,
						destSmallPicHeight);
				resampleOp
						.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
				destSmallPic = resampleOp.filter(destBigPic, null);
			}

			ImageIO.write(destBigPic, "JPG", new File(destFilePath));
			ImageIO.write(destSmallPic, "JPG", new File(destSmallFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static BufferedImage scaleImage(InputStream inputStream,
			String destFilePath) {
		BufferedImage destBigPic = null;
		// BufferedImage destSmallPic = null;
		try {
			BufferedImage src = ImageIO.read(inputStream);
			if (src != null) {
				int srcWidth = src.getWidth();
				int srcHeight = src.getHeight();

				int previewWidth = 320;
				int previewHeight = (int) (srcHeight * (((double) previewWidth) / srcWidth));

				if (srcWidth == previewWidth && srcHeight == previewHeight) {
					destBigPic = src;
				} else if (srcWidth <= previewWidth
						&& srcHeight <= previewHeight) {

					destBigPic = new BufferedImage(previewWidth, previewHeight,
							BufferedImage.TYPE_INT_RGB);
					Graphics2D g = destBigPic.createGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, previewWidth, previewHeight);
					g.drawImage(src, (previewWidth - srcWidth) / 2,
							(previewHeight - srcHeight) / 2, srcWidth,
							srcHeight, null);
					g.dispose();
				} else {
//					float scale = Math.min(previewWidth * 1.0f / srcWidth, previewHeight * 1.0f / srcHeight);
					float scale = 1.0f;
					ResampleOp resampleOp = new ResampleOp( (int) (srcWidth * scale), (int) (srcHeight * scale));
					resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal);
					BufferedImage mediaBigPic = resampleOp.filter(src, null);

					int mediaWidth = mediaBigPic.getWidth();
					int mediaHeight = mediaBigPic.getHeight();

					destBigPic = new BufferedImage(mediaWidth, mediaHeight,  BufferedImage.TYPE_INT_RGB);
//					destBigPic = new BufferedImage(previewWidth, previewHeight,  BufferedImage.TYPE_INT_RGB);
					Graphics2D g = destBigPic.createGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, srcWidth, srcHeight);
					g.drawImage(mediaBigPic, 0, 0, mediaWidth,mediaHeight, null);
//					g.fillRect(0, 0, previewWidth, previewHeight);
//					g.drawImage(mediaBigPic, (previewWidth - mediaWidth) / 2,
//							(previewHeight - mediaHeight) / 2, mediaWidth,
//							mediaHeight, null);
					g.dispose();
				}
				ImageIO.write(destBigPic, "JPG", new File(destFilePath));
				return destBigPic;
			} else {
				System.out.println("file not found...");
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static File rotateImage(String filename, int angle) {
        File file = new File(filename);
        if (file.exists() && file.canRead() && file.isFile()) {
        BufferedImage buffimg = null;
        try {
            buffimg = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int w = buffimg.getWidth();
        int h = buffimg.getHeight();
        BufferedImage tempimg = null;
        Graphics2D graphics2d = null;
        int type = buffimg.getColorModel().getTransparency();
        if (angle%360 == 0) {
            return file;
        } else if (angle%180 == 0) {
            tempimg = new BufferedImage(w, h, type);
            graphics2d = tempimg.createGraphics();
            graphics2d.rotate(Math.toRadians(angle), w / 2, h / 2);
        } else if (angle % 90 == 0) {
             tempimg = new BufferedImage(h, w, type);
             graphics2d = tempimg.createGraphics();
             angle = -angle;
             if(angle<0)
             graphics2d.rotate(Math.toRadians(angle), w/ 2, w / 2);
             else
             graphics2d.rotate(Math.toRadians(angle), h / 2, h / 2);
        } else {
            return file;
        }
        graphics2d.drawImage(buffimg, 0, 0, null);
        graphics2d.dispose();
        buffimg = tempimg;
        try {
        	ImageIO.write(buffimg, "JPG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffimg.flush();
        }
        return file;
   }
}
