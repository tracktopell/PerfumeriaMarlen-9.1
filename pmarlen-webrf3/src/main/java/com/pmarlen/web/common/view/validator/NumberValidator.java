package com.pmarlen.web.common.view.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.pmarlen.web.common.view.messages.Messages;


/**
 *
 * @author dukzux
 */
public class NumberValidator implements Validator {
	
	private static final String REG_EXP_NUMBER_PATTERN = "^[0-9]+$";
	
	public static final String NUMBER_FORMAT_INVALID_MESSAGE_ID = "NUMBER_ERROR";
	
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
            
            if (!numberValue.matches(REG_EXP_NUMBER_PATTERN)) {

            	HtmlInputText htmlInputText = (HtmlInputText)component;
            	
            	if(htmlInputText!=null){
            		
            		strLabelComponent = htmlInputText.getLabel();
            		
            		if(strLabelComponent!=null){
            		
            			FacesMessage facesMessageError = Messages.getMessage(NUMBER_FORMAT_INVALID_MESSAGE_ID,(new Object[] { strLabelComponent }));
						facesMessageError.setSeverity(FacesMessage.SEVERITY_ERROR);
						throw new ValidatorException(facesMessageError);
            				
            		}
            		
            	}            	
            	            	
            }        	
        	
        }
               
    }
}