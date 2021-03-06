/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.web.operation;

import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.Almacen;
import com.pmarlen.model.beans.Industria;
import com.pmarlen.model.beans.Linea;
import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Sucursal;
import com.pmarlen.model.controller.IndustriaJPAController;
import com.pmarlen.model.controller.LineaJPAController;
import com.pmarlen.model.controller.MarcaJPAController;
import com.pmarlen.model.controller.SucursalJPAController;
import com.pmarlen.model.dto.InventarioFastView;
import com.pmarlen.model.dto.MovimientoHistoricoProductoFastView;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.model.FileUploaded;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.richfaces.event.DropEvent;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VEAXX9M
 */
public class GaleriaProductosAsignarFotosMB {

	private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;
	private SucursalJPAController sucursalJPAController;
	private IndustriaJPAController industriaJPAController;
	private LineaJPAController lineaJPAController;
	private MarcaJPAController marcaJPAController;
	private Integer industriaSelectedId;
	private Integer lineaSelectedId;
	private Integer marcaSelectedId;
	public static final int SELECTION_MODE_INDUSTRIA_MARCA = 1;
	public static final int SELECTION_MODE_LINEA_MARCA = 2;
	private int selecionMode;
	private List<InventarioFastView> inventarioFastViewList;
	private int scrollerPage;
	private Sucursal sucursalPrincipal;
	private Sucursal sucursalObjetivo;
	private boolean codigoBuscarEnabled;
	private String codigoBuscar;
	private Almacen almacenObjetivo;
	private Hashtable<Integer, String> tipoAlmacenHashTable;
	private List<SelectItem> resultTipoAlmacenList;
	private Integer tipoAlmacen;
	//private Integer almacenId;
	private int numRecShow;
	//==========================================================================
	private LinkedHashMap<String, FileUploaded> files = new LinkedHashMap<String, FileUploaded>();
	private int uploadsAvailable = 50;
	private boolean autoUpload = false;
	private boolean useFlash = false;
	private final Logger logger = LoggerFactory.getLogger(GaleriaProductosAsignarFotosMB.class);

	public boolean getCodigoBuscarEnabled() {
		return codigoBuscarEnabled;
	}

	public void setCodigoBuscarEnabled(boolean codigoBuscarEnabled) {
		this.codigoBuscarEnabled = codigoBuscarEnabled;
	}

	public GaleriaProductosAsignarFotosMB() {
		numRecShow = 25;
		selecionMode = SELECTION_MODE_INDUSTRIA_MARCA;
		codigoBuscarEnabled = false;
		//almacenId = 1;
		tipoAlmacen = Constants.ALMACEN_LINEA;
	}
	private List<Industria> industriaList = null;
	private List<Linea> lineaList = null;
	private List<Marca> marcaList = null;

	public List<SelectItem> getIndustriaList() {
		if (industriaList == null) {
			industriaList = industriaJPAController.findAllEntities();
		}
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

		for (Industria industria : industriaList) {
			resultList.add(new SelectItem(industria.getId(), industria.getNombre()));
		}
		return resultList;
	}

	public List<SelectItem> getLineaList() {
		if (lineaList == null) {
			lineaList = lineaJPAController.findAllEntities();
		}
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

		for (Linea linea : lineaList) {
			resultList.add(new SelectItem(linea.getId(), linea.getNombre()));
		}
		return resultList;
	}

	public List<SelectItem> getMarcaList() {
		if (marcaList == null) {
			marcaList = marcaJPAController.findAllEntities();
		}
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));
		if (selecionMode == SELECTION_MODE_INDUSTRIA_MARCA && industriaSelectedId != null) {
			for (Marca marca : marcaList) {
				if (marca.getIndustria().getId().intValue() == industriaSelectedId) {
					resultList.add(new SelectItem(marca.getId(), marca.getNombre()));
				}
			}
		} else if (selecionMode == SELECTION_MODE_LINEA_MARCA && lineaSelectedId != null) {
			for (Marca marca : marcaList) {
				if (marca.getLinea().getId().intValue() == lineaSelectedId) {
					resultList.add(new SelectItem(marca.getId(), marca.getNombre()));
				}
			}
		}
		return resultList;
	}

	public void updateRecShow(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String numRecShowToSet = context.getExternalContext().getRequestParameterMap().get("numRecShowToSet");
		numRecShow = Integer.parseInt(numRecShowToSet);
		scrollerPage = 1;

		logger.debug(">> updateRecShow: numRecShowToSet=" + numRecShowToSet + ", scrollerPage=" + scrollerPage);

		//reinicializarLista();		
		//actualizarLista();
	}

	public void reinicializarLista() {
		logger.debug(">> reinicializarLista");
		inventarioFastViewList = null;
		scrollerPage = 1;
	}

	public List<InventarioFastView> getInventarioFastViewList() {
		if (inventarioFastViewList == null) {
			actualizarLista();
		}
		return inventarioFastViewList;
	}

	public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
		this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
	}

	public void setSucursalJPAController(SucursalJPAController sucursalJPAController) {
		this.sucursalJPAController = sucursalJPAController;
	}

	/**
	 *
	 * @param scrollerPage
	 */
	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	/**
	 *
	 * @return scrollerPage
	 */
	public int getScrollerPage() {
		return scrollerPage;
	}

	/**
	 * @return the numRecShow
	 */
	public int getNumRecShow() {
		return numRecShow;
	}

	/**
	 * @param numRecShow the numRecShow to set
	 */
	public void setNumRecShow(int numRecShow) {
		this.numRecShow = numRecShow;
	}

	public int getEntityCount() {
		if (inventarioFastViewList != null) {
			return inventarioFastViewList.size();
		} else {
			return 0;
		}
	}

	private void actualizarLista() {
		try {
			Integer almacenId = getAlmacenObjetivo().getId();
			logger.debug(">> actualizarLista : almacenId=" + almacenId + ", marcaSelectedId=" + marcaSelectedId + ", industriaSelectedId=" + industriaSelectedId + ", lineaSelectedId=" + lineaSelectedId + ", selecionMode=" + selecionMode);

			if (marcaSelectedId != null) {
				logger.debug(">> actualizarLista : Find by marca");
				inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastViewByMarca(almacenId, marcaSelectedId);
			} else if (selecionMode == SELECTION_MODE_INDUSTRIA_MARCA && industriaSelectedId != null) {
				logger.debug(">> actualizarLista : Find by industria");
				inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastViewByIndustria(almacenId, industriaSelectedId);
			} else if (selecionMode == SELECTION_MODE_LINEA_MARCA && lineaSelectedId != null) {
				logger.debug(">> actualizarLista : Find by linea");
				inventarioFastViewList = pedidoVentaBusinessLogic.findInventarioFastViewByLinea(almacenId, lineaSelectedId);
			}

			if (inventarioFastViewList != null) {
				logger.debug(">> actualizarLista : after inventarioFastViewList size =" + inventarioFastViewList.size());
			} else {
				logger.debug(">> actualizarLista : after inventarioFastViewList EMPTY :(  ");
			}

		} catch (Exception e) {
			logger.error(">> actualizarLista : ", e);
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al filtrar:", e.getMessage()));
		}
	}

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
		if (tipoAlmacen > 0) {
			return getTipoAlmacenHashTable().get(tipoAlmacen);
		} else {
			return "-";
		}
	}

	public Integer getTipoAlmacen() {
		return tipoAlmacen;
	}

	public void setTipoAlmacen(Integer tipoAlmacen) {
		this.tipoAlmacen = tipoAlmacen;
	}

	public void almacenSelected(ValueChangeEvent event) {
		logger.info(">> almacenSelected: old=" + event.getOldValue() + ", new=" + event.getNewValue());
		tipoAlmacen = (Integer) event.getNewValue();
		almacenObjetivo = null;
		industriaSelectedId = null;
		lineaSelectedId = null;
		marcaSelectedId = null;
		inventarioFastViewList = null;
		actualizarLista();
	}

	public void selectModeChanged(ValueChangeEvent event) {
		logger.info(">> selectModeChanged: old=" + event.getOldValue() + ", new=" + event.getNewValue());
		selecionMode = (Integer) event.getNewValue();
		industriaSelectedId = null;
		lineaSelectedId = null;
		marcaSelectedId = null;
		inventarioFastViewList = null;
		actualizarLista();
	}

	public void industriaChanged(ValueChangeEvent event) {
		logger.info(">> industriaChanged: old=" + event.getOldValue() + ", new=" + event.getNewValue());
		industriaSelectedId = (Integer) event.getNewValue();
		lineaSelectedId = null;
		marcaSelectedId = null;
		inventarioFastViewList = null;
		actualizarLista();
	}

	public void lineaChanged(ValueChangeEvent event) {
		logger.info(">> lineaChanged: old=" + event.getOldValue() + ", new=" + event.getNewValue());
		lineaSelectedId = (Integer) event.getNewValue();
		industriaSelectedId = null;
		marcaSelectedId = null;
		inventarioFastViewList = null;
		actualizarLista();
	}

	public void marcaChanged(ValueChangeEvent event) {
		logger.info(">> marcaChanged: old=" + event.getOldValue() + ", new=" + event.getNewValue());
		marcaSelectedId = (Integer) event.getNewValue();
		inventarioFastViewList = null;
		actualizarLista();
	}

	public Sucursal getSucursalObjetivo() {
		if (sucursalObjetivo == null) {
			sucursalPrincipal = sucursalJPAController.getSucursalPrincipal();
			sucursalObjetivo = sucursalPrincipal;
		}

		return sucursalObjetivo;
	}

	/**
	 * @return the almacenObjetivo
	 */
	public Almacen getAlmacenObjetivo() {
		if (almacenObjetivo == null) {
			Collection<Almacen> almacenCollection = getSucursalObjetivo().getAlmacenCollection();
			for (Almacen a : almacenCollection) {
				if (a.getTipoAlmacen() == tipoAlmacen) {
					almacenObjetivo = a;
					break;
				}
			}

		}
		return almacenObjetivo;
	}

	public String getAlmacenObjetivoDescripcionTipo() {
		return getTipoAlmacenHashTable().get(getAlmacenObjetivo().getTipoAlmacen());
	}

	public String getCodigoBuscar() {
		return codigoBuscar;
	}

	public void setCodigoBuscar(String codigoBuscar) {
		this.codigoBuscar = codigoBuscar;
	}

	public void codigoBuscarEnabledChanged(ActionEvent event) {
		logger.debug(">> codigoBuscarEnabledChanged: codigoBuscarEnabled=" + codigoBuscarEnabled);
		if (codigoBuscarEnabled == false && inventarioFastViewList.size() == 1) {
			codigoBuscar = null;
			reinicializarLista();
			actualizarLista();
		}
	}

	public void codigoBuscarChanged(ValueChangeEvent event) {
		logger.debug(">> codigoBuscarChanged: old=" + event.getOldValue() + ", new=" + event.getNewValue() + ", codigoBuscar=" + codigoBuscar);
		codigoBuscar = event.getNewValue().toString();
		refrescarFiltroCodigoBarras();
	}

	public void buscarProductoPorCodigo(ActionEvent e) {
		logger.debug(">> buscarProductoPorCodigo: codigoBuscar=" + codigoBuscar);
		refrescarFiltroCodigoBarras();
	}

	private void refrescarFiltroCodigoBarras() {
		logger.debug(">> refrescarFiltroCodigoBarras: codigoBuscar= ->" + codigoBuscar + "<-");
		if (codigoBuscar != null && codigoBuscar.trim().length() >= 5) {
			reinicializarLista();
			actualizarLista();
		}
	}

	public List<MovimientoHistoricoProductoFastView> getMovimientoHistoricoProductoFastView() {
		if (codigoBuscarEnabled && codigoBuscar != null) {
			return pedidoVentaBusinessLogic.findMovimientoHistoricoProductoFastView(tipoAlmacen, codigoBuscar);
		} else {
			return null;
		}
	}

	/**
	 * @param industriaJPAController the industriaJPAController to set
	 */
	public void setIndustriaJPAController(IndustriaJPAController industriaJPAController) {
		this.industriaJPAController = industriaJPAController;
	}

	/**
	 * @param lineaJPAController the lineaJPAController to set
	 */
	public void setLineaJPAController(LineaJPAController lineaJPAController) {
		this.lineaJPAController = lineaJPAController;
	}

	/**
	 * @param marcaJPAController the marcaJPAController to set
	 */
	public void setMarcaJPAController(MarcaJPAController marcaJPAController) {
		this.marcaJPAController = marcaJPAController;
	}

	public void setSelecionMode(int selecionMode) {
		this.selecionMode = selecionMode;
	}

	public int getSelecionMode() {
		return selecionMode;
	}

	public int getSELECTION_MODE_INDUSTRIA_MARCA() {
		return SELECTION_MODE_INDUSTRIA_MARCA;
	}

	public int getSELECTION_MODE_LINEA_MARCA() {
		return SELECTION_MODE_LINEA_MARCA;
	}

	/**
	 * @return the industriaSelectedId
	 */
	public Integer getIndustriaSelectedId() {
		return industriaSelectedId;
	}

	/**
	 * @param industriaSelectedId the industriaSelectedId to set
	 */
	public void setIndustriaSelectedId(Integer industriaSelectedId) {
		this.industriaSelectedId = industriaSelectedId;
	}

	/**
	 * @return the lineaSelectedId
	 */
	public Integer getLineaSelectedId() {
		return lineaSelectedId;
	}

	/**
	 * @param lineaSelectedId the lineaSelectedId to set
	 */
	public void setLineaSelectedId(Integer lineaSelectedId) {
		this.lineaSelectedId = lineaSelectedId;
	}

	/**
	 * @return the marcaSelectedId
	 */
	public Integer getMarcaSelectedId() {
		return marcaSelectedId;
	}

	/**
	 * @param marcaSelectedId the marcaSelectedId to set
	 */
	public void setMarcaSelectedId(Integer marcaSelectedId) {
		this.marcaSelectedId = marcaSelectedId;
	}

	//==========================================================================
	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public void listener(UploadEvent event) throws Exception {
		UploadItem item = event.getUploadItem();
		
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		final String tmpDir = System.getProperty("java.io.tmpdir");
		logger.debug("-------->>>>>>>listener:tmpDir="+tmpDir);
		
		FileUploaded file = new FileUploaded();

		file.setLength(item.getData().length);
		file.setName(item.getFileName());
		file.setData(item.getData());
		file.setId(Constants.getMD5Encrypted(file.getName() + System.currentTimeMillis()));
		file.setPathDownloaded(tmpDir+File.separator+file.getId());

		if (file.isZipFile()) {
			logger.debug("listener:File zip :" + file.getName());
			//get the zip file content
			ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(file.getData()));
			//get the zipped file list entry
			ZipEntry ze = null;
			while ((ze = zis.getNextEntry()) != null) {
				String mime = FileUploaded.getAccordingMime(ze.getName());
				logger.debug("listener:\t->File unzip : name=" + ze.getName() + ", realbytes=" + ze.getSize() + ", compressed size=" + ze.getCompressedSize() + ", date=" + new Date(ze.getTime()) + ", mime=" + mime);

				if (mime.startsWith("image/")) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[8192];
					int len;
					while ((len = zis.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					baos.close();

					FileUploaded fileInZip = new FileUploaded();

					fileInZip.setLength(ze.getCompressedSize());
					fileInZip.setName(ze.getName());
					fileInZip.setData(baos.toByteArray());
					fileInZip.setId(Constants.getMD5Encrypted(ze.getName() + System.currentTimeMillis()));
					fileInZip.setPathDownloaded(tmpDir+File.separator+fileInZip.getId());
					
					fileInZip.downloadToPath();
					files.put(fileInZip.getId(), fileInZip);
					uploadsAvailable--;
				}
			}
		} else {
			if(file.isAccepted()){
				file.downloadToPath();
				files.put(file.getId(), file);
				uploadsAvailable--;
			}
		}

	}

	public void refreshUploadData(ActionEvent e) {
	}

	public String clearUploadData() {
		files.clear();
		setUploadsAvailable(50);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<FileUploaded> getFiles() {
		ArrayList<FileUploaded> r = new ArrayList<FileUploaded>();
		for (String idk : files.keySet()) {
			r.add(files.get(idk));
		}
		return r;
	}
	/*
	 public void setFiles(Collection<FileUploaded> fs) { 
	 this.files = new LinkedHashMap<String,FileUploaded>();
	 for(FileUploaded f:fs){
	 this.files.put(f.getId(), f);
	 }        
	 }
	 */

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

	public FileUploaded getFileUploaded(String id) {
		return files.get(id);
	}
	
	public void processDrop(DropEvent de){
		final Object dropValue = de.getDropValue();
		logger.info("->dorp:"+dropValue.getClass());
	}
	
	public void selectPicture(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String fileId = context.getExternalContext().getRequestParameterMap().get("fileId");
		
		logger.debug(">> selectPicture: fileId=" + fileId);
		for(String fids: files.keySet()){
			files.get(fids).setSelected(false);
		}
		
		files.get(fileId).setSelected(true);
	}
	
	public void assignFileUpload(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		int productoId = Integer.parseInt(context.getExternalContext().getRequestParameterMap().get("productoId"));
		
		logger.debug(">> assignFileUpload: productoId=" + productoId);
		InventarioFastView ifvSelected = null;
		for(InventarioFastView ifv:inventarioFastViewList){
			if(ifv.getProductoId().intValue() == productoId){
				ifvSelected = ifv;
				break;
			}
		}
		if(ifvSelected != null) {
			String fileSelectedId = null;
			for(String fids: files.keySet()){
				if(files.get(fids).isSelected()){
					fileSelectedId = fids;
					break;
				}
			}
			
			if(fileSelectedId != null){
				ifvSelected.setFileUploaded(files.get(fileSelectedId));
				logger.debug(">> assignFileUpload: -->>ASSIGNED ");
				files.remove(fileSelectedId);
				uploadsAvailable++;
			}
		}
	}	
}
