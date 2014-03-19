package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * PerfilJPAController
 */
@Repository("perfilJPAController")
public class PerfilJPAController extends EntityJPAController<Perfil>{

	public PerfilJPAController() {
		super(LoggerFactory.getLogger(PerfilJPAController.class));
	}
	
	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Perfil newEntityInstance() {
		Perfil perfilNewInstance = new Perfil();
		logger.debug("->newEntityInstance: perfilNewInstance="+perfilNewInstance);
		return perfilNewInstance;
	}

	@Override
	protected Class getEntityClass() {
		return Perfil.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Perfil";
	}

	@Override
	protected Object getEntityId(Perfil entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Perfil x) {		
		x.getUsuarioCollection();
	}*/

	@Override
	public Perfil findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Perfil x where upper(x.descripcion) = upper(:descripcion )");
			
			q.setParameter("descripcion", readable);
			
			Perfil x = (Perfil) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object perfilId, List<String> illegalOrphanMessages) {		
		logger.debug("->validateBeforeDestroy: perfilId="+perfilId);		
		Query q = em.createQuery("select count(x) from Marca x where x.perfil.id = :perfilId");		
		q.setParameter("perfilId", perfilId);
		int countRelated = ((Long) q.getSingleResult()).intValue();

		if (countRelated > 0) {
			final String name = "Hay " + countRelated + " Marca(s), relacionados a esta Perfil";
			illegalOrphanMessages.add(name.toUpperCase());
		}
	}
	
	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

}
