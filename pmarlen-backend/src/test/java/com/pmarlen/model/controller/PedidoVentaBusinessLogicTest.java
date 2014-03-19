package com.pmarlen.model.controller;

import com.pmarlen.businesslogic.LogicaFinaciera;
import com.pmarlen.businesslogic.PedidoVentaBusinessLogic;
import com.pmarlen.model.beans.*;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class PedidoVentaBusinessLogicTest {

    private Logger logger = LoggerFactory.getLogger(PedidoVentaBusinessLogicTest.class);
    @Autowired
    private PedidoVentaBusinessLogic pedidoVentaBusinessLogic;

    @Test
    //@Ignore
    public void testCrearPedidoVentaByPedidoVentaBusinessLogic() throws SQLException {
        logger.info(">>testCrearPedidoVentaByPedidoVentaBusinessLogic: ============================== INITIALIZED CONTEXT ============================");

		assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        List<Producto> productoList = pedidoVentaBusinessLogic.findProductoEntities();
        List<PedidoVentaDetalle> detalleVentaPedidoList = new ArrayList<PedidoVentaDetalle>();

        PedidoVenta pedidoVenta = new PedidoVenta();

        for (Producto producto : productoList) {
            if (Math.random() >= 0.5) {
                Producto productoBuscado = pedidoVentaBusinessLogic.findProducto(producto.getId());
                PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

                detalleVentaPedido.setProducto(productoBuscado);
                detalleVentaPedido.setPrecioVenta(productoBuscado.getCosto()*1.2);
                int cantMax = 0;
                Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
                for (AlmacenProducto almacenProducto : almacenProductoCollection) {
                    cantMax += almacenProducto.getCantidadActual();
                }
                detalleVentaPedido.setCantidad(cantMax / 2);                
                
                detalleVentaPedidoList.add(detalleVentaPedido);
            }
        }

        Cliente cliente = pedidoVentaBusinessLogic.findCliente(1);
        assertNotNull("cliente is null.", cliente);

        Usuario usuario = pedidoVentaBusinessLogic.findUsuario("hmiranda");
        Usuario usuarioModifico = pedidoVentaBusinessLogic.findUsuario("uleon");
        assertNotNull("usuario is null.", usuario);


        FormaDePago formaDePago = pedidoVentaBusinessLogic.findFormaDePago(1);
        assertNotNull("formaDePago is null.", formaDePago);

		MetodoDePago metodoDePago = null;//pedidoVentaBusinessLogic.findMetodoDePago(1);
        assertNotNull("metodoDePago is null.", metodoDePago);

        pedidoVenta.setCliente(cliente);
        pedidoVenta.setUsuario(usuario);
        pedidoVenta.setFormaDePago(formaDePago);
		pedidoVenta.setMetodoDePago(metodoDePago);
		pedidoVenta.setFactoriva(LogicaFinaciera.getImpuestoIVA());		
        pedidoVenta.setComentarios("TEST.testCrearPedidoVentaByPedidoVentaBusinessLogic:@" + (new Date()));        
        pedidoVenta.setPedidoVentaDetalleCollection(detalleVentaPedidoList);

        List<PedidoVenta> pedidoVentaListBefore = pedidoVentaBusinessLogic.findPedidoVentaEntities();

        pedidoVenta = pedidoVentaBusinessLogic.crearPedidoCapturado(pedidoVenta,usuarioModifico);
        
        logger.info(">> PedidoVenta creado: pedidoVenta.getId()="+pedidoVenta.getId());		
		
		//pedidoVenta = pedidoVentaBusinessLogic.crearPedidoVentaDetalleCapturado(pedidoVenta,detalleVentaPedidoList);
        

        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaBusinessLogic.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + 1) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + 1, pedidoVentaListAfter.size());

        PedidoVenta pedidoVentaAfter = pedidoVentaBusinessLogic.findPedidoVenta(pedidoVenta.getId());
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionAfter = pedidoVentaAfter.getPedidoVentaDetalleCollection();

        assertEquals("The # of beans in List: " + detalleVentaPedidoCollectionAfter.size()  + " != " + detalleVentaPedidoList.size(),
                detalleVentaPedidoCollectionAfter.size(), detalleVentaPedidoList.size());    
    }

	@Test
    //@Ignore
    public void testGuardarPedidosEnviadosVentaByPedidoVentaBusinessLogic() throws SQLException {
        logger.info(">>testGuardarPedidosEnviadosVentaByPedidoVentaBusinessLogic: ============================== INITIALIZED CONTEXT ============================");

        assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        List<Producto> productoList = pedidoVentaBusinessLogic.findProductoEntities();
		int numPedidos = (int)(5+Math.random()*4.0);
		
		List<PedidoVenta> pedidoVentaToInsert = new ArrayList<PedidoVenta>();
		
		Usuario usuarioModifico = pedidoVentaBusinessLogic.findUsuario("uleon");
		for(int numPedido = 0; numPedido<numPedidos;numPedido++){
		
			List<PedidoVentaDetalle> detalleVentaPedidoList = new ArrayList<PedidoVentaDetalle>();

			PedidoVenta pedidoVenta = new PedidoVenta();
		
			for (Producto producto : productoList) {
				if (Math.random() >= 0.5) {
					Producto productoBuscado = pedidoVentaBusinessLogic.findProducto(producto.getId());
					PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

					detalleVentaPedido.setProducto(productoBuscado);
					detalleVentaPedido.setPrecioVenta(productoBuscado.getCosto()*1.2);
					int cantMax = 0;
					Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
					for (AlmacenProducto almacenProducto : almacenProductoCollection) {
						cantMax += almacenProducto.getCantidadActual();
					}
					detalleVentaPedido.setCantidad(cantMax / 2);                
					//detalleVentaPedido.setPedidoVentaDetallePK(new PedidoVentaDetallePK(0, detalleVentaPedido.getProducto().getId()));

					detalleVentaPedidoList.add(detalleVentaPedido);
				}
			}

			Cliente cliente = null;
			if (Math.random() >= 0.5) {
				cliente = new Cliente();
				Cliente clienteParecido = pedidoVentaBusinessLogic.findCliente(1);
				
				BeanUtils.copyProperties(clienteParecido, cliente, new String[]{"id","cuentaBancariaCollection","contactoCollection","pedidoVentaCollection"});
				cliente.setId(clienteParecido.getId()+(int)(10000*Math.random()));
				cliente.setRazonSocial(clienteParecido.getRazonSocial()+"(PARECIDO)");
				cliente.setNombreEstablecimiento(clienteParecido.getNombreEstablecimiento()+"(PARECIDO)");
			} else {
				cliente = pedidoVentaBusinessLogic.findCliente(1);
			}
			
			assertNotNull("cliente is null.", cliente);

			Usuario usuario = pedidoVentaBusinessLogic.findUsuario("hmiranda");			
			assertNotNull("usuario is null.", usuario);


			FormaDePago formaDePago = pedidoVentaBusinessLogic.findFormaDePago(1);
			assertNotNull("formaDePago is null.", formaDePago);
			Almacen almacen = new Almacen();
			almacen.setId(1);
			pedidoVenta.setAlmacen(almacen);
			pedidoVenta.setCliente(cliente);
			pedidoVenta.setUsuario(usuario);
			pedidoVenta.setFormaDePago(formaDePago);
			pedidoVenta.setComentarios("Prueba desde TEST@" + (new Date()));        
			pedidoVenta.setPedidoVentaDetalleCollection(detalleVentaPedidoList);
			
			pedidoVentaToInsert.add(pedidoVenta);
		}
		
		
		List<PedidoVenta> pedidoVentaListBefore = pedidoVentaBusinessLogic.findPedidoVentaEntities();
		
		logger.debug(" ==>> pedidoVentaListBefore.size()= "+pedidoVentaListBefore.size());
		
        try {
            pedidoVentaBusinessLogic.guardarPedidosEnviados(usuarioModifico, pedidoVentaToInsert);
        } catch (Exception ex) {
            logger.error(" ==>> pedidoVentaBusinessLogic.guardarPedidosEnviados: ", ex);
        }
		
        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaBusinessLogic.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + numPedidos) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + numPedidos, pedidoVentaListAfter.size());

    }
	
    
    @Test
	@Ignore
    public void testEditarPedidoVentaByPedidoVentaBusinessLogic() throws SQLException {
        logger.info(">>testEditarPedidoVentaByPedidoVentaBusinessLogic: ============================== INITIALIZED CONTEXT ============================");

        assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        List<Producto> productoList = pedidoVentaBusinessLogic.findProductoEntities();
        List<PedidoVentaDetalle> detalleVentaPedidoList = new ArrayList<PedidoVentaDetalle>();

        PedidoVenta pedidoVenta = new PedidoVenta();
        
        for (Producto producto : productoList) {
            if (Math.random() >= 0.5) {
                Producto productoBuscado = pedidoVentaBusinessLogic.findProducto(producto.getId());
                PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

                detalleVentaPedido.setProducto(productoBuscado);
                detalleVentaPedido.setPrecioVenta(productoBuscado.getCosto()*1.2);
                int cantMax = 0;
                Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
                for (AlmacenProducto almacenProducto : almacenProductoCollection) {
                    cantMax += almacenProducto.getCantidadActual();
                }
                detalleVentaPedido.setCantidad(cantMax / 2);
				detalleVentaPedido.setPedidoVenta(null);
				detalleVentaPedido.setProducto(detalleVentaPedido.getProducto());				

                detalleVentaPedidoList.add(detalleVentaPedido);
            }
        }

        Cliente cliente = pedidoVentaBusinessLogic.findCliente(1);
        assertNotNull("cliente is null.", cliente);

        Usuario usuario = pedidoVentaBusinessLogic.findUsuario("hmiranda");
        Usuario usuarioModifico = pedidoVentaBusinessLogic.findUsuario("aestrada");
        assertNotNull("usuario is null.", usuario);


        FormaDePago formaDePago = pedidoVentaBusinessLogic.findFormaDePago(1);
        assertNotNull("formaDePago is null.", formaDePago);
		
		MetodoDePago metodoDePago = null;//pedidoVentaBusinessLogic.findMetodoDePago(1);
        assertNotNull("metodoDePago is null.", metodoDePago);


        pedidoVenta.setCliente(cliente);
        pedidoVenta.setUsuario(usuario);
        pedidoVenta.setFormaDePago(formaDePago);
		pedidoVenta.setMetodoDePago(metodoDePago);
        pedidoVenta.setComentarios("TEST:testEditarPedidoVentaByPedidoVentaBusinessLogic@" + (new Date()));
        
        pedidoVenta.setPedidoVentaDetalleCollection(detalleVentaPedidoList);

        List<PedidoVenta> pedidoVentaListBefore = pedidoVentaBusinessLogic.findPedidoVentaEntities();

        pedidoVenta = pedidoVentaBusinessLogic.crearPedidoCapturado(pedidoVenta,usuarioModifico);

        logger.info(">> PedidoVenta creado: pedidoVenta.getId()="+pedidoVenta.getId());
		
		//pedidoVenta = pedidoVentaBusinessLogic.crearPedidoVentaDetalleCapturado(pedidoVenta,detalleVentaPedidoList);

        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaBusinessLogic.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + 1) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + 1, pedidoVentaListAfter.size());
        //----------------------------------------------------------------------
        PedidoVenta pedidoVentaAfter = pedidoVentaBusinessLogic.findPedidoVenta(pedidoVenta.getId());
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionAfter = pedidoVentaAfter.getPedidoVentaDetalleCollection();

        assertEquals("The # of beans in List: " + detalleVentaPedidoCollectionAfter.size()  + " != " + detalleVentaPedidoList.size(),
                detalleVentaPedidoCollectionAfter.size(), detalleVentaPedidoList.size());

        //======================================================================

        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionChanged = pedidoVentaAfter.getPedidoVentaDetalleCollection();
        List<PedidoVentaDetalle> detalleVentaPedidoCollectionToDelete = new ArrayList<PedidoVentaDetalle>();

        for(PedidoVentaDetalle dvp: detalleVentaPedidoCollectionChanged) {
            if (Math.random() >= 0.5) {
                detalleVentaPedidoCollectionToDelete.add(dvp);
            }
        }

        for(PedidoVentaDetalle dvpDelete: detalleVentaPedidoCollectionToDelete) {
            assertTrue("Cant delete PedidoVentaDetalle from collection:",detalleVentaPedidoCollectionChanged.remove(dvpDelete));
        }

        for (Producto producto : productoList) {
            if (Math.random() >= 0.5) {
                boolean agregado = false;
                for (PedidoVentaDetalle detalleVentaPedido: detalleVentaPedidoCollectionChanged) {
                    if(detalleVentaPedido.getProducto().equals(producto)){
                        agregado = true;
                        break;
                    }
                }
                if( ! agregado ) {                    
                    Producto productoBuscado = pedidoVentaBusinessLogic.findProducto(producto.getId());
                    PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

                    detalleVentaPedido.setProducto(productoBuscado);
                    detalleVentaPedido.setPrecioVenta(productoBuscado.getCosto()*1.2);
                    int cantMax = 0;
                    Collection<AlmacenProducto> almacenProductoCollection = productoBuscado.getAlmacenProductoCollection();
                    for (AlmacenProducto almacenProducto : almacenProductoCollection) {
                        cantMax += almacenProducto.getCantidadActual();
                    }
                    detalleVentaPedido.setCantidad(cantMax / 2);
                    detalleVentaPedido.setPedidoVenta(pedidoVentaAfter);
                    //detalleVentaPedido.setPedidoVentaDetallePK(new PedidoVentaDetallePK(pedidoVentaAfter.getId(), detalleVentaPedido.getProducto().getId()));

                    detalleVentaPedidoCollectionChanged.add(detalleVentaPedido);
                }
            }
        }

        Cliente clienteChanged = pedidoVentaBusinessLogic.findCliente(2);
        assertNotNull("clienteChanged is null.", clienteChanged);

        Usuario usuarioChanged = pedidoVentaBusinessLogic.findUsuario("aestrada");
        assertNotNull("usuarioChanged is null.", usuarioChanged);

        FormaDePago formaDePagoChanged = pedidoVentaBusinessLogic.findFormaDePago(2);
        assertNotNull("formaDePagoChanged is null.", formaDePagoChanged);

        pedidoVentaAfter.setCliente(clienteChanged);
        pedidoVentaAfter.setUsuario(usuarioChanged);
        pedidoVentaAfter.setFormaDePago(formaDePago);
        pedidoVentaAfter.setComentarios("Changed TEST@" + (new Date()));        
        pedidoVentaAfter.setPedidoVentaDetalleCollection(detalleVentaPedidoCollectionChanged);
        
        try {
            pedidoVentaBusinessLogic.sincronizarPedido(pedidoVentaAfter,usuarioChanged);
            logger.info(">> pedidoVentaAfter editado");
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            fail(ex.getMessage());
        }

    }
	
	@Test
	@Ignore
    public void testSurtirPedidoVenta() throws SQLException {
        logger.info("============================== INITIALIZED CONTEXT ============================");
        SimpleDateFormat sdfDefault  = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        SimpleDateFormat sdfExtended = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzzzzz (Z)");
        Date dateSystem = new Date();
        logger.debug("-->> TimeZone.getDefault()="+TimeZone.getDefault().getDisplayName()+
                ", Time=defaultformat:"+sdfDefault.format(dateSystem)+", ExtendedFormat:"+sdfExtended.format(dateSystem));        

        assertNotNull("pedidoVentaBusinessLogic is null.", pedidoVentaBusinessLogic);

        //======================================================================

        ArrayList<Producto> productoListPedido1 = new ArrayList<Producto>();
        List<PedidoVentaDetalle> detalleVentaPedido1List = new ArrayList<PedidoVentaDetalle>();

        PedidoVenta pedidoVenta1 = new PedidoVenta();
        pedidoVenta1.setFactoriva(LogicaFinaciera.getImpuestoIVA());


        productoListPedido1.add(pedidoVentaBusinessLogic.findProducto(1));
        productoListPedido1.add(pedidoVentaBusinessLogic.findProducto(2));
        productoListPedido1.add(pedidoVentaBusinessLogic.findProducto(3));
        productoListPedido1.add(pedidoVentaBusinessLogic.findProducto(4));
        productoListPedido1.add(pedidoVentaBusinessLogic.findProducto(5));
        productoListPedido1.add(pedidoVentaBusinessLogic.findProducto(6));

        boolean conDescPedido1[] = new boolean[]{
                true,
                false,
                true,
                false,
                true,
                false
        };
        int cantPedidaPedido1[] = new int[]{
                8,
                5,
                4,
                5,
                13,
                5
        };

        for(int indProd=0; indProd < productoListPedido1.size(); indProd++){
            Producto producto = productoListPedido1.get(indProd);

            Producto productoBuscado = pedidoVentaBusinessLogic.findProducto(producto.getId());
            PedidoVentaDetalle detalleVentaPedido = new PedidoVentaDetalle();

            detalleVentaPedido.setProducto(productoBuscado);
            detalleVentaPedido.setPrecioVenta(productoBuscado.getCosto()*1.2);
            detalleVentaPedido.setCantidad(cantPedidaPedido1[indProd]);            

            detalleVentaPedido1List.add(detalleVentaPedido);
        }

        pedidoVenta1.setCliente(new Cliente(1));
        pedidoVenta1.setUsuario(new Usuario("hmiranda"));
        pedidoVenta1.setFormaDePago(new FormaDePago(1));
		pedidoVenta1.setMetodoDePago(new MetodoDePago(1));
        pedidoVenta1.setComentarios("TEST:testSurtirPedidoVenta@" + (new Date()));
        pedidoVenta1.setAlmacen(new Almacen(1));
		
        pedidoVenta1.setPedidoVentaDetalleCollection(detalleVentaPedido1List);

        List<PedidoVenta> pedidoVentaListBefore = pedidoVentaBusinessLogic.findPedidoVentaEntities();

        pedidoVenta1 = pedidoVentaBusinessLogic.crearPedidoCapturado(pedidoVenta1,new Usuario("aestrada"));

        logger.info(">> PedidoVenta creado: pedidoVenta.getId()="+pedidoVenta1.getId());
		
		//pedidoVenta1 = pedidoVentaBusinessLogic.crearPedidoVentaDetalleCapturado(pedidoVenta1,detalleVentaPedido1List);

        List<PedidoVenta> pedidoVentaListAfter = pedidoVentaBusinessLogic.findPedidoVentaEntities();

        assertEquals("The # of beans in List: " + (pedidoVentaListBefore.size() + 1) + " != " + pedidoVentaListAfter.size(),
                pedidoVentaListBefore.size() + 1, pedidoVentaListAfter.size());
        //----------------------------------------------------------------------
        PedidoVenta pedidoVentaAfter = pedidoVentaBusinessLogic.findPedidoVenta(pedidoVenta1.getId());
        Collection<PedidoVentaDetalle> detalleVentaPedidoCollectionAfter = pedidoVentaAfter.getPedidoVentaDetalleCollection();

        assertEquals("The # of beans in List: " + detalleVentaPedidoCollectionAfter.size()  + " != " + detalleVentaPedido1List.size(),
                detalleVentaPedidoCollectionAfter.size(), detalleVentaPedido1List.size());

        //======================================================================

        try {
            pedidoVentaBusinessLogic.sincronizarPedido(pedidoVentaAfter,new Usuario("aestrada"));
            logger.info(">> Ok, sincronizarPedido");

            pedidoVentaBusinessLogic.verificarPedido(pedidoVentaAfter, new Usuario("aestrada"));
            logger.info(">> Ok, verificarPedido");

            pedidoVentaBusinessLogic.surtirPedido(pedidoVentaAfter,new Usuario("aestrada"));
            logger.info(">> Ok, surtirPedido");

        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            fail(ex.getMessage());
        }

        List<Producto> productoForReport = pedidoVentaBusinessLogic.findProductoForReport();
        DecimalFormat df = new DecimalFormat("000000");

        for(Producto produto: productoForReport) {
            logger.info("==>>Producto:"+produto.getId());
            Collection<AlmacenProducto> almacenProductoCollection = produto.getAlmacenProductoCollection();
            for(AlmacenProducto ap: almacenProductoCollection){
                logger.info("\t==>>Almacen: CantidadActual=\t"+df.format(ap.getCantidadActual())+"\t"+ap.getAlmacen().getTipoAlmacen());
            }
            Collection<MovimientoHistoricoProducto> movimientoHistoricoProductoCollection = produto.getMovimientoHistoricoProductoCollection();
            for(MovimientoHistoricoProducto mhp: movimientoHistoricoProductoCollection) {
                logger.info("\t==>>M H P  : Cantidad      =\t"+df.format(mhp.getCantidad())+"\t"+mhp.getAlmacen().getTipoAlmacen()+"\t"+mhp.getTipoMovimiento().getDescripcion());
            }
        }

    }

    /**
     * @return the pedidoVentaBusinessLogic
     */
    public PedidoVentaBusinessLogic getPedidoVentaBusinessLogic() {
        return pedidoVentaBusinessLogic;
    }

    /**
     * @param pedidoVentaBusinessLogic the pedidoVentaBusinessLogic to set
     */
    public void setPedidoVentaBusinessLogic(PedidoVentaBusinessLogic pedidoVentaBusinessLogic) {
        this.pedidoVentaBusinessLogic = pedidoVentaBusinessLogic;
    }
}
