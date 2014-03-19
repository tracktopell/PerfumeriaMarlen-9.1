package com.pmarlen.web.common.view.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.pmarlen.web.common.view.messages.Messages;


/**
 *
 * @author alfred
 */
public class ExtensionValidator implements Validator {
   
	private static String regExpPatternString = "^([0-9]{1,5})+([ ]{0,1}[0-9]{1,5})*$";
	
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            return;
        }
        String emailValue = value.toString();
        if (! emailValue.matches(regExpPatternString)) {
            FacesMessage message = Messages.getSimpleMessage("EXTENSION_FORMAT_ERROR");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}