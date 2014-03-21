
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
 * Class for mapping DTO Entity of Table Pedido_Venta_Pago.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */

public class PedidoVentaPago implements java.io.Serializable {
    private static final long serialVersionUID = 1617856151;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * pedido ventaid
    */
    private PedidoVenta pedidoVenta;
    
    /**
    * fecha
    */
    private java.util.Date fecha;
    
    /**
    * importe
    */
    private double importe;
    
    /**
    * referencia
    */
    private String referencia;
    
    /**
    * comentarios
    */
    private String comentarios;
    
    /**
    * nota credito id
    */
    private NotaCredito notaCredito;

    /** 
     * Default Constructor
     */
    public PedidoVentaPago() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public PedidoVentaPago( Integer id ) {
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

    public java.util.Date getFecha() {
        return this.fecha;
    }

    public void setFecha(java.util.Date v) {
        this.fecha = v;
    }

    public double getImporte() {
        return this.importe;
    }

    public void setImporte(double v) {
        this.importe = v;
    }

    public String getReferencia() {
        return this.referencia;
    }

    public void setReferencia(String v) {
        this.referencia = v;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String v) {
        this.comentarios = v;
    }

    public NotaCredito getNotaCredito () {
        return this.notaCredito;
    }

    public void setNotaCredito(NotaCredito v) {
        this.notaCredito = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof PedidoVentaPago)) {
            return false;
        }

    	PedidoVentaPago other = (PedidoVentaPago ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.wscommons.services.dto.PedidoVentaPago[id = "+id+ "]";
    }

}