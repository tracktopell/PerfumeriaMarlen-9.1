package com.pmarlen.model.controller;

import com.pmarlen.model.beans.Almacen;
import org.springframework.stereotype.Repository;
import com.pmarlen.model.beans.Sucursal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SucursalJPAController
 */
@Repository("sucursalJPAController")
public class SucursalJPAController extends EntityJPAController<Sucursal> {

	public SucursalJPAController() {
		super(LoggerFactory.getLogger(SucursalJPAController.class));
	}

	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Sucursal newEntityInstance() {
		Sucursal marcaNewInstance = new Sucursal();
		logger.debug("->newEntityInstance: marcaNewInstance=" + marcaNewInstance);
		return marcaNewInstance;
	}

	@Override
	protected Class getEntityClass() {
		return Sucursal.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Sucursal";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(Sucursal entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Sucursal x) {
		x.getAlmacenCollection();
		x.getSucursalCollection();
		x.getUsuarioCollection();

		x.getPoblacion();
	}*/

	@Override
	public Sucursal findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Sucursal x where upper(x.nombre) = upper(:nombre )");

			q.setParameter("nombre", readable);

			Sucursal x = (Sucursal) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object sucursalId, List<String> illegalOrphanMessages) {
		logger.debug("->validateBeforeDestroy: sucursalId=" + sucursalId);
		Query q = null;
		int countRelated = -1;
		String relatedEntities[] = {
			"Almacen",
			"Sucursal",
			"Usuario"};
		for (String re : relatedEntities) {
			q = em.createQuery("select count(x) from " + re + " x where x.sucursal.id = :sucursalId");
			q.setParameter("sucursalId", sucursalId);
			countRelated = ((Long) q.getSingleResult()).intValue();

			if (countRelated > 0) {
				final String name = "Hay " + countRelated + " " + re + "(s), relacionados a esta Sucursal";
				illegalOrphanMessages.add(name.toUpperCase());
			}
		}
	}

	public Sucursal getSucursalPrincipal() {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select object(o) from Sucursal as o where o.sucursal = null");
			
			Sucursal x = (Sucursal) q.getSingleResult();
			retrieveEagerAllAtributes(x);
			for (Almacen a : x.getAlmacenCollection()) {
				a.getTipoAlmacen();
			}
			return x;
		} finally {
			em.close();
		}
	}
}
