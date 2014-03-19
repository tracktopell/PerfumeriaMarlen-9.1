/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import com.pmarlen.model.Constants;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Web application lifecycle listener.
 * @author aestrada
 */
public class ContextAndSessionListener implements ServletContextListener, HttpSessionListener {
    
    Logger logger = LoggerFactory.getLogger(ContextAndSessionListener.class);
    private static SimpleDateFormat sdfDefault  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
    private static SimpleDateFormat sdfExtended = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzzzzz (Z)");
	public static final Hashtable<String , SessionInfo> sessionInfoHT = new Hashtable<String , SessionInfo>();    
	
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Date dateSystem = new Date();
		logger.info("=================================================================================>>");
		logger.info("-->>contextInitialized: serverVersion="+Constants.getServerVersion());
		TimeZone defaultTZ = TimeZone.getDefault();
		SessionInfo.setApplicationContextCreationTime(System.currentTimeMillis());
        logger.debug("-->>contextInitialized: TimeZone.getDefault()="+defaultTZ.getDisplayName()+", Time=defaultformat:"+sdfDefault.format(dateSystem)+", ExtendedFormat:"+sdfExtended.format(dateSystem));  
		
        //NavigationMB.prepareToParseXMLMenu();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("<<--contextDestroyed <<=================================================================================");		
    }

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
		final HttpSession session = hse.getSession();
		logger.info("-->>sessionCreated["+session.getId()+"] ==============================================>>");
		logger.debug("-->>sessionCreated["+session.getId()+"] CreationTime          :"+sdfDefault.format(session.getCreationTime()));
		logger.debug("-->>sessionCreated["+session.getId()+"] LastAccessedTime      :"+sdfDefault.format(session.getLastAccessedTime()));
		logger.debug("-->>sessionCreated["+session.getId()+"] MaxInactiveInterval   :"+session.getMaxInactiveInterval()+" secs.");
		logger.debug("-->>sessionCreated["+session.getId()+"] new                   ?"+session.isNew());
		logger.debug("-->>contextInitialized: =========================================================>>End of initialization");
	}

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
		final HttpSession session = hse.getSession();
        logger.info ("<<--["+session.getId()+"] sessionDestroyed");
		logger.debug("<<--["+session.getId()+"] was new ? "+session.isNew());
		logger.debug("<<--------------------------------------------------------------------------------");
		sessionInfoHT.remove(session.getId());
    }
}
