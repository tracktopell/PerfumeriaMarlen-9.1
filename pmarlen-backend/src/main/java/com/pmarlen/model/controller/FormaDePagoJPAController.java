package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.FormaDePago;
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
 * FormaDePagoJPAController
 */
@Repository("formaDePagoJPAController")
public class FormaDePagoJPAController extends EntityJPAController<FormaDePago> {

	public FormaDePagoJPAController() {
		super(LoggerFactory.getLogger(FormaDePagoJPAController.class));
	}
	
	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public FormaDePago newEntityInstance() {
		return new FormaDePago();
	}

	@Override
	protected Class getEntityClass() {
		return FormaDePago.class;
	}

	@Override
	protected String getEntityClassName() {
		return "FormaDePago";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(FormaDePago entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(FormaDePago x) {
		final Collection<PedidoVenta> marcaCollection = x.getPedidoVentaCollection();
		final Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
	}*/

	@Override
	public FormaDePago findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from FormaDePago x where upper(x.nombre) = upper(:nombre )");
			
			q.setParameter("nombre", readable);
			
			FormaDePago x = (FormaDePago) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object formaDePagoId, List<String> illegalOrphanMessages) {
		logger.debug("->validateBeforeDestroy: formaDePagoId="+formaDePagoId);		
		Query q = em.createQuery("select count(x) from PedidoVenta x where x.formaDePago.id = :formaDePagoId");		
		q.setParameter("formaDePagoId", formaDePagoId);
		int countRelated = ((Long) q.getSingleResult()).intValue();

		if (countRelated > 0) {
			final String name = "Hay " + countRelated + " PedidoVenta(s), relacionados a esta FormaDePago";
			illegalOrphanMessages.add(name.toUpperCase());
		}
	}
	 
}
