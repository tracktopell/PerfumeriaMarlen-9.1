
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
 * Class for mapping JPA Entity of Table Pedido_Venta_Estado.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */



@Entity
@Table(name = "PEDIDO_VENTA_ESTADO")
public class PedidoVentaEstado implements java.io.Serializable {
    private static final long serialVersionUID = 1409104443;
    
    /**
    * id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "ID"   )
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    /**
    * pedido venta id
    */
    @JoinColumn(name = "PEDIDO_VENTA_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private PedidoVenta pedidoVenta;
    
    /**
    * estado id
    */
    @JoinColumn(name = "ESTADO_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Estado estado;
    
    /**
    * fecha
    */
    @Basic(optional = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA"   )
    private java.util.Date fecha;
    
    /**
    * usuario modifico
    */
    @JoinColumn(name = "USUARIO_MODIFICO" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    /**
    * comentarios
    */
    @Basic(optional = true)
    @Column(name = "COMENTARIOS" , length=255  )
    private String comentarios;

    /** 
     * Default Constructor
     */
    public PedidoVentaEstado() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoVentaEstado( Integer id ) {
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

    public PedidoVenta getPedidoVenta () {
        return this.pedidoVenta;
    }

    public void setPedidoVenta(PedidoVenta v) {
        this.pedidoVenta = v;
    }

    public Estado getEstado () {
        return this.estado;
    }

    public void setEstado(Estado v) {
        this.estado = v;
    }

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoVentaEstado)) {
            return false;
        }

    	PedidoVentaEstado other = (PedidoVentaEstado ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.PedidoVentaEstado[id = "+id+ "]";
    }

}
