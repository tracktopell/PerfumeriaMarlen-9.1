/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.controller.PedidoVentaJPAController;
import com.pmarlen.model.dto.PedidoFastView;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEAXX9M
 */
public class PedidosPreVisorMB {

    @Autowired
    private PedidoVentaJPAController pedidoVentaJPAController;
	@Autowired
    private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;

    @Autowired
    private SessionUserMB sessionUserMB;
	
	private List<PedidoVentaEasyView> pedidoVentaEasyViewList;
	private int scrollerPage;
	
	private int numRecShow;
    
	private final Logger logger = LoggerFactory.getLogger(PedidosPreVisorMB.class);

    public PedidosPreVisorMB() {
		numRecShow = 25;
    }
	
	public void updateRecShow(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String numRecShowToSet = context.getExternalContext().getRequestParameterMap().get("numRecShowToSet");

		reinicializarLista();
		
		actualizarLista();

		numRecShow = Integer.parseInt(numRecShowToSet);
	}

	public void reinicializarLista(){
		pedidoVentaEasyViewList = null;
	}

    public List<PedidoVentaEasyView> getPedidoVentaList(){
		if(pedidoVentaEasyViewList == null){
			actualizarLista();
		}
        return pedidoVentaEasyViewList;
    }

    public void setPedidoVentaJPAController(PedidoVentaJPAController pedidoVentaJPAController) {
        this.pedidoVentaJPAController = pedidoVentaJPAController;
    }

	public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
		this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
	}
	

    public void setSessionUserMB(SessionUserMB sessionUserMB) {
        this.sessionUserMB = sessionUserMB;
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

	private void actualizarLista() {
		pedidoVentaEasyViewList = new ArrayList<PedidoVentaEasyView>();
		logger.info("-->>> getPedidoVentaList: before load ?");
		List<PedidoVenta> pedidoVentaList = pedidoVentaJPAController.findPedidoVentaEntities();

		logger.info("-->>> getPedidoVentaList: after load: pedidoVentaList size ="+pedidoVentaList.size());

		for(PedidoVenta pv: pedidoVentaList){
			PedidoVentaEasyView pvev = new PedidoVentaEasyView(pv) ;
			pedidoVentaEasyViewList.add(pvev);
		}
		logger.info("-->>> getPedidoVentaList: NEW strategy");
		try{
			List<PedidoFastView> pedidoFastViewList = pedidoVentaBusinessLogic.findPedidoFastView();
			logger.info("-->>> getPedidoVentaList: pedidoFastViewList size ="+pedidoFastViewList.size());

			for(PedidoFastView pvfv: pedidoFastViewList){
				logger.info("-->>> getPedidoVentaList: pedidoFastView:"+pvfv);
			}
			logger.info("-->>> getPedidoVentaList: END STRATEGY");
		}catch(Exception e){
			logger.error("STRATERGY FAILS:",e);
		}
	}
    
}
