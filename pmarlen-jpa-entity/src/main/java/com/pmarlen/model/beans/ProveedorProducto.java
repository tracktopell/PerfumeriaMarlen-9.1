
package com.pmarlen.model.beans;

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
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class for mapping JPA Entity of Table Proveedor_Producto.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */



@Entity
@Table(name = "PROVEEDOR_PRODUCTO")
public class ProveedorProducto implements java.io.Serializable {
    private static final long serialVersionUID = 630080870;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID"   )
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * proveedor id
    */
    @JoinColumn(name = "PROVEEDOR_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Proveedor proveedor;
    
    /**
    * producto id
    */
    @JoinColumn(name = "PRODUCTO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Producto producto;
    
    /**
    * precio compra
    */
    @Basic(optional = false)
    @Column(name = "PRECIO_COMPRA"   )
    private double precioCompra;

    /** 
     * Default Constructor
     */
    public ProveedorProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public ProveedorProducto( Integer id ) {
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

    public Proveedor getProveedor () {
        return this.proveedor;
    }

    public void setProveedor(Proveedor v) {
        this.proveedor = v;
    }

    public Producto getProducto () {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }

    public double getPrecioCompra() {
        return this.precioCompra;
    }

    public void setPrecioCompra(double v) {
        this.precioCompra = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof ProveedorProducto)) {
            return false;
        }

    	ProveedorProducto other = (ProveedorProducto ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.ProveedorProducto[id = "+id+ "]";
    }

}
