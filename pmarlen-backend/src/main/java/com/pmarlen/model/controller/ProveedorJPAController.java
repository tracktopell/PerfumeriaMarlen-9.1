package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.Proveedor;
import com.pmarlen.model.beans.Usuario;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ProveedorJPAController
 */

@Repository("proveedorJPAController")
public class ProveedorJPAController extends EntityJPAController<Proveedor>{

	public ProveedorJPAController() {
		super(LoggerFactory.getLogger(ProveedorJPAController.class));
	}

	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Proveedor newEntityInstance() {
		return new Proveedor();
	}

	@Override
	protected Class getEntityClass() {
		return Proveedor.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Proveedor";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(Proveedor entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Proveedor x) {
		x.getPoblacion();
		x.getPedidoVentaCollection();
		x.getNotaCreditoCollection();
	}*/

	@Override
	public Proveedor findEntityByReadableProperty(String rfc) {
		logger.debug("->@Override findById:rfc="+rfc);
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Proveedor x where upper(x.rfc) = upper(:rfc)");

			q.setParameter("rfc", rfc);
			Proveedor x = (Proveedor) q.getSingleResult();
			
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object proveedorId, List<String> illegalOrphanMessages) {
		Query q = null;
		int countRelated = -1;
		
		String relatedEntities[] = {
				"PedidoVenta",
				"NotaCredito"};
		for(String re:relatedEntities){
			q = em.createQuery("select count(x) from "+re+" x where x.proveedor.id= :proveedorId");
			q.setParameter("proveedorId", proveedorId);
			countRelated = ((Long) q.getSingleResult()).intValue();

			if (countRelated > 0) {
				final String name = "Hay " + countRelated + " "+re+"(s), relacionados a este Usuario";
				illegalOrphanMessages.add(name.toUpperCase());
			}
		}
	}

}
