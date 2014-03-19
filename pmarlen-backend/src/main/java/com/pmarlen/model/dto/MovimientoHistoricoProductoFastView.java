/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.dto;

import com.pmarlen.model.Constants;
import java.util.Date;

/**
 *
 * @author alfredo
 */
public class MovimientoHistoricoProductoFastView {
/*
 ALMACEN_ID	PRODUCTO_ID	CODIGO_BARRAS	FECHA	TIPO_MOVIMIENTO_ID	CANTIDAD	COSTO	PRECIO	USUARIO_ID
 */	
	private Integer almacenId;
	private Integer productoId;
	private String  productoCodigoBarras;
	private Date    fecha;
	private Integer tipoMovimientoId;
	private String  tipoMovimientoDescripcion;
	private Integer cantidad;
	private Double  costo;
	private Double  precio;
	private String  usuarioId;
	private Integer saldo;

	public MovimientoHistoricoProductoFastView(Object[] rs) {
		this.almacenId					= ((Integer)rs[ 0 ]);
		this.productoId					= ((Integer)rs[ 1 ]);
		this.productoCodigoBarras		= rs[ 2 ].toString();
		this.fecha						= (Date)rs[ 3 ];
		this.tipoMovimientoId			= ((Integer)rs[ 4 ]);
		this.tipoMovimientoDescripcion	= rs[ 5 ].toString();
		this.cantidad					= ((Integer)rs[ 6 ]);
		this.costo						= ((Double)rs[ 7 ]);
		this.precio						= ((Double)rs[ 8 ]);
		this.usuarioId					= rs[ 9 ].toString();
		this.saldo						= 0;
	}
	
	/**
	 * @return the almacenId
	 */
	public Integer getAlmacenId() {
		return almacenId;
	}

	/**
	 * @param almacenId the almacenId to set
	 */
	public void setAlmacenId(Integer almacenId) {
		this.almacenId = almacenId;
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
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the tipoMovimientoId
	 */
	public Integer getTipoMovimientoId() {
		return tipoMovimientoId;
	}

	/**
	 * @param tipoMovimientoId the tipoMovimientoId to set
	 */
	public void setTipoMovimientoId(Integer tipoMovimientoId) {
		this.tipoMovimientoId = tipoMovimientoId;
	}

	/**
	 * @return the tipoMovimientoDescripcion
	 */
	public String getTipoMovimientoDescripcion() {
		return tipoMovimientoDescripcion;
	}

	/**
	 * @param tipoMovimientoDescripcion the tipoMovimientoDescripcion to set
	 */
	public void setTipoMovimientoDescripcion(String tipoMovimientoDescripcion) {
		this.tipoMovimientoDescripcion = tipoMovimientoDescripcion;
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the costo
	 */
	public Double getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(Double costo) {
		this.costo = costo;
	}

	/**
	 * @return the precio
	 */
	public Double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	/**
	 * @return the usuarioId
	 */
	public String getUsuarioId() {
		return usuarioId;
	}

	/**
	 * @param usuarioId the usuarioId to set
	 */
	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	public Integer getSaldo() {
		return saldo;
	}

	public boolean getEntrada(){
		final int tm = tipoMovimientoId.intValue();
		return(tm >= Constants.TIPO_MOV_ENTRADA_ALMACEN && tm <= Constants.TIPO_MOV_ENTRADA_ALMACEN_DEV);					
	}
	
	public boolean getSalida(){
		final int tm = tipoMovimientoId.intValue();
		return(tm >= Constants.TIPO_MOV_SALIDA_ALMACEN && tm <= Constants.TIPO_MOV_SALIDA_DEV);					
	}
	
}
