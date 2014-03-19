/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.wscommons.services;

import com.pmarlen.model.beans.EventoSincronizacion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alfredo
 */
public class UpdateDataFacade implements Serializable{
	private List dataInsertList;
	private List dataUpdateList;
	
	private List<EventoSincronizacion> lastEvents;
	
	public UpdateDataFacade() {
		this.dataInsertList = new ArrayList();
		this.dataUpdateList = new ArrayList();
	}
	

	/**
	 * @return the dataInsertList
	 */
	public List getDataInsertList() {
		return dataInsertList;
	}

	/**
	 * @param dataInsertList the dataInsertList to set
	 */
	public void setDataInsertList(List dataInsertList) {
		this.dataInsertList = dataInsertList;
	}

	/**
	 * @return the dataUpdateList
	 */
	public List getDataUpdateList() {
		return dataUpdateList;
	}

	/**
	 * @param dataUpdateList the dataUpdateList to set
	 */
	public void setDataUpdateList(List dataUpdateList) {
		this.dataUpdateList = dataUpdateList;
	}

	/**
	 * @return the lastEvents
	 */
	public List<EventoSincronizacion> getLastEvents() {
		return lastEvents;
	}

	/**
	 * @param lastEvents the lastEvents to set
	 */
	public void setLastEvents(List<EventoSincronizacion> lastEvents) {
		this.lastEvents = lastEvents;
	}
}
