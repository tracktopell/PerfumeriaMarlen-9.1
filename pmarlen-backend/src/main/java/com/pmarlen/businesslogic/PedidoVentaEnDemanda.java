/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic;

/**
 *
 * @author alfredo
 */
public class PedidoVentaEnDemanda {
	private int pedidoVentaId;	
	private int edtadoId;
	private int cantidadPedida;

	public PedidoVentaEnDemanda() {
	}

	public PedidoVentaEnDemanda(int pedidoVentaId, int edtadoId, int cantidadPedida) {
		this.pedidoVentaId = pedidoVentaId;
		this.edtadoId = edtadoId;
		this.cantidadPedida = cantidadPedida;
	}

	/**
	 * @return the pedidoVentaId
	 */
	public int getPedidoVentaId() {
		return pedidoVentaId;
	}

	/**
	 * @param pedidoVentaId the pedidoVentaId to set
	 */
	public void setPedidoVentaId(int pedidoVentaId) {
		this.pedidoVentaId = pedidoVentaId;
	}

	/**
	 * @return the edtadoId
	 */
	public int getEdtadoId() {
		return edtadoId;
	}

	/**
	 * @param edtadoId the edtadoId to set
	 */
	public void setEdtadoId(int edtadoId) {
		this.edtadoId = edtadoId;
	}

	public int getCantidadPedida() {
		return cantidadPedida;
	}

	public void setCantidadPedida(int cantidadPedida) {
		this.cantidadPedida = cantidadPedida;
	}
	
	
}
