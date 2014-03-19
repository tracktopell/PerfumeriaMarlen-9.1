/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.Producto;

/**
 *
 * @author alfredo
 */
public class ProductoEnAlmacenesDetalle {

	private Producto producto;
	private AlmacenProducto almacenPrincipal;
	private AlmacenProducto almacenOportunidad;
	private AlmacenProducto almacenMerma;

	ProductoEnAlmacenesDetalle(Producto producto) {
		this.producto = producto;
		
		almacenPrincipal = new AlmacenProducto();
		almacenPrincipal.setCantidadActual(0);

		almacenOportunidad = new AlmacenProducto();
		almacenOportunidad.setCantidadActual(0);

		almacenMerma = new AlmacenProducto();
		almacenOportunidad.setCantidadActual(0);
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
	 * @return the almacenPrincipal
	 */
	public AlmacenProducto getAlmacenPrincipal() {
		return almacenPrincipal;
	}

	/**
	 * @param almacenPrincipal the almacenPrincipal to set
	 */
	public void setAlmacenPrincipal(AlmacenProducto almacenPrincipal) {
		this.almacenPrincipal = almacenPrincipal;
	}

	/**
	 * @return the almacenOportunidad
	 */
	public AlmacenProducto getAlmacenOportunidad() {
		return almacenOportunidad;
	}

	/**
	 * @param almacenOportunidad the almacenOportunidad to set
	 */
	public void setAlmacenOportunidad(AlmacenProducto almacenOportunidad) {
		this.almacenOportunidad = almacenOportunidad;
	}

	/**
	 * @return the almacenMerma
	 */
	public AlmacenProducto getAlmacenMerma() {
		return almacenMerma;
	}

	/**
	 * @param almacenMerma the almacenMerma to set
	 */
	public void setAlmacenMerma(AlmacenProducto almacenMerma) {
		this.almacenMerma = almacenMerma;
	}
}
