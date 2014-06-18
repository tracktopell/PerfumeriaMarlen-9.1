
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
 * Class for mapping DTO Entity of Table Almacen.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */

public class Almacen implements java.io.Serializable {
    private static final long serialVersionUID = 1829541984;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * sucursal id
    */
    private Sucursal sucursal;
    
    /**
    * tipo almacen
    */
    private int tipoAlmacen;
    
    private Collection<PedidoVenta> pedidoVentaCollection;
    
    
    private Collection<EntradaAlmacen> entradaAlmacenCollection;
    
    
    private Collection<NotaCredito> notaCreditoCollection;
    
    
    private Collection<AlmacenProducto> almacenProductoCollection;
    
    
    private Collection<MovimientoOperativoAlmacen> movimientoOperativoAlmacenToAlmacenOrigenCollection;
    
    
    private Collection<MovimientoOperativoAlmacen> movimientoOperativoAlmacenToAlmacenDestinoCollection;
    
    
    private Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection;
    

    /** 
     * Default Constructor
     */
    public Almacen() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Almacen( Integer id ) {
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

    public Sucursal getSucursal () {
        return this.sucursal;
    }

    public void setSucursal(Sucursal v) {
        this.sucursal = v;
    }

    public int getTipoAlmacen() {
        return this.tipoAlmacen;
    }

    public void setTipoAlmacen(int v) {
        this.tipoAlmacen = v;
    }

    
    public Collection<PedidoVenta> getPedidoVentaCollection() {
        return this.pedidoVentaCollection;
    }
    
    
    public void setPedidoVentaCollection(Collection<PedidoVenta>  v) {
        this.pedidoVentaCollection = v;
    }
    
    public Collection<EntradaAlmacen> getEntradaAlmacenCollection() {
        return this.entradaAlmacenCollection;
    }
    
    
    public void setEntradaAlmacenCollection(Collection<EntradaAlmacen>  v) {
        this.entradaAlmacenCollection = v;
    }
    
    public Collection<NotaCredito> getNotaCreditoCollection() {
        return this.notaCreditoCollection;
    }
    
    
    public void setNotaCreditoCollection(Collection<NotaCredito>  v) {
        this.notaCreditoCollection = v;
    }
    
    public Collection<AlmacenProducto> getAlmacenProductoCollection() {
        return this.almacenProductoCollection;
    }
    
    
    public void setAlmacenProductoCollection(Collection<AlmacenProducto>  v) {
        this.almacenProductoCollection = v;
    }
    
    public Collection<MovimientoOperativoAlmacen> getMovimientoOperativoAlmacenToAlmacenOrigenCollection() {
        return this.movimientoOperativoAlmacenToAlmacenOrigenCollection;
    }
    
    
    public void setMovimientoOperativoAlmacenToAlmacenOrigenCollection(Collection<MovimientoOperativoAlmacen>  v) {
        this.movimientoOperativoAlmacenToAlmacenOrigenCollection = v;
    }
    
    public Collection<MovimientoOperativoAlmacen> getMovimientoOperativoAlmacenToAlmacenDestinoCollection() {
        return this.movimientoOperativoAlmacenToAlmacenDestinoCollection;
    }
    
    
    public void setMovimientoOperativoAlmacenToAlmacenDestinoCollection(Collection<MovimientoOperativoAlmacen>  v) {
        this.movimientoOperativoAlmacenToAlmacenDestinoCollection = v;
    }
    
    public Collection<MovimientoHistoricoProducto> getMovimientoHistoricoProductoCollection() {
        return this.movimientoHistoricoProductoCollection;
    }
    
    
    public void setMovimientoHistoricoProductoCollection(Collection<MovimientoHistoricoProducto>  v) {
        this.movimientoHistoricoProductoCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Almacen)) {
            return false;
        }

    	Almacen other = (Almacen ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.Almacen[id = "+id+ "]";
    }

}
