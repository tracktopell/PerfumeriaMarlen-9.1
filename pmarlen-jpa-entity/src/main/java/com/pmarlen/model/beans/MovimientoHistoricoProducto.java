
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
 * Class for mapping JPA Entity of Table Movimiento_Historico_Producto.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */



@Entity
@Table(name = "MOVIMIENTO_HISTORICO_PRODUCTO")
public class MovimientoHistoricoProducto implements java.io.Serializable {
    private static final long serialVersionUID = 195522436;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID"   )
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * almacen id
    */
    @JoinColumn(name = "ALMACEN_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Almacen almacen;
    
    /**
    * producto id
    */
    @JoinColumn(name = "PRODUCTO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Producto producto;
    
    /**
    * fecha
    */
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA"   )
    private java.util.Date fecha;
    
    /**
    * tipo movimiento id
    */
    @JoinColumn(name = "TIPO_MOVIMIENTO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoMovimiento tipoMovimiento;
    
    /**
    * cantidad
    */
    @Basic(optional = false)
    @Column(name = "CANTIDAD"   )
    private int cantidad;
    
    /**
    * costo
    */
    @Basic(optional = true)
    @Column(name = "COSTO"   )
    private Double costo;
    
    /**
    * precio
    */
    @Basic(optional = true)
    @Column(name = "PRECIO"   )
    private Double precio;
    
    /**
    * usuario id
    */
    @JoinColumn(name = "USUARIO_ID" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;

    /** 
     * Default Constructor
     */
    public MovimientoHistoricoProducto() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoHistoricoProducto( Integer id ) {
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

    public Almacen getAlmacen () {
        return this.almacen;
    }

    public void setAlmacen(Almacen v) {
        this.almacen = v;
    }

    public Producto getProducto () {
        return this.producto;
    }

    public void setProducto(Producto v) {
        this.producto = v;
    }

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public TipoMovimiento getTipoMovimiento () {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento v) {
        this.tipoMovimiento = v;
    }

    public int getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(int v) {
        this.cantidad = v;
    }

    public Double getCosto() {
        return this.costo;
    }

    public void setCosto(Double v) {
        this.costo = v;
    }

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double v) {
        this.precio = v;
    }

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MovimientoHistoricoProducto)) {
            return false;
        }

    	MovimientoHistoricoProducto other = (MovimientoHistoricoProducto ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.MovimientoHistoricoProducto[id = "+id+ "]";
    }

}
