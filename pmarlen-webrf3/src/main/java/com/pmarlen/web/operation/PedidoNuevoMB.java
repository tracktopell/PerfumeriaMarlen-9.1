/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.AlmacenProductoDemanda;
import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.MetodoDePago;
import com.pmarlen.model.beans.PedidoVenta;
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
import com.pmarlen.web.security.managedbean.SessionUserMB;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author VEAXX9M
 */
public class PedidoNuevoMB {
    
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
    private List<PedidoVentaDetalleWrapper> pedidoVentaDetalleList;
    private PedidoVenta pedidoVenta;
    private PedidoVentaDetalleWrapper detalleVentaPedidoSeleccionado;
    private String nombreDescripcion;
	private String codigoBuscar;
	private Producto productoEncontrado;
    private Integer productoSelected;
    private List<SelectItem> productoConNombreDescripcion;
    private Integer clienteId;
    private Integer formaDePagoId;
	private Integer metodoDePagoId;
    private final Logger logger = LoggerFactory.getLogger(PedidoNuevoMB.class);
	private Almacen almacenObjetivo;
	private int modoVenta;
	
	private int descuentoCalculado;
	
	private int descuentoEspecial;
	
	private List<SelectItem> descuentosPosiblesList;

	private List<AlmacenProducto> listAlmacenProductoBuscar;
	
	private Hashtable<Integer,AlmacenProductoDemanda>  productoDemandaHT;
	
	private Integer cantidadAgregar;
	private Integer cantidadCBAgregar;
	
	/**
	 * @return the descuentoCalculado
	 */
	public int getDescuentoCalculado() {
		return descuentoCalculado;
	}

	public List<SelectItem> getDescuentosPosiblesList(){
		if(descuentosPosiblesList == null){
			descuentosPosiblesList = new ArrayList<SelectItem>();

			descuentosPosiblesList.add(new SelectItem(0,  "SIN DESCUENTO"));
			descuentosPosiblesList.add(new SelectItem(2,  "- 2%"));
			descuentosPosiblesList.add(new SelectItem(5,  "- 5%"));
			descuentosPosiblesList.add(new SelectItem(7,  "- 7%"));
			descuentosPosiblesList.add(new SelectItem(10, "- 10%"));			
		}
		return descuentosPosiblesList;
	}
	
	private Hashtable<Integer,String> tipoAlmacenHashTable;	
	private List<SelectItem> resultTipoAlmacenList;

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
		if(modoVenta > 0){
			return getTipoAlmacenHashTable().get(modoVenta);
		} else{
			return "-";
		}
    }

	static Sucursal sucursalPrincipal;
	
	public void almacenSelected(ValueChangeEvent event){
		logger.info("## >> * almacenSelected: old=" + event.getOldValue()+", new="+event.getNewValue());        
		modoVenta = (Integer)event.getNewValue();
		actualizarAlmacenObjetivoDesdeModoVenta();
		listAlmacenProductoBuscar = null;
		productoDemandaHT         = null;
	}
	
	public void descuentosPosiblesListChanged(ValueChangeEvent e) {
        Integer newValue = (Integer) e.getNewValue();
        logger.debug("## >> descuentosPosiblesListChanged: newValue=" + newValue);
		this.descuentoEspecial = newValue;
	}
	
    public PedidoNuevoMB() {
        reiniciarPedido();		
    }

    private void reiniciarPedido() {
        detalleVentaPedidoSeleccionado = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
        pedidoVentaDetalleList = new ArrayList<PedidoVentaDetalleWrapper>();
        pedidoVenta = new PedidoVenta();
        productoConNombreDescripcion = new ArrayList<SelectItem>();
		descuentoCalculado=0;
		descuentoEspecial =0;
		modoVenta = 1;
		clienteId = null;
		formaDePagoId = null;
		metodoDePagoId = null;
		descuentoEspecial = 0;
		almacenObjetivo = null;
		listAlmacenProductoBuscar = null;
		productoDemandaHT         = null;
		cantidadCBAgregar = 1;
		//actualizarAlmacenObjetivoDesdeModoVenta();
    }

    public void actualizarEstatusFiscal(ActionEvent e) {
        logger.debug("## >> actualizarEstatusFiscal: ");
    }
	
	public void modoVentaChanged(ValueChangeEvent e) {
        logger.debug("## >> modoVentaChanged: "+e.getOldValue()+" -->> "+e.getNewValue());		
    }
	
	public void modoVentaChangedAction(ActionEvent e) {
        logger.debug("## >> modoVentaChangedAction: "+modoVenta);		
    }
	
	public String modoVentaSaved() {
        logger.debug("## >> modoVentaSaved: modoVenta="+modoVenta);
		actualizarAlmacenObjetivoDesdeModoVenta();

		return "modoVentaSaved";
    }
		
	public void buscarProductoPorCodigo(ActionEvent e) {
        logger.debug("## >> * buscarProductoPorCodigo: productoSelected=" + codigoBuscar);
		
		productoEncontrado = productoJPAController.findEntityByReadableProperty(codigoBuscar);
		if(productoEncontrado != null){
		
			agregarProductoADetalle(productoEncontrado.getId(),1);
			productoEncontrado = null;
			productoConNombreDescripcion = new ArrayList<SelectItem>();
			nombreDescripcion = null;
		} else{
			logger.debug("## >> no necontrado:" + codigoBuscar);
		}
		codigoBuscar ="";
    }
	
	public void buscarNProductoPorCodigo(ActionEvent e) {
        logger.debug("## >> buscarNProductoPorCodigo: productoSelected=" + codigoBuscar+", cantidadAgregar="+cantidadCBAgregar);
		
		productoEncontrado = productoJPAController.findEntityByReadableProperty(codigoBuscar);
		if(productoEncontrado != null){
		
			agregarProductoADetalle(productoEncontrado.getId(),cantidadCBAgregar);
			productoEncontrado = null;
			productoConNombreDescripcion = new ArrayList<SelectItem>();
			nombreDescripcion = null;
		} else{
			logger.debug("## >> no necontrado:" + codigoBuscar);
		}
		codigoBuscar ="";
    }

	
    public void agregarProductoBuscado(ActionEvent e) {
        logger.debug("## >> agregarProductoBuscado: productoSelected=" + productoSelected);
        agregarProductoADetalle(productoSelected,1);
        productoSelected = null;
        productoConNombreDescripcion = new ArrayList<SelectItem>();
        nombreDescripcion = null;
    }
	
	public void agregarNProductoBuscado(ActionEvent e) {
        logger.debug("## >> agregarNProductoBuscado: productoSelected=" + productoSelected+", cantidadAgregar="+cantidadAgregar);
        agregarProductoADetalle(productoSelected,cantidadAgregar);
        productoSelected = null;
        productoConNombreDescripcion = new ArrayList<SelectItem>();
        nombreDescripcion = null;
    }

    public void agregar1Producto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> agregar1Producto: productoId=" + productoId);

        agregarProductoADetalle(productoId, cantidadAgregar);
    }
	
    private void agregarProductoADetalle(Integer productoIdAgregar, int cantidad) {
        PedidoVentaDetalleWrapper detalleVentaPedidoAgregar = null;
        logger.debug("## >> agregarProductoADetalle: productoIdAgregar=" + productoIdAgregar);
        for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
            if (dvp.getProducto().getId().intValue() == productoIdAgregar.intValue()) {
                detalleVentaPedidoAgregar = dvp;
                break;
            }
        }
        try {
            if (detalleVentaPedidoAgregar != null) {
                if (detalleVentaPedidoAgregar.getCantidad()+cantidad > detalleVentaPedidoAgregar.getCantMax()) {
                    logger.warn("## >> agregarProductoADetalle: Cantidad Exedida, no se agregara");

                    throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Agregar Producto",
                                "Excede la existencia en Almacén"));
                } else {
                    detalleVentaPedidoAgregar.setCantidad(detalleVentaPedidoAgregar.getCantidad() + cantidad);
                    logger.debug("## >> agregarProductoADetalle: \t Ok, edit");
                }
            } else {
                detalleVentaPedidoAgregar = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
                detalleVentaPedidoAgregar.setCantidad(cantidad);

                Producto producto = productoJPAController.findById(productoIdAgregar);
                Collection<AlmacenProducto> almacenProductoCollection = producto.getAlmacenProductoCollection();
                int cantMaxAlmacen = 0;
				double precioObjetivo = 0.0;
                for (AlmacenProducto almacenProducto : almacenProductoCollection) {
					if(almacenProducto.getAlmacen().getId().intValue() == getAlmacenObjetivo().getId().intValue()){
						precioObjetivo = almacenProducto.getPrecioVenta();					
						cantMaxAlmacen = almacenProducto.getCantidadActual();
					}
                }

                if (cantidad > cantMaxAlmacen ) {
                    logger.warn("## >> agregarProductoADetalle: Cantidad Exedida, No hay existencia en Almacenes");
                    throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Agregar Producto",
                                "Excede la existencia en Almacén"));
                }
				if(listAlmacenProductoBuscar ==  null){
					getListAlmacenProductoBuscar();
				}
				AlmacenProductoDemanda prodEnDemanda = productoDemandaHT.get(productoIdAgregar);
				if(prodEnDemanda != null){
					detalleVentaPedidoAgregar.setCantDemanda(prodEnDemanda.getSumDemanda());
					detalleVentaPedidoAgregar.setOtrosPedidos(prodEnDemanda.getOtrosPedidos());
				}
				
                detalleVentaPedidoAgregar.setCantMax(cantMaxAlmacen);
                detalleVentaPedidoAgregar.setProducto(producto);                
                detalleVentaPedidoAgregar.setDescuentoAplicado(0.0);
				detalleVentaPedidoAgregar.setPrecioVenta(precioObjetivo);
                
                pedidoVentaDetalleList.add(detalleVentaPedidoAgregar);
				//pedidoVentaDetalleList.add(0, detalleVentaPedidoAgregar);
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
	
	public int getPedidoVentaActualSize(){
		if(pedidoVentaDetalleList==null){
			pedidoVentaDetalleList = new ArrayList<PedidoVentaDetalleWrapper>();
		}
		return pedidoVentaDetalleList.size();
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
				
                for(PedidoVentaDetalleWrapper pvdw:pedidoVentaDetalleList){
                    pedidoVentaDetalleCollection.add(pvdw.getPedidoVentaDetalle());
                }
                for(PedidoVentaDetalle pvd: pedidoVentaDetalleCollection){
                    logger.debug("\t==>>pedidoVentaDetalleCollection:"+pvd.getCantidad()+" x "+pvd.getProducto());
                }
                pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollection);
				pedidoVenta.setPorcentajeDescuentoCalculado(descuentoCalculado);
				pedidoVenta.setPorcentajeDescuentoExtra(descuentoEspecial);
				double descuentoAplicar = descuentoCalculado/100.0 + descuentoEspecial /100.0;
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
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
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
        logger.debug("\t## >> dataValidation: clienteId=" + clienteId + ", formaDePagoId=" + formaDePagoId+ ", metodoDePagoId=" + metodoDePagoId);

        if (clienteId == null || (clienteId != null && clienteId.intValue() == 0)) {
            logger.debug("\t\t## >> throw new ValidatorException Cliente!");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación:", "¡ Debe seleccionar el Cliente !"));
        }
        if (metodoDePagoId == null || (metodoDePagoId != null && metodoDePagoId.intValue() == 0)) {
            logger.debug("\t\t## >> throw new ValidatorException Metodo De Pago!");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación:", "¡Debe seleccionalr el Método de Pago !"));
        }
		if (formaDePagoId == null || (formaDePagoId != null && formaDePagoId.intValue() == 0)) {
            logger.debug("\t\t## >> throw new ValidatorException Forma De Pago!");
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de Validación:", "¡Debe seleccionalr la Forma de Pago !"));
        }
    }

    public void seleccionarProducto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> seleccionarProducto: productoId=" + productoId);
        boolean selectedFromDetalle = false;
        for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
            if (dvp.getProducto().getId() == productoId ) {
                detalleVentaPedidoSeleccionado.setProducto(dvp.getProducto());
                detalleVentaPedidoSeleccionado.setCantidad(dvp.getCantidad());
                selectedFromDetalle = true;
                break;
            }
        }
        if (!selectedFromDetalle) {
            logger.warn("\t## >> productoId=" + productoId + " => detalleVentaPedidoSeleccionado is null");
        }
        logger.debug("## >> end: seleccionarProducto");
    }

    public void activarDescuento(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> activarDescuento: productoId=" + productoId);
        boolean selectedFromDetalle = false;
        for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
            logger.debug("## >> activarDescuento: ["+dvp.getProducto().getId()+"] == "+productoId+" ? "+(dvp.getProducto().getId() == productoId)+" , or use .equals ?"+(dvp.getProducto().getId().equals(productoId)));
            if (dvp.getProducto().getId().equals(productoId)) {
                //dvp.setDescuentoAplicado(dvp.getProducto().getFactorMaxDesc());
                selectedFromDetalle = true;
                break;
            }
        }
        if (!selectedFromDetalle) {
            logger.warn("\t## >> productoId=" + productoId + " => selectedFromDetalle=" + selectedFromDetalle);
        }
        logger.debug("## >> end: activarDescuento");
    }

    public void desactivarDescuento(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> desactivarDescuento: productoId=" + productoId);
        boolean selectedFromDetalle = false;
        for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
            if (dvp.getProducto().getId() == productoId) {
                dvp.setDescuentoAplicado(0.0);
                selectedFromDetalle = true;
                break;
            }
        }
        if (!selectedFromDetalle) {
            logger.warn("\t## >> productoId=" + productoId + " => selectedFromDetalle=" + selectedFromDetalle);
        }
        logger.debug("## >> end: desactivarDescuento");
    }

    public void guardarCantidadPedidoVentaDetalleSeleccionado(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();

        logger.debug("## >> guardarCantidadPedidoVentaDetalleSeleccionado: productoId=" + detalleVentaPedidoSeleccionado.getProducto().getId() + ", cantidad=" + detalleVentaPedidoSeleccionado.getCantidad());
        for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
            if (dvp.getProducto().getId() == detalleVentaPedidoSeleccionado.getProducto().getId()) {
                dvp.setCantidad(detalleVentaPedidoSeleccionado.getCantidad());
                logger.debug("\t## >> ok, edited ");
                break;
            }
        }
        detalleVentaPedidoSeleccionado = new PedidoVentaDetalleWrapper(new PedidoVentaDetalle());
        logger.debug("## >> end: guardarCantidadPedidoVentaDetalleSeleccionado");
    }

    public void eliminarProducto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> eliminarProducto: productoId=" + productoId);
        int indexToDelete = -1, i = 0;
        for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
            if (dvp.getProducto().getId().intValue() == productoId.intValue()) {
                indexToDelete = i;
                logger.debug("\t## >> indexToDelete=" + indexToDelete);
                break;
            }
            i++;
        }
        if (indexToDelete != -1) {
            PedidoVentaDetalleWrapper dvpDeleted = pedidoVentaDetalleList.remove(indexToDelete);
            logger.debug("\t\t## >> dvpDeleted["+indexToDelete+"] = " + dvpDeleted);
        } else{
		    logger.debug("\t\t## >> can delete["+indexToDelete+"]");
			throw new IllegalStateException("can't delete row:"+indexToDelete);
		}
        logger.debug("## >> end: eliminarProducto");
    }

    public PedidoVentaDetalleFooter getPedidoFooter() {

        PedidoVentaDetalleFooter dvpf = new PedidoVentaDetalleFooter();

        int totalPiezas = 0;
        dvpf.setCantTotal(totalPiezas);
        dvpf.setDescuento(0.0);
		double subtotal  = 0.0;
        double descuento = 0.0;
		double importeDescuento = 0.0;
        double subTotalRegistro = 0.0;
        for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
            totalPiezas += dvp.getCantidad();
            subTotalRegistro = dvp.getCantidad() * dvp.getPrecioVenta();
			subtotal        += subTotalRegistro;            
        }

        dvpf.setNumItems(totalPiezas);
		
		descuentoCalculado = 0;
		if(modoVenta == Constants.ALMACEN_LINEA){
			if(subtotal >= 5000 && subtotal < 10000){
				descuento = 0.05;
				descuentoCalculado = 5;
			} else if(subtotal >= 10000 ){
				descuento = 0.1;
				descuentoCalculado = 10;
			}
		}
		
		descuento = descuento + (descuentoEspecial/100.0);
		
		importeDescuento = subtotal * descuento;
		
		double subtotalDeplegar  = subtotal / (1 + LogicaFinaciera.getImpuestoIVA());
		double impuestoDesplegar = subtotal - subtotalDeplegar;
		
        dvpf.setSubtotal(subtotalDeplegar);		
        dvpf.setImpuesto(impuestoDesplegar);
        dvpf.setDescuento(importeDescuento);

        dvpf.setTotal(subtotalDeplegar + impuestoDesplegar - importeDescuento);

        return dvpf;
    }
	
	private List<AlmacenProducto> getListAlmacenProductoBuscar(){
		if(listAlmacenProductoBuscar ==  null){
			Integer almacenId = getAlmacenObjetivo().getId();	
			logger.debug("## >> getListAlmacenProductoBuscar->getAlmacenObjetivo().getId()=" + almacenId);
			listAlmacenProductoBuscar = productoJPAController.findAllValidProductosForAlmacen(almacenId);			
			productoDemandaHT         = productoJPAController.findDemandaProductosForAlmacen(almacenId);
			logger.debug("## >> getListAlmacenProductoBuscar->listAlmacenProductoBuscar.size()=" + listAlmacenProductoBuscar.size());
			logger.debug("## >> getListAlmacenProductoBuscar->productoDemandaHT.size()=" + productoDemandaHT.size());
		}
		return listAlmacenProductoBuscar;
	}
	
	public void cantidadDetalleBtnChanged(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));
		
		logger.debug("## >> cantidadDetalleBtnChanged: productoId="+productoId);
		try{
			for (PedidoVentaDetalleWrapper dvp : pedidoVentaDetalleList) {
				logger.debug("## >> cantidadDetalleBtnChanged:\t"+dvp.getProducto().getCodigoBarras()+", "+dvp.getCantidad()+" ["+dvp.getCantMax()+"]");
				if(dvp.getProducto().getId().intValue() == productoId.intValue()){
					if(dvp.getCantidad() > dvp.getCantMax()){
						logger.debug("## \t\t>> cantidadDetalleBtnChanged: Excede Max!");						
						dvp.setCantidad(dvp.getCantMax());
						throw new Exception("Cantidad exede máximo en almacén actual. se asignará el máximo.");
					}
				}
			}	
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al editar cantidad:", ex.getMessage()));
            
		}	
	}
	
    public void nombreDescripcionChanged(ValueChangeEvent e) {
		String   nombrePresentacionBuscar  = ((String) e.getNewValue());
        String[] nombresPresentacionBuscar = nombrePresentacionBuscar.toLowerCase().split("([ ])+");
        logger.debug("## >> nombreDescripcionChanged: nombrePresentacionBuscar=" + nombrePresentacionBuscar+" =>"+Arrays.asList(nombresPresentacionBuscar));
        productoConNombreDescripcion = new ArrayList<SelectItem>();
		
		cantidadAgregar = null;		
		if (nombrePresentacionBuscar.trim().length() >= 3) {        
			String nombreDescripcionOriginal   = null;
			String nombreDescripcionOriginalLC = null;
			boolean found=false;
			for(AlmacenProducto ap: getListAlmacenProductoBuscar()){
				nombreDescripcionOriginal   = ap.getProducto().getNombre() + "/" + ap.getProducto().getPresentacion()+" ("+ap.getProducto().getContenido()+ap.getProducto().getUnidadMedida()+" / "+ap.getProducto().getUnidadesPorCaja()+"UxCj.) #"+ap.getCantidadActual();
				nombreDescripcionOriginalLC = nombreDescripcionOriginal.toLowerCase();
				found=false;
				for(String n:nombresPresentacionBuscar){
					if (nombreDescripcionOriginalLC.contains(n)) {
						found=true;				
					}
				}
				if(found){			
					cantidadAgregar = 1;
					productoConNombreDescripcion.add(new SelectItem(ap.getProducto().getId(), nombreDescripcionOriginal));
				}
			}
		}
    }
	private Producto productoSearchedAndSelected;
	
	public void cantidadAgregarChanged(ValueChangeEvent e) {
		Long   cantidadAgregarValue  = ((Long) e.getNewValue());
        logger.debug("## >> cantidadAgregarChanged: cantidadAgregarValue=" + cantidadAgregarValue);
		agregarProductoADetalle(productoSelected,cantidadAgregarValue.intValue());
        productoSelected = null;
        productoConNombreDescripcion = new ArrayList<SelectItem>();
        nombreDescripcion = null;
	}
	
	public void productoSelectedChanged(ValueChangeEvent e) {
        logger.debug("## >> productoSelectedChanged:" );		
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
		if(almacenObjetivo==null){
			Sucursal sucursalPrincipal = sucursalJPAController.getSucursalPrincipal();

			Collection<Almacen> almacenCollection = sucursalPrincipal.getAlmacenCollection();
			for(Almacen a: almacenCollection){
				if(a.getTipoAlmacen() == modoVenta){
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
		logger.debug("## >> actualizarAlmacenObjetivoDesdeModoVenta: modoVenta="+modoVenta);
		
		//almacenObjetivo = null;
		if(sucursalPrincipal == null){
			sucursalPrincipal = sucursalJPAController.getSucursalPrincipal();
		}
		
		Collection<Almacen> almacenCollection = sucursalPrincipal.getAlmacenCollection();
		for(Almacen a: almacenCollection){
			if(a.getTipoAlmacen() == modoVenta){
				almacenObjetivo = a;
				logger.debug("-> actualizarAlmacenObjetivoDesdeModoVenta: sucursalPrincipal="+sucursalPrincipal.getId()+", almacenObjetivo="+almacenObjetivo.getId());
		
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
		if(clienteSelected == null || (clienteSelected != null && !clienteSelected.getId().equals(clienteId))){
			if(clienteId != null) {
				clienteSelected = clienteJPAController.findById(clienteId);
			}
		}
		return clienteSelected;
	}
	
}
