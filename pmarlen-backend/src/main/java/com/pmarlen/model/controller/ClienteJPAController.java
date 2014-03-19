package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;

import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Usuario;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ClienteJPAController
 */

@Repository("clienteJPAController")
public class ClienteJPAController extends EntityJPAController<Cliente>{

	public ClienteJPAController() {
		super(LoggerFactory.getLogger(ClienteJPAController.class));
	}

	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Cliente newEntityInstance() {
		return new Cliente();
	}

	@Override
	protected Class getEntityClass() {
		return Cliente.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Cliente";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(Cliente entity) {
		return entity.getId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Cliente x) {
		x.getPoblacion();
		x.getPedidoVentaCollection();
		x.getNotaCreditoCollection();
	}*/

	@Override
	public Cliente findEntityByReadableProperty(String rfc) {
		logger.debug("->@Override findById:rfc="+rfc);
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Cliente x where upper(x.rfc) = upper(:rfc)");

			q.setParameter("rfc", rfc);
			Cliente x = (Cliente) q.getSingleResult();
			
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object clienteId, List<String> illegalOrphanMessages) {
		Query q = null;
		int countRelated = -1;
		
		String relatedEntities[] = {
				"PedidoVenta",
				"NotaCredito"};
		for(String re:relatedEntities){
			q = em.createQuery("select count(x) from "+re+" x where x.cliente.id= :clienteId");
			q.setParameter("clienteId", clienteId);
			countRelated = ((Long) q.getSingleResult()).intValue();

			if (countRelated > 0) {
				final String name = "Hay " + countRelated + " "+re+"(s), relacionados a este Usuario";
				illegalOrphanMessages.add(name.toUpperCase());
			}
		}
	}

	protected String orderBy(String object){
		return " order by "+object+".razonSocial";
	}
}
