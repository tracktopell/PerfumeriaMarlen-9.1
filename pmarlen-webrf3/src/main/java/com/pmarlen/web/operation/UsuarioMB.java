package com.pmarlen.web.operation;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.beans.Sucursal;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.PerfilJPAController;
import com.pmarlen.model.controller.SucursalJPAController;
import com.pmarlen.model.controller.UsuarioJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UsuarioMB extends EntityCRUDMB<Usuario>{
	private SucursalJPAController sucursalJPAController;
	
	private PerfilJPAController perfilJPAController;
	
	private List<Sucursal> sucursalList;

	private List<Perfil> perfilList;

	private boolean changePassword;

	private String passwordConfirm;

	private String[] perfilesIds;

	public String[] getPerfilesIds() {
		return perfilesIds;
	}

	public void setPerfilesIds(String[] perfilesIds) {
		this.perfilesIds = perfilesIds;
	}
	
	public UsuarioMB() {		
		super(LoggerFactory.getLogger(UsuarioMB.class));
		entity = new Usuario();
		entity.setSucursal(new Sucursal());
	}

	@Autowired
	public void setSucursalJPAController(SucursalJPAController sucursalJPAController) {
		this.sucursalJPAController=sucursalJPAController;
	}

	@Autowired
	public void setPerfilJPAController(PerfilJPAController perfilJPAController) {
		this.perfilJPAController = perfilJPAController;
	}
	
	@Autowired
	public void setUsuarioJPAController(UsuarioJPAController productoJPAController) {
		super.setEntityJPAController(productoJPAController);
	}
	
	@Override
	public void preparaNuevoRegistro(ActionEvent e) {
		super.preparaNuevoRegistro(e);
		entity.setSucursal(new Sucursal());
		perfilesIds = new String[0];
	}
	
	public List<SelectItem> getSucursalList() {
        List<Sucursal> sucursalList = sucursalJPAController.findAllEntities();
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));
		
		List<Sucursal> sortedSucursalList = sucursalList; //sortSucursal(sucursalList);
        for (Sucursal sucursal : sucursalList) {
            resultList.add(new SelectItem(sucursal.getId(),sucursal.getNombre()));
        }
        return resultList;
    }
	
	public List<SelectItem> getPerfilList() {
        List<Perfil> perfilList = perfilJPAController.findAllEntities();
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        
		List<Perfil> sortedPerfilList = sortPerfil(perfilList);
        for (Perfil perfil : perfilList) {
			if(!perfil.getId().equals(Constants.PERFIL_ROOT)){
				resultList.add(new SelectItem(perfil.getId(),perfil.getDescripcion()));
			}
        }
        return resultList;
    }
	
	public List<SelectItem> getHabilitadoList() {
        List<SelectItem> resultList = new ArrayList<SelectItem>();
		
        resultList.add(new SelectItem(0, Messages.getLocalizedString("COMMON_DISABLED")));
		resultList.add(new SelectItem(1, Messages.getLocalizedString("COMMON_ENABLED")));
		
        return resultList;
    }
	
	private List<Sucursal> sortSucursal(List<Sucursal> ml){
		
		SortedSet<Sucursal> ss = new TreeSet<Sucursal>(new Comparator<Sucursal>(){

			@Override
			public int compare(Sucursal m1, Sucursal m2) {
				int r3 = m1.getNombre().compareTo(m2.getNombre());
				return r3;
			}
		});
		final Iterator<Sucursal> iterator = ss.iterator();
		List<Sucursal> result=new ArrayList<Sucursal>();
		while(iterator.hasNext()){
			result.add(iterator.next());
		}
		return result;
	}
	
	private List<Perfil> sortPerfil(List<Perfil> ml){
		
		SortedSet<Perfil> ss = new TreeSet<Perfil>(new Comparator<Perfil>(){

			@Override
			public int compare(Perfil m1, Perfil m2) {
				int r3 = m1.getDescripcion().compareTo(m2.getDescripcion());
				return r3;
			}
		});
		final Iterator<Perfil> iterator = ss.iterator();
		List<Perfil> result=new ArrayList<Perfil>();
		while(iterator.hasNext()){
			result.add(iterator.next());
		}
		return result;
	}

	@Override
	public void specialValidatationForm() throws ValidationException {
		logger.debug("specialValidatationForm: entity=" + entity);
		Usuario usuarioByNombre=null;
		try{
			usuarioByNombre = entityJPAController.findEntityByReadableProperty(entity.getNombreCompleto());
		} catch(NoResultException nsre){
			usuarioByNombre = null;
		}
		logger.debug("specialValidatationForm: usuarioByNombre=" + usuarioByNombre);

		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		if (usuarioByNombre != null && getEntityId()==null) {
			prepraredMessages.put("formSecundario:inputTextNombre",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
					(getEntityId() == null ? Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE")
					: Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT")) + " : ",
					Messages.getLocalizedString("COMMON_CRUD_RECORD_EXIST_SAME_DESCRIPTION")));			
		}
		
		if(!entity.getPassword().equals(passwordConfirm)){
			prepraredMessages.put("formSecundario:inputTextPasswordConfirm",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
					(getEntityId() == null ? Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE")
					: Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT")) + " : ",
					Messages.getLocalizedString("USUARIO_PASSWORDS_NOT_MATCH")));			
		}
		if(prepraredMessages.size()>0){
			throw new ValidationException(prepraredMessages);
		}
		
		logger.debug("specialValidatationForm: encoding password for MD5");
		entity.setPassword(Constants.getMD5Encrypted(entity.getPassword()));
		
	}

	@Override
	public Object getEntityId() {
		if(entity == null){
			entity = new Usuario();
		}
		return entity.getUsuarioId();
	}

	@Override
	protected Object parseToObjectIdValue(String id) {
		return id;
	}

	@Override
	public void editarRegistro(ActionEvent e) {
		super.editarRegistro(e); //To change body of generated methods, choose Tools | Templates.
		final Collection<Perfil> perfilCollection = entity.getPerfilCollection();
		perfilesIds = new String[perfilCollection.size()];
		int perfilCounter=0;
		for(Perfil p: perfilCollection){
			perfilesIds[perfilCounter++]=p.getId();
		}
	}

	@Override
	public void aceptarGuardar(ActionEvent e) {
		List<Perfil> perfilesNuevos=new ArrayList<Perfil>();
		List<Perfil> todosLosPerfiles = perfilJPAController.findAllEntities();
		for(Perfil p: todosLosPerfiles){
			for(String perfilId: perfilesIds){
				if(p.getId().equals(perfilId)){
					p.setUsuarioCollection(null);
					perfilesNuevos.add(p);
					break;
				}
			}
		}
		
		final Collection<Perfil> perfilCollection = entity.getPerfilCollection();
		logger.debug("aceptarGuardar:perfilCollection="+perfilCollection);
		
		logger.debug("aceptarGuardar:getHabilitadoList="+getHabilitadoList());
		logger.debug("aceptarGuardar:perfilesNuevos="+perfilesNuevos);
		logger.debug("aceptarGuardar:entity.getHabilitado="+entity.getHabilitado());
		
		entity.setPerfilCollection(perfilesNuevos);
		super.aceptarGuardar(e);
	}
	
	
	
	public boolean isChangePassword() {
		return changePassword;
	}

	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}

	
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	

}
