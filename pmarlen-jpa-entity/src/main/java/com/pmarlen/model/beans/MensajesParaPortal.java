
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
 * Class for mapping JPA Entity of Table Mensajes_para_portal.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/01/15 02:39
 */



@Entity
@Table(name = "MENSAJES_PARA_PORTAL")
public class MensajesParaPortal implements java.io.Serializable {
    private static final long serialVersionUID = 1366564906;
    
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
    * usuariousuario id
    */
    @JoinColumn(name = "USUARIOUSUARIO_ID" , referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario usuario;
    
    /**
    * titulo
    */
    @Basic(optional = false)
    @Column(name = "TITULO" , length=255  )
    private String titulo;
    
    /**
    * contenido mensaje
    */
    @Basic(optional = false)
    @Column(name = "CONTENIDO_MENSAJE" , length=1024  )
    private String contenidoMensaje;
    
    /**
    * fuente
    */
    @Basic(optional = false)
    @Column(name = "FUENTE" , length=128  )
    private String fuente;

    /** 
     * Default Constructor
     */
    public MensajesParaPortal() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public MensajesParaPortal( Integer id ) {
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

    public Usuario getUsuario () {
        return this.usuario;
    }

    public void setUsuario(Usuario v) {
        this.usuario = v;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String v) {
        this.titulo = v;
    }

    public String getContenidoMensaje() {
        return this.contenidoMensaje;
    }

    public void setContenidoMensaje(String v) {
        this.contenidoMensaje = v;
    }

    public String getFuente() {
        return this.fuente;
    }

    public void setFuente(String v) {
        this.fuente = v;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof MensajesParaPortal)) {
            return false;
        }

    	MensajesParaPortal other = (MensajesParaPortal ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return "com.pmarlen.model.beans.MensajesParaPortal[id = "+id+ "]";
    }

}
