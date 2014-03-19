/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.dto;

import com.pmarlen.model.beans.Multimedio;
import java.util.Collection;

/**
 *
 * @author alfredo
 */
public class ProductoEnAlmacenFastView {
	private Integer id;
	private String  codigoBarras;
	private String  nombre;
	private String  presentacion;
	private Double  precioBase;
	private String  marca;
	private String  linea;
	private int unidadesPorCaja;
    private String unidadMedida;
    private String contenido;
    private Double   precioBaseAlmacen;
	
	private Integer  principalActual;
	private Double   principalPV;
	private Double   principalPM;	
	private Integer  oportunidadActual;
	private Double   oportunidadPV;
	
	public ProductoEnAlmacenFastView(Integer id, String codigoBarras, String nombre, String presentacion, Double precioBase, String marca, String linea, Integer principalActual, Double principalPV, Double principalPM,Integer oportunidadActual, Double oportunidadPV) {
		this.id = id;
		this.codigoBarras = codigoBarras;
		this.nombre = nombre;
		this.presentacion = presentacion;
		this.precioBase = precioBase;
		this.marca = marca;
		this.linea = linea;
		this.principalActual = principalActual;
		this.principalPV = principalPV;
		this.principalPM = principalPM;
		this.oportunidadActual = oportunidadActual;
		this.oportunidadPV = oportunidadPV;
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the codigoBarras
	 */
	public String getCodigoBarras() {
		return codigoBarras;
	}

	/**
	 * @param codigoBarras the codigoBarras to set
	 */
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * @param presentacion the presentacion to set
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	/**
	 * @return the precioBase
	 */
	public Double getPrecioBase() {
		return precioBase;
	}

	/**
	 * @param precioBase the precioBase to set
	 */
	public void setPrecioBase(Double precioBase) {
		this.precioBase = precioBase;
	}

	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}

	/**
	 * @return the linea
	 */
	public String getLinea() {
		return linea;
	}

	/**
	 * @param linea the linea to set
	 */
	public void setLinea(String linea) {
		this.linea = linea;
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
	 * @return the principalPM
	 */
	public Double getPrincipalPM() {
		return principalPM;
	}

	/**
	 * @param principalPM the principalPM to set
	 */
	public void setPrincipalPM(Double principalPM) {
		this.principalPM = principalPM;
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

	/**
	 * @return the unidadesPorCaja
	 */
	public int getUnidadesPorCaja() {
		return unidadesPorCaja;
	}

	/**
	 * @param unidadesPorCaja the unidadesPorCaja to set
	 */
	public void setUnidadesPorCaja(int unidadesPorCaja) {
		this.unidadesPorCaja = unidadesPorCaja;
	}

	/**
	 * @return the unidadMedida
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida the unidadMedida to set
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	/**
	 * @return the precioBaseAlmacen
	 */
	public Double getPrecioBaseAlmacen() {
		return precioBaseAlmacen;
	}

	/**
	 * @param precioBaseAlmacen the precioBaseAlmacen to set
	 */
	public void setPrecioBaseAlmacen(Double precioBaseAlmacen) {
		this.precioBaseAlmacen = precioBaseAlmacen;
	}
}
