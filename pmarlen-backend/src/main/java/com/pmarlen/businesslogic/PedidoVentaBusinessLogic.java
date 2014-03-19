package com.pmarlen.businesslogic;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.*;
import com.pmarlen.model.dto.InventarioFastView;
import com.pmarlen.model.dto.MovimientoEntreAlmacenes;
import com.pmarlen.model.dto.MovimientoHistoricoProductoFastView;
import com.pmarlen.model.dto.PedidoFastView;
import com.pmarlen.tasks.CallDIGIFACTTask;
import com.pmarlen.util.QueryXMLLoader;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Collection;
import java.util.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


/**
 * PedidoVentaBusinessLogic
 */

@Repository("pedidoVentaBusinessLogic")
public class PedidoVentaBusinessLogic {
	
	private Logger logger;

	private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
	
	private CallDIGIFACTTask callDIGIFACTTask;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
	
	private Almacen almacenDefault;
	
	public PedidoVentaBusinessLogic() {
		logger = LoggerFactory.getLogger(PedidoVentaBusinessLogic.class);        
        logger.debug("->PedidoVentaBusinessLogic, created");
	}
	
    public PedidoVenta crearPedidoCapturado(PedidoVenta pedidoVentaX,Usuario usuarioModifico) {
        Collection<PedidoVentaDetalle> detalleVentaPedidoOrigList = pedidoVentaX.getPedidoVentaDetalleCollection();
		Collection<PedidoVentaDetalle> detalleVentaPedidoInsert   = new ArrayList<PedidoVentaDetalle>();
        logger.debug("->crearPedidoCapturado");
        EntityManager em = null;
		PedidoVenta pedidoVenta = null;
		try {
            em = getEntityManager();
            em.getTransaction().begin();
        
			pedidoVenta = new PedidoVenta();
		
			pedidoVenta.setComentarios(pedidoVentaX.getComentarios());
			pedidoVenta.setPorcentajeDescuentoCalculado(pedidoVentaX.getPorcentajeDescuentoCalculado());
			pedidoVenta.setPorcentajeDescuentoExtra    (pedidoVentaX.getPorcentajeDescuentoExtra());
			pedidoVenta.setDescuentoAplicado(pedidoVentaX.getDescuentoAplicado());
			pedidoVenta.setFactoriva(pedidoVentaX.getFactoriva());
			pedidoVenta.setCliente(pedidoVentaX.getCliente());
			pedidoVenta.setFormaDePago(pedidoVentaX.getFormaDePago());
			pedidoVenta.setMetodoDePago(pedidoVentaX.getMetodoDePago());
			
			pedidoVenta.setUsuario(pedidoVentaX.getUsuario());
			pedidoVenta.setAlmacen(pedidoVentaX.getAlmacen());
			
			logger.debug("-> antes pedidoVenta="+pedidoVenta+", detail size:"+detalleVentaPedidoOrigList.size()+", {"+pedidoVenta.getCliente()+", "+pedidoVenta.getComentarios()+", "+pedidoVenta.getDescuentoAplicado()+", "+pedidoVenta.getFactoriva()+", "+pedidoVenta.getFormaDePago()+", "+pedidoVenta.getUsuario()+"}");
			em.persist(pedidoVenta);
			//em.flush();
			//em.merge(pedidoVenta);			
			
			logger.debug("-->> despues pedidoVenta="+pedidoVenta+": {"+pedidoVenta.getCliente()+", "+pedidoVenta.getComentarios()+", "+pedidoVenta.getDescuentoAplicado()+", "+pedidoVenta.getFactoriva()+", "+pedidoVenta.getFormaDePago()+", "+pedidoVenta.getUsuario()+"}");
			
            FormaDePago formaDePago = pedidoVentaX.getFormaDePago();
            if (formaDePago != null) {
                formaDePago = em.getReference(FormaDePago.class, formaDePago.getId());
                pedidoVenta.setFormaDePago(formaDePago);
            }
            Cliente cliente = pedidoVentaX.getCliente();
            if (cliente != null) {
                cliente = em.getReference(Cliente.class, cliente.getId());
                
                pedidoVenta.setCliente(cliente);
            }
            Usuario usuario = pedidoVentaX.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(Usuario.class, usuario.getUsuarioId());
                pedidoVenta.setUsuario(usuario);
            }
						
			
            //em.persist(pedidoVenta);
			//em.flush();
			//em.merge(pedidoVenta);
			
			//logger.debug("==>>pedidoVenta, despues 2do persist:"+pedidoVenta);
			//em.getTransaction().commit();            
			//logger.debug("================================================>>T1.commit();");
			//em.getTransaction().begin();
        
            if(detalleVentaPedidoOrigList != null ){
                for(PedidoVentaDetalle dvp: detalleVentaPedidoOrigList ) {
					
					PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();
					
					detalleVentaPedido.setCantidad(dvp.getCantidad());
					detalleVentaPedido.setPedidoVenta(pedidoVenta);
					detalleVentaPedido.setPrecioVenta(dvp.getPrecioVenta());
					detalleVentaPedido.setProducto(dvp.getProducto());
					
					detalleVentaPedidoInsert.add(detalleVentaPedido);
                    em.persist(detalleVentaPedido);  				
					
					//em.merge(detalleVentaPedido);
					//em.flush();
					logger.debug("\t->crearPedidoCapturado: detalleVentaPedido["+dvp.getCantidad()+" x "+dvp.getProducto()+"], pedidoVenta="+pedidoVenta+": persisted->>detalleVentaPedido="+detalleVentaPedido);
                }
            }
            pedidoVenta.setPedidoVentaDetalleCollection(detalleVentaPedidoInsert);
            
            
            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            pedidoVentaEstado.setPedidoVenta(pedidoVenta);
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_CAPTURADO));
            
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_CAPTURADO));
            final Date dateCaptura = new Date();
            logger.debug("==>>crearPedidoCapturado():"+dateCaptura);
            pedidoVentaEstado.setFecha(dateCaptura);
            pedidoVentaEstado.setPedidoVenta(pedidoVenta);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);
            //em.flush();
			//em.merge(pedidoVenta);
			
            em.getTransaction().commit();            
			logger.debug("==>>commit final!");
			
			return pedidoVenta;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
	
	public void movimientoEntreElmacenes(List<MovimientoEntreAlmacenes> movimientoEntreAlmacenesList,Integer sucursalDestinoId,int tipoAlmacen,Usuario usuarioModifico) {
        logger.debug("->movimientoEntreElmacenes");
        EntityManager em = null;
		Almacen almacenCentral  = getAlmacenDefault();
        Almacen almacenDestino = null;
        
		try {
            em = getEntityManager();
            em.getTransaction().begin();
			
			Query q = em.createQuery("select x from Sucursal x where x.id =:sucursalId");
			q.setParameter("sucursalId", sucursalDestinoId);	
			Sucursal sucDestino = (Sucursal)q.getSingleResult();
			final Collection<Almacen> almacenCollection = sucDestino.getAlmacenCollection();
			for(Almacen almacen: almacenCollection){
				if(almacen.getTipoAlmacen() == tipoAlmacen){
					almacenDestino = almacen;
				}
			}
			
			AlmacenProducto apOrigen  = null;
			AlmacenProducto apDestino = null;
			for(MovimientoEntreAlmacenes ma: movimientoEntreAlmacenesList){
				
				if(ma.getDestinoSurtir()==0){
					continue;
				}
				
				Query q2 = em.createQuery("select x from AlmacenProducto x where x.producto.id =:productoId");
				q2.setParameter("productoId", ma.getProducto().getId());
				
				List<AlmacenProducto> apList = q2.getResultList();
				for(AlmacenProducto ap: apList) {
					if(ap.getAlmacen().getId().intValue() == almacenCentral.getId().intValue()){
						apOrigen = ap;
					} else if(ap.getAlmacen().getId().intValue() == almacenDestino.getId().intValue()){
						apDestino = ap;
					}
				}
				
				if(apDestino == null){
					apDestino = new AlmacenProducto();
					apDestino.setAlmacen(almacenDestino);
					apDestino.setCantidadActual(ma.getDestinoSurtir());
					apDestino.setProducto(ma.getProducto());
					apDestino.setPrecioVenta(ma.getDestinoPV());
					
					em.persist(apDestino);
				} else {
					apDestino.setProducto(ma.getProducto());
					apDestino.setCantidadActual(apDestino.getCantidadActual()+ma.getDestinoSurtir());
					apDestino.setPrecioVenta(ma.getDestinoPV());
				}
				
				apOrigen.setCantidadActual(apOrigen.getCantidadActual()-ma.getDestinoSurtir());
				logger.debug("\t==>>PRODUCTO"+ma.getProducto().getId()+" ["+almacenCentral.getId()+"] -> "+ma.getDestinoSurtir()+"x["+ma.getProducto()+"] -> ["+almacenDestino.getId()+"]");
				em.flush();
				apDestino = null;
			}
			
            em.getTransaction().commit();            
			logger.debug("==>>commit final!");
			
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
	public PedidoVenta _crearPedidoVentaDetalleCapturado(PedidoVenta pedidoVenta,Collection<PedidoVentaDetalle> detalleVentaPedidoCollection) {
        
        logger.debug("->crearPedidoVentaDetalleCapturado");
        EntityManager em = null;
		
        try {
            em = getEntityManager();
            em.getTransaction().begin();
        
			logger.debug("================================================>>T1.commit();");
	    
            if(detalleVentaPedidoCollection != null ){
                for(PedidoVentaDetalle dvp: detalleVentaPedidoCollection ) {
					
					PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();
					
					detalleVentaPedido.setCantidad(dvp.getCantidad());
					detalleVentaPedido.setPedidoVenta(pedidoVenta);
					detalleVentaPedido.setPrecioVenta(dvp.getPrecioVenta());
					detalleVentaPedido.setProducto(dvp.getProducto());
					
                    em.persist(detalleVentaPedido);  					
					//em.merge(detalleVentaPedido);
					//em.flush();
					logger.debug("\t->crearPedidoCapturado: detalleVentaPedido["+dvp.getCantidad()+" x "+dvp.getProducto()+"], pedidoVenta="+pedidoVenta+": persisted->>detalleVentaPedido="+detalleVentaPedido);
                }
            }
            
            //em.flush();
			//em.merge(pedidoVenta);
			
            em.getTransaction().commit();            
			logger.debug("==>>commit final!");
			
			return pedidoVenta;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/*
    private void edit(PedidoVenta pedidoVenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = null;
        Collection<FacturaCliente>     facturaClienteCollection     = null;
        Collection<PedidoVentaEstado>  pedidoVentaEstadoCollection  = null;

        detalleVentaPedidoCollection    = pedidoVenta.getPedidoVentaDetalleCollection();
        facturaClienteCollection        = pedidoVenta.getFacturaClienteCollection();
        pedidoVentaEstadoCollection     = pedidoVenta.getPedidoVentaEstadoCollection();

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            
            pedidoVentaOld.setCliente(pedidoVenta.getCliente());
            pedidoVentaOld.setComentarios(pedidoVenta.getComentarios());
            pedidoVentaOld.setEsFiscal(pedidoVenta.getEsFiscal());
            pedidoVentaOld.setFormaDePago(pedidoVenta.getFormaDePago());
            pedidoVentaOld.setUsuario(pedidoVenta.getUsuario());
            
            Collection<PedidoVentaDetalle> detalleVentaPedidoOldCollection = pedidoVentaOld.getPedidoVentaDetalleCollection();
            Collection<FacturaCliente>     facturaClienteOldCollection     = pedidoVentaOld.getFacturaClienteCollection();
            Collection<PedidoVentaEstado>  pedidoVentaEstadoOldCollection  = pedidoVentaOld.getPedidoVentaEstadoCollection();

            if(detalleVentaPedidoOldCollection != null && detalleVentaPedidoOldCollection.size()>0){
                pedidoVentaOld.setPedidoVentaDetalleCollection(null);
                em.merge(pedidoVentaOld);

                for(PedidoVentaDetalle detalleVentaPedidoOld: detalleVentaPedidoOldCollection ) {
                    logger.debug("\t=>em.remove("+detalleVentaPedidoOld+");");
                    em.remove(detalleVentaPedidoOld);
                }
            }
            if(facturaClienteOldCollection != null && facturaClienteOldCollection.size()>0) {
                pedidoVentaOld.setFacturaClienteCollection(null);
                em.merge(pedidoVentaOld);

                for(FacturaCliente facturaClienteOld: facturaClienteOldCollection) {
                    logger.debug("\t=>em.remove("+facturaClienteOld+");");
                    em.remove(facturaClienteOld);
                }
            }

            if(pedidoVentaEstadoOldCollection != null && pedidoVentaEstadoOldCollection.size()>0) {
                pedidoVentaOld.setPedidoVentaEstadoCollection(null);
                em.merge(pedidoVentaOld);

                for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoOldCollection) {
                    logger.debug("\t=>em.remove("+pedidoVentaEstadoOld+");");
                    em.remove(pedidoVentaEstadoOld);
                }
            }

            em.flush();

            if(detalleVentaPedidoCollection != null && detalleVentaPedidoCollection.size()>0){
                pedidoVentaOld.setPedidoVentaDetalleCollection(detalleVentaPedidoCollection);
                em.merge(pedidoVentaOld);

                for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection ) {
                    detalleVentaPedido.getPedidoVentaDetallePK().setPedidoVentaId(pedidoVenta.getId());
                    logger.debug("\t=>em.persist("+detalleVentaPedido+");");
                    em.persist(detalleVentaPedido);
                }
            }
            
            if(facturaClienteCollection != null && facturaClienteCollection.size()>0) {
                pedidoVentaOld.setFacturaClienteCollection(facturaClienteCollection);
                em.merge(pedidoVentaOld);

                for(FacturaCliente facturaCliente: facturaClienteCollection) {
                    facturaCliente.setPedidoVenta(pedidoVenta);
                    logger.debug("\t=>em.persist("+facturaCliente+");");
                    em.persist(facturaCliente);
                }
            }

            if(pedidoVentaEstadoCollection != null && pedidoVentaEstadoCollection.size()>0) {
                pedidoVentaOld.setPedidoVentaEstadoCollection(pedidoVentaEstadoCollection);
                em.merge(pedidoVentaOld);

                for(PedidoVentaEstado pedidoVentaEstado: pedidoVentaEstadoCollection) {
                    pedidoVentaEstado.setPedidoVenta(pedidoVenta);
                    pedidoVentaEstado.getPedidoVentaEstadoPK().setPedidoVentaId(pedidoVenta.getId());
                    logger.debug("\t=>em.persist("+pedidoVentaEstado+");");
                    em.persist(pedidoVentaEstado);
                }
            }

            logger.debug("Ok, prepared to save");
            em.getTransaction().commit();
            logger.debug("===================>>> commited !!!");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
*/
    public void sincronizarPedido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
        modificarPedidoNoSurtido(pedidoVenta,
                usuarioModifico,
                new Estado(Constants.ESTADO_CAPTURADO),
                new Estado(Constants.ESTADO_SINCRONIZADO));
    }
	
	public void actualizarPedido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
        modificarPedidoNoSurtido(pedidoVenta,usuarioModifico);
    }

    public void verificarPedido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
        modificarPedidoNoSurtido(pedidoVenta,
                usuarioModifico,
                new Estado(Constants.ESTADO_VERIFICADO),
                new Estado(Constants.ESTADO_VERIFICADO));
    }
	
	private void modificarPedidoNoSurtido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
		modificarPedidoNoSurtido(pedidoVenta,usuarioModifico,null, null);
	}

    private void modificarPedidoNoSurtido(PedidoVenta pedidoVenta,Usuario usuarioModifico,Estado ultimoEstado, Estado sigEstado) throws IllegalStateException {
        Collection<PedidoVentaDetalle> PedidoVentaDetalleUpdatedCollection = null;
    
        PedidoVentaDetalleUpdatedCollection    = pedidoVenta.getPedidoVentaDetalleCollection();
    
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();

            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

			if(ultimoEstado != null) {
				for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
					if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
						throw new IllegalStateException("El pedido esta en estado Illegal");
					}
				}
			}

            pedidoVentaOld.setCliente(pedidoVenta.getCliente());
            pedidoVentaOld.setComentarios(pedidoVenta.getComentarios());
            pedidoVentaOld.setFormaDePago(pedidoVenta.getFormaDePago());
			pedidoVentaOld.setMetodoDePago(pedidoVenta.getMetodoDePago());
            pedidoVentaOld.setPorcentajeDescuentoCalculado(pedidoVenta.getPorcentajeDescuentoCalculado());
			pedidoVentaOld.setPorcentajeDescuentoExtra    (pedidoVenta.getPorcentajeDescuentoExtra());
			pedidoVentaOld.setDescuentoAplicado           (pedidoVenta.getDescuentoAplicado());
			
            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVentaOld.getPedidoVentaDetalleCollection();
            
            if(pedidoVentaDetalleCollection != null && pedidoVentaDetalleCollection.size()>0){
                pedidoVentaOld.setPedidoVentaDetalleCollection(null);
                em.merge(pedidoVentaOld);

                for(PedidoVentaDetalle detalleVentaPedidoOld: pedidoVentaDetalleCollection ) {
                    em.remove(detalleVentaPedidoOld);
                }
            }

            em.flush();

            em.merge(pedidoVentaOld);

            if(PedidoVentaDetalleUpdatedCollection != null && PedidoVentaDetalleUpdatedCollection.size()>0){
                
                for(PedidoVentaDetalle detalleVentaPedido: PedidoVentaDetalleUpdatedCollection ) {
                    
                    detalleVentaPedido.setProducto(detalleVentaPedido.getProducto());
                    detalleVentaPedido.setPedidoVenta(pedidoVenta);
                    
                }
                pedidoVentaOld.setPedidoVentaDetalleCollection(PedidoVentaDetalleUpdatedCollection);

                em.merge(pedidoVentaOld);

//                for(PedidoVentaDetalle pedidoVentaDetalle: detalleVentaPedidoCollection ) {
//                    pedidoVentaDetalle.getPedidoVentaDetallePK().setPedidoVentaId(pedidoVenta.getId());
//                    em.persist(pedidoVentaDetalle);
//                }
            }
			
			if(sigEstado != null){
				PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();

				pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
				pedidoVentaEstado.setEstado(sigEstado);

				Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
				pedidoVentaEstado.setEstado(sigEstado);
				pedidoVentaEstado.setFecha(new Date());
				pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
				pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
				em.persist(pedidoVentaEstado);
			}
            em.getTransaction().commit();            
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void surtirPedido(PedidoVenta pedidoVenta,Usuario usuarioModifico) throws IllegalStateException {
        Estado ultimoEstado = new Estado(Constants.ESTADO_VERIFICADO);
        logger.debug("-->> surtirPedido: pedidoVenta.almacen="+pedidoVenta.getAlmacen());
		
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Date fechaTransaccion = new Date();
            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
                if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
                    throw new IllegalStateException("El pedido esta en estado Ilegal");
                }
            }

            Collection<PedidoVentaDetalle> detalleVentaPedidoOldCollection = pedidoVentaOld.getPedidoVentaDetalleCollection();
			logger.debug("-->> DESCARGA INVENTARIO");

            for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoOldCollection ) {
                AlmacenProducto almacenProducto   = null;
                Producto productoASurtir = em.find(Producto.class, detalleVentaPedido.getProducto().getId());
                Collection<AlmacenProducto> almacenProductoCollection = productoASurtir.getAlmacenProductoCollection();
                for(AlmacenProducto ap: almacenProductoCollection){
					logger.debug("\t-->>ap.id=["+ap.getId()+"], producto.id="+ap.getProducto().getId()+", almacen.id="+ap.getAlmacen().getId()+", cantidad="+ap.getCantidadActual());
		
					if(ap.getAlmacen().getId().intValue() == pedidoVenta.getAlmacen().getId().intValue()){
						almacenProducto = ap;
						logger.debug("\t\t->> Ok found !!");
						break;
					}
                }

                if(almacenProducto == null ) {
                    throw new IllegalStateException("No hay registro de almacen para el producto:"+productoASurtir.getId());
                }
                
                int cantEnAlmacenTotal = almacenProducto.getCantidadActual() ;
                logger.debug("-->> DESCARGA INVENTARIO: almacen:"+almacenProducto.getAlmacen().getId()+", producto="+productoASurtir.getCodigoBarras()+", cantidadPedida="+detalleVentaPedido.getCantidad()+", almacenPrincipal = "+almacenProducto.getCantidadActual()+" = "+cantEnAlmacenTotal);
                
                
				if (detalleVentaPedido.getCantidad() <= almacenProducto.getCantidadActual()){
					
					almacenProducto.setCantidadActual(almacenProducto.getCantidadActual() - detalleVentaPedido.getCantidad());
					//------------------------------------------------------
					MovimientoHistoricoProducto mhpFis = new MovimientoHistoricoProducto();

					mhpFis.setAlmacen(almacenProducto.getAlmacen());					
					mhpFis.setCantidad(detalleVentaPedido.getCantidad());
					mhpFis.setCosto(productoASurtir.getCosto());
					mhpFis.setProducto(productoASurtir);
					mhpFis.setTipoMovimiento(new TipoMovimiento(Constants.TIPO_MOV_SALIDA_ALMACEN));
					mhpFis.setUsuario(usuarioModifico);
					mhpFis.setAlmacen(almacenProducto.getAlmacen());
					mhpFis.setFecha(fechaTransaccion);
					mhpFis.setProducto(productoASurtir);
					
					em.persist(mhpFis);
				} else {
					throw new IllegalStateException("No hay suficiente para surtir:ProductoId="+productoASurtir.getId()+", cantidadRequerida="+detalleVentaPedido.getCantidad()+", cantidadAlmacen=" +almacenProducto.getCantidadActual());
				}
			}
            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
			pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_SURTIDO));
			
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_SURTIDO));
            pedidoVentaEstado.setFecha(fechaTransaccion);
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
	

    public List<Producto> findProductoForReport() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT p FROM Producto p");

            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {
                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                for(MovimientoHistoricoProducto mhp: movimientoHistoricoProductoCollection) {
                }
                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
                for(AlmacenProducto ap: almacenProductoCollection) {
                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }

	public void guardarPedidosEnviados(Usuario usuarioAuthenticated, List<PedidoVenta> pedidoVentaList) throws Exception {
		
		EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
			logger.debug("\t-->> [1 ]guardarPedidosEnviados: "+usuarioAuthenticated+", pedidoVentaList.size()="+pedidoVentaList.size()+", em="+em);
			for(PedidoVenta pedidoVentaIter: pedidoVentaList) {				
				PedidoVenta pedidoVenta = new PedidoVenta();
				
				//BeanUtils.copyProperties(pedidoVentaIter, pedidoVenta, new String[]{"id","pedidoVentaEstadoCollection","pedidoVentaDetalleCollection","facturaClienteCollection"});
                pedidoVenta.setComentarios(pedidoVentaIter.getComentarios());
				pedidoVenta.setFactoriva(pedidoVentaIter.getFactoriva());
                FormaDePago formaDePago = pedidoVenta.getFormaDePago();
                logger.debug("\t\t-->> [1.0.0.2 ]guardarPedidosEnviados: formaDePago="+pedidoVentaIter.getFormaDePago());                
				formaDePago = em.getReference(FormaDePago.class, pedidoVentaIter.getFormaDePago().getId());
				pedidoVenta.setFormaDePago(formaDePago);
				logger.debug("\t\t-->> [1.0.0.4 ]guardarPedidosEnviados: pedidoVentaIter.getAlmacen()="+pedidoVentaIter.getAlmacen());                
				Almacen almacen= em.getReference(Almacen.class, pedidoVentaIter.getAlmacen().getId());
				logger.debug("\t\t-->> [1.0.0.5 ]guardarPedidosEnviados: almacen="+almacen);
				pedidoVenta.setAlmacen(almacen);
                Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVentaIter.getPedidoVentaDetalleCollection();
				
				Cliente clientePedido = pedidoVentaIter.getCliente();
				Cliente clienteFound = null;
				boolean clienteFoundBean = false;
				try {
					logger.debug("\t\t-->> [1.0.1 ] EntityNotFoundException ==>> em.find(Cliente.class, "+clientePedido.getId()+" ) ?");					
					clienteFound = em.find(Cliente.class, clientePedido.getId());
					logger.debug("\t\t-->> [1.0.2 ] EntityNotFoundException ==>> OK clienteFound="+clienteFound);
					
					if(clienteFound != null) {
						clienteFound = em.getReference(Cliente.class, clientePedido.getId());
						clienteFoundBean = true;
						logger.debug("\t\t-->> [1.0.2.1 ] EntityNotFoundException ==>> get Reference="+clienteFound);
					}
				} catch(Exception enfe){
					logger.error("\t\t-->> [1.1 ] EntityNotFoundException ==>> "+enfe.getMessage()+" catched, but :)");
					clienteFound = null;
				}
				
				logger.debug("\t\t-->> [1.1.1. ] ==>> clienteFoundBean="+clienteFoundBean);
				
				if( ! clienteFoundBean ) {					
					Cliente clienteNuevo = new Cliente();
					
					BeanUtils.copyProperties(clientePedido,clienteNuevo,new String[]{"id","pedidoVentaCollection","contactoCollection"});
					
					logger.debug("\t\t\t-->> [1.3.5]guardarPedidosEnviados: em.persist(clienteNuevo) ?");
					em.persist(clienteNuevo);				
					logger.debug("\t\t\t-->> [1.3.7]guardarPedidosEnviados: OK, em.persist(clienteNuevo.id="+clienteNuevo.getId()+")");
					em.refresh(clienteNuevo);
					
					pedidoVenta.setCliente(clienteNuevo);
				} else {
					logger.debug("\t\t\t-->> [1.2.1 ]guardarPedidosEnviados: clienteFound="+clienteFound.getId());
					pedidoVenta.setCliente(clienteFound);
				}
				logger.debug("\t\t-->> [1.4]guardarPedidosEnviados: ");
				Usuario usuario = pedidoVentaIter.getUsuario();

				Usuario usuarioRef = em.getReference(Usuario.class, usuario.getUsuarioId());
				pedidoVenta.setUsuario(usuarioRef);				
                
				logger.debug("\t\t-->> [1.5]guardarPedidosEnviados: pedidoVentaEstadoCollection="+pedidoVenta.getPedidoVentaEstadoCollection());
				pedidoVenta.setPedidoVentaEstadoCollection(null);                
                logger.debug("\t\t-->> [1.6]guardarPedidosEnviados: PedidoVenta id="+pedidoVenta.getId());
				em.persist(pedidoVenta);
				logger.debug("\t\t-->> [1.7]guardarPedidosEnviados: PedidoVenta id="+pedidoVenta.getId());
				//==============================================================
				if(detalleVentaPedidoCollection != null ){
					for(PedidoVentaDetalle pvd: detalleVentaPedidoCollection ) {
						
						PedidoVentaDetalle pvdInsert = new PedidoVentaDetalle();
						
						BeanUtils.copyProperties(pvd, pvdInsert);
						
						pvdInsert.setPedidoVenta(pedidoVenta);
						pvdInsert.setProducto(pvd.getProducto());
												
						logger.debug("\t\t\t-->> [1.7.1]guardarPedidosEnviados: persist PedidoVentaDetalle: pvdInsert="+pvdInsert);
						em.persist(pvdInsert);                    
                        logger.debug("\t\t\t-->> [1.7.2]guardarPedidosEnviados: OK persist PedidoVentaDetalle");
					}
				}
				//==============================================================
				Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVentaIter.getPedidoVentaEstadoCollection();
                PedidoVentaEstado pedidoVentaEstadoNuevo = new PedidoVentaEstado();
				
				pedidoVentaEstadoNuevo.setPedidoVenta(pedidoVenta);
				pedidoVentaEstadoNuevo.setEstado(new Estado(Constants.ESTADO_ENVIADO));			
				
				Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioAuthenticated.getUsuarioId());
				pedidoVentaEstadoNuevo.setEstado(new Estado(Constants.ESTADO_CAPTURADO));
				pedidoVentaEstadoNuevo.setFecha(new Date());
				pedidoVentaEstadoNuevo.setPedidoVenta(pedidoVenta);
				pedidoVentaEstadoNuevo.setUsuario(usuarioModificoRefreshed);
				em.persist(pedidoVentaEstadoNuevo);
				logger.debug("\t\t-->> [1.7.9]guardarPedidosEnviados: pedidoVentaEstadoNuevo="+pedidoVentaEstadoNuevo.getId());
				/*
				if(pedidoVentaEstadoCollection != null) {
                    pedidoVentaEstadoCollection.add(pedidoVentaEstadoNuevo);
                } else {
                    pedidoVentaEstadoCollection = new ArrayList<PedidoVentaEstado>();
                    pedidoVentaEstadoCollection.add(pedidoVentaEstadoNuevo);
                }
                logger.debug("\t\t-->> [1.8]guardarPedidosEnviados: pedidoVentaEstadoCollection.size()="+pedidoVentaEstadoCollection.size());
                
                for(PedidoVentaEstado pedidoVentaEstadoIter: pedidoVentaEstadoCollection) {

                    PedidoVentaEstado pedidoVentaEstadoInsert = new PedidoVentaEstado();

                    BeanUtils.copyProperties(pedidoVentaEstadoIter, pedidoVentaEstadoInsert);
                    pedidoVentaEstadoInsert.getPedidoVentaEstadoPK().setPedidoVentaId(pedidoVenta.getId());
                    em.persist(pedidoVentaEstadoInsert);
                    logger.debug("\t\t\t-->> [1.8.1]guardarPedidosEnviados: OK persist PedidoVentaEstado: pedioId="+pedidoVentaEstadoInsert);
                }
				*/
				logger.debug("\t\t-->> [1.15]guardarPedidosEnviados: OK Pedido Guardado");
				em.flush();
				logger.debug("\t\t-->> [1.16]guardarPedidosEnviados: em.flush(); Ok!!");
			}
			logger.debug("\t-->> guardarPedidosEnviados: commit ?");
            em.getTransaction().commit();            
			logger.debug("\t-->> guardarPedidosEnviados: commit OK");
        } catch (Exception e) {
			//logger.error("Exception caught:", e);
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
				//logger.debug("\t-->> guardarPedidosEnviados: ROLLBACK :(");
            }
            throw e;
        } finally {
            if (em != null) {
                em.close();                
            }
        } 
	}
	

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Producto as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {
//                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
//                for (Multimedio multimedio: multimedioCollection){
//                }
//                Collection<CodigoDeBarras> codigoDeBarrasCollection = x.getCodigoDeBarrasCollection();
//                for(CodigoDeBarras codigoDeBarras: codigoDeBarrasCollection) {
//                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Producto> findAllProductoConMovimientosEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Producto as o");
            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {

                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                for (Multimedio multimedio: multimedioCollection){
                }

                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
                for(AlmacenProducto almacenProducto: almacenProductoCollection){
                    almacenProducto.getCantidadActual();
                }

                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                for(MovimientoHistoricoProducto movimientoHistoricoProducto: movimientoHistoricoProductoCollection){
                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }


    public List<Producto> findProductoEntitiesByMarca(Marca marca) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Producto as o where o.marca.id = :marcaId");
            q.setParameter("marcaId",marca.getId());

            List<Producto> resultList = q.getResultList();

            for (Producto x: resultList) {
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                for (Multimedio multimedio: multimedioCollection){
                }
            }

            return resultList;
        } finally {
            em.close();
        }
    }


    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            Producto x = em.find(Producto.class, id);
            if( x != null) {
                Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
                for (Multimedio multimedio: multimedioCollection){
                }

                Collection<AlmacenProducto> almacenProductoCollection = x.getAlmacenProductoCollection();
                for(AlmacenProducto almacenProducto:almacenProductoCollection){
                }

                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                for(MovimientoHistoricoProducto movimientoHistoricoProducto: movimientoHistoricoProductoCollection){
                }
            }
            return x;
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Producto as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }	
	
    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Cliente as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Cliente as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Usuario> resultLsit = q.getResultList();
            for (Usuario x : resultLsit) {
                Collection<Perfil> perfilCollection = x.getPerfilCollection();
                Collection<PedidoVenta> pedidoVentaCollection = x.getPedidoVentaCollection();
                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = x.getPedidoVentaEstadoCollection();
                Collection<MensajesParaPortal> mensajesParaPortalCollection = x.getMensajesParaPortalCollection();

                x.setPerfilCollection(null);
                x.setPedidoVentaCollection(null);
                x.setMovimientoHistoricoProductoCollection(null);
                x.setPedidoVentaEstadoCollection(null);
                x.setMensajesParaPortalCollection(null);
            }
            return resultLsit;
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String usuarioId, String password) throws NoResultException{
        EntityManager em = getEntityManager();
        try {
            Usuario userAuthenticated = null;
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId and u.password = :password");
            q.setParameter("usuarioId", usuarioId);
            q.setParameter("password", password);
            userAuthenticated = (Usuario) q.getSingleResult();

            Collection<Perfil> perfilCollection = userAuthenticated.getPerfilCollection();
            Collection<PedidoVenta> pedidoVentaCollection = userAuthenticated.getPedidoVentaCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = userAuthenticated.getMovimientoHistoricoProductoCollection();
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = userAuthenticated.getPedidoVentaEstadoCollection();
            Collection<MensajesParaPortal> mensajesParaPortalCollection = userAuthenticated.getMensajesParaPortalCollection();

            userAuthenticated.setPerfilCollection(null);
            userAuthenticated.setPedidoVentaCollection(null);
            userAuthenticated.setMovimientoHistoricoProductoCollection(null);
            userAuthenticated.setPedidoVentaEstadoCollection(null);
            userAuthenticated.setMensajesParaPortalCollection(null);

            return userAuthenticated;
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            Usuario x = em.find(Usuario.class, id);

            Collection<Perfil> perfilCollection = x.getPerfilCollection();
            for (Perfil perfil : perfilCollection) {
            }

            return x;
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }	
	
    public List<PedidoVenta> findPedidoVentaEntities() {
        return findPedidoVentaEntities(true, -1, -1);
    }

    public List<PedidoVenta> findPedidoVentaEntities(int maxResults, int firstResult) {
        return findPedidoVentaEntities(false, maxResults, firstResult);
    }

    private List<PedidoVenta> findPedidoVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PedidoVenta as o");
			
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<PedidoVenta> result = q.getResultList();
            for(PedidoVenta pedidoVenta:result){
//                Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVenta.getPedidoVentaDetalleCollection();
//                for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection) {
//                }

                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
                for(PedidoVentaEstado pedidoVentaEstado:pedidoVentaEstadoCollection) {
                }

//                Collection<FacturaCliente> facturaClienteCollection = pedidoVenta.getFacturaClienteCollection();
//                for(FacturaCliente facturaCliente:facturaClienteCollection){
//                }
            }
            return result;
        } finally {
            em.close();
        }
    }

    public PedidoVenta findPedidoVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            PedidoVenta pedidoVenta = em.find(PedidoVenta.class, id);

            Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVenta.getPedidoVentaDetalleCollection();
            for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection) {
            }

            Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
            for(PedidoVentaEstado pedidoVentaEstado:pedidoVentaEstadoCollection) {
            }

            return pedidoVenta;
        } finally {
            em.close();
        }
    }
    public int getPedidoVentaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PedidoVenta as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
	
    public List<FormaDePago> findFormaDePagoEntities() {
        return findFormaDePagoEntities(true, -1, -1);
    }

    public List<FormaDePago> findFormaDePagoEntities(int maxResults, int firstResult) {
        return findFormaDePagoEntities(false, maxResults, firstResult);
    }

    private List<FormaDePago> findFormaDePagoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from FormaDePago as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FormaDePago findFormaDePago(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormaDePago.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormaDePagoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from FormaDePago as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

	public void generarCFDPedido(PedidoVenta pedidoVenta, Usuario usuarioModifico) {
        Estado ultimoEstado = new Estado(Constants.ESTADO_SURTIDO);
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Date fechaTransaccion = new Date();
            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
			
			pedidoVentaOld.getCliente().getPoblacion().getMunicipioODelegacion();
			Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVentaOld.getPedidoVentaDetalleCollection();
			for(PedidoVentaDetalle pvd: pedidoVentaDetalleCollection){
				pvd.getProducto();
			}
			
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
				//if(pedidoVenta.getCfdVenta()!=null && pedidoVenta.getCfdVenta().getCallingErrorResult()== null){
				//	if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
				//		throw new IllegalStateException("El pedido esta en estado Ilegal");
				//	}
				//}
            }

            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
			pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_FACTURADO));
			
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_FACTURADO));
            pedidoVentaEstado.setFecha(new Date());
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();
			
			logger.error("\t\t-->> Aqui se invoca a CallDIGIFACTTask !!");
			
			
			callDIGIFACTTask.invokeDIGIFACTWebService(pedidoVentaOld);
			logger.error("\t\t<<-- Aqui se termina de invocar a CallDIGIFACTTask !!");
        } finally {
            if (em != null) {
                em.close();
            }
        }
	}

	
	public void enviarPedido(PedidoVenta pedidoVenta, Usuario usuarioModifico) {
        Estado ultimoEstado = new Estado(Constants.ESTADO_FACTURADO);
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Date fechaTransaccion = new Date();
            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
                if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
                    throw new IllegalStateException("El pedido esta en estado Ilegal");
                }
            }

            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
			pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_ENVIADO));
			
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_ENVIADO));
            pedidoVentaEstado.setFecha(new Date());
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();
			
        } finally {
            if (em != null) {
                em.close();
            }
        }
	}
	
	public void confirmarDevolucionPedido(PedidoVenta pedidoVenta, Usuario usuarioModifico) {
        Estado ultimoEstado = new Estado(Constants.ESTADO_ENVIADO);
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Date fechaTransaccion = new Date();
            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
                if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
                    throw new IllegalStateException("El pedido esta en estado Ilegal");
                }
            }

            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
			pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_DEVUELTO));
			
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_DEVUELTO));
            pedidoVentaEstado.setFecha(new Date());
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();
			
        } finally {
            if (em != null) {
                em.close();
            }
        }
	}
	
	public void confirmarEntregaPedido(PedidoVenta pedidoVenta, Usuario usuarioModifico) {
        Estado ultimoEstado = new Estado(Constants.ESTADO_ENVIADO);
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Date fechaTransaccion = new Date();
            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            for(PedidoVentaEstado pedidoVentaEstadoOld: pedidoVentaEstadoCollectionOld) {
                if(pedidoVentaEstadoOld.getEstado().getId() > ultimoEstado.getId()) {
                    throw new IllegalStateException("El pedido esta en estado Ilegal");
                }
            }

            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
			pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_ENTREGADO));
			
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_ENTREGADO));
            pedidoVentaEstado.setFecha(new Date());
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();
			
        } finally {
            if (em != null) {
                em.close();
            }
        }
	}
	
	public void cancelarPedido(PedidoVenta pedidoVenta, Usuario usuarioModifico) {
        
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Date fechaTransaccion = new Date();
            PedidoVenta pedidoVentaOld = em.find(PedidoVenta.class, pedidoVenta.getId());
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = pedidoVentaOld.getPedidoVentaEstadoCollection();

            PedidoVentaEstado pedidoVentaEstado = new PedidoVentaEstado();
            
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
			pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_CANCELADO));
			
            Usuario usuarioModificoRefreshed = em.getReference(Usuario.class, usuarioModifico.getUsuarioId());
            pedidoVentaEstado.setEstado(new Estado(Constants.ESTADO_CANCELADO));
            pedidoVentaEstado.setFecha(new Date());
            pedidoVentaEstado.setPedidoVenta(pedidoVentaOld);
            pedidoVentaEstado.setUsuario(usuarioModificoRefreshed);
            em.persist(pedidoVentaEstado);

            em.getTransaction().commit();
			
        } finally {
            if (em != null) {
                em.close();
            }
        }
	}

	/**
	 * @param callDIGIFACTTask the callDIGIFACTTask to set
	 */
	@Autowired
	public void setCallDIGIFACTTask(CallDIGIFACTTask callDIGIFACTTask) {
		this.callDIGIFACTTask = callDIGIFACTTask;
	}
	
	public Almacen getAlmacenDefault() {
		
		if(almacenDefault == null) {
			EntityManager em = null;
			Sucursal sucursal = null;
			try {
				em = getEntityManager();
				Query q = em.createQuery("select o from Sucursal as o where o.sucursal is null");
				
				sucursal =  (Sucursal)q.getSingleResult();
				Collection<Almacen> almacenCollection = sucursal.getAlmacenCollection();
				for(Almacen a: almacenCollection){
					if(a.getTipoAlmacen() == Constants.ALMACEN_LINEA){						
						almacenDefault = a;						
						logger.debug("==>>getAlmacenDefault: getAlmacenDefault="+almacenDefault);
					}
				}
				
			} finally {
				if (em != null) {
					em.close();
				}
			}	
		}
		
		
		return almacenDefault;
	}
		
	public List<PedidoFastView> findPedidoFastView() {
        EntityManager em = getEntityManager();
		List<PedidoFastView> result = new ArrayList<PedidoFastView>();
        try {
            Query q = em.createNativeQuery(QueryXMLLoader.getInstance().loadQuery("verPedidos"));
			
			List<Object[]> firstResult = q.getResultList();
			//logger.debug("-->>findPedidoFastView: firstResult.size="+firstResult.size());
			
			for(Object[] r: firstResult){
				final PedidoFastView pedidoFastView = new PedidoFastView(r);				
				result.add(pedidoFastView);			
			}
			
            return result;
        } finally {
            em.close();
        }
    }

	public List<InventarioFastView> findInventarioFastView(Integer almacenId) {
        EntityManager em = getEntityManager();
		List<InventarioFastView> result = new ArrayList<InventarioFastView>();
        try {
            Query q = em.createNativeQuery(QueryXMLLoader.getInstance().loadQuery("inventarios"));
			q.setParameter("almacenId", almacenId);
			List<Object[]> firstResult = q.getResultList();
			//logger.debug("-->>findInventarioFastView: firstResult.size="+firstResult.size());
			
			for(Object[] r: firstResult){
				//logger.debug("-->>findInventarioFastView: r="+Arrays.asList(r));
				InventarioFastView ifv = new InventarioFastView(r);				
				result.add(ifv);			
			}
			
            return result;
        } finally {
            em.close();
        }
    }

	public List<InventarioFastView> findInventarioFastView(Integer almacenId,String codigoBarras) {
        EntityManager em = getEntityManager();
		List<InventarioFastView> result = new ArrayList<InventarioFastView>();
        try {
            Query q = em.createNativeQuery(QueryXMLLoader.getInstance().loadQuery("inventarioEspecifico"));
			q.setParameter("almacenId"   , almacenId);
			q.setParameter("codigoBarras", codigoBarras);
			
			List<Object[]> firstResult = q.getResultList();
			//logger.debug("-->>findInventarioFastView: firstResult.size="+firstResult.size());
			
			for(Object[] r: firstResult){
				//logger.debug("-->>findInventarioFastView: r="+Arrays.asList(r));
				InventarioFastView ifv = new InventarioFastView(r);				
				result.add(ifv);			
			}
			
            return result;
        } finally {
            em.close();
        }
    }

	public List<MovimientoHistoricoProductoFastView> findMovimientoHistoricoProductoFastView(Integer almacenId,String codigoBarras) {
        EntityManager em = getEntityManager();
		List<MovimientoHistoricoProductoFastView> result = new ArrayList<MovimientoHistoricoProductoFastView>();
        try {
            Query q = em.createNativeQuery(QueryXMLLoader.getInstance().loadQuery("movimientoHistoricoRroductoEyS"));
			q.setParameter("almacenId"   , almacenId);
			q.setParameter("productoCodigoBarras", codigoBarras);
			
			List<Object[]> firstResult = q.getResultList();
			logger.debug("-->>findMovimientoHistoricoProductoFastView: firstResult.size="+firstResult.size());
			Integer saldoTotal = 0;

			for(Object[] r: firstResult){
				
				MovimientoHistoricoProductoFastView mhpfv = new MovimientoHistoricoProductoFastView(r);				
				
				if(mhpfv.getEntrada()){
					saldoTotal += mhpfv.getCantidad();
				} else {
					saldoTotal -= mhpfv.getCantidad();
				}
				mhpfv.setSaldo(saldoTotal);
	
				result.add(mhpfv);			
			
			}
			
            return result;
        } finally {
            em.close();
        }
    }

}
