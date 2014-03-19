/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.controller;

import com.pmarlen.model.beans.*;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository("getListDataBusinesJPAController")
public class GetListDataBusinesJPAController {
    
    private Logger logger = LoggerFactory.getLogger(GetListDataBusinesJPAController.class);

    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Usuario x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Usuario> resultLsit = q.getResultList();
            for (Usuario x : resultLsit) {
                
				Collection<Perfil> perfilCollection = x.getPerfilCollection();
                for(Perfil perfil: perfilCollection){
                    perfil.setUsuarioCollection(null);					
                }
				Sucursal sucursal = x.getSucursal();
				
				if(sucursal != null) {
					/*
					sucursal.setAlmacenCollection(null);
					sucursal.setPoblacion(null);
					sucursal.setSucursal(null);
					sucursal.setSucursalCollection(null);
					sucursal.setUsuarioCollection(null);
					*/
					Sucursal sucX = new Sucursal(sucursal.getId());
					x.setSucursal(sucX);
				}
				
				x.setPedidoVentaCollection(null);
                x.setMovimientoHistoricoProductoCollection(null);
                x.setPedidoVentaEstadoCollection(null);
                x.setMensajesParaPortalCollection(null);
				x.setMovimientoOperativoAlmacenCollection(null);
				x.setMovimientoMonederoElectronicoCollection(null);
				x.setNotaCreditoCollection(null);
            }
            return resultLsit;
        } finally {
            em.close();
        }
    }

    public List<Industria> findIndustriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Industria x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Industria> resultList = q.getResultList();
            for (Industria x : resultList) {
                Collection<Marca> marcaCollection = x.getMarcaCollection();
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();

                x.setMarcaCollection(null);
                x.setMultimedioCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Linea> findLineaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Linea x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Linea> resultList = q.getResultList();
            for (Linea x : resultList) {
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();

                x.setMultimedioCollection(null);				
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Marca x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Marca> resultList = q.getResultList();
            for (Marca x : resultList) {
                Collection<Producto> productoCollection = x.getProductoCollection();
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                
                x.setProductoCollection(null);
                x.setMultimedioCollection(null);

                x.getIndustria().setMarcaCollection(null);
                x.getIndustria().setMultimedioCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Producto x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Producto> resultList = q.getResultList();
            for (Producto x : resultList) {
                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
				Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = x.getPedidoVentaDetalleCollection();
                Collection<ProveedorProducto> proveedorProductoCollection = x.getProveedorProductoCollection();
				Collection<MovimientoOperativoAlmacenDetalle> movimientoOperativoAlmacenDetalleCollection = x.getMovimientoOperativoAlmacenDetalleCollection();
				
                x.setAlmacenProductoCollection(null);                
                x.setMovimientoHistoricoProductoCollection(null);
                x.setMultimedioCollection(null);
                x.setPedidoVentaDetalleCollection(null);
                x.setProveedorProductoCollection(null);
				x.setMovimientoOperativoAlmacenDetalleCollection(null);
				x.setNotaCreditoDetalleCollection(null);
				
                x.getMarca().setIndustria(null);
                x.getMarca().setMultimedioCollection(null);
                x.getMarca().setProductoCollection(null);
				
				
            }
            return resultList;
        } finally {
            em.close();
        }
    }
	
	public List<AlmacenProducto> findAlmacenProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM AlmacenProducto x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<AlmacenProducto> almacenProductoList = q.getResultList();
			for(AlmacenProducto ap: almacenProductoList){
				Almacen almacen = ap.getAlmacen();
				almacen.setAlmacenProductoCollection(null);
				almacen.setMovimientoHistoricoProductoCollection(null);
				almacen.setMovimientoOperativoAlmacenToAlmacenDestinoCollection(null);
				almacen.setMovimientoOperativoAlmacenToAlmacenOrigenCollection(null);
				almacen.setPedidoVentaCollection(null);
				almacen.setSucursal(null);					
				
				Producto producto = ap.getProducto();
				producto.setAlmacenProductoCollection(null);
				producto.setMarca(null);
				producto.setMovimientoHistoricoProductoCollection(null);
				producto.setMovimientoOperativoAlmacenDetalleCollection(null);
				producto.setMultimedioCollection(null);
				producto.setPedidoVentaDetalleCollection(null);
				producto.setProveedorProductoCollection(null);

			}
            return almacenProductoList;
        } finally {
            em.close();
        }
    }
	
    public List<Multimedio> findMultimedioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {            
            Query q = em.createQuery("SELECT x FROM Multimedio x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Multimedio> resultList = q.getResultList();

            if(resultList != null){
                logger.debug("-->>findMultimedioEntities:resultList size="+resultList.size());
            } else {
                logger.debug("-->>findMultimedioEntities:resultList is null");
            }

            for (Multimedio x : resultList) {
                Collection<Industria> IndustriaCollection = x.getIndustriaCollection();
                Collection<Linea> lineaCollection = x.getLineaCollection();
                Collection<Marca> marcaCollection = x.getMarcaCollection();
                Collection<Producto> productoCollection = x.getProductoCollection();

                for(Producto producto: productoCollection){
                    producto.setMarca(null);
                    
                    producto.setAlmacenProductoCollection(null);
                    
                    producto.setMovimientoHistoricoProductoCollection(null);
                    producto.setMultimedioCollection(null);
                    producto.setPedidoVentaDetalleCollection(null);
                    producto.setProveedorProductoCollection(null);
					producto.setMovimientoOperativoAlmacenDetalleCollection(null);
                }

                x.setIndustriaCollection(null);
                x.setLineaCollection(null);
                x.setMarcaCollection(null);

                x.setProductoCollection(productoCollection);
                
                //logger.debug("\t-->>findMultimedioEntities:\tpreparing to marshalling Multimedio:"+x.getId());
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<FormaDePago> findFormaDePagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM FormaDePago x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<FormaDePago> resultList = q.getResultList();
            for (FormaDePago x : resultList) {
                Collection<PedidoVenta> pedidoVentaCollection = x.getPedidoVentaCollection();
                x.setPedidoVentaCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Estado x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Estado> resultList = q.getResultList();
            for (Estado x : resultList) {
                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = x.getPedidoVentaEstadoCollection();

                x.setPedidoVentaEstadoCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }
	
	public List<Sucursal> findSucursalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Sucursal x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Sucursal> resultList = q.getResultList();
			
			//logger.debug("-->>findSucursalEntities:="+resultList);
            for (Sucursal x : resultList) {
				
				//logger.debug("\t-->>Sucursal.id="+x.getId());
				
				x.setSucursalCollection(null);
				
				x.getPoblacion().setClienteCollection(null);
				x.getPoblacion().setProveedorCollection(null);
				x.getPoblacion().setSucursalCollection(null);
				Collection<Usuario> usuarioCollection = x.getUsuarioCollection();
				Collection<Usuario> usuarioCollectionMinimized = new ArrayList<Usuario>();
				//logger.debug("\t-->>usuarioCollection="+usuarioCollection);
				for(Usuario u: usuarioCollection) {
					//logger.debug("\t\t-->>usuario="+u.getUsuarioId());
					usuarioCollectionMinimized.add(new Usuario(u.getUsuarioId()));							
				}
				x.setUsuarioCollection(usuarioCollectionMinimized);
				
				Collection<Almacen> almacenCollection = x.getAlmacenCollection();
				//logger.debug("\t\t-->>almacenCollection="+almacenCollection);
				
				for(Almacen a: almacenCollection) {
					//logger.debug("\t\t\t-->>almacen="+a.getId());
					Collection<AlmacenProducto> almacenProductoCollection = a.getAlmacenProductoCollection();
					Collection<AlmacenProducto> almacenProductoMinimizedList = new ArrayList<AlmacenProducto>();
					for(AlmacenProducto ap: almacenProductoCollection){						
						AlmacenProducto almacenProductoMinimized = new AlmacenProducto(ap.getId());
						
						almacenProductoMinimized.setProducto(new Producto(ap.getProducto().getId()));
						almacenProductoMinimized.setAlmacen(new Almacen(ap.getAlmacen().getId()));
						almacenProductoMinimized.setCantidadActual(ap.getCantidadActual());
						almacenProductoMinimized.setPrecioVenta(ap.getPrecioVenta());
						
						//logger.debug("\t\t\t\t-->>producto="+almacenProductoMinimized.getProducto().getId()+" x "+almacenProductoMinimized.getCantidadActual());
						almacenProductoMinimizedList.add(almacenProductoMinimized);
					}
					a.setAlmacenProductoCollection(almacenProductoMinimizedList);
					a.setPedidoVentaCollection(null);
					a.setMovimientoHistoricoProductoCollection(null);
					a.setMovimientoOperativoAlmacenToAlmacenOrigenCollection(null);
					a.setMovimientoOperativoAlmacenToAlmacenDestinoCollection(null);
					a.setNotaCreditoCollection(null);
					a.setSucursal(null);
				}
				
				Sucursal sucursalPadre = x.getSucursal();
				if(sucursalPadre != null) {
					Integer idPadre = sucursalPadre.getId();
					//logger.debug("\t\t\t-->>padre="+idPadre);
					x.setSucursal(new Sucursal(idPadre));				
				}
            }
            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT x FROM Cliente x");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Cliente> resultList = q.getResultList();
            for (Cliente x : resultList) {
                
                Collection<PedidoVenta> pedidoVentaCollection = x.getPedidoVentaCollection();

                x.setPedidoVentaCollection(null);               
                x.getPoblacion().setClienteCollection(null);
                x.getPoblacion().setMunicipioODelegacion(null);
                x.getPoblacion().setProveedorCollection(null);
				x.getPoblacion().setSucursalCollection(null);
				x.setNotaCreditoCollection(null);
            }
            return resultList;
        } finally {
            em.close();
        }
    }
}
