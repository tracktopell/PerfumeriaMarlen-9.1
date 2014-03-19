package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.PedidoVentaEstado;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import com.pmarlen.model.controller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.Estado;
import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.beans.Usuario;

/**
 * PedidoVentaEstadoJPAController
 */

@Repository("pedidoVentaEstadoJPAController")

public class PedidoVentaEstadoJPAController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidoVentaEstado pedidoVentaEstado) throws PreexistingEntityException, Exception {
//        if (pedidoVentaEstado.getPedidoVentaEstadoPK() == null) {
//            pedidoVentaEstado.setPedidoVentaEstadoPK(new PedidoVentaEstadoPK());
//        }
//        pedidoVentaEstado.getPedidoVentaEstadoPK().setEstadoId(pedidoVentaEstado.getEstado().getId());
//        pedidoVentaEstado.getPedidoVentaEstadoPK().setPedidoVentaId(pedidoVentaEstado.getPedidoVenta().getId());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            Estado estado = pedidoVentaEstado.getEstado();
//            if (estado != null) {
//                estado = em.getReference(estado.getClass(), estado.getId());
//                pedidoVentaEstado.setEstado(estado);
//            }
//            PedidoVenta pedidoVenta = pedidoVentaEstado.getPedidoVenta();
//            if (pedidoVenta != null) {
//                pedidoVenta = em.getReference(pedidoVenta.getClass(), pedidoVenta.getId());
//                pedidoVentaEstado.setPedidoVenta(pedidoVenta);
//            }
//            Usuario usuario = pedidoVentaEstado.getUsuario();
//            if (usuario != null) {
//                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioId());
//                pedidoVentaEstado.setUsuario(usuario);
//            }
//            em.persist(pedidoVentaEstado);
//            if (estado != null) {
//                estado.getPedidoVentaEstadoCollection().add(pedidoVentaEstado);
//                estado = em.merge(estado);
//            }
//            if (pedidoVenta != null) {
//                pedidoVenta.getPedidoVentaEstadoCollection().add(pedidoVentaEstado);
//                pedidoVenta = em.merge(pedidoVenta);
//            }
//            if (usuario != null) {
//                usuario.getPedidoVentaEstadoCollection().add(pedidoVentaEstado);
//                usuario = em.merge(usuario);
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            if (findPedidoVentaEstado(pedidoVentaEstado.getPedidoVentaEstadoPK()) != null) {
//                throw new PreexistingEntityException("PedidoVentaEstado " + pedidoVentaEstado + " already exists.", ex);
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(PedidoVentaEstado pedidoVentaEstado) throws NonexistentEntityException, Exception {
//        pedidoVentaEstado.getPedidoVentaEstadoPK().setEstadoId(pedidoVentaEstado.getEstado().getId());
//        pedidoVentaEstado.getPedidoVentaEstadoPK().setPedidoVentaId(pedidoVentaEstado.getPedidoVenta().getId());
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            PedidoVentaEstado persistentPedidoVentaEstado = em.find(PedidoVentaEstado.class, pedidoVentaEstado.getPedidoVentaEstadoPK());
//            Estado estadoOld = persistentPedidoVentaEstado.getEstado();
//            Estado estadoNew = pedidoVentaEstado.getEstado();
//            PedidoVenta pedidoVentaOld = persistentPedidoVentaEstado.getPedidoVenta();
//            PedidoVenta pedidoVentaNew = pedidoVentaEstado.getPedidoVenta();
//            Usuario usuarioOld = persistentPedidoVentaEstado.getUsuario();
//            Usuario usuarioNew = pedidoVentaEstado.getUsuario();
//            if (estadoNew != null) {
//                estadoNew = em.getReference(estadoNew.getClass(), estadoNew.getId());
//                pedidoVentaEstado.setEstado(estadoNew);
//            }
//            if (pedidoVentaNew != null) {
//                pedidoVentaNew = em.getReference(pedidoVentaNew.getClass(), pedidoVentaNew.getId());
//                pedidoVentaEstado.setPedidoVenta(pedidoVentaNew);
//            }
//            if (usuarioNew != null) {
//                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioId());
//                pedidoVentaEstado.setUsuario(usuarioNew);
//            }
//            pedidoVentaEstado = em.merge(pedidoVentaEstado);
//            if (estadoOld != null && !estadoOld.equals(estadoNew)) {
//                estadoOld.getPedidoVentaEstadoCollection().remove(pedidoVentaEstado);
//                estadoOld = em.merge(estadoOld);
//            }
//            if (estadoNew != null && !estadoNew.equals(estadoOld)) {
//                estadoNew.getPedidoVentaEstadoCollection().add(pedidoVentaEstado);
//                estadoNew = em.merge(estadoNew);
//            }
//            if (pedidoVentaOld != null && !pedidoVentaOld.equals(pedidoVentaNew)) {
//                pedidoVentaOld.getPedidoVentaEstadoCollection().remove(pedidoVentaEstado);
//                pedidoVentaOld = em.merge(pedidoVentaOld);
//            }
//            if (pedidoVentaNew != null && !pedidoVentaNew.equals(pedidoVentaOld)) {
//                pedidoVentaNew.getPedidoVentaEstadoCollection().add(pedidoVentaEstado);
//                pedidoVentaNew = em.merge(pedidoVentaNew);
//            }
//            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
//                usuarioOld.getPedidoVentaEstadoCollection().remove(pedidoVentaEstado);
//                usuarioOld = em.merge(usuarioOld);
//            }
//            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
//                usuarioNew.getPedidoVentaEstadoCollection().add(pedidoVentaEstado);
//                usuarioNew = em.merge(usuarioNew);
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                PedidoVentaEstadoPK id = pedidoVentaEstado.getPedidoVentaEstadoPK();
//                if (findPedidoVentaEstado(id) == null) {
//                    throw new NonexistentEntityException("The pedidoVentaEstado with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            PedidoVentaEstado pedidoVentaEstado;
//            try {
//                pedidoVentaEstado = em.getReference(PedidoVentaEstado.class, id);
//                pedidoVentaEstado.getPedidoVentaEstadoPK();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The pedidoVentaEstado with id " + id + " no longer exists.", enfe);
//            }
//            Estado estado = pedidoVentaEstado.getEstado();
//            if (estado != null) {
//                estado.getPedidoVentaEstadoCollection().remove(pedidoVentaEstado);
//                estado = em.merge(estado);
//            }
//            PedidoVenta pedidoVenta = pedidoVentaEstado.getPedidoVenta();
//            if (pedidoVenta != null) {
//                pedidoVenta.getPedidoVentaEstadoCollection().remove(pedidoVentaEstado);
//                pedidoVenta = em.merge(pedidoVenta);
//            }
//            Usuario usuario = pedidoVentaEstado.getUsuario();
//            if (usuario != null) {
//                usuario.getPedidoVentaEstadoCollection().remove(pedidoVentaEstado);
//                usuario = em.merge(usuario);
//            }
//            em.remove(pedidoVentaEstado);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<PedidoVentaEstado> findPedidoVentaEstadoEntities() {
        return findPedidoVentaEstadoEntities(true, -1, -1);
    }

    public List<PedidoVentaEstado> findPedidoVentaEstadoEntities(int maxResults, int firstResult) {
        return findPedidoVentaEstadoEntities(false, maxResults, firstResult);
    }

    private List<PedidoVentaEstado> findPedidoVentaEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from PedidoVentaEstado as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public PedidoVentaEstado findPedidoVentaEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PedidoVentaEstado.class, id);
        } finally {
            em.close();
        }
    }

    public int getPedidoVentaEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PedidoVentaEstado as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
