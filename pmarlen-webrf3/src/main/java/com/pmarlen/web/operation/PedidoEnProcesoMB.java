/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.businesslogic.reports.GeneradorDeFactura;
import com.pmarlen.businesslogic.reports.GeneradorImpresionPedidoVenta;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.ClienteJPAController;
import com.pmarlen.model.controller.IndustriaJPAController;
import com.pmarlen.model.controller.FormaDePagoJPAController;
import com.pmarlen.model.controller.LineaJPAController;
import com.pmarlen.model.controller.MarcaJPAController;
import com.pmarlen.model.controller.PedidoVentaEstadoJPAController;
import com.pmarlen.model.controller.PedidoVentaJPAController;
import com.pmarlen.model.controller.ProductoJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEAXX9M
 */
public class PedidoEnProcesoMB {

	@Autowired
	private ProductoJPAController productoJPAController;
	@Autowired
	private MarcaJPAController marcaJPAController;
	@Autowired
	private IndustriaJPAController industriaJPAController;
	@Autowired
	private LineaJPAController lineaJPAController;
	@Autowired
	private ClienteJPAController clienteJPAController;
	@Autowired
	private FormaDePagoJPAController formaDePagoJPAController;
	@Autowired
	private PedidoVentaJPAController pedidoVentaJPAController;
	@Autowired
	private PedidoVentaEstadoJPAController pedidoVentaEstadoJPAController;
	@Autowired
	private SessionUserMB sessionUserMB;
	@Autowired
	private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;
	@Autowired	
	private PedidoNuevoMB pedidoNuevoMB;
	
	private List<PedidoVentaDetalleWrapper> pedidoVentaDetalleList;
	private PedidoVenta pedidoVenta;
	private PedidoVentaEstado pedidoVentaEstado;
	private Collection<PedidoVentaEstado> pedidoVentaEstadoList;
	private PedidoVentaDetalleWrapper detalleVentaPedidoSeleccionado;
	private String nombreDescripcion;
	private Integer productoSelected;
	private List<SelectItem> productoConNombreDescripcion;
	private Integer clienteId;
	private Integer formaDePagoId;
	private final Logger logger = LoggerFactory.getLogger(PedidoEnProcesoMB.class);

	public PedidoEnProcesoMB() {
	}

	public String prepararPedidoParaEdicion() {
		FacesContext context = FacesContext.getCurrentInstance();

		String pedidoVentaId = context.getExternalContext().getRequestParameterMap().get("pedidoVentaId");

		logger.debug("========================================================>>");
		logger.debug("-->>prepararPedidoParaEdicion: pedidoVentaId=" + pedidoVentaId);
		logger.debug("========================================================>>");

		pedidoVenta = pedidoVentaJPAController.findPedidoVenta(Integer.parseInt(pedidoVentaId));

		detalleVentaPedidoSeleccionado = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
		productoConNombreDescripcion = new ArrayList<SelectItem>();

		clienteId = pedidoVenta.getCliente().getId();

		formaDePagoId = pedidoVenta.getFormaDePago().getId();

		pedidoVentaEstado = getLastPedidoVentaEstado();
		
		

		pedidoVentaEstadoList = getPedidoVentaEstadoListInOrder();

		pedidoVentaDetalleList = new ArrayList<PedidoVentaDetalleWrapper>();

		Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = pedidoVenta.getPedidoVentaDetalleCollection();
		for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
			PedidoVentaDetalleWrapper detalleVentaPedidoAgregar = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
			detalleVentaPedidoAgregar.setCantidad(pvd.getCantidad());

			Producto producto = productoJPAController.findById(pvd.getProducto().getId());
			Collection<AlmacenProducto> almacenProductoCollection = producto.getAlmacenProductoCollection();
			int cantMaxAlmacenes = 0;
			for (AlmacenProducto almacenProducto : almacenProductoCollection) {
				cantMaxAlmacenes += almacenProducto.getCantidadActual();
			}

			if (!isPedidoEnabledToChangeNumbersData()) {
				detalleVentaPedidoAgregar.setCantMax(cantMaxAlmacenes + pvd.getCantidad());
			} else {
				detalleVentaPedidoAgregar.setCantMax(cantMaxAlmacenes);
			}


			detalleVentaPedidoAgregar.setProducto(producto);
			detalleVentaPedidoAgregar.setPrecioVenta(pvd.getPrecioVenta());

			pedidoVentaDetalleList.add(detalleVentaPedidoAgregar);
		}


		logger.debug("\t-->>pedidoVentaEditar.Factoriva=" + pedidoVenta + ", clienteId=" + clienteId + ", formaDePagoId=" + formaDePagoId + ", pedidoVentaDetalleList.size=" + pedidoVentaDetalleList.size());

		return "editarPedido";
	}

	public void actualizarEstatusFiscal(ActionEvent e) {
		logger.debug("################################ >> actualizarEstatusFiscal: ");
	}

	public void agregarProductoBuscado(ActionEvent e) {
		logger.debug("################################ >> agregarProductoBuscado: productoSelected=" + productoSelected);
		agregarProductoADetalle(productoSelected);
		productoSelected = null;
		productoConNombreDescripcion = new ArrayList<SelectItem>();
		nombreDescripcion = null;
	}

	public void agregar1Producto(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

		logger.debug("################################ >> agregar1Producto: productoId=" + productoId);

		agregarProductoADetalle(productoId);
	}

	private void agregarProductoADetalle(Integer productoIdAgregar) {
		PedidoVentaDetalleWrapper detalleVentaPedidoAgregar = null;
		logger.debug("################################ >> agregarProductoADetalle: productoIdAgregar=" + productoIdAgregar);
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
			if (dvp.getProducto().getId() == productoIdAgregar) {
				detalleVentaPedidoAgregar = dvp;
				break;
			}
		}
		try {
			if (detalleVentaPedidoAgregar != null) {
				if (detalleVentaPedidoAgregar.getCantidad() >= detalleVentaPedidoAgregar.getCantMax()) {
					logger.warn("################################ >> agregarProductoADetalle: Cantidad Exedida, no se agregara");

					throw new ValidatorException(
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Sobrepasa cantidad Maxima de existencia en Almacenes",
							"Sobrepasa cantidad Maxima de existencia en Almacenes"));
				} else {
					detalleVentaPedidoAgregar.setCantidad(detalleVentaPedidoAgregar.getCantidad() + 1);
					logger.debug("################################ >> agregarProductoADetalle: \t Ok, edit");
				}
			} else {
				detalleVentaPedidoAgregar = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
				detalleVentaPedidoAgregar.setCantidad(1);

				Producto producto = productoJPAController.findById(productoIdAgregar);
				Collection<AlmacenProducto> almacenProductoCollection = producto.getAlmacenProductoCollection();
				int cantMaxAlmacen = 0;
				double precioVenta = 0.0;
				for (AlmacenProducto almacenProducto : almacenProductoCollection) {
					if(almacenProducto.getAlmacen().getId().intValue() == pedidoNuevoMB.getAlmacenObjetivo().getId().intValue()){
						cantMaxAlmacen += almacenProducto.getCantidadActual();
						precioVenta = almacenProducto.getPrecioVenta();
					}
				}

				if (cantMaxAlmacen <= 0) {
					logger.warn("################################ >> agregarProductoADetalle: Cantidad Exedida, No hay existencia en Almacenes");

					throw new ValidatorException(
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No hay existencia en Almacenes",
							"No hay existencia en Almacenes"));
				}

				detalleVentaPedidoAgregar.setCantMax(cantMaxAlmacen);
				detalleVentaPedidoAgregar.setProducto(producto);
				detalleVentaPedidoAgregar.setDescuentoAplicado(0.0);

				detalleVentaPedidoAgregar.setPrecioVenta(precioVenta);


				pedidoVentaDetalleList.add(detalleVentaPedidoAgregar);
				logger.debug("################################ >> agregarProductoADetalle: \t Ok, Add new");
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
		}
	}

	public String guardarPedidoVerificado() {
		logger.debug("========================================================>>");
		logger.debug("-->>guardarPedidoVerificado():");
		logger.debug("========================================================>>");
		try {

			dataValidation();
			try {
				pedidoVenta.setCliente(new Cliente(clienteId));
				pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
				pedidoVenta.setUsuario(sessionUserMB.getUsuarioAuthenticated());
				pedidoVenta.setComentarios("Prueba de Pedido @" + new Date());

				Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();

				for (PedidoVentaDetalleWrapper pvdw : pedidoVentaDetalleList) {
					pedidoVentaDetalleCollection.add(pvdw.getPedidoVentaDetalle());
				}

				for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
					logger.debug("==============>>\t[" + pvd.getCantidad() + "] " + pvd.getProducto());
				}
				pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);


				pedidoVentaBusinessLogic.verificarPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK verificarPedido =======================");
				return "pedidoVerificado";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String guardarPedidoParaSurtir() {
		logger.debug("========================================================>>");
		logger.debug("-->>guardarPedidoParaSurtir():");
		logger.debug("========================================================>>");
		try {

			dataValidation();
			try {
				pedidoVenta.setCliente(new Cliente(clienteId));
				pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
				pedidoVenta.setUsuario(sessionUserMB.getUsuarioAuthenticated());
				pedidoVenta.setComentarios("Prueba de Pedido @" + new Date());

				Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();

				for (PedidoVentaDetalleWrapper pvdw : pedidoVentaDetalleList) {
					pedidoVentaDetalleCollection.add(pvdw.getPedidoVentaDetalle());
				}

				for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
					logger.debug("==============>>\t[" + pvd.getCantidad() + "] " + pvd.getProducto());
				}
				pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);


				pedidoVentaBusinessLogic.surtirPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK surtirPedido =======================");
				return "pedidoSurido";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
			}
		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String generarCFD() {
		logger.debug("========================================================>>");
		logger.debug("-->>generarCFD():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {
				pedidoVenta.setCliente(new Cliente(clienteId));
				pedidoVenta.setFormaDePago(new FormaDePago(formaDePagoId));
				pedidoVenta.setUsuario(sessionUserMB.getUsuarioAuthenticated());
				pedidoVenta.setComentarios("Prueba de Pedido @" + new Date());

				Collection<PedidoVentaDetalle> pedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();

				for (PedidoVentaDetalleWrapper pvdw : pedidoVentaDetalleList) {
					pedidoVentaDetalleCollection.add(pvdw.getPedidoVentaDetalle());
				}

				for (PedidoVentaDetalle pvd : pedidoVentaDetalleCollection) {
					logger.debug("==============>>\t[" + pvd.getCantidad() + "] " + pvd.getProducto());
				}
				pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);

				pedidoVentaBusinessLogic.generarCFDPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());

				logger.debug("<<===================== OK generarCFDPedido =======================");
				return "pedidoSurido";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
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

				logger.debug("<<===================== OK generarCFDPedido =======================");
				return "pedidoSurido";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
			}

		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String confirmarEntregaPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>confirmarEntregaPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {

				pedidoVentaBusinessLogic.confirmarEntregaPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());

				logger.debug("<<===================== OK confirmarEntregaPedido =======================");
				return "pedidoSurido";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
			}

		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String confirmarDevolucionPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>confirmarDevolucionPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {

				pedidoVentaBusinessLogic.confirmarDevolucionPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());

				logger.debug("<<===================== OK confirmarDevolucionPedido =======================");
				return "pedidoSurido";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
			}

		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	public String cancelarPedido() {
		logger.debug("========================================================>>");
		logger.debug("-->>cancelarPedido():");
		logger.debug("========================================================>>");
		try {
			dataValidation();
			try {

				pedidoVentaBusinessLogic.cancelarPedido(pedidoVenta, sessionUserMB.getUsuarioAuthenticated());

				logger.debug("<<===================== OK cancelarPedido =======================");
				return "pedidoSurido";
			} catch (Exception ex) {
				logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
				ex.printStackTrace(System.err);
				logger.debug("Error in MB to create pedido:", ex);

				throw new ValidatorException(
						new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
			} finally {
			}

		} catch (ValidatorException ve) {
			logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			FacesContext.getCurrentInstance().addMessage(
					null,
					ve.getFacesMessage());
			return null;
		}
	}

	private void dataValidation() throws ValidatorException {
		logger.debug("\t################################ >> dataValidation: clienteId=" + clienteId + ", formaDePagoId=" + formaDePagoId);

		if (clienteId == null || (clienteId != null && clienteId.intValue() == 0)) {
			logger.debug("\t\t################################ >> throw new ValidatorException Cliente!");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar el Cliente !!", "Debe seleccionalr el Cliente !!"));
		}
		if (formaDePagoId == null || (formaDePagoId != null && formaDePagoId.intValue() == 0)) {
			logger.debug("\t\t################################ >> throw new ValidatorException FormaDePago!");
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe seleccionar la Forma de Pago!!", "Debe seleccionalr la Forma de Pago!!"));
		}
	}

	public void seleccionarProducto(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

		logger.debug("################################ >> seleccionarProducto: productoId=" + productoId);
		boolean selectedFromDetalle = false;
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
			if (dvp.getProducto().getId() == productoId) {
				detalleVentaPedidoSeleccionado.setProducto(dvp.getProducto());
				detalleVentaPedidoSeleccionado.setCantidad(dvp.getCantidad());
				selectedFromDetalle = true;
				break;
			}
		}
		if (!selectedFromDetalle) {
			logger.warn("\t################################ >> productoId=" + productoId + " => detalleVentaPedidoSeleccionado is null");
		}
		logger.debug("################################ >> end: seleccionarProducto");
	}

	public void activarDescuento(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

		logger.debug("################################ >> activarDescuento: productoId=" + productoId);
		boolean selectedFromDetalle = false;
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
			if (dvp.getProducto().getId() == productoId) {
				selectedFromDetalle = true;
				break;
			}
		}
		if (!selectedFromDetalle) {
			logger.warn("\t################################ >> productoId=" + productoId + " => selectedFromDetalle=" + selectedFromDetalle);
		}
		logger.debug("################################ >> end: activarDescuento");
	}

	public void desactivarDescuento(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

		logger.debug("################################ >> desactivarDescuento: productoId=" + productoId);
		boolean selectedFromDetalle = false;
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
			if (dvp.getProducto().getId() == productoId) {
				dvp.setDescuentoAplicado(0.0);
				selectedFromDetalle = true;
				break;
			}
		}
		if (!selectedFromDetalle) {
			logger.warn("\t################################ >> productoId=" + productoId + " => selectedFromDetalle=" + selectedFromDetalle);
		}
		logger.debug("################################ >> end: desactivarDescuento");
	}

	public void guardarCantidadPedidoVentaDetalleSeleccionado(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		logger.debug("################################ >> guardarCantidadPedidoVentaDetalleSeleccionado: productoId=" + detalleVentaPedidoSeleccionado.getProducto().getId() + ", cantidad=" + detalleVentaPedidoSeleccionado.getCantidad());
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
			if (dvp.getProducto().getId() == detalleVentaPedidoSeleccionado.getProducto().getId()) {
				dvp.setCantidad(detalleVentaPedidoSeleccionado.getCantidad());
				logger.debug("\t################################ >> ok, edited ");
				break;
			}
		}
		detalleVentaPedidoSeleccionado = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
		logger.debug("################################ >> end: guardarCantidadPedidoVentaDetalleSeleccionado");
	}

	public void eliminarProducto(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

		logger.debug("################################ >> eliminarProducto: productoId=" + productoId);
		int indexToDelete = -1, i = 0;
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
			if (dvp.getProducto().getId().intValue() == productoId.intValue()) {
				indexToDelete = i;
				logger.debug("\t################################ >> indexToDelete=" + indexToDelete);
				break;
			}
			i++;
		}
		if (indexToDelete != -1) {
			PedidoVentaDetalleWrapper dvpDeleted = pedidoVentaDetalleList.remove(indexToDelete);
			logger.debug("\t\t################################ >> dvpDeleted[" + indexToDelete + "] = " + dvpDeleted);
		} else {
			logger.debug("\t\t################################ >> can't delete[" + indexToDelete + "]");
			throw new IllegalStateException("can't delete row:" + indexToDelete);
		}
		logger.debug("################################ >> end: eliminarProducto");
	}

	public PedidoVentaDetalleFooter getPedidoFooter() {

		PedidoVentaDetalleFooter dvpf = new PedidoVentaDetalleFooter();

		int totalPiezas = 0;
		dvpf.setCantTotal(totalPiezas);
		dvpf.setDescuento(0.0);
		double subtotal = 0.0;
		double descuento = 0.0;
		double descuentoRegistro = 0.0;
		double impuesto = 0.0;
		double impuestoRegistro = 0.0;
		double subTotalRegistro = 0.0;
		for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
			totalPiezas += dvp.getCantidad();
			subTotalRegistro = dvp.getCantidad() * dvp.getPrecioVenta();

			impuestoRegistro = subTotalRegistro * LogicaFinaciera.getImpuestoIVA();

			if (dvp.getDescuentoAplicado() != 0.0) {
				descuentoRegistro = dvp.getDescuentoAplicado() * (subTotalRegistro + impuestoRegistro);
			} else {
				descuentoRegistro = 0;
			}

			impuesto += impuestoRegistro;
			descuento += descuentoRegistro;
			subtotal += subTotalRegistro;
		}

		dvpf.setNumItems(totalPiezas);

		dvpf.setSubtotal(subtotal);
		dvpf.setImpuesto(impuesto);
		dvpf.setDescuento(descuento);

		dvpf.setTotal(subtotal + impuesto - descuento);

		return dvpf;
	}

	public void nombreDescripcionChanged(ValueChangeEvent e) {
		String newValue = (String) e.getNewValue();
		logger.debug("################################ >> nombreDescripcionChanged: newValue=" + newValue);
		productoConNombreDescripcion = new ArrayList<SelectItem>();
		if (newValue.trim().length() >= 3) {
			List<Producto> listResultOriginal = productoJPAController.findAllEntities();
			for (Producto productoOriginal : listResultOriginal) {
				String nombreDescripcionOriginal = productoOriginal.getNombre() + "/" + productoOriginal.getPresentacion();
				if (nombreDescripcionOriginal.toLowerCase().contains(newValue.toLowerCase())) {
					SelectItem productoSI = new SelectItem(productoOriginal.getId(), nombreDescripcionOriginal);
					productoConNombreDescripcion.add(productoSI);
				}
			}
		}
	}

	public void buscarProductoConNombreDescripcion(ActionEvent e) {
		logger.debug("################################ >> productoConNombreDescripcion: nombreDescripcion=" + nombreDescripcion);
		productoConNombreDescripcion = new ArrayList<SelectItem>();
		if (nombreDescripcion.trim().length() >= 3) {
			List<Producto> listResultOriginal = productoJPAController.findAllEntities();
			for (Producto productoOriginal : listResultOriginal) {
				String nombreDescripcionOriginal = productoOriginal.getNombre() + "/" + productoOriginal.getPresentacion();
				if (nombreDescripcionOriginal.toLowerCase().contains(nombreDescripcion.toLowerCase())) {
					SelectItem productoSI = new SelectItem(productoOriginal.getId(), nombreDescripcionOriginal);
					productoConNombreDescripcion.add(productoSI);
				}
			}
		}

	}
	private Producto productoSearchedAndSelected;

	public List<Producto> autocompletarProductoConNombreDescripcion(Object suggest) {
		List<Producto> listResult = null;
		nombreDescripcion = (String) suggest;
		logger.debug("################################ >> autocompletarProductoConNombreDescripcion: nombreDescripcion=" + nombreDescripcion);

		if (nombreDescripcion.trim().length() >= 3) {
			listResult = new ArrayList<Producto>();
			List<Producto> listResultOriginal = productoJPAController.findAllEntities();
			for (Producto productoOriginal : listResultOriginal) {
				String nombreDescripcionOriginal = productoOriginal.getNombre() + "/" + productoOriginal.getPresentacion();
				if (nombreDescripcionOriginal.toLowerCase().contains(nombreDescripcion.toLowerCase())) {
					listResult.add(productoOriginal);
				}
			}
		}
		return listResult;
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

	public List<PedidoVenta> getPedidoVentaList() {
		List<PedidoVenta> pedidoVentaList = pedidoVentaJPAController.findPedidoVentaEntities();
		return pedidoVentaList;
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
		List<PedidoVentaEstado> pedidoVentaEstadoList = new ArrayList<PedidoVentaEstado>();

		if (localPedidoVentaEstadoComparator == null) {
			localPedidoVentaEstadoComparator = new PedidoVentaEstadoComparator();
		}

		TreeSet<PedidoVentaEstado> treeSetPedidoVentaEstado = new TreeSet<PedidoVentaEstado>(localPedidoVentaEstadoComparator);

		for (PedidoVentaEstado pve : pedidoVentaEstadoCollection) {
			treeSetPedidoVentaEstado.add(pve);
		}

		for (PedidoVentaEstado pve : treeSetPedidoVentaEstado) {
			pedidoVentaEstadoList.add(pve);
		}

		return pedidoVentaEstadoList;
	}
	private static PedidoVentaEstadoComparator localPedidoVentaEstadoComparator;

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
		return pedidoVentaEstado.getEstado().getId() >= Constants.ESTADO_SURTIDO && 
				pedidoVentaEstado.getEstado().getId() < Constants.ESTADO_ENVIADO;
	}
	
	public boolean isPedidoVentaEstado_FACTURADO() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_FACTURADO;
	}
	
	public boolean isPedidoVentaEstado_FACTURABLE() {
		return	pedidoVentaEstado.getEstado().getId() >= Constants.ESTADO_SURTIDO;
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
	
	public boolean isPedidoVentaEstado_CANCELABLE() {
		return  pedidoVentaEstado.getEstado().getId() <= Constants.ESTADO_SURTIDO;
	}

	public boolean isPedidoVentaEstado_MODIFICABLE_PRODUCTOS() {
		return pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_CAPTURADO
				|| pedidoVentaEstado.getEstado().getId() == Constants.ESTADO_SINCRONIZADO;
	}

	//--------------------------------------------------------------------------
	public void setProductoJPAController(ProductoJPAController productoJPAController) {
		this.productoJPAController = productoJPAController;
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
		return pedidoVentaDetalleList;
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

	public Integer getProductoSelected() {
		return productoSelected;
	}

	public void setProductoSelected(Integer productoSelected) {
		this.productoSelected = productoSelected;
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

	public void setPedidoVentaJPAController(PedidoVentaJPAController pedidoVentaJPAController) {
		this.pedidoVentaJPAController = pedidoVentaJPAController;
	}

	public void setPedidoVentaEstadoJPAController(PedidoVentaEstadoJPAController pedidoVentaEstadoJPAController) {
		this.pedidoVentaEstadoJPAController = pedidoVentaEstadoJPAController;
	}

	public void setSessionUserMB(SessionUserMB sessionUserMB) {
		this.sessionUserMB = sessionUserMB;
	}

	/**
	 * @return the pedidoVentaEstado
	 */
	public PedidoVentaEstado getPedidoVentaEstado() {
		return pedidoVentaEstado;
	}

	/**
	 * @return the pedidoVentaEstadoList
	 */
	public Collection<PedidoVentaEstado> getPedidoVentaEstadoList() {
		return pedidoVentaEstadoList;
	}

	/**
	 * @param pedidoVentaBusinessLogic the pedidoVentaBusinessLogic to set
	 */
	public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
		this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
	}
	@Autowired
	GeneradorDeFactura generadorDeFactura;
	
	@Autowired
	GeneradorImpresionPedidoVenta generadorImpresionPedidoVenta;


	public void setGeneradorDeFactura(GeneradorDeFactura generadorDeFactura) {
		this.generadorDeFactura = generadorDeFactura;
	}

	public void setGeneradorImpresionPedidoVenta(GeneradorImpresionPedidoVenta generadorImpresionPedidoVenta) {
		this.generadorImpresionPedidoVenta = generadorImpresionPedidoVenta;
	}

	public void descargarFactura(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer pedidoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("pedidoId"));
		logger.debug("################################ >> descargarFactura: productoId=" + pedidoId);


		FacesContext faces = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
		
		ServletOutputStream out = null;
		try {
			PedidoVenta pedidoVentaEncontrado = null;
			logger.debug("############>>: pedidoVentaJPAController="+pedidoVentaJPAController);
			pedidoVentaEncontrado = pedidoVentaJPAController.findPedidoVenta(pedidoId);
			
			byte[] pdfBytes = generadorDeFactura.generaPdfFactura(pedidoVentaEncontrado,true);
			
			response.setContentType("application/pdf");
			response.setContentLength(pdfBytes.length);
			response.setHeader("Content-disposition", "inline; filename=factura.pdf");
			out = response.getOutputStream();
			out.write(pdfBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		faces.responseComplete();
	}
	
	public void descargarPedioVenta(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		Integer pedidoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("pedidoId"));
		logger.debug("################################ >> descargarFactura: productoId=" + pedidoId);


		FacesContext faces = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
		
		ServletOutputStream out = null;
		try {
			PedidoVenta pedidoVentaEncontrado = null;
			logger.debug("############>>: pedidoVentaJPAController="+pedidoVentaJPAController);
			pedidoVentaEncontrado = pedidoVentaJPAController.findPedidoVenta(pedidoId);
			
			byte[] pdfBytes = generadorImpresionPedidoVenta.generaPdfPedidoVenta(pedidoVentaEncontrado,true,null);
			
			response.setContentType("application/pdf");
			response.setContentLength(pdfBytes.length);
			response.setHeader("Content-disposition", "inline; filename=factura.pdf");
			out = response.getOutputStream();
			out.write(pdfBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		faces.responseComplete();
	}
}
