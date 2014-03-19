/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.model.controller;

import com.pmarlen.model.beans.EventoSincronizacion;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;

/**
 *
 * @author alfredo
 */
public abstract class EntityJPAController<T> implements Serializable {

	protected Logger logger;
	protected EntityManagerFactory emf = null;

	public EntityJPAController(Logger logger) {
		this.logger = logger;
	}

	public abstract void setEntityManagerFactory(EntityManagerFactory emf);

	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public abstract T newEntityInstance();

	protected abstract Class getEntityClass();

	protected abstract String getEntityClassName();

	protected abstract boolean isAffectedBySincronizacionEvent();

	protected abstract Object getEntityId(T entity);
	
	protected boolean retrieveEagerExtensive = false;

	public void create(T entity) {
		EntityManager em = null;
		final Class entityClass = getEntityClass();
		final String entityClassName = getEntityClassName();
		Hashtable<String, Object> propertiesToCopy = new Hashtable<String, Object>();
		Hashtable<String, Object> propertiesM2MToCopy = new Hashtable<String, Object>();
		List<String> propertiesWithNULLValueToCopy = new ArrayList<String>();
		List<String> propertiesKey = new ArrayList<String>();

		try {
			em = getEntityManager();
			em.getTransaction().begin();

			T entityToCreate = newEntityInstance();
			logger.trace("->create:entity=" + entity);
			logger.trace("->create:entityToCreate is null ?" + (entityToCreate == null));
			logger.trace("->create: before persist, entityToCreate is null ?" + (entityToCreate == null));

			logger.trace("->create: copyng the properties:");


			final Field[] declaredFields = entityClass.getDeclaredFields();
			for (Field f : declaredFields) {
				//logger.trace("->create: \tcopy: " + entityClassName + "." + f.getName() + " accesible ? " + (f.isAccessible()));
				if (!f.isAccessible()) {

					if (f.isAnnotationPresent(javax.persistence.Id.class)) {
						propertiesKey.add(f.getName());
					}
					if (f.isAnnotationPresent(javax.persistence.Id.class)
							&& !f.isAnnotationPresent(javax.persistence.GeneratedValue.class)
							&& !Modifier.isStatic(f.getModifiers())) {

						Object valueCopyed = PropertyUtils.getProperty(entity, f.getName());
						if (valueCopyed != null) {
							propertiesToCopy.put(f.getName(), valueCopyed);
						} else {
							propertiesWithNULLValueToCopy.add(f.getName());
						}
						logger.trace("->create:\t\t ID elegible 2 be copied, added to copy list: " + f.getName() + " = " + valueCopyed + ", is really null?" + (valueCopyed == null));
					} else if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& !f.isAnnotationPresent(javax.persistence.OneToMany.class)
							&& !f.isAnnotationPresent(javax.persistence.ManyToMany.class)
							&& !Modifier.isStatic(f.getModifiers())) {

						Object valueCopyed = PropertyUtils.getProperty(entity, f.getName());
						if (valueCopyed != null) {
							propertiesToCopy.put(f.getName(), valueCopyed);
						} else {
							propertiesWithNULLValueToCopy.add(f.getName());
						}
						logger.trace("->create:\t\t elegible 2 be copied, added to copy list: " + f.getName() + " = " + valueCopyed + ", is really null?" + (valueCopyed == null));
					} else if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& f.isAnnotationPresent(javax.persistence.ManyToMany.class)) {

						Object valueCopyed = PropertyUtils.getProperty(entity, f.getName());
						if (valueCopyed != null) {
							propertiesM2MToCopy.put(f.getName(), valueCopyed);
						} else {
							propertiesWithNULLValueToCopy.add(f.getName());
						}
						logger.trace("->create:\t\t M2M elegible 2 be copied, added to copy list: " + f.getName() + " = " + valueCopyed + ", is really null?" + (valueCopyed == null));
					}
				}
			}
			logger.trace("->create:copy values ?");
			for (String p2c : propertiesToCopy.keySet()) {
				Object valueCopyed = propertiesToCopy.get(p2c);
				logger.trace("->create:\t\t copy value with SpringUtils: " + p2c + " = " + valueCopyed + ", is null?" + (valueCopyed == null));
				BeanUtils.copyProperty(entityToCreate, p2c, valueCopyed);
			}
			for (String p2c : propertiesWithNULLValueToCopy) {
				logger.trace("->create:\t\t copy null with SpringUtils");
				BeanUtils.copyProperty(entityToCreate, p2c, null);
			}

			em.persist(entityToCreate);
			em.flush();
			if(propertiesM2MToCopy.size()>0){
				for (String pM2M2c : propertiesM2MToCopy.keySet()) {
					Object collectionValueCopyed = propertiesM2MToCopy.get(pM2M2c);
					List listForNewRelatedObjects = new ArrayList();
					if (collectionValueCopyed != null && collectionValueCopyed instanceof Collection) {
						Collection valueCollection = (Collection) collectionValueCopyed;
						final Iterator iterator = valueCollection.iterator();
						while (iterator.hasNext()) {
							Object objRel = iterator.next();
							logger.trace("->create:\t\t\t merging M2M related property: " + objRel);
							//em.merge(objRel);
							listForNewRelatedObjects.add(objRel);
						}
					}
					logger.trace("->create:\t\tafter, merge each property, copy M2M value with SpringUtils: " + pM2M2c + " = " + collectionValueCopyed + ", is null?" + (collectionValueCopyed == null));
					BeanUtils.copyProperty(entityToCreate, pM2M2c, listForNewRelatedObjects);
				}
				em.merge(entity);
				logger.trace("->create:merged entity");
				em.flush();
				logger.trace("->create:flush");

			}


			if (isAffectedBySincronizacionEvent()) {
				EventoSincronizacion es = new EventoSincronizacion();
				es.setTipoEvento(1);
				es.setAfectacionGlobal(1);
				es.setFechaEvento(new Date());
				es.setSucursalIdAfectada(0);
				es.setTabla(entityClassName);
				es.setCamposLlave(propertiesKey.toString());
				es.setValoresLlave(String.valueOf(getEntityId(entityToCreate)));

				em.persist(es);
			}
			em.flush();

			em.getTransaction().commit();
		} catch (NoSuchMethodException nsme) {
			logger.error("->create:", nsme);
			em.getTransaction().rollback();
			throw new IllegalStateException(nsme.getMessage());
		} catch (IllegalAccessException iae) {
			logger.error("->create:", iae);
			em.getTransaction().rollback();
			throw new IllegalStateException(iae.getMessage());
		} catch (InvocationTargetException ite) {
			logger.error("->create:", ite);
			em.getTransaction().rollback();
			throw new IllegalStateException(ite.getMessage());
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(T entity) throws NonexistentEntityException {
		EntityManager em = null;
		final Class entityClass = getEntityClass();
		final String entityClassName = getEntityClassName();
		Hashtable<String, Object> propertiesToCopy = new Hashtable<String, Object>();
		Hashtable<String, Object> propertiesM2MToCopy = new Hashtable<String, Object>();
		List<String> propertiesWithNULLValueToCopy = new ArrayList<String>();
		List<String> propertiesKey = new ArrayList<String>();

		try {
			em = getEntityManager();
			em.getTransaction().begin();

			T entityToPersist = null;
			Object entityId = null;
			try {
				entityId = getEntityId(entity);
				entityToPersist = (T) em.getReference(getEntityClass(), entityId);
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The " + getEntityClassName() + " with id=" + entityId + " no longer exists.", enfe);
			}

			logger.trace("->edit:entity=" + entity);
			logger.trace("->edit:entityToPersist is null ?" + (entityToPersist == null));

			logger.trace("->edit: copyng the properties:");

			final Field[] declaredFields = entityClass.getDeclaredFields();
			for (Field f : declaredFields) {
				//logger.trace("->create: \tcopy: " + entityClass + "." + f + " accesible ? " + (f.isAccessible()));
				if (!f.isAccessible()) {

					if (f.isAnnotationPresent(javax.persistence.Id.class)) {
						propertiesKey.add(f.getName());
					}
					if (f.isAnnotationPresent(javax.persistence.Id.class)
							&& !f.isAnnotationPresent(javax.persistence.GeneratedValue.class)
							&& !Modifier.isStatic(f.getModifiers())) {

						Object valueCopyed = PropertyUtils.getProperty(entity, f.getName());
						if (valueCopyed != null) {
							//propertiesToCopy.put(f.getName(),valueCopyed);
						} else {
							//propertiesWithNULLValueToCopy.add(f.getName());
						}
						logger.trace("->edit:\t\t ID elegible 2 be NOT copied, added to copy list: " + f.getName() + " = " + valueCopyed + ", is really null?" + (valueCopyed == null));
					} else if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& !f.isAnnotationPresent(javax.persistence.OneToMany.class)
							&& !f.isAnnotationPresent(javax.persistence.ManyToMany.class)
							&& !Modifier.isStatic(f.getModifiers())) {

						Object valueCopyed = PropertyUtils.getProperty(entity, f.getName());
						if (valueCopyed != null) {
							propertiesToCopy.put(f.getName(), valueCopyed);
						} else {
							propertiesWithNULLValueToCopy.add(f.getName());
						}
						logger.trace("->edit:\t\t elegible 2 be copied, added to copy list: " + f.getName() + " = " + valueCopyed + ", is really null?" + (valueCopyed == null));
					} else if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& f.isAnnotationPresent(javax.persistence.ManyToMany.class)) {

						Object valueCopyed = PropertyUtils.getProperty(entity, f.getName());
						if (valueCopyed != null) {
							propertiesM2MToCopy.put(f.getName(), valueCopyed);
						} else {
							propertiesWithNULLValueToCopy.add(f.getName());
						}
						logger.trace("->edit:\t\t M2M elegible 2 be copied, added to copy list: " + f.getName() + ", is really null?" + (valueCopyed == null));
					}
				}
			}
			logger.trace("->edit:copy values ?");
			for (String p2c : propertiesToCopy.keySet()) {
				Object valueCopyed = propertiesToCopy.get(p2c);
				logger.trace("->edit:\tcopy value with SpringUtils: " + p2c + " = " + valueCopyed + ", is null?" + (valueCopyed == null));
				BeanUtils.copyProperty(entityToPersist, p2c, valueCopyed);
			}
			for (String p2c : propertiesWithNULLValueToCopy) {
				logger.trace("->edit:\tcopy null with SpringUtils");
				BeanUtils.copyProperty(entityToPersist, p2c, null);
			}

			em.merge(entityToPersist);
			em.flush();

			if (propertiesM2MToCopy.size() > 0) {
				for (String pM2M2c : propertiesM2MToCopy.keySet()) {
					Object collectionValueCopyed = propertiesM2MToCopy.get(pM2M2c);
					List listForNewRelatedObjects = new ArrayList();
					if (collectionValueCopyed != null && collectionValueCopyed instanceof Collection) {
						Collection valueCollection = (Collection) collectionValueCopyed;
						final Iterator iterator = valueCollection.iterator();
						while (iterator.hasNext()) {
							Object objRel = iterator.next();
							logger.trace("->edit:\t\t\t merging M2M related property: " + objRel);
							//em.merge(objRel);
							listForNewRelatedObjects.add(objRel);
						}
					}
					logger.trace("->edit:\t\tafter, merge each property, copy M2M value with SpringUtils: " + pM2M2c + " = " + collectionValueCopyed + ", is null?" + (collectionValueCopyed == null));
					BeanUtils.copyProperty(entityToPersist, pM2M2c, listForNewRelatedObjects);
				}
				em.merge(entityToPersist);
				logger.trace("->edit:merged entity");				
				em.flush();
				logger.trace("->edit:merged entity");				
			}

			if (isAffectedBySincronizacionEvent()) {

				EventoSincronizacion es = new EventoSincronizacion();

				es.setTipoEvento(2);
				es.setAfectacionGlobal(1);
				es.setFechaEvento(new Date());
				es.setSucursalIdAfectada(0);
				es.setTabla(entityClassName);
				es.setCamposLlave(propertiesKey.toString());
				es.setValoresLlave(String.valueOf(getEntityId(entityToPersist)));

				em.persist(es);
			}
			em.getTransaction().commit();
		} catch (NoSuchMethodException nsme) {
			logger.error("->edit:", nsme);
			em.getTransaction().rollback();
			throw new IllegalStateException(nsme.getMessage());
		} catch (IllegalAccessException iae) {
			logger.error("->edit:", iae);
			em.getTransaction().rollback();
			throw new IllegalStateException(iae.getMessage());
		} catch (InvocationTargetException ite) {
			logger.error("->edit:", ite);
			em.getTransaction().rollback();
			throw new IllegalStateException(ite.getMessage());
		} catch (NonexistentEntityException nee) {
			em.getTransaction().rollback();
			throw nee;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Object id) throws IllegalOrphanException, NonexistentEntityException {
		List<String> illegalOrphanMessages = new ArrayList<String>();
		final Class entityClass = getEntityClass();
		final String entityClassName = getEntityClassName();
		List<String> propertiesToCopy = new ArrayList<String>();
		List<String> propertiesM2MToCopy = new ArrayList<String>();
		List<String> propertiesKey = new ArrayList<String>();

		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			T entityToDelete;
			try {
				entityToDelete = (T) em.getReference(getEntityClass(), id);
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The " + getEntityClassName() + " with id " + id + " no longer exists.", enfe);
			}

			validateBeforeDestroy(em, id, illegalOrphanMessages);
			logger.trace("->destroy: after validateBeforeDestroy, illegalOrphanMessages=" + illegalOrphanMessages);

			if (illegalOrphanMessages.size() > 0) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}

			logger.trace("->destroy:entityToPersist is null ?" + (entityToDelete == null));

			final Field[] declaredFields = entityClass.getDeclaredFields();
			for (Field f : declaredFields) {
				if (!f.isAccessible()) {

					if (f.isAnnotationPresent(javax.persistence.Id.class)) {
						propertiesKey.add(f.getName());
					} else if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& f.isAnnotationPresent(javax.persistence.ManyToMany.class)) {

						BeanUtils.copyProperty(entityToDelete, f.getName(), new ArrayList());
						logger.trace("->destroy:\t\t M2M elegible 2 remove relation from entity :" + f.getName());
					}
				}
			}

			em.merge(entityToDelete);
			em.flush();

			em.remove(entityToDelete);

			if (isAffectedBySincronizacionEvent()) {

				EventoSincronizacion es = new EventoSincronizacion();

				es.setTipoEvento(3);
				es.setAfectacionGlobal(1);
				es.setFechaEvento(new Date());
				es.setSucursalIdAfectada(0);
				es.setTabla(entityClassName);
				es.setCamposLlave(propertiesKey.toString());
				es.setValoresLlave(String.valueOf(getEntityId(entityToDelete)));

				em.persist(es);
			}
			em.getTransaction().commit();
		} catch (IllegalAccessException iae) {
			logger.error("->destroy:", iae);
			em.getTransaction().rollback();
			throw new IllegalStateException(iae.getMessage());
		} catch (InvocationTargetException ite) {
			logger.error("->destroy:", ite);
			em.getTransaction().rollback();
			throw new IllegalStateException(ite.getMessage());
		} catch (IllegalOrphanException ioe) {
			em.getTransaction().rollback();
			throw ioe;
		} catch (NonexistentEntityException nee) {
			em.getTransaction().rollback();

			throw nee;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<T> findAllEntities() {
		return findEntities(true, -1, -1);
	}

	public List<T> findEntities(int maxResults, int firstResult) {
		return findEntities(false, maxResults, firstResult);
	}

	public List<T> findEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from " + getEntityClassName() + " x "+orderBy("x"));
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			List<T> resultList = (List<T>) q.getResultList();
			for (T x : resultList) {
				if(retrieveEagerExtensive){
					retrieveEagerAllAtributes(x);
				} else {
					retrieveEagerAtributes(x);
				}
				
			}
			return resultList;
		} finally {
			em.close();
		}
	}
	
	protected String orderBy(String object){
		return "";
	}

	protected void retrieveEagerAtributes(T x){
		final Class entityClass = getEntityClass();
		final Field[] declaredFields = entityClass.getDeclaredFields();
		logger.trace("->retrieveEagerAtributes: entityClass="+entityClass.getSimpleName());
		try{
			for (Field f : declaredFields) {
				if (!f.isAccessible()) {
					if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& (
							f.isAnnotationPresent(javax.persistence.OneToMany.class) || 
							f.isAnnotationPresent(javax.persistence.ManyToMany.class)
							)
							&&!Modifier.isStatic(f.getModifiers())) {

						logger.trace("->retrieveEagerAtributes:\tCollection shuld not used for retrieve: " + f.getName() );						
					} else if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& f.isAnnotationPresent(javax.persistence.ManyToOne.class) 						
							&&!Modifier.isStatic(f.getModifiers())) {

						logger.trace("->retrieveEagerAtributes:\tBean to retrieve: " + f.getName() );
						Object childObjectCopyed = PropertyUtils.getProperty(x, f.getName());
						logger.trace("->retrieveEagerAtributes:\t\tobjectCopyed: " + childObjectCopyed);

					}
				}
			}
		}catch(Exception e){
			throw new IllegalStateException(e.getMessage());
		}
	}

	protected void retrieveEagerAllAtributes(T x) {
		final Class entityClass = getEntityClass();
		final Field[] declaredFields = entityClass.getDeclaredFields();
		logger.trace("->retrieveEagerAllAtributes: entityClass="+entityClass.getSimpleName());
		try{
			for (Field f : declaredFields) {
				//logger.trace("->retrieveEagerAllAtributes: \tretrieve: " + entityClass.getSimpleName() + "." + f.getName() + " accesible ? " + (f.isAccessible())+" @"+Arrays.asList(f.getAnnotations()));
				if (!f.isAccessible()) {
					if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& (
							f.isAnnotationPresent(javax.persistence.OneToMany.class) || 
							f.isAnnotationPresent(javax.persistence.ManyToMany.class)
							)
							&&!Modifier.isStatic(f.getModifiers())) {

						logger.trace("->retrieveEagerAllAtributes:\tCollection to retrieve: " + f.getName() );
						Collection childCollectionCopyed = (Collection)PropertyUtils.getProperty(x, f.getName());
						Iterator   iteratorForCollection = childCollectionCopyed.iterator();
						while(iteratorForCollection.hasNext()) {
							Object childObject = iteratorForCollection.next();
							logger.trace("->retrieveEagerAllAtributes:\t\tchildObject: " + childObject);
						}
					} else if (!f.isAnnotationPresent(javax.persistence.Id.class)
							&& f.isAnnotationPresent(javax.persistence.ManyToOne.class) 						
							&&!Modifier.isStatic(f.getModifiers())) {

						logger.trace("->retrieveEagerAllAtributes:\tBean to retrieve: " + f.getName() );
						Object childObjectCopyed = PropertyUtils.getProperty(x, f.getName());
						logger.trace("->retrieveEagerAllAtributes:\t\tobjectCopyed: " + childObjectCopyed);
					}
				}
			}
		}catch(Exception e){
			throw new IllegalStateException(e.getMessage());
		}
	}

	public T findById(Object id) {
		EntityManager em = getEntityManager();
		try {
			T x = (T) em.find(getEntityClass(), id);
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	public int getEntityCount() {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select count(o) from " + getEntityClassName() + " as o");
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

	public abstract T findEntityByReadableProperty(String readable);

	protected abstract void validateBeforeDestroy(EntityManager em, Object id, List<String> illegalOrphanMessages);
}
