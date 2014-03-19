package com.pmarlen.model.controller;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.pmarlen.model.beans.PedidoVenta;
import com.pmarlen.model.controller.exceptions.IllegalOrphanException;
import com.pmarlen.model.controller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import com.pmarlen.model.beans.FormaDePago;
import com.pmarlen.model.beans.Cliente;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.beans.PedidoVentaDetalle;
import java.util.ArrayList;
import java.util.Collection;
import com.pmarlen.model.beans.PedidoVentaEstado;

/**
 * PedidoVentaJPAController
 */

@Repository("pedidoVentaJPAController")
public class PedidoVentaJPAController {


    private EntityManagerFactory emf = null;

    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }


    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PedidoVenta pedidoVenta) {
//        if (pedidoVenta.getPedidoVentaDetalleCollection() == null) {
//            pedidoVenta.setPedidoVentaDetalleCollection(new ArrayList<PedidoVentaDetalle>());
//        }
//        if (pedidoVenta.getFacturaClienteCollection() == null) {
//            pedidoVenta.setFacturaClienteCollection(new ArrayList<FacturaCliente>());
//        }
//        if (pedidoVenta.getPedidoVentaEstadoCollection() == null) {
//            pedidoVenta.setPedidoVentaEstadoCollection(new ArrayList<PedidoVentaEstado>());
//        }
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            FormaDePago formaDePago = pedidoVenta.getFormaDePago();
//            if (formaDePago != null) {
//                formaDePago = em.getReference(formaDePago.getClass(), formaDePago.getId());
//                pedidoVenta.setFormaDePago(formaDePago);
//            }
//            Cliente cliente = pedidoVenta.getCliente();
//            if (cliente != null) {
//                cliente = em.getReference(cliente.getClass(), cliente.getId());
//                pedidoVenta.setCliente(cliente);
//            }
//            Usuario usuario = pedidoVenta.getUsuario();
//            if (usuario != null) {
//                usuario = em.getReference(usuario.getClass(), usuario.getUsuarioId());
//                pedidoVenta.setUsuario(usuario);
//            }
//            Collection<PedidoVentaDetalle> attachedPedidoVentaDetalleCollection = new ArrayList<PedidoVentaDetalle>();
//            for (PedidoVentaDetalle pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach : pedidoVenta.getPedidoVentaDetalleCollection()) {
//                pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach = em.getReference(pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach.getClass(), pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach.getPedidoVentaDetallePK());
//                attachedPedidoVentaDetalleCollection.add(pedidoVentaDetalleCollectionPedidoVentaDetalleToAttach);
//            }
//            pedidoVenta.setPedidoVentaDetalleCollection(attachedPedidoVentaDetalleCollection);
//            Collection<FacturaCliente> attachedFacturaClienteCollection = new ArrayList<FacturaCliente>();
//            for (FacturaCliente facturaClienteCollectionFacturaClienteToAttach : pedidoVenta.getFacturaClienteCollection()) {
//                facturaClienteCollectionFacturaClienteToAttach = em.getReference(facturaClienteCollectionFacturaClienteToAttach.getClass(), facturaClienteCollectionFacturaClienteToAttach.getId());
//                attachedFacturaClienteCollection.add(facturaClienteCollectionFacturaClienteToAttach);
//            }
//            pedidoVenta.setFacturaClienteCollection(attachedFacturaClienteCollection);
//            Collection<PedidoVentaEstado> attachedPedidoVentaEstadoCollection = new ArrayList<PedidoVentaEstado>();
//            for (PedidoVentaEstado pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach : pedidoVenta.getPedidoVentaEstadoCollection()) {
//                pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach = em.getReference(pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach.getClass(), pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach.getPedidoVentaEstadoPK());
//                attachedPedidoVentaEstadoCollection.add(pedidoVentaEstadoCollectionPedidoVentaEstadoToAttach);
//            }
//            pedidoVenta.setPedidoVentaEstadoCollection(attachedPedidoVentaEstadoCollection);
//            em.persist(pedidoVenta);
//            if (formaDePago != null) {
//                formaDePago.getPedidoVentaCollection().add(pedidoVenta);
//                formaDePago = em.merge(formaDePago);
//            }
//            if (cliente != null) {
//                cliente.getPedidoVentaCollection().add(pedidoVenta);
//                cliente = em.merge(cliente);
//            }
//            if (usuario != null) {
//                usuario.getPedidoVentaCollection().add(pedidoVenta);
//                usuario = em.merge(usuario);
//            }
//            for (PedidoVentaDetalle pedidoVentaDetalleCollectionPedidoVentaDetalle : pedidoVenta.getPedidoVentaDetalleCollection()) {
//                PedidoVenta oldPedidoVentaOfPedidoVentaDetalleCollectionPedidoVentaDetalle = pedidoVentaDetalleCollectionPedidoVentaDetalle.getPedidoVenta();
//                pedidoVentaDetalleCollectionPedidoVentaDetalle.setPedidoVenta(pedidoVenta);
//                pedidoVentaDetalleCollectionPedidoVentaDetalle = em.merge(pedidoVentaDetalleCollectionPedidoVentaDetalle);
//                if (oldPedidoVentaOfPedidoVentaDetalleCollectionPedidoVentaDetalle != null) {
//                    oldPedidoVentaOfPedidoVentaDetalleCollectionPedidoVentaDetalle.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalleCollectionPedidoVentaDetalle);
//                    oldPedidoVentaOfPedidoVentaDetalleCollectionPedidoVentaDetalle = em.merge(oldPedidoVentaOfPedidoVentaDetalleCollectionPedidoVentaDetalle);
//                }
//            }
//            for (FacturaCliente facturaClienteCollectionFacturaCliente : pedidoVenta.getFacturaClienteCollection()) {
//                PedidoVenta oldPedidoVentaOfFacturaClienteCollectionFacturaCliente = facturaClienteCollectionFacturaCliente.getPedidoVenta();
//                facturaClienteCollectionFacturaCliente.setPedidoVenta(pedidoVenta);
//                facturaClienteCollectionFacturaCliente = em.merge(facturaClienteCollectionFacturaCliente);
//                if (oldPedidoVentaOfFacturaClienteCollectionFacturaCliente != null) {
//                    oldPedidoVentaOfFacturaClienteCollectionFacturaCliente.getFacturaClienteCollection().remove(facturaClienteCollectionFacturaCliente);
//                    oldPedidoVentaOfFacturaClienteCollectionFacturaCliente = em.merge(oldPedidoVentaOfFacturaClienteCollectionFacturaCliente);
//                }
//            }
//            for (PedidoVentaEstado pedidoVentaEstadoCollectionPedidoVentaEstado : pedidoVenta.getPedidoVentaEstadoCollection()) {
//                PedidoVenta oldPedidoVentaOfPedidoVentaEstadoCollectionPedidoVentaEstado = pedidoVentaEstadoCollectionPedidoVentaEstado.getPedidoVenta();
//                pedidoVentaEstadoCollectionPedidoVentaEstado.setPedidoVenta(pedidoVenta);
//                pedidoVentaEstadoCollectionPedidoVentaEstado = em.merge(pedidoVentaEstadoCollectionPedidoVentaEstado);
//                if (oldPedidoVentaOfPedidoVentaEstadoCollectionPedidoVentaEstado != null) {
//                    oldPedidoVentaOfPedidoVentaEstadoCollectionPedidoVentaEstado.getPedidoVentaEstadoCollection().remove(pedidoVentaEstadoCollectionPedidoVentaEstado);
//                    oldPedidoVentaOfPedidoVentaEstadoCollectionPedidoVentaEstado = em.merge(oldPedidoVentaOfPedidoVentaEstadoCollectionPedidoVentaEstado);
//                }
//            }
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void edit(PedidoVenta pedidoVenta) throws IllegalOrphanException, NonexistentEntityException, Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            PedidoVenta persistentPedidoVenta = em.find(PedidoVenta.class, pedidoVenta.getId());
//            FormaDePago formaDePagoOld = persistentPedidoVenta.getFormaDePago();
//            FormaDePago formaDePagoNew = pedidoVenta.getFormaDePago();
//            Cliente clienteOld = persistentPedidoVenta.getCliente();
//            Cliente clienteNew = pedidoVenta.getCliente();
//            Usuario usuarioOld = persistentPedidoVenta.getUsuario();
//            Usuario usuarioNew = pedidoVenta.getUsuario();
//            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionOld = persistentPedidoVenta.getPedidoVentaDetalleCollection();
//            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionNew = pedidoVenta.getPedidoVentaDetalleCollection();
//            Collection<FacturaCliente> facturaClienteCollectionOld = persistentPedidoVenta.getFacturaClienteCollection();
//            Collection<FacturaCliente> facturaClienteCollectionNew = pedidoVenta.getFacturaClienteCollection();
//            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOld = persistentPedidoVenta.getPedidoVentaEstadoCollection();
//            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionNew = pedidoVenta.getPedidoVentaEstadoCollection();
//            List<String> illegalOrphanMessages = null;
//            for (PedidoVentaDetalle pedidoVentaDetalleCollectionOldPedidoVentaDetalle : pedidoVentaDetalleCollectionOld) {
//                if (!pedidoVentaDetalleCollectionNew.contains(pedidoVentaDetalleCollectionOldPedidoVentaDetalle)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain PedidoVentaDetalle " + pedidoVentaDetalleCollectionOldPedidoVentaDetalle + " since its pedidoVenta field is not nullable.");
//                }
//            }
//            for (FacturaCliente facturaClienteCollectionOldFacturaCliente : facturaClienteCollectionOld) {
//                if (!facturaClienteCollectionNew.contains(facturaClienteCollectionOldFacturaCliente)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain FacturaCliente " + facturaClienteCollectionOldFacturaCliente + " since its pedidoVenta field is not nullable.");
//                }
//            }
//            for (PedidoVentaEstado pedidoVentaEstadoCollectionOldPedidoVentaEstado : pedidoVentaEstadoCollectionOld) {
//                if (!pedidoVentaEstadoCollectionNew.contains(pedidoVentaEstadoCollectionOldPedidoVentaEstado)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain PedidoVentaEstado " + pedidoVentaEstadoCollectionOldPedidoVentaEstado + " since its pedidoVenta field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (formaDePagoNew != null) {
//                formaDePagoNew = em.getReference(formaDePagoNew.getClass(), formaDePagoNew.getId());
//                pedidoVenta.setFormaDePago(formaDePagoNew);
//            }
//            if (clienteNew != null) {
//                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
//                pedidoVenta.setCliente(clienteNew);
//            }
//            if (usuarioNew != null) {
//                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getUsuarioId());
//                pedidoVenta.setUsuario(usuarioNew);
//            }
//            Collection<PedidoVentaDetalle> attachedPedidoVentaDetalleCollectionNew = new ArrayList<PedidoVentaDetalle>();
//            for (PedidoVentaDetalle pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach : pedidoVentaDetalleCollectionNew) {
//                pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach = em.getReference(pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach.getClass(), pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach.getPedidoVentaDetallePK());
//                attachedPedidoVentaDetalleCollectionNew.add(pedidoVentaDetalleCollectionNewPedidoVentaDetalleToAttach);
//            }
//            pedidoVentaDetalleCollectionNew = attachedPedidoVentaDetalleCollectionNew;
//            pedidoVenta.setPedidoVentaDetalleCollection(pedidoVentaDetalleCollectionNew);
//            Collection<FacturaCliente> attachedFacturaClienteCollectionNew = new ArrayList<FacturaCliente>();
//            for (FacturaCliente facturaClienteCollectionNewFacturaClienteToAttach : facturaClienteCollectionNew) {
//                facturaClienteCollectionNewFacturaClienteToAttach = em.getReference(facturaClienteCollectionNewFacturaClienteToAttach.getClass(), facturaClienteCollectionNewFacturaClienteToAttach.getId());
//                attachedFacturaClienteCollectionNew.add(facturaClienteCollectionNewFacturaClienteToAttach);
//            }
//            facturaClienteCollectionNew = attachedFacturaClienteCollectionNew;
//            pedidoVenta.setFacturaClienteCollection(facturaClienteCollectionNew);
//            Collection<PedidoVentaEstado> attachedPedidoVentaEstadoCollectionNew = new ArrayList<PedidoVentaEstado>();
//            for (PedidoVentaEstado pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach : pedidoVentaEstadoCollectionNew) {
//                pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach = em.getReference(pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach.getClass(), pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach.getPedidoVentaEstadoPK());
//                attachedPedidoVentaEstadoCollectionNew.add(pedidoVentaEstadoCollectionNewPedidoVentaEstadoToAttach);
//            }
//            pedidoVentaEstadoCollectionNew = attachedPedidoVentaEstadoCollectionNew;
//            pedidoVenta.setPedidoVentaEstadoCollection(pedidoVentaEstadoCollectionNew);
//            pedidoVenta = em.merge(pedidoVenta);
//            if (formaDePagoOld != null && !formaDePagoOld.equals(formaDePagoNew)) {
//                formaDePagoOld.getPedidoVentaCollection().remove(pedidoVenta);
//                formaDePagoOld = em.merge(formaDePagoOld);
//            }
//            if (formaDePagoNew != null && !formaDePagoNew.equals(formaDePagoOld)) {
//                formaDePagoNew.getPedidoVentaCollection().add(pedidoVenta);
//                formaDePagoNew = em.merge(formaDePagoNew);
//            }
//            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
//                clienteOld.getPedidoVentaCollection().remove(pedidoVenta);
//                clienteOld = em.merge(clienteOld);
//            }
//            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
//                clienteNew.getPedidoVentaCollection().add(pedidoVenta);
//                clienteNew = em.merge(clienteNew);
//            }
//            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
//                usuarioOld.getPedidoVentaCollection().remove(pedidoVenta);
//                usuarioOld = em.merge(usuarioOld);
//            }
//            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
//                usuarioNew.getPedidoVentaCollection().add(pedidoVenta);
//                usuarioNew = em.merge(usuarioNew);
//            }
//            for (PedidoVentaDetalle pedidoVentaDetalleCollectionNewPedidoVentaDetalle : pedidoVentaDetalleCollectionNew) {
//                if (!pedidoVentaDetalleCollectionOld.contains(pedidoVentaDetalleCollectionNewPedidoVentaDetalle)) {
//                    PedidoVenta oldPedidoVentaOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle = pedidoVentaDetalleCollectionNewPedidoVentaDetalle.getPedidoVenta();
//                    pedidoVentaDetalleCollectionNewPedidoVentaDetalle.setPedidoVenta(pedidoVenta);
//                    pedidoVentaDetalleCollectionNewPedidoVentaDetalle = em.merge(pedidoVentaDetalleCollectionNewPedidoVentaDetalle);
//                    if (oldPedidoVentaOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle != null && !oldPedidoVentaOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle.equals(pedidoVenta)) {
//                        oldPedidoVentaOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle.getPedidoVentaDetalleCollection().remove(pedidoVentaDetalleCollectionNewPedidoVentaDetalle);
//                        oldPedidoVentaOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle = em.merge(oldPedidoVentaOfPedidoVentaDetalleCollectionNewPedidoVentaDetalle);
//                    }
//                }
//            }
//            for (FacturaCliente facturaClienteCollectionNewFacturaCliente : facturaClienteCollectionNew) {
//                if (!facturaClienteCollectionOld.contains(facturaClienteCollectionNewFacturaCliente)) {
//                    PedidoVenta oldPedidoVentaOfFacturaClienteCollectionNewFacturaCliente = facturaClienteCollectionNewFacturaCliente.getPedidoVenta();
//                    facturaClienteCollectionNewFacturaCliente.setPedidoVenta(pedidoVenta);
//                    facturaClienteCollectionNewFacturaCliente = em.merge(facturaClienteCollectionNewFacturaCliente);
//                    if (oldPedidoVentaOfFacturaClienteCollectionNewFacturaCliente != null && !oldPedidoVentaOfFacturaClienteCollectionNewFacturaCliente.equals(pedidoVenta)) {
//                        oldPedidoVentaOfFacturaClienteCollectionNewFacturaCliente.getFacturaClienteCollection().remove(facturaClienteCollectionNewFacturaCliente);
//                        oldPedidoVentaOfFacturaClienteCollectionNewFacturaCliente = em.merge(oldPedidoVentaOfFacturaClienteCollectionNewFacturaCliente);
//                    }
//                }
//            }
//            for (PedidoVentaEstado pedidoVentaEstadoCollectionNewPedidoVentaEstado : pedidoVentaEstadoCollectionNew) {
//                if (!pedidoVentaEstadoCollectionOld.contains(pedidoVentaEstadoCollectionNewPedidoVentaEstado)) {
//                    PedidoVenta oldPedidoVentaOfPedidoVentaEstadoCollectionNewPedidoVentaEstado = pedidoVentaEstadoCollectionNewPedidoVentaEstado.getPedidoVenta();
//                    pedidoVentaEstadoCollectionNewPedidoVentaEstado.setPedidoVenta(pedidoVenta);
//                    pedidoVentaEstadoCollectionNewPedidoVentaEstado = em.merge(pedidoVentaEstadoCollectionNewPedidoVentaEstado);
//                    if (oldPedidoVentaOfPedidoVentaEstadoCollectionNewPedidoVentaEstado != null && !oldPedidoVentaOfPedidoVentaEstadoCollectionNewPedidoVentaEstado.equals(pedidoVenta)) {
//                        oldPedidoVentaOfPedidoVentaEstadoCollectionNewPedidoVentaEstado.getPedidoVentaEstadoCollection().remove(pedidoVentaEstadoCollectionNewPedidoVentaEstado);
//                        oldPedidoVentaOfPedidoVentaEstadoCollectionNewPedidoVentaEstado = em.merge(oldPedidoVentaOfPedidoVentaEstadoCollectionNewPedidoVentaEstado);
//                    }
//                }
//            }
//            em.getTransaction().commit();
//        } catch (Exception ex) {
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                Integer id = pedidoVenta.getId();
//                if (findPedidoVenta(id) == null) {
//                    throw new NonexistentEntityException("The pedidoVenta with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.getTransaction().begin();
//            PedidoVenta pedidoVenta;
//            try {
//                pedidoVenta = em.getReference(PedidoVenta.class, id);
//                pedidoVenta.getId();
//            } catch (EntityNotFoundException enfe) {
//                throw new NonexistentEntityException("The pedidoVenta with id " + id + " no longer exists.", enfe);
//            }
//            List<String> illegalOrphanMessages = null;
//            Collection<PedidoVentaDetalle> pedidoVentaDetalleCollectionOrphanCheck = pedidoVenta.getPedidoVentaDetalleCollection();
//            for (PedidoVentaDetalle pedidoVentaDetalleCollectionOrphanCheckPedidoVentaDetalle : pedidoVentaDetalleCollectionOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This PedidoVenta (" + pedidoVenta + ") cannot be destroyed since the PedidoVentaDetalle " + pedidoVentaDetalleCollectionOrphanCheckPedidoVentaDetalle + " in its pedidoVentaDetalleCollection field has a non-nullable pedidoVenta field.");
//            }
//            Collection<FacturaCliente> facturaClienteCollectionOrphanCheck = pedidoVenta.getFacturaClienteCollection();
//            for (FacturaCliente facturaClienteCollectionOrphanCheckFacturaCliente : facturaClienteCollectionOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This PedidoVenta (" + pedidoVenta + ") cannot be destroyed since the FacturaCliente " + facturaClienteCollectionOrphanCheckFacturaCliente + " in its facturaClienteCollection field has a non-nullable pedidoVenta field.");
//            }
//            Collection<PedidoVentaEstado> pedidoVentaEstadoCollectionOrphanCheck = pedidoVenta.getPedidoVentaEstadoCollection();
//            for (PedidoVentaEstado pedidoVentaEstadoCollectionOrphanCheckPedidoVentaEstado : pedidoVentaEstadoCollectionOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This PedidoVenta (" + pedidoVenta + ") cannot be destroyed since the PedidoVentaEstado " + pedidoVentaEstadoCollectionOrphanCheckPedidoVentaEstado + " in its pedidoVentaEstadoCollection field has a non-nullable pedidoVenta field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            FormaDePago formaDePago = pedidoVenta.getFormaDePago();
//            if (formaDePago != null) {
//                formaDePago.getPedidoVentaCollection().remove(pedidoVenta);
//                formaDePago = em.merge(formaDePago);
//            }
//            Cliente cliente = pedidoVenta.getCliente();
//            if (cliente != null) {
//                cliente.getPedidoVentaCollection().remove(pedidoVenta);
//                cliente = em.merge(cliente);
//            }
//            Usuario usuario = pedidoVenta.getUsuario();
//            if (usuario != null) {
//                usuario.getPedidoVentaCollection().remove(pedidoVenta);
//                usuario = em.merge(usuario);
//            }
//            em.remove(pedidoVenta);
//            em.getTransaction().commit();
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
    }

    public List<PedidoVenta> findPedidoVentaEntities() {
        return findPedidoVentaEntities(true, -1, -1);
    }

    public List<PedidoVenta> findPedidoVentaEntities(int maxResults, int firstResult) {
        return findPedidoVentaEntities(false, maxResults, firstResult);
    }

    private List<PedidoVenta> findPedidoVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select pv from PedidoVenta pv order by pv.id desc");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            List<PedidoVenta> result = q.getResultList();
            for(PedidoVenta pedidoVenta:result){
                Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVenta.getPedidoVentaDetalleCollection();
                for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection) {
                }

                Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
                for(PedidoVentaEstado pedidoVentaEstado:pedidoVentaEstadoCollection) {
					pedidoVentaEstado.getEstado();
                }

            }
            return result;
        } finally {
            em.close();
        }
    }

    public PedidoVenta findPedidoVenta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            PedidoVenta pedidoVenta = em.find(PedidoVenta.class, id);
			
			pedidoVenta.getCliente().getPoblacion();
			pedidoVenta.getAlmacen().getSucursal().getPoblacion();
			
            Collection<PedidoVentaDetalle> detalleVentaPedidoCollection = pedidoVenta.getPedidoVentaDetalleCollection();
            for(PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollection) {
            }

            Collection<PedidoVentaEstado> pedidoVentaEstadoCollection = pedidoVenta.getPedidoVentaEstadoCollection();
            for(PedidoVentaEstado pedidoVentaEstado:pedidoVentaEstadoCollection) {
				pedidoVentaEstado.getEstado();				
            }

            return pedidoVenta;
        } finally {
            em.close();
        }
    }
    public int getPedidoVentaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from PedidoVenta as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
