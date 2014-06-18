
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
 * Class for mapping DTO Entity of Table Configuracion_Sistema.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */

public class ConfiguracionSistema implements java.io.Serializable {
    private static final long serialVersionUID = 1043540735;
    
    /**
    * llave
    */
    private String llave;
    
    /**
    * valor
    */
    private String valor;

    /** 
     * Default Constructor
     */
    public ConfiguracionSistema() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public ConfiguracionSistema( String llave ) {
        this.llave 	= 	llave;

    }
    
    /**
     * Getters and Setters
     */
    public String getLlave() {
        return this.llave;
    }

    public void setLlave(String v) {
        this.llave = v;
    }

    public String getValor() {
        return this.valor;
    }

    public void setValor(String v) {
        this.valor = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (llave != null ? llave.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof ConfiguracionSistema)) {
            return false;
        }

    	ConfiguracionSistema other = (ConfiguracionSistema ) o;
        if ( (this.llave == null && other.llave != null) || (this.llave != null && !this.llave.equals(other.llave))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.ConfiguracionSistema[llave = "+llave+ "]";
    }

}
