/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.tasks;

import com.pmarlen.model.controller.SessionInfoController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author alfredo
 */
@Service("updateSessionInfoTask")
public class UpdateSessionInfoTask {

	private Logger logger;
	
	private SessionInfoController sessionInfoController;
	
	public UpdateSessionInfoTask() {
		logger = LoggerFactory.getLogger(UpdateSessionInfoTask.class);
		logger.trace("->UpdateSessionInfoTask, created");
	}

	@Async
	@Scheduled(cron = "*/60 * * * * *")
	//@Scheduled(fixedRate=60000)	
	public void invokeUpdateSessionInfoTask() {
		
		logger.trace("===>>>invokeUpdateSessionInfoTask: sessionInfoController.killOldest()");
		sessionInfoController.killOldest();
	}

	/**
	 * @param sessionInfoController the sessionInfoController to set
	 */
	@Autowired
	public void setSessionInfoController(SessionInfoController sessionInfoController) {
		this.sessionInfoController = sessionInfoController;
	}

}
