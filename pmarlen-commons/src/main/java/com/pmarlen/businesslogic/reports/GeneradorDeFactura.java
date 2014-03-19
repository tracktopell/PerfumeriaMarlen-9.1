package com.pmarlen.businesslogic.reports;

import com.pmarlen.model.beans.CfdVenta;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

public class GeneradorDeFactura {
	
    public byte[] generaPdfFactura(PedidoVenta pedidoVenta,boolean fullPrint) {
		byte[] pdfBytes = null;
		CfdVenta cfdVenta = null;
        try {
			cfdVenta = pedidoVenta.getCfdVenta();
			if(cfdVenta == null || (cfdVenta.getContenidoOriginalXml()== null&&cfdVenta.getCallingErrorResult()!=null)){
				throw new IllegalStateException("No existe CFD en el PedidoVenta, para generar PDF de Factura");
			}
			
			InputStream isXMLCFD = new ByteArrayInputStream(cfdVenta.getContenidoOriginalXml().getBytes());
			HashMap cfdMap = ParseCFDXML.parseCFDXML(isXMLCFD);
			
            String reportPath;
            
            reportPath = "/reports/facturaDesign1.jrxml";
            InputStream inputStream = GeneradorDeFactura.class.getResourceAsStream(reportPath);
            
            Collection<Map<String,?>> col = new ArrayList<Map<String,?>>();
            DecimalFormat    df    = new  DecimalFormat("$###,###,###,##0.00");
            DecimalFormat    dfEnt = new  DecimalFormat("###########0.00");
            DecimalFormat    dfBC  = new  DecimalFormat("0000000000000");
            System.out.println("Ok, jrxml loaded");
            double p  = 0.0;            
            double im = 0.0;
            double sp = 0.0;            
            double sim= 0.0;
            int n;
            double dd;
            double d = 0;
						
			Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVenta.getPedidoVentaDetalleCollection();
			for(PedidoVentaDetalle pvd:pedidoVentaDetalleCollection){
				Map<String,Object> vals = new HashMap<String,Object> ();
                
                n = pvd.getCantidad();
                
                vals.put("clave",pvd.getProducto().getId());
                vals.put("cantidad",n);
                vals.put("codigoBarras",pvd.getProducto().getCodigoBarras());                
                vals.put("descripcion",pvd.getProducto().getNombre()+" / "+pvd.getProducto().getPresentacion()+" ("+pvd.getProducto().getContenido()+" "+pvd.getProducto().getUnidadMedida()+")");
                
				p  = pvd.getPrecioVenta() / 1.16;
                im = n * p ; 
                
                sp  += p;
                sim += im;
                
                vals.put("precio",df.format(p));
                vals.put("importe",df.format(im));
                vals.put("unidadMedida",pvd.getProducto().getUnidadMedida());
                
                col.add(vals);
			}
			
            JRDataSource beanColDataSource = new JRMapCollectionDataSource(col);
            System.out.println("Ok, JRDataSource created");
            
            Map parameters = new HashMap();
            SimpleDateFormat sdf_cdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            
            SimpleDateFormat sdf_f1 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sdf_h1 = new SimpleDateFormat("HH:mm");
            
            
            Date fechaReporte = new Date();
            
			parameters.put("pedidoVentaId" ,String.valueOf(pedidoVenta.getId()));            
            parameters.put("noFactura" ,""+cfdMap.get("serie")+cfdMap.get("folio"));
            parameters.put("printImages" ,fullPrint);
            parameters.put("folioFiscal" ,cfdMap.get("UUID"));
            parameters.put("fechaYHoraCert" ,cfdMap.get("FechaTimbrado").toString().replace("T", " "));
			String lugarExpedicion = pedidoVenta.getAlmacen().getSucursal().getPoblacion().getMunicipioODelegacion()+", "+pedidoVenta.getAlmacen().getSucursal().getPoblacion().getEntidadFederativa();
			parameters.put("lugarExp" , lugarExpedicion);
            parameters.put("fechaYHoraExp" ,cfdMap.get("fecha").toString().replace("T", " "));
            parameters.put("noSerCertSAT" ,cfdMap.get("noCertificadoSAT"));
            
            parameters.put("cliente",pedidoVenta.getCliente().getRazonSocial());
            parameters.put("rfc",pedidoVenta.getCliente().getRfc());
			String direccionValue = pedidoVenta.getCliente().getCalle()+
					", Num. Ext. "+(pedidoVenta.getCliente().getNumExterior()!=null&&pedidoVenta.getCliente().getNumExterior().length()>0?pedidoVenta.getCliente().getNumExterior():"S/N")+
					", Num. Int. "+(pedidoVenta.getCliente().getNumInterior()!=null&&pedidoVenta.getCliente().getNumInterior().length()>0?pedidoVenta.getCliente().getNumInterior():"S/N")+					
					", "+pedidoVenta.getCliente().getPoblacion().getTipoAsentamiento()+" "+pedidoVenta.getCliente().getPoblacion().getNombre()+
					", "+pedidoVenta.getCliente().getPoblacion().getMunicipioODelegacion()+
					", "+pedidoVenta.getCliente().getPoblacion().getEntidadFederativa()+
					", C.P. "+pedidoVenta.getCliente().getPoblacion().getCodigoPostal();
            parameters.put("direccion" , direccionValue.toUpperCase());
			
			if(pedidoVenta.getComentarios()!=null && pedidoVenta.getComentarios().trim().length()>1){
				parameters.put("comentarios" ,pedidoVenta.getComentarios());
			} else{
				parameters.put("comentarios" ,null);
			}            
			parameters.put("formaDePago" ,pedidoVenta.getFormaDePago().getDescripcion().toUpperCase());
			parameters.put("metodoDePago",pedidoVenta.getMetodoDePago().getDescripcion().toUpperCase());
            
			d = pedidoVenta.getDescuentoAplicado();
			double descuentoFactura = d * sim;
			double ivaFactura = (sim-descuentoFactura)*0.16;
            parameters.put("subtotal" , df.format(sim));
            parameters.put("iva" ,df.format(ivaFactura));
            parameters.put("descuento" ,df.format(descuentoFactura));
            
            double total = sim - descuentoFactura + ivaFactura;
            parameters.put("total" ,df.format(total));  
            
            String intDecParts[] = dfEnt.format(total).split("\\.");
            
            String letrasParteEntera  = NumeroCastellano.numeroACastellano(Long.parseLong(intDecParts[0])).trim();
            String letrasParteDecimal = NumeroCastellano.numeroACastellano(Long.parseLong(intDecParts[1])).trim();
            
            parameters.put("importeLetra" ,(letrasParteEntera+" Pesos "+intDecParts[1]+"/100 M.N.").toUpperCase());
            
            parameters.put("cadenaOriginalSAT"  ,cfdMap.get("cadenaOriginal"));            
            parameters.put("selloDigitalEmisor" ,cfdMap.get("sello"));
            parameters.put("selloDigitalSAT"    ,cfdMap.get("selloSAT"));
			
			byte[] qrImage = QRImageGenerator.getQRImage(cfdMap.get("QR").toString());
			ByteArrayInputStream baosImageQR = new ByteArrayInputStream(qrImage);
			
			Image imageQR = ImageIO.read(baosImageQR);			
			parameters.put("imageQR"    ,imageQR);
            
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            System.out.println("Ok, JasperReport compiled: pageHeight="+jasperReport.getPageHeight());            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            System.out.println("Ok, JasperPrint created");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			System.out.println("Ok, JasperExportManager executed");
			pdfBytes = baos.toByteArray();
            
            System.out.println("Ok, finished");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            //System.exit(1);
        }
		return pdfBytes;
    }

}