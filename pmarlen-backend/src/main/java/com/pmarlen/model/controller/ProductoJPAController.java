package com.pmarlen.model.controller;

import com.pmarlen.businesslogic.AlmacenProductoDemanda;
import com.pmarlen.businesslogic.PedidoVentaEnDemanda;
import com.pmarlen.model.beans.*;
import com.pmarlen.util.QueryXMLLoader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ProductoJPAController
 */
@Repository("productoJPAController")
public class ProductoJPAController extends EntityJPAController<Producto> {

	public ProductoJPAController() {
		super(LoggerFactory.getLogger(ProductoJPAController.class));
	}

	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Producto newEntityInstance() {
		Producto productoNewInstance = new Producto();
		logger.debug("->newEntityInstance: productoNewInstance=" + productoNewInstance);
		return productoNewInstance;
	}

	@Override
	protected Class getEntityClass() {
		return Producto.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Producto";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(Producto entity) {
		return entity.getId();
	}

	@Override
	public Producto findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Producto x where upper(x.codigoBarras) = upper(:codigoBarras)");

			q.setParameter("codigoBarras", readable);

			Producto x = (Producto) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object productoId, List<String> illegalOrphanMessages) {
		logger.debug("->validateBeforeDestroy: productoId=" + productoId);
		Query q = null;
		int countRelated = -1;
		String relatedEntities[] = {
			"AlmacenProducto",
			"MovimientoHistoricoProducto",
			"MovimientoOperativoAlmacenDetalle",
			"NotaCreditoDetalle",
			"PedidoCompraDetalle",
			"PedidoVentaDetalle",
			"ProveedorProducto"};
		for(String re:relatedEntities){
			q = em.createQuery("select count(x) from "+re+" x where x.producto.id = :productoId");
			q.setParameter("productoId", productoId);
			countRelated = ((Long) q.getSingleResult()).intValue();

			if (countRelated > 0) {
				final String name = "Hay " + countRelated + " "+re+"(s), relacionados a este Producto";
				illegalOrphanMessages.add(name.toUpperCase());
			}
		}		
	}
	
	public List<AlmacenProducto> findAllValidProductosForAlmacen(int almacenId) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select ap from AlmacenProducto ap where ap.almacen.id=:almacenId and ap.cantidadActual>0 order by ap.producto.nombre, ap.producto.presentacion");
			q.setParameter("almacenId", almacenId);
			List<AlmacenProducto> resultList = (List<AlmacenProducto>) q.getResultList();
			//for (AlmacenProducto x : resultList) {
				//retrieveEagerAtributes(x);				
			//}
			return resultList;
		} finally {
			em.close();
		}
	}

	final static String queryDemanda =  "SELECT    COUNT(PV.ID) OTROS_PEDIDOS, "+
			                            "          P.ID, " +
										"          SUM(PVD.CANTIDAD) SUM_DEMANDA, " +
										"          PV.ALMACEN_ID " +
										"FROM      PEDIDO_VENTA_DETALLE PVD, " +
										"          PEDIDO_VENTA PV, " +
										"          PRODUCTO P, " +
										"          ( " +
										"                SELECT    PEDIDO_VENTA_ID,MAX(ESTADO_ID) " +
										"                FROM      PEDIDO_VENTA_ESTADO PVE,PEDIDO_VENTA PV " +
										"                WHERE     PVE.PEDIDO_VENTA_ID = PV.ID " +
										"                AND       ESTADO_ID < 8 " +
										"                AND       PV.ALMACEN_ID=:almacenId " +
										"                GROUP BY  PVE.PEDIDO_VENTA_ID " +
										"          ) RX " +
										"WHERE     1=1 " +
										"AND       PV.ID           = PVD.PEDIDO_VENTA_ID " +
										"AND       PVD.PRODUCTO_ID = P.ID " +
										"AND       PV.ID           = RX.PEDIDO_VENTA_ID " +
										"GROUP BY  P.ID";
	
	public Hashtable<Integer,AlmacenProductoDemanda> findDemandaProductosForAlmacen(int almacenIdRearch) {		
		logger.debug("->findDemandaProductosForAlmacen: almacenId="+almacenIdRearch);
		
		EntityManager em = getEntityManager();
		Hashtable<Integer,AlmacenProductoDemanda> resultHT = new Hashtable<Integer,AlmacenProductoDemanda>();
		
		try {			
			Query q = em.createNativeQuery( queryDemanda);
			q.setParameter("almacenId", almacenIdRearch);
			
			List<Object[]> originalList = q.getResultList();
			int productoId=-1;
			int otrosPedidos = -1;
			int sumDemanda = -1;
			int almacenId  = -1;
	
			AlmacenProductoDemanda almacenProductoDemanda = null;
			for(Object[] resultRow:originalList){
				
				productoId   = ((Integer)		resultRow[1]).intValue();
				sumDemanda   = ((BigDecimal)	resultRow[2]).intValue();
				almacenId    = ((Integer)		resultRow[3]).intValue();
				otrosPedidos = ((BigInteger)	resultRow[0]).intValue();
				
				almacenProductoDemanda = null;
				
				resultHT.put(productoId, almacenProductoDemanda);
				//logger.debug("->put: almacenProductoDemanda="+almacenProductoDemanda);
			}
			
			return resultHT;
		} finally {
			em.close();
		}
	}
	
	public AlmacenProductoDemanda findDemandaProductoForAlmacen(int almacenIdRearch,int pId) {		
		//logger.debug("->findDemandaProductoForAlmacen: almacenId="+almacenIdRearch+", pId="+pId);
		
		EntityManager em = getEntityManager();
		Hashtable<Integer,AlmacenProductoDemanda> resultHT = new Hashtable<Integer,AlmacenProductoDemanda>();
		
		try {			
			Query q = em.createNativeQuery(QueryXMLLoader.getInstance().loadQuery("demandaProductoXAlmacen"));
			q.setParameter("almacenId", almacenIdRearch);
			q.setParameter("productoId", pId);
			
			List<Object[]> originalList = q.getResultList();
			int productoId=-1;
			int otrosPedidos		= 0;
			int sumDemanda			= 0;
			int almacenId			= -1;
			int cantidadActual		= 0;			
			double precioVenta		= 0.0;			
			int pedidoVentaId		= -1;
			int pedidoVentaEstado	= 0;
			int catPedidaPD			=0;
			List<PedidoVentaEnDemanda> pvedList = new ArrayList<PedidoVentaEnDemanda>(); 
			String productoCodigoBarras = null;
	
			AlmacenProductoDemanda almacenProductoDemanda = null;
			int counter=0;
			for(Object[] resultRow:originalList){
				//                  0               1            2             3                  4                     5                6
				//P.ID AS PRODUCTO_ID,P.CODIGO_BARRAS,PVD.CANTIDAD,PV.ALMACEN_ID,AP.CANTIDAD_ACTUAL,PV.ID PEDIDO_VENTA_ID,RX.MAX_ESTADO_ID
				
				productoId				= ((Integer)	resultRow[0]).intValue();
				productoCodigoBarras	= (String)		resultRow[1];
				catPedidaPD				=((Integer)	resultRow[2]).intValue();
				sumDemanda				+= catPedidaPD;
				almacenId				= ((Integer)	resultRow[3]).intValue();				
				cantidadActual			= ((Integer)	resultRow[4]).intValue();
				precioVenta				= ((Double)		resultRow[5]).doubleValue();
				pedidoVentaId			= ((Integer)	resultRow[6]).intValue();
				pedidoVentaEstado		= ((Integer)	resultRow[7]).intValue();
				otrosPedidos			+= 1;
				counter++;
				pvedList.add(new PedidoVentaEnDemanda(pedidoVentaId, pedidoVentaEstado,catPedidaPD));				
				//logger.debug("\t\t->findDemandaProductoForAlmacen: productoCodigoBarras="+productoCodigoBarras+", sumDemanda="+sumDemanda+", cantidadActual="+cantidadActual);		
			}
			if(counter==0){
				Query q2 = em.createQuery("select ap from AlmacenProducto ap where ap.producto.id=:productoId and ap.almacen.id=:almacenId order by ap.producto.id,ap.producto.codigoBarras");
				q2.setParameter("almacenId", almacenIdRearch);
				q2.setParameter("productoId", pId);
				AlmacenProducto ap = (AlmacenProducto)q2.getSingleResult();
				productoId				= pId;
				productoCodigoBarras	= ap.getProducto().getCodigoBarras();
				sumDemanda				= 0;
				almacenId				= almacenIdRearch;				
				cantidadActual			= ap.getCantidadActual();
				precioVenta				= ap.getPrecioVenta();
				pedidoVentaId			= -1;
				pedidoVentaEstado		= -1;
				otrosPedidos			= 0;
				//logger.debug("\t\t->findDemandaProductoForAlmacen:  AlmacenProduto: productoCodigoBarras="+productoCodigoBarras+", cantidadActual="+cantidadActual+"");
			}
			//logger.debug("\t\t\t->findDemandaProductoForAlmacen: ("+counter+") AlmacenProduto: productoCodigoBarras="+productoCodigoBarras+", cantidadActual="+cantidadActual+", precioVenta="+precioVenta);
			
			almacenProductoDemanda = new AlmacenProductoDemanda(productoId, productoCodigoBarras, sumDemanda, almacenId, 
					 otrosPedidos, pvedList, cantidadActual,precioVenta);
				
			return almacenProductoDemanda;
		} finally {
			em.close();
		}
	}

		
}
