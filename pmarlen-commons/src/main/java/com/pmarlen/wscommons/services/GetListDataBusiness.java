
package com.pmarlen.wscommons.services;

import com.pmarlen.model.beans.*;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author alfred
 */
@WebService
public interface GetListDataBusiness {
	@WebMethod(operationName = "getUsuarioList")
    public List<Usuario> getUsuarioList();
	
    @WebMethod(operationName = "getIndustriaList")
    public List<Industria> getIndustriaList();
		
    @WebMethod(operationName = "getLineaList")
    public List<Linea> getLineaList();

    @WebMethod(operationName = "getMarcaList")
    public List<Marca> getMarcaList();

    @WebMethod(operationName = "getProductoList")
    public List<Producto> getProductoList();

    @WebMethod(operationName = "getProductoMultimedioList")
    public List<Multimedio> getProductoMultimedioList();
	
	@WebMethod(operationName = "getAlmacenProductoList")
    public List<AlmacenProducto> getAlmacenProductoList();

    @WebMethod(operationName = "getFormaDePagoList")
    public List<FormaDePago> getFormaDePagoList();

    @WebMethod(operationName = "getEstadoList")
    public List<Estado> getEstadoList();

    @WebMethod(operationName = "getSucursalList")
    public List<Sucursal> getSucursalList();

	@WebMethod(operationName = "getClienteList")
    public List<Cliente> getClienteList();
	
	@WebMethod(operationName = "getServerVersion")
    public String getServerVersion();

	@WebMethod(operationName = "getAllDataPackCompressed")
    public byte[] getAllDataPackCompressed();

	@WebMethod(operationName = "getUpdateDataPackCompressed")
    public byte[] getUpdateDataPackCompressed(@WebParam(name="fechaEventoInicio")long fechaEventoInicio);
	
}
