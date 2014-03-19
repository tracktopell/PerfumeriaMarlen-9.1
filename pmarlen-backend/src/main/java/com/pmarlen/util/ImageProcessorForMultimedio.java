package com.pmarlen.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CrearImagenesDePruebaEnMultimedio
 */
public class ImageProcessorForMultimedio {

	private float jpegImageQuality = 1.0f;
	private int targetImageWidth = 1024;
	private int targetImageHeight = 768;
	private int targetMediumImageWidth = 320;
	private int targetMediumImageHeight = 240;
	private int targetMinimunImageWidth = 160;
	private int targetMinimunImageHeight = 120;
	private int targetIconImageWidth = 20;
	private int targetIconImageHeight = 15;
	private double targetAspectRatio = (double) targetImageWidth / (double) targetImageHeight; // 4/3 = 1.333
	private final Logger logger = LoggerFactory.getLogger(ImageProcessorForMultimedio.class);
	private static ImageWriter jpegImageWriter = null;
	private static ImageWriteParam param = null;
	private static ImageProcessorForMultimedio instance;

	private ImageProcessorForMultimedio() {
		// Get a ImageWriter for jpeg format.
		Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
		if (!writers.hasNext()) {
			throw new IllegalStateException("No JPEG writers found");
		}
		jpegImageWriter = (ImageWriter) writers.next();

		param = jpegImageWriter.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(jpegImageQuality);

	}

	public static ImageProcessorForMultimedio getInstance() {
		if (instance == null) {
			instance = new ImageProcessorForMultimedio();
		}
		return instance;
	}

	public byte[] transformImage(byte[] originalDate) throws IOException {
		BufferedImage originalImage = null;
		BufferedImage result = null;
		Image imageScalled = null;

		originalImage = ImageIO.read(new ByteArrayInputStream(originalDate));

		int w = originalImage.getWidth();
		int h = originalImage.getHeight();

		int newImageW = 0;
		int newImageH = 0;
		double aspectRatio = (double) w / (double) h;

		System.err.println("\t====> Image: size:" + w + "x" + h + " = (" + aspectRatio + ") => " + targetImageWidth + "x" + targetImageHeight);


		if (aspectRatio > 1.0) {
			if (aspectRatio < targetAspectRatio) {
				newImageW = ((targetImageHeight * w) / h);
				newImageH = targetImageHeight;
			} else {
				newImageW = targetImageWidth;
				newImageH = ((targetImageHeight * h) / w);
			}
			System.err.println("\t\tAdjusting W|H: resize =>> " + newImageW + "x" + newImageH);
		} else if (aspectRatio < 1.0) {
			newImageW = ((targetImageHeight * w) / h);
			newImageH = targetImageHeight;
			System.err.println("\t\tAdjusting W: resize =>> " + newImageW + "x" + newImageH);
		} else {
			newImageW = targetImageWidth;
			newImageH = targetImageHeight;

			System.err.println("\t\tAdjusting like zoom [W=H=]: resize =>> " + newImageW + "x" + newImageH);
		}

		imageScalled = originalImage.getScaledInstance(newImageW, newImageH, Image.SCALE_SMOOTH);
		System.err.println("\t\t =>> ok, scalled :" + imageScalled.getWidth(null) + "x" + imageScalled.getHeight(null));

		int coordImgX = 0;
		int coordImgY = 0;

		BufferedImage biTransformed = null;
		biTransformed = new BufferedImage(targetImageWidth, targetImageHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = (Graphics2D) biTransformed.getGraphics();
		Color bgColor = Color.WHITE;
		g2d.setColor(bgColor);
		g2d.fillRect(0, 0, targetImageWidth, targetImageHeight);

		coordImgX = (targetImageWidth - newImageW) / 2;
		coordImgY = (targetImageHeight - newImageH) / 2;

		if (aspectRatio < 1.0) {
			System.err.println("\t\t CENTER + CROP =>> (" + (coordImgX) + ",0) to (" + targetImageWidth + "," + targetImageHeight + ")");
			g2d.drawImage(imageScalled, coordImgX, coordImgY, null);
		} else if (aspectRatio > 1.0) {
			System.err.println("\t\t FILL BACKGROUNG WITH GRADIENT =>> Paint image in (" + coordImgX + ",0)");
			g2d.drawImage(drawVerticalsBarsgradient(imageScalled, 0.02f, bgColor), coordImgX, coordImgY, null);
		} else {
			System.err.println("\t\t CENTER AS SAME =>> (" + (coordImgX) + ",0) to (" + targetImageWidth + "," + targetImageHeight + ")");
			g2d.drawImage(imageScalled, coordImgX, coordImgY, null);
		}

		System.err.println("\t\t--------------------->>Ok trasnformed");

		byte[] imageBytes = asJPEG(biTransformed);

		return imageBytes;
	}

	private byte[] asJPEG(BufferedImage bi) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
		jpegImageWriter.setOutput(ios);

		jpegImageWriter.write(null, new IIOImage(bi, null, null), param);

		byte[] imageBytes = baos.toByteArray();

		return imageBytes;
	}

	public List<byte[]> generateImagesForAllSizes(byte[] originalData) throws IOException {
		List<byte[]> imageList = new ArrayList<byte[]>();

		Dimension[] dimImagesArr = new Dimension[]{
			new Dimension(targetMediumImageWidth, targetMediumImageHeight),
			new Dimension(targetMinimunImageWidth, targetMinimunImageHeight),
			new Dimension(targetIconImageWidth, targetIconImageHeight)
		};

		BufferedImage productoImge = ImageIO.read(new ByteArrayInputStream(originalData));
		logger.debug("-->>generateImagesForAllSizes: productoImge=" + productoImge);
		for (Dimension d : dimImagesArr) {

			Image imageResized = productoImge.getScaledInstance((int) d.getWidth(), (int) d.getHeight(), Image.SCALE_SMOOTH);
			BufferedImage productoImgeMedResRenderd = new BufferedImage((int) d.getWidth(), (int) d.getHeight(), BufferedImage.TYPE_INT_RGB);

			Graphics2D g2d = (Graphics2D) productoImgeMedResRenderd.getGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, productoImgeMedResRenderd.getWidth(), productoImgeMedResRenderd.getHeight());
			g2d.drawImage(imageResized, 0, 0, null);

			imageList.add(asJPEG(productoImgeMedResRenderd));
			logger.debug("\t-->>generateImagesForAllSizes: OK for " + d);
		}

		return imageList;
	}

	private BufferedImage drawVerticalsBarsgradient(Image imageToDraw, float fadeWidth, Color barColor) {
		int imageWidth = imageToDraw.getWidth(null);
		int imageHeight = imageToDraw.getHeight(null);

		BufferedImage reflection = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D rg = reflection.createGraphics();
		rg.drawImage(imageToDraw, 0, 0, null);
		rg.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN));
		Color barColorTransparent = new Color(barColor.getRed() / 255.0f, barColor.getGreen() / 255.0f, barColor.getBlue() / 255.0f, 0.0f);
		rg.setPaint(
				new GradientPaint(
				0, 0, barColorTransparent,
				imageWidth * fadeWidth, 0, barColor));
		rg.fillRect(0, 0,
				(int) (imageWidth * fadeWidth), imageHeight);

		rg.setPaint(
				new GradientPaint(
				imageWidth, 0, barColorTransparent,
				imageWidth - imageWidth * fadeWidth, 0, barColor));
		rg.fillRect((int) (imageWidth - imageWidth * fadeWidth), 0,
				imageWidth, imageHeight);
		rg.dispose();
		return reflection;
	}

	/**
	 * @return the jpegImageQuality
	 */
	public float getJpegImageQuality() {
		return jpegImageQuality;
	}

	/**
	 * @param jpegImageQuality the jpegImageQuality to set
	 */
	public void setJpegImageQuality(float jpegImageQuality) {
		if (jpegImageQuality < 0.01f || jpegImageQuality > 1.0f) {
			throw new IllegalArgumentException("jpegImageQuality=" + jpegImageQuality + " must be betwen [0.01 and 1.0]");
		}
		this.jpegImageQuality = jpegImageQuality;
	}
}
