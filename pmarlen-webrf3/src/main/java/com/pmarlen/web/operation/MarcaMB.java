package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Industria;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.controller.IndustriaJPAController;
import com.pmarlen.model.controller.LineaJPAController;
import com.pmarlen.model.controller.MarcaJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class MarcaMB extends EntityCRUDMB<Marca>{
	
	private LineaJPAController lineaJPAController;
	
	private IndustriaJPAController industriaJPAController;
	
	private List<Linea> lineaList;
	
	private List<Industria> industriaList;

	public MarcaMB() {		
		super(LoggerFactory.getLogger(MarcaMB.class));
		entity = new Marca();
		entity.setLinea(new Linea());
		entity.setIndustria(new Industria());		
	}
	
	@Autowired
	public void setLineaJPAController(LineaJPAController lineaJPAController) {
		this.lineaJPAController = lineaJPAController;
	}

	@Autowired
	public void setIndustriaJPAController(IndustriaJPAController industriaJPAController) {
		this.industriaJPAController = industriaJPAController;
	}
	
	
	@Autowired
	public void setMarcaJPAController(MarcaJPAController marcaJPAController) {
		super.setEntityJPAController(marcaJPAController);
	}

	@Override
	public void preparaNuevoRegistro(ActionEvent e) {
		super.preparaNuevoRegistro(e);
		entity.setIndustria(new Industria());
		entity.setLinea(new Linea());
	}
	
	public List<SelectItem> getLineaList() {
        List<Linea> lineaList = lineaJPAController.findAllEntities();
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

        for (Linea linea : lineaList) {
            resultList.add(new SelectItem(linea.getId(), linea.getNombre()));
        }
        return resultList;
    }
	
	public List<SelectItem> getIndustriaList() {
        List<Industria> industriaList = industriaJPAController.findAllEntities();
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

        for (Industria industria : industriaList) {
            resultList.add(new SelectItem(industria.getId(), industria.getNombre()));
        }
        return resultList;
    }
	

	@Override
	public void specialValidatationForm() throws ValidationException {
		logger.debug("specialValidatationForm: entity=" + entity);
		Marca marcaByNombre=null;
		try{
			marcaByNombre = entityJPAController.findEntityByReadableProperty(entity.getNombre());
		} catch(NoResultException nsre){
			marcaByNombre = null;
		}
		logger.debug("specialValidatationForm: marcaByNombre=" + marcaByNombre);

		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		if (marcaByNombre != null && getEntityId()==null) {
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
			entity = new Marca();
		}
		return entity.getId();
	}
	
	@Override
	protected Object parseToObjectIdValue(String id) {
		return new Integer(id);
	}
}
