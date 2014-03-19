package com.pmarlen.dev.task;

import com.pmarlen.businesslogic.reports.GeneradorDeFactura;
import com.pmarlen.businesslogic.reports.NumeroCastellano;
import com.pmarlen.businesslogic.reports.ParseCFDXML;
import com.pmarlen.businesslogic.reports.QRImageGenerator;
import com.pmarlen.model.beans.CfdVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * Hello world!
 *
 */

public class GenraImagenEnHojaPDF {
	
    public static void generaPdf(Image img,OutputStream os) {
        try {
            String reportPath;
            
            reportPath = "/reports/pdfImagenReport.jrxml";
            InputStream inputStream = GeneradorDeFactura.class.getResourceAsStream(reportPath);
            
            JRDataSource ds = new JREmptyDataSource();
            System.out.println("Ok, JRDataSource created");
            
            Map parameters = new HashMap();
            parameters.put("img" ,img);
            
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            System.out.println("Ok, JasperReport compiled: pageHeight="+jasperReport.getPageHeight());            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
            System.out.println("Ok, JasperPrint created");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, os);
			System.out.println("Ok, JasperExportManager executed");			
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

}