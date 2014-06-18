
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
 * Class for mapping DTO Entity of Table Nota_credito.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */

public class NotaCredito implements java.io.Serializable {
    private static final long serialVersionUID = 399361189;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * cliente id
    */
    private Cliente cliente;
    
    /**
    * usuariousuario id
    */
    private Usuario usuario;
    
    /**
    * almacen id
    */
    private Almacen almacen;
    
    /**
    * fecha
    */
    private java.util.Date fecha;
    
    /**
    * numero ticket devuelto
    */
    private String numeroTicketDevuelto;
    
    private Collection<NotaCreditoDetalle> notaCreditoDetalleCollection;
    
    
    private Collection<PedidoVentaPago> pedidoVentaPagoCollection;
    

    /** 
     * Default Constructor
     */
    public NotaCredito() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public NotaCredito( Integer id ) {
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

    public Cliente getCliente () {
        return this.cliente;
    }

    public void setCliente(Cliente v) {
        this.cliente = v;
    }

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public Almacen getAlmacen () {
        return this.almacen;
    }

    public void setAlmacen(Almacen v) {
        this.almacen = v;
    }

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public String getNumeroTicketDevuelto() {
        return this.numeroTicketDevuelto;
    }

    public void setNumeroTicketDevuelto(String v) {
        this.numeroTicketDevuelto = v;
    }

    
    public Collection<NotaCreditoDetalle> getNotaCreditoDetalleCollection() {
        return this.notaCreditoDetalleCollection;
    }
    
    
    public void setNotaCreditoDetalleCollection(Collection<NotaCreditoDetalle>  v) {
        this.notaCreditoDetalleCollection = v;
    }
    
    public Collection<PedidoVentaPago> getPedidoVentaPagoCollection() {
        return this.pedidoVentaPagoCollection;
    }
    
    
    public void setPedidoVentaPagoCollection(Collection<PedidoVentaPago>  v) {
        this.pedidoVentaPagoCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof NotaCredito)) {
            return false;
        }

    	NotaCredito other = (NotaCredito ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.NotaCredito[id = "+id+ "]";
    }

}
