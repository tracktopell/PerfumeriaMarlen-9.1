/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import com.pmarlen.web.model.FileUploaded;
import com.pmarlen.web.model.ImageIconGenerator;
import com.pmarlen.web.operation.GaleriaProductosAsignarFotosMB;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alfredo
 */
public class UploadedFileServlet extends HttpServlet {

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
		try {	
			String requestURI = request.getRequestURI();
			String requestURIByPaths[] = requestURI.split("/");
			boolean performanceIcon = true;
			int minSize = 40;
			int maxSize = 100;
			System.err.println("-->>UploadedFileServlet:requestURIByPaths="+Arrays.asList(requestURIByPaths));

			GaleriaProductosAsignarFotosMB galeriaProductosAsignarFotosMB = (GaleriaProductosAsignarFotosMB) request.getSession().getAttribute("galeriaProductosAsignarFotosMB");
			System.err.println("-->>AbrirPedidoVenta: from session galeriaProductosAsignarFotosMB="+galeriaProductosAsignarFotosMB);
			if(galeriaProductosAsignarFotosMB != null) {
				
				String fileUploadedId = requestURIByPaths[requestURIByPaths.length-1];
				System.err.println("-->>AbrirPedidoVenta: getFileUploaded: fileUploadedId="+fileUploadedId);
				
				final FileUploaded fileUploaded = galeriaProductosAsignarFotosMB.getFileUploaded(fileUploadedId);
				InputStream is = null;
				if(fileUploaded != null) {
					final ServletOutputStream outputStream = response.getOutputStream();
				
					final ImageIconGenerator imageIconGenerator = new ImageIconGenerator();
					byte[] newIconImageByes = null;
					final String pathDownloaded4Icon = fileUploaded.getPathDownloaded()+"_ICON"+minSize;
					File iconFile = new File(pathDownloaded4Icon);
					int iconifedZize=0;
					if(!iconFile.exists()){
						newIconImageByes = imageIconGenerator.writeScaledJpegImageFrom(fileUploaded.getPathDownloaded(), minSize);
						iconifedZize = newIconImageByes.length;
						FileOutputStream fos = new FileOutputStream(iconFile);
						fos.write(newIconImageByes);
						fos.flush();
						fos.close();
						ByteArrayInputStream bais = new ByteArrayInputStream(newIconImageByes);
						is = bais;
						System.err.println("-->>AbrirPedidoVenta: iconified to size:"+newIconImageByes.length);

					} else {
						iconifedZize = (int)iconFile.length();
						FileInputStream fis =  new FileInputStream(iconFile);
						System.err.println("-->>AbrirPedidoVenta: file opened");

						is = fis;
					}
					
					response.setContentLength(iconifedZize);
					response.setContentType(fileUploaded.getMime());
					response.setStatus(HttpServletResponse.SC_OK);
					
					int r = -1;
					byte[] buffer = new byte[1024*256];
					while((r=is.read(buffer, 0, buffer.length)) != -1){
						outputStream.write(buffer,0,r);
						outputStream.flush();
					}
					//outputStream.write(fileUploaded.getData());
					//outputStream.flush();
					outputStream.close();
					is.close();
					System.err.println("-->>AbrirPedidoVenta: getFileUploaded: OK, dispatched !");
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				}
			}
			
		} catch(Exception e){		
			e.printStackTrace(System.err);
		}finally {			
			
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
