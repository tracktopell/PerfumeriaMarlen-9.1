package com.pmarlen.web.operation;

import com.pmarlen.model.beans.Marca;
import com.pmarlen.model.beans.Producto;
import com.pmarlen.model.controller.MarcaJPAController;
import com.pmarlen.model.controller.ProductoJPAController;
import com.pmarlen.web.common.view.messages.Messages;
import com.pmarlen.web.common.view.validator.ValidationException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductoMB extends EntityCRUDMB<Producto>{
	private MarcaJPAController marcaJPAController;
	
	private List<Marca> marcaList;
	
	public ProductoMB() {		
		super(LoggerFactory.getLogger(ProductoMB.class));
		entity = new Producto();
		entity.setMarca(new Marca());
	}

	@Autowired
	public void setMarcaJPAController(MarcaJPAController marcaJPAController) {
		this.marcaJPAController=marcaJPAController;
	}
	
	@Autowired
	public void setProductoJPAController(ProductoJPAController productoJPAController) {
		super.setEntityJPAController(productoJPAController);
	}
	@Override
	public void preparaNuevoRegistro(ActionEvent e) {
		super.preparaNuevoRegistro(e);
		entity.setMarca(new Marca());
	}
	
	public List<SelectItem> getMarcaList() {
        List<Marca> marcaList = marcaJPAController.findAllEntities();
        List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));
		
		List<Marca> sortedMarcaList = marcaList; //sortMarca(marcaList);
        for (Marca marca : marcaList) {
            resultList.add(new SelectItem(marca.getId(),"("+marca.getLinea().getNombre()+")"+"["+marca.getIndustria().getNombre()+"] "+ marca.getNombre()));
        }
        return resultList;
    }
	
	public List<SelectItem> getUnidadDeMedidaList(){
		List<SelectItem> resultList = new ArrayList<SelectItem>();
        resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));	
	
		resultList.add(new SelectItem("PZ","PZ"));
		resultList.add(new SelectItem("ML", "ML"));
		resultList.add(new SelectItem("OZ", "OZ"));
		resultList.add(new SelectItem("G", "G"));
		resultList.add(new SelectItem("MG", "MG"));
		resultList.add(new SelectItem("LT", "LT"));
		resultList.add(new SelectItem("M", "M"));
		resultList.add(new SelectItem("L", "L"));
		resultList.add(new SelectItem("KG", "KG"));
		resultList.add(new SelectItem("K", "K"));
		resultList.add(new SelectItem("TABS", "TABS"));

	return resultList;
	}
	
	private List<Marca> sortMarca(List<Marca> ml){
		
		SortedSet<Marca> ss = new TreeSet<Marca>(new Comparator<Marca>(){

			@Override
			public int compare(Marca m1, Marca m2) {
				int r1 = m1.getLinea().getNombre().compareTo(m2.getLinea().getNombre());
				int r2 = m1.getIndustria().getNombre().compareTo(m2.getIndustria().getNombre());
				int r3 = m1.getNombre().compareTo(m2.getNombre());
				return r1 * 100000000 + r2 * 10000 +r3;
			}
		});
		final Iterator<Marca> iterator = ss.iterator();
		List<Marca> result=new ArrayList<Marca>();
		while(iterator.hasNext()){
			result.add(iterator.next());
		}
		return result;
	}
	

	@Override
	public void specialValidatationForm() throws ValidationException {
		logger.debug("specialValidatationForm: entity=" + entity);
		Producto productoByCodigoBarras=null;
		try{
			productoByCodigoBarras = entityJPAController.findEntityByReadableProperty(entity.getCodigoBarras());
		} catch(NoResultException nsre){
			productoByCodigoBarras = null;
		}
		logger.debug("specialValidatationForm: productoByCodigoBarras=" + productoByCodigoBarras);

		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		if (productoByCodigoBarras != null && getEntityId()==null) {
			prepraredMessages.put("formSecundario:inputTextNombre",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
					(getEntityId() == null ? Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE")
					: Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT")) + " : ",
					Messages.getLocalizedString("PRODUCTO_CRUD_RECORD_EXIST_SAME_CODIGOBARRAS")));

			throw new ValidationException(prepraredMessages);
		}
	}

	@Override
	public Object getEntityId() {
		if(entity == null){
			entity = new Producto();
		}
		return entity.getId();
	}
	
	@Override
	protected Object parseToObjectIdValue(String id) {
		return new Integer(id);
	}
	/*
	private Producto producto;
	private List<Producto> productoList;
	private int scrollerPage;
	private ProductoJPAController productoJPAController;
	private MarcaJPAController marcaJPAController;
	private List<Marca> marcaList;
	private LineaJPAController lineaJPAController;
	private List<Linea> lineaList;
	//private ArrayList<MultimedioFile> multimedioFiles = new ArrayList<MultimedioFile>();
	private LinkedHashMap<String, MultimedioFile> multimedioFiles;
	private int uploadsAvailable = 5;
	private boolean autoUpload = true;
	private boolean useFlash = false;
	private final Logger logger = LoggerFactory.getLogger(ProductoMB.class);

	public ProductoMB() {
		logger.debug("=============>> ProductoMB created.");
		multimedioFiles = new LinkedHashMap<String, MultimedioFile>();
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("multimedioFiles", multimedioFiles);
	}

	public List<Producto> getProductoList() {
		actualizarLista();
		return productoList;
	}

	public List<SelectItem> getMarcaList() {
		List<Marca> marcaList = marcaJPAController.findAllEntities();
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

		for (Marca marca : marcaList) {
			resultList.add(new SelectItem(marca.getId(), marca.getNombre()));
		}
		return resultList;
	}

	public List<SelectItem> getLineaList() {
		List<Linea> lineaList = lineaJPAController.findAllEntities();
		List<SelectItem> resultList = new ArrayList<SelectItem>();
		resultList.add(new SelectItem(null, Messages.getLocalizedString("COMMON_SELECTONEITEM")));

		for (Linea linea : lineaList) {
			resultList.add(new SelectItem(linea.getId(), linea.getNombre()));
		}
		return resultList;
	}

	public void preparaNuevoRegistro(ActionEvent e) {

		logger.debug("===>>preparaNuevoRegistro: ");

		producto = new Producto();
		producto.setMarca(new Marca());

		multimedioFiles.clear();
		setUploadsAvailable(5);

		logger.debug("\tproducto=" + producto);
	}

	public void cancelarGuardar(ActionEvent e) {

		logger.debug("-->> cancelarGuardar:");

		//producto = new Producto();
	}

	public void specialValidatationForm() throws ValidationException {
		Producto productoByCodigoBarras = null;

		try {
			productoByCodigoBarras = productoJPAController.findProductoBycodigoBarras(producto.getCodigoBarras());
		} catch (NoResultException nsre) {
			productoByCodigoBarras = null;
		}

		logger.debug("specialValidatationForm: productoByCodigoBarras=" + productoByCodigoBarras);

		Hashtable<String, FacesMessage> prepraredMessages = new Hashtable<String, FacesMessage>();

		if (productoByCodigoBarras != null && producto.getId() == null) {
			prepraredMessages.put("formSecundario:inputTextCodigoBarras",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
					(producto.getId() == null ? Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE")
					: Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT")) + " : ",
					Messages.getLocalizedString("COMMON_CRUD_RECORD_EXIST_SAME_DESCRIPTION")));

			throw new ValidationException(prepraredMessages);
		}
	}

	public void aceptarGuardar(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			specialValidatationForm();

		} catch (ValidationException ve) {
			Hashtable<String, FacesMessage> prepraredMessages = ve.getPrepraredMessages();
			for (String componentId : prepraredMessages.keySet()) {
				context.addMessage(componentId, prepraredMessages.get(componentId));
			}
			return;
		}

		if (producto.getId() == null) {
			guardarNuevo();
		} else {
			actualizar();
		}
	}

	public void guardarNuevo() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			productoJPAController.create(producto, multimedioFiles.values());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					Messages.getLocalizedString("COMMON_CRUD_OK") + " : ",
					Messages.getLocalizedString("COMMON_CRUD_OK_CREATE")));

			productoList = null;
			actualizarLista();
		} catch (Exception ex) {
			logger.error("guardarNuevo", ex);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getLocalizedString("COMMON_CRUD_ERROR_CREATE") + " : ",
					ex.getMessage().toUpperCase()));
		}
	}

	public void actualizar() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			productoJPAController.edit(producto, multimedioFiles.values());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					Messages.getLocalizedString("COMMON_CRUD_OK") + " : ",
					Messages.getLocalizedString("COMMON_CRUD_OK_UPDATE")));
			productoList = null;
			actualizarLista();
		} catch (Exception ex) {
			logger.error("actualizar", ex);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getLocalizedString("COMMON_CRUD_ERROR_EDIT") + " : ",
					ex.getMessage().toUpperCase()));
		}
	}

	public void preparEliminarRegistro(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String productoId = context.getExternalContext().getRequestParameterMap().get("productoId");

		producto = productoJPAController.findProducto(Integer.parseInt(productoId));

		logger.debug("-->> preparEliminarRegistro: producto=" + producto);
	}

	public void eliminarRegistro(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		logger.debug("-->> eliminarRegistro: producto.id=" + producto.getId());

		try {

			productoJPAController.destroy(producto.getId());

			logger.debug("==>> OK, removed: " + producto);

			productoList = null;
			actualizarLista();

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					Messages.getLocalizedString("COMMON_CRUD_OK") + " : ",
					Messages.getLocalizedString("COMMON_CRUD_OK_DELETE")));

		} catch (IllegalOrphanException ex) {

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					Messages.getLocalizedString("COMMON_CRUD_WARNING_DELETE") + " : ",
					ex.getMessages().toString().replace("[", "").replace("]", "").toUpperCase()));

		} catch (Exception ex) {
			logger.error("eliminarRegistro", ex);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					Messages.getLocalizedString("COMMON_CRUD_ERROR_DELETE") + " : ",
					ex.getMessage()));
		}

	}
	private String detalleProductoId;

	public String getDetalleProductoId() {
		return this.detalleProductoId;
	}

	public void setDetalleProductoId(String detalleProductoId) {
		this.detalleProductoId = detalleProductoId;
	}

	public void editarRegistro(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String productoId = context.getExternalContext().getRequestParameterMap().get("productoId");

		producto = productoJPAController.findProducto(Integer.parseInt(productoId));
		Collection<Multimedio> multimedioCollection = producto.getMultimedioCollection();

		multimedioFiles.clear();
		setUploadsAvailable(5);
		int errorConter     = 0;
		int indexMultimedio = 0;
		long currentTimeMillis = System.currentTimeMillis();
		byte[] data =null;
		for (Multimedio m : multimedioCollection) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(m.getRutaContenido());
				MultimedioFile mf = new MultimedioFile();
				data = new byte[m.getSizeBytes()];
				fis.read(data);
				mf.setData(data);
				mf.setName(m.getNombreArchivo());
				mf.setLength(m.getSizeBytes());
				
				mf.setId("PM_" + currentTimeMillis+indexMultimedio);
				multimedioFiles.put(mf.getId(), mf);
				indexMultimedio++;
				fis.close();
				fis=null;
				data=null;
			} catch (Exception ex) {
				logger.error("editarRegistro:",ex);
			} 
		}
		setUploadsAvailable(5-multimedioFiles.size());
		logger.debug("-->> editarRegistro: producto=" + producto);
	}

	private void actualizarLista() {
		if (productoList == null) {
			productoList = productoJPAController.findProductoEntities();
		}
	}

	public int getProductoCount() {
		if (productoList != null) {
			return productoList.size();
		} else {
			return 0;
		}
	}

	//==========================================================================
	public void actualizaIconos(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String productoId = context.getExternalContext().getRequestParameterMap().get("productoId");
		logger.debug("===>>>actualizaIconos:productoId=" + productoId);
		try {
			int updated = 0;
			updated = productoJPAController.updateMultimedioWebIcons(Integer.parseInt(productoId));
			if(updated>0){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"ACTUALIZAR ICONOS : ",
						"SE ACTUALIZARÓN "+updated+" ICONOS"));
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"ACTUALIZAR ICONOS : ",
						"NO TIENE NINGÚN ICONO AGREGADO TODAVIA"));
			}
		} catch(IOException ioe){
			logger.error("Error al actualizar Iconos:",ioe);
				
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"ACTUALIZAR ICONOS : ",
				ioe.getMessage());

			context.addMessage(null,fm);
		}
	}
	public int getSize() {
		if (getMultimedioFiles().size() > 0) {
			return getMultimedioFiles().size();
		} else {
			return 0;
		}
	}
	
	public int getPreferredColumns() {
		if (getMultimedioFiles().size() >= 3) {
			return 3;
		} else if (getMultimedioFiles().size() == 2) {		
			return 2;
		} else {
			return 1;
		}
	}

	public void listener(UploadEvent event) throws Exception {
		logger.debug("-->>listener:");
		UploadItem item = event.getUploadItem();
		MultimedioFile mf = new MultimedioFile();
		mf.setName(item.getFileName());
		final byte[] data = item.getData();

		ImageProcessorForMultimedio ipm = ImageProcessorForMultimedio.getInstance();

		byte[] processedData = ipm.transformImage(data);
		mf.setLength(processedData.length);		
		mf.setData(processedData);
		mf.setId("PM_" + System.currentTimeMillis());

		multimedioFiles.put(mf.getId(), mf);

		logger.debug("\t-->>multimedioFiles.add(" + mf.getData().length + " bytes)");

		uploadsAvailable--;
	}

	public void removeUploadData(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();

		String multimedioFileId = context.getExternalContext().getRequestParameterMap().get("multimedioFileId");
		logger.debug("===>>>removeUploadData:multimedioFileId=" + multimedioFileId);
		if(multimedioFiles.containsKey(multimedioFileId) ){
			multimedioFiles.remove(multimedioFileId);
			setUploadsAvailable(5-multimedioFiles.size());
		}
	}
	
	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public Collection<MultimedioFile> getMultimedioFiles() {
		Collection<MultimedioFile> mfc = new ArrayList<MultimedioFile>();

		Set<String> keySet = multimedioFiles.keySet();
		for (String k : keySet) {
			mfc.add(multimedioFiles.get(k));
		}
		return mfc;
	}
	
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
	*/

}
