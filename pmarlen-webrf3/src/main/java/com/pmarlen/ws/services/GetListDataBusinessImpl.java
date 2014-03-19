package com.pmarlen.ws.services;

import com.pmarlen.businesslogic.UpdateDataProvider;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.*;
import com.pmarlen.model.controller.GetListDataBusinesJPAController;
import com.pmarlen.wscommons.services.GetListDataBusiness;
import com.pmarlen.wscommons.services.UpdateDataFacade;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import java.util.List;
import java.util.zip.GZIPOutputStream;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

@WebService(endpointInterface = "com.pmarlen.wscommons.services.GetListDataBusiness")
@Repository("getListDataBusinessImpl")
public class GetListDataBusinessImpl implements GetListDataBusiness {

	private static Logger logger = LoggerFactory.getLogger(GetListDataBusinessImpl.class);
	
	@Autowired
	private GetListDataBusinesJPAController getListDataBusinesJPAController;

	@Autowired
	private UpdateDataProvider updateDataProvider;
	
	public List<Usuario> getUsuarioList() {
		logger.debug("@WebService invocation -->> getUsuarioList");
		List<Usuario> entitiesList = getListDataBusinesJPAController.findUsuarioEntities(true, -1, -1);
		if(entitiesList ==null){
			entitiesList =  new ArrayList<Usuario>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}

	public List<Industria> getIndustriaList() {
		logger.debug("@WebService invocation -->> getIndustriaList");
		List<Industria> entitiesList = getListDataBusinesJPAController.findIndustriaEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<Industria>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}

	public List<Linea> getLineaList() {
		logger.debug("@WebService invocation -->> getLineaList");
		List<Linea> entitiesList = getListDataBusinesJPAController.findLineaEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<Linea>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}

	public List<Marca> getMarcaList() {
		logger.debug("@WebService invocation -->> getMarcaList");
		List<Marca> entitiesList = getListDataBusinesJPAController.findMarcaEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<Marca>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}

	public List<Producto> getProductoList() {
		logger.debug("@WebService invocation -->> getProductoList");
		List<Producto> entitiesList = getListDataBusinesJPAController.findProductoEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<Producto>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}

	public List<Multimedio> getProductoMultimedioList() {
		logger.debug("@WebService invocation -->> getProductoMultimedioList");
		List<Multimedio> entitiesList = getListDataBusinesJPAController.findMultimedioEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<Multimedio>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}
	
	public List<AlmacenProducto> getAlmacenProductoList(){
		logger.debug("@WebService invocation -->> getAlmacenProductoList");
		List<AlmacenProducto> entitiesList = getListDataBusinesJPAController.findAlmacenProductoEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<AlmacenProducto>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}


	public List<FormaDePago> getFormaDePagoList() {
		logger.debug("@WebService invocation -->> getFormaDePagoList");
		List<FormaDePago> entitiesList = getListDataBusinesJPAController.findFormaDePagoEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<FormaDePago>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}

	public List<Estado> getEstadoList() {
		logger.debug("@WebService invocation -->> getEstadoList");
		List<Estado> entitiesList = getListDataBusinesJPAController.findEstadoEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<Estado>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}
	
	public List<Sucursal> getSucursalList(){
		logger.debug("@WebService invocation -->> getSucursalList");
		List<Sucursal> entitiesList = getListDataBusinesJPAController.findSucursalEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList =  new ArrayList<Sucursal>();
		}
		//logger.debug("\t-->> list size="+entitiesList.size());
		for(Sucursal s: entitiesList){
			//logger.debug("\t-->> "+s.getId()+":"+s.getNombre());
			Collection<Almacen> almacenCollection = s.getAlmacenCollection();
			if(almacenCollection != null) {
				for(Almacen a: almacenCollection){
					//logger.debug("\t\t-->> Almacen:"+a.getId());
				}
			}
		}
		logger.debug("\t-->> list size="+entitiesList.size());		
		return entitiesList;
	}

	public List<Cliente> getClienteList() {
		logger.debug("@WebService invocation -->> getClienteList");
		List<Cliente> entitiesList = getListDataBusinesJPAController.findClienteEntities(true, -1, -1);
		if(entitiesList == null){
			entitiesList = new ArrayList<Cliente>();
		}
		logger.debug("\t-->> list size="+entitiesList.size());
		return entitiesList;
	}

	public String getServerVersion() {
		logger.debug("@WebService invocation -->> getServerVersion");
		String serverVersion = Constants.getServerVersion();
		logger.debug("\t-->> "+serverVersion);
		return serverVersion;
	}
	
	public byte[] getAllDataPackCompressed(){
		List resultList =  new ArrayList();
		byte[] resultBytes = null;
		logger.debug("@WebService invocation -->> getAllDataPackCompressed");
		long t0=System.currentTimeMillis();
		
		resultList.add(getUsuarioList());

		resultList.add(getIndustriaList());
		
		resultList.add(getLineaList());

		resultList.add(getMarcaList());

		resultList.add(getProductoList());

		resultList.add(getProductoMultimedioList());

		resultList.add(getAlmacenProductoList());

		resultList.add(getFormaDePagoList());

		resultList.add(getEstadoList());

		resultList.add(getSucursalList());

		resultList.add(getClienteList());
		
		long t1=System.currentTimeMillis();
		logger.debug("\tgetAllDataPackCompressed: ---------->> end Getting all lists. T="+(t1-t0));
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut;
		try {			
			gzipOut = new GZIPOutputStream(baos);
		 
			ObjectOutputStream objectOutGZ = new ObjectOutputStream(gzipOut);
			objectOutGZ.writeObject(resultList);

			objectOutGZ.close();
			resultBytes = baos.toByteArray();
			
			long t2=System.currentTimeMillis();
			
			logger.debug("\tgetAllDataPackCompressed: resultBytes size="+resultBytes.length+", T = get each "+(t1-t0)+" + compress "+(t2-t1)+" = "+(t2-t0)+" millisecs.");
		
		} catch (IOException ex) {
			logger.error("Serializing List", ex);
		}
		logger.debug("<<-- END getAllDataPackCompressed: returnnig bytes.");
		return resultBytes;	
	}

    public byte[] getUpdateDataPackCompressed(long fechaEventoInicio){
		UpdateDataFacade updateDataFacade = null;
		byte[] resultBytes = null;
		long t0=System.currentTimeMillis();
		
		long t1=System.currentTimeMillis();
		logger.debug("\tgetAllDataPackCompressed: ---------->> end Getting all lists. T="+(t1-t0));
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream gzipOut;
		try {			
			gzipOut = new GZIPOutputStream(baos);
		 
			ObjectOutputStream objectOutGZ = new ObjectOutputStream(gzipOut);
			
			updateDataFacade = updateDataProvider.createUpdateDataFacadeForLastEventsSince(new Date(fechaEventoInicio));
			
			objectOutGZ.writeObject(updateDataFacade);

			objectOutGZ.close();
			resultBytes = baos.toByteArray();
			
			long t2=System.currentTimeMillis();
			
			logger.debug("\tgetUpdateDataPackCompressedl: resultBytes size="+resultBytes.length+", T = get each "+(t1-t0)+" + compress "+(t2-t1)+" = "+(t2-t0)+" millisecs.");
		
		} catch (IOException ex) {
			logger.error("Serializing List", ex);
		}
		logger.debug("<<-- END getAllDataPackCompressed: returnnig bytes.");
		
		return resultBytes;
	}

	public void setGetListDataBusinesJPAController(GetListDataBusinesJPAController getListDataBusinesJPAController) {
		this.getListDataBusinesJPAController = getListDataBusinesJPAController;
	}

	/**
	 * @param updateDataProvider the updateDataProvider to set
	 */
	public void setUpdateDataProvider(UpdateDataProvider updateDataProvider) {
		this.updateDataProvider = updateDataProvider;
	}
}
	