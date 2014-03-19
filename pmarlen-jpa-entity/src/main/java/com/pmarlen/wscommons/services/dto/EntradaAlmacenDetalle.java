
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
 * Class for mapping DTO Entity of Table Entrada_Almacen_Detalle.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */

public class EntradaAlmacenDetalle implements java.io.Serializable {
    private static final long serialVersionUID = 1977394898;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * entrada almacen id
    */
    private EntradaAlmacen entradaAlmacen;
    
    /**
    * producto id
    */
    private Producto producto;
    
    /**
    * cantidad
    */
    private int cantidad;
    
    /**
    * precio venta
    */
    private double precioVenta;
    
    /**
    * costo
    */
    private double costo;

    /** 
     * Default Constructor
     */
    public EntradaAlmacenDetalle() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EntradaAlmacenDetalle( Integer id ) {
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

    public EntradaAlmacen getEntradaAlmacen () {
        return this.entradaAlmacen;
    }

    public void setEntradaAlmacen(EntradaAlmacen v) {
        this.entradaAlmacen = v;
    }

    public Producto getProducto () {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int v) {
        this.cantidad = v;
    }

    public double getPrecioVenta() {
        return this.precioVenta;
    }

    public void setPrecioVenta(double v) {
        this.precioVenta = v;
    }

    public double getCosto() {
        return this.costo;
    }

    public void setCosto(double v) {
        this.costo = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof EntradaAlmacenDetalle)) {
            return false;
        }

    	EntradaAlmacenDetalle other = (EntradaAlmacenDetalle ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.EntradaAlmacenDetalle[id = "+id+ "]";
    }

}
