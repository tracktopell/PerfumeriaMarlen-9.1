package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Multimedio;
import com.pmarlen.model.controller.LineaJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.Collection;
import java.util.Hashtable;
import javax.faces.application.FacesMessage;
import javax.persistence.NoResultException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LineaMB extends EntityCRUDMB<Linea> {

	public LineaMB() {
		super(LoggerFactory.getLogger(LineaMB.class));
		entity = new Linea();
	}
	
	@Autowired
	public void setLineaJPAController(LineaJPAController lineaJPAController) {
		super.setEntityJPAController(lineaJPAController);
	}
	
	@Override
	public void specialValidatationForm() throws ValidationException {
		Linea lineaByNombre=null;
		try{
			lineaByNombre = entityJPAController.findEntityByReadableProperty(entity.getNombre());
		} catch(NoResultException nsre){
			lineaByNombre = null;
		}
		logger.debug("specialValidatationForm: lineaByNombre=" + lineaByNombre);

		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		if (lineaByNombre != null && getEntityId()==null) {
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
			entity = new Linea();
		}
		return entity.getId();
	}

	@Override
	protected Object parseToObjectIdValue(String id) {
		return new Integer(id);
	}

	@Override
	public void actualizar() {
		logger.debug("LineaMB.actualizar(): -> before inherited, actualizar() ");
		
		final Collection<Multimedio> multimedioCollection = entity.getMultimedioCollection();
		logger.debug("LineaMB.actualizar(): -> multimedioCollection is null ? "+(multimedioCollection == null) );
		try{
			for (Multimedio m: multimedioCollection){
				logger.debug("LineaMB.actualizar(): \t-> multimedio="+m);
			}
		}catch(Throwable t){
			logger.error("LineaMB.actualizar(): iteration:",t);
		}
		logger.debug("LineaMB.actualizar(): ok, call super.actualizar() ");
		super.actualizar(); //To change body of generated methods, choose Tools | Templates.
	}
	
	
}