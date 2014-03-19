package com.pmarlen.web.operation;

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
public class CantidadProductoEnPedidoValidator implements Validator {
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }
        boolean isValid =  true;
        if ( isValid ) {
            FacesMessage message = Messages.getSimpleMessage("EMAIL_FORMAT_ERROR");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
