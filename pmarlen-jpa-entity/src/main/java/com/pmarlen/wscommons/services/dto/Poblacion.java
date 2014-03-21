
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
 * Class for mapping DTO Entity of Table Poblacion.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */

public class Poblacion implements java.io.Serializable {
    private static final long serialVersionUID = 536557396;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * nombre
    */
    private String nombre;
    
    /**
    * tipo asentamiento
    */
    private String tipoAsentamiento;
    
    /**
    * municipio o delegacion
    */
    private String municipioODelegacion;
    
    /**
    * entidad federativa
    */
    private String entidadFederativa;
    
    /**
    * codigo postal
    */
    private String codigoPostal;
    
    private Collection<Sucursal> sucursalCollection;
    
    
    private Collection<Cliente> clienteCollection;
    
    
    private Collection<Proveedor> proveedorCollection;
    

    /** 
     * Default Constructor
     */
    public Poblacion() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Poblacion( Integer id ) {
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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String v) {
        this.nombre = v;
    }

    public String getTipoAsentamiento() {
        return this.tipoAsentamiento;
    }

    public void setTipoAsentamiento(String v) {
        this.tipoAsentamiento = v;
    }

    public String getMunicipioODelegacion() {
        return this.municipioODelegacion;
    }

    public void setMunicipioODelegacion(String v) {
        this.municipioODelegacion = v;
    }

    public String getEntidadFederativa() {
        return this.entidadFederativa;
    }

    public void setEntidadFederativa(String v) {
        this.entidadFederativa = v;
    }

    public String getCodigoPostal() {
        return this.codigoPostal;
    }

    public void setCodigoPostal(String v) {
        this.codigoPostal = v;
    }

    
    public Collection<Sucursal> getSucursalCollection() {
        return this.sucursalCollection;
    }
    
    
    public void setSucursalCollection(Collection<Sucursal>  v) {
        this.sucursalCollection = v;
    }
    
    public Collection<Cliente> getClienteCollection() {
        return this.clienteCollection;
    }
    
    
    public void setClienteCollection(Collection<Cliente>  v) {
        this.clienteCollection = v;
    }
    
    public Collection<Proveedor> getProveedorCollection() {
        return this.proveedorCollection;
    }
    
    
    public void setProveedorCollection(Collection<Proveedor>  v) {
        this.proveedorCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Poblacion)) {
            return false;
        }

    	Poblacion other = (Poblacion ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

}