
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
 * Class for mapping JPA Entity of Table Forma_De_Pago.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */



@Entity
@Table(name = "FORMA_DE_PAGO")
public class FormaDePago implements java.io.Serializable {
    private static final long serialVersionUID = 1561354273;
    
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
    @Column(name = "DESCRIPCION" , length=255  )
    private String descripcion;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formaDePago")
    private Collection<PedidoVenta> pedidoVentaCollection;
    

    /** 
     * Default Constructor
     */
    public FormaDePago() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public FormaDePago( Integer id ) {
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

    
    public Collection<PedidoVenta> getPedidoVentaCollection() {
        return this.pedidoVentaCollection;
    }
    
    
    public void setPedidoVentaCollection(Collection<PedidoVenta>  v) {
        this.pedidoVentaCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof FormaDePago)) {
            return false;
        }

    	FormaDePago other = (FormaDePago ) o;
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
