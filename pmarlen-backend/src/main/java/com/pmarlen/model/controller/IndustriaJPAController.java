package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.Industria;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Multimedio;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * IndustriaJPAController
 */
@Repository("industriaJPAController")
public class IndustriaJPAController extends EntityJPAController<Industria> {

	public IndustriaJPAController() {
		super(LoggerFactory.getLogger(IndustriaJPAController.class));
	}
	
	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Industria newEntityInstance() {
		return new Industria();
	}

	@Override
	protected Class getEntityClass() {
		return Industria.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Industria";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(Industria entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Industria x) {
		final Collection<Marca> marcaCollection = x.getMarcaCollection();
		final Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
	}*/

	@Override
	public Industria findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Industria x where upper(x.nombre) = upper(:nombre )");
			
			q.setParameter("nombre", readable);
			
			Industria x = (Industria) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object industriaId, List<String> illegalOrphanMessages) {
		logger.debug("->validateBeforeDestroy: industriaId="+industriaId);		
		Query q = em.createQuery("select count(x) from Marca x where x.industria.id = :industriaId");		
		q.setParameter("industriaId", industriaId);
		int countRelated = ((Long) q.getSingleResult()).intValue();

		if (countRelated > 0) {
			final String name = "Hay " + countRelated + " Marca(s), relacionados a esta Industria";
			illegalOrphanMessages.add(name.toUpperCase());
		}
	}
	 
}
