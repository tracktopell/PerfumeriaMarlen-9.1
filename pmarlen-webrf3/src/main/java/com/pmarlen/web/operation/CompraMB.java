/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.CompraBusinessLogic;
//import com.pmarlen.businesslogic.CompraVentaBusinessLogic;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.AlmacenProducto;
import com.pmarlen.model.beans.EntradaAlmacen;
import com.pmarlen.model.beans.EntradaAlmacenDetalle;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.beans.Sucursal;
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
public class CompraMB {
    
    private ProductoJPAController productoJPAController;
    
    private SucursalJPAController sucursalJPAController;
    	    
    private SessionUserMB sessionUserMB;
    
    private List<EntradaAlmacenDetalle> entradaAlmacenDetalleList;
	private EntradaAlmacen entradaAlmacen;
    private EntradaAlmacenDetalle entradaAlmacenSeleccionado;
    private String nombreDescripcion;
	private String codigoBuscar;
	private Producto productoEncontrado;
    private Integer productoSelected;
    private List<SelectItem> productoConNombreDescripcion;
    private Integer clienteId;
    private Integer formaDePagoId;
    private final Logger logger = LoggerFactory.getLogger(CompraMB.class);
	private Almacen almacenObjetivo;
	private int modoVenta;
	
	private int descuentoCalculado;
	
	private int descuentoEspecial;
	
	private List<SelectItem> descuentosPosiblesList;

	private List<AlmacenProducto> listAlmacenProductoBuscar;
	
	private Integer cantidadAgregar;
	
	private CompraBusinessLogic compraBusinessLogic;	
	/**
	 * @return the descuentoCalculado
	 */
	public int getDescuentoCalculado() {
		return descuentoCalculado;
	}

	public void setCompraBusinessLogic(CompraBusinessLogic compraBusinessLogic) {
		this.compraBusinessLogic = compraBusinessLogic;
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
	}
	
	public void descuentosPosiblesListChanged(ValueChangeEvent e) {
        Integer newValue = (Integer) e.getNewValue();
        logger.debug("## >> descuentosPosiblesListChanged: newValue=" + newValue);
		this.descuentoEspecial = newValue;
	}
	
    public CompraMB() {
        reiniciarCompra();		
    }

    private void reiniciarCompra() {
		logger.debug("## >> reiniciarCompra: ");
        entradaAlmacenSeleccionado = new EntradaAlmacenDetalle();
        entradaAlmacenDetalleList = new ArrayList<EntradaAlmacenDetalle>();
        entradaAlmacen = new EntradaAlmacen();
		//entradaAlmacen.setUsuario(sessionUserMB.getUsuarioAuthenticated());
        productoConNombreDescripcion = new ArrayList<SelectItem>();
		descuentoCalculado=0;
		descuentoEspecial =0;
		modoVenta = 1;
		clienteId = null;
		formaDePagoId = null;
		descuentoEspecial = 0;
		almacenObjetivo = null;
		listAlmacenProductoBuscar = null;		
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
        EntradaAlmacenDetalle detalleVentaCompraAgregar = null;
        logger.debug("## >> agregarProductoADetalle: productoIdAgregar=" + productoIdAgregar);
        for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
            if (dvp.getProducto().getId().intValue() == productoIdAgregar.intValue()) {
                detalleVentaCompraAgregar = dvp;
                break;
            }
        }
        try {
            if (detalleVentaCompraAgregar != null) {
				detalleVentaCompraAgregar.setCantidad(detalleVentaCompraAgregar.getCantidad() + cantidad);
				logger.debug("## >> agregarProductoADetalle: \t Ok, edit");

            } else {
                detalleVentaCompraAgregar = new EntradaAlmacenDetalle();
                detalleVentaCompraAgregar.setCantidad(cantidad);

                Producto producto = productoJPAController.findById(productoIdAgregar);
                Collection<AlmacenProducto> almacenProductoCollection = producto.getAlmacenProductoCollection();
                int cantActualAlmacen = 0;
				double precioObjetivo = 0.0;
                for (AlmacenProducto almacenProducto : almacenProductoCollection) {
					if(almacenProducto.getAlmacen().getId().intValue() == getAlmacenObjetivo().getId().intValue()){
						precioObjetivo = almacenProducto.getPrecioVenta();					
						cantActualAlmacen = almacenProducto.getCantidadActual();
					}
                }

				if(listAlmacenProductoBuscar ==  null){
					getListAlmacenProductoBuscar();
				}
				
                detalleVentaCompraAgregar.setProducto(producto);                
                detalleVentaCompraAgregar.setPrecioVenta(precioObjetivo);
                
                entradaAlmacenDetalleList.add(detalleVentaCompraAgregar);				
                logger.debug("## >> agregarProductoADetalle: \t Ok, Add new");
            }
        } catch (ValidatorException ve) {
            logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    ve.getFacesMessage());
        }
    }
	
	public int getEntradaAlmacenDetalleListSize(){
		if(entradaAlmacenDetalleList==null){
			entradaAlmacenDetalleList = new ArrayList<EntradaAlmacenDetalle>();
		}
		return entradaAlmacenDetalleList.size();
	}

    public String confirmarCompra() {
        logger.debug("========================================================>>");
        logger.debug("-->>confirmarCompra():");
        logger.debug("========================================================>>");
        try {
            dataValidation();
            try {
                entradaAlmacen.setUsuario(sessionUserMB.getUsuarioAuthenticated());				
                entradaAlmacen.setComentarios("Entrada Compra" + new Date());				
				entradaAlmacen.setAlmacen(almacenObjetivo);
                Collection<EntradaAlmacenDetalle> entradaAlmacenDetalleCollection = new ArrayList<EntradaAlmacenDetalle>();
				
                for(EntradaAlmacenDetalle pvdw:entradaAlmacenDetalleList){
                    entradaAlmacenDetalleCollection.add(pvdw);
                }
                for(EntradaAlmacenDetalle pvd: entradaAlmacenDetalleCollection){
                    logger.debug("\t==>>entradaAlmacenDetalleCollection:"+pvd.getCantidad()+" x "+pvd.getProducto());
                }
                entradaAlmacen.setEntradaAlmacenDetalleCollection(entradaAlmacenDetalleCollection);
				double descuentoAplicar = descuentoCalculado/100.0 + descuentoEspecial /100.0;
				entradaAlmacen.setDescuentoAplicado(descuentoAplicar);
				
                entradaAlmacen = compraBusinessLogic.crearCompraCapturada(entradaAlmacen, sessionUserMB.getUsuarioAuthenticated());
				logger.debug("<<===================== OK crearCompraCapturado =======================");
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmar Compra :", "Se confirmo correctamente");
				FacesContext.getCurrentInstance().addMessage(null,fm);
				return null;
            } catch (Exception ex) {
                logger.debug("<<++++++++++++++++++++++++++++++++++++++++++++++++++");
                ex.printStackTrace(System.err);
                logger.debug("Error in MB to create Compra:", ex);

                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.toString(), ex.toString()));
            } finally {
                reiniciarCompra();
            }
        } catch (ValidatorException ve) {
            logger.debug("<<!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    ve.getFacesMessage());
            return null;
        }
    }
    
	public String cancelarCompra() {
        logger.debug("========================================================>>");
        logger.debug("-->>cancelarCompra():");
        logger.debug("========================================================>>");
		reiniciarCompra();
		return null;
	}
	
	public void cancelarCompra(ActionEvent e) {
		logger.debug("==========>>cancelarCompraVenta():");        
		reiniciarCompra();		
	}
    
	private void dataValidation() throws ValidatorException {
        logger.debug("\t## >> dataValidation: clienteId=" + clienteId + ", formaDePagoId=" + formaDePagoId);
    }

    public void seleccionarProducto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> seleccionarProducto: productoId=" + productoId);
        boolean selectedFromDetalle = false;
        for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
            if (dvp.getProducto().getId() == productoId ) {
                entradaAlmacenSeleccionado.setProducto(dvp.getProducto());
                entradaAlmacenSeleccionado.setCantidad(dvp.getCantidad());
                selectedFromDetalle = true;
                break;
            }
        }
        if (!selectedFromDetalle) {
            logger.warn("\t## >> productoId=" + productoId + " => detalleVentaCompraSeleccionado is null");
        }
        logger.debug("## >> end: seleccionarProducto");
    }

    public void activarDescuento(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> activarDescuento: productoId=" + productoId);
        boolean selectedFromDetalle = false;
        for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
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
        for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
            if (dvp.getProducto().getId() == productoId) {
                selectedFromDetalle = true;
                break;
            }
        }
        if (!selectedFromDetalle) {
            logger.warn("\t## >> productoId=" + productoId + " => selectedFromDetalle=" + selectedFromDetalle);
        }
        logger.debug("## >> end: desactivarDescuento");
    }

    public void guardarCantidadEntradaAlmacenDetalleSeleccionado(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();

        logger.debug("## >> guardarCantidadEntradaAlmacenDetalleSeleccionado: productoId=" + entradaAlmacenSeleccionado.getProducto().getId() + ", cantidad=" + entradaAlmacenSeleccionado.getCantidad());
        for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
            if (dvp.getProducto().getId() == entradaAlmacenSeleccionado.getProducto().getId()) {
                dvp.setCantidad(entradaAlmacenSeleccionado.getCantidad());
                logger.debug("\t## >> ok, edited ");
                break;
            }
        }
        entradaAlmacenSeleccionado = new EntradaAlmacenDetalle();
        logger.debug("## >> end: guardarCantidadEntradaAlmacenDetalleSeleccionado");
    }

    public void eliminarProducto(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));

        logger.debug("## >> eliminarProducto: productoId=" + productoId);
        int indexToDelete = -1, i = 0;
        for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
            if (dvp.getProducto().getId().intValue() == productoId.intValue()) {
                indexToDelete = i;
                logger.debug("\t## >> indexToDelete=" + indexToDelete);
                break;
            }
            i++;
        }
        if (indexToDelete != -1) {
            EntradaAlmacenDetalle dvpDeleted = entradaAlmacenDetalleList.remove(indexToDelete);
            logger.debug("\t\t## >> dvpDeleted["+indexToDelete+"] = " + dvpDeleted);
        } else{
		    logger.debug("\t\t## >> can delete["+indexToDelete+"]");
			throw new IllegalStateException("can't delete row:"+indexToDelete);
		}
        logger.debug("## >> end: eliminarProducto");
    }

	/*
    public EntradaAlmacenDetalleFooter getCompraFooter() {

        EntradaAlmacenDetalleFooter dvpf = new EntradaAlmacenDetalleFooter();

        int totalPiezas = 0;
        dvpf.setCantTotal(totalPiezas);
        dvpf.setDescuento(0.0);
		double subtotal  = 0.0;
        double descuento = 0.0;
		double importeDescuento = 0.0;
        double subTotalRegistro = 0.0;
        for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
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
	*/
	private List<AlmacenProducto> getListAlmacenProductoBuscar(){
		if(listAlmacenProductoBuscar ==  null){
			Integer almacenId = getAlmacenObjetivo().getId();	
			logger.debug("## >> getListAlmacenProductoBuscar->getAlmacenObjetivo().getId()=" + almacenId);
			listAlmacenProductoBuscar = productoJPAController.findAllValidProductosForAlmacen(almacenId);			
			logger.debug("## >> getListAlmacenProductoBuscar->listAlmacenProductoBuscar.size()=" + listAlmacenProductoBuscar.size());			
		}
		return listAlmacenProductoBuscar;
	}
	
	public void cantidadDetalleBtnChanged(ActionEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Integer productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));
		
		logger.debug("## >> cantidadDetalleBtnChanged: productoId="+productoId);
		try{
			for (EntradaAlmacenDetalle dvp : entradaAlmacenDetalleList) {
				logger.debug("## >> cantidadDetalleBtnChanged:\t"+dvp.getProducto().getCodigoBarras()+", "+dvp.getCantidad()+" ]");
				if(dvp.getProducto().getId().intValue() == productoId.intValue()){
					
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
    
    //--------------------------------------------------------------------------
    public void setProductoJPAController(ProductoJPAController productoJPAController) {
        this.productoJPAController = productoJPAController;
    }

	public void setSucursalJPAController(SucursalJPAController sucursalJPAController) {
		this.sucursalJPAController = sucursalJPAController;
	}
	
    public List<EntradaAlmacenDetalle> getEntradaAlmacenDetalleList() {
        return entradaAlmacenDetalleList;
    }

    public EntradaAlmacenDetalle getEntradaAlmacenDetalleSeleccionado() {
        return entradaAlmacenSeleccionado;
    }

    public void setEntradaAlmacenDetalleSeleccionado(EntradaAlmacenDetalle detalleVentaCompraSeleccionado) {
        this.entradaAlmacenSeleccionado = detalleVentaCompraSeleccionado;
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

    public void setSessionUserMB(SessionUserMB sessionUserMB) {
        this.sessionUserMB = sessionUserMB;
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

}
