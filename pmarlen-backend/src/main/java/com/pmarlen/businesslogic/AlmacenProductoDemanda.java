/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic;

/**
 *
 * @author alfredo
 */
public class AlmacenProductoDemanda {
	private int productoId;
	private int sumDemanda;
	private int almacenId;
	private int otrosPedidos;
	
	public AlmacenProductoDemanda(int productoId, int sumDemanda, int almacenId,int otrosPedidos) {
		this.productoId = productoId;
		this.sumDemanda = sumDemanda;
		this.almacenId = almacenId;
		this.otrosPedidos = otrosPedidos;
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


	@Override
	public String toString() {
		return "AlmacenProductoDemanda{productoId="+productoId+",sumDemanda="+sumDemanda+",almacenId="+almacenId+", otrosPedidos="+otrosPedidos+"}";
	}

	
}
