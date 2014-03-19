
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
 * Class for mapping JPA Entity of Table Entrada_almacen.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */



@Entity
@Table(name = "ENTRADA_ALMACEN")
public class EntradaAlmacen implements java.io.Serializable {
    private static final long serialVersionUID = 2105286989;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID"   )
    private Integer id;
    
    /**
    * almacen id
    */
    @JoinColumn(name = "ALMACEN_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Almacen almacen;
    
    /**
    * usuario id
    */
    @JoinColumn(name = "USUARIO_ID" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    /**
    * fecha
    */
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA"   )
    private java.util.Date fecha;
    
    /**
    * comentarios
    */
    @Basic(optional = true)
    @Column(name = "COMENTARIOS" , length=255  )
    private String comentarios;
    
    /**
    * descuento aplicado
    */
    @Basic(optional = true)
    @Column(name = "DESCUENTO_APLICADO"   )
    private Double descuentoAplicado;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entradaAlmacen")
    private Collection<EntradaAlmacenDetalle> entradaAlmacenDetalleCollection;
    

    /** 
     * Default Constructor
     */
    public EntradaAlmacen() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EntradaAlmacen( Integer id ) {
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

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
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

    
    public Collection<EntradaAlmacenDetalle> getEntradaAlmacenDetalleCollection() {
        return this.entradaAlmacenDetalleCollection;
    }
    
    
    public void setEntradaAlmacenDetalleCollection(Collection<EntradaAlmacenDetalle>  v) {
        this.entradaAlmacenDetalleCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof EntradaAlmacen)) {
            return false;
        }

    	EntradaAlmacen other = (EntradaAlmacen ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.EntradaAlmacen[id = "+id+ "]";
    }

}
