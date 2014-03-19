package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.Poblacion;
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
 * PoblacionJPAController
 */
@Repository("poblacionJPAController")
public class PoblacionJPAController extends EntityJPAController<Poblacion>{

	public PoblacionJPAController() {
		super(LoggerFactory.getLogger(PoblacionJPAController.class));
	}
	
	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Poblacion newEntityInstance() {
		Poblacion poblacionNewInstance = new Poblacion();
		logger.debug("->newEntityInstance: poblacionNewInstance="+poblacionNewInstance);
		return poblacionNewInstance;
	}

	@Override
	protected Class getEntityClass() {
		return Poblacion.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Poblacion";
	}

	@Override
	protected Object getEntityId(Poblacion entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Poblacion x) {				
	}*/

	@Override
	public Poblacion findEntityByReadableProperty(String nombre) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Poblacion x where upper(x.nombre) like %:nombre%");
			
			q.setParameter("nombre", "%"+nombre.toUpperCase()+"%");
			
			Poblacion x = (Poblacion) q.getSingleResult();
			if (x != null) {
				//retrieveLazyAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}
	
	public List<String> findAllEntidadFederativa() {
		List<String> entidadFederativaList=null;
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select distinct(x.entidadFederativa) from Poblacion x order by x.entidadFederativa");
			
			entidadFederativaList = q.getResultList();
			
			return entidadFederativaList;
		} finally {
			em.close();
		}
	}	

	public List<String> findAllMunicipioODelegacion(String entidadFederativa) {
		List<String> municipioODelegacionList=null;
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select distinct(x.municipioODelegacion) from Poblacion x where x.entidadFederativa = :entidadFederativa order by x.municipioODelegacion");
			q.setParameter("entidadFederativa", entidadFederativa);
			
			municipioODelegacionList = q.getResultList();
			
			return municipioODelegacionList;
		} finally {
			em.close();
		}
	}
	
	public List<String> findAllCodigoPostal(String municipioODelegacion) {
		List<String> codigoPostalList=null;
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Poblacion x where x.municipioODelegacion = :municipioODelegacion order by x.codigoPostal");
			q.setParameter("municipioODelegacion", municipioODelegacion);
			
			codigoPostalList = q.getResultList();
			
			return codigoPostalList;
		} finally {
			em.close();
		}
	}

	public List<Poblacion> findAllPoblacionByMunicipioODelegacion(String municipioODelegacion) {
		List<Poblacion> poblacionList=null;
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Poblacion x where x.municipioODelegacion = :municipioODelegacion order by x.nombre,x.codigoPostal");
			q.setParameter("municipioODelegacion", municipioODelegacion);
			
			poblacionList = q.getResultList();
			
			return poblacionList;
		} finally {
			em.close();
		}
	}
	
	public List<Poblacion> findAllPoblacionByCodigoPostal(String codigoPostal) {
		List<Poblacion> poblacionList=null;
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Poblacion x where x.codigoPostal = :codigoPostal order by x.codigoPostal,x.nombre ");
			q.setParameter("codigoPostal", codigoPostal);
			
			poblacionList = q.getResultList();
			
			return poblacionList;
		} finally {
			em.close();
		}
	}
	
	@Override
	protected void validateBeforeDestroy(EntityManager em, Object poblacionId, List<String> illegalOrphanMessages) {		
		Query q = null;
		int countRelated = -1;
		
		String relatedEntities[] = {
				"Sucursal",
				"Contacto",
				"Cliente",
				"Proveedor"};
		for(String re:relatedEntities){
			q = em.createQuery("select count(x) from "+re+" x where x.poblacion.id = :poblacionId");
			q.setParameter("id", poblacionId);
			countRelated = ((Long) q.getSingleResult()).intValue();

			if (countRelated > 0) {
				final String name = "Hay " + countRelated + " "+re+"(s), relacionados a este Poblacion";
				illegalOrphanMessages.add(name.toUpperCase());
			}
		}
	}
	
	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

}
