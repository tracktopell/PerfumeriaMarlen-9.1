
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
 * Class for mapping DTO Entity of Table Producto.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */

public class Producto implements java.io.Serializable {
    private static final long serialVersionUID = 334281416;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * codigo barras
    */
    private String codigoBarras;
    
    /**
    * marca id
    */
    private Marca marca;
    
    /**
    * nombre
    */
    private String nombre;
    
    /**
    * presentacion
    */
    private String presentacion;
    
    /**
    * unidades por caja
    */
    private int unidadesPorCaja;
    
    /**
    * unidad medida
    */
    private String unidadMedida;
    
    /**
    * costo
    */
    private double costo;
    
    /**
    * costo venta
    */
    private double costoVenta;
    
    /**
    * contenido
    */
    private String contenido;
    
    /**
    * abrebiatura
    */
    private String abrebiatura;
    
    /**
    * cajas por empaque
    */
    private Integer cajasPorEmpaque;
    
    /**
    * empaques por cama
    */
    private Integer empaquesPorCama;
    
    /**
    * camas por tarima
    */
    private Integer camasPorTarima;
    
    private Collection<NotaCreditoDetalle> notaCreditoDetalleCollection;
    
    
    private Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection;
    
    
    private Collection<EntradaAlmacenDetalle> entradaAlmacenDetalleCollection;
    
    
    private Collection<ProveedorProducto> proveedorProductoCollection;
    
    
    private Collection<MovimientoOperativoAlmacenDetalle> movimientoOperativoAlmacenDetalleCollection;
    
    
    private Collection<AlmacenProducto> almacenProductoCollection;
    
    
    private Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection;
    
    
    private Collection<Multimedio> multimedioCollection;
    

    /** 
     * Default Constructor
     */
    public Producto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Producto( Integer id ) {
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

    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    public void setCodigoBarras(String v) {
        this.codigoBarras = v;
    }

    public Marca getMarca () {
        return this.marca;
    }

    public void setMarca(Marca v) {
        this.marca = v;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    public String getPresentacion() {
        return this.presentacion;
    }

    public void setPresentacion(String v) {
        this.presentacion = v;
    }

    public int getUnidadesPorCaja() {
        return this.unidadesPorCaja;
    }

    public void setUnidadesPorCaja(int v) {
        this.unidadesPorCaja = v;
    }

    public String getUnidadMedida() {
        return this.unidadMedida;
    }

    public void setUnidadMedida(String v) {
        this.unidadMedida = v;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double v) {
        this.costo = v;
    }

    public double getCostoVenta() {
        return this.costoVenta;
    }

    public void setCostoVenta(double v) {
        this.costoVenta = v;
    }

    public String getContenido() {
        return this.contenido;
    }

    public void setContenido(String v) {
        this.contenido = v;
    }

    public String getAbrebiatura() {
        return this.abrebiatura;
    }

    public void setAbrebiatura(String v) {
        this.abrebiatura = v;
    }

    public Integer getCajasPorEmpaque() {
        return this.cajasPorEmpaque;
    }

    public void setCajasPorEmpaque(Integer v) {
        this.cajasPorEmpaque = v;
    }

    public Integer getEmpaquesPorCama() {
        return this.empaquesPorCama;
    }

    public void setEmpaquesPorCama(Integer v) {
        this.empaquesPorCama = v;
    }

    public Integer getCamasPorTarima() {
        return this.camasPorTarima;
    }

    public void setCamasPorTarima(Integer v) {
        this.camasPorTarima = v;
    }

    
    public Collection<NotaCreditoDetalle> getNotaCreditoDetalleCollection() {
        return this.notaCreditoDetalleCollection;
    }
    
    
    public void setNotaCreditoDetalleCollection(Collection<NotaCreditoDetalle>  v) {
        this.notaCreditoDetalleCollection = v;
    }
    
    public Collection<PedidoVentaDetalle> getPedidoVentaDetalleCollection() {
        return this.pedidoVentaDetalleCollection;
    }
    
    
    public void setPedidoVentaDetalleCollection(Collection<PedidoVentaDetalle>  v) {
        this.pedidoVentaDetalleCollection = v;
    }
    
    public Collection<EntradaAlmacenDetalle> getEntradaAlmacenDetalleCollection() {
        return this.entradaAlmacenDetalleCollection;
    }
    
    
    public void setEntradaAlmacenDetalleCollection(Collection<EntradaAlmacenDetalle>  v) {
        this.entradaAlmacenDetalleCollection = v;
    }
    
    public Collection<ProveedorProducto> getProveedorProductoCollection() {
        return this.proveedorProductoCollection;
    }
    
    
    public void setProveedorProductoCollection(Collection<ProveedorProducto>  v) {
        this.proveedorProductoCollection = v;
    }
    
    public Collection<MovimientoOperativoAlmacenDetalle> getMovimientoOperativoAlmacenDetalleCollection() {
        return this.movimientoOperativoAlmacenDetalleCollection;
    }
    
    
    public void setMovimientoOperativoAlmacenDetalleCollection(Collection<MovimientoOperativoAlmacenDetalle>  v) {
        this.movimientoOperativoAlmacenDetalleCollection = v;
    }
    
    public Collection<AlmacenProducto> getAlmacenProductoCollection() {
        return this.almacenProductoCollection;
    }
    
    
    public void setAlmacenProductoCollection(Collection<AlmacenProducto>  v) {
        this.almacenProductoCollection = v;
    }
    
    public Collection<MovimientoHistoricoProducto> getMovimientoHistoricoProductoCollection() {
        return this.movimientoHistoricoProductoCollection;
    }
    
    
    public void setMovimientoHistoricoProductoCollection(Collection<MovimientoHistoricoProducto>  v) {
        this.movimientoHistoricoProductoCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Multimedio>
    
    public Collection<Multimedio> getMultimedioCollection() {
        return this.multimedioCollection;
    }
    
    
    public void setMultimedioCollection(Collection<Multimedio>  v) {
        this.multimedioCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Producto)) {
            return false;
        }

    	Producto other = (Producto ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombre + ", " + presentacion;
    }

}
