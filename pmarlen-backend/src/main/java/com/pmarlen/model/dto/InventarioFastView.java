/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.dto;

import com.pmarlen.web.model.FileUploaded;
import java.util.ArrayList;

/**
 *
 * @author alfredo
 */
public class InventarioFastView {
	/*
SELECT 
S.ID SUCURSAL_ID,A.ID ALMACEN_ID,A.TIPO_ALMACEN,P.ID,P.CODIGO_BARRAS,P.NOMBRE,
P.PRESENTACION,M.NOMBRE MARCA_NOMBRE,I.NOMBRE INDUSTRIA_NOMBRE,L.NOMBRE LINEA_NOMBRE,
AP.CANTIDAD_ACTUAL,AP.PRECIO_VENTA,AP.COSTO,AP.PRECIO_MAYOREO	 
	 */
	private Integer sucursalId;
	private Integer almcecenId;
	private Integer tipoAlmacen;
	private Integer productoId;
	private String productoCodigoBarras;
	private String productoNombre;
	private String productoPresentacion;
	private String marcaNombre;
	private String inductriaNombre;
	private String lineaNombre;
	private Integer almacenProductoCantidadActual;
	private Double almacenProductoPrecioVenta;
	private Double almacenProductoPrecioMayoreo;
	private FileUploaded fileUploaded;

	public InventarioFastView(Object[] rs) {		
		this.sucursalId = ((Integer)rs[ 0 ]);
		this.almcecenId = ((Integer)rs[ 1 ]);
		this.tipoAlmacen = ((Integer)rs[ 2 ]);
		this.productoId = ((Integer)rs[ 3 ]);	
		this.productoCodigoBarras = rs[ 4 ].toString();
		this.productoNombre = rs[ 5 ].toString();
		this.productoPresentacion = rs[ 6 ].toString();
		this.marcaNombre = rs[ 7 ].toString();
		this.inductriaNombre = rs[ 8 ].toString();
		this.lineaNombre = rs[ 9 ].toString();
		this.almacenProductoCantidadActual = ((Integer)rs[ 10 ]);
		this.almacenProductoPrecioVenta = ((Double)rs[ 11 ]);
		this.almacenProductoPrecioMayoreo = ((Double)rs[ 12 ]);
	}
	
	@Override
	public boolean equals(Object obj) {
		return	this.productoId == ((InventarioFastView)obj).productoId && 
				this.almcecenId == ((InventarioFastView)obj).almcecenId;
	}

	/**
	 * @return the sucursalId
	 */
	public Integer getSucursalId() {
		return sucursalId;
	}

	/**
	 * @param sucursalId the sucursalId to set
	 */
	public void setSucursalId(Integer sucursalId) {
		this.sucursalId = sucursalId;
	}

	/**
	 * @return the almcecenId
	 */
	public Integer getAlmcecenId() {
		return almcecenId;
	}

	/**
	 * @param almcecenId the almcecenId to set
	 */
	public void setAlmcecenId(Integer almcecenId) {
		this.almcecenId = almcecenId;
	}

	/**
	 * @return the tipoAlmacen
	 */
	public Integer getTipoAlmacen() {
		return tipoAlmacen;
	}

	/**
	 * @param tipoAlmacen the tipoAlmacen to set
	 */
	public void setTipoAlmacen(Integer tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}

	/**
	 * @return the productoId
	 */
	public Integer getProductoId() {
		return productoId;
	}

	/**
	 * @param productoId the productoId to set
	 */
	public void setProductoId(Integer productoId) {
		this.productoId = productoId;
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
	 * @return the productoNombre
	 */
	public String getProductoNombre() {
		return productoNombre;
	}

	/**
	 * @param productoNombre the productoNombre to set
	 */
	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	/**
	 * @return the productoPresentacion
	 */
	public String getProductoPresentacion() {
		return productoPresentacion;
	}

	/**
	 * @param productoPresentacion the productoPresentacion to set
	 */
	public void setProductoPresentacion(String productoPresentacion) {
		this.productoPresentacion = productoPresentacion;
	}

	/**
	 * @return the marcaNombre
	 */
	public String getMarcaNombre() {
		return marcaNombre;
	}

	/**
	 * @param marcaNombre the marcaNombre to set
	 */
	public void setMarcaNombre(String marcaNombre) {
		this.marcaNombre = marcaNombre;
	}

	/**
	 * @return the inductriaNombre
	 */
	public String getInductriaNombre() {
		return inductriaNombre;
	}

	/**
	 * @param inductriaNombre the inductriaNombre to set
	 */
	public void setInductriaNombre(String inductriaNombre) {
		this.inductriaNombre = inductriaNombre;
	}

	/**
	 * @return the lineaNombre
	 */
	public String getLineaNombre() {
		return lineaNombre;
	}

	/**
	 * @param lineaNombre the lineaNombre to set
	 */
	public void setLineaNombre(String lineaNombre) {
		this.lineaNombre = lineaNombre;
	}

	/**
	 * @return the almacenProductoCantidadActual
	 */
	public Integer getAlmacenProductoCantidadActual() {
		return almacenProductoCantidadActual;
	}

	/**
	 * @param almacenProductoCantidadActual the almacenProductoCantidadActual to set
	 */
	public void setAlmacenProductoCantidadActual(Integer almacenProductoCantidadActual) {
		this.almacenProductoCantidadActual = almacenProductoCantidadActual;
	}

	/**
	 * @return the almacenProductoPrecioVenta
	 */
	public Double getAlmacenProductoPrecioVenta() {
		return almacenProductoPrecioVenta;
	}

	/**
	 * @param almacenProductoPrecioVenta the almacenProductoPrecioVenta to set
	 */
	public void setAlmacenProductoPrecioVenta(Double almacenProductoPrecioVenta) {
		this.almacenProductoPrecioVenta = almacenProductoPrecioVenta;
	}

	/**
	 * @return the almacenProductoPrecioMayoreo
	 */
	public Double getAlmacenProductoPrecioMayoreo() {
		return almacenProductoPrecioMayoreo;
	}

	/**
	 * @param almacenProductoPrecioMayoreo the almacenProductoPrecioMayoreo to set
	 */
	public void setAlmacenProductoPrecioMayoreo(Double almacenProductoPrecioMayoreo) {
		this.almacenProductoPrecioMayoreo = almacenProductoPrecioMayoreo;
	}

	public void setFileUploaded(FileUploaded fileUploaded) {
		this.fileUploaded = fileUploaded;
	}

	public FileUploaded getFileUploaded() {
		return fileUploaded;
	}
	
}
