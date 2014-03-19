package  com.pmarlen.wscommons.services;

import com.pmarlen.businesslogic.exception.AuthenticationException;
import com.pmarlen.model.beans.Usuario;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author alfred
 */

@WebService(name="AuthenticateService")
public interface AuthenticateService {
    @WebMethod(operationName="authenticateUsuario")
    Usuario authenticateUsuario(
			@WebParam(name="usuarioId")String usuarioId,
			@WebParam(name="password")String password) throws AuthenticationException;

}
