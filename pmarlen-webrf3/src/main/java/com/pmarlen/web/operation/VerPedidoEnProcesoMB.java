/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.AlmacenProductoDemanda;
import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.businesslogic.PedidoVentaEnDemanda;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.MetodoDePago;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Sucursal;
import com.pmarlen.model.controller.ClienteJPAController;
import com.pmarlen.model.controller.IndustriaJPAController;
import com.pmarlen.model.controller.FormaDePagoJPAController;
import com.pmarlen.model.controller.LineaJPAController;
import com.pmarlen.model.controller.MarcaJPAController;
import com.pmarlen.model.controller.MetodoDePagoJPAController;
import com.pmarlen.model.controller.PedidoVentaEstadoJPAController;
import com.pmarlen.model.controller.PedidoVentaJPAController;
import com.pmarlen.model.controller.ProductoJPAController;
import com.pmarlen.model.controller.SucursalJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import static com.pmarlen.web.operation.PedidoNuevoMB.sucursalPrincipal;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.TreeSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VEAXX9M
 */
public class VerPedidoEnProcesoMB {

	private ProductoJPAController productoJPAController;
	private SucursalJPAController sucursalJPAController;
	private MarcaJPAController marcaJPAController;
	private IndustriaJPAController industriaJPAController;
	private LineaJPAController lineaJPAController;
	private ClienteJPAController clienteJPAController;
	private FormaDePagoJPAController formaDePagoJPAController;
	private MetodoDePagoJPAController metodoDePagoJPAController;
	private PedidoVentaJPAController pedidoVentaJPAController;
	private PedidoVentaEstadoJPAController pedidoVentaEstadoJPAController;
	private SessionUserMB sessionUserMB;
	private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;
	private LinkedHashMap<String, PedidoVentaDetalleWrapper> pedidoVentaDetalleMap;
	private PedidoVenta pedidoVenta;
	private PedidoVentaDetalleWrapper detalleVentaPedidoSeleccionado;
	private PedidoVentaEstado pedidoVentaEstado;
	private Collection<PedidoVentaEstado> pedidoVentaEstadoList;
	private static PedidoVentaEstadoComparator localPedidoVentaEstadoComparator;
	private String nombreDescripcion;
	private String codigoBuscar;
	private Producto productoEncontrado;
	private String productoCBSelected;
	private List<SelectItem> productoConNombreDescripcion;
	private Integer clienteId;
	private Integer formaDePagoId;
	private Integer metodoDePagoId;
	private final Logger logger = LoggerFactory.getLogger(VerPedidoEnProcesoMB.class);
	private Almacen almacenObjetivo;
	private int modoVenta;
	private int descuentoCalculado;
	private int descuentoEspecial;
	private List<SelectItem> descuentosPosiblesList;
	private List<AlmacenProducto> listAlmacenProductoBuscar;
	private Integer cantidadAgregar;
	private Integer cantidadCBAgregar;
	private String productoCBPattern = "^[0-9]{4,16}$";

	/**
	 * @return the descuentoCalculado
	 */
	public int getDescuentoCalculado() {
		return descuentoCalculado;
	}

	public List<SelectItem> getDescuentosPosiblesList() {
		if (descuentosPosiblesList == null) {
			descuentosPosiblesList = new ArrayList<SelectItem>();

			descuentosPosiblesList.add(new SelectItem(0, "SIN DESCUENTO"));
			descuentosPosiblesList.add(new SelectItem(2, "- 2%"));
			descuentosPosiblesList.add(new SelectItem(5, "- 5%"));
			descuentosPosiblesList.add(new SelectItem(7, "- 7%"));
			descuentosPosiblesList.add(new SelectItem(10, "- 10%"));
		}
		return descuentosPosiblesList;
	}
	private Hashtable<Integer, String> tipoAlmacenHashTable;
	private List<SelectItem> resultTipoAlmacenList;

	public Hashtable<Integer, String> getTipoAlmacenHashTable() {
		if (tipoAlmacenHashTable == null) {
			tipoAlmacenHashTable = new Hashtable<Integer, String>();

			tipoAlmacenHashTable.put(Constants.ALMACEN_LINEA, Messages.getLocalizedString("COMMON_ALMACEN_LINEA"));
			tipoAlmacenHashTable.put(Constants.ALMACEN_OPORTUNIDAD, Messages.getLocalizedString("COMMON_ALMACEN_OPORTUNIDAD"));
			tipoAlmacenHashTable.put(Constants.ALMACEN_REGALIAS, Messages.getLocalizedString("COMMON_ALMACEN_REGALIAS"));
		}
		return tipoAlmacenHashTable;
	}

	public List<SelectItem> getTipoAlmacenList() {
		if (resultTipoAlmacenList == null) {

			resultTipoAlmacenList = new ArrayList<SelectItem>();

			resultTipoAlmacenList.add(new SelectItem(Constants.ALMACEN_LINEA, getTipoAlmacenHashTable().get(Constants.ALMACEN_LINEA)));
			resultTipoAlmacenList.add(new SelectItem(Constants.ALMACEN_OPORTUNIDAD, getTipoAlmacenHashTable().get(Constants.ALMACEN_OPORTUNIDAD)));
			resultTipoAlmacenList.add(new SelectItem(Constants.ALMACEN_REGALIAS, getTipoAlmacenHashTable().get(Constants.ALMACEN_REGALIAS)));
		}

		return resultTipoAlmacenList;
	}

	public String getTipoAlmacenSeleccionado() {
		if (modoVenta > 0) {
			return getTipoAlmacenHashTable().get(modoVenta);
		} else {
			return "-";
		}
	}
	static Sucursal sucursalPrincipal;

	public void almacenSelected(ValueChangeEvent event) {
		logger.info("## >> * almacenSelected: old=" + event.getOldValue() + ", new=" + event.getNewValue());
		modoVenta = (Integer) event.getNewValue();
		actualizarAlmacenObjetivoDesdeModoVenta();
		listAlmacenProductoBuscar = null;
	}

	public void descuentosPosiblesListChanged(ValueChangeEvent e) {
		Integer newValue = (Integer) e.getNewValue();
		logger.debug("## >> descuentosPosiblesListChanged: newValue=" + newValue);
		this.descuentoEspecial = newValue;
	}

	public VerPedidoEnProcesoMB() {
		reiniciarPedido();
	}

	private void reiniciarPedido() {
		detalleVentaPedidoSeleccionado = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
		//pedidoVentaDetalleList = new ArrayList<PedidoVentaDetalleWrapper>();
		pedidoVentaDetalleMap = new LinkedHashMap<String, PedidoVentaDetalleWrapper>();
		pedidoVenta = new PedidoVenta();
		productoConNombreDescripcion = new ArrayList<SelectItem>();
		descuentoCalculado = 0;
		descuentoEspecial = 0;
		modoVenta = 1;
		clienteId = null;
		formaDePagoId = null;
		metodoDePagoId = null;
		descuentoEspecial = 0;
		almacenObjetivo = null;
		listAlmacenProductoBuscar = null;
		cantidadCBAgregar = 1;
		//actualizarAlmacenObjetivoDesdeModoVenta();
	}

	public void prepararPedidoParaEdicionFromList(Integer pedidoVentaId) {
		logger.debug("## >> prepararPedidoParaEdicionFromList: pedidoVentaId=" + pedidoVentaId);
		cargarPadidoVenta(pedidoVentaId);
	}

	public void prepararPedidoParaEdicion(Integer pedidoVentaId) {
		logger.debug("## >> prepararPedidoParaEdicion(int): paramPedidoVentaId=" + pedidoVentaId);
		cargarPadidoVenta(pedidoVentaId);
	}

	public String prepararPedidoParaEdicion() {

		FacesContext context = FacesContext.getCurrentInstance();
		String paramPedidoVentaId = context.getExternalContext().getRequestParameterMap().get("pedidoVentaId");
		Integer pedidoVentaId = Integer.parseInt(paramPedidoVentaId);
		logger.debug("## >> prepararPedidoParaEdicion: paramPedidoVentaId=" + paramPedidoVentaId);
		cargarPadidoVenta(pedidoVentaId);

		return "verPedidoEnProceso";

	}

	public void cargarPadidoVenta(Integer pedidoVentaId) {

		logger.debug("## >> cargarPadidoVenta: pedidoVentaId=" + pedidoVentaId);

		pedidoVenta = pedidoVentaJPAController.findPedidoVenta(pedidoVentaId);
		logger.debug("## >> cargarPadidoVenta: pedidoVenta=" + pedidoVenta);


		detalleVentaPedidoSeleccionado = null;
		pedidoVentaDetalleMap = new LinkedHashMap<String, PedidoVentaDetalleWrapper>();
		almacenObjetivo = pedidoVenta.getAlmacen();
		logger.debug("## >> cargarPadidoVenta: almacenObjetivo=" + almacenObjetivo);
		pedidoVentaEstado = getLastPedidoVentaEstado();
		logger.debug("## >> cargarPadidoVenta: pedidoVentaEstado=" + pedidoVentaEstado);
		pedidoVentaEstadoList = getPedidoVentaEstadoListInOrder();

		productoConNombreDescripcion = new ArrayList<SelectItem>();
		descuentoCalculado = pedidoVenta.getPorcentajeDescuentoCalculado() != null ? pedidoVenta.getPorcentajeDescuentoCalculado().intValue() : 0;
		descuentoEspecial = pedidoVenta.getPorcentajeDescuentoExtra() != null ? pedidoVenta.getPorcentajeDescuentoExtra().intValue() : 0;
		clienteId = pedidoVenta.getCliente().getId();
		formaDePagoId = pedidoVenta.getFormaDePago().getId();
		metodoDePagoId = pedidoVenta.getMetodoDePago().getId();
		final Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVenta.getPedidoVentaDetalleCollection();
		agregarProductosADetalle(pedidoVentaDetalleCollection);
		descuentoEspecial = pedidoVenta.getPorcentajeDescuentoExtra();

		listAlmacenProductoBuscar = null;
	}

	public void modoVentaChanged(ValueChangeEvent e) {
		logger.debug("## >> modoVentaChanged: " + e.getOldValue() + " -->> " + e.getNewValue());
	}

	public void modoVentaChangedAction(ActionEvent e) {
		logger.debug("## >> modoVentaChangedAction: " + modoVenta);
	}

	public String modoVentaSaved() {
		logger.debug("## >> modoVentaSaved: modoVenta=" + modoVenta);
		actualizarAlmacenObjetivoDesdeModoVenta();

		return "modoVentaSaved";
	}

	public void buscarProductoPorCodigo(ActionEvent e) {
		logger.debug("## >> * buscarProductoPorCodigo: productoSelected=" + codigoBuscar);

		productoEncontrado = productoJPAController.findEntityByReadableProperty(codigoBuscar);
		if (productoEncontrado != null) {

			agregarProductoADetalle(productoEncontrado, 1);
			productoEncontrado = null;
			productoConNombreDescripcion = new ArrayList<SelectItem>();
			nombreDescripcion = null;
		} else {
			logger.debug("## >> no necontrado:" + codigoBuscar);
		}
		codigoBuscar = "";
	}

	public void buscarNProductoPorCodigo(ActionEvent e) {
		logger.debug("## >> buscarNProductoPorCodigo: productoSelected=" + codigoBuscar + ", cantidadCBAgregar =" + cantidadCBAgregar);

		if (codigoBuscar == null || !codigoBuscar.matches(productoCBPattern)) {
			logger.debug("## >> buscarNProductoPorCodigo: no es un codigo valido ");
			codigoBuscar = "";
			return;
		}

		try {
			productoEncontrado = productoJPAController.findEntityByReadableProperty(codigoBuscar);
			if (productoEncontrado != null) {

				agregarProductoADetalle(productoEncontrado, cantidadCBAgregar);
				productoEncontrado = null;
				productoConNombreDescripcion = new ArrayList<SelectItem>();
				nombreDescripcion = null;
			} else {
				logger.debug("## >> no necontrado:" + codigoBuscar);
			}
		} catch (NoResultException nre) {
			final FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Agregar Producto : ",
					"No existe el producto con Código de Barras: " + codigoBuscar);
			FacesContext.getCurrentInstance().addMessage(
					null,
					fm);
		}
		codigoBuscar = "";
	}

	public void _agregarProductoBuscado(ActionEvent e) {
		logger.debug("## >> agregarProductoBuscado: codigoBuscar=" + codigoBuscar + ", cantidadAgregar=" + cantidadAgregar);

		Producto p = productoJPAController.findEntityByReadableProperty(codigoBuscar);

		agregarProductoADetalle(p, 1);
		productoCBSelected = null;
		productoConNombreDescripcion = new ArrayList<SelectItem>();
		nombreDescripcion = null;
	}

	public void agregarNProductoBuscado(ActionEvent e) {
		logger.debug("## >> agregarNProductoBuscado: productoCBSelected=" + productoCBSelected + ", cantidadAgregar=" + cantidadAgregar);
		Producto p = productoJPAController.findEntityByReadableProperty(productoCBSelected);
		agregarProductoADetalle(p, cantidadAgregar);
		productoCBSelected = null;
		productoConNombreDescripcion = new ArrayList<SelectItem>();
		nombreDescripcion = null;
	}

	private void agregarProductoADetalle(Producto productoAgregar, int cantidad) {
		PedidoVentaDetalleWrapper detalleVentaPedidoAgregar = pedidoVentaDetalleMap.get(productoAgregar.getCodigoBarras());
		logger.debug("## >> agregarProductoADetalleMap: productoAgregar=" + productoAgregar.getCodigoBarras());
		try {
			if (detalleVentaPedidoAgregar != null) {
				if (detalleVentaPedidoAgregar.getDetalleVentaPedido().getCantidad() + cantidad > detalleVentaPedidoAgregar.getAlmacenProductoDemanda().getCantidadActual()) {
					logger.warn("## >> agregarProductoADetalleMap: Cantidad Exedida, no se agregara");

					throw new ValidatorException(
							new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Agregar Producto : ",
							"Excede la existencia en Almacén, se agregará pero no se podra surtir hasta que haya suficiente"));
				} else {
					final int nuevoTotal = detalleVentaPedidoAgregar.getDetalleVentaPedido().getCantidad() + cantidad;

					FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Agregar Producto : ",
							"OK, actualizado Producto :" + codigoBuscar + ", cantidad =" + detalleVentaPedidoAgregar.getDetalleVentaPedido().getCantidad() + " + " + cantidad + " = " + nuevoTotal);
					FacesContext.getCurrentInstance().addMessage(
							null,
							fm);
					detalleVentaPedidoAgregar.getDetalleVentaPedido().setCantidad(nuevoTotal);
				}
			} else {
				detalleVentaPedidoAgregar = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
				detalleVentaPedidoAgregar.getDetalleVentaPedido().setCantidad(cantidad);
				
				int cantMaxAlmacen = 0;
				double precioObjetivo = 0.0;
				
				Collection<AlmacenProducto> almacenProductoCollection = productoAgregar.getAlmacenProductoCollection();

				for (AlmacenProducto almacenProducto : almacenProductoCollection) {
					if (almacenProducto.getAlmacen().getId().intValue() == getAlmacenObjetivo().getId().intValue()) {
						precioObjetivo = almacenProducto.getPrecioVenta();
						cantMaxAlmacen = almacenProducto.getCantidadActual();
					}
				}
				 
				if (listAlmacenProductoBuscar == null) {
					getListAlmacenProductoBuscar();
				}
				AlmacenProductoDemanda prodEnDemanda = new AlmacenProductoDemanda();
				prodEnDemanda.setCantidadActual(cantMaxAlmacen);
				detalleVentaPedidoAgregar.setAlmacenProductoDemanda(prodEnDemanda);
				
				detalleVentaPedidoAgregar.getDetalleVentaPedido().setProducto(productoAgregar);
				detalleVentaPedidoAgregar.getDetalleVentaPedido().setPrecioVenta(precioObjetivo);

				pedidoVentaDetalleMap.put(productoAgregar.getCodigoBarras(), detalleVentaPedidoAgregar);

				if (cantidad > cantMaxAlmacen) {
					logger.warn("## >> agregarProductoADetalleMap: Cantidad Exedida, No hay existencia en Almacenes");
					throw new ValidatorException(
							new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Agregar Producto : ",
							"Excede la existencia en Almacén, se agregará pero no se podra surtir hasta que haya suficiente"));
				} else {
					FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Agregar Producto : ",
							"OK, agregado Producto :" + codigoBuscar + ", cantidad=" + cantidad);
					FacesContext.getCurrentInstance().addMessage(
							null,
							fm);
				}
				logger.debug("## >> agregarProductoADetalle: \t Ok, Add new");
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
		} finally {
			cantidadAgregar = 1;
			cantidadCBAgregar = 1;
		}
	}

	private void agregarProductosADetalle(Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection) {
		logger.debug("## >> agregarProductosADetalle: size="+pedidoVentaDetalleCollection.size());
		for (PedidoVentaDetalle pvdAgregar : pedidoVentaDetalleCollection) {
			Producto productoAgregar = pvdAgregar.getProducto();
			int cantidad = pvdAgregar.getCantidad();

			PedidoVentaDetalleWrapper detalleVentaPedidoAgregar = pedidoVentaDetalleMap.get(productoAgregar.getCodigoBarras());
			logger.debug("\t## >> agregarProductosADetalle: productoAgregar=" +cantidad+" x"+productoAgregar.getCodigoBarras()+" $"+pvdAgregar.getPrecioVenta());
			if (detalleVentaPedidoAgregar != null) {
				if (detalleVentaPedidoAgregar.getDetalleVentaPedido().getCantidad() + cantidad > detalleVentaPedidoAgregar.getAlmacenProductoDemanda().getCantidadActual()) {
					logger.warn("\t## >> agregarProductosADetalle: Cantidad Exedida, no se agregara");
					/*
					 throw new ValidatorException(
					 new FacesMessage(FacesMessage.SEVERITY_WARN,
					 "Agregar Producto : ",
					 "Excede la existencia en Almacén, se agregará pero no se podra surtir hasta que haya suficiente"));
					 */
				} else {
					final int nuevoTotal = detalleVentaPedidoAgregar.getDetalleVentaPedido().getCantidad() + cantidad;
					/*
					 FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
					 "Agregar Producto : ",
					 "OK, actualizado Producto :" + codigoBuscar + ", cantidad =" + detalleVentaPedidoAgregar.getDetalleVentaPedido().getCantidad() + " + " + cantidad + " = " + nuevoTotal);
					 FacesContext.getCurrentInstance().addMessage(
					 null,
					 fm);
					 */
					detalleVentaPedidoAgregar.getDetalleVentaPedido().setCantidad(nuevoTotal);
				}
			} else {
				detalleVentaPedidoAgregar = new PedidoVentaDetalleWrapper(pvdAgregar);
				detalleVentaPedidoAgregar.getDetalleVentaPedido().setCantidad(cantidad);

				if (listAlmacenProductoBuscar == null) {
					getListAlmacenProductoBuscar();
				}
				AlmacenProductoDemanda prodEnDemanda = new AlmacenProductoDemanda();

				detalleVentaPedidoAgregar.setAlmacenProductoDemanda(prodEnDemanda);
				detalleVentaPedidoAgregar.getDetalleVentaPedido().setProducto(productoAgregar);
				
				pedidoVentaDetalleMap.put(productoAgregar.getCodigoBarras(), detalleVentaPedidoAgregar);

				logger.debug("\t## >> agregarProductoADetalle: \t Ok, Add new");
			}

		}
		logger.debug("## >> agregarProductoADetalle: END");
	}

	public int getPedidoVentaActualSize() {
		if (pedidoVentaDetalleMap == null) {
			pedidoVentaDetalleMap = new LinkedHashMap<String, PedidoVentaDetalleWrapper>();
		}
		return pedidoVentaDetalleMap.size();
	}
	
	public String actualizarPedido() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		logger.debug("========================================================>>");
		logger.debug("-->>actualizarPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {
				pedidoVenta.setCliente(new Cliente(clienteId));
				pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
				pedidoVenta.setMetodoDePago(new MetodoDePago(metodoDePagoId));
				//pedidoVenta.setUsuario(sessionUserMB.getUsuarioAuthenticated());				
				pedidoVenta.setFactoriva(LogicaFinaciera.getImpuestoIVA());

				Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();

				for (PedidoVentaDetalleWrapper pvdw : pedidoVentaDetalleMap.values()) {
					pedidoVentaDetalleCollection.add(pvdw.getDetalleVentaPedido());
				}
				for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
					logger.debug("\t==>>pedidoVentaDetalleCollection:" + pvd.getCantidad() + " x " + pvd.getProducto());
				}
				pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);
				pedidoVenta.setPorcentajeDescuentoCalculado(descuentoCalculado);
				pedidoVenta.setPorcentajeDescuentoExtra(descuentoEspecial);
				double descuentoAplicar = descuentoCalculado / 100.0 + descuentoEspecial / 100.0;
				pedidoVenta.setDescuentoAplicado(descuentoAplicar);

				pedidoVentaBusinessLogic.actualizarPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK sincronizarPedido =======================");
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Actualizar : ",
						"Se guardo correctamente el Pedido"));

				//return "pedidoActualizado";
				return null;
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
				cargarPadidoVenta(pedidoVenta.getId());
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String confirmarPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>confirmarPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {
				pedidoVenta.setCliente(new Cliente(clienteId));
				pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
				pedidoVenta.setMetodoDePago(new MetodoDePago(metodoDePagoId));
				pedidoVenta.setUsuario(sessionUserMB.getUsuarioAuthenticated());
				pedidoVenta.setFactoriva(LogicaFinaciera.getImpuestoIVA());
				pedidoVenta.setAlmacen(almacenObjetivo);
				Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();

				for (PedidoVentaDetalleWrapper pvdw : pedidoVentaDetalleMap.values()) {
					pedidoVentaDetalleCollection.add(pvdw.getDetalleVentaPedido());
				}
				for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
					logger.debug("\t==>>pedidoVentaDetalleCollection:" + pvd.getCantidad() + " x " + pvd.getProducto().getId() + "[" + pvd.getProducto().getCodigoBarras() + "]");
				}
				pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);
				pedidoVenta.setPorcentajeDescuentoCalculado(descuentoCalculado);
				pedidoVenta.setPorcentajeDescuentoExtra(descuentoEspecial);
				double descuentoAplicar = descuentoCalculado / 100.0 + descuentoEspecial / 100.0;
				pedidoVenta.setDescuentoAplicado(descuentoAplicar);

				pedidoVenta = pedidoVentaBusinessLogic.crearPedidoCapturado(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK crearPedidoCapturado =======================");
				pedidoVentaBusinessLogic.sincronizarPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK sincronizarPedido =======================");
				return "pedidoCreado";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getClass().toString() + " : ", ex.getLocalizedMessage()));
			} finally {
				reiniciarPedido();
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}
	public String verificarPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>verificarPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {
				pedidoVenta.setCliente(new Cliente(clienteId));
				pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
				pedidoVenta.setMetodoDePago(new MetodoDePago(metodoDePagoId));
				//pedidoVenta.setComentarios("PedidoNuevoMB.confirmarPedido @" + new Date());
				pedidoVenta.setFactoriva(LogicaFinaciera.getImpuestoIVA());

				Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();

				for (PedidoVentaDetalleWrapper pvdw : pedidoVentaDetalleMap.values()) {
					pedidoVentaDetalleCollection.add(pvdw.getDetalleVentaPedido());
				}
				for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
					logger.debug("\t==>>pedidoVentaDetalleCollection:" + pvd.getCantidad() + " x " + pvd.getProducto());
				}
				pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);
				pedidoVenta.setPorcentajeDescuentoCalculado(descuentoCalculado);
				pedidoVenta.setPorcentajeDescuentoExtra(descuentoEspecial);
				double descuentoAplicar = descuentoCalculado / 100.0 + descuentoEspecial / 100.0;
				pedidoVenta.setDescuentoAplicado(descuentoAplicar);

				pedidoVentaBusinessLogic.verificarPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK verificarPedido =======================");
				return "pedidoActualizado";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verificar Pedido : ", ex.getMessage()));
			} finally {
				cargarPadidoVenta(pedidoVenta.getId());
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String surtirPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>surtirPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {
				//pedidoVenta.setCliente(new Cliente(clienteId));
				pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
				pedidoVenta.setMetodoDePago(new MetodoDePago(metodoDePagoId));
				//pedidoVenta.setFactoriva(LogicaFinaciera.getImpuestoIVA());

				Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();

				for (PedidoVentaDetalleWrapper pvdw : pedidoVentaDetalleMap.values()) {
					pedidoVentaDetalleCollection.add(pvdw.getDetalleVentaPedido());
				}
				for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
					logger.debug("\t==>>pedidoVentaDetalleCollection:" + pvd.getCantidad() + " x " + pvd.getProducto());
				}
				pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);
				pedidoVenta.setPorcentajeDescuentoCalculado(descuentoCalculado);
				pedidoVenta.setPorcentajeDescuentoExtra(descuentoEspecial);
				double descuentoAplicar = descuentoCalculado / 100.0 + descuentoEspecial / 100.0;
				pedidoVenta.setDescuentoAplicado(descuentoAplicar);

				pedidoVentaBusinessLogic.surtirPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK surtirPedido =======================");
				return "pedidoActualizado";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Surtir Pedido :", ex.getMessage()));
			} finally {
				cargarPadidoVenta(pedidoVenta.getId());
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String enviarPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>enviarPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {
				pedidoVentaBusinessLogic.enviarPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK enviarPedido =======================");
				return "pedidoActualizado";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
				cargarPadidoVenta(pedidoVenta.getId());
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String actualizarCFDVenta() {
		logger.debug("========================================================>>");
		logger.debug("-->>actualizarCFDVenta():");
		logger.debug("========================================================>>");

		String callingErrorResult = pedidoVenta.getCfdVenta().getCallingErrorResult();
		if (callingErrorResult != null && callingErrorResult.equals("<null>")) {
			callingErrorResult = null;
		}
		String contenidoOriginalXml = pedidoVenta.getCfdVenta().getContenidoOriginalXml();
		if (contenidoOriginalXml != null && contenidoOriginalXml.equals("<null>")) {
			contenidoOriginalXml = null;
		}
		pedidoVenta.getCfdVenta().setCallingErrorResult(callingErrorResult);
		pedidoVenta.getCfdVenta().setUltimaActualizacion(new Date());
		pedidoVenta.getCfdVenta().setContenidoOriginalXml(contenidoOriginalXml);
		try {
			pedidoVentaBusinessLogic.updateCFD(pedidoVenta, pedidoVenta.getCfdVenta());
			logger.debug("-->>actualizarCFDVenta():OK, updated !");
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "actualizarCFDVenta: OK:", "Recargar"));

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "actualizarCFDVenta: ERROR:", ex.getLocalizedMessage()));

		} finally {
			return null;
		}
	}

	public String generarCFDPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>generarCFDPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {
				pedidoVentaBusinessLogic.generarCFDPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK generarCFDPedido =======================");
				return "pedidoActualizado";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
				cargarPadidoVenta(pedidoVenta.getId());
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}
	
	public void cancelarCambios(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();	
		logger.debug("========================================================>>");
		logger.debug("-->>cancelarCambios():");
		logger.debug("========================================================>>");
		
		cargarPadidoVenta(pedidoVenta.getId());
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Cancelar cambios: ",
						"Se restauró correctamente el Pedido"));
	}

	public String cancelarPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>cancelarPedido():");
		logger.debug("========================================================>>");
		reiniciarPedido();
		return null;
	}

	public void cancelarPedidoVenta(ActionEvent e) {
		logger.debug("==========>>cancelarPedidoVenta():");
		reiniciarPedido();
	}

	private void dataValidation() throws ValidatorException {
		logger.debug("\t## >> dataValidation: clienteId=" + clienteId + ", formaDePagoId=" + formaDePagoId + ", metodoDePagoId=" + metodoDePagoId);

		if (clienteId == null || (clienteId != null && clienteId.intValue() == 0)) {
			logger.debug("\t\t## >> throw new ValidatorException Cliente!");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación : ", "¡ Debe seleccionar el Cliente !"));
		}
		if (metodoDePagoId == null || (metodoDePagoId != null && metodoDePagoId.intValue() == 0)) {
			logger.debug("\t\t## >> throw new ValidatorException Metodo De Pago!");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación : ", "¡ Debe seleccionar el Método de Pago !"));
		}
		if (formaDePagoId == null || (formaDePagoId != null && formaDePagoId.intValue() == 0)) {
			logger.debug("\t\t## >> throw new ValidatorException Forma De Pago!");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación : ", "¡ Debe seleccionar la Forma de Pago !"));
		}
	}

	public void seleccionarProducto(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		String productoCB = context.getExternalContext().getRequestParameterMap().get("productoCB");

		logger.debug("## >> seleccionarProducto: productoCB=" + productoCB);
		PedidoVentaDetalleWrapper dvpSelected = pedidoVentaDetalleMap.get(productoCB);
		if (dvpSelected != null) {
			detalleVentaPedidoSeleccionado.getDetalleVentaPedido().setProducto(dvpSelected.getDetalleVentaPedido().getProducto());
			detalleVentaPedidoSeleccionado.getDetalleVentaPedido().setCantidad(dvpSelected.getDetalleVentaPedido().getCantidad());
		} else {
			throw new IllegalStateException("No se peude seleccionar productoCB=" + productoCB);
		}
		logger.debug("## >> end: seleccionarProducto");
	}

	public void guardarCantidadPedidoVentaDetalleSeleccionado(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		String productoCB = detalleVentaPedidoSeleccionado.getDetalleVentaPedido().getProducto().getCodigoBarras();

		logger.debug("## >> guardarCantidadPedidoVentaDetalleSeleccionado: productoCB=" + productoCB + ", cantidad=" + detalleVentaPedidoSeleccionado.getDetalleVentaPedido().getCantidad());
		PedidoVentaDetalleWrapper dvpSelected = pedidoVentaDetalleMap.get(productoCB);
		if (dvpSelected != null) {
			dvpSelected.getDetalleVentaPedido().setCantidad(detalleVentaPedidoSeleccionado.getDetalleVentaPedido().getCantidad());
			logger.debug("\t## >> ok, edited ");
		} else {
			throw new IllegalStateException("No se peude seleccionar productoCB=" + productoCB);
		}
		detalleVentaPedidoSeleccionado = null;
		logger.debug("## >> end: guardarCantidadPedidoVentaDetalleSeleccionado");
	}

	public void eliminarProducto(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		String productoCB = context.getExternalContext().getRequestParameterMap().get("productoCB");

		logger.debug("## >> eliminarProducto: productoCB=" + productoCB);
		if (pedidoVentaDetalleMap.containsKey(productoCB)) {
			pedidoVentaDetalleMap.remove(productoCB);
		} else {
			throw new IllegalStateException("No se puede borrar productoCB=" + productoCB);
		}
		logger.debug("## >> end: eliminarProducto");
	}

	public PedidoVentaDetalleFooter getPedidoFooter() {

		PedidoVentaDetalleFooter dvpf = new PedidoVentaDetalleFooter();

		int totalPiezas = 0;
		dvpf.setCantTotal(totalPiezas);
		dvpf.setDescuento(0.0);
		double subtotal = 0.0;
		double descuento = 0.0;
		double importeDescuento = 0.0;
		double subTotalRegistro = 0.0;
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleMap.values()) {
			totalPiezas += dvp.getDetalleVentaPedido().getCantidad();
			subTotalRegistro = dvp.getDetalleVentaPedido().getCantidad() * dvp.getDetalleVentaPedido().getPrecioVenta();
			subtotal += subTotalRegistro;
		}

		dvpf.setNumItems(totalPiezas);

		descuentoCalculado = 0;
		if (modoVenta == Constants.ALMACEN_LINEA) {
			if (subtotal >= 5000 && subtotal < 10000) {
				descuento = 0.05;
				descuentoCalculado = 5;
			} else if (subtotal >= 10000) {
				descuento = 0.1;
				descuentoCalculado = 10;
			}
		}

		descuento = descuento + (descuentoEspecial / 100.0);

		importeDescuento = subtotal * descuento;

		double subtotalDeplegar = subtotal / (1 + LogicaFinaciera.getImpuestoIVA());
		double impuestoDesplegar = subtotal - subtotalDeplegar;

		dvpf.setSubtotal(subtotalDeplegar);
		dvpf.setImpuesto(impuestoDesplegar);
		dvpf.setDescuento(importeDescuento);

		dvpf.setTotal(subtotalDeplegar + impuestoDesplegar - importeDescuento);

		return dvpf;
	}

	private List<AlmacenProducto> getListAlmacenProductoBuscar() {
		if (listAlmacenProductoBuscar == null) {
			Integer almacenId = getAlmacenObjetivo().getId();
			logger.debug("## >> getListAlmacenProductoBuscar->getAlmacenObjetivo().getId()=" + almacenId);
			listAlmacenProductoBuscar = productoJPAController.findAllValidProductosForAlmacen(almacenId);
			logger.debug("## >> getListAlmacenProductoBuscar->listAlmacenProductoBuscar.size()=" + listAlmacenProductoBuscar.size());
		}
		return listAlmacenProductoBuscar;
	}

	public void cantidadDetalleBtnChanged(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		String productoCB = context.getExternalContext().getRequestParameterMap().get("productoCB");

		logger.debug("## >> cantidadDetalleBtnChanged: productoCB=" + productoCB);
		PedidoVentaDetalleWrapper dvpSelected = pedidoVentaDetalleMap.get(productoCB);
		if (dvpSelected != null) {
			if (dvpSelected.getDetalleVentaPedido().getCantidad() > dvpSelected.getAlmacenProductoDemanda().getCantidadActual()) {
				logger.debug("## \t\t>> cantidadDetalleBtnChanged: Excede Max!");

				//dvpSelected.setCantidad(dvpSelected.getCantMax());

				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Cantidad pedida de producto : ",
						"Excede la existencia en Almacén, se actualiza pero no se podra surtir hasta que haya suficiente");
				FacesContext.getCurrentInstance().addMessage(
						null,
						fm);

			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Cantidad pedida de producto : ",
						"OK, se actualiza la nueva cantidad");
				FacesContext.getCurrentInstance().addMessage(
						null,
						fm);
			}
		}
	}

	public void nombreDescripcionChanged(ValueChangeEvent e) {
		String nombrePresentacionBuscar = ((String) e.getNewValue());
		String[] nombresPresentacionBuscar = nombrePresentacionBuscar.toLowerCase().split("([ ])+");
		logger.debug("## >> nombreDescripcionChanged: nombrePresentacionBuscar=" + nombrePresentacionBuscar + " =>" + Arrays.asList(nombresPresentacionBuscar));
		productoConNombreDescripcion = new ArrayList<SelectItem>();

		cantidadAgregar = null;
		if (nombrePresentacionBuscar.trim().length() >= 3) {
			String nombreDescripcionOriginal = null;
			String nombreDescripcionOriginalLC = null;
			boolean found = false;
			for (AlmacenProducto ap : getListAlmacenProductoBuscar()) {
				nombreDescripcionOriginal = ap.getProducto().getNombre() + "/" + ap.getProducto().getPresentacion() + " (" + ap.getProducto().getContenido() + ap.getProducto().getUnidadMedida() + " / " + ap.getProducto().getUnidadesPorCaja() + "UxCj.) #" + ap.getCantidadActual();
				nombreDescripcionOriginalLC = nombreDescripcionOriginal.toLowerCase();
				found = false;
				for (String n : nombresPresentacionBuscar) {
					if (nombreDescripcionOriginalLC.contains(n)) {
						found = true;
					}
				}
				if (found) {
					cantidadAgregar = 1;
					productoConNombreDescripcion.add(new SelectItem(ap.getProducto().getCodigoBarras(), nombreDescripcionOriginal));
				}
			}
		}
	}
	private Producto productoSearchedAndSelected;

	public void cantidadAgregarChanged(ValueChangeEvent e) {
		Long cantidadAgregarValue = ((Long) e.getNewValue());
		logger.debug("## >> cantidadAgregarChanged: productoCBSelected=" + productoCBSelected + ", cantidadAgregarValue=" + cantidadAgregarValue);
		Producto p = productoJPAController.findEntityByReadableProperty(productoCBSelected);
		agregarProductoADetalle(p, cantidadAgregarValue.intValue());
		productoCBSelected = null;
		productoConNombreDescripcion = new ArrayList<SelectItem>();
		nombreDescripcion = null;
	}

	public void productoSelectedChanged(ValueChangeEvent e) {
		logger.debug("## >> productoSelectedChanged:");
	}

	public List<SelectItem> getProductoConNombreDescripcion() {
		return productoConNombreDescripcion;
	}

	public List<SelectItem> getClienteList() {
		List<Cliente> clienteList = clienteJPAController.findAllEntities();
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

		for (Cliente cliente : clienteList) {
			resultList.add(new SelectItem(cliente.getId(), cliente.getRazonSocial()));
		}
		return resultList;
	}

	public List<SelectItem> getFormaDePagoList() {
		List<FormaDePago> formaDePagoList = formaDePagoJPAController.findAllEntities();
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

		for (FormaDePago formaDePago : formaDePagoList) {
			resultList.add(new SelectItem(formaDePago.getId(), formaDePago.getDescripcion()));
		}
		return resultList;
	}

	public List<SelectItem> getMetodoDePagoList() {
		List<MetodoDePago> metodoDePagoList = metodoDePagoJPAController.findAllEntities();
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

		for (MetodoDePago metodoDePago : metodoDePagoList) {
			resultList.add(new SelectItem(metodoDePago.getId(), metodoDePago.getDescripcion()));
		}
		return resultList;
	}

	public List<PedidoVenta> getPedidoVentaList() {
		List<PedidoVenta> pedidoVentaList = pedidoVentaJPAController.findPedidoVentaEntities();
		return pedidoVentaList;
	}

	//--------------------------------------------------------------------------
	public void setProductoJPAController(ProductoJPAController productoJPAController) {
		this.productoJPAController = productoJPAController;
	}

	public void setSucursalJPAController(SucursalJPAController sucursalJPAController) {
		this.sucursalJPAController = sucursalJPAController;
	}

	public void setMarcaJPAController(MarcaJPAController marcaJPAController) {
		this.marcaJPAController = marcaJPAController;
	}

	public void setIndustriaJPAController(IndustriaJPAController industriaJPAController) {
		this.industriaJPAController = industriaJPAController;
	}

	public void setLineaJPAController(LineaJPAController lineaJPAController) {
		this.lineaJPAController = lineaJPAController;
	}

	public List<PedidoVentaDetalleWrapper> getPedidoVentaDetalleList() {
		logger.debug("->getPedidoVentaDetalleList: pedidoVentaId="+pedidoVenta.getId());
		final Collection<PedidoVentaDetalleWrapper> values = pedidoVentaDetalleMap.values();
		List<PedidoVentaDetalleWrapper> pvdwList = new ArrayList<PedidoVentaDetalleWrapper>();
		Integer almacenId = getAlmacenObjetivo().getId();			
		for (PedidoVentaDetalleWrapper pvdw : values) {
			final PedidoVentaDetalle detalleVentaPedido = pvdw.getDetalleVentaPedido();
			logger.debug("\t->getPedidoVentaDetalleList:detalleVentaPedido="+detalleVentaPedido.getCantidad()+" x "+detalleVentaPedido.getProducto().getCodigoBarras()+" $"+detalleVentaPedido.getPrecioVenta());
			final Producto producto = detalleVentaPedido.getProducto();
			//logger.debug("\t->getPedidoVentaDetalleList:producto="+producto);
			AlmacenProductoDemanda findDemandaProductoForAlmacen = 
					productoJPAController.findDemandaProductoForAlmacen(almacenId,producto.getId());
			//logger.debug("\t\t->getPedidoVentaDetalleList:findDemandaProductoForAlmacen="+findDemandaProductoForAlmacen);
			boolean pedidoEnDisputa=false;
			if(findDemandaProductoForAlmacen != null) {
				if( findDemandaProductoForAlmacen.getOtrosPedidos() > 0 ){
					final List<PedidoVentaEnDemanda> pedidoVentaList = findDemandaProductoForAlmacen.getPedidoVentaList();
					for(PedidoVentaEnDemanda ped: pedidoVentaList){
						if(ped.getPedidoVentaId() == pedidoVenta.getId()){
							pedidoEnDisputa = true;
							break;
						}
					}
					pvdw.setAlmacenProductoDemanda(findDemandaProductoForAlmacen);
					pvdw.getAlmacenProductoDemanda().setCantidadActual(findDemandaProductoForAlmacen.getCantidadActual());
					if( pedidoEnDisputa ){
						pvdw.getAlmacenProductoDemanda().setOtrosPedidos(findDemandaProductoForAlmacen.getOtrosPedidos() - 1);
						pvdw.getAlmacenProductoDemanda().setSumDemanda(findDemandaProductoForAlmacen.getSumDemanda() - detalleVentaPedido.getCantidad());			
					} else {
						pvdw.getAlmacenProductoDemanda().setOtrosPedidos(findDemandaProductoForAlmacen.getOtrosPedidos());
						pvdw.getAlmacenProductoDemanda().setSumDemanda(findDemandaProductoForAlmacen.getSumDemanda());					
					}
				}
				if(pvdw.getDetalleVentaPedido().getPrecioVenta()==0.0){
					pvdw.getDetalleVentaPedido().setPrecioVenta(findDemandaProductoForAlmacen.getPrecioVenta());
				}
				pvdw.setAlmacenProductoDemanda(findDemandaProductoForAlmacen);
			}
			pvdwList.add(pvdw);
		}
		return pvdwList;
	}

	public PedidoVentaDetalleWrapper getPedidoVentaDetalleSeleccionado() {
		return detalleVentaPedidoSeleccionado;
	}

	public void setPedidoVentaDetalleSeleccionado(PedidoVentaDetalleWrapper detalleVentaPedidoSeleccionado) {
		this.detalleVentaPedidoSeleccionado = detalleVentaPedidoSeleccionado;
	}

	public PedidoVenta getPedidoVenta() {
		return pedidoVenta;
	}

	public void setPedidoVenta(PedidoVenta pedidoVenta) {
		this.pedidoVenta = pedidoVenta;
	}

	public String getNombreDescripcion() {
		return nombreDescripcion;
	}

	public void setNombreDescripcion(String nombreDescripcion) {
		this.nombreDescripcion = nombreDescripcion;
	}

	public String getProductoCBSelected() {
		return productoCBSelected;
	}

	public void setProductoCBSelected(String productoCBSelected) {
		this.productoCBSelected = productoCBSelected;
	}

	/**
	 * @return the productoJPAController
	 */
	public ProductoJPAController getProductoJPAController() {
		return productoJPAController;
	}

	/**
	 * @return the productoSearchedAndSelected
	 */
	public Producto getProductoSearchedAndSelected() {
		return productoSearchedAndSelected;
	}

	/**
	 * @param productoSearchedAndSelected the productoSearchedAndSelected to set
	 */
	public void setProductoSearchedAndSelected(Producto productoSearchedAndSelected) {
		logger.debug(">> setProductoSearchedAndSelected: =" + productoSearchedAndSelected);
		this.productoSearchedAndSelected = productoSearchedAndSelected;
	}

	public void setClienteJPAController(ClienteJPAController clienteJPAController) {
		this.clienteJPAController = clienteJPAController;
	}

	public void setFormaDePagoJPAController(FormaDePagoJPAController formaDePagoJPAController) {
		this.formaDePagoJPAController = formaDePagoJPAController;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getFormaDePagoId() {
		return formaDePagoId;
	}

	public void setFormaDePagoId(Integer formaDePagoId) {
		this.formaDePagoId = formaDePagoId;
	}

	public Integer getMetodoDePagoId() {
		return metodoDePagoId;
	}

	public void setMetodoDePagoId(Integer metodoDePagoId) {
		this.metodoDePagoId = metodoDePagoId;
	}

	public void setMetodoDePagoJPAController(MetodoDePagoJPAController metodoDePagoJPAController) {
		this.metodoDePagoJPAController = metodoDePagoJPAController;
	}

	public void setPedidoVentaJPAController(PedidoVentaJPAController pedidoVentaJPAController) {
		this.pedidoVentaJPAController = pedidoVentaJPAController;
	}

	public void setPedidoVentaEstadoJPAController(PedidoVentaEstadoJPAController pedidoVentaEstadoJPAController) {
		this.pedidoVentaEstadoJPAController = pedidoVentaEstadoJPAController;
	}

	public void setSessionUserMB(SessionUserMB sessionUserMB) {
		this.sessionUserMB = sessionUserMB;
	}

	public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
		this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
	}
	//--------------------------------------------------------------------------

	/**
	 * @return the codigoBuscar
	 */
	public String getCodigoBuscar() {
		return codigoBuscar;
	}

	/**
	 * @param codigoBuscar the codigoBuscar to set
	 */
	public void setCodigoBuscar(String codigoBuscar) {
		this.codigoBuscar = codigoBuscar;
	}

	/**
	 * @return the almacenObjetivo
	 */
	public Almacen getAlmacenObjetivo() {
		if (almacenObjetivo == null) {
			Sucursal sucursalPrincipal = sucursalJPAController.getSucursalPrincipal();

			Collection<Almacen> almacenCollection = sucursalPrincipal.getAlmacenCollection();
			for (Almacen a : almacenCollection) {
				if (a.getTipoAlmacen() == modoVenta) {
					almacenObjetivo = a;
					break;
				}
			}

		}
		return almacenObjetivo;
	}

	/**
	 * @param almacenObjetivo the almacenObjetivo to set
	 */
	public void setAlmacenObjetivo(Almacen almacenObjetivo) {
		this.almacenObjetivo = almacenObjetivo;
	}

	/**
	 * @return the modoVenta
	 */
	public int getModoVenta() {
		return modoVenta;
	}

	/**
	 * @param modoVenta the modoVenta to set
	 */
	public void setModoVenta(int modoVenta) {
		this.modoVenta = modoVenta;
	}

	/**
	 * @return the descuentoEspecial
	 */
	public int getDescuentoEspecial() {
		return descuentoEspecial;
	}

	/**
	 * @param descuentoEspecial the descuentoEspecial to set
	 */
	public void setDescuentoEspecial(int descuentoEspecial) {
		this.descuentoEspecial = descuentoEspecial;
	}

	private void actualizarAlmacenObjetivoDesdeModoVenta() {
		logger.debug("## >> actualizarAlmacenObjetivoDesdeModoVenta: modoVenta=" + modoVenta);

		//almacenObjetivo = null;
		if (sucursalPrincipal == null) {
			sucursalPrincipal = sucursalJPAController.getSucursalPrincipal();
		}

		Collection<Almacen> almacenCollection = sucursalPrincipal.getAlmacenCollection();
		for (Almacen a : almacenCollection) {
			if (a.getTipoAlmacen() == modoVenta) {
				almacenObjetivo = a;
				logger.debug("-> actualizarAlmacenObjetivoDesdeModoVenta: sucursalPrincipal=" + sucursalPrincipal.getId() + ", almacenObjetivo=" + almacenObjetivo.getId());

				break;
			}
		}
	}

	/**
	 * @return the cantidadAgregar
	 */
	public Integer getCantidadAgregar() {
		return cantidadAgregar;
	}

	/**
	 * @param cantidadAgregar the cantidadAgregar to set
	 */
	public void setCantidadAgregar(Integer cantidadAgregar) {
		this.cantidadAgregar = cantidadAgregar;
	}

	public Integer getCantidadCBAgregar() {
		return cantidadCBAgregar;
	}

	public void setCantidadCBAgregar(Integer cantidadCBAgregar) {
		this.cantidadCBAgregar = cantidadCBAgregar;
	}
	private Cliente clienteSelected;

	public Cliente getClienteSelected() {
		if (clienteSelected == null || (clienteSelected != null && !clienteSelected.getId().equals(clienteId))) {
			if (clienteId != null) {
				clienteSelected = clienteJPAController.findById(clienteId);
			}
		}
		return clienteSelected;
	}

	private PedidoVentaEstado getLastPedidoVentaEstado() {
		Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();

		if (localPedidoVentaEstadoComparator == null) {
			localPedidoVentaEstadoComparator = new PedidoVentaEstadoComparator();
		}

		TreeSet<PedidoVentaEstado> treeSetPedidoVentaEstado = new TreeSet<PedidoVentaEstado>(localPedidoVentaEstadoComparator);

		for (PedidoVentaEstado pve : pedidoVentaEstadoCollection) {
			treeSetPedidoVentaEstado.add(pve);
		}

		return treeSetPedidoVentaEstado.last();
	}

	private Collection<PedidoVentaEstado> getPedidoVentaEstadoListInOrder() {
		Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
		List<PedidoVentaEstado> pedidoVentaEstadoListForSort = new ArrayList<PedidoVentaEstado>();

		if (localPedidoVentaEstadoComparator == null) {
			localPedidoVentaEstadoComparator = new PedidoVentaEstadoComparator();
		}

		TreeSet<PedidoVentaEstado> treeSetPedidoVentaEstado = new TreeSet<PedidoVentaEstado>(localPedidoVentaEstadoComparator);

		for (PedidoVentaEstado pve : pedidoVentaEstadoCollection) {
			treeSetPedidoVentaEstado.add(pve);
		}

		for (PedidoVentaEstado pve : treeSetPedidoVentaEstado) {
			pedidoVentaEstadoListForSort.add(pve);
		}

		return pedidoVentaEstadoListForSort;
	}

	public PedidoVentaEstado getPedidoVentaEstado() {
		return pedidoVentaEstado;
	}

	public Collection<PedidoVentaEstado> getPedidoVentaEstadoList() {
		return pedidoVentaEstadoList;
	}

	public boolean isPedidoEnabledToChangeBasicData() {
		return pedidoVentaEstado.getEstado().getId() < Constants.ESTADO_VERIFICADO;
	}

	public boolean isPedidoEnabledToChangeNumbersData() {
		return pedidoVentaEstado.getEstado().getId() < Constants.ESTADO_SURTIDO;
	}

	public boolean isPedidoVentaEstado_CAPTURADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_CAPTURADO;
	}

	public boolean isPedidoVentaEstado_SINCRONIZADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_SINCRONIZADO;
	}

	public boolean isPedidoVentaEstado_VERIFICADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_VERIFICADO;
	}

	public boolean isPedidoVentaEstado_SURTIDO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_SURTIDO;
	}

	public boolean isPedidoVentaEstado_ENVIABLE() {
		return pedidoVentaEstado.getEstado().getId() >= Constants.ESTADO_SURTIDO
				&& pedidoVentaEstado.getEstado().getId() < Constants.ESTADO_ENVIADO;
	}

	public boolean isPedidoVentaEstado_FACTURADO() {
		return pedidoVentaEstado.getEstado().getId() >= Constants.ESTADO_FACTURADO;
	}

	public boolean isPedidoVentaEstado_FACTURABLE() {
		return pedidoVentaEstado.getEstado().getId() >= Constants.ESTADO_SURTIDO;
	}

	public boolean isPedidoVentaEstado_ENVIADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_ENVIADO;
	}

	public boolean isPedidoVentaEstado_ENTREGADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_ENTREGADO;
	}

	public boolean isPedidoVentaEstado_DEVUELTO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_DEVUELTO;
	}

	public boolean isPedidoVentaEstado_PAGADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_PAGADO;
	}

	public boolean isPedidoVentaEstado_CANCELADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_CANCELADO;
	}

	public boolean isPedidoVentaEstado_MODIFICABLE() {
		return pedidoVentaEstado.getEstado().getId() < Constants.ESTADO_SURTIDO;
	}

	public boolean isPedidoVentaEstado_MODIFICABLE_PRODUCTOS() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_CAPTURADO
				|| pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_SINCRONIZADO;
	}
}
