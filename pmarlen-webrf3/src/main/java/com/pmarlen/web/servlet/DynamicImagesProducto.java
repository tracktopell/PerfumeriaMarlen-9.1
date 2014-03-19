/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import org.springframework.web.context.WebApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author VEAXX9M
 */
public class DynamicImagesProducto extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(ContextAndSessionListener.class);

    private String jndi_datasource_name;
    public void init(ServletConfig config) {
        
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OutputStream os = response.getOutputStream();

        String name = request.getParameter("name");
        if(name != null) {
			File f = new File("/home/pmarlenmultimedio/linked_src/"+name);
			if(!f.exists() || !f.canRead()){
				f = new File("/home/pmarlenmultimedio/linked_src/"+name.substring(0, name.indexOf("_"))+"_MULTIMEDIO_DEFAULT.jpg");
			}
			FileInputStream fis = new FileInputStream(f);
			byte[] buffer = new byte[1024*16];
			int r;
			response.setContentType("image/jpeg");
			response.setContentLength((int)f.length());
			while((r=fis.read(buffer, 0, buffer.length)) != 0 ){
				os.write(buffer,0,r);
				os.flush();
			}
			os.close();
			fis.close();
		}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
