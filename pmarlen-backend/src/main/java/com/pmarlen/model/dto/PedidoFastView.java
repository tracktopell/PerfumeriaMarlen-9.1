/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author alfredo
 */
public class PedidoFastView {
	private Integer pedidoId;
	private Integer estadoActualId;		
	private Date    estadoActualFecha;
	private String  usuarioNombreCompleto;
	private Integer numElementos;
	private String sucursalNombre;
	private Integer tipoAlmacen;
	private String  clienteRFC;
	private String  clienteRazonSocial;
	private String  clienteNombreEstablecimiento;
	private String  formaDePago;
	private String  medioDePago;
	private Double  descuentoAplicado;
	private Integer porcentajeDescuentoCalculado;
	private Integer porcentajeDescuentoExtra;
	private Double  importePedido;
	private Double  importeFinalPedido;
	private String  estadoActualDescripcion;
	private Double  factorIva;
	private Integer cfdId;
	private Date    cfdUltimaActualizacion;
	private Integer cfdContenidoXMLOriginal;
	private String  cfdErrorCallingresult;	
	
	private static DecimalFormat df = new DecimalFormat("###,###,##0.00");

	public PedidoFastView(int id) {
		this.pedidoId = id;
	}

	public PedidoFastView(Object[] rs) {
		
		this.pedidoId							= ((Integer)rs[0]);
		this.estadoActualId						= ((Integer)rs[1]);
		this.estadoActualFecha					= ((Timestamp)rs[2]);
		this.usuarioNombreCompleto				= rs[3].toString();
		this.numElementos						= ((BigInteger)rs[4]).intValue();
		this.sucursalNombre						= rs[5].toString();
		this.tipoAlmacen						= ((Integer)rs[6]);
		this.clienteRFC							= rs[7].toString();
		this.clienteRazonSocial					= rs[8].toString();
		this.clienteNombreEstablecimiento		= rs[9].toString();
		this.formaDePago						= rs[10].toString();
		this.medioDePago						= rs[11].toString();
		this.descuentoAplicado					= ((Double)rs[12]);
		this.porcentajeDescuentoCalculado		= ((Integer)rs[13]);
		this.porcentajeDescuentoExtra			= ((Integer)rs[14]);
		this.importePedido						= ((Double)rs[15]);
		this.importeFinalPedido					= ((Double)rs[16]);
		this.estadoActualDescripcion			= ((String)rs[17]);
		this.factorIva							= ((Double)rs[18]);
		this.cfdId								= ((Integer)rs[19]);
		this.cfdUltimaActualizacion				= ((Timestamp)rs[20]);
		this.cfdContenidoXMLOriginal			= ((Integer)rs[21]);
		this.cfdErrorCallingresult				= ((String)rs[22]);		
	}

	@Override
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch (Exception ex) {
			return "PedidoFastView";
		}
	}
	
	

	
	/**
	 * @return the pedidoId
	 */
	public Integer getPedidoId() {
		return pedidoId;
	}

	/**
	 * @param pedidoId the pedidoId to set
	 */
	public void setPedidoId(Integer pedidoId) {
		this.pedidoId = pedidoId;
	}

	/**
	 * @return the estadoActualId
	 */
	public Integer getEstadoActualId() {
		return estadoActualId;
	}

	/**
	 * @param estadoActualId the estadoActualId to set
	 */
	public void setEstadoActualId(Integer estadoActualId) {
		this.estadoActualId = estadoActualId;
	}

	/**
	 * @return the estadoActualFecha
	 */
	public Date getEstadoActualFecha() {
		return estadoActualFecha;
	}

	/**
	 * @param estadoActualFecha the estadoActualFecha to set
	 */
	public void setEstadoActualFecha(Date estadoActualFecha) {
		this.estadoActualFecha = estadoActualFecha;
	}

	/**
	 * @return the usuarioNombreCompleto
	 */
	public String getUsuarioNombreCompleto() {
		return usuarioNombreCompleto;
	}

	/**
	 * @param usuarioNombreCompleto the usuarioNombreCompleto to set
	 */
	public void setUsuarioNombreCompleto(String usuarioNombreCompleto) {
		this.usuarioNombreCompleto = usuarioNombreCompleto;
	}

	/**
	 * @return the numElementos
	 */
	public Integer getNumElementos() {
		return numElementos;
	}

	/**
	 * @param numElementos the numElementos to set
	 */
	public void setNumElementos(Integer numElementos) {
		this.numElementos = numElementos;
	}

	/**
	 * @return the sucursalNombre
	 */
	public String getSucursalNombre() {
		return sucursalNombre;
	}

	/**
	 * @param sucursalNombre the sucursalNombre to set
	 */
	public void setSucursalNombre(String sucursalNombre) {
		this.sucursalNombre = sucursalNombre;
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
	 * @return the clienteRFC
	 */
	public String getClienteRFC() {
		return clienteRFC;
	}

	/**
	 * @param clienteRFC the clienteRFC to set
	 */
	public void setClienteRFC(String clienteRFC) {
		this.clienteRFC = clienteRFC;
	}

	/**
	 * @return the clienteRazonSocial
	 */
	public String getClienteRazonSocial() {
		return clienteRazonSocial;
	}

	/**
	 * @param clienteRazonSocial the clienteRazonSocial to set
	 */
	public void setClienteRazonSocial(String clienteRazonSocial) {
		this.clienteRazonSocial = clienteRazonSocial;
	}

	/**
	 * @return the clienteNombreEstablecimiento
	 */
	public String getClienteNombreEstablecimiento() {
		return clienteNombreEstablecimiento;
	}

	/**
	 * @param clienteNombreEstablecimiento the clienteNombreEstablecimiento to set
	 */
	public void setClienteNombreEstablecimiento(String clienteNombreEstablecimiento) {
		this.clienteNombreEstablecimiento = clienteNombreEstablecimiento;
	}

	/**
	 * @return the formaDePago
	 */
	public String getFormaDePago() {
		return formaDePago;
	}

	/**
	 * @param formaDePago the formaDePago to set
	 */
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	/**
	 * @return the medioDePago
	 */
	public String getMedioDePago() {
		return medioDePago;
	}

	/**
	 * @param medioDePago the medioDePago to set
	 */
	public void setMedioDePago(String medioDePago) {
		this.medioDePago = medioDePago;
	}

	/**
	 * @return the descuentoAplicado
	 */
	public Double getDescuentoAplicado() {
		return descuentoAplicado;
	}

	/**
	 * @param descuentoAplicado the descuentoAplicado to set
	 */
	public void setDescuentoAplicado(Double descuentoAplicado) {
		this.descuentoAplicado = descuentoAplicado;
	}

	/**
	 * @return the porcentajeDescuentoCalculado
	 */
	public Integer getPorcentajeDescuentoCalculado() {
		return porcentajeDescuentoCalculado;
	}

	/**
	 * @param porcentajeDescuentoCalculado the porcentajeDescuentoCalculado to set
	 */
	public void setPorcentajeDescuentoCalculado(Integer porcentajeDescuentoCalculado) {
		this.porcentajeDescuentoCalculado = porcentajeDescuentoCalculado;
	}

	/**
	 * @return the porcentajeDescuentoExtra
	 */
	public Integer getPorcentajeDescuentoExtra() {
		return porcentajeDescuentoExtra;
	}

	/**
	 * @param porcentajeDescuentoExtra the porcentajeDescuentoExtra to set
	 */
	public void setPorcentajeDescuentoExtra(Integer porcentajeDescuentoExtra) {
		this.porcentajeDescuentoExtra = porcentajeDescuentoExtra;
	}

	/**
	 * @return the importePedido
	 */
	public Double getImportePedido() {
		return importePedido;
	}
	
	public String getImportePedidoFormatted() {
		return df.format(importePedido);
	}

	/**
	 * @param importePedido the importePedido to set
	 */
	public void setImportePedido(Double importePedido) {
		this.importePedido = importePedido;
	}

	/**
	 * @return the importeFinalPedido
	 */
	public Double getImporteFinalPedido() {
		return importeFinalPedido;
	}
	
	/**
	 * @return the importeFinalPedido
	 */
	public String getImporteFinalPedidoFormatted() {
		return df.format(importeFinalPedido);
	}

	/**
	 * @param importeFinalPedido the importeFinalPedido to set
	 */
	public void setImporteFinalPedido(Double importeFinalPedido) {
		this.importeFinalPedido = importeFinalPedido;
	}

	public void setEstadoActualDescripcion(String estadoActualDescripcion) {
		this.estadoActualDescripcion = estadoActualDescripcion;
	}

	public String getEstadoActualDescripcion() {
		return estadoActualDescripcion;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.pedidoId == ((PedidoFastView)obj).pedidoId;
	}

	/**
	 * @return the cfdId
	 */
	public Integer getCfdId() {
		return cfdId;
	}

	/**
	 * @param cfdId the cfdId to set
	 */
	public void setCfdId(Integer cfdId) {
		this.cfdId = cfdId;
	}

	/**
	 * @return the cfdUltimaActualizacion
	 */
	public Date getCfdUltimaActualizacion() {
		return cfdUltimaActualizacion;
	}

	/**
	 * @param cfdUltimaActualizacion the cfdUltimaActualizacion to set
	 */
	public void setCfdUltimaActualizacion(Date cfdUltimaActualizacion) {
		this.cfdUltimaActualizacion = cfdUltimaActualizacion;
	}

	/**
	 * @return the cfdContenidoXMLOriginal
	 */
	public Integer getCfdContenidoXMLOriginal() {
		return cfdContenidoXMLOriginal;
	}

	/**
	 * @param cfdContenidoXMLOriginal the cfdContenidoXMLOriginal to set
	 */
	public void setCfdContenidoXMLOriginal(Integer cfdContenidoXMLOriginal) {
		this.cfdContenidoXMLOriginal = cfdContenidoXMLOriginal;
	}

	/**
	 * @return the cfdErrorCallingresult
	 */
	public String getCfdErrorCallingresult() {
		return cfdErrorCallingresult;
	}

	/**
	 * @param cfdErrorCallingresult the cfdErrorCallingresult to set
	 */
	public void setCfdErrorCallingresult(String cfdErrorCallingresult) {
		this.cfdErrorCallingresult = cfdErrorCallingresult;
	}
	
}
