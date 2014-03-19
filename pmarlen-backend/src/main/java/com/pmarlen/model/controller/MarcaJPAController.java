package com.pmarlen.model.controller;

import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Marca;
import org.springframework.stereotype.Repository;
import com.pmarlen.model.beans.Marca;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * MarcaJPAController
 */
@Repository("marcaJPAController")
public class MarcaJPAController extends EntityJPAController<Marca>{

	public MarcaJPAController() {
		super(LoggerFactory.getLogger(MarcaJPAController.class));
	}
	
	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Marca newEntityInstance() {
		Marca marcaNewInstance = new Marca();
		logger.debug("->newEntityInstance: marcaNewInstance="+marcaNewInstance);
		return marcaNewInstance;
	}

	@Override
	protected Class getEntityClass() {
		return Marca.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Marca";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;		
	}

	@Override
	protected Object getEntityId(Marca entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Marca x) {
		x.getMultimedioCollection();
		x.getProductoCollection();
		x.getIndustria();
		x.getLinea();
	}*/

	@Override
	public Marca findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Marca x where upper(x.nombre) = upper(:nombre )");
			
			q.setParameter("nombre", readable);
			
			Marca x = (Marca) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object marcaId, List<String> illegalOrphanMessages) {		
		logger.debug("->validateBeforeDestroy: marcaId="+marcaId);		
		Query q = em.createQuery("select count(x) from Producto x where x.marca.id = :marcaId");		
		q.setParameter("marcaId", marcaId);
		int countRelated = ((Long) q.getSingleResult()).intValue();

		if (countRelated > 0) {
			final String name = "Hay " + countRelated + " Producto(s), relacionados a esta Marca";
			illegalOrphanMessages.add(name.toUpperCase());
		}
	}
	
	protected String orderBy(String object){
		return "order by "+object+".linea.nombre, "+object+".industria.nombre";
	}


	/*
	private EntityManagerFactory emf = null;

	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Marca marca) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Marca persistentMarca = new Marca();

			persistentMarca.setNombre(marca.getNombre());
			
			persistentMarca.setMarca(marca.getMarca());
			persistentMarca.setIndustria(marca.getIndustria());

			em.persist(persistentMarca);

			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Marca marca) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Marca persistentMarca = em.find(Marca.class, marca.getId());

			persistentMarca.setNombre(marca.getNombre());
			persistentMarca.setIndustria(marca.getIndustria());
			persistentMarca.setMarca(marca.getMarca());

			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
		List<String> illegalOrphanMessages = new ArrayList<String>();

		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Marca marca;
			try {
				marca = em.getReference(Marca.class, id);
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
			}
			
			Query q = em.createQuery("select count(x) from Producto x where x.marca.id = :marcaId");
			q.setParameter("marcaId", id);
			
			int countProducto = ((Long) q.getSingleResult()).intValue();

			if (countProducto > 0) {
				illegalOrphanMessages.add("Hay " + countProducto + " Producto(s), relacionados a esta Marca");
			}

			if (illegalOrphanMessages.size() > 0) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}

			em.remove(marca);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Marca> findMarcaEntities() {
		return findMarcaEntities(true, -1, -1);
	}

	public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
		return findMarcaEntities(false, maxResults, firstResult);
	}

	private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select object(o) from Marca as o");
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			List<Marca> resultList = q.getResultList();
			for (Marca x : resultList) {
//                Collection<Marca> marcaCollection = x.getMarcaCollection();
//                for(Marca marca :marcaCollection) {
//                }
			}
			return resultList;
		} finally {
			em.close();
		}
	}

	public Marca findMarca(Integer id) {
		EntityManager em = getEntityManager();
		try {
			Marca x = em.find(Marca.class, id);
			if (x != null) {
				x.getMultimedioCollection();
				x.getProductoCollection();
			}
			return x;
		} finally {
			em.close();
		}
	}

	public int getMarcaCount() {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select count(o) from Marca as o");
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

	public Marca findMarcaByNombre(String nombre) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Marca x where upper(x.nombre) = upper(:nombre )");
			
			q.setParameter("nombre", nombre);
			
			Marca x = (Marca) q.getSingleResult();
			if (x != null) {
				x.getMultimedioCollection();
				x.getProductoCollection();
			}
			return x;
		} finally {
			em.close();
		}
	}

	public Marca findMarcaByMarcaAndIndustriaAndNombre(Marca marca,Industria industria,String nombre) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Marca x where x.marca.id=:marcaId and x.industria.id=:industriaId and upper(x.nombre) = upper(:nombre )");
			
			q.setParameter("nombre", nombre);
			q.setParameter("marcaId", marca.getId());
			q.setParameter("industriaId", industria.getId());
			
			Marca x = (Marca) q.getSingleResult();
			if (x != null) {
				x.getMultimedioCollection();
				x.getProductoCollection();
			}
			return x;
		} finally {
			em.close();
		}
	}

    public List<Marca> findMarcaEntitiesByIndustria(Industria industria) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Marca as o where o.industria.id = :industriaId");

            q.setParameter("industriaId", industria.getId());

            List<Marca> resultList = q.getResultList();
//            for(Marca x: resultList) {
//                Collection<Producto> productoCollection = x.getProductoCollection();
//                for(Producto producto: productoCollection){
//                }
//            }

            return resultList;
        } finally {
            em.close();
        }
    }

    public List<Marca> findMarcaEntitiesByMarca(Marca marca) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select DISTINCT x from Marca x,Producto p where p.marca.id = x.id and p.marca.id = :marcaId");

            q.setParameter("marcaId", marca.getId());

            List<Marca> resultList = q.getResultList();
//            for(Marca x: resultList) {
//                Collection<Producto> productoCollection = x.getProductoCollection();
//                for(Producto producto: productoCollection){
//                }
//            }

            return resultList;
        } finally {
            em.close();
        }
    }
	*/
}
