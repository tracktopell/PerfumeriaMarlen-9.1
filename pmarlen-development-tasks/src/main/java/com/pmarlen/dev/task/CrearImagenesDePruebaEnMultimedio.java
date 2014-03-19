package com.pmarlen.dev.task;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

/**
 * CrearImagenesDePruebaEnMultimedio
 */
public class CrearImagenesDePruebaEnMultimedio {

	float jpegImageQuality = 1.0f;

	int targetImageWidth		= 1024;
	int targetImageHeight		= 768;

	int targetMediumImageWidth	= 320;
	int targetMediumImageHeight = 240;

	int targetMinimunImageWidth	 = 160;
	int targetMinimunImageHeight = 120;

	int targetIconImageWidth	= 20;
	int targetIconImageHeight	= 15;
	
	public void crearImagesnes(String url, String user, String password, String baseDirImages,String dirImages, String maskFileName,String finalOutputDir) {
		//System.err.println(" \t====> debug: url="+url+", user="+user+", password="+password+", dirImages="+dirImages+", maskFileName="+maskFileName);

		Connection conn = null;
		PreparedStatement psProducto = null;
		PreparedStatement psMultimedio = null;
		PreparedStatement psInsertMultimedio = null;
		PreparedStatement psInsertProductoMultimedio = null;

		ResultSet rsProducto = null;
		ResultSet rsMultimedio = null;
		ArrayList<Integer> productoIds = new ArrayList<Integer>();
		ArrayList<Integer> multimedioIds = null;
		DecimalFormat df = new DecimalFormat("0000");
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmmss");

		byte[] imagenProximamenteBytes = null;

		//----------------

		// Get a ImageWriter for jpeg format.
		Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
		if (!writers.hasNext()) {
			throw new IllegalStateException("No JPEG writers found");
		}
		ImageWriter jpegImageWriter = (ImageWriter) writers.next();

		ImageWriteParam param = jpegImageWriter.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(jpegImageQuality);

		try {
			conn = DriverManager.getConnection(url, user, password);

			psProducto = conn.prepareStatement("SELECT ID FROM PRODUCTO");
			rsProducto = psProducto.executeQuery();

			while (rsProducto.next()) {
				Integer productoId = rsProducto.getInt("Id");
				productoIds.add(productoId);
			}
			rsProducto.close();
			psProducto.close();
			System.err.println(" ====> debug: maskFileName=\"" + maskFileName + "\"");
			
			System.err.println("====> debug: Ok, found " + productoIds.size() + " productos.");

			psMultimedio				= conn.prepareStatement("SELECT PRODUCTO_ID,MULTIMEDIO_ID FROM PRODUCTO_MULTIMEDIO WHERE PRODUCTO_ID = ?");
			psInsertMultimedio			= conn.prepareStatement("INSERT INTO MULTIMEDIO (RUTA_CONTENIDO,MIME_TYPE,NOMBRE_ARCHIVO,SIZE_BYTES) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			psInsertProductoMultimedio	= conn.prepareStatement("INSERT INTO PRODUCTO_MULTIMEDIO(PRODUCTO_ID,MULTIMEDIO_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
			
			File imageFileDir = new File(dirImages);
			File[] filesInDir = imageFileDir.listFiles();
			
			Hashtable<String,File> posibleImageFiles = new Hashtable<String,File> ();
			System.err.println("====> Listing dor for search all Images files in dir:"+imageFileDir.getAbsolutePath());
			for(File f: filesInDir){
				System.err.println("\t====> is this: "+f.getPath()+" ?");
				if(f.isDirectory() ){
					continue;
				}
				String fnlc = f.getName().toLowerCase();
				if(fnlc.endsWith("jpeg") ||fnlc.endsWith("jpg") || fnlc.endsWith("png")){
					System.err.println("\t\t====> it seems like Image:KEY="+f.getName().toLowerCase());
					posibleImageFiles.put(fnlc, f);
				}
			}

			for (Integer productoId : productoIds) {
				psMultimedio.clearParameters();

				psMultimedio.setInt(1, productoId);
				rsMultimedio = psMultimedio.executeQuery();

				multimedioIds = new ArrayList<Integer>();
				while (rsMultimedio.next()) {
					Integer multimedioId = rsMultimedio.getInt("MULTIMEDIO_ID");
					multimedioIds.add(multimedioId);
				}
				rsMultimedio.close();
				int rowsAffected = -1;
				if (multimedioIds.size() > 0) {
					Statement st = conn.createStatement();
					for (Integer multimedioId : multimedioIds) {
						System.err.println("\tDelete multimedio resources for producto:" + productoId);
						rowsAffected = -1;
						if ((rowsAffected = st.executeUpdate("DELETE FROM PRODUCTO_MULTIMEDIO WHERE MULTIMEDIO_ID=" + multimedioId)) < 1) {
							System.err.println(" in delete PRODUCTO_MULTIMEDIO with MULTIMEDIO_ID=" + multimedioId + ", affects :" + rowsAffected);
						}
						rowsAffected = -1;
						if ((rowsAffected = st.executeUpdate("DELETE FROM MULTIMEDIO WHERE ID=" + multimedioId)) < 1) {
							System.err.println(" in delete MULTIMEDIO with ID=" + multimedioId + ", affects :" + rowsAffected);
						}

					}
					st.close();
				}

				psInsertMultimedio.clearParameters();
				//--------------------------------------------------------------
				boolean loadFromfile;
				InputStream is = null;
				byte[] imageBytes = null;
				int sizeBytes=0;

				loadFromfile = (dirImages != null && maskFileName != null);
				if (loadFromfile) {
					String fileNameToSearch = maskFileName.replace("@PRODUCTO_ID@", String.valueOf(productoId));
					File imageFile = new File(dirImages + File.separator + fileNameToSearch);
					
					System.err.println("------------------------------> ProductoId="+productoId+", try to read from file :"+imageFile);
					
					if (imageFile.exists() || imageFile.canRead()){
						System.err.println(" ====> debug: OK reading from :"+imageFile);
					}else{
						
						Enumeration<String> posibleFileNamesEnumeration = posibleImageFiles.keys();
						System.err.println(" \t====> debug: exist || readable ? = ( "+imageFile.exists()+"||"+imageFile.canRead()+"), then try to search with another like name "); 
						imageFile = null;
						while(posibleFileNamesEnumeration.hasMoreElements()) {
							String posibleFileName = posibleFileNamesEnumeration.nextElement();
							if(fileNameToSearch.substring(0, fileNameToSearch.lastIndexOf(".")).
									equalsIgnoreCase(posibleFileName.substring(0, posibleFileName.lastIndexOf(".")))){
								imageFile = posibleImageFiles.get(posibleFileName);
								System.err.println(" \t\t====> debug: OK We found some like the original looking for, reading from :"+imageFile);
								
								break;
							}							
						}				
					}

					
					if (imageFile != null) {
						BufferedImage originalImage = null;

						originalImage = ImageIO.read(imageFile);
						Image imageScalled = null;
						int w = originalImage.getWidth();
						int h = originalImage.getHeight();

						int newImageW = 0;
						int newImageH = 0;

						System.err.println("\t====> Image: size:" + w + "x" + h + " => " + targetImageWidth + "x" + targetImageHeight);

						if (h != targetImageHeight && h>w) {
							newImageH = targetImageHeight;
							newImageW = ((targetImageHeight * w) / h);
							System.err.println("\t\t resize =>> " + newImageW + "x" + newImageH);
						} else {
							newImageH = ((targetImageHeight * h) / w);
							newImageW = targetImageWidth;
							System.err.println("\t\t resize =>> " + newImageW + "x" + newImageH);
							//newImageH = targetImageHeight;
							//newImageW = w;
						}
						
						imageScalled = originalImage.getScaledInstance(newImageW, newImageH, Image.SCALE_SMOOTH);
						
						int coordImgX = 0;

						BufferedImage biTransformed = null;
						biTransformed = new BufferedImage(targetImageWidth, targetImageHeight, BufferedImage.TYPE_INT_RGB);

						Graphics2D g2d = (Graphics2D) biTransformed.getGraphics();
						Color bgColor = Color.WHITE;
						g2d.setColor(bgColor);
						g2d.fillRect(0, 0, targetImageWidth, targetImageHeight);
						int coordImgY = 0;
						if (newImageW >= targetImageWidth) {
							coordImgX = (targetImageWidth  - newImageW) / 2;
							coordImgY = (targetImageHeight - newImageH) / 2;
							System.err.println("\t\t CENTER + CROP =>> (" + (coordImgX) + ",0) to (" + targetImageWidth + "," + targetImageHeight + ")");
							g2d.drawImage(imageScalled, coordImgX, coordImgY, null);
						} else if (newImageW < targetImageWidth) {
							coordImgX = (targetImageWidth - newImageW) / 2;
							System.err.println("\t\t FILL BACKGROUNG WITH GRADIENT =>> Paint image in (" + coordImgX + ",0)");
							g2d.drawImage(drawVerticalsBarsgradient(imageScalled,0.02f,bgColor), coordImgX, 0, null);
						} 

						System.err.println("\t\t--------------------->>Ok trasnformed");

						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
						jpegImageWriter.setOutput(ios);
						jpegImageWriter.write(null, new IIOImage(biTransformed, null, null), param);
						
						imageBytes = baos.toByteArray();
						sizeBytes=imageBytes.length;
						//is = new ByteArrayInputStream(imageBytes);
						
						loadFromfile = true;
						
					} else {
						System.err.println("\tCan't read image from : file=" + imageFile + ", path=");
						loadFromfile = false;
					}
				}

				if (!loadFromfile) {

					if(imagenProximamenteBytes == null){
						InputStream isImagenProx =  getClass().getResourceAsStream("/imgs/PROXIMAMENTE_IMG.jpg");
						
						byte[] buffer = new byte[1028 * 64];
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int r = -1;
						while ((r = isImagenProx.read(buffer, 0, buffer.length)) != -1) {
							baos.write(buffer, 0, r);
							baos.flush();
						}
						buffer = null;
						isImagenProx.close();
						baos.close();

						imagenProximamenteBytes = baos.toByteArray();	
						System.err.println("\t\tOk PROXIMAMENTE image loaded!");
					}
										
					/*
					if (maskFileName == null) {
						maskFileName = "@PRODUCTO_ID@.jpg";
					}
					
					BufferedImage bi = null;
					bi = new BufferedImage(targetImageWidth, targetImageHeight, BufferedImage.TYPE_INT_RGB);

					Graphics2D g2d = (Graphics2D) bi.getGraphics();
					g2d.setColor(Color.WHITE);
					g2d.fillRect(0, 0, bi.getWidth(), bi.getHeight());
					g2d.setColor(Color.GREEN);
					g2d.drawString("IMAGEN TEMPORAL" + productoId, 5, bi.getHeight() / 2);
					g2d.drawString("PRODUCTO :" + productoId, 5, bi.getHeight() / 2 + 50);
					g2d.drawString("FECHA :" + sdf.format(new Date()), 5, bi.getHeight() / 2 + 100);


					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
					jpegImageWriter.setOutput(ios);
					jpegImageWriter.write(null, new IIOImage(bi, null, null), param);
					//ImageIO.write(bi, "JPEG", baos);

					imageBytes = baos.toByteArray();
					*/
					imageBytes = imagenProximamenteBytes;
					sizeBytes=imageBytes.length;
					//is = new ByteArrayInputStream(imageBytes);
					System.err.println("\tOk copyed PROXIMAMENTE image for producto:" + productoId);
				}
				String nombreArchivoFinal = "PRODUCTO_"+productoId+".jpg";
				
				FileOutputStream fos = new FileOutputStream(baseDirImages+finalOutputDir+nombreArchivoFinal);
				fos.write(imageBytes);
				fos.flush();
				fos.close();
				//psInsertMultimedio.setBlob(1, is);
				psInsertMultimedio.setString(1, finalOutputDir);
				psInsertMultimedio.setString(2, "image/jpeg");				
				psInsertMultimedio.setString(3, nombreArchivoFinal);
				psInsertMultimedio.setInt   (4, sizeBytes);

				Integer multimedioId = null;
				rowsAffected = psInsertMultimedio.executeUpdate();
				if (rowsAffected < 1) {
					System.err.println(" in insert MULTIMEDIO just affects :" + rowsAffected);
				} else {
					ResultSet rsgk = psInsertMultimedio.getGeneratedKeys();
					if (rsgk.next()) {
						multimedioId = rsgk.getInt(1);
					}
					rsgk.close();
				}

				if (multimedioId != null) {
					psInsertProductoMultimedio.clearParameters();
					psInsertProductoMultimedio.setInt(1, productoId);
					psInsertProductoMultimedio.setInt(2, multimedioId);

					rowsAffected = psInsertProductoMultimedio.executeUpdate();
					if (rowsAffected < 1) {
						System.err.println(" in insert PRODUCTO_MULTIMEDIO just affects :" + rowsAffected);
					}
				}
			}

			conn.close();

		} catch (IOException ex) {
			ex.printStackTrace(System.err);
			return;
		} catch (SQLException ex) {
			ex.printStackTrace(System.err);
			return;
		}
	}

	public void extraerImagenes(String url, String user, String password, String baseDirImages,String dirImages) {

		Connection conn = null;
		PreparedStatement psMultimedio = null;

		ResultSet rsMultimedio = null;
		byte[] buffer = new byte[1028 * 64];
		try {
			conn = DriverManager.getConnection(url, user, password);

			psMultimedio = conn.prepareStatement("SELECT ID,RUTA_CONTENIDO,NOMBRE_ARCHIVO FROM MULTIMEDIO");
			rsMultimedio = psMultimedio.executeQuery();

			int contExtraidos = 0;
			while (rsMultimedio.next()) {
				Integer multimedioId = rsMultimedio.getInt("ID");
				String nombrearchivo = rsMultimedio.getString("NOMBRE_ARCHIVO");

				InputStream is = null;
				//is = rsMultimedio.getBinaryStream("CONTENIDO");
				is = new FileInputStream(baseDirImages+rsMultimedio.getString("RUTA_CONTENIDO")+nombrearchivo);
				FileOutputStream fos = new FileOutputStream(dirImages + File.separator + nombrearchivo);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				int r = -1;
				while ((r = is.read(buffer, 0, buffer.length)) != -1) {
					fos.write(buffer, 0, r);
					baos.write(buffer, 0, r);
				}
				is.close();
				fos.close();
				baos.close();
				System.err.println("\tOk exploded image for multimedio:" + multimedioId);
				
				BufferedImage productoImge = ImageIO.read(new ByteArrayInputStream(baos.toByteArray()));
				
				Image productoImgeMedRes = productoImge.getScaledInstance(targetMediumImageWidth, targetMediumImageHeight, Image.SCALE_SMOOTH);
				BufferedImage productoImgeMedResRenderd = new BufferedImage(targetMediumImageWidth, targetMediumImageHeight, BufferedImage.TYPE_INT_RGB);

				Graphics2D g2d = (Graphics2D) productoImgeMedResRenderd.getGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, productoImgeMedResRenderd.getWidth(), productoImgeMedResRenderd.getHeight());
				g2d.drawImage(productoImgeMedRes, 0, 0, null);
				ImageIO.write(productoImgeMedResRenderd,"jpeg", new FileOutputStream(dirImages + File.separator + "MED_"+nombrearchivo));
				System.err.println("\t\tOk exploded MedRes:");
				
				Image productoImgeMinRes = productoImge.getScaledInstance(targetMinimunImageWidth, targetMinimunImageHeight, Image.SCALE_SMOOTH);
				BufferedImage productoImgeMinResRenderd = new BufferedImage(targetMinimunImageWidth, targetMinimunImageHeight, BufferedImage.TYPE_INT_RGB);

				g2d = (Graphics2D) productoImgeMinResRenderd.getGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, productoImgeMinResRenderd.getWidth(), productoImgeMinResRenderd.getHeight());
				g2d.drawImage(productoImgeMinRes, 0, 0, null);
				ImageIO.write(productoImgeMinResRenderd,"jpeg", new FileOutputStream(dirImages + File.separator + "MIN_"+nombrearchivo));
				System.err.println("\t\tOk exploded MinRes:");
				
				Image productoImgeIconRes = productoImge.getScaledInstance(targetIconImageWidth, targetIconImageHeight, Image.SCALE_SMOOTH);
				BufferedImage productoImgeIconResRenderd = new BufferedImage(targetIconImageWidth, targetIconImageHeight, BufferedImage.TYPE_INT_RGB);

				g2d = (Graphics2D) productoImgeIconResRenderd.getGraphics();
				g2d.setColor(Color.WHITE);
				g2d.fillRect(0, 0, productoImgeIconResRenderd.getWidth(), productoImgeIconResRenderd.getHeight());
				g2d.drawImage(productoImgeIconRes, 0, 0, null);
				ImageIO.write(productoImgeIconResRenderd, "jpeg", new FileOutputStream(dirImages + File.separator + "ICO_"+nombrearchivo));
				System.err.println("\t\tOk exploded Icono:");
				contExtraidos++;
			}
			rsMultimedio.close();
			psMultimedio.close();

			System.out.println("-->>contExtraidos="+contExtraidos);
			conn.close();

		} catch (IOException ex) {
			ex.printStackTrace(System.err);
			return;
		} catch (SQLException ex) {
			ex.printStackTrace(System.err);
			return;
		}
	}
	
	public BufferedImage drawVerticalsBarsgradient(Image imageToDraw,float fadeWidth,Color barColor) {
		int imageWidth  = imageToDraw.getWidth(null);
		int imageHeight = imageToDraw.getHeight(null);
		
		BufferedImage reflection = new BufferedImage( imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB );
		Graphics2D rg = reflection.createGraphics();
		rg.drawImage( imageToDraw, 0,0,null );
		rg.setComposite( AlphaComposite.getInstance( AlphaComposite.DST_IN ) );
		Color barColorTransparent = new Color( barColor.getRed()/255.0f, barColor.getGreen()/255.0f, barColor.getBlue()/255.0f, 0.0f);
		rg.setPaint( 
			new GradientPaint( 
				0                     , 0 , barColorTransparent,
				imageWidth*fadeWidth, 0   , barColor
			)	
		);
		rg.fillRect( 0, 0, 
					 (int)(imageWidth*fadeWidth), imageHeight );
		
		rg.setPaint( 
			new GradientPaint( 
				imageWidth                       , 0 ,barColorTransparent,
				imageWidth - imageWidth*fadeWidth, 0 ,barColor 
			)	
		);
		rg.fillRect( (int)(imageWidth - imageWidth*fadeWidth), 0, 
					 imageWidth, imageHeight );
		rg.dispose();
		return reflection;
	}

	public static void main(String[] args) {
		CrearImagenesDePruebaEnMultimedio cidpm = new CrearImagenesDePruebaEnMultimedio();
		if (args.length == 0) {
			System.err.println(" -u  url  user  password  baseDir dirImages  @PRODUCTO_ID@.JPG finalOutputDir");
			System.err.println(" -x  url  user  password  baseDir dirImages");

			System.exit(1);
		}
		System.out.println("==>> ");
		if (args[0].equals("-u")) {
			cidpm.crearImagesnes(args[1], args[2], args[3], args[4], args[5], args[6], args[7]);			
		} else if (args[0].equals("-x")) {
			cidpm.extraerImagenes(args[1], args[2], args[3], args[4], args[5]);
		}

	}
}
