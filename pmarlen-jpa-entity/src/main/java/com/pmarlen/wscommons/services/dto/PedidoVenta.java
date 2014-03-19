
package com.pmarlen.wscommons.services.dto;

import java.io.Serializable;
import java.util.Set;
import java.util.Collection;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Embeddable;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EmbeddedId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class for mapping DTO Entity of Table Pedido_Venta.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */

public class PedidoVenta implements java.io.Serializable {
    private static final long serialVersionUID = 1478682892;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * cliente id
    */
    private Cliente cliente;
    
    /**
    * forma de pago id
    */
    private FormaDePago formaDePago;
    
    /**
    * metodo de pago id
    */
    private MetodoDePago metodoDePago;
    
    /**
    * usuario id
    */
    private Usuario usuario;
    
    /**
    * almacen id
    */
    private Almacen almacen;
    
    /**
    * factoriva
    */
    private double factoriva;
    
    /**
    * comentarios
    */
    private String comentarios;
    
    /**
    * descuento aplicado
    */
    private Double descuentoAplicado;
    
    /**
    * facturable
    */
    private Integer facturable;
    
    /**
    * cfd venta id
    */
    private CfdVenta cfdVenta;
    
    /**
    * numero ticket
    */
    private String numeroTicket;
    
    /**
    * caja
    */
    private Integer caja;
    
    /**
    * importe recibido
    */
    private Double importeRecibido;
    
    /**
    * aprobacion bancaria tc
    */
    private String aprobacionBancariaTc;
    
    /**
    * mayoreo
    */
    private Integer mayoreo;
    
    /**
    * porcentaje descuento calculado
    */
    private Integer porcentajeDescuentoCalculado;
    
    /**
    * porcentaje descuento extra
    */
    private Integer porcentajeDescuentoExtra;
    
    private Collection<PedidoVentaEstado> pedidoVentaEstadoCollection;
    
    
    private Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection;
    
    
    private Collection<PedidoVentaPago> pedidoVentaPagoCollection;
    

    /** 
     * Default Constructor
     */
    public PedidoVenta() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoVenta( Integer id ) {
        this.id 	= 	id;

    }
    
    /**
     * Getters and Setters
     */
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer v) {
        this.id = v;
    }

    public Cliente getCliente () {
        return this.cliente;
    }

    public void setCliente(Cliente v) {
        this.cliente = v;
    }

    public FormaDePago getFormaDePago () {
        return this.formaDePago;
    }

    public void setFormaDePago(FormaDePago v) {
        this.formaDePago = v;
    }

    public MetodoDePago getMetodoDePago () {
        return this.metodoDePago;
    }

    public void setMetodoDePago(MetodoDePago v) {
        this.metodoDePago = v;
    }

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public Almacen getAlmacen () {
        return this.almacen;
    }

    public void setAlmacen(Almacen v) {
        this.almacen = v;
    }

    public double getFactoriva() {
        return this.factoriva;
    }

    public void setFactoriva(double v) {
        this.factoriva = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
    }

    public Double getDescuentoAplicado() {
        return this.descuentoAplicado;
    }

    public void setDescuentoAplicado(Double v) {
        this.descuentoAplicado = v;
    }

    public Integer getFacturable() {
        return this.facturable;
    }

    public void setFacturable(Integer v) {
        this.facturable = v;
    }

    public CfdVenta getCfdVenta () {
        return this.cfdVenta;
    }

    public void setCfdVenta(CfdVenta v) {
        this.cfdVenta = v;
    }

    public String getNumeroTicket() {
        return this.numeroTicket;
    }

    public void setNumeroTicket(String v) {
        this.numeroTicket = v;
    }

    public Integer getCaja() {
        return this.caja;
    }

    public void setCaja(Integer v) {
        this.caja = v;
    }

    public Double getImporteRecibido() {
        return this.importeRecibido;
    }

    public void setImporteRecibido(Double v) {
        this.importeRecibido = v;
    }

    public String getAprobacionBancariaTc() {
        return this.aprobacionBancariaTc;
    }

    public void setAprobacionBancariaTc(String v) {
        this.aprobacionBancariaTc = v;
    }

    public Integer getMayoreo() {
        return this.mayoreo;
    }

    public void setMayoreo(Integer v) {
        this.mayoreo = v;
    }

    public Integer getPorcentajeDescuentoCalculado() {
        return this.porcentajeDescuentoCalculado;
    }

    public void setPorcentajeDescuentoCalculado(Integer v) {
        this.porcentajeDescuentoCalculado = v;
    }

    public Integer getPorcentajeDescuentoExtra() {
        return this.porcentajeDescuentoExtra;
    }

    public void setPorcentajeDescuentoExtra(Integer v) {
        this.porcentajeDescuentoExtra = v;
    }

    
    public Collection<PedidoVentaEstado> getPedidoVentaEstadoCollection() {
        return this.pedidoVentaEstadoCollection;
    }
    
    
    public void setPedidoVentaEstadoCollection(Collection<PedidoVentaEstado>  v) {
        this.pedidoVentaEstadoCollection = v;
    }
    
    public Collection<PedidoVentaDetalle> getPedidoVentaDetalleCollection() {
        return this.pedidoVentaDetalleCollection;
    }
    
    
    public void setPedidoVentaDetalleCollection(Collection<PedidoVentaDetalle>  v) {
        this.pedidoVentaDetalleCollection = v;
    }
    
    public Collection<PedidoVentaPago> getPedidoVentaPagoCollection() {
        return this.pedidoVentaPagoCollection;
    }
    
    
    public void setPedidoVentaPagoCollection(Collection<PedidoVentaPago>  v) {
        this.pedidoVentaPagoCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoVenta)) {
            return false;
        }

    	PedidoVenta other = (PedidoVenta ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.PedidoVenta[id = "+id+ "]";
    }

}
