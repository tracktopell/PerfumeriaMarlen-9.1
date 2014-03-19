/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.PedidoVentaEstado;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author alfredo
 */
public class PedidoVentaEasyView {
	private PedidoVenta pedidoVenta;
	private Collection<PedidoVentaEstado> pedidoVentaEstadoCollection;
	private Collection<PedidoVentaEstado> pveTodos;
	
	private PedidoVentaEstado pveCapturado;
	private PedidoVentaEstado pveSincronizado;
	private PedidoVentaEstado pveVerificado;
	private PedidoVentaEstado pveSurtido;
	private PedidoVentaEstado pveFacturado;
	private PedidoVentaEstado pveRemisionado;	
	private PedidoVentaEstado pveEnviado;
	private PedidoVentaEstado pveEntregado;
	private PedidoVentaEstado pveDevuelto;
	//private PedidoVentaEstado pveVendiSuc;
	//private PedidoVentaEstado pveFactSuc;
	//private PedidoVentaEstado pveDevSuc;
	private PedidoVentaEstado pvePagado;
	private PedidoVentaEstado pveCancelado;
	
	public PedidoVentaEasyView(PedidoVenta pedidoVenta) {
		this.pedidoVenta = pedidoVenta;
		pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
		pveTodos = new ArrayList<PedidoVentaEstado> ();
	
		pveCapturado = new PedidoVentaEstado(-1);		
		pveSincronizado= new PedidoVentaEstado(-1);
		pveVerificado= new PedidoVentaEstado(-1);
		pveSurtido = new PedidoVentaEstado(-1);
		pveFacturado = new PedidoVentaEstado(-1);
		pveRemisionado= new PedidoVentaEstado(-1);
		pveEnviado = new PedidoVentaEstado(-1);
		pveEntregado = new PedidoVentaEstado(-1);
		pveDevuelto = new PedidoVentaEstado(-1);
		//pveVendiSuc = new PedidoVentaEstado(-1);
		//pveFactSuc = new PedidoVentaEstado(-1);
		//pveDevSuc = new PedidoVentaEstado(-1);
		pvePagado = new PedidoVentaEstado(-1);
		pveCancelado = new PedidoVentaEstado(-1);
		
		for(PedidoVentaEstado pve:pedidoVentaEstadoCollection) {
			if(pve.getEstado().getId().intValue() == Constants.ESTADO_CAPTURADO){
				pveCapturado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_SINCRONIZADO){
				pveSincronizado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_SINCRONIZADO){
				pveSincronizado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_VERIFICADO){
				pveVerificado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_SURTIDO){
				pveSurtido = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_FACTURADO){
				pveFacturado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_REMISIONADO){
				pveRemisionado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_ENVIADO){
				pveEnviado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_ENTREGADO){
				pveEntregado = pve;
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_DEVUELTO){
				pveDevuelto = pve;				
				/*
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_VENDIDO_SUCURSAL){
				pveVendiSuc = pve;				
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_FACTURADO_SUCURSAL){
				pveFactSuc = pve;				
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_DEVUELTO_SUCURSAL){
				pveDevSuc = pve;				
				*/ 
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_PAGADO){
				pvePagado = pve;				
			} else if(pve.getEstado().getId().intValue() == Constants.ESTADO_CANCELADO){
				pveCancelado = pve;
			} else {
				throw new IllegalStateException("Estado no encontrado:"+pve.getEstado());
			}
		}
		
		pveTodos.add(pveCapturado);		
		pveTodos.add(pveSincronizado);
		pveTodos.add(pveVerificado);
		pveTodos.add(pveSurtido);
		pveTodos.add(pveFacturado);
		pveTodos.add(pveRemisionado);
		pveTodos.add(pveEnviado);
		pveTodos.add(pveEntregado);
		pveTodos.add(pveDevuelto);
		//pveTodos.add(pveVendiSuc);
		//pveTodos.add(pveFactSuc);
		//pveTodos.add(pveDevSuc);
		pveTodos.add(pvePagado);
		pveTodos.add(pveCancelado);
		
	}

	/**
	 * @return the pedidoVenta
	 */
	public PedidoVenta getPedidoVenta() {
		return pedidoVenta;
	}

	/**
	 * @return the pveCapturado
	 */
	public PedidoVentaEstado getPveCapturado() {
		return pveCapturado;
	}

	/**
	 * @return the pveSincronizado
	 */
	public PedidoVentaEstado getPveSincronizado() {
		return pveSincronizado;
	}

	/**
	 * @return the pveVerificado
	 */
	public PedidoVentaEstado getPveVerificado() {
		return pveVerificado;
	}

	/**
	 * @return the pveSurtido
	 */
	public PedidoVentaEstado getPveSurtido() {
		return pveSurtido;
	}

	/**
	 * @return the pveFacturado
	 */
	public PedidoVentaEstado getPveFacturado() {
		return pveFacturado;
	}

	/**
	 * @return the pveEnviado
	 */
	public PedidoVentaEstado getPveEnviado() {
		return pveEnviado;
	}

	/**
	 * @return the pveEntregado
	 */
	public PedidoVentaEstado getPveEntregado() {
		return pveEntregado;
	}

	/**
	 * @return the pveDevuelto
	 */
	public PedidoVentaEstado getPveDevuelto() {
		return pveDevuelto;
	}

	/**
	 * @return the pveCancelado
	 */
	public PedidoVentaEstado getPveCancelado() {
		return pveCancelado;
	}

	/**
	 * @return the pveTodos
	 */
	public Collection<PedidoVentaEstado> getPveTodos() {
		return pveTodos;
	}
	
	public double getImporteTotal() {
		double t = 0.0;
		
		
		Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVenta.getPedidoVentaDetalleCollection();
		for(PedidoVentaDetalle pvd: pedidoVentaDetalleCollection){
			t += pvd.getCantidad() * pvd.getPrecioVenta();
		}
		
		if(pedidoVenta.getDescuentoAplicado() != null && pedidoVenta.getDescuentoAplicado().doubleValue()>0.0){
			t = t * (1.0 - pedidoVenta.getDescuentoAplicado());
		}
		
		return t;
	}
}
