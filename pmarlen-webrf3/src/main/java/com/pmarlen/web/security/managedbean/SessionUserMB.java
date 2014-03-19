package com.pmarlen.web.security.managedbean;

import java.util.*;
import java.text.*;
import java.io.*;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.controller.UsuarioJPAController;
import com.pmarlen.model.Constants;
import com.pmarlen.web.servlet.ContextAndSessionListener;
import com.pmarlen.web.servlet.SessionInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUserMB {

	private final Logger logger = LoggerFactory.getLogger(SessionUserMB.class);
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
	private UsuarioJPAController usuarioJPAController;
	private Usuario usuarioAuthenticated;

	public void setUsuarioJPAController(UsuarioJPAController usuarioJPAController) {
		this.usuarioJPAController = usuarioJPAController;
	}

	public Usuario getUsuarioAuthenticated() {
		if (usuarioAuthenticated == null) {
			FacesContext fCtx = FacesContext.getCurrentInstance();
			ExternalContext externalContext = fCtx.getExternalContext();
			HttpSession session = (HttpSession) externalContext.getSession(false);
			String sessionId = session.getId();
			
			String remoteUser      = externalContext.getRemoteUser();
			String userPrincipal   = externalContext.getUserPrincipal().getName();
			
			logger.info("==>> getUsuarioAuthenticated : Session[" + sessionId + "] first enter: remoteUser=" + remoteUser+", userPrincipal=" + userPrincipal);
				
			if (remoteUser != null) {
				
				Date creationTime = new Date(session.getCreationTime());

				logger.debug("==>> UsuarioAuthenticated : Session[" + sessionId + "] created     :" + sdf.format(creationTime));
				logger.debug("==>> UsuarioAuthenticated : Session[" + sessionId + "] new         ?" + session.isNew());
				usuarioAuthenticated = this.usuarioJPAController.findById(remoteUser);
				if (usuarioAuthenticated != null && !session.isNew()) {
					logger.debug("\t==>> usuarioAuthenticated : Session[" + sessionId + "] True enter :" + usuarioAuthenticated);
					SessionInfo si = ContextAndSessionListener.sessionInfoHT.get(sessionId);
					if (si != null) {
						si.setUserName(remoteUser);
					} else {
						si = new SessionInfo(session, usuarioAuthenticated.getNombreCompleto());
						ContextAndSessionListener.sessionInfoHT.put(sessionId, si);
					}
				}
			}
		}
		return usuarioAuthenticated;
	}

	public String getSessionTimeOutMinus3SecsToDisplay() {
		HttpServletRequest r = null;

		r = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		r.getSession().getMaxInactiveInterval();

		return String.valueOf(r.getSession().getMaxInactiveInterval() - 3);
	}

	public void updateLastVisitedPage(String page) {
		FacesContext fCtx = FacesContext.getCurrentInstance();
		ExternalContext externalContext = fCtx.getExternalContext();
		HttpSession session = (HttpSession) externalContext.getSession(false);
		HttpServletRequest request = (HttpServletRequest) (externalContext.getRequest());
		String sessionId = session.getId();
		String userAgent = request.getHeader("User-Agent");
		String remoteAdrrInfo = request.getRemoteAddr() + "(" + request.getRemoteHost() + ")";
		logger.debug("==>> updateLastVisitedPage : page"+page+" from : "+ remoteAdrrInfo +" using:"+userAgent);
		SessionInfo si = ContextAndSessionListener.sessionInfoHT.get(sessionId);
		if(si != null) {
			si.setLastVisitedPage(page);
			si.setRemoteAddr(remoteAdrrInfo);
			if(userAgent != null){
				si.setUserAgent(userAgent);
			}
		}
	}

	public boolean isRootUser() {
		if (usuarioAuthenticated != null) {
			Perfil perfilFinalUser = new Perfil();
			Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
			perfilFinalUser.setId(Constants.PERFIL_ROOT);
			return perfilCollection.contains(perfilFinalUser);
		} else {
			return false;
		}
	}

	public boolean isAdminUser() {
		if (usuarioAuthenticated != null) {
			Perfil perfilFinalUser = new Perfil();
			Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
			perfilFinalUser.setId(Constants.PERFIL_ADMIN);
			return isRootUser() || perfilCollection.contains(perfilFinalUser);
		} else {
			return false;
		}
	}

	public boolean isSalesUser() {
		if (usuarioAuthenticated != null) {

			Perfil perfilFinalUser = new Perfil();
			Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
			perfilFinalUser.setId(Constants.PERFIL_SALES);
			return isRootUser() || perfilCollection.contains(perfilFinalUser);
		} else {
			return false;
		}
	}

	public boolean isStockUser() {
		if (usuarioAuthenticated != null) {
			Perfil perfilFinalUser = new Perfil();
			Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
			perfilFinalUser.setId(Constants.PERFIL_STOCK);
			return isRootUser() || perfilCollection.contains(perfilFinalUser);
		} else {
			return false;
		}
	}
}
