/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.dto.PedidoFastView;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEAXX9M
 */
public class PedidosVentasViewMB {

	@Autowired
	private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;
	@Autowired
	private VerPedidoEnProcesoMB verPedidoEnProcesoMB;
	private List<PedidoFastView> pedidoFastViewList;
	private int scrollerPage;
	private Integer pedidoBuscar;
	private Integer mostrar;
	private int numRecShow;
	private final Logger logger = LoggerFactory.getLogger(PedidosVentasViewMB.class);
	
	public PedidosVentasViewMB() {
		numRecShow = 25;
		mostrar = 25;
	}
	private List descuentosPosiblesList;
	
	public List<SelectItem> getDescuentosPosiblesList() {
		if (descuentosPosiblesList == null) {
			descuentosPosiblesList = new ArrayList<SelectItem>();
			
			descuentosPosiblesList.add(new SelectItem(25, "ÚLTIMOS 25"));
			descuentosPosiblesList.add(new SelectItem(100, "ÚLTIMOS 10"));
			descuentosPosiblesList.add(new SelectItem(200, "ÚLTIMOS 200"));			
			descuentosPosiblesList.add(new SelectItem(0, "TODOS"));
			
		}
		return descuentosPosiblesList;
	}
	
	public Integer getMostrar() {
		return mostrar;
	}
	
	public void setMostrar(Integer mostrar) {
		this.mostrar = mostrar;
	}
	
	public void updateRecShow(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		String numRecShowToSet = context.getExternalContext().getRequestParameterMap().get("numRecShowToSet");
		
		reinicializarLista();
		
		actualizarLista();
		
		numRecShow = Integer.parseInt(numRecShowToSet);
	}
	
	public void reinicializarLista() {
		pedidoFastViewList = null;
		scrollerPage = 1;
	}
	
	public List<PedidoFastView> getPedidoFastViewList() {
		if (pedidoFastViewList == null) {
			actualizarLista();
		}
		return pedidoFastViewList;
	}
	
	public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
		this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
	}
	
	public void setVerPedidoEnProcesoMB(VerPedidoEnProcesoMB verPedidoEnProcesoMB) {
		this.verPedidoEnProcesoMB = verPedidoEnProcesoMB;
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
		if (pedidoFastViewList != null) {
			return pedidoFastViewList.size();
		} else {
			return 0;
		}
	}
	
	private void actualizarLista() {
		
		logger.info("-->>> actualizarLista : before");
		try {
			final List<PedidoFastView> findPedidoFastViewOriginal = pedidoVentaBusinessLogic.findPedidoFastView();
			logger.info("-->>> actualizarLista : after: findPedidoFastViewOriginal size =" + findPedidoFastViewOriginal.size()+", mostrar="+mostrar);
			pedidoFastViewList = null;
			if (mostrar == 0) {
				pedidoFastViewList = findPedidoFastViewOriginal;
			} else {
				pedidoFastViewList = new ArrayList<PedidoFastView>();
				int i=0;
				
				for (PedidoFastView x : findPedidoFastViewOriginal) {
					if(i<mostrar){
						pedidoFastViewList.add(x);
					} else {
						break;
					}
					i++;
				}
			}
		} catch (Exception e) {
			pedidoFastViewList = new ArrayList<PedidoFastView>();
			logger.error("-->>> actualizarLista : STRATERGY FAILS:", e);
		}
	}
	
	public Integer getPedidoBuscar() {
		return pedidoBuscar;
	}
	
	public void setPedidoBuscar(Integer pedidoBuscar) {
		this.pedidoBuscar = pedidoBuscar;
	}
	
	public String buscarPedidoDirecto() {
		
		logger.debug("-->>> buscarPedidoDirecto: this.pedidoBuscar:" + this.pedidoBuscar);
		
		boolean found = false;
		try {
			if (this.pedidoBuscar == null) {
				throw new Exception("Pedido invalido");
			}
			
			for (PedidoFastView pfv : pedidoFastViewList) {
				if (pfv.getPedidoId().intValue() == this.pedidoBuscar.intValue()) {
					found = true;
					break;
				}
			}			
			if (!found) {
				throw new Exception("No existe el Pedido #" + this.pedidoBuscar);
			}
			logger.debug("-->>> buscarPedidoDirecto: Ok let's edit !");
			verPedidoEnProcesoMB.prepararPedidoParaEdicionFromList(this.pedidoBuscar);
			//this.pedidoBuscar = null;
			return "verPedidoEnProceso";
			
		} catch (Exception ex) {
			logger.error("-->>> buscarPedidoDirecto: Exception", ex);
			final FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Buscar Pedido : ",
					ex.getMessage());
			FacesContext.getCurrentInstance().addMessage(
					null,
					facesMessage);
			//this.pedidoBuscar = null;
			return null;
		}		
	}
	
	public void mostrarChanged(ValueChangeEvent vce){
		logger.error("-->>> mostrarChanged: mostrar=:" + mostrar);
		pedidoFastViewList = null;
	}
	
	public String buscarPedidoDirectoForzado(ActionEvent ae) {
		
		logger.error("-->>> buscarPedidoDirectoForzado: this.pedidoBuscar:" + this.pedidoBuscar);
		
		verPedidoEnProcesoMB.prepararPedidoParaEdicionFromList(pedidoBuscar);
		return "verPedidoEnProceso";		
	}
}
