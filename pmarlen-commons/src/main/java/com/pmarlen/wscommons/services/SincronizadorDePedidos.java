package  com.pmarlen.wscommons.services;

import com.pmarlen.businesslogic.exception.AuthenticationException;
import com.pmarlen.businesslogic.exception.PedidoVentaException;

import com.pmarlen.wscommons.services.dto.PedidoVenta;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author alfred
 */

@WebService
public interface SincronizadorDePedidos {
    @WebMethod(operationName="enviarPedido")
    void enviarPedido(
			@WebParam(name="usuarioId")String usuarioId,
			@WebParam(name="password")String password,
			@WebParam(name="pedidoVentaList")List<PedidoVenta> pedidoVentaList)  throws AuthenticationException, PedidoVentaException;
}
