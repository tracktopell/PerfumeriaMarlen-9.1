/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pmarlen.tasks;

import com.pmarlen.digifactws.production.client.DigifactClient;
import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.CfdVenta;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.PedidoVenta;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 * @author alfredo
 */
@Service("callDIGIFACTTask")
public class CallDIGIFACTTask {
	private static final boolean inDevelopmentMode = false;
	
	private EntityManagerFactory emf = null;
	private Logger logger;

	public CallDIGIFACTTask() {
		logger = LoggerFactory.getLogger(CallDIGIFACTTask.class);
		logger.debug("->CallDIGIFACTTask, created");
	}

	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Async
	public void invokeDIGIFACTWebService(PedidoVenta pedidoVenta) {
		logger.debug("->CallDIGIFACTTask===>>> invokeDIGIFACTWebService: pedidoVenta=" + pedidoVenta);
		CfdVenta cfdVentaActualizado = null;
		try {
			final String usuarioSicofi = pedidoVenta.getAlmacen().getSucursal().getUsuarioSicofi();
			final String serieSicofi = pedidoVenta.getAlmacen().getSucursal().getSerieSicofi();
			final String passwordSicofi = pedidoVenta.getAlmacen().getSucursal().getPasswordSicofi();
			if (inDevelopmentMode) {
				for (int i = 0; i < 10; i++) {
					logger.debug("\t->CallDIGIFACTTask.invokeDIGIFACTWebService===>>>SIMULATION [" + i + "] ...wait");
					Thread.sleep(2000);
				}

				cfdVentaActualizado = new CfdVenta();
				
				cfdVentaActualizado.setContenidoOriginalXml(null);
				cfdVentaActualizado.setUltimaActualizacion(new Date());
				cfdVentaActualizado.setCallingErrorResult("SIMULATION TEST");

			} else {
				logger.debug("\t->CallDIGIFACTTask.invokeDIGIFACTWebService<<------Real call:");
				cfdVentaActualizado = DigifactClient.invokeWS(pedidoVenta, serieSicofi, usuarioSicofi, passwordSicofi);
				logger.debug("\t->CallDIGIFACTTask.invokeDIGIFACTWebService<<------After Real call:cfdVentaActualizado: XML="+cfdVentaActualizado.getContenidoOriginalXml());
			}
			logger.debug("\t->CallDIGIFACTTask.invokeDIGIFACTWebService<<------END TASK SIMULATION");
			actualizarCFDPedido(pedidoVenta, cfdVentaActualizado);
			logger.debug("\t->CallDIGIFACTTask.invokeDIGIFACTWebService<<------OK persist.");

		} catch (Exception ie) {
			logger.error("Interrupted for in invokeDIGIFACTWebService:", ie);
		}
	}

	public void actualizarCFDPedido(PedidoVenta pedidoVenta, CfdVenta cfdVenta) {

		EntityManager em = null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			CfdVenta cfdVentaActualizado = new CfdVenta();

			cfdVentaActualizado.setContenidoOriginalXml(cfdVenta.getContenidoOriginalXml());
			cfdVentaActualizado.setUltimaActualizacion(cfdVenta.getUltimaActualizacion());
			cfdVentaActualizado.setCallingErrorResult(cfdVenta.getCallingErrorResult());

			em.persist(cfdVentaActualizado);

			PedidoVenta pedidoVentaUpdate = em.find(PedidoVenta.class, pedidoVenta.getId());

			pedidoVentaUpdate.setCfdVenta(cfdVentaActualizado);

			em.getTransaction().commit();
			logger.debug("\t->CallDIGIFACTTask.actualizarCFDPedido<<------END TASK");
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
