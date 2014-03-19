package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Industria;
import com.pmarlen.model.controller.IndustriaJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.Hashtable;
import javax.faces.application.FacesMessage;
import javax.persistence.NoResultException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class IndustriaMB extends EntityCRUDMB<Industria>{

	public IndustriaMB() {
		super(LoggerFactory.getLogger(IndustriaMB.class));
		entity = new Industria();
	}

	@Autowired
	public void setIndustriaJPAController(IndustriaJPAController industriaJPAController) {
		super.setEntityJPAController(industriaJPAController);
	}
	
	@Override
	public void specialValidatationForm() throws ValidationException {
		Industria industriaByNombre=null;
		try{
			industriaByNombre = entityJPAController.findEntityByReadableProperty(entity.getNombre());
		} catch(NoResultException nsre){
			industriaByNombre = null;
		}
		logger.debug("specialValidatationForm: industriaByNombre=" + industriaByNombre);

		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		if (industriaByNombre != null && getEntityId()==null) {
			prepraredMessages.put("formSecundario:inputTextNombre",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
					(getEntityId() == null ? Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE")
					: Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT")) + " : ",
					Messages.getLocalizedString("COMMON_CRUD_RECORD_EXIST_SAME_DESCRIPTION")));

			throw new ValidationException(prepraredMessages);
		}
	}

	@Override
	public Object getEntityId() {
		if(entity == null){
			entity = new Industria();
		}
		return entity.getId();
	}

	@Override
	protected Object parseToObjectIdValue(String id) {
		return new Integer(id);
	}
}
