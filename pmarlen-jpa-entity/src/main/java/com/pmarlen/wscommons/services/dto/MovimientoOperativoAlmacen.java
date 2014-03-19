
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
 * Class for mapping DTO Entity of Table Movimiento_operativo_almacen.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */

public class MovimientoOperativoAlmacen implements java.io.Serializable {
    private static final long serialVersionUID = 518770477;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * motivo
    */
    private String motivo;
    
    /**
    * usuario id
    */
    private Usuario usuario;
    
    /**
    * fecha inicio
    */
    private java.util.Date fechaInicio;
    
    /**
    * almacen origen
    */
    private Almacen almacenOrigen;
    
    /**
    * almacen destino
    */
    private Almacen almacenDestino;
    
    /**
    * tipo movimiento id
    */
    private TipoMovimiento tipoMovimiento;
    
    /**
    * fecha confirmacion
    */
    private Integer fechaConfirmacion;
    
    private Collection<MovimientoOperativoAlmacenDetalle> movimientoOperativoAlmacenDetalleCollection;
    

    /** 
     * Default Constructor
     */
    public MovimientoOperativoAlmacen() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoOperativoAlmacen( Integer id ) {
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

    public String getMotivo() {
        return this.motivo;
    }

    public void setMotivo(String v) {
        this.motivo = v;
    }

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public java.util.Date getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(java.util.Date v) {
        this.fechaInicio = v;
    }

    public Almacen getAlmacenOrigen () {
        return this.almacenOrigen;
    }

    public void setAlmacenOrigen(Almacen v) {
        this.almacenOrigen = v;
    }

    public Almacen getAlmacenDestino () {
        return this.almacenDestino;
    }

    public void setAlmacenDestino(Almacen v) {
        this.almacenDestino = v;
    }

    public TipoMovimiento getTipoMovimiento () {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento v) {
        this.tipoMovimiento = v;
    }

    public Integer getFechaConfirmacion() {
        return this.fechaConfirmacion;
    }

    public void setFechaConfirmacion(Integer v) {
        this.fechaConfirmacion = v;
    }

    
    public Collection<MovimientoOperativoAlmacenDetalle> getMovimientoOperativoAlmacenDetalleCollection() {
        return this.movimientoOperativoAlmacenDetalleCollection;
    }
    
    
    public void setMovimientoOperativoAlmacenDetalleCollection(Collection<MovimientoOperativoAlmacenDetalle>  v) {
        this.movimientoOperativoAlmacenDetalleCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MovimientoOperativoAlmacen)) {
            return false;
        }

    	MovimientoOperativoAlmacen other = (MovimientoOperativoAlmacen ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.MovimientoOperativoAlmacen[id = "+id+ "]";
    }

}
