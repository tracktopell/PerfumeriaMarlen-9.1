/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 *
 * @author alfredo
 */
public class ImageIconGenerator {

	private ImageWriter jpegImageWriter;
	private ImageWriteParam param;
	private static final float jpegBestImageQuality = 1.0f;
	private static final float jpegMediumImageQuality = 0.5f;
	private static final float jpegLowImageQuality = 0.25f;
	private static final float jpegMinimunImageQuality = 0.1f;
	private static BufferedImage emptyTransparentImage;
	
	public ImageIconGenerator() {
		Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
		if (!writers.hasNext()) {
			throw new IllegalStateException("No JPEG writers found");
		}
		jpegImageWriter = (ImageWriter) writers.next();

		param = jpegImageWriter.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(jpegMinimunImageQuality);
	}

	public static BufferedImage getEmptyTransparentImage() {
		if(emptyTransparentImage == null){
//			try {
//				emptyTransparentImage=ImageIO.read(ImageIconGenerator.class.getResourceAsStream("/imgs/emptyTrasnparent.png"));
//			} catch (Exception ex) {
				int w=600;
				int h=600;
				int d=5;
				emptyTransparentImage =  new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
				Graphics2D g2D = (Graphics2D)emptyTransparentImage.getGraphics();
				g2D.setColor(Color.WHITE);
				g2D.fillRect(0, 0, w, h);
				
				g2D.setColor(Color.GRAY);
				
				for(int i=0;i<w;i+=d){
					for(int j=0;j<h;j+=d){
						if((i+j)%2==0){
							g2D.fillRect(i, j, d, d);
						}
					}
				}				
//			}
		}
		return emptyTransparentImage;
	}
	
	

	public byte[] writeScaledJpegImageFrom(String srcFile, int maxSize) throws IOException {
		BufferedImage src;
		int newImageW = 0;
		int newImageH = 0;
		
		src = ImageIO.read(new FileInputStream(srcFile));
		
		int w = src.getWidth();
		int h = src.getHeight();
		int targetImageHeight = maxSize;
		int targetImageWidth  = maxSize;

		if (h != targetImageHeight && h > w) {
			newImageH = targetImageHeight;
			newImageW = ((targetImageHeight * w) / h);			
		} else {
			newImageH = ((targetImageHeight * h) / w);
			newImageW = targetImageWidth;			
		}

		Image productoImgeMedRes = src.getScaledInstance(newImageW, newImageH, Image.SCALE_SMOOTH);
		BufferedImage outImg = new BufferedImage(newImageW, newImageH, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2ds = (Graphics2D) outImg.getGraphics();
		BufferedImage eti = getEmptyTransparentImage();
		g2ds.drawImage(eti, 0, 0, null);
		
		g2ds.drawImage(productoImgeMedRes, 0, 0, null);
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		jpegImageWriter.setOutput(ios);
		jpegImageWriter.write(null, new IIOImage(outImg, null, null), param);
		
		return baos.toByteArray();
	}
	
	public byte[] writeScaledPNGImageFrom(String srcFile, int maxSize) throws IOException {
		BufferedImage src;
		int newImageW = 0;
		int newImageH = 0;
		
		src = ImageIO.read(new FileInputStream(srcFile));
		
		int w = src.getWidth();
		int h = src.getHeight();
		int targetImageHeight = maxSize;
		int targetImageWidth  = maxSize;

		if (h != targetImageHeight && h > w) {
			newImageH = targetImageHeight;
			newImageW = ((targetImageHeight * w) / h);			
		} else {
			newImageH = ((targetImageHeight * h) / w);
			newImageW = targetImageWidth;			
		}

		Image productoImgMedRes = src.getScaledInstance(newImageW, newImageH, Image.SCALE_SMOOTH);
		BufferedImage outImg = new BufferedImage(newImageW, newImageH, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2ds = (Graphics2D) outImg.getGraphics();
		//g2ds.setColor(Color.WHITE);
		//g2ds.fillRect(0, 0, outImg.getWidth(), outImg.getHeight());
		g2ds.drawImage(productoImgMedRes, 0, 0, null);
		ByteArrayOutputStream baos= new ByteArrayOutputStream();
		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		//jpegImageWriter.setOutput(ios);
		//jpegImageWriter.write(null, new IIOImage(outImg, null, null), param);
		ImageIO.write(outImg, "png", ios);
		return baos.toByteArray();
	}
}
