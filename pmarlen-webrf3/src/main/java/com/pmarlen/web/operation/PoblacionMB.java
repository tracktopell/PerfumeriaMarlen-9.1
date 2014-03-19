package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Poblacion;
import com.pmarlen.model.controller.PoblacionJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PoblacionMB extends EntityCRUDMB<Poblacion>{
	
	private static List<String> entidadFederativaList;
	
	private static List<String> municipioODelegacionList;
	
	private List<Poblacion> poblacionByMunicipioODelegacionList = null;
	
	private String entidadFederativa;
	
	private String municipioODelegacion;
	
	private String codigoPostal;	
	
	public PoblacionMB() {		
		super(LoggerFactory.getLogger(PoblacionMB.class));
		entity = new Poblacion();
	}
	
	@Autowired
	public void setPoblacionJPAController(PoblacionJPAController poblacionJPAController) {
		super.setEntityJPAController(poblacionJPAController);
	}

	@Override
	public void specialValidatationForm() throws ValidationException {
		logger.debug("specialValidatationForm: entity=" + entity);
	}

	@Override
	public Object getEntityId() {
		return entity.getId();
	}

	@Override
	protected Object parseToObjectIdValue(String id) {
		return id;
	}

	void estadoInicial() {
		if(entity != null){
			entidadFederativa    = entity.getEntidadFederativa();
			municipioODelegacion = entity.getMunicipioODelegacion();			
		} else {
			entidadFederativa    = null;
			municipioODelegacion = null;
		}
	}
	

	public List<SelectItem> getEntidadFederativaList() {
		if(entidadFederativaList == null){
			entidadFederativaList = ((PoblacionJPAController)entityJPAController).findAllEntidadFederativa();
		}
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));
		
        for (String ef : entidadFederativaList) {
            resultList.add(new SelectItem(ef,ef.toUpperCase()));
        }
        return resultList;
    }
	
	public void entidadFederativaChanged(ValueChangeEvent e) {
        //Integer newValue = (Integer) e.getNewValue();
        logger.debug("->entidadFederativaChanged:" );
		
	}
	
	public void municipioODelegacionChanged(ValueChangeEvent e) {
        //Integer newValue = (Integer) e.getNewValue();
        logger.debug("->municipioODelegacionChanged:" );		
	}

	public void poblacionChanged(ValueChangeEvent e) {
		Object oldValue = e.getOldValue();
        Object newValue = e.getNewValue();
		logger.debug("->poblacionChanged:oldValue="+oldValue+", newValue="+newValue +", class ? " +(newValue!=null?newValue.getClass().toString():" null") );
		if(newValue != null){
			Integer poblacionSelectedId = new Integer(newValue.toString());
			
			for (Poblacion p : poblacionByMunicipioODelegacionList) {
				if(p.getId().intValue() == poblacionSelectedId.intValue()){
					entity = p;
					logger.debug("->poblacionChanged: \tselected="+entity.getId());
				}
			}
		}
	}

	public void aceptarPoblacion(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();		
		logger.debug("aceptarPoblacion: this.entity="+this.entity);
	}	

	public List<SelectItem> getMunicipioODelegacionList() {
		List<String> municipioODelegacionList = null;
        List<SelectItem> resultList = new ArrayList<SelectItem>();

		if(entidadFederativa != null && entidadFederativa.length()>1){
			municipioODelegacionList = ((PoblacionJPAController)entityJPAController).findAllMunicipioODelegacion(entidadFederativa);

			resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

			for (String mod : municipioODelegacionList) {
				resultList.add(new SelectItem(mod,mod.toUpperCase()));
			}
		}
        
		return resultList;
    }

	public List<SelectItem> getCodigoPostalList() {
		List<String> codigoPostalList = null;
        List<SelectItem> resultList = new ArrayList<SelectItem>();

		if(municipioODelegacion != null && municipioODelegacion.length()>1){
			codigoPostalList = ((PoblacionJPAController)entityJPAController).findAllCodigoPostal(municipioODelegacion);

			resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

			for (String cp : codigoPostalList) {
				resultList.add(new SelectItem(cp,cp.toUpperCase()));
			}
		}
        
		return resultList;
    }
	
	public List<SelectItem> getPoblacionByCodigoPostal() {
		List<Poblacion> poblacionList = null;
        List<SelectItem> resultList = new ArrayList<SelectItem>();

		if(codigoPostal != null && codigoPostal.length()>1){
			poblacionList = ((PoblacionJPAController)entityJPAController).findAllPoblacionByCodigoPostal(codigoPostal);

			resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

			for (Poblacion p : poblacionList) {				
				resultList.add(new SelectItem(p.getId(),"["+p.getCodigoPostal()+"] "+p.getNombre().toUpperCase()));
			}
		}
        
		return resultList;
    }

	
	public List<SelectItem> getPoblacionByMunicipioODelegacion() {		
        List<SelectItem> resultList = new ArrayList<SelectItem>();

		if(municipioODelegacion != null && municipioODelegacion.length()>1){
			poblacionByMunicipioODelegacionList = ((PoblacionJPAController)entityJPAController).findAllPoblacionByMunicipioODelegacion(municipioODelegacion);

			resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

			for (Poblacion p : poblacionByMunicipioODelegacionList) {				
				resultList.add(new SelectItem(p.getId(),"["+p.getCodigoPostal()+"] "+p.getNombre().toUpperCase()));
			}
		}
        
		return resultList;
    }

	/**
	 * @return the entidadFederativa
	 */
	public String getEntidadFederativa() {
		return entidadFederativa;
	}

	/**
	 * @param entidadFederativa the entidadFederativa to set
	 */
	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	/**
	 * @return the municipioODelegacion
	 */
	public String getMunicipioODelegacion() {
		return municipioODelegacion;
	}

	/**
	 * @param municipioODelegacion the municipioODelegacion to set
	 */
	public void setMunicipioODelegacion(String municipioODelegacion) {
		this.municipioODelegacion = municipioODelegacion;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

}
