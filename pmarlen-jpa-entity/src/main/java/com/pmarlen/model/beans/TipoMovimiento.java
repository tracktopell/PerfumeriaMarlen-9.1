
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
 * Class for mapping JPA Entity of Table Tipo_Movimiento.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */



@Entity
@Table(name = "TIPO_MOVIMIENTO")
public class TipoMovimiento implements java.io.Serializable {
    private static final long serialVersionUID = 1298842157;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID"   )
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * descripcion
    */
    @Basic(optional = false)
    @Column(name = "DESCRIPCION" , length=64  )
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMovimiento")
    private Collection<MovimientoMonederoElectronico> movimientoMonederoElectronicoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMovimiento")
    private Collection<MovimientoOperativoAlmacen> movimientoOperativoAlmacenCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoMovimiento")
    private Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection;
    

    /** 
     * Default Constructor
     */
    public TipoMovimiento() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public TipoMovimiento( Integer id ) {
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String v) {
        this.descripcion = v;
    }

    
    public Collection<MovimientoMonederoElectronico> getMovimientoMonederoElectronicoCollection() {
        return this.movimientoMonederoElectronicoCollection;
    }
    
    
    public void setMovimientoMonederoElectronicoCollection(Collection<MovimientoMonederoElectronico>  v) {
        this.movimientoMonederoElectronicoCollection = v;
    }
    
    public Collection<MovimientoOperativoAlmacen> getMovimientoOperativoAlmacenCollection() {
        return this.movimientoOperativoAlmacenCollection;
    }
    
    
    public void setMovimientoOperativoAlmacenCollection(Collection<MovimientoOperativoAlmacen>  v) {
        this.movimientoOperativoAlmacenCollection = v;
    }
    
    public Collection<MovimientoHistoricoProducto> getMovimientoHistoricoProductoCollection() {
        return this.movimientoHistoricoProductoCollection;
    }
    
    
    public void setMovimientoHistoricoProductoCollection(Collection<MovimientoHistoricoProducto>  v) {
        this.movimientoHistoricoProductoCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof TipoMovimiento)) {
            return false;
        }

    	TipoMovimiento other = (TipoMovimiento ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }

}
