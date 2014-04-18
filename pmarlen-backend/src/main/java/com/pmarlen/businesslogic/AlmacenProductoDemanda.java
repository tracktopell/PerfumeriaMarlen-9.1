/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic;

import java.util.List;

/**
 *
 * @author alfredo
 */
public class AlmacenProductoDemanda {
	private int productoId;
	private String productoCodigoBarras;
	private int sumDemanda;
	private int almacenId;
	private int otrosPedidos;
	private List<PedidoVentaEnDemanda> pedidoVentaList;
	private int cantidadActual;
	private double precioVenta;

	public AlmacenProductoDemanda() {
	}

	public AlmacenProductoDemanda(int productoId, String productoCodigoBarras, int sumDemanda, int almacenId, int otrosPedidos, List<PedidoVentaEnDemanda> pedidoVentaList, int cantidadActual,double precioVenta) {
		this.productoId = productoId;
		this.productoCodigoBarras = productoCodigoBarras;
		this.sumDemanda = sumDemanda;
		this.almacenId = almacenId;
		this.otrosPedidos = otrosPedidos;
		this.pedidoVentaList = pedidoVentaList;
		this.cantidadActual = cantidadActual;
		this.precioVenta = precioVenta;
	}
	
	
	
	/**
	 * @return the productoId
	 */
	public int getProductoId() {
		return productoId;
	}

	/**
	 * @param productoId the productoId to set
	 */
	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	/**
	 * @return the sumDemanda
	 */
	public int getSumDemanda() {
		return sumDemanda;
	}

	/**
	 * @param sumDemanda the sumDemanda to set
	 */
	public void setSumDemanda(int sumDemanda) {
		this.sumDemanda = sumDemanda;
	}

	/**
	 * @return the almacenId
	 */
	public int getAlmacenId() {
		return almacenId;
	}

	/**
	 * @param almacenId the almacenId to set
	 */
	public void setAlmacenId(int almacenId) {
		this.almacenId = almacenId;
	}

	public int getOtrosPedidos() {
		return otrosPedidos;
	}

	
	public void setOtrosPedidos(int otrosPedidos) {
		this.otrosPedidos = otrosPedidos;
	}

	public int getCantidadActual() {
		return cantidadActual;
	}

	public void setCantidadActual(int cantidadActual) {
		this.cantidadActual = cantidadActual;
	}
	
	@Override
	public String toString() {
		return "AlmacenProductoDemanda{productoId="+productoId+", productoCodigoBarras="+productoCodigoBarras+
				",sumDemanda="+sumDemanda+",almacenId="+almacenId+", otrosPedidos="+otrosPedidos+", cantidadActual="+
				cantidadActual+", precioVenta="+precioVenta+"}";
	}

	/**
	 * @return the productoCodigoBarras
	 */
	public String getProductoCodigoBarras() {
		return productoCodigoBarras;
	}

	/**
	 * @param productoCodigoBarras the productoCodigoBarras to set
	 */
	public void setProductoCodigoBarras(String productoCodigoBarras) {
		this.productoCodigoBarras = productoCodigoBarras;
	}

	/**
	 * @return the pedidoVentaList
	 */
	public List<PedidoVentaEnDemanda> getPedidoVentaList() {
		return pedidoVentaList;
	}

	/**
	 * @param pedidoVentaList the pedidoVentaList to set
	 */
	public void setPedidoVentaList(List<PedidoVentaEnDemanda> pedidoVentaList) {
		this.pedidoVentaList = pedidoVentaList;
	}

	public double getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(double precioVenta) {
		this.precioVenta = precioVenta;
	}

}
