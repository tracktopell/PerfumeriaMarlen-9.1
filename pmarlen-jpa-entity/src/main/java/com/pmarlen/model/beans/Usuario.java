
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
 * Class for mapping JPA Entity of Table Usuario.
 * m2m
 * @author Tracktopell::jpa-builder @see  https://github.com/tracktopell/UtilProjects/tree/master/jpa-builder
 * @version 0.8.5
 * @date 2014/06/18 02:23
 */



@Entity
@Table(name = "USUARIO")
public class Usuario implements java.io.Serializable {
    private static final long serialVersionUID = 1956912637;
    
    /**
    * usuario id
    */
    @Id
    @Basic(optional = false)
    @Column(name = "USUARIO_ID" , length=16  )
    private String usuarioId;
    
    /**
    * habilitado
    */
    @Basic(optional = false)
    @Column(name = "HABILITADO"   )
    private int habilitado;
    
    /**
    * nombre completo
    */
    @Basic(optional = false)
    @Column(name = "NOMBRE_COMPLETO" , length=128  )
    private String nombreCompleto;
    
    /**
    * password
    */
    @Basic(optional = false)
    @Column(name = "PASSWORD" , length=64  )
    private String password;
    
    /**
    * email
    */
    @Basic(optional = true)
    @Column(name = "EMAIL" , length=64  )
    private String email;
    
    /**
    * sucursal id
    */
    @JoinColumn(name = "SUCURSAL_ID" , referencedColumnName = "ID")
    @ManyToOne(optional = true)
    private Sucursal sucursal;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<PedidoVentaEstado> pedidoVentaEstadoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<PedidoVenta> pedidoVentaCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<MovimientoMonederoElectronico> movimientoMonederoElectronicoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<EntradaAlmacen> entradaAlmacenCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<MensajesParaPortal> mensajesParaPortalCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<NotaCredito> notaCreditoCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<MovimientoOperativoAlmacen> movimientoOperativoAlmacenCollection;
    
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection;
    
    
    @JoinTable(name               = "USUARIO_PERFIL",
               joinColumns        = {@JoinColumn(name = "USUARIO_ID", referencedColumnName ="USUARIO_ID")},
               inverseJoinColumns = {@JoinColumn(name = "PERFIL_ID", referencedColumnName ="ID")}
               )
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Perfil> perfilCollection;
    

    /** 
     * Default Constructor
     */
    public Usuario() {
    }

    /** 
     * lazy Constructor just with IDs
     */
    public Usuario( String usuarioId ) {
        this.usuarioId 	= 	usuarioId;

    }
    
    /**
     * Getters and Setters
     */
    public String getUsuarioId() {
        return this.usuarioId;
    }

    public void setUsuarioId(String v) {
        this.usuarioId = v;
    }

    public int getHabilitado() {
        return this.habilitado;
    }

    public void setHabilitado(int v) {
        this.habilitado = v;
    }

    public String getNombreCompleto() {
        return this.nombreCompleto;
    }

    public void setNombreCompleto(String v) {
        this.nombreCompleto = v;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String v) {
        this.password = v;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String v) {
        this.email = v;
    }

    public Sucursal getSucursal () {
        return this.sucursal;
    }

    public void setSucursal(Sucursal v) {
        this.sucursal = v;
    }

    
    public Collection<PedidoVentaEstado> getPedidoVentaEstadoCollection() {
        return this.pedidoVentaEstadoCollection;
    }
    
    
    public void setPedidoVentaEstadoCollection(Collection<PedidoVentaEstado>  v) {
        this.pedidoVentaEstadoCollection = v;
    }
    
    public Collection<PedidoVenta> getPedidoVentaCollection() {
        return this.pedidoVentaCollection;
    }
    
    
    public void setPedidoVentaCollection(Collection<PedidoVenta>  v) {
        this.pedidoVentaCollection = v;
    }
    
    public Collection<MovimientoMonederoElectronico> getMovimientoMonederoElectronicoCollection() {
        return this.movimientoMonederoElectronicoCollection;
    }
    
    
    public void setMovimientoMonederoElectronicoCollection(Collection<MovimientoMonederoElectronico>  v) {
        this.movimientoMonederoElectronicoCollection = v;
    }
    
    public Collection<EntradaAlmacen> getEntradaAlmacenCollection() {
        return this.entradaAlmacenCollection;
    }
    
    
    public void setEntradaAlmacenCollection(Collection<EntradaAlmacen>  v) {
        this.entradaAlmacenCollection = v;
    }
    
    public Collection<MensajesParaPortal> getMensajesParaPortalCollection() {
        return this.mensajesParaPortalCollection;
    }
    
    
    public void setMensajesParaPortalCollection(Collection<MensajesParaPortal>  v) {
        this.mensajesParaPortalCollection = v;
    }
    
    public Collection<NotaCredito> getNotaCreditoCollection() {
        return this.notaCreditoCollection;
    }
    
    
    public void setNotaCreditoCollection(Collection<NotaCredito>  v) {
        this.notaCreditoCollection = v;
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
    // Getter and Setters @ManyToMany Collection<Perfil>
    
    public Collection<Perfil> getPerfilCollection() {
        return this.perfilCollection;
    }
    
    
    public void setPerfilCollection(Collection<Perfil>  v) {
        this.perfilCollection = v;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash = (usuarioId != null ? usuarioId.hashCode() : 0 );
        return hash;
    }

    public boolean equals(Object o){

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(o instanceof Usuario)) {
            return false;
        }

    	Usuario other = (Usuario ) o;
        if ( (this.usuarioId == null && other.usuarioId != null) || (this.usuarioId != null && !this.usuarioId.equals(other.usuarioId))) {
            return false;
        }


    	return true;
    }

    @Override
    public String toString() {
        return nombreCompleto;
    }

}
