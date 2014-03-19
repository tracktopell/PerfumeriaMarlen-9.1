/**
 * Archivo: backUpDatabaseOnLinuxService.java
 * 
 * Fecha de Creaci&oacute;n: 26/09/2011
 *
 * 2H Software - Bursatec 2011
 */
package com.pmarlen.tasks;

import com.pmarlen.model.beans.Linea;
import java.util.List;
import java.util.TimerTask;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Alfredo Estrada Gonz&aacute;lez.
 * @version 1.0
 *
 */

@Service("backUpDatabaseOnLinuxService")
public class BackUpDatabaseOnLinuxService {

	private EntityManagerFactory emf = null;
	
  	public BackUpDatabaseOnLinuxService(){
		logger = LoggerFactory.getLogger(BackUpDatabaseOnLinuxService.class.getSimpleName());        
        logger.debug("->backUpDatabaseOnLinuxService, created");		
	}
	
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

	private Logger logger;


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

	@Async
	//@Scheduled(cron = "0 0,8,16 * * * *")
	@Scheduled(cron = "*/2 * * * * *")
	public void run() {
		logger.debug("->> run: mysqldump");				
	}
}
