package com.pmarlen.web.common.view.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.pmarlen.web.common.view.messages.Messages;

public class AlphanumericValidator implements Validator {
	
	private static final String REG_EXP_ALPHANUMBER_PATTERN = "^([A-Za-z][0-9a-zA-Z]+)$";
	
	public static final String ALPHANUMBER_FORMAT_INVALID_MESSAGE_ID = "ALPHANUMBER_ERROR";
	
    public void validate(FacesContext context, UIComponent component, Object objectValue) throws ValidatorException {

    	String strLabelComponent = null;
    	   
        if (objectValue == null) {
            return;
        }
        
        if(!(component instanceof UIInput)){
        	return;
        }
        
        if(component instanceof UIInput){
        	
            String numberValue = objectValue.toString();
            
            if (!numberValue.matches(REG_EXP_ALPHANUMBER_PATTERN)) {
            	
            	if(component instanceof HtmlInputText){

		        	HtmlInputText htmlInputText = (HtmlInputText)component;
		        	
		        	if(htmlInputText!=null)		        		
		        		strLabelComponent = htmlInputText.getLabel();
		        	
            	}else if(component instanceof HtmlInputSecret){
    				HtmlInputSecret htmlInputSecret = (HtmlInputSecret)component;
		        	
		        	if(htmlInputSecret!=null)		        		
		        		strLabelComponent = htmlInputSecret.getLabel();
            	}
            		
        		if(strLabelComponent!=null){
        		
        			FacesMessage facesMessageError = Messages.getMessage(ALPHANUMBER_FORMAT_INVALID_MESSAGE_ID,(new Object[] { strLabelComponent }));
					facesMessageError.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(facesMessageError);
        				
        		}         	
            	            	
            }        	
        	
        }
               
    }

}
