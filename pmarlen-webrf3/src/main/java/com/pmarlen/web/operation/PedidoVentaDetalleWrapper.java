package com.pmarlen.web.operation;

import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.Producto;

/**
 * PedidoVentaDetalleWrapper
 */
public class PedidoVentaDetalleWrapper {

    private PedidoVentaDetalle detalleVentaPedido;

    private int cantMax;
	
	private int cantDemanda;
	
	private int otrosPedidos;
    
    public PedidoVentaDetalleWrapper(PedidoVentaDetalle detalleVentaPedido){
        this.detalleVentaPedido = detalleVentaPedido;
		this.cantDemanda  = 0;
		this.otrosPedidos = 0;
    }

    public int getCantidad() {
        return this.detalleVentaPedido.getCantidad();
    }

    public void setCantidad(int cantidad) {
        this.detalleVentaPedido.setCantidad(cantidad);
    }

    public double getPrecioVenta() {
        return this.detalleVentaPedido.getPrecioVenta();
    }

    public void setPrecioVenta(double PrecioVenta) {
        this.detalleVentaPedido.setPrecioVenta(PrecioVenta);
    }

    public double getDescuentoAplicado() {
        return 0.0;
    }

    public void setDescuentoAplicado(double descuentoAplicado) {
        
    }

    public Producto getProducto() {
        return this.detalleVentaPedido.getProducto();
    }

    public void setProducto(Producto producto) {
        this.detalleVentaPedido.setProducto(producto);
    }

    public PedidoVentaDetalle getPedidoVentaDetalle() {
        return detalleVentaPedido;
    }

    public void setPedidoVentaDetalle(PedidoVentaDetalle detalleVentaPedido) {
        this.detalleVentaPedido = detalleVentaPedido;
    }

    public int getCantMax() {
        return cantMax;
    }

    public void setCantMax(int cantMax) {
        this.cantMax = cantMax;
    }

	public int getCantDemanda() {
		return cantDemanda;
	}

	public void setCantDemanda(int cantDemanda) {
		this.cantDemanda = cantDemanda;
	}

	public int getOtrosPedidos() {
		return otrosPedidos;
	}

	public void setOtrosPedidos(int otrosPedidos) {
		this.otrosPedidos = otrosPedidos;
	}
	
}
