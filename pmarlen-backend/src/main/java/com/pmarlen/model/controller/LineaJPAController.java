package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.Linea;
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
 * LineaJPAController
 */
@Repository("lineaJPAController")
public class LineaJPAController extends EntityJPAController<Linea>{

	public LineaJPAController() {
		super(LoggerFactory.getLogger(LineaJPAController.class));
	}
	
	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Linea newEntityInstance() {
		Linea lineaNewInstance = new Linea();
		logger.debug("->newEntityInstance: lineaNewInstance="+lineaNewInstance);
		return lineaNewInstance;
	}

	@Override
	protected Class getEntityClass() {
		return Linea.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Linea";
	}

	@Override
	protected Object getEntityId(Linea entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Linea x) {		
		final Collection<Marca> marcaCollection = x.getMarcaCollection();		
		final Collection<Multimedio> multimedioCollection = x.getMultimedioCollection();
		for(Multimedio m: multimedioCollection){
			m.getLineaCollection();
		}
	}
	*/
	@Override
	public Linea findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Linea x where upper(x.nombre) = upper(:nombre )");
			
			q.setParameter("nombre", readable);
			
			Linea x = (Linea) q.getSingleResult();
			//if (x != null) {
			//	retrieveEagerAllAtributes(x);
			//}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object lineaId, List<String> illegalOrphanMessages) {		
		logger.debug("->validateBeforeDestroy: lineaId="+lineaId);		
		Query q = em.createQuery("select count(x) from Marca x where x.linea.id = :lineaId");		
		q.setParameter("lineaId", lineaId);
		int countRelated = ((Long) q.getSingleResult()).intValue();

		if (countRelated > 0) {
			final String name = "Hay " + countRelated + " Marca(s), relacionados a esta Linea";
			illegalOrphanMessages.add(name.toUpperCase());
		}
	}
	
	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

}
