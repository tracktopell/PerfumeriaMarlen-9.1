
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
 * Class for mapping JPA Entity of Table Movimiento_Monedero_Electronico.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */



@Entity
@Table(name = "MOVIMIENTO_MONEDERO_ELECTRONICO")
public class MovimientoMonederoElectronico implements java.io.Serializable {
    private static final long serialVersionUID = 42854248;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID"   )
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * fecha
    */
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA"   )
    private java.util.Date fecha;
    
    /**
    * monedero electronico id
    */
    @JoinColumn(name = "MONEDERO_ELECTRONICO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private MonederoElectronico monederoElectronico;
    
    /**
    * usuario id
    */
    @JoinColumn(name = "USUARIO_ID" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    /**
    * importe
    */
    @Basic(optional = false)
    @Column(name = "IMPORTE"   )
    private double importe;
    
    /**
    * tipo movimiento id
    */
    @JoinColumn(name = "TIPO_MOVIMIENTO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private TipoMovimiento tipoMovimiento;

    /** 
     * Default Constructor
     */
    public MovimientoMonederoElectronico() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MovimientoMonederoElectronico( Integer id ) {
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

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public MonederoElectronico getMonederoElectronico () {
        return this.monederoElectronico;
    }

    public void setMonederoElectronico(MonederoElectronico v) {
        this.monederoElectronico = v;
    }

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public double getImporte() {
        return this.importe;
    }

    public void setImporte(double v) {
        this.importe = v;
    }

    public TipoMovimiento getTipoMovimiento () {
        return this.tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento v) {
        this.tipoMovimiento = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MovimientoMonederoElectronico)) {
            return false;
        }

    	MovimientoMonederoElectronico other = (MovimientoMonederoElectronico ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.MovimientoMonederoElectronico[id = "+id+ "]";
    }

}
