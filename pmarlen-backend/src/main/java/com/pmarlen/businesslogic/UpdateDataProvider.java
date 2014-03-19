/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.businesslogic;

import com.pmarlen.model.beans.EventoSincronizacion;
import com.pmarlen.wscommons.services.UpdateDataFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfredo
 */
@Repository("updateDataProvider")
public class UpdateDataProvider {
	private Logger logger;

	private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

	public UpdateDataProvider() {
		logger = LoggerFactory.getLogger(UpdateDataProvider.class);        
        logger.debug("->UpdateDataProvider, created");
	}
	
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
	
	public boolean existUpdateDataFacadeForLastEventsSince(Date fechaEventoInicio){
		logger.debug("->createUpdateDataFacadeForLastEventsSince");
        EntityManager em = null;
		UpdateDataFacade udf = null;
		boolean existUpdateDataFacade = false;
		try {
            em = getEntityManager();
            
			udf = new UpdateDataFacade();
			EventoSincronizacion x = new EventoSincronizacion();
			
			Query q = em.createQuery("select count(x) from EventoSincronizacion x where x.fechaEvento >= :fechaEventoInicio and (x.sucursalIdAfectada = :sucursalIdAfectada or x.afectacionGlobal = 1)");
			
			q.setParameter("fechaEventoInicio", fechaEventoInicio);
			
			if(((Number)q.getSingleResult()).intValue() > 0){
				existUpdateDataFacade = true;
			}
			
		} catch(Exception e){
			logger.error(" in iteration:",e);
		} finally {
            if (em != null) {
                em.close();
            }
        }
		return existUpdateDataFacade;
		
	}
	public UpdateDataFacade createUpdateDataFacadeForLastEventsSince(Date fechaEventoInicio){
		logger.debug("->createUpdateDataFacadeForLastEventsSince");
        EntityManager em = null;
		UpdateDataFacade udf = null;
		try {
            em = getEntityManager();
            //em.getTransaction().begin();
			
			udf = new UpdateDataFacade();			
			Query q = em.createQuery("select x from EventoSincronizacion x where x.fechaEvento >= :fechaEventoInicio and"
					+ " (x.sucursalIdAfectada = :sucursalIdAfectada or x.afectacionGlobal = 1) order by tipoEvento,fechaEvento");
			
			q.setParameter("fechaEventoInicio", fechaEventoInicio);
			List<EventoSincronizacion> resultList = q.getResultList();
			
			udf.setLastEvents(resultList);
			
			for(EventoSincronizacion es: resultList){
				if(es.getTipoEvento() == 1){
					String ris = "select x from "+es.getTabla()+" x where x."+es.getCamposLlave()+" = "+es.getValoresLlave();
					Query qri = em.createQuery(ris);
					
					Object insertObject = qri.getSingleResult();
					logger.debug("\t =>"+ris+" ==>> "+insertObject);
					
					udf.getDataInsertList().add(insertObject);					
				} else if(es.getTipoEvento() == 2){
					String rus = "select x from "+es.getTabla()+" x where x."+es.getCamposLlave()+" = "+es.getValoresLlave();
					
					Query qru = em.createQuery(rus);
					
					Object updateObject = qru.getSingleResult();
					logger.debug("\t =>"+rus+" ==>> "+updateObject);
					
					udf.getDataUpdateList().add(updateObject);
				} else if(es.getTipoEvento() == 3){
					break;
				}
			}
			
			//em.getTransaction().commit();
        } catch(Exception e){
			logger.error(" in iteration:",e);
		} finally {
            if (em != null) {
                em.close();
            }
        }
		
		return udf;
	}
	
}
