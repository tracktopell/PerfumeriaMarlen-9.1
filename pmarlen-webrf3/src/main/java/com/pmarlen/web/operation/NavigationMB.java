package com.pmarlen.web.operation;

import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.richfaces.component.html.HtmlPanelMenu;
import org.richfaces.component.html.HtmlPanelMenuGroup;
import org.richfaces.component.html.HtmlPanelMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author alfredo
 */
public class NavigationMB {
	
	private LineaMB lineaMB;
	private IndustriaMB industriaMB;
	private MarcaMB marcaMB;
	private ProductoMB productoMB;
	private UsuarioMB  usuarioMB;
	private ClienteMB  clienteMB;
	private ProveedorMB  proveedorMB;
	private SucursalMB  sucursalMB;	
	private PedidosPreVisorMB pedidosPreVisorMB;
	private VerPedidoEnProcesoMB verPedidoEnProcesoMB;	
	private static final Logger logger = LoggerFactory.getLogger(NavigationMB.class);
	private Boolean renderedForMobile;
	private static HtmlPanelMenu htmlPanelMenu;
	private SessionUserMB sessionUserMB;
	private PedidosVentasViewMB pedidosVentasViewMB;
	private InventariosViewMB inventariosViewMB;
	private GaleriaProductosMB galeriaProductosMB;

	private CompraMB compraMB;
	
	public NavigationMB() {
	}

	public Locale getLocale() {
		FacesContext context = FacesContext.getCurrentInstance();
		return getLocale(context);
	}
	private List<Locale> supportedLocales;

	public void setSessionUserMB(SessionUserMB sessionUserMB) {
		this.sessionUserMB = sessionUserMB;
	}
	
	public void setLineaMB(LineaMB lineaMB) {
		this.lineaMB = lineaMB;
	}

	public void setIndustriaMB(IndustriaMB industriaMB) {
		this.industriaMB = industriaMB;
	}

	public void setMarcaMB(MarcaMB marcaMB) {
		this.marcaMB = marcaMB;
	}

	public void setProductoMB(ProductoMB productoMB) {
		this.productoMB = productoMB;
	}

	public void setUsuarioMB(UsuarioMB usuarioMB) {
		this.usuarioMB = usuarioMB;
	}

	public void setClienteMB(ClienteMB clienteMB) {
		this.clienteMB = clienteMB;
	}

	public void setProveedorMB(ProveedorMB proveedorMB) {
		this.proveedorMB = proveedorMB;
	}

	public void setSucursalMB(SucursalMB sucursalMB) {
		this.sucursalMB = sucursalMB;
	}

	public void setPedidosPreVisorMB(PedidosPreVisorMB pedidosPreVisorMB) {
		this.pedidosPreVisorMB = pedidosPreVisorMB;
	}
	
	public void setVerPedidoEnProcesoMB(VerPedidoEnProcesoMB verPedidoEnProcesoMB) {
		this.verPedidoEnProcesoMB = verPedidoEnProcesoMB;
	}

	public void setPedidosVentasViewMB(PedidosVentasViewMB pedidosVentasViewMB) {
		this.pedidosVentasViewMB = pedidosVentasViewMB;
	}

	public void setInventariosViewMB(InventariosViewMB inventariosViewMB) {
		this.inventariosViewMB = inventariosViewMB;
	}

	public void setGaleriaProductosMB(GaleriaProductosMB galeriaProductosMB) {
		this.galeriaProductosMB = galeriaProductosMB;
	}
	
	public void setCompraMB(CompraMB compraMB) {
		this.compraMB = compraMB;
	}
	
	
	public List<Locale> getSupportedLocales() {
		//logger.trace("->getSupportedLocales:");
		if (supportedLocales == null) {
			supportedLocales = new ArrayList<Locale>();
			logger.trace("\t creating supportedLocales list.");

			FacesContext context = FacesContext.getCurrentInstance();
			Application app = context.getApplication();

			final Iterator<Locale> sli = app.getSupportedLocales();
			while (sli.hasNext()) {
				Locale l = sli.next();
				supportedLocales.add(l);
				//logger.trace("\t\t->add local:" + l);
			}
		}
		//logger.trace("->getSupportedLocales: supportedLocales=" + supportedLocales);

		return supportedLocales;
	}

	public String changeLocale() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();

		String localeToChange = context.getExternalContext().getRequestParameterMap().get("localeToChange");

		logger.trace("->changeLocale: localeToChange=" + localeToChange);

		final Iterator<Locale> sli = app.getSupportedLocales();
		while (sli.hasNext()) {
			Locale l = sli.next();
			if (l.toString().equals(localeToChange)) {
				setLocale(context, l);
				logger.trace("\tOk found, changing Locale to :" + l);
				break;
			}
		}
		return null;
	}

	public String getLocaleInfo() {
		FacesContext context = FacesContext.getCurrentInstance();

		Application app = context.getApplication();
		String appBundle = app.getMessageBundle();
		Locale locale = getLocale(context);
		final Iterator<Locale> supportedLocales = app.getSupportedLocales();
		StringBuffer sb = new StringBuffer("{");
		int nl = 0;
		while (supportedLocales.hasNext()) {
			Locale l = supportedLocales.next();
			if (nl > 0) {
				sb.append(", ");

			}
			if (locale.equals(l)) {
				sb.append("*");
			}
			sb.append(l.toString());
			nl++;
		}
		sb.append("}");

		return sb.toString();
	}

	public static Locale getLocale(FacesContext context) {
		Locale locale = null;
		UIViewRoot viewRoot = context.getViewRoot();
		if (viewRoot != null) {
			locale = viewRoot.getLocale();
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}

	public static void setLocale(FacesContext context, Locale locale) {

		UIViewRoot viewRoot = context.getViewRoot();
		if (viewRoot != null) {
			viewRoot.setLocale(locale);
		}

	}

	public boolean getRenderedForMobile() {
		if (renderedForMobile == null) {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
			final boolean androidOperaMiniTest = userAgent.toLowerCase().contains("android") && (userAgent.toLowerCase().contains("opera mini")||userAgent.toLowerCase().contains("like gecko"));
			renderedForMobile = androidOperaMiniTest;
			logger.trace("==>>renderedForMobile=" + renderedForMobile);
		}
		return renderedForMobile.booleanValue();
	}

	public int getNumColumnsLiteral() {
		int nc = -1;
		if (getRenderedForMobile()) {
			nc = 2;
		} else {
			nc = 1;
		}
		return nc;
	}

	public String getColumnClassLiteral() {
		String s = null;

		if (getRenderedForMobile()) {
			s = "columnWidth20 leftAlign topAlign,columnWidth80 centerAlign topAlign";
		} else {
			s = "centerAlign topAlign";
		}
		return s;
	}
	/*
	 public boolean getUseMenuBarLayout(){
	 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	 String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
	 return !userAgent.toLowerCase().contains("android");
	 }	
	
	 public boolean getUsePanelMenuLayout(){
	 ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	 String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");
		
	 return userAgent.toLowerCase().contains("android");
	 }
	 */

	public String home() {
		sessionUserMB.updateLastVisitedPage("home");
		return "home";
	}

	//--------------------------------------------
	public String swapMenuStyle() {
		logger.debug("===>>swapMenuStyle:renderedForMobile=" + renderedForMobile);
		renderedForMobile = !renderedForMobile;
		logger.debug("\t===>>renderedForMobile=" + renderedForMobile);
		return null;
	}

	//--------------------------------------------
	
	public String cambiarModoVenta() {
		sessionUserMB.updateLastVisitedPage("cambiarModoVenta");
		return "cambiarModoVenta";
	}

	public String verProductosXLineaMarca() {
		sessionUserMB.updateLastVisitedPage("verProductosXLineaMarca");
		return "verProductosXLineaMarca";
	}

	public String verProductosXIndustriaMarca() {
		sessionUserMB.updateLastVisitedPage("verProductosXIndustriaMarca");
		return "verProductosXIndustriaMarca";
	}
	

	public String pedidoSimple() {
		sessionUserMB.updateLastVisitedPage("pedidoSimple");
		return "pedidoSimple";
	}

	public String verProductosEnPedidoSimple() {
		sessionUserMB.updateLastVisitedPage("verProductosEnPedidoSimple");
		return "verProductosEnPedidoSimple";
	}

	public String listarPedidosVentas() {
		sessionUserMB.updateLastVisitedPage("listarPedidosVentas");
		pedidosPreVisorMB.reinicializarLista();
		return "listarPedidosVentas";
	}
	
	public String pedidosVentasView() {
		sessionUserMB.updateLastVisitedPage("pedidosVentasView");
		pedidosVentasViewMB.reinicializarLista();
		return "pedidosVentasView";
	}

	public String inventariosView() {
		sessionUserMB.updateLastVisitedPage("inventariosView");
		inventariosViewMB.reinicializarLista();
		return "inventariosView";
	}
	
	public String galeriaProductos() {
		sessionUserMB.updateLastVisitedPage("galeriaProductos");
		galeriaProductosMB.reinicializarLista();
		return "galeriaProductos";
	}


	public String pedidoNuevo() {
		sessionUserMB.updateLastVisitedPage("pedidoNuevo");
		return "pedidoNuevo";
	}
	
	public String compra() {
		sessionUserMB.updateLastVisitedPage("compra");
		return "compra";
	}

	public String pedidoVenta() {
		sessionUserMB.updateLastVisitedPage("pedidoVenta");
		return "pedidoVenta";
	}
	
	public String catalogoLinea() {
		sessionUserMB.updateLastVisitedPage("catalogoLinea");
		this.lineaMB.reinicializarLista();
		this.lineaMB.actualizarLista();		
		return "catalogoLinea";
	}

	public String catalogoIndustria() {
		sessionUserMB.updateLastVisitedPage("catalogoIndustria");
		this.industriaMB.reinicializarLista();
		this.industriaMB.actualizarLista();				
		return "catalogoIndustria";
	}

	public String catalogoMarca() {
		sessionUserMB.updateLastVisitedPage("catalogoMarca");
		this.marcaMB.reinicializarLista();
		this.marcaMB.actualizarLista();
		return "catalogoMarca";
	}
	
	public String catalogoUsuario() {
		sessionUserMB.updateLastVisitedPage("catalogoUsuario");
		this.usuarioMB.reinicializarLista();
		this.usuarioMB.actualizarLista();		
		return "catalogoUsuario";
	}

	public String catalogoProveedor() {
		sessionUserMB.updateLastVisitedPage("catalogoProveedor");
		this.proveedorMB.reinicializarLista();
		this.proveedorMB.actualizarLista();		
		return "catalogoProveedor";
	}
	
	public String catalogoSucursal() {
		sessionUserMB.updateLastVisitedPage("catalogoSucursal");
		this.sucursalMB.reinicializarLista();
		this.sucursalMB.actualizarLista();		
		return "catalogoSucursal";
	}
	
	public String catalogoCliente() {
		sessionUserMB.updateLastVisitedPage("catalogoCliente");
		this.clienteMB.reinicializarLista();
		this.clienteMB.actualizarLista();		
		return "catalogoCliente";
	}
	
	public String catalogoProducto() {
		sessionUserMB.updateLastVisitedPage("catalogoProducto");
		this.productoMB.reinicializarLista();
		this.productoMB.actualizarLista();		
		return "catalogoProducto";
	}

	public String importarProducto() {
		sessionUserMB.updateLastVisitedPage("importarProducto");
		return "importarProducto";
	}

	public String surtirAlmacenPrincipal() {
		sessionUserMB.updateLastVisitedPage("surtirAlmacenPrincipalMB");
		return "surtirAlmacenPrincipal";
	}

	public String editarPedido() {
		sessionUserMB.updateLastVisitedPage("editarPedido");
		return "editarPedido";
	}

	public String activeSessions() {
		sessionUserMB.updateLastVisitedPage("activeSessions");
		return "activeSessions";
	}

	public String surtirSucursalAlmacenPrincipal() {
		sessionUserMB.updateLastVisitedPage("surtirSucursalAlmacenPrincipal");
		//operacionesEnAlmacenesMB.refreshAllProductosEnTransitoDeSucursal();
		return "surtirSucursalAlmacenPrincipal";
	}

	public String surtirSucursalAlmacenPrincipalNuevo() {
		sessionUserMB.updateLastVisitedPage("surtirSucursalAlmacenPrincipalNuevo");
		return "surtirSucursalAlmacenPrincipalNuevo";
	}
	//==========================================================================
	private Document doc;

	public void prepareToParseXMLMenu() {
		ServletContext context = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		File fXmlFile = new File(context.getRealPath("/templates/include/menuBar.xhtml"));
		try {
			logger.trace("prepareToParseXMLMenu -> try to open:" + fXmlFile);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			logger.trace("-> before parse: dBuilder=" + dBuilder);

			BufferedReader brXML = new BufferedReader(new InputStreamReader(new FileInputStream(fXmlFile)));

			ByteArrayOutputStream baosXMLFile = new ByteArrayOutputStream();
			PrintWriter pwXMLFiltered = new PrintWriter(baosXMLFile);
			logger.trace("-> filtering XML");

			String line = null;
			while ((line = brXML.readLine()) != null) {

				if (line.contains("<!DOCTYPE")) {
					line = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
				}
				logger.trace("\t=>filtered:" + line);

				pwXMLFiltered.println(line);
				pwXMLFiltered.flush();
			}
			baosXMLFile.close();
			byte[] byteArrayXMLFiltered = baosXMLFile.toByteArray();

			logger.trace("-> after filtering XML, byteArrayXMLFiltered= " + byteArrayXMLFiltered.length + " bytes");

			InputStream isFile = new ByteArrayInputStream(byteArrayXMLFiltered);

			doc = dBuilder.parse(isFile);
			logger.trace("-> after parse:doc=" + doc);
			doc.getDocumentElement().normalize();
			logger.trace("Root element :" + doc.getDocumentElement().getNodeName());

		} catch (Exception ex) {
			logger.error("prepareToParseXMLMenu", ex);
		}
	}

	public UIComponent getHtmlPanelMenu() {
		return parseAndBuildMenu();
	}

	private UIComponent parseAndBuildMenu() {
		if (htmlPanelMenu == null) {

			prepareToParseXMLMenu();

			htmlPanelMenu = new HtmlPanelMenu();
			logger.trace("-> parseAndBuildMenu, doc=" + doc);
			try {

				NodeList nList = doc.getElementsByTagName("rich:toolBar");
				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					//logger.trace("Current Element :" + nNode.getNodeName());

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;
						NodeList nListx = eElement.getElementsByTagName("rich:dropDownMenu");
						for (int tempx = 0; tempx < nListx.getLength(); tempx++) {

							Node nNodex = nListx.item(tempx);

							//logger.trace("\tCurrent Element("+nNode.getNodeType()+") :" + nNodex.getNodeName());

							if (nNodex.getNodeType() == Node.ELEMENT_NODE && nNodex.getNodeName().equals("rich:dropDownMenu")) {
								LinkedHashMap<String, String> propsDopMenu = getPropsDopMenu(nNodex);

								logger.trace("\t\t rich:dropDownMenu -> " + propsDopMenu);
								HtmlPanelMenuGroup pmg = new HtmlPanelMenuGroup();

								pmg.setValueExpression("label", createVEFor(propsDopMenu.get("label4Menu")));
								pmg.setExpanded(true);

								NodeList childNodes = nNodex.getChildNodes();
								for (int tempxx = 0; tempxx < childNodes.getLength(); tempxx++) {

									Node nNodexx = childNodes.item(tempxx);

									//logger.trace("\t\tCurrent Element("+nNodexx.getNodeType()+") :" + nNodexx.getNodeName());

									if (nNodexx.getNodeName().equals("rich:menuItem")) {
										Element e = (Element) nNodexx;

										logger.trace("\t\t\tOK rich:menuItem -> value=" + e.getAttribute("value") + ", Action=" + e.getAttribute("action"));
										HtmlPanelMenuItem pmi = new HtmlPanelMenuItem();
										//pmi.setLabel(e.getAttribute("value"));
										pmi.setValueExpression("label", createVEFor(e.getAttribute("value")));
										pmi.setActionExpression(createMEFor(e.getAttribute("action")));
										
										pmg.getChildren().add(pmi);
									} else if (nNodexx.getNodeName().equals("rich:menuGroup")) {
										Element ex = (Element) nNodexx;
										
										logger.trace("\t\t\tBUG rich:menuGroup -> value=" + ex.getAttribute("value"));
										HtmlPanelMenuGroup pmgx = new HtmlPanelMenuGroup();
										pmgx.setValueExpression("label", createVEFor(ex.getAttribute("value")));
										pmgx.setExpanded(true);

										NodeList childNodesxx = nNodexx.getChildNodes();
										for (int tempxxx = 0; tempxxx < childNodesxx.getLength(); tempxxx++) {

											Node nNodexxx = childNodesxx.item(tempxxx);

											if (nNodexxx.getNodeName().equals("rich:menuItem")) {
												Element exx = (Element) nNodexxx;

												logger.trace("\t\t\t\tBUG rich:menuItem -> value=" + exx.getAttribute("value") + ", ActioN=" + exx.getAttribute("action"));
												HtmlPanelMenuItem pmix = new HtmlPanelMenuItem();
												//pmix.setLabel(e.getAttribute("value"));
												pmix.setValueExpression("label", createVEFor(exx.getAttribute("value")));
												pmix.setActionExpression(createMEFor(exx.getAttribute("action")));
												pmgx.getChildren().add(pmix);
											}
										}
										pmg.getChildren().add(pmgx);
									}
								}

								htmlPanelMenu.getChildren().add(pmg);
							}
						}
					}
				}
			} catch (Throwable e) {
				logger.error("at reading resource:", e);
			} finally {
				logger.trace("-> after finally ?");
			}
		}
		logger.trace("parseAndBuildMenu:-> return ?");
		return htmlPanelMenu;
	}
	
	/**
	 * @param htmlPanelMenu the htmlPanelMenu to set
	 */
	public void setHtmlPanelMenu(UIComponent htmlPanelMenu) {
		this.htmlPanelMenu =(HtmlPanelMenu) htmlPanelMenu;
	}

	private LinkedHashMap<String, String> getPropsDopMenu(Node node) {
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();

		Element e = (Element) node;

		String rendered = e.getAttribute("rendered");

		if (rendered != null && rendered.length() > 0) {
			result.put("rendered", rendered);
		}
		NodeList cn = e.getChildNodes();
		final int lengthCN = cn.getLength();

		for (int temp = 0; temp < lengthCN; temp++) {

			Node nNode = cn.item(temp);

			//logger.trace("\t\t->Current Element("+nNode.getNodeType()+") ["+lengthCN+"] :" + nNode.getNodeName());
			if (!nNode.getNodeName().equals("f:facet")) {
				continue;
			}
			Element ex = (Element) nNode;

			if (ex.getNodeName().equals("f:facet") && ex.getAttribute("name").equals("label")) {
				NodeList cnx = ex.getChildNodes();
				for (int tempx = 0; tempx < cnx.getLength(); tempx++) {

					Node nNodex = cnx.item(tempx);
					//logger.trace("\t\t\t==>Current Element("+nNodex.getNodeType()+") :" + nNodex.getNodeName());

					if (!nNodex.getNodeName().equals("h:panelGroup")) {
						continue;
					}
					Element exx = (Element) nNodex;

					if (exx.getNodeName().equals("h:panelGroup")) {
						NodeList cnxx = exx.getChildNodes();
						for (int tempxx = 0; tempxx < cnxx.getLength(); tempxx++) {
							Node nNodexx = cnxx.item(tempxx);
							//logger.trace("\t\t\t\t+++>Current Element("+nNodexx.getNodeType()+") :" + nNodexx.getNodeName());

							if (nNodexx.getNodeName().equals("h:outputText")) {
								Element exxx = (Element) nNodexx;

								String label4Menu = exxx.getAttribute("value");

								if (label4Menu != null && label4Menu.length() > 0) {
									result.put("label4Menu", label4Menu);
								}
							}

						}

					}

				}
			}
		}

		return result;
	}

	private ValueExpression createVEFor(String exp) {
		ValueExpression valueExpressionLabel = FacesContext.getCurrentInstance().
				getApplication().getExpressionFactory().createValueExpression(
				FacesContext.getCurrentInstance().getELContext(),
				exp, String.class);
		return valueExpressionLabel;
	}

	private MethodExpression createMEFor(String exp) {
		MethodExpression methodExpression = FacesContext.getCurrentInstance().getApplication().
				getExpressionFactory().createMethodExpression(
				FacesContext.getCurrentInstance().getELContext(),
				exp,
				String.class,
				new Class[0]);
		return methodExpression;
	}
}
