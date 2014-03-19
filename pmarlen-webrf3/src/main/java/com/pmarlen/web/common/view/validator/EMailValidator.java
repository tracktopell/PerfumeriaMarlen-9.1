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
public class EMailValidator implements Validator {
    private static String regExpPatternString = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        if (value == null) {
            return;
        }
        String emailValue = value.toString();
        if (! emailValue.matches(regExpPatternString)) {
            FacesMessage message = Messages.getSimpleMessage("EMAIL_FORMAT_ERROR");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
