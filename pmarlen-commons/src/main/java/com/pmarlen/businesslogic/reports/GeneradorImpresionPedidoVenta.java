package com.pmarlen.businesslogic.reports;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.CfdVenta;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaEstado;
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

public class GeneradorImpresionPedidoVenta {
	
    public byte[] generaPdfPedidoVenta(PedidoVenta pedidoVenta,boolean fullPrint,String usuarioImr) {
		byte[] pdfBytes = null;
		try {
			String reportPath;
            
            reportPath = "/reports/pedidoVentaDesign1.jrxml";
            InputStream inputStream = GeneradorImpresionPedidoVenta.class.getResourceAsStream(reportPath);
            
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
            //int numReg  = 75;//Integer.parseInt(args[0]);
            Random rand = new Random(System.currentTimeMillis());
            double dd;
            double d = 0;
			
			final Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVenta.getPedidoVentaDetalleCollection();
			final Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
			
			PedidoVentaEstado pveUltimoEstado = null;
			
			for(PedidoVentaEstado pve:pedidoVentaEstadoCollection){
				pveUltimoEstado = pve;
			}
			
			//numReg = pedidoVentaDetalleCollection.size();
			for(PedidoVentaDetalle pvd:pedidoVentaDetalleCollection){
				Map<String,Object> vals = new HashMap<String,Object> ();
                
                n = pvd.getCantidad();
                
                vals.put("clave",pvd.getProducto().getId());
                vals.put("cantidad",n);
                vals.put("codigoBarras",pvd.getProducto().getCodigoBarras());                
                vals.put("descripcion",pvd.getProducto().getNombre()+"/"+pvd.getProducto().getPresentacion()+"("+pvd.getProducto().getContenido()+pvd.getProducto().getUnidadMedida()+"/"+pvd.getProducto().getUnidadesPorCaja()+")");
                
				p  = pvd.getPrecioVenta();
                im = n * p ; 
                
                sp  += p;
                sim += im;
                
                vals.put("precio",df.format(p));
                //vals.put("desc"  ,df.format(0.0));
                vals.put("importe",df.format(im));
                vals.put("unidadMedida",pvd.getProducto().getUnidadMedida());
                
                col.add(vals);
			}
			d = pedidoVenta.getDescuentoAplicado()*sim;
			
            JRDataSource beanColDataSource = new JRMapCollectionDataSource(col);
            System.out.println("Ok, JRDataSource created");
            
            Map parameters = new HashMap();
            SimpleDateFormat sdf_cdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            
            SimpleDateFormat sdf_f1 = new SimpleDateFormat("yyyy/MM/dd");
            SimpleDateFormat sdf_h1 = new SimpleDateFormat("HH:mm");
            
            Date fechaReporte = new Date();
            
            parameters.put("printImages" ,fullPrint);
            String lugarExpedicion = pedidoVenta.getAlmacen().getSucursal().getPoblacion().getMunicipioODelegacion()+", "+pedidoVenta.getAlmacen().getSucursal().getPoblacion().getEntidadFederativa();
			parameters.put("usuario",pedidoVenta.getUsuario().getNombreCompleto());
			parameters.put("usuarioImpr",usuarioImr);
			parameters.put("pedidoVentaId",pedidoVenta.getId());
			parameters.put("pedidoVentaEstado",pveUltimoEstado.getEstado().getDescripcion().toString());
			
			parameters.put("tipoAlmacen", Constants.getDescripcionTipoAlmacen(pedidoVenta.getAlmacen().getTipoAlmacen()));
			
			parameters.put("fechaHoraImpr",sdf_cdf.format(new Date()));
						
            parameters.put("cliente",pedidoVenta.getCliente().getRazonSocial());
            parameters.put("rfc",pedidoVenta.getCliente().getRfc());
			String direccionValue = null;
			direccionValue = pedidoVenta.getCliente().getDireccionFacturacion();
			if(direccionValue == null){
				direccionValue = pedidoVenta.getCliente().getCalle()+
						", Num. Ext. "+(pedidoVenta.getCliente().getNumExterior()!=null&&pedidoVenta.getCliente().getNumExterior().length()>0?pedidoVenta.getCliente().getNumExterior():"S/N")+
						", Num. Int. "+(pedidoVenta.getCliente().getNumInterior()!=null&&pedidoVenta.getCliente().getNumInterior().length()>0?pedidoVenta.getCliente().getNumInterior():"S/N")+					
						", "+pedidoVenta.getCliente().getPoblacion().getTipoAsentamiento()+" "+pedidoVenta.getCliente().getPoblacion().getNombre()+
						", "+pedidoVenta.getCliente().getPoblacion().getMunicipioODelegacion()+
						", "+pedidoVenta.getCliente().getPoblacion().getEntidadFederativa()+
						", C.P. "+pedidoVenta.getCliente().getPoblacion().getCodigoPostal();
			}
            parameters.put("direccion" , direccionValue.toUpperCase());
			
            if(pedidoVenta.getComentarios()!=null && pedidoVenta.getComentarios().trim().length()>1){
				parameters.put("comentarios" ,pedidoVenta.getComentarios());
			} else{
				parameters.put("comentarios" ,null);
			}
            parameters.put("formaDePago" ,pedidoVenta.getFormaDePago().getDescripcion().toUpperCase());
            parameters.put("metodoDePago",pedidoVenta.getMetodoDePago().getDescripcion().toUpperCase());
            
            parameters.put("subtotal" , df.format(sim));
            //parameters.put("iva" ,df.format(sim*0.16));
            parameters.put("descuento" ,df.format(d));
            
            double total = sim - d;
            parameters.put("total" ,df.format(total));  
            
            String intDecParts[] = dfEnt.format(total).split("\\.");
            
            String letrasParteEntera  = NumeroCastellano.numeroACastellano(Long.parseLong(intDecParts[0])).trim();
            String letrasParteDecimal = NumeroCastellano.numeroACastellano(Long.parseLong(intDecParts[1])).trim();
            
            JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			/*
            int pageHeight = jasperDesign.getPageHeight(); //jasperReport.getPageHeight();
            int titleHeight = jasperDesign.getTitle().getHeight();
            int pageHeaderHeight = jasperDesign.getPageHeader().getHeight();
            int columnHeaderHeight = jasperDesign.getColumnHeader().getHeight();
            if(parameters.get("rfc")== null) {
                pageHeaderHeight = 0;
            }
			*/
            //int detailHeight = jasperDesign.getDetailSection().getBands()[0].getHeight();
            //int sumaryHeight = jasperDesign.getSummary().getHeight();
            //int allBandsHeight = titleHeight + pageHeaderHeight + columnHeaderHeight + detailHeight + sumaryHeight;
            //int exactPageHeight = titleHeight + pageHeaderHeight + columnHeaderHeight + detailHeight * numReg + sumaryHeight;
            
            //System.out.println("Ok, JasperDesign created: pageHeight="+pageHeight+", pageHeaderHeight="+pageHeaderHeight+", columnHeaderHeight="+columnHeaderHeight+", detailHeight="+detailHeight+", allBandsHeight="+allBandsHeight+", exactPageHeight="+exactPageHeight);
            //jasperDesign.setPageHeight(exactPageHeight);
            //System.out.println("\t=>JasperDesign pageHeight="+jasperDesign.getPageHeight());
            
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            System.out.println("Ok, JasperReport compiled: pageHeight="+jasperReport.getPageHeight());            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanColDataSource);
            System.out.println("Ok, JasperPrint created");
            //JasperExportManager.exportReportToPdfFile(jasperPrint, "jasper_out_"+sdf.format(new Date())+".pdf");
            //System.out.println("Ok, JasperExportManager executed");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, baos);
			System.out.println("Ok, JasperExportManager executed");
			pdfBytes = baos.toByteArray();
            //JasperPrintManager.printReport(jasperPrint, false);
            //System.out.println("Ok, printed. executed");                        
            
            System.out.println("Ok, finished");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            //System.exit(1);
        }
		return pdfBytes;
    }

}