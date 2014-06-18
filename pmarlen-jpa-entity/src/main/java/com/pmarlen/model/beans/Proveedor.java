
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
 * Class for mapping JPA Entity of Table Proveedor.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */



@Entity
@Table(name = "PROVEEDOR")
public class Proveedor implements java.io.Serializable {
    private static final long serialVersionUID = 1467961897;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID"   )
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * rfc
    */
    @Basic(optional = false)
    @Column(name = "RFC" , length=13  )
    private String rfc;
    
    /**
    * fecha creacion
    */
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION"   )
    private java.util.Date fechaCreacion;
    
    /**
    * razon social
    */
    @Basic(optional = false)
    @Column(name = "RAZON_SOCIAL" , length=128  )
    private String razonSocial;
    
    /**
    * calle
    */
    @Basic(optional = false)
    @Column(name = "CALLE" , length=128  )
    private String calle;
    
    /**
    * num exterior
    */
    @Basic(optional = true)
    @Column(name = "NUM_EXTERIOR" , length=16  )
    private String numExterior;
    
    /**
    * num interior
    */
    @Basic(optional = false)
    @Column(name = "NUM_INTERIOR" , length=16  )
    private String numInterior;
    
    /**
    * poblacion id
    */
    @JoinColumn(name = "POBLACION_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Poblacion poblacion;
    
    /**
    * contacto
    */
    @Basic(optional = true)
    @Column(name = "CONTACTO" , length=128  )
    private String contacto;
    
    /**
    * telefonos
    */
    @Basic(optional = true)
    @Column(name = "TELEFONOS" , length=64  )
    private String telefonos;
    
    /**
    * email
    */
    @Basic(optional = true)
    @Column(name = "EMAIL" , length=64  )
    private String email;
    
    /**
    * url
    */
    @Basic(optional = true)
    @Column(name = "URL" , length=255  )
    private String url;
    
    /**
    * observaciones
    */
    @Basic(optional = true)
    @Column(name = "OBSERVACIONES" , length=255  )
    private String observaciones;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedor")
    private Collection<ProveedorProducto> proveedorProductoCollection;
    

    /** 
     * Default Constructor
     */
    public Proveedor() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Proveedor( Integer id ) {
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

    public String getRfc() {
        return this.rfc;
    }

    public void setRfc(String v) {
        this.rfc = v;
    }

    public java.util.Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(java.util.Date v) {
        this.fechaCreacion = v;
    }

    public String getRazonSocial() {
        return this.razonSocial;
    }

    public void setRazonSocial(String v) {
        this.razonSocial = v;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String v) {
        this.calle = v;
    }

    public String getNumExterior() {
        return this.numExterior;
    }

    public void setNumExterior(String v) {
        this.numExterior = v;
    }

    public String getNumInterior() {
        return this.numInterior;
    }

    public void setNumInterior(String v) {
        this.numInterior = v;
    }

    public Poblacion getPoblacion () {
        return this.poblacion;
    }

    public void setPoblacion(Poblacion v) {
        this.poblacion = v;
    }

    public String getContacto() {
        return this.contacto;
    }

    public void setContacto(String v) {
        this.contacto = v;
    }

    public String getTelefonos() {
        return this.telefonos;
    }

    public void setTelefonos(String v) {
        this.telefonos = v;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String v) {
        this.url = v;
    }

    public String getObservaciones() {
        return this.observaciones;
    }

    public void setObservaciones(String v) {
        this.observaciones = v;
    }

    
    public Collection<ProveedorProducto> getProveedorProductoCollection() {
        return this.proveedorProductoCollection;
    }
    
    
    public void setProveedorProductoCollection(Collection<ProveedorProducto>  v) {
        this.proveedorProductoCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Proveedor)) {
            return false;
        }

    	Proveedor other = (Proveedor ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return razonSocial;
    }

}
