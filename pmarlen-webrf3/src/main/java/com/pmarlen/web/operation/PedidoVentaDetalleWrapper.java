package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.AlmacenProductoDemanda;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.Producto;

/**
 * PedidoVentaDetalleWrapper
 */
public class PedidoVentaDetalleWrapper {

    private PedidoVentaDetalle detalleVentaPedido;
	
	private AlmacenProductoDemanda almacenProductoDemanda;
	
    public PedidoVentaDetalleWrapper(PedidoVentaDetalle detalleVentaPedido){
        this.detalleVentaPedido = detalleVentaPedido;		
    }

	public void setDetalleVentaPedido(PedidoVentaDetalle detalleVentaPedido) {
		this.detalleVentaPedido = detalleVentaPedido;
	}

	public PedidoVentaDetalle getDetalleVentaPedido() {
		return detalleVentaPedido;
	}
	
    
	public void setAlmacenProductoDemanda(AlmacenProductoDemanda almacenProductoDemanda) {
		this.almacenProductoDemanda = almacenProductoDemanda;
	}

	public AlmacenProductoDemanda getAlmacenProductoDemanda() {
		return almacenProductoDemanda;
	}	
}
