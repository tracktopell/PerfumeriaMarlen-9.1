/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.common.view.validator;

import java.util.Hashtable;
import javax.faces.application.FacesMessage;

/**
 *
 * @author alfredo
 */
public class ValidationException extends Exception{
	Hashtable<String,FacesMessage> prepraredMessages;
	
	public ValidationException(Hashtable<String,FacesMessage> prepraredMessages){
		this.prepraredMessages = prepraredMessages;
	}
	
	public Hashtable<String,FacesMessage> getPrepraredMessages(){
		return prepraredMessages;
	}
	
}
