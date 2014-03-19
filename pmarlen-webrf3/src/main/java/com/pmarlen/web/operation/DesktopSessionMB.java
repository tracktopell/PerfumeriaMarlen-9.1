package com.pmarlen.web.operation;

import com.pmarlen.model.controller.SessionInfoController;
import com.pmarlen.security.OnlineSessionInfo;
import com.pmarlen.web.servlet.ContextAndSessionListener;
import com.pmarlen.web.servlet.SessionInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("serial")
public class DesktopSessionMB implements Serializable {

	private final Logger logger = LoggerFactory.getLogger(DesktopSessionMB.class);

	private SessionInfoController sessionInfoController;
			
	public DesktopSessionMB() {
		logger.debug("=============>> DesktopSessionMB created.");
		
	}

	public List<OnlineSessionInfo> getList(){
		return sessionInfoController.getViewOnlineSessionInfo();
	}
	
	public List<SessionInfo> getSessionInfoList(){
		final Enumeration<SessionInfo> elements = ContextAndSessionListener.sessionInfoHT.elements();
		List<SessionInfo> result = new ArrayList<SessionInfo>();
		
		while(elements.hasMoreElements()){
			result.add(elements.nextElement());
		}
		
		return result;
	}

	/**
	 * @param sessionInfoController the sessionInfoController to set
	 */
	@Autowired
	public void setSessionInfoController(SessionInfoController sessionInfoController) {
		this.sessionInfoController = sessionInfoController;
	}


}
