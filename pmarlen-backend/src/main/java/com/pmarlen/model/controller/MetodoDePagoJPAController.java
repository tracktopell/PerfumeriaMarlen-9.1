package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.MetodoDePago;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.Multimedio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MetodoDePagoJPAController
 */
@Repository("metodoDePagoJPAController")
public class MetodoDePagoJPAController extends EntityJPAController<MetodoDePago> {

	public MetodoDePagoJPAController() {
		super(LoggerFactory.getLogger(MetodoDePagoJPAController.class));
	}
	
	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public MetodoDePago newEntityInstance() {
		return new MetodoDePago();
	}

	@Override
	protected Class getEntityClass() {
		return MetodoDePago.class;
	}

	@Override
	protected String getEntityClassName() {
		return "MetodoDePago";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(MetodoDePago entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(MetodoDePago x) {
		final Collection<PedidoVenta> marcaCollection = x.getPedidoVentaCollection();
		final Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
	}*/

	@Override
	public MetodoDePago findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from MetodoDePago x where upper(x.nombre) = upper(:nombre )");
			
			q.setParameter("nombre", readable);
			
			MetodoDePago x = (MetodoDePago) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object metodoDePagoId, List<String> illegalOrphanMessages) {
		logger.debug("->validateBeforeDestroy: metodoDePagoId="+metodoDePagoId);		
		Query q = em.createQuery("select count(x) from PedidoVenta x where x.metodoDePago.id = :metodoDePagoId");		
		q.setParameter("metodoDePagoId", metodoDePagoId);
		int countRelated = ((Long) q.getSingleResult()).intValue();

		if (countRelated > 0) {
			final String name = "Hay " + countRelated + " PedidoVenta(s), relacionados a esta MetodoDePago";
			illegalOrphanMessages.add(name.toUpperCase());
		}
	}
	 
}
