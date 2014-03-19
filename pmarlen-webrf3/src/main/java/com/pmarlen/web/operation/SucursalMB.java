package com.pmarlen.web.operation;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.Sucursal;
import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.controller.SucursalJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SucursalMB extends EntityCRUDMB<Sucursal>{
	
	private SucursalJPAController sucursalJPAController;
	
	private PoblacionMB poblacionMB;
	
	private boolean changePassword;

	private String passwordConfirm;

	
	public SucursalMB() {		
		super(LoggerFactory.getLogger(SucursalMB.class));
		entity = new Sucursal();
		entity.setPoblacion(new Poblacion());
		ArrayList<Almacen> almacenes =  new ArrayList<Almacen>();
		
		final Almacen almacenLinea = new Almacen();
		almacenLinea.setTipoAlmacen(Constants.ALMACEN_LINEA);
		almacenes.add(almacenLinea);
		
		final Almacen almacenOportunidad = new Almacen();
		almacenOportunidad.setTipoAlmacen(Constants.ALMACEN_OPORTUNIDAD);
		almacenes.add(almacenOportunidad);
		
		final Almacen almacenRegalias = new Almacen();
		almacenRegalias.setTipoAlmacen(Constants.ALMACEN_OPORTUNIDAD);
		almacenes.add(almacenRegalias);
		
		entity.setAlmacenCollection(almacenes);
	}

	@Autowired
	public void setSucursalJPAController(SucursalJPAController productoJPAController) {
		super.setEntityJPAController(productoJPAController);
	}

	public void setPoblacionMB(PoblacionMB poblacionMB) {
		this.poblacionMB = poblacionMB;
	}
	
	
	
	@Override
	public void preparaNuevoRegistro(ActionEvent e) {
		super.preparaNuevoRegistro(e);		
		
		entity = new Sucursal();
		entity.setSucursal(sucursalJPAController.getSucursalPrincipal());
		entity.setPoblacion(new Poblacion());
		ArrayList<Almacen> almacenes =  new ArrayList<Almacen>();
		
		final Almacen almacenLinea = new Almacen();
		almacenLinea.setTipoAlmacen(Constants.ALMACEN_LINEA);
		almacenes.add(almacenLinea);
		
		final Almacen almacenOportunidad = new Almacen();
		almacenOportunidad.setTipoAlmacen(Constants.ALMACEN_OPORTUNIDAD);
		almacenes.add(almacenOportunidad);
		
		final Almacen almacenRegalias = new Almacen();
		almacenRegalias.setTipoAlmacen(Constants.ALMACEN_OPORTUNIDAD);
		almacenes.add(almacenRegalias);
		
		entity.setAlmacenCollection(almacenes);

	}

	@Override
	public void editarRegistro(ActionEvent e) {
		super.editarRegistro(e);		
		preparaDialogoPoblacion(e);
	}
	
	public void preparaDialogoPoblacion(ActionEvent e) {
		logger.debug("->preparaDialogoPoblacion:");		
		poblacionMB.entity = entity.getPoblacion();
		poblacionMB.estadoInicial();
		logger.debug("->preparaDialogoPoblacion: entity.getPoblacion() = "+entity.getPoblacion());
		logger.debug("->preparaDialogoPoblacion: poblacionMB.entity    = "+poblacionMB.entity);
	}
		
	public void aceptarPoblacion(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();		
		logger.debug("aceptarPoblacion: poblacionMB.entity  ="+poblacionMB.entity);
		logger.debug("aceptarPoblacion: entity.getPoblacion ="+entity.getPoblacion());
		entity.setPoblacion(poblacionMB.entity);
		logger.debug("\t->aceptarPoblacion:entity.getPoblacion ="+entity.getPoblacion());		
	}
	
	@Override
	public void specialValidatationForm() throws ValidationException {
		logger.debug("specialValidatationForm: entity=" + entity);
		Sucursal sucursalByRFC=null;
		try{
			sucursalByRFC = entityJPAController.findEntityByReadableProperty(entity.getNombre());
		} catch(NoResultException nsre){
			sucursalByRFC = null;
		}
		logger.debug("specialValidatationForm: sucursalByRFC=" + sucursalByRFC);

		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		if (sucursalByRFC != null && getEntityId()==null) {
			prepraredMessages.put("formSecundario:inputTextRfc",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
					(getEntityId() == null ? Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE")
					: Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT")) + " : ",
					Messages.getLocalizedString("COMMON_CRUD_RECORD_EXIST_SAME_DESCRIPTION")));			
		}		
		
		if(prepraredMessages.size()>0){
			throw new ValidationException(prepraredMessages);
		}
	}

	@Override
	public Object getEntityId() {
		if(entity == null){
			entity = new Sucursal();
		}
		return entity.getId();
	}

	@Override
	protected Object parseToObjectIdValue(String id) {
		return new Integer (id);
	}

	/**
	 * @return the changePassword
	 */
	public boolean isChangePassword() {
		return changePassword;
	}

	/**
	 * @param changePassword the changePassword to set
	 */
	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}

	/**
	 * @return the passwordConfirm
	 */
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	/**
	 * @param passwordConfirm the passwordConfirm to set
	 */
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
