/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import com.pmarlen.businesslogic.reports.GeneradorDeFactura;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.controller.PedidoVentaJPAController;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author alfredo
 */
public class GenerarXMLCFD extends HttpServlet {

	/**
	 * Processes requests for both HTTP
	 * <code>GET</code> and
	 * <code>POST</code> methods.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.err.println("-->>GenerarXMLCFD servlet: RequestURI="+request.getRequestURI());
		ServletOutputStream outputStream = null;
		String pedidoId = null;
		Integer pedidoVentaId = null;
		
		try {
            //                /docs/xml/
			//                /docs/xml/CFD_PerfumeriaMarlen_No_###.xml			
			// /pmarlen-webrf3/docs/xml/CFD_PerfumeriaMarlen_No_316.xml
			// 0---------1---------2---------3---------4---------5-----
			String requestURI = request.getRequestURI();
			//String requestURIByPaths[] = requestURI.split("/");
			int pedidoIdIndex     = requestURI.lastIndexOf("No_")+3;
			int xmlIndex          = requestURI.lastIndexOf(".xml");
			System.err.println("-->>GenerarXMLCFD servlet: pedidoIdIndex="+pedidoIdIndex+", xmlIndex="+xmlIndex);
			
			if(xmlIndex > pedidoIdIndex) {
				pedidoId = requestURI.substring(pedidoIdIndex,xmlIndex);
				pedidoVentaId = Integer.parseInt(pedidoId);				
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return;
			}
			
			System.err.println("-->>GenerarXMLCFD servlet: afterParse: pedidoVentaId="+pedidoVentaId);
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			System.err.println("-->>GenerarXMLCFD servlet: before find Beans in SpringContext");
			
			PedidoVenta pedidoVentaEncontrado = null;
			PedidoVentaJPAController pedidoVentaJPAController = (PedidoVentaJPAController)context.getBean("pedidoVentaJPAController");
			System.err.println("-->>GenerarXMLCFD servlet: pedidoVentaJPAController="+pedidoVentaJPAController);
			
			pedidoVentaEncontrado = pedidoVentaJPAController.findPedidoVenta(pedidoVentaId);
			
			final String xml = pedidoVentaEncontrado.getCfdVenta().getContenidoOriginalXml();
			System.err.println("-->>OK writing XML : "+xml.length()+" bytes");
			
			response.setContentType("text/xml");
			
			outputStream = response.getOutputStream();
			
			outputStream.write(xml.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch(Exception ex){
			response.sendError(400);
			outputStream.close();
			
		} finally {			
			
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP
	 * <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP
	 * <code>POST</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
