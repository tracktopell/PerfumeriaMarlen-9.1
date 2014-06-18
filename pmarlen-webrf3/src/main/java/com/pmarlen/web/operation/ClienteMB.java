package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.controller.SucursalJPAController;
import com.pmarlen.model.controller.ClienteJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.Date;
import java.util.Hashtable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ClienteMB extends EntityCRUDMB<Cliente>{
	
	private SucursalJPAController sucursalJPAController;
	
	private PoblacionMB poblacionMB;
	
	public ClienteMB() {		
		super(LoggerFactory.getLogger(ClienteMB.class));
		entity = new Cliente();
		entity.setFechaCreacion(new Date());
		entity.setPoblacion(new Poblacion());
	}

	@Autowired
	public void setClienteJPAController(ClienteJPAController productoJPAController) {
		super.setEntityJPAController(productoJPAController);
	}

	public void setPoblacionMB(PoblacionMB poblacionMB) {
		this.poblacionMB = poblacionMB;
	}
	
	
	
	@Override
	public void preparaNuevoRegistro(ActionEvent e) {
		super.preparaNuevoRegistro(e);		
		
		entity = new Cliente();
		entity.setFechaCreacion(new Date());
		entity.setPoblacion(new Poblacion());
		
		preparaDialogoPoblacion(e);
	}

	@Override
	public void editarRegistro(ActionEvent e) {
		super.editarRegistro(e);
		if(entity.getDireccionFacturacion() == null){
			String direccionValue = null;
			direccionValue = entity.getCalle()+
							", Num. Ext. "+(entity.getNumExterior()!=null&&entity.getNumExterior().length()>0?entity.getNumExterior():"S/N")+
							", Num. Int. "+(entity.getNumInterior()!=null&&entity.getNumInterior().length()>0?entity.getNumInterior():"S/N")+					
							", "+entity.getPoblacion().getTipoAsentamiento()+" "+entity.getPoblacion().getNombre()+
							", "+entity.getPoblacion().getMunicipioODelegacion()+
							", "+entity.getPoblacion().getEntidadFederativa()+
							", C.P. "+entity.getPoblacion().getCodigoPostal();
			entity.setDireccionFacturacion(direccionValue);
		}
		preparaDialogoPoblacion(e);
	}

	public void aceptarPoblacion(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();		
		logger.debug("aceptarPoblacion: poblacionMB.entity  ="+poblacionMB.entity);
		logger.debug("aceptarPoblacion: entity.getPoblacion ="+entity.getPoblacion());
		entity.setPoblacion(poblacionMB.entity);
		logger.debug("\t->aceptarPoblacion:entity.getPoblacion ="+entity.getPoblacion());
		
	}
	
	public void preparaDialogoPoblacion(ActionEvent e) {
		logger.debug("->preparaDialogoPoblacion");
		FacesContext context = FacesContext.getCurrentInstance();		
		poblacionMB.entity = entity.getPoblacion();
		poblacionMB.estadoInicial();
		logger.debug("->preparaDialogoPoblacion: entity.getPoblacion() = "+entity.getPoblacion());
		logger.debug("->preparaDialogoPoblacion: poblacionMB.entity    = "+poblacionMB.entity);
	}
		
	@Override
	public void specialValidatationForm() throws ValidationException {
		logger.debug("specialValidatationForm: entity=" + entity);
		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		/*
		Cliente usuarioByRFC=null;
		try{
			usuarioByRFC = entityJPAController.findEntityByReadableProperty(entity.getRfc());
		} catch(NoResultException nsre){
			usuarioByRFC = null;
		}
		logger.debug("specialValidatationForm: usuarioByRFC=" + usuarioByRFC);
		if (usuarioByRFC != null && getEntityId()==null) {
			prepraredMessages.put("formSecundario:inputTextRfc",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
					(getEntityId() == null ? Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE")
					: Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT")) + " : ",
					Messages.getLocalizedString("COMMON_CRUD_RECORD_EXIST_SAME_DESCRIPTION")));			
		}		
		*/
		if(prepraredMessages.size()>0){
			throw new ValidationException(prepraredMessages);
		}
	}

	@Override
	public Object getEntityId() {
		if(entity == null){
			entity = new Cliente();
		}
		return entity.getId();
	}

	@Override
	protected Object parseToObjectIdValue(String id) {
		return new Integer (id);
	}
}
