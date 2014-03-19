package com.pmarlen.model.controller;

import com.pmarlen.model.Constants;
import com.pmarlen.model.beans.*;
import java.util.Collection;
import org.springframework.stereotype.Repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.EntityManagerFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * UsuarioJPAController
 */
@Repository("usuarioJPAController")
public class UsuarioJPAController extends EntityJPAController<Usuario>{
	public UsuarioJPAController() {
		super(LoggerFactory.getLogger(UsuarioJPAController.class));
		this.retrieveEagerExtensive = true;
	}

	@Override
	@Autowired
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@Override
	public Usuario newEntityInstance() {
		return new Usuario();
	}

	@Override
	protected Class getEntityClass() {
		return Usuario.class;
	}

	@Override
	protected String getEntityClassName() {
		return "Usuario";
	}

	@Override
	protected boolean isAffectedBySincronizacionEvent() {
		return true;
	}

	@Override
	protected Object getEntityId(Usuario entity) {
		return entity.getUsuarioId();
	}

	/*@Override
	protected void retrieveLazyAtributes(Usuario x) {
		x.getMensajesParaPortalCollection();
		x.getMovimientoHistoricoProductoCollection();
		x.getMovimientoMonederoElectronicoCollection();
		x.getMovimientoOperativoAlmacenCollection();
		x.getNotaCreditoCollection();
		x.getPedidoCompraCollection();
		x.getPedidoCompraEstadoCollection();
		x.getPedidoVentaCollection();
		x.getPedidoVentaEstadoCollection();
		final Collection<Perfil> perfilCollection = x.getPerfilCollection();
		for(Perfil p : perfilCollection){
			p.getDescripcion();
		}
		x.getSucursal();
		x.getSucursal().getAlmacenCollection();		
	}*/

	@Override
	public Usuario findById(Object usuarioId) {
		logger.debug("->@Override findById:usuarioId="+usuarioId);
		EntityManager em = getEntityManager();
		try {
			Usuario x = (Usuario) em.find(Usuario.class, usuarioId);
			if (x != null) {
				final Collection<Perfil> perfilCollection = x.getPerfilCollection();
				for(Perfil p: perfilCollection){					
					p.getDescripcion();
				}
				final Sucursal sucursal = x.getSucursal();
				final Collection<Almacen> almacenCollection = sucursal.getAlmacenCollection();
				for(Almacen a:almacenCollection){
					a.getSucursal();
				}
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	public Usuario findEntityByReadableProperty(String readable) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("select x from Usuario x where upper(x.nombreCompleto) = upper(:nombreCompleto)");

			q.setParameter("nombreCompleto", readable);

			Usuario x = (Usuario) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}
	
	public Usuario findByIdAndPassword(String usuarioId,String password) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId and u.password = :password");
			
            q.setParameter("usuarioId", usuarioId);
            q.setParameter("password", Constants.getMD5Encrypted(password));
            
			Usuario x = (Usuario) q.getSingleResult();
			if (x != null) {
				retrieveEagerAllAtributes(x);
			}
			return x;
		} finally {
			em.close();
		}
	}

	@Override
	protected void validateBeforeDestroy(EntityManager em, Object usuarioId, List<String> illegalOrphanMessages) {
		Query q = null;
		int countRelated = -1;
		
		String relatedEntities[] = {
				"MensajesParaPortal",
				"MovimientoHistoricoProducto",
				"MovimientoMonederoElectronico",
				"MovimientoOperativoAlmacen",
				"NotaCredito",
				"PedidoVenta",
				"PedidoVentaEstado",
				"EntradaAlmacen"};
		for(String re:relatedEntities){
			q = em.createQuery("select count(x) from "+re+" x where x.usuario.usuarioId = :usuarioId");
			q.setParameter("usuarioId", usuarioId);
			countRelated = ((Long) q.getSingleResult()).intValue();

			if (countRelated > 0) {
				final String name = "Hay " + countRelated + " "+re+"(s), relacionados a este Usuario";
				illegalOrphanMessages.add(name.toUpperCase());
			}
		}
	}
/*
    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

   public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getPerfilCollection() == null) {
            usuario.setPerfilCollection(new ArrayList<Perfil>());
        }
        if (usuario.getPedidoVentaCollection() == null) {
            usuario.setPedidoVentaCollection(new ArrayList<PedidoVenta>());
        }
        if (usuario.getMovimientoHistoricoProductoCollection() == null) {
            usuario.setMovimientoHistoricoProductoCollection(new ArrayList<MovimientoHistoricoProducto>());
        }
        if (usuario.getEstadoDeCuentaCollection() == null) {
            usuario.setEstadoDeCuentaCollection(new ArrayList<EstadoDeCuenta>());
        }
        if (usuario.getPedidoCompraEstadoCollection() == null) {
            usuario.setPedidoCompraEstadoCollection(new ArrayList<PedidoCompraEstado>());
        }
        if (usuario.getPedidoCompraCollection() == null) {
            usuario.setPedidoCompraCollection(new ArrayList<PedidoCompra>());
        }
        if (usuario.getPedidoVentaEstadoCollection() == null) {
            usuario.setPedidoVentaEstadoCollection(new ArrayList<PedidoVentaEstado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Perfil> attachedPerfilCollection = new ArrayList<Perfil>();
            for (Perfil perfilCollectionPerfilToAttach : usuario.getPerfilCollection()) {
                perfilCollectionPerfilToAttach = em.getReference(perfilCollectionPerfilToAttach.getClass(), perfilCollectionPerfilToAttach.getId());
                attachedPerfilCollection.add(perfilCollectionPerfilToAttach);
            }
            usuario.setPerfilCollection(attachedPerfilCollection);
            Collection<PedidoVenta> attachedPedidoVentaCollection = new ArrayList<PedidoVenta>();
            for (PedidoVenta pedidoVentaCollectionPedidoVentaToAttach : usuario.getPedidoVentaCollection()) {
                pedidoVentaCollectionPedidoVentaToAttach = em.getReference(pedidoVentaCollectionPedidoVentaToAttach.getClass(), pedidoVentaCollectionPedidoVentaToAttach.getId());
                attachedPedidoVentaCollection.add(pedidoVentaCollectionPedidoVentaToAttach);
            }
            usuario.setPedidoVentaCollection(attachedPedidoVentaCollection);
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollection = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach : usuario.getMovimientoHistoricoProductoCollection()) {
                movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollection.add(movimientoHistoricoProductoCollectionMovimientoHistoricoProductoToAttach);
            }
            usuario.setMovimientoHistoricoProductoCollection(attachedMovimientoHistoricoProductoCollection);
            Collection<EstadoDeCuenta> attachedEstadoDeCuentaCollection = new ArrayList<EstadoDeCuenta>();
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuentaToAttach : usuario.getEstadoDeCuentaCollection()) {
                estadoDeCuentaCollectionEstadoDeCuentaToAttach = em.getReference(estadoDeCuentaCollectionEstadoDeCuentaToAttach.getClass(), estadoDeCuentaCollectionEstadoDeCuentaToAttach.getId());
                attachedEstadoDeCuentaCollection.add(estadoDeCuentaCollectionEstadoDeCuentaToAttach);
            }
            usuario.setEstadoDeCuentaCollection(attachedEstadoDeCuentaCollection);
            Collection<PedidoCompraEstado> attachedPedidoCompraEstadoCollection = new ArrayList<PedidoCompraEstado>();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach : usuario.getPedidoCompraEstadoCollection()) {
                pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach = em.getReference(pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach.getClass(), pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach.getPedidoCompraEstadoPK());
                attachedPedidoCompraEstadoCollection.add(pedidoCompraEstadoCollectionPedidoCompraEstadoToAttach);
            }
            usuario.setPedidoCompraEstadoCollection(attachedPedidoCompraEstadoCollection);
            Collection<PedidoCompra> attachedPedidoCompraCollection = new ArrayList<PedidoCompra>();
            for (PedidoCompra pedidoCompraCollectionPedidoCompraToAttach : usuario.getPedidoCompraCollection()) {
                pedidoCompraCollectionPedidoCompraToAttach = em.getReference(pedidoCompraCollectionPedidoCompraToAttach.getClass(), pedidoCompraCollectionPedidoCompraToAttach.getId());
                attachedPedidoCompraCollection.add(pedidoCompraCollectionPedidoCompraToAttach);
            }
            usuario.setPedidoCompraCollection(attachedPedidoCompraCollection);
            Collection<PedidoVentaEstado> attachedPedidoVentaEstadoCollection = new ArrayList<PedidoVentaEstado>();
            for (PedidoVentaEstado pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach : usuario.getPedidoVentaEstadoCollection()) {
                pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach = em.getReference(pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach.getClass(), pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach.getPedidoVentaEstadoPK());
                attachedPedidoVentaEstadoCollection.add(pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach);
            }
            usuario.setPedidoVentaEstadoCollection(attachedPedidoVentaEstadoCollection);
            em.persist(usuario);
            for (Perfil perfilCollectionPerfil : usuario.getPerfilCollection()) {
                perfilCollectionPerfil.getUsuarioCollection().add(usuario);
                perfilCollectionPerfil = em.merge(perfilCollectionPerfil);
            }
            for (PedidoVenta pedidoVentaCollectionPedidoVenta : usuario.getPedidoVentaCollection()) {
                Usuario oldUsuarioOfPedidoVentaCollectionPedidoVenta = pedidoVentaCollectionPedidoVenta.getUsuario();
                pedidoVentaCollectionPedidoVenta.setUsuario(usuario);
                pedidoVentaCollectionPedidoVenta = em.merge(pedidoVentaCollectionPedidoVenta);
                if (oldUsuarioOfPedidoVentaCollectionPedidoVenta != null) {
                    oldUsuarioOfPedidoVentaCollectionPedidoVenta.getPedidoVentaCollection().remove(pedidoVentaCollectionPedidoVenta);
                    oldUsuarioOfPedidoVentaCollectionPedidoVenta = em.merge(oldUsuarioOfPedidoVentaCollectionPedidoVenta);
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionMovimientoHistoricoProducto : usuario.getMovimientoHistoricoProductoCollection()) {
                Usuario oldUsuarioOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getUsuario();
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto.setUsuario(usuario);
                movimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                if (oldUsuarioOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto != null) {
                    oldUsuarioOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                    oldUsuarioOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto = em.merge(oldUsuarioOfMovimientoHistoricoProductoCollectionMovimientoHistoricoProducto);
                }
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionEstadoDeCuenta : usuario.getEstadoDeCuentaCollection()) {
                Usuario oldUsuarioOfEstadoDeCuentaCollectionEstadoDeCuenta = estadoDeCuentaCollectionEstadoDeCuenta.getUsuario();
                estadoDeCuentaCollectionEstadoDeCuenta.setUsuario(usuario);
                estadoDeCuentaCollectionEstadoDeCuenta = em.merge(estadoDeCuentaCollectionEstadoDeCuenta);
                if (oldUsuarioOfEstadoDeCuentaCollectionEstadoDeCuenta != null) {
                    oldUsuarioOfEstadoDeCuentaCollectionEstadoDeCuenta.getEstadoDeCuentaCollection().remove(estadoDeCuentaCollectionEstadoDeCuenta);
                    oldUsuarioOfEstadoDeCuentaCollectionEstadoDeCuenta = em.merge(oldUsuarioOfEstadoDeCuentaCollectionEstadoDeCuenta);
                }
            }
            for (PedidoCompraEstado pedidoCompraEstadoCollectionPedidoCompraEstado : usuario.getPedidoCompraEstadoCollection()) {
                Usuario oldUsuarioOfPedidoCompraEstadoCollectionPedidoCompraEstado = pedidoCompraEstadoCollectionPedidoCompraEstado.getUsuario();
                pedidoCompraEstadoCollectionPedidoCompraEstado.setUsuario(usuario);
                pedidoCompraEstadoCollectionPedidoCompraEstado = em.merge(pedidoCompraEstadoCollectionPedidoCompraEstado);
                if (oldUsuarioOfPedidoCompraEstadoCollectionPedidoCompraEstado != null) {
                    oldUsuarioOfPedidoCompraEstadoCollectionPedidoCompraEstado.getPedidoCompraEstadoCollection().remove(pedidoCompraEstadoCollectionPedidoCompraEstado);
                    oldUsuarioOfPedidoCompraEstadoCollectionPedidoCompraEstado = em.merge(oldUsuarioOfPedidoCompraEstadoCollectionPedidoCompraEstado);
                }
            }
            for (PedidoCompra pedidoCompraCollectionPedidoCompra : usuario.getPedidoCompraCollection()) {
                Usuario oldUsuarioOfPedidoCompraCollectionPedidoCompra = pedidoCompraCollectionPedidoCompra.getUsuario();
                pedidoCompraCollectionPedidoCompra.setUsuario(usuario);
                pedidoCompraCollectionPedidoCompra = em.merge(pedidoCompraCollectionPedidoCompra);
                if (oldUsuarioOfPedidoCompraCollectionPedidoCompra != null) {
                    oldUsuarioOfPedidoCompraCollectionPedidoCompra.getPedidoCompraCollection().remove(pedidoCompraCollectionPedidoCompra);
                    oldUsuarioOfPedidoCompraCollectionPedidoCompra = em.merge(oldUsuarioOfPedidoCompraCollectionPedidoCompra);
                }
            }
            for (PedidoVentaEstado pedidoVentaEstadoCollectionPedidoVentaEstado : usuario.getPedidoVentaEstadoCollection()) {
                Usuario oldUsuarioOfPedidoVentaEstadoCollectionPedidoVentaEstado = pedidoVentaEstadoCollectionPedidoVentaEstado.getUsuario();
                pedidoVentaEstadoCollectionPedidoVentaEstado.setUsuario(usuario);
                pedidoVentaEstadoCollectionPedidoVentaEstado = em.merge(pedidoVentaEstadoCollectionPedidoVentaEstado);
                if (oldUsuarioOfPedidoVentaEstadoCollectionPedidoVentaEstado != null) {
                    oldUsuarioOfPedidoVentaEstadoCollectionPedidoVentaEstado.getPedidoVentaEstadoCollection().remove(pedidoVentaEstadoCollectionPedidoVentaEstado);
                    oldUsuarioOfPedidoVentaEstadoCollectionPedidoVentaEstado = em.merge(oldUsuarioOfPedidoVentaEstadoCollectionPedidoVentaEstado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getUsuarioId()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getUsuarioId());
            Collection<Perfil> perfilCollectionOld = persistentUsuario.getPerfilCollection();
            Collection<Perfil> perfilCollectionNew = usuario.getPerfilCollection();
            Collection<PedidoVenta> pedidoVentaCollectionOld = persistentUsuario.getPedidoVentaCollection();
            Collection<PedidoVenta> pedidoVentaCollectionNew = usuario.getPedidoVentaCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOld = persistentUsuario.getMovimientoHistoricoProductoCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionNew = usuario.getMovimientoHistoricoProductoCollection();
            Collection<EstadoDeCuenta> estadoDeCuentaCollectionOld = persistentUsuario.getEstadoDeCuentaCollection();
            Collection<EstadoDeCuenta> estadoDeCuentaCollectionNew = usuario.getEstadoDeCuentaCollection();
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionOld = persistentUsuario.getPedidoCompraEstadoCollection();
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionNew = usuario.getPedidoCompraEstadoCollection();
            Collection<PedidoCompra> pedidoCompraCollectionOld = persistentUsuario.getPedidoCompraCollection();
            Collection<PedidoCompra> pedidoCompraCollectionNew = usuario.getPedidoCompraCollection();
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = persistentUsuario.getPedidoVentaEstadoCollection();
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionNew = usuario.getPedidoVentaEstadoCollection();
            List<String> illegalOrphanMessages = null;
            for (PedidoVenta pedidoVentaCollectionOldPedidoVenta : pedidoVentaCollectionOld) {
                if (!pedidoVentaCollectionNew.contains(pedidoVentaCollectionOldPedidoVenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoVenta " + pedidoVentaCollectionOldPedidoVenta + " since its usuario field is not nullable.");
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOld) {
                if (!movimientoHistoricoProductoCollectionNew.contains(movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOldMovimientoHistoricoProducto + " since its usuario field is not nullable.");
                }
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionOldEstadoDeCuenta : estadoDeCuentaCollectionOld) {
                if (!estadoDeCuentaCollectionNew.contains(estadoDeCuentaCollectionOldEstadoDeCuenta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EstadoDeCuenta " + estadoDeCuentaCollectionOldEstadoDeCuenta + " since its usuario field is not nullable.");
                }
            }
            for (PedidoCompraEstado pedidoCompraEstadoCollectionOldPedidoCompraEstado : pedidoCompraEstadoCollectionOld) {
                if (!pedidoCompraEstadoCollectionNew.contains(pedidoCompraEstadoCollectionOldPedidoCompraEstado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompraEstado " + pedidoCompraEstadoCollectionOldPedidoCompraEstado + " since its usuario field is not nullable.");
                }
            }
            for (PedidoCompra pedidoCompraCollectionOldPedidoCompra : pedidoCompraCollectionOld) {
                if (!pedidoCompraCollectionNew.contains(pedidoCompraCollectionOldPedidoCompra)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoCompra " + pedidoCompraCollectionOldPedidoCompra + " since its usuario field is not nullable.");
                }
            }
            for (PedidoVentaEstado pedidoVentaEstadoCollectionOldPedidoVentaEstado : pedidoVentaEstadoCollectionOld) {
                if (!pedidoVentaEstadoCollectionNew.contains(pedidoVentaEstadoCollectionOldPedidoVentaEstado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PedidoVentaEstado " + pedidoVentaEstadoCollectionOldPedidoVentaEstado + " since its usuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Perfil> attachedPerfilCollectionNew = new ArrayList<Perfil>();
            for (Perfil perfilCollectionNewPerfilToAttach : perfilCollectionNew) {
                perfilCollectionNewPerfilToAttach = em.getReference(perfilCollectionNewPerfilToAttach.getClass(), perfilCollectionNewPerfilToAttach.getId());
                attachedPerfilCollectionNew.add(perfilCollectionNewPerfilToAttach);
            }
            perfilCollectionNew = attachedPerfilCollectionNew;
            usuario.setPerfilCollection(perfilCollectionNew);
            Collection<PedidoVenta> attachedPedidoVentaCollectionNew = new ArrayList<PedidoVenta>();
            for (PedidoVenta pedidoVentaCollectionNewPedidoVentaToAttach : pedidoVentaCollectionNew) {
                pedidoVentaCollectionNewPedidoVentaToAttach = em.getReference(pedidoVentaCollectionNewPedidoVentaToAttach.getClass(), pedidoVentaCollectionNewPedidoVentaToAttach.getId());
                attachedPedidoVentaCollectionNew.add(pedidoVentaCollectionNewPedidoVentaToAttach);
            }
            pedidoVentaCollectionNew = attachedPedidoVentaCollectionNew;
            usuario.setPedidoVentaCollection(pedidoVentaCollectionNew);
            Collection<MovimientoHistoricoProducto> attachedMovimientoHistoricoProductoCollectionNew = new ArrayList<MovimientoHistoricoProducto>();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach : movimientoHistoricoProductoCollectionNew) {
                movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach = em.getReference(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getClass(), movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach.getMovimientoHistoricoProductoPK());
                attachedMovimientoHistoricoProductoCollectionNew.add(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProductoToAttach);
            }
            movimientoHistoricoProductoCollectionNew = attachedMovimientoHistoricoProductoCollectionNew;
            usuario.setMovimientoHistoricoProductoCollection(movimientoHistoricoProductoCollectionNew);
            Collection<EstadoDeCuenta> attachedEstadoDeCuentaCollectionNew = new ArrayList<EstadoDeCuenta>();
            for (EstadoDeCuenta estadoDeCuentaCollectionNewEstadoDeCuentaToAttach : estadoDeCuentaCollectionNew) {
                estadoDeCuentaCollectionNewEstadoDeCuentaToAttach = em.getReference(estadoDeCuentaCollectionNewEstadoDeCuentaToAttach.getClass(), estadoDeCuentaCollectionNewEstadoDeCuentaToAttach.getId());
                attachedEstadoDeCuentaCollectionNew.add(estadoDeCuentaCollectionNewEstadoDeCuentaToAttach);
            }
            estadoDeCuentaCollectionNew = attachedEstadoDeCuentaCollectionNew;
            usuario.setEstadoDeCuentaCollection(estadoDeCuentaCollectionNew);
            Collection<PedidoCompraEstado> attachedPedidoCompraEstadoCollectionNew = new ArrayList<PedidoCompraEstado>();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach : pedidoCompraEstadoCollectionNew) {
                pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach = em.getReference(pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach.getClass(), pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach.getPedidoCompraEstadoPK());
                attachedPedidoCompraEstadoCollectionNew.add(pedidoCompraEstadoCollectionNewPedidoCompraEstadoToAttach);
            }
            pedidoCompraEstadoCollectionNew = attachedPedidoCompraEstadoCollectionNew;
            usuario.setPedidoCompraEstadoCollection(pedidoCompraEstadoCollectionNew);
            Collection<PedidoCompra> attachedPedidoCompraCollectionNew = new ArrayList<PedidoCompra>();
            for (PedidoCompra pedidoCompraCollectionNewPedidoCompraToAttach : pedidoCompraCollectionNew) {
                pedidoCompraCollectionNewPedidoCompraToAttach = em.getReference(pedidoCompraCollectionNewPedidoCompraToAttach.getClass(), pedidoCompraCollectionNewPedidoCompraToAttach.getId());
                attachedPedidoCompraCollectionNew.add(pedidoCompraCollectionNewPedidoCompraToAttach);
            }
            pedidoCompraCollectionNew = attachedPedidoCompraCollectionNew;
            usuario.setPedidoCompraCollection(pedidoCompraCollectionNew);
            Collection<PedidoVentaEstado> attachedPedidoVentaEstadoCollectionNew = new ArrayList<PedidoVentaEstado>();
            for (PedidoVentaEstado pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach : pedidoVentaEstadoCollectionNew) {
                pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach = em.getReference(pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach.getClass(), pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach.getPedidoVentaEstadoPK());
                attachedPedidoVentaEstadoCollectionNew.add(pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach);
            }
            pedidoVentaEstadoCollectionNew = attachedPedidoVentaEstadoCollectionNew;
            usuario.setPedidoVentaEstadoCollection(pedidoVentaEstadoCollectionNew);
            usuario = em.merge(usuario);
            for (Perfil perfilCollectionOldPerfil : perfilCollectionOld) {
                if (!perfilCollectionNew.contains(perfilCollectionOldPerfil)) {
                    perfilCollectionOldPerfil.getUsuarioCollection().remove(usuario);
                    perfilCollectionOldPerfil = em.merge(perfilCollectionOldPerfil);
                }
            }
            for (Perfil perfilCollectionNewPerfil : perfilCollectionNew) {
                if (!perfilCollectionOld.contains(perfilCollectionNewPerfil)) {
                    perfilCollectionNewPerfil.getUsuarioCollection().add(usuario);
                    perfilCollectionNewPerfil = em.merge(perfilCollectionNewPerfil);
                }
            }
            for (PedidoVenta pedidoVentaCollectionNewPedidoVenta : pedidoVentaCollectionNew) {
                if (!pedidoVentaCollectionOld.contains(pedidoVentaCollectionNewPedidoVenta)) {
                    Usuario oldUsuarioOfPedidoVentaCollectionNewPedidoVenta = pedidoVentaCollectionNewPedidoVenta.getUsuario();
                    pedidoVentaCollectionNewPedidoVenta.setUsuario(usuario);
                    pedidoVentaCollectionNewPedidoVenta = em.merge(pedidoVentaCollectionNewPedidoVenta);
                    if (oldUsuarioOfPedidoVentaCollectionNewPedidoVenta != null && !oldUsuarioOfPedidoVentaCollectionNewPedidoVenta.equals(usuario)) {
                        oldUsuarioOfPedidoVentaCollectionNewPedidoVenta.getPedidoVentaCollection().remove(pedidoVentaCollectionNewPedidoVenta);
                        oldUsuarioOfPedidoVentaCollectionNewPedidoVenta = em.merge(oldUsuarioOfPedidoVentaCollectionNewPedidoVenta);
                    }
                }
            }
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionNew) {
                if (!movimientoHistoricoProductoCollectionOld.contains(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto)) {
                    Usuario oldUsuarioOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getUsuario();
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.setUsuario(usuario);
                    movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    if (oldUsuarioOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto != null && !oldUsuarioOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.equals(usuario)) {
                        oldUsuarioOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto.getMovimientoHistoricoProductoCollection().remove(movimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                        oldUsuarioOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto = em.merge(oldUsuarioOfMovimientoHistoricoProductoCollectionNewMovimientoHistoricoProducto);
                    }
                }
            }
            for (EstadoDeCuenta estadoDeCuentaCollectionNewEstadoDeCuenta : estadoDeCuentaCollectionNew) {
                if (!estadoDeCuentaCollectionOld.contains(estadoDeCuentaCollectionNewEstadoDeCuenta)) {
                    Usuario oldUsuarioOfEstadoDeCuentaCollectionNewEstadoDeCuenta = estadoDeCuentaCollectionNewEstadoDeCuenta.getUsuario();
                    estadoDeCuentaCollectionNewEstadoDeCuenta.setUsuario(usuario);
                    estadoDeCuentaCollectionNewEstadoDeCuenta = em.merge(estadoDeCuentaCollectionNewEstadoDeCuenta);
                    if (oldUsuarioOfEstadoDeCuentaCollectionNewEstadoDeCuenta != null && !oldUsuarioOfEstadoDeCuentaCollectionNewEstadoDeCuenta.equals(usuario)) {
                        oldUsuarioOfEstadoDeCuentaCollectionNewEstadoDeCuenta.getEstadoDeCuentaCollection().remove(estadoDeCuentaCollectionNewEstadoDeCuenta);
                        oldUsuarioOfEstadoDeCuentaCollectionNewEstadoDeCuenta = em.merge(oldUsuarioOfEstadoDeCuentaCollectionNewEstadoDeCuenta);
                    }
                }
            }
            for (PedidoCompraEstado pedidoCompraEstadoCollectionNewPedidoCompraEstado : pedidoCompraEstadoCollectionNew) {
                if (!pedidoCompraEstadoCollectionOld.contains(pedidoCompraEstadoCollectionNewPedidoCompraEstado)) {
                    Usuario oldUsuarioOfPedidoCompraEstadoCollectionNewPedidoCompraEstado = pedidoCompraEstadoCollectionNewPedidoCompraEstado.getUsuario();
                    pedidoCompraEstadoCollectionNewPedidoCompraEstado.setUsuario(usuario);
                    pedidoCompraEstadoCollectionNewPedidoCompraEstado = em.merge(pedidoCompraEstadoCollectionNewPedidoCompraEstado);
                    if (oldUsuarioOfPedidoCompraEstadoCollectionNewPedidoCompraEstado != null && !oldUsuarioOfPedidoCompraEstadoCollectionNewPedidoCompraEstado.equals(usuario)) {
                        oldUsuarioOfPedidoCompraEstadoCollectionNewPedidoCompraEstado.getPedidoCompraEstadoCollection().remove(pedidoCompraEstadoCollectionNewPedidoCompraEstado);
                        oldUsuarioOfPedidoCompraEstadoCollectionNewPedidoCompraEstado = em.merge(oldUsuarioOfPedidoCompraEstadoCollectionNewPedidoCompraEstado);
                    }
                }
            }
            for (PedidoCompra pedidoCompraCollectionNewPedidoCompra : pedidoCompraCollectionNew) {
                if (!pedidoCompraCollectionOld.contains(pedidoCompraCollectionNewPedidoCompra)) {
                    Usuario oldUsuarioOfPedidoCompraCollectionNewPedidoCompra = pedidoCompraCollectionNewPedidoCompra.getUsuario();
                    pedidoCompraCollectionNewPedidoCompra.setUsuario(usuario);
                    pedidoCompraCollectionNewPedidoCompra = em.merge(pedidoCompraCollectionNewPedidoCompra);
                    if (oldUsuarioOfPedidoCompraCollectionNewPedidoCompra != null && !oldUsuarioOfPedidoCompraCollectionNewPedidoCompra.equals(usuario)) {
                        oldUsuarioOfPedidoCompraCollectionNewPedidoCompra.getPedidoCompraCollection().remove(pedidoCompraCollectionNewPedidoCompra);
                        oldUsuarioOfPedidoCompraCollectionNewPedidoCompra = em.merge(oldUsuarioOfPedidoCompraCollectionNewPedidoCompra);
                    }
                }
            }
            for (PedidoVentaEstado pedidoVentaEstadoCollectionNewPedidoVentaEstado : pedidoVentaEstadoCollectionNew) {
                if (!pedidoVentaEstadoCollectionOld.contains(pedidoVentaEstadoCollectionNewPedidoVentaEstado)) {
                    Usuario oldUsuarioOfPedidoVentaEstadoCollectionNewPedidoVentaEstado = pedidoVentaEstadoCollectionNewPedidoVentaEstado.getUsuario();
                    pedidoVentaEstadoCollectionNewPedidoVentaEstado.setUsuario(usuario);
                    pedidoVentaEstadoCollectionNewPedidoVentaEstado = em.merge(pedidoVentaEstadoCollectionNewPedidoVentaEstado);
                    if (oldUsuarioOfPedidoVentaEstadoCollectionNewPedidoVentaEstado != null && !oldUsuarioOfPedidoVentaEstadoCollectionNewPedidoVentaEstado.equals(usuario)) {
                        oldUsuarioOfPedidoVentaEstadoCollectionNewPedidoVentaEstado.getPedidoVentaEstadoCollection().remove(pedidoVentaEstadoCollectionNewPedidoVentaEstado);
                        oldUsuarioOfPedidoVentaEstadoCollectionNewPedidoVentaEstado = em.merge(oldUsuarioOfPedidoVentaEstadoCollectionNewPedidoVentaEstado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getUsuarioId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getUsuarioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PedidoVenta> pedidoVentaCollectionOrphanCheck = usuario.getPedidoVentaCollection();
            for (PedidoVenta pedidoVentaCollectionOrphanCheckPedidoVenta : pedidoVentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PedidoVenta " + pedidoVentaCollectionOrphanCheckPedidoVenta + " in its pedidoVentaCollection field has a non-nullable usuario field.");
            }
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollectionOrphanCheck = usuario.getMovimientoHistoricoProductoCollection();
            for (MovimientoHistoricoProducto movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto : movimientoHistoricoProductoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the MovimientoHistoricoProducto " + movimientoHistoricoProductoCollectionOrphanCheckMovimientoHistoricoProducto + " in its movimientoHistoricoProductoCollection field has a non-nullable usuario field.");
            }
            Collection<EstadoDeCuenta> estadoDeCuentaCollectionOrphanCheck = usuario.getEstadoDeCuentaCollection();
            for (EstadoDeCuenta estadoDeCuentaCollectionOrphanCheckEstadoDeCuenta : estadoDeCuentaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the EstadoDeCuenta " + estadoDeCuentaCollectionOrphanCheckEstadoDeCuenta + " in its estadoDeCuentaCollection field has a non-nullable usuario field.");
            }
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollectionOrphanCheck = usuario.getPedidoCompraEstadoCollection();
            for (PedidoCompraEstado pedidoCompraEstadoCollectionOrphanCheckPedidoCompraEstado : pedidoCompraEstadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PedidoCompraEstado " + pedidoCompraEstadoCollectionOrphanCheckPedidoCompraEstado + " in its pedidoCompraEstadoCollection field has a non-nullable usuario field.");
            }
            Collection<PedidoCompra> pedidoCompraCollectionOrphanCheck = usuario.getPedidoCompraCollection();
            for (PedidoCompra pedidoCompraCollectionOrphanCheckPedidoCompra : pedidoCompraCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PedidoCompra " + pedidoCompraCollectionOrphanCheckPedidoCompra + " in its pedidoCompraCollection field has a non-nullable usuario field.");
            }
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOrphanCheck = usuario.getPedidoVentaEstadoCollection();
            for (PedidoVentaEstado pedidoVentaEstadoCollectionOrphanCheckPedidoVentaEstado : pedidoVentaEstadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PedidoVentaEstado " + pedidoVentaEstadoCollectionOrphanCheckPedidoVentaEstado + " in its pedidoVentaEstadoCollection field has a non-nullable usuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Perfil> perfilCollection = usuario.getPerfilCollection();
            for (Perfil perfilCollectionPerfil : perfilCollection) {
                perfilCollectionPerfil.getUsuarioCollection().remove(usuario);
                perfilCollectionPerfil = em.merge(perfilCollectionPerfil);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<Usuario> resultLsit = q.getResultList();
            for (Usuario x : resultLsit) {
                Collection<Perfil> perfilCollection = x.getPerfilCollection();
                Collection<PedidoVenta> pedidoVentaCollection = x.getPedidoVentaCollection();
                Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = x.getMovimientoHistoricoProductoCollection();
                Collection<PedidoCompraEstado> pedidoCompraEstadoCollection = x.getPedidoCompraEstadoCollection();
                Collection<PedidoCompra> pedidoCompraCollection = x.getPedidoCompraCollection();
                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = x.getPedidoVentaEstadoCollection();
                Collection<MensajesParaPortal> mensajesParaPortalCollection = x.getMensajesParaPortalCollection();

                x.setPerfilCollection(null);
                x.setPedidoVentaCollection(null);
                x.setMovimientoHistoricoProductoCollection(null);                
                x.setPedidoCompraEstadoCollection(null);
                x.setPedidoCompraCollection(null);
                x.setPedidoVentaEstadoCollection(null);
                x.setMensajesParaPortalCollection(null);
            }
            return resultLsit;
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String usuarioId, String password) throws NoResultException{
        EntityManager em = getEntityManager();
        try {
            Usuario userAuthenticated = null;
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.usuarioId = :usuarioId and u.password = :password");
            q.setParameter("usuarioId", usuarioId);
            q.setParameter("password", Constants.getMD5Encrypted(password));
            userAuthenticated = (Usuario) q.getSingleResult();

            Collection<Perfil> perfilCollection = userAuthenticated.getPerfilCollection();
            Collection<PedidoVenta> pedidoVentaCollection = userAuthenticated.getPedidoVentaCollection();
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = userAuthenticated.getMovimientoHistoricoProductoCollection();
            Collection<PedidoCompraEstado> pedidoCompraEstadoCollection = userAuthenticated.getPedidoCompraEstadoCollection();
            Collection<PedidoCompra> pedidoCompraCollection = userAuthenticated.getPedidoCompraCollection();
            Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = userAuthenticated.getPedidoVentaEstadoCollection();
            Collection<MensajesParaPortal> mensajesParaPortalCollection = userAuthenticated.getMensajesParaPortalCollection();
			Collection<MovimientoOperativoAlmacen> movimientoOperativoAlmacenCollection = userAuthenticated.getMovimientoOperativoAlmacenCollection();
			
			

            userAuthenticated.setPerfilCollection(null);
            userAuthenticated.setPedidoVentaCollection(null);
            userAuthenticated.setMovimientoHistoricoProductoCollection(null);
            userAuthenticated.setPedidoCompraEstadoCollection(null);
            userAuthenticated.setPedidoCompraCollection(null);
            userAuthenticated.setPedidoVentaEstadoCollection(null);
            userAuthenticated.setMensajesParaPortalCollection(null);
			userAuthenticated.setMovimientoOperativoAlmacenCollection(null);

            return userAuthenticated;
        } finally {
            em.close();
        }
    }

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            Usuario x = em.find(Usuario.class, id);

            Collection<Perfil> perfilCollection = x.getPerfilCollection();
            for (Perfil perfil : perfilCollection) {
            }

            return x;
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
	*/
	
}
