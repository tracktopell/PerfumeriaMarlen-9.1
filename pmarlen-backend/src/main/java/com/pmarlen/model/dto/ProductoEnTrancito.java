/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.dto;

import com.pmarlen.model.beans.Producto;

/**
 *
 * @author alfredo
 */
public class ProductoEnTrancito {
	private Producto producto;
	
	private Integer  principalActual;
	private Integer  principalEntrancito;
	private Double   principalPV;
	
	
	private Integer  oportunidadActual;
	private Integer  oportunidadEntrancito;
	private Double   oportunidadPV;

	public ProductoEnTrancito(Producto producto) {
		this.producto = producto;
	}
	
	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	/**
	 * @return the principalActual
	 */
	public Integer getPrincipalActual() {
		return principalActual;
	}

	/**
	 * @param principalActual the principalActual to set
	 */
	public void setPrincipalActual(Integer principalActual) {
		this.principalActual = principalActual;
	}

	/**
	 * @return the principalEntrancito
	 */
	public Integer getPrincipalEntrancito() {
		return principalEntrancito;
	}

	/**
	 * @param principalEntrancito the principalEntrancito to set
	 */
	public void setPrincipalEntrancito(Integer principalEntrancito) {
		this.principalEntrancito = principalEntrancito;
	}

	/**
	 * @return the oportunidadActual
	 */
	public Integer getOportunidadActual() {
		return oportunidadActual;
	}

	/**
	 * @param oportunidadActual the oportunidadActual to set
	 */
	public void setOportunidadActual(Integer oportunidadActual) {
		this.oportunidadActual = oportunidadActual;
	}

	/**
	 * @return the oportunidadEntrancito
	 */
	public Integer getOportunidadEntrancito() {
		return oportunidadEntrancito;
	}

	/**
	 * @param oportunidadEntrancito the oportunidadEntrancito to set
	 */
	public void setOportunidadEntrancito(Integer oportunidadEntrancito) {
		this.oportunidadEntrancito = oportunidadEntrancito;
	}

	/**
	 * @return the principalPV
	 */
	public Double getPrincipalPV() {
		return principalPV;
	}

	/**
	 * @param principalPV the principalPV to set
	 */
	public void setPrincipalPV(Double principalPV) {
		this.principalPV = principalPV;
	}

	/**
	 * @return the oportunidadPV
	 */
	public Double getOportunidadPV() {
		return oportunidadPV;
	}

	/**
	 * @param oportunidadPV the oportunidadPV to set
	 */
	public void setOportunidadPV(Double oportunidadPV) {
		this.oportunidadPV = oportunidadPV;
	}
}
