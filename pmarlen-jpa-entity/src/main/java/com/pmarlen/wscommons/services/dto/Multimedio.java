
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
 * Class for mapping DTO Entity of Table Multimedio.
 * 
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */

public class Multimedio implements java.io.Serializable {
    private static final long serialVersionUID = 1042752596;
    
    /**
    * id
    */
    private Integer id;
    
    /**
    * mime type
    */
    private String mimeType;
    
    /**
    * ruta contenido
    */
    private String rutaContenido;
    
    /**
    * size bytes
    */
    private int sizeBytes;
    
    /**
    * nombre archivo
    */
    private String nombreArchivo;
    
    private Collection<Marca> marcaCollection;
    
    
    private Collection<Linea> lineaCollection;
    
    
    private Collection<Producto> productoCollection;
    
    
    private Collection<Industria> industriaCollection;
    

    /** 
     * Default Constructor
     */
    public Multimedio() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Multimedio( Integer id ) {
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

    public String getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(String v) {
        this.mimeType = v;
    }

    public String getRutaContenido() {
        return this.rutaContenido;
    }

    public void setRutaContenido(String v) {
        this.rutaContenido = v;
    }

    public int getSizeBytes() {
        return this.sizeBytes;
    }

    public void setSizeBytes(int v) {
        this.sizeBytes = v;
    }

    public String getNombreArchivo() {
        return this.nombreArchivo;
    }

    public void setNombreArchivo(String v) {
        this.nombreArchivo = v;
    }

    // Getter and Setters @ManyToMany Collection<Marca>
    
    public Collection<Marca> getMarcaCollection() {
        return this.marcaCollection;
    }
    
    
    public void setMarcaCollection(Collection<Marca>  v) {
        this.marcaCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Linea>
    
    public Collection<Linea> getLineaCollection() {
        return this.lineaCollection;
    }
    
    
    public void setLineaCollection(Collection<Linea>  v) {
        this.lineaCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Producto>
    
    public Collection<Producto> getProductoCollection() {
        return this.productoCollection;
    }
    
    
    public void setProductoCollection(Collection<Producto>  v) {
        this.productoCollection = v;
    }
    // Getter and Setters @ManyToMany Collection<Industria>
    
    public Collection<Industria> getIndustriaCollection() {
        return this.industriaCollection;
    }
    
    
    public void setIndustriaCollection(Collection<Industria>  v) {
        this.industriaCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (id != null ? id.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Multimedio)) {
            return false;
        }

    	Multimedio other = (Multimedio ) o;
        if ( (this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombreArchivo;
    }

}
