package com.pmarlen.businesslogic;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.*;
import com.pmarlen.model.dto.MovimientoEntreAlmacenes;
import com.pmarlen.tasks.CallDIGIFACTTask;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Collection;
import java.util.Date;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


/**
 * PedidoVentaBusinessLogic
 */

@Repository("compraBusinessLogic")
public class CompraBusinessLogic {
	
	private Logger logger;

	private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }
	
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
	
	public CompraBusinessLogic() {
		logger = LoggerFactory.getLogger(CompraBusinessLogic.class);        
        logger.debug("->CompraBusinessLogic, created");
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
	    
}
