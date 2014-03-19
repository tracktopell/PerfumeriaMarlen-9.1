/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.servlet;

import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.ProductoJPAController;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author alfredo
 */
public class ConsultaPrecio extends HttpServlet {

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
		String productoCodigoBarras  = request.getParameter("cb");
		int almacenObjetivoId = 1;
		if(productoCodigoBarras == null){
			productoCodigoBarras = "";
		}
			
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			Producto prod = null;
			double   precioVenta = 0.0;	
			if(productoCodigoBarras.length()>0){
				ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
				System.err.println("-->>ConsultaPrecio servlet: before find Beans in SpringContext");

				ProductoJPAController productoJPAController = (ProductoJPAController)context.getBean("productoJPAController");
				try{
					System.err.println("-->>ConsultaPrecio servlet: before search producto");

					prod = productoJPAController.findEntityByReadableProperty(productoCodigoBarras);

					System.err.println("-->>ConsultaPrecio servlet: producto found:"+prod);
					final Collection<AlmacenProducto> almacenProductoCollection = prod.getAlmacenProductoCollection();
					for(AlmacenProducto ap: almacenProductoCollection){
						System.err.println("-->>ConsultaPrecio servlet:\t almacenProducto.getAlmacen     ="+ap.getAlmacen());
						System.err.println("-->>ConsultaPrecio servlet:\t almacenProducto.getPrecioVenta ="+ap.getPrecioVenta());
						if(ap.getAlmacen().getId().intValue() == almacenObjetivoId) {
							precioVenta = ap.getPrecioVenta();
						}
					}
				}catch(Exception ex){
					ex.printStackTrace(System.err);
				}
			}
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Perfumeria Marlen - ConsultaPrecio</title>");			
			out.println("</head>");
			out.println("<body>");
			if(prod != null){
				DecimalFormat df =  new DecimalFormat("###,###,##0.00");
				//out.println("<h3>producto: ["+prod.getCodigoBarras()+"]" + prod.getNombre() + "/"+prod.getPresentacion()+", $ "+df.format(precioVenta)+"</h3>");
				out.println("<table width='100%'>");
				out.println("<tr><td align='right'>CODIGO: </td><td>"+prod.getCodigoBarras()+"</td></tr>");
				out.println("<tr><td align='right'>LINEA: </td><td>"+prod.getMarca().getLinea().getNombre()+"</td></tr>");
				out.println("<tr><td align='right'>MARCA: </td><td>"+prod.getMarca().getNombre()+"</td></tr>");
				out.println("<tr><td align='right'>INDUSTRIA: </td><td>"+prod.getMarca().getIndustria().getNombre()+"</td></tr>");
				if(prod.getAbrebiatura() != null) {
					out.println("<tr><td align='right'>ABREB: </td><td>"+prod.getAbrebiatura()+"</td></tr>");
				}
				out.println("<tr><td align='right'>NOMBRE: </td><td>"+prod.getNombre()+"</td></tr>");
				out.println("<tr><td align='right'>PRESENTACION: </td><td>"+prod.getPresentacion()+"</td></tr>");
				out.println("<tr><td align='right'>CONTENIDO: </td><td>"+prod.getContenido()+ " "+prod.getUnidadMedida()+"</td></tr>");
				out.println("<tr><td align='right'>UNIDADES x CAJA: </td><td>"+prod.getUnidadesPorCaja()+"</td></tr>");
				out.println("<tr><td align='right'>PRECIO VENTA: </td><td>$ "+df.format(precioVenta)+"</td></tr>");				
				out.println("</table>");
			} else {
				out.println("<h3>PRODUCTO NO ENCONTRADO :  CODIGO DE BARRAS BUSCADO:"+productoCodigoBarras+"</h3>");
			}
			out.println("</body>");
			out.println("</html>");
		} finally {
			out.close();
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
