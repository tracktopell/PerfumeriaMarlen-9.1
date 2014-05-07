/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.Sucursal;
import com.pmarlen.model.controller.SucursalJPAController;
import com.pmarlen.model.dto.InventarioFastView;
import com.pmarlen.model.dto.MovimientoHistoricoProductoFastView;
import com.pmarlen.web.common.view.messages.Messages;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author VEAXX9M
 */
public class InventariosViewMB {
	private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;
	
	private SucursalJPAController sucursalJPAController;

    private List<InventarioFastView> inventarioFastViewList;
	
	private int scrollerPage;

	private Sucursal sucursalPrincipal;
	
	private Sucursal sucursalObjetivo;
	
	private boolean codigoBuscarEnabled;
	
	private String codigoBuscar;
	
	private Almacen almacenObjetivo;
	
	private Hashtable<Integer,String> tipoAlmacenHashTable;	
	
	private List<SelectItem> resultTipoAlmacenList;

	private Integer tipoAlmacen;

	//private Integer almacenId;
	
	private int numRecShow;
    
	private final Logger logger = LoggerFactory.getLogger(InventariosViewMB.class);

	public boolean getCodigoBuscarEnabled() {
		return codigoBuscarEnabled;
	}

	public void setCodigoBuscarEnabled(boolean codigoBuscarEnabled) {
		this.codigoBuscarEnabled = codigoBuscarEnabled;
	}

    public InventariosViewMB() {
		numRecShow = 25;
		codigoBuscarEnabled=true;
		//almacenId = 1;
		tipoAlmacen = Constants.ALMACEN_LINEA;
    }
	
	public void updateRecShow(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String numRecShowToSet = context.getExternalContext().getRequestParameterMap().get("numRecShowToSet");

		reinicializarLista();
		
		actualizarLista();

		numRecShow = Integer.parseInt(numRecShowToSet);
	}

	public void reinicializarLista(){
		logger.debug(">> reinicializarLista");
		inventarioFastViewList = null;
		scrollerPage = 1;
	}

    public List<InventarioFastView> getInventarioFastViewList(){
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

	private void actualizarLista() {		
		try{
			Integer almacenId = getAlmacenObjetivo().getId();
			logger.debug(">> actualizarLista : almacenId="+almacenId+", codigoBuscarEnabled="+codigoBuscarEnabled+", codigoBuscar="+codigoBuscar);		
			
			if(codigoBuscarEnabled && codigoBuscar != null) {
				logger.debug(">> actualizarLista : \tfind by codigo barras");
				inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastView(almacenId,codigoBuscar);			
				
				if(inventarioFastViewList.size()<1){
					throw new NoSuchElementException("PRODUCTO NO ENCOTRNADO");
				}
				
			} else {
				logger.debug(">> actualizarLista : \tfind nothing !");
				inventarioFastViewList = new ArrayList<InventarioFastView>();
				//inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastView(almacenId);			
			}
			
			logger.debug(">> actualizarLista : after inventarioFastViewList size ="+inventarioFastViewList.size());			
		}catch(Exception e){
			logger.error(">> actualizarLista : ",e);
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al filtrar:", e.getMessage()));
		}
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
	
	public void almacenSelected(ValueChangeEvent event){
		logger.info(">> almacenSelected: old=" + event.getOldValue()+", new="+event.getNewValue());        
		tipoAlmacen = (Integer)event.getNewValue();
		almacenObjetivo        = null;
		inventarioFastViewList = null;
		actualizarLista();			
	}

	public Sucursal getSucursalObjetivo() {
		if(sucursalObjetivo == null) {
			sucursalPrincipal = sucursalJPAController.getSucursalPrincipal();
			sucursalObjetivo = sucursalPrincipal;
		}
		
		return sucursalObjetivo;
	}
	
	/**
	 * @return the almacenObjetivo
	 */
	public Almacen getAlmacenObjetivo() {
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

	public String getCodigoBuscar() {
		return codigoBuscar;
	}

	public void setCodigoBuscar(String codigoBuscar) {
		this.codigoBuscar = codigoBuscar;
	}
	
	public void codigoBuscarEnabledChanged(ActionEvent event){
		logger.debug(">> codigoBuscarEnabledChanged: codigoBuscarEnabled="+codigoBuscarEnabled);
		if(codigoBuscarEnabled==false && inventarioFastViewList.size() == 1) {
			codigoBuscar = null;
			reinicializarLista();
			actualizarLista();		
		}
	}
	
	public void codigoBuscarChanged(ValueChangeEvent event){
		logger.debug(">> codigoBuscarChanged: old="+ event.getOldValue()+", new="+event.getNewValue()+", codigoBuscar="+codigoBuscar);
		codigoBuscar = event.getNewValue().toString();
		refrescarFiltroCodigoBarras();
	}
	
	public void buscarProductoPorCodigo(ActionEvent e) {
		logger.debug(">> buscarProductoPorCodigo: codigoBuscar="+codigoBuscar);
		refrescarFiltroCodigoBarras();
	}
	
	public void precioVentaChanged(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
        String productoCodigoBarras = context.getExternalContext().getRequestParameterMap().get("productoCodigoBarras");
		
		logger.debug(">> precioVentaChanged: productoCodigoBarras="+productoCodigoBarras);
		InventarioFastView ifvUpdated=null;
		for(InventarioFastView ifv: inventarioFastViewList){
			if(ifv.getProductoCodigoBarras().equals(productoCodigoBarras)){
				logger.debug(">> precioVentaChanged: actualizar precioVenta="+ifv.getAlmacenProductoPrecioVenta());		
				ifvUpdated=ifv;
				break;
			}
		}
		
		if(ifvUpdated != null) {
			actualizacionDirectaPrecioVentaAlmacenProducto(ifvUpdated.getProductoCodigoBarras(), almacenObjetivo.getId(), ifvUpdated.getAlmacenProductoPrecioVenta());
		}
	}
	
	private void actualizacionDirectaPrecioVentaAlmacenProducto(String productoCB,int almacenId,double precioVenta) {
		try{
			pedidoVentaBusinessLogic.actualizacionDirectaPrecioVentaAlmacenProducto(productoCB, almacenId, precioVenta);
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualziar precio Venta:", " Se actualizó correctamente el precio de venta, Producto "+productoCB));
		}catch(Exception e){
			logger.error(">> actualizacionDirectaPrecioVentaAlmacenProducto: ",e);
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar:", e.getMessage()));
		}
	}

	private void refrescarFiltroCodigoBarras() {
		logger.debug(">> refrescarFiltroCodigoBarras: codigoBuscar= ->"+codigoBuscar+"<-");
		if(codigoBuscar != null && codigoBuscar.trim().length() >= 5) {			
			reinicializarLista();
			actualizarLista();
		}
	}
	
	public List<MovimientoHistoricoProductoFastView> getMovimientoHistoricoProductoFastView(){
		if(codigoBuscarEnabled && codigoBuscar != null){
			return pedidoVentaBusinessLogic.findMovimientoHistoricoProductoFastView(tipoAlmacen, codigoBuscar);
		} else {
			return null;
		}
	} 

}
