
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
 * Class for mapping DTO Entity of Table Monedero_Electronico.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */

public class MonederoElectronico implements java.io.Serializable {
    private static final long serialVersionUID = 318207356;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * activado
    */
    private int activado;
    
    /**
    * num tarjeta
    */
    private String numTarjeta;
    
    /**
    * importe
    */
    private double importe;
    
    private Collection<MovimientoMonederoElectronico> movimientoMonederoElectronicoCollection;
    

    /** 
     * Default Constructor
     */
    public MonederoElectronico() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MonederoElectronico( Integer id ) {
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

    public int getActivado() {
        return this.activado;
    }

    public void setActivado(int v) {
        this.activado = v;
    }

    public String getNumTarjeta() {
        return this.numTarjeta;
    }

    public void setNumTarjeta(String v) {
        this.numTarjeta = v;
    }

    public double getImporte() {
        return this.importe;
    }

    public void setImporte(double v) {
        this.importe = v;
    }

    
    public Collection<MovimientoMonederoElectronico> getMovimientoMonederoElectronicoCollection() {
        return this.movimientoMonederoElectronicoCollection;
    }
    
    
    public void setMovimientoMonederoElectronicoCollection(Collection<MovimientoMonederoElectronico>  v) {
        this.movimientoMonederoElectronicoCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MonederoElectronico)) {
            return false;
        }

    	MonederoElectronico other = (MonederoElectronico ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.MonederoElectronico[id = "+id+ "]";
    }

}
