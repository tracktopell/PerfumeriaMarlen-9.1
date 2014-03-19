
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
 * Class for mapping JPA Entity of Table Evento_Sincronizacion.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */



@Entity
@Table(name = "EVENTO_SINCRONIZACION")
public class EventoSincronizacion implements java.io.Serializable {
    private static final long serialVersionUID = 211645886;
    
    /**
    * fecha evento
    */
    @Id
    @Basic(optional = false)
    @Column(name = "FECHA_EVENTO"   )
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date fechaEvento;
    
    /**
    * tipo evento
    */
    @Basic(optional = false)
    @Column(name = "TIPO_EVENTO"   )
    private int tipoEvento;
    
    /**
    * afectacion global
    */
    @Basic(optional = false)
    @Column(name = "AFECTACION_GLOBAL"   )
    private int afectacionGlobal;
    
    /**
    * sucursal id afectada
    */
    @Basic(optional = false)
    @Column(name = "SUCURSAL_ID_AFECTADA"   )
    private int sucursalIdAfectada;
    
    /**
    * tabla
    */
    @Basic(optional = false)
    @Column(name = "TABLA" , length=64  )
    private String tabla;
    
    /**
    * campos llave
    */
    @Basic(optional = false)
    @Column(name = "CAMPOS_LLAVE" , length=64  )
    private String camposLlave;
    
    /**
    * valores llave
    */
    @Basic(optional = false)
    @Column(name = "VALORES_LLAVE" , length=128  )
    private String valoresLlave;

    /** 
     * Default Constructor
     */
    public EventoSincronizacion() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public EventoSincronizacion( java.util.Date fechaEvento ) {
        this.fechaEvento 	= 	fechaEvento;

    }
    
    /**
     * Getters and Setters
     */
    public java.util.Date getFechaEvento() {
        return this.fechaEvento;
    }

    public void setFechaEvento(java.util.Date v) {
        this.fechaEvento = v;
    }

    public int getTipoEvento() {
        return this.tipoEvento;
    }

    public void setTipoEvento(int v) {
        this.tipoEvento = v;
    }

    public int getAfectacionGlobal() {
        return this.afectacionGlobal;
    }

    public void setAfectacionGlobal(int v) {
        this.afectacionGlobal = v;
    }

    public int getSucursalIdAfectada() {
        return this.sucursalIdAfectada;
    }

    public void setSucursalIdAfectada(int v) {
        this.sucursalIdAfectada = v;
    }

    public String getTabla() {
        return this.tabla;
    }

    public void setTabla(String v) {
        this.tabla = v;
    }

    public String getCamposLlave() {
        return this.camposLlave;
    }

    public void setCamposLlave(String v) {
        this.camposLlave = v;
    }

    public String getValoresLlave() {
        return this.valoresLlave;
    }

    public void setValoresLlave(String v) {
        this.valoresLlave = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (fechaEvento != null ? fechaEvento.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof EventoSincronizacion)) {
            return false;
        }

    	EventoSincronizacion other = (EventoSincronizacion ) o;
        if ( (this.fechaEvento == null && other.fechaEvento != null) || (this.fechaEvento != null && !this.fechaEvento.equals(other.fechaEvento))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.EventoSincronizacion[fechaEvento = "+fechaEvento+ "]";
    }

}
