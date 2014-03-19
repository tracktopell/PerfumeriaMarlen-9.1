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
public class MovimientoEntreAlmacenes {
	private Producto producto;
	
	private int		 origenCantidadActual;
	private double   origenPV;
	private double   origenPM;
	
	private int      destinoActual;
	private double   destinoPV;
	private double   destinoPM;
	
	private int  destinoSurtir;
	
	public MovimientoEntreAlmacenes(){
	}

	public MovimientoEntreAlmacenes(Producto producto, int origenCantidadActual, double origenPV, double origenPM, int destinoActual, double destinoPV, double destinoPM, int destinoSurtir) {
		this.producto = producto;
		this.origenCantidadActual = origenCantidadActual;
		this.origenPV = origenPV;
		this.origenPM = origenPM;
		this.destinoActual = destinoActual;
		this.destinoPV = destinoPV;
		this.destinoPM = destinoPM;
		this.destinoSurtir = destinoSurtir;
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
	 * @return the origenCantidadActual
	 */
	public int getOrigenCantidadActual() {
		return origenCantidadActual;
	}

	/**
	 * @param origenCantidadActual the origenCantidadActual to set
	 */
	public void setOrigenCantidadActual(int origenCantidadActual) {
		this.origenCantidadActual = origenCantidadActual;
	}

	/**
	 * @return the origenPV
	 */
	public double getOrigenPV() {
		return origenPV;
	}

	/**
	 * @param origenPV the origenPV to set
	 */
	public void setOrigenPV(double origenPV) {
		this.origenPV = origenPV;
	}

	/**
	 * @return the destinoActual
	 */
	public int getDestinoActual() {
		return destinoActual;
	}

	/**
	 * @param destinoActual the destinoActual to set
	 */
	public void setDestinoActual(int destinoActual) {
		this.destinoActual = destinoActual;
	}

	/**
	 * @return the destinoPV
	 */
	public double getDestinoPV() {
		return destinoPV;
	}

	/**
	 * @param destinoPV the destinoPV to set
	 */
	public void setDestinoPV(double destinoPV) {
		this.destinoPV = destinoPV;
	}

	/**
	 * @return the destinoSurtir
	 */
	public int getDestinoSurtir() {
		return destinoSurtir;
	}

	/**
	 * @param destinoSurtir the destinoSurtir to set
	 */
	public void setDestinoSurtir(int destinoSurtir) {
		this.destinoSurtir = destinoSurtir;
	}

	/**
	 * @return the origenPM
	 */
	public double getOrigenPM() {
		return origenPM;
	}

	/**
	 * @param origenPM the origenPM to set
	 */
	public void setOrigenPM(double origenPM) {
		this.origenPM = origenPM;
	}

	/**
	 * @return the destinoPM
	 */
	public double getDestinoPM() {
		return destinoPM;
	}

	/**
	 * @param destinoPM the destinoPM to set
	 */
	public void setDestinoPM(double destinoPM) {
		this.destinoPM = destinoPM;
	}
		
}
