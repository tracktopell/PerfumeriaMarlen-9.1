package com.pmarlen.businesslogic;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.*;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.Collection;
import java.util.Date;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * entradaAlmacenBusinessLogic
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

	public EntradaAlmacen crearCompraCapturada(EntradaAlmacen entradaAlmacen, Usuario usuarioModifico) {
		Collection<EntradaAlmacenDetalle> entradaAlmacenListOriginal = entradaAlmacen.getEntradaAlmacenDetalleCollection();
		Collection<EntradaAlmacenDetalle> entradaAlmacenDetalleInsert = new ArrayList<EntradaAlmacenDetalle>();
		logger.debug("->crearCompraCapturada");
		EntityManager em = null;
		EntradaAlmacen entradaAlmacenInsert = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			
			Date fechaTransaccion = new Date();
			
			entradaAlmacenInsert = new EntradaAlmacen();

			entradaAlmacenInsert.setComentarios(entradaAlmacen.getComentarios());
			entradaAlmacenInsert.setDescuentoAplicado(entradaAlmacen.getDescuentoAplicado());
			entradaAlmacenInsert.setFecha(fechaTransaccion);
			entradaAlmacenInsert.setUsuario(entradaAlmacen.getUsuario());
			entradaAlmacenInsert.setAlmacen(entradaAlmacen.getAlmacen());

			em.persist(entradaAlmacenInsert);
			//em.flush();
			//em.merge(entradaAlmacen);			

			Usuario usuario = entradaAlmacen.getUsuario();
			if (usuario != null) {
				usuario = em.getReference(Usuario.class, usuario.getUsuarioId());
				entradaAlmacenInsert.setUsuario(usuario);
			}


			//em.persist(entradaAlmacen);
			//em.flush();
			//em.merge(entradaAlmacen);

			//logger.debug("==>>entradaAlmacen, despues 2do persist:"+entradaAlmacen);
			//em.getTransaction().commit();            
			//logger.debug("================================================>>T1.commit();");
			//em.getTransaction().begin();

			if (entradaAlmacenListOriginal != null) {
				for (EntradaAlmacenDetalle ead : entradaAlmacenListOriginal) {

					EntradaAlmacenDetalle eadInsert = new EntradaAlmacenDetalle();

					eadInsert.setCantidad(ead.getCantidad());
					eadInsert.setPrecioVenta(ead.getPrecioVenta());
					eadInsert.setProducto(ead.getProducto());
					eadInsert.setEntradaAlmacen(entradaAlmacenInsert);
					eadInsert.setCosto(0.0);
					
					entradaAlmacenDetalleInsert.add(eadInsert);
					em.persist(eadInsert);

					//----------------------------------------------------------
					AlmacenProducto almacenProducto = null;
					Producto productoASurtir = em.find(Producto.class, eadInsert.getProducto().getId());
					Collection<AlmacenProducto> almacenProductoCollection = productoASurtir.getAlmacenProductoCollection();
					for (AlmacenProducto ap : almacenProductoCollection) {
						logger.debug("\t-->>ap.id=[" + ap.getId() + "], producto.id=" + ap.getProducto().getId() + ", almacen.id=" + ap.getAlmacen().getId() + ", cantidad="
								+ ap.getCantidadActual());

						if (ap.getAlmacen().getId().intValue() == entradaAlmacen.getAlmacen().getId().intValue()) {
							almacenProducto = ap;
							logger.debug("\t\t->> Ok found !!");
							break;
						}
					}

					if (almacenProducto == null) {
						throw new IllegalStateException("No hay registro de almacen para el producto:" + productoASurtir.getId());
					}
					
					int cantEnAlmacenTotal = almacenProducto.getCantidadActual();
					
					almacenProducto.setCantidadActual(almacenProducto.getCantidadActual() + eadInsert.getCantidad());
					almacenProducto.setPrecioVenta   (eadInsert.getPrecioVenta());
					
					//----------------------------------------------------------
					MovimientoHistoricoProducto mhpFis = new MovimientoHistoricoProducto();

					mhpFis.setAlmacen(almacenProducto.getAlmacen());
					mhpFis.setCantidad(eadInsert.getCantidad());
					mhpFis.setCosto(productoASurtir.getCosto());
					mhpFis.setProducto(productoASurtir);
					mhpFis.setTipoMovimiento(new TipoMovimiento(Constants.TIPO_MOV_ENTRADA_ALMACEN));
					mhpFis.setUsuario(usuarioModifico);
					mhpFis.setAlmacen(almacenProducto.getAlmacen());
					mhpFis.setFecha(fechaTransaccion);
					mhpFis.setProducto(productoASurtir);

					em.persist(mhpFis);

					//em.merge(entrada);
					//em.flush();
					logger.debug("\t->crearCompraCapturada: entrada[" + ead.getCantidad() + " x " + ead.getProducto() + "], entradaAlmacen=" + entradaAlmacenInsert + ": persisted->>entrada=" + eadInsert);
				}
			}
			entradaAlmacenInsert.setEntradaAlmacenDetalleCollection(entradaAlmacenDetalleInsert);

			//em.flush();
			//em.merge(entradaAlmacen);

			em.getTransaction().commit();
			logger.debug("==>>commit final!");

			return entradaAlmacenInsert;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
