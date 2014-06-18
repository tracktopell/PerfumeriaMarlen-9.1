
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
 * Class for mapping DTO Entity of Table Movimiento_operativo_almacen_Detalle.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */

public class MovimientoOperativoAlmacenDetalle implements java.io.Serializable {
    private static final long serialVersionUID = 1377032712;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * movimiento operativo almacen id
    */
    private MovimientoOperativoAlmacen movimientoOperativoAlmacen;
    
    /**
    * producto id
    */
    private Producto producto;
    
    /**
    * cantidad
    */
    private int cantidad;

    /** 
     * Default Constructor
     */
    public MovimientoOperativoAlmacenDetalle() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoOperativoAlmacenDetalle( Integer id ) {
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

    public MovimientoOperativoAlmacen getMovimientoOperativoAlmacen () {
        return this.movimientoOperativoAlmacen;
    }

    public void setMovimientoOperativoAlmacen(MovimientoOperativoAlmacen v) {
        this.movimientoOperativoAlmacen = v;
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


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MovimientoOperativoAlmacenDetalle)) {
            return false;
        }

    	MovimientoOperativoAlmacenDetalle other = (MovimientoOperativoAlmacenDetalle ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.MovimientoOperativoAlmacenDetalle[id = "+id+ "]";
    }

}
