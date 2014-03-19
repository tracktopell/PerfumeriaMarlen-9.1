package com.pmarlen.web.common.view.validator;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.pmarlen.web.common.view.messages.Messages;


/**
 * 
 * @author juan.santiagoc
 */

public class RepeatValueValidator implements Validator, Serializable {	
	public static final String CONFIRM_ERROR_MESSAGE_ID = "CONFIRM_VALUE_ERROR";
	
	private String target;

	public void validate(FacesContext facesContext, UIComponent uiComponent, Object object)
			throws ValidatorException {

		String strLabelComponent = null;
		String strLabelComponentToCompare = null;
		String strValueToCompare = null;
		
		//facesContext.getViewRoot().findComponent("formAddEditUser:inputTextUserPassword")
		
        if (object == null || !(uiComponent instanceof UIInput) || target == null){
        	return;
        }
        UIComponent uiComponentToCompare = uiComponent.findComponent(target);
       
        if(uiComponentToCompare instanceof HtmlInputText){
    		
        	strLabelComponentToCompare = ((HtmlInputText)uiComponentToCompare).getLabel();
        	strValueToCompare =	(String) ((HtmlInputText)uiComponentToCompare).getValue();
    		
    	}else if(uiComponentToCompare instanceof HtmlInputSecret){
    		
    		strLabelComponentToCompare = ((HtmlInputSecret)uiComponentToCompare).getLabel();
        	strValueToCompare =	(String)((HtmlInputSecret)uiComponentToCompare).getValue();
    		
    	}else{
    		return ;
    	}
        
        if( !object.toString().equals(strValueToCompare)){
	        	
        	if(uiComponent instanceof HtmlInputText){
        		
        		strLabelComponent = ((HtmlInputText)uiComponent).getLabel();
        		
        	}else if(uiComponent instanceof HtmlInputSecret){
        		
        		strLabelComponent = ((HtmlInputSecret)uiComponent).getLabel();
        		
        	}else{
        		return ;
        	}
        		
    		if(strLabelComponent!=null){	    
    			
	            FacesMessage message = Messages.getMessage(CONFIRM_ERROR_MESSAGE_ID,(new Object[] { strLabelComponent, strLabelComponentToCompare }));
	            message.setSeverity(FacesMessage.SEVERITY_ERROR);
	            
				throw new ValidatorException(message);
				
        	}
        }

	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}


}
