/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.digifactws.production.client;

import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.digifactws.production.*;
import com.pmarlen.model.beans.CfdVenta;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author alfredo
 */
public class DigifactClient {
/**
     * @param args the command line arguments
     */
    public static CfdVenta invokeWS(PedidoVenta pedidoVenta,String serie,String usuario,String contrasena) {
        DatosCFD datosCFD;
        Receptor receptor;
        ArrayOfAnyType conceptos;
        Concepto conceptoWS;
        ArrayOfAnyType impuestos;
        Impuesto impuestoWS;
        String xmlAddenda;
        CFD service = new CFD();
        CFDSoap port = service.getCFDSoap();
		
		CfdVenta cfdVenta=null;
        try {
            datosCFD = new DatosCFD();
            //String serie = "PMS";

            datosCFD.setSerie(serie);
            datosCFD.setFormadePago(pedidoVenta.getFormaDePago().getDescripcion().toUpperCase());
            datosCFD.setTipodeComprobante("F");
            datosCFD.setMetododePago(pedidoVenta.getMetodoDePago().getDescripcion());
            datosCFD.setEmailMensaje("FACTURA PEDIDO:"+pedidoVenta.getId());
			
			double st = 0.0;
			double da = pedidoVenta.getDescuentoAplicado()!=null?pedidoVenta.getDescuentoAplicado().doubleValue():0.0;
			
			final Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVenta.getPedidoVentaDetalleCollection();
			for(PedidoVentaDetalle pvd:pedidoVentaDetalleCollection){
				st += (pvd.getCantidad() * pvd.getPrecioVenta()); 
			}
			st = st / (1.0+pedidoVenta.getFactoriva());
			if(da > 0.0){
				st = st * (1.0 - da);
			}
            datosCFD.setSubtotal(st);
			
            datosCFD.setTotal(st * (1.0 + pedidoVenta.getFactoriva()));
            datosCFD.setLugarDeExpedicion("Mexico DF");

            receptor = new Receptor();
			
            receptor.setCalle(pedidoVenta.getCliente().getCalle());
            receptor.setNumExt(pedidoVenta.getCliente().getNumExterior());
            receptor.setNumInt(pedidoVenta.getCliente().getNumInterior());
            receptor.setColonia(pedidoVenta.getCliente().getPoblacion().getNombre());
            receptor.setContacto1("ULISES LEÓN RESENDIZ");
            receptor.setContacto2("LUCIANO LEÓN SANCHEZ");
            receptor.setCP(pedidoVenta.getCliente().getPoblacion().getCodigoPostal());
            receptor.setMunicipio(pedidoVenta.getCliente().getPoblacion().getMunicipioODelegacion());
            receptor.setNoCliente(String.valueOf(pedidoVenta.getCliente().getId()));
			final String[] telefonos = pedidoVenta.getCliente().getTelefonos().split(":");
            receptor.setTelefono1(telefonos[0]);
            receptor.setTelefono2(telefonos.length>1?telefonos[1]:"");
            receptor.setCiudad(pedidoVenta.getCliente().getPoblacion().getNombre());
            receptor.setEstado(pedidoVenta.getCliente().getPoblacion().getEntidadFederativa());
            receptor.setPais("Mexico");
            
			receptor.setEmail1(usuario);
            receptor.setEmail2("facturacion@perfumeriamarlen.com.mx");
            receptor.setEmail3("cdfdigifact@perfumeriamarlen.com.mx");
            
			receptor.setRFC(pedidoVenta.getCliente().getRfc());
            receptor.setRazonSocial(pedidoVenta.getCliente().getRazonSocial());

            conceptos = new ArrayOfAnyType();
			for(PedidoVentaDetalle pvd:pedidoVentaDetalleCollection){
				conceptoWS = new Concepto();
				
				conceptoWS.setCantidad(pvd.getCantidad());
				conceptoWS.setUnidad("PZA");
				conceptoWS.setDescripcion(pvd.getProducto().getNombre()+"/"+pvd.getProducto().getPresentacion()+"("+pvd.getProducto().getContenido()+" "+pvd.getProducto().getUnidadMedida()+")");
				double precioPVD_CFD = pvd.getPrecioVenta() / (1.0+pedidoVenta.getFactoriva());
				double importePVD_CFD = precioPVD_CFD * pvd.getCantidad();
				if(da>0.0){
					importePVD_CFD = importePVD_CFD * (1.0 - da);
				}
				conceptoWS.setPrecio(precioPVD_CFD);				
				conceptoWS.setImporte(importePVD_CFD);
				
				conceptos.getAnyType().add(conceptoWS);
			}
            
            impuestos = new ArrayOfAnyType();

            impuestoWS = new Impuesto();
            impuestoWS.setTasa(pedidoVenta.getFactoriva()*100.0);
            impuestoWS.setTipoImpuesto("IVA");
            impuestoWS.setImporte(st * pedidoVenta.getFactoriva());
            impuestos.getAnyType().add(impuestoWS);

            //impuestoWS = new Impuesto();
            //impuestoWS.setTasa(8);
            //impuestoWS.setTipoImpuesto("IVAR");
            //impuestoWS.setImporte(110.39);
            //impuestos.getAnyType().add(impuestoWS);

            xmlAddenda = "";
            //usuario    = "cfdsuc2@perfumeriamarlen.com.mx";
            //contrasena = "Pm@rl3n01";
            //======================================================================
            cfdVenta = new CfdVenta();
			try {
				System.out.println("-->> Invocacion a SICOFI: pedidoVentaId="+pedidoVenta.getId());

				String xml = port.generaCFD(usuario, contrasena, datosCFD, receptor, conceptos, impuestos, xmlAddenda);
				System.out.println("-->>OK recibido el XML desde digifact: mide "+xml.length()+" bytes");
				//FileOutputStream fosXML = new FileOutputStream("CFD_"+serie+".xml");
				//fosXML.write(xml.getBytes());
				//fosXML.flush();
				//fosXML.close();				
				//System.out.println(xml);

				cfdVenta.setContenidoOriginalXml(xml);
				cfdVenta.setUltimaActualizacion(new Date());
				System.out.println("-->>OK invocacion a DIGIFACT para pedidoVentaID="+pedidoVenta.getId());
				cfdVenta.setCallingErrorResult(null);
			}catch(Exception ex){
				System.out.println("-->>Error en invocacion a DIGIFACT para pedidoVentaID="+pedidoVenta.getId()+", Exception="+ex);
				final String localizedMessage = ex.getLocalizedMessage().trim();
				if(localizedMessage.length()>254){
					cfdVenta.setCallingErrorResult(localizedMessage.substring(0, 254));
				} else{
					cfdVenta.setCallingErrorResult(localizedMessage);
				}
				cfdVenta.setUltimaActualizacion(new Date());				
			}
			
        } catch (Exception e) {
            e.printStackTrace(System.err);
			
        }
		return cfdVenta;
    }    
}
