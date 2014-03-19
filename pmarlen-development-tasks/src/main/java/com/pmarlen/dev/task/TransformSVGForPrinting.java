package com.pmarlen.dev.task;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;

import org.w3c.dom.Document;

/**
 * Hello world!
 *
 */
public class TransformSVGForPrinting {

	private static void cleanSVGOfLabels(String fileName) throws IOException{
		
		FileInputStream  fis = null;
		FileOutputStream fos = null;
		
		fis = new FileInputStream(fileName);
		fos = new FileOutputStream(fileName+".tmp");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		PrintStream ps = new PrintStream(fos);
		
		String line = null;
		boolean printLine = true;
		boolean cleanned = false;
		while(( line = br.readLine() ) != null){
			if(line.contains("rotate(-45)")){
				printLine = false;
				cleanned  = true;
			}
			if(!printLine) {
				if(line.contains("></g")){
					printLine = true;					
				}	
			} else{
				ps.println(line);
			}
		}
		File outFile =  new File(fileName+".tmp");
		if(cleanned ) {
			File inFile  =  new File(fileName);			
			if(!outFile.renameTo(inFile)){
				throw new IOException("can't rename ("+outFile+" to "+inFile+") file in cleanning SVG label Task");
			}
		}
		outFile.delete();
	}

	PNGTranscoder trans = new PNGTranscoder();

	public TransformSVGForPrinting() {
		//trans.addTranscodingHint(PNGTranscoder.KEY_QUALITY,new Float(1.0));
	}

	public void transcodeToSVG(String inputFilename,String outputFilename) throws Exception {
		// Transcode the file.
		//String svgURI = new File(inputFilename).toURL().toString();
		TranscoderInput input = new TranscoderInput(new FileInputStream(inputFilename));
		OutputStream ostream = new FileOutputStream(outputFilename);
		TranscoderOutput output = new TranscoderOutput(ostream);
		trans.transcode(input, output);

		// Flush and close the output.
		ostream.flush();
		ostream.close();
	}
	
	public void tile(String inputFilename,
			String outputFilename,
			Rectangle aoi) throws Exception {
		// Set hints to indicate the dimensions of the output image
		// and the input area of interest.
		trans.addTranscodingHint(PNGTranscoder.KEY_WIDTH,
				new Float(aoi.width));
		trans.addTranscodingHint(PNGTranscoder.KEY_HEIGHT,
				new Float(aoi.height));
		trans.addTranscodingHint(PNGTranscoder.KEY_AOI, aoi);

		// Transcode the file.
		//String svgURI = new File(inputFilename).toURL().toString();
		TranscoderInput input = new TranscoderInput(new FileInputStream(inputFilename));
		OutputStream ostream = new FileOutputStream(outputFilename);
		TranscoderOutput output = new TranscoderOutput(ostream);
		trans.transcode(input, output);

		// Flush and close the output.
		ostream.flush();
		ostream.close();
	}

	public static void main(String[] args) {
		System.out.println("Batik SVG explore:");
		try {
			String fileName = args[0];
			
			cleanSVGOfLabels(fileName);
			
			
			String parser = XMLResourceDescriptor.getXMLParserClassName();
			SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
			
			
			Document doc = f.createDocument(null, new FileInputStream(fileName));
		
			System.out.println("SVG Attributes:width,height=" + doc.getDocumentElement().getAttributes().getNamedItem("width").getNodeValue() + "," + doc.getDocumentElement().getAttributes().getNamedItem("height").getNodeValue());
			// Rasterize the samples/anne.svg document and save it
			// as four tiles.
			TransformSVGForPrinting p = new TransformSVGForPrinting();
			
			String outFile = null;
			
			//outFile = fileName + ".png";
			//System.out.println("-> new png:"+outFile);
			
			outFile = fileName.substring(0,fileName.lastIndexOf("."))+".png"; 
			String fdfOutFile = fileName.substring(0,fileName.lastIndexOf("."))+".pdf"; 
			
			p.transcodeToSVG(fileName, outFile);
			
			BufferedImage img = ImageIO.read(new FileInputStream(outFile));
			GenraImagenEnHojaPDF.generaPdf(img, new FileOutputStream(fdfOutFile));
			
			int documentWidth = Integer.parseInt(doc.getDocumentElement().getAttributes().getNamedItem("width").getNodeValue());
			int documentHeight = Integer.parseInt(doc.getDocumentElement().getAttributes().getNamedItem("height").getNodeValue());

			int dw2 = documentWidth / 2;
			int dh2 = documentHeight / 2;
			System.out.println("export:");
			
			p.tile(fileName, fileName + "_00.png", new Rectangle(0, 0, dw2, dh2));			
			img = ImageIO.read(new FileInputStream(fileName + "_00.png"));
			GenraImagenEnHojaPDF.generaPdf(img, new FileOutputStream(fileName + "_00.pdf"));

			p.tile(fileName, fileName + "_01.png", new Rectangle(dw2, 0, dw2, dh2));
			img = ImageIO.read(new FileInputStream(fileName + "_01.png"));
			GenraImagenEnHojaPDF.generaPdf(img, new FileOutputStream(fileName + "_01.pdf"));
			
			p.tile(fileName, fileName + "_10.png", new Rectangle(0, dh2, dw2, dh2));
			img = ImageIO.read(new FileInputStream(fileName + "_10.png"));
			GenraImagenEnHojaPDF.generaPdf(img, new FileOutputStream(fileName + "_10.pdf"));
			
			p.tile(fileName, fileName + "_11.png", new Rectangle(dw2, dh2, dw2, dh2));
			img = ImageIO.read(new FileInputStream(fileName + "_11.png"));
			GenraImagenEnHojaPDF.generaPdf(img, new FileOutputStream(fileName + "_11.pdf"));

		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
	}
}
