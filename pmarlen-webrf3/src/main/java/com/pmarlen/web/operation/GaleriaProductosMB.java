/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.Industria;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Sucursal;
import com.pmarlen.model.controller.IndustriaJPAController;
import com.pmarlen.model.controller.LineaJPAController;
import com.pmarlen.model.controller.MarcaJPAController;
import com.pmarlen.model.controller.SucursalJPAController;
import com.pmarlen.model.dto.InventarioFastView;
import com.pmarlen.model.dto.MovimientoHistoricoProductoFastView;
import com.pmarlen.web.common.view.messages.Messages;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.richfaces.event.DropEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author VEAXX9M
 */
public class GaleriaProductosMB {

	private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;
	
	private SucursalJPAController sucursalJPAController;
	
	private IndustriaJPAController industriaJPAController;
	
	private LineaJPAController lineaJPAController;

	private MarcaJPAController marcaJPAController;
	
	private Integer industriaSelectedId;
	
	private Integer lineaSelectedId;
	
	private Integer marcaSelectedId;
	
	public static final int SELECTION_MODE_INDUSTRIA_MARCA = 1;

	public static final int SELECTION_MODE_LINEA_MARCA = 2;
	
	private int selecionMode;
	
    private List<InventarioFastView> inventarioFastViewList;
	
	private int scrollerPage;

	private Sucursal sucursalPrincipal;
	
	private Sucursal sucursalObjetivo;
	
	private boolean codigoBuscarEnabled;
	
	private Almacen almacenObjetivo;
	
	private Hashtable<Integer,String> tipoAlmacenHashTable;	
	
	private List<SelectItem> resultTipoAlmacenList;

	private Integer tipoAlmacen;
	private int numRecShow;
    
	private final Logger logger = LoggerFactory.getLogger(GaleriaProductosMB.class);

	public boolean getCodigoBuscarEnabled() {
		return codigoBuscarEnabled;
	}

	public void setCodigoBuscarEnabled(boolean codigoBuscarEnabled) {
		this.codigoBuscarEnabled = codigoBuscarEnabled;
	}

    public GaleriaProductosMB() {
		numRecShow = 25;
		selecionMode = SELECTION_MODE_INDUSTRIA_MARCA;
		codigoBuscarEnabled=false;
		tipoAlmacen = Constants.ALMACEN_LINEA;
    }
			
	private List<Industria> industriaList=null;
	private List<Linea> lineaList=null;
	private List<Marca> marcaList=null;
	
	public List<SelectItem> getIndustriaList() {
		//logger.debug(">> getIndustriaList: industriaList is null?"+(industriaList==null));
		
		if(industriaList == null){
			//logger.debug("\t>> getIndustriaList: industriaList = industriaJPAController.findAllEntities();");
			industriaList = industriaJPAController.findAllEntities();
		}
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));
		
        for (Industria industria : industriaList) {
            resultList.add(new SelectItem(industria.getId(),industria.getNombre()));
        }
        return resultList;
    }
	
	public List<SelectItem> getLineaList() {
        //logger.debug(">> getLineaList: lineaList is null?"+(lineaList==null));
		
		if(lineaList == null) {
			//logger.debug("\t>> getLineaList: lineaList = lineaJPAController.findAllEntities();");
			lineaList = lineaJPAController.findAllEntities();
		}
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));
		
        for (Linea linea : lineaList) {
            resultList.add(new SelectItem(linea.getId(),linea.getNombre()));
        }
        return resultList;
    }
	
	public List<SelectItem> getMarcaList() {
		//logger.debug(">> getMarcaList: marcaList is null?"+(marcaList==null)+", selecionMode="+selecionMode);
		
        if(marcaList == null){
			//logger.debug(">> getMarcaList: marcaList = marcaJPAController.findAllEntities();");
			marcaList = marcaJPAController.findAllEntities();
		}
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));
		if(selecionMode == SELECTION_MODE_INDUSTRIA_MARCA && industriaSelectedId != null){
			for (Marca marca : marcaList) {
				if(marca.getIndustria().getId().intValue() == industriaSelectedId) {
					resultList.add(new SelectItem(marca.getId(),marca.getNombre()));									
				}
			} 
		} else if(selecionMode == SELECTION_MODE_LINEA_MARCA && lineaSelectedId != null){
			for (Marca marca : marcaList) {
				if(marca.getLinea().getId().intValue() == lineaSelectedId) {
					resultList.add(new SelectItem(marca.getId(),marca.getNombre()));
				}
			}
		}
        return resultList;
    }
	
	public void updateRecShow(ActionEvent e) {
		//logger.debug("\t>> updateRecShow: ");
		FacesContext context = FacesContext.getCurrentInstance();

		String numRecShowToSet = context.getExternalContext().getRequestParameterMap().get("numRecShowToSet");
		numRecShow = Integer.parseInt(numRecShowToSet);
		scrollerPage = 1;
	}

	public void reinicializarLista(){
		//logger.debug(">> reinicializarLista");
		inventarioFastViewList = null;
		scrollerPage = 1;
	}

    public List<InventarioFastView> getInventarioFastViewList(){
		//logger.debug(">> getInventarioFastViewList: inventarioFastViewList="+inventarioFastViewList);
		if(inventarioFastViewList == null){
			actualizarLista();
		}
        return inventarioFastViewList;
    }

	public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
		this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
	}

	public void setSucursalJPAController(SucursalJPAController sucursalJPAController) {
		this.sucursalJPAController = sucursalJPAController;
	}

	

    /**
	 *
	 * @param scrollerPage
	 */
	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	/**
	 *
	 * @return scrollerPage
	 */
	public int getScrollerPage() {
		return scrollerPage;
	}
/**
	 * @return the numRecShow
	 */
	public int getNumRecShow() {
		return numRecShow;
	}

	/**
	 * @param numRecShow the numRecShow to set
	 */
	public void setNumRecShow(int numRecShow) {
		this.numRecShow = numRecShow;
	}
	
	
	public int getEntityCount() {
		if (inventarioFastViewList != null) {
			return inventarioFastViewList.size();
		} else {
			return 0;
		}
	}
	
	private boolean aLaVergaSelectionModeSet=false;
	private boolean aLaVergaIndustriaSet=false;
	private boolean aLaVergaLineaSet=false;
	private boolean aLaVergaMarcaSet=false;
	
	
	
	public void almacenSelected(ValueChangeEvent event){
		logger.info(">> almacenSelected: old=" + event.getOldValue()+", new="+event.getNewValue()+", selecionMode"+selecionMode);        
		tipoAlmacen            = (Integer)event.getNewValue();
		
		selecionMode			= SELECTION_MODE_INDUSTRIA_MARCA;		
//		aLaVergaSelectionModeSet= true;
//		aLaVergaIndustriaSet	= true;
//		aLaVergaLineaSet		= true;
//		aLaVergaMarcaSet		= true;
//		
		logger.info("\t>> almacenSelected: ?  selecionMode="+selecionMode+",aLaVergaSelectionModeSet="+aLaVergaSelectionModeSet);        
		almacenObjetivo        = null;
//		industriaSelectedId    = null;
//		lineaSelectedId        = null;
//		marcaSelectedId        = null;		
		inventarioFastViewList = null;		
		//actualizarLista();
	}
	
	public void selectModeChanged(ValueChangeEvent event){
		logger.info(">> selectModeChanged: old=" + event.getOldValue()+", new="+event.getNewValue());        
		selecionMode           = (Integer)event.getNewValue();
		
		industriaSelectedId    = null;
		lineaSelectedId        = null;		
		marcaSelectedId        = null;
		inventarioFastViewList = null;
		//actualizarLista();			
	}
	
	private void actualizarLista() {		
		try{
			//logger.debug("------------>> actualizarLista : ");
			Integer almacenId = getAlmacenObjetivo().getId();
			//logger.debug("\t>> actualizarLista : almacenId="+almacenId+", marcaSelectedId="+marcaSelectedId+", industriaSelectedId="+industriaSelectedId+", lineaSelectedId="+lineaSelectedId+", selecionMode="+selecionMode);		
			if( marcaSelectedId != null) {
				//logger.debug("\t>> actualizarLista : Find by marca");
				inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastViewByMarca(almacenId,marcaSelectedId);
			} else if(selecionMode == SELECTION_MODE_INDUSTRIA_MARCA && industriaSelectedId != null){
				//logger.debug("\t>> actualizarLista : Find by industria");
				inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastViewByIndustria(almacenId,industriaSelectedId);				
			} else if(selecionMode == SELECTION_MODE_LINEA_MARCA && lineaSelectedId != null){
				//logger.debug("\t>> actualizarLista : Find by linea");
				inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastViewByLinea(almacenId,lineaSelectedId);
			}
			
			if(inventarioFastViewList != null){
				//logger.debug("\t>> actualizarLista : after inventarioFastViewList size ="+inventarioFastViewList.size());			
			} else {
				//logger.debug("\t>> actualizarLista : after inventarioFastViewList EMPTY :(  ");
			}
			
		} catch(Exception e){
			//logger.error("\t>> actualizarLista : Error:",e);
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al filtrar:", e.getMessage()));
		}
	}
	
	public void industriaChanged(ValueChangeEvent event){
		//logger.info(">> industriaChanged: old=" + event.getOldValue()+", new="+event.getNewValue());
		industriaSelectedId		= (Integer)event.getNewValue();
		lineaSelectedId			= null;		
		marcaSelectedId			= null;
		inventarioFastViewList	= null;		
	}
	
	public void lineaChanged(ValueChangeEvent event){
		//logger.info(">> lineaChanged: old=" + event.getOldValue()+", new="+event.getNewValue());        
		lineaSelectedId			= (Integer)event.getNewValue();
		industriaSelectedId		= null;
		marcaSelectedId			= null;
		inventarioFastViewList	= null;
	}
	
	public void marcaChanged(ValueChangeEvent event){
		//logger.info(">> marcaChanged: old=" + event.getOldValue()+", new="+event.getNewValue());        
		marcaSelectedId			= (Integer)event.getNewValue();
		inventarioFastViewList	= null;
	}
	
	public Sucursal getSucursalObjetivo() {
		//logger.info(">> getSucursalObjetivo: sucursalObjetivo="+sucursalObjetivo);        
		if(sucursalObjetivo == null) {
			sucursalPrincipal = sucursalJPAController.getSucursalPrincipal();
			sucursalObjetivo = sucursalPrincipal;
		}
		return sucursalObjetivo;
	}
	
	public Almacen getAlmacenObjetivo() {
		//logger.info(">> getAlmacenObjetivo: almacenObjetivo="+almacenObjetivo);        
		if(almacenObjetivo == null){			
			Collection<Almacen> almacenCollection = getSucursalObjetivo().getAlmacenCollection();
			for(Almacen a: almacenCollection){
				if(a.getTipoAlmacen() == tipoAlmacen){
					almacenObjetivo = a;
					break;
				}
			}
		}
		return almacenObjetivo;
	}

	public void setIndustriaJPAController(IndustriaJPAController industriaJPAController) {
		this.industriaJPAController = industriaJPAController;
	}

	public void setLineaJPAController(LineaJPAController lineaJPAController) {
		this.lineaJPAController = lineaJPAController;
	}

	public void setMarcaJPAController(MarcaJPAController marcaJPAController) {
		this.marcaJPAController = marcaJPAController;
	}

	public void setSelecionMode(int selecionMode) {
		//logger.debug(">> setSelecionMode: selecionMode="+selecionMode+", this.selecionMode="+this.selecionMode+", aLaVergaSelectionModeSet="+aLaVergaSelectionModeSet);
		if(aLaVergaSelectionModeSet){
			aLaVergaSelectionModeSet=false;
		} else {
			this.selecionMode = selecionMode;
		}
	}

	public int getSelecionMode() {
		//logger.debug(">> getSelecionMode: retunr this.selecionMode="+this.selecionMode);				
		return selecionMode;
	}

	public int getSELECTION_MODE_INDUSTRIA_MARCA() {
		return SELECTION_MODE_INDUSTRIA_MARCA;
	}

	public int getSELECTION_MODE_LINEA_MARCA() {
		return SELECTION_MODE_LINEA_MARCA;
	}

	public Integer getIndustriaSelectedId() {
		return industriaSelectedId;
	}

	public void setIndustriaSelectedId(Integer industriaSelectedId) {
		this.industriaSelectedId = industriaSelectedId;		
	}

	public Integer getLineaSelectedId() {		
		return lineaSelectedId;
	}

	public void setLineaSelectedId(Integer lineaSelectedId) {
		this.lineaSelectedId = lineaSelectedId;
		
	}

	public Integer getMarcaSelectedId() {
		return marcaSelectedId;
	}

	public void setMarcaSelectedId(Integer marcaSelectedId) {
		this.marcaSelectedId = marcaSelectedId;
	}
	
	public String getAlmacenObjetivoDescripcionTipo() {
		return getTipoAlmacenHashTable().get(getAlmacenObjetivo().getTipoAlmacen());
	}

	public Hashtable<Integer, String> getTipoAlmacenHashTable() {
		if(tipoAlmacenHashTable == null){
			tipoAlmacenHashTable = new Hashtable<Integer, String> ();
			
			tipoAlmacenHashTable.put(Constants.ALMACEN_LINEA		, Messages.getLocalizedString("COMMON_ALMACEN_LINEA"));
			tipoAlmacenHashTable.put(Constants.ALMACEN_OPORTUNIDAD	, Messages.getLocalizedString("COMMON_ALMACEN_OPORTUNIDAD"));
			tipoAlmacenHashTable.put(Constants.ALMACEN_REGALIAS		, Messages.getLocalizedString("COMMON_ALMACEN_REGALIAS"));			
		}
		return tipoAlmacenHashTable;
	}
	
	public List<SelectItem> getTipoAlmacenList() {
		if(resultTipoAlmacenList == null){
			
			resultTipoAlmacenList = new ArrayList<SelectItem>();
	
			resultTipoAlmacenList.add(new SelectItem(Constants.ALMACEN_LINEA		, getTipoAlmacenHashTable().get(Constants.ALMACEN_LINEA)));
			resultTipoAlmacenList.add(new SelectItem(Constants.ALMACEN_OPORTUNIDAD	, getTipoAlmacenHashTable().get(Constants.ALMACEN_OPORTUNIDAD)));
			resultTipoAlmacenList.add(new SelectItem(Constants.ALMACEN_REGALIAS		, getTipoAlmacenHashTable().get(Constants.ALMACEN_REGALIAS)));
		}
	    
        return resultTipoAlmacenList;
    }
	
	public String getTipoAlmacenSeleccionado() {
		if(tipoAlmacen > 0){
			return getTipoAlmacenHashTable().get(tipoAlmacen);
		} else{
			return "-";
		}
    }

	public Integer getTipoAlmacen() {
		return tipoAlmacen;
	}

	public void setTipoAlmacen(Integer tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}
			
}
