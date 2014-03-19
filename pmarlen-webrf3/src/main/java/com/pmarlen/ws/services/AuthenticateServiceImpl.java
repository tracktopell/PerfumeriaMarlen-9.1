package  com.pmarlen.ws.services;

import com.pmarlen.businesslogic.exception.AuthenticationException;

import javax.jws.WebService;
import com.pmarlen.wscommons.services.AuthenticateService;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import com.pmarlen.model.beans.Perfil;
import com.pmarlen.model.beans.Usuario;
import com.pmarlen.model.controller.UsuarioJPAController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.NoResultException;

@WebService(endpointInterface = "com.pmarlen.wscommons.services.AuthenticateService")
@Repository("authenticateServiceImpl")
public class AuthenticateServiceImpl implements  AuthenticateService {

    private UsuarioJPAController usuarioJPAController;

    public Usuario authenticateUsuario(String usuarioId,String password) throws AuthenticationException {
        System.err.print("--->> call --->>> authenticateUsuario(String usaurioId="+usuarioId+",String password="+password+"); ");
        Usuario usuarioAuthenticated = null;
        try {
            usuarioAuthenticated =  usuarioJPAController.findByIdAndPassword(usuarioId,password);
        } catch(NoResultException nre) {
            throw new AuthenticationException("ERROR IN USER / PASSORD");
        }

        System.err.println(" usuarioAuthenticated ="+usuarioAuthenticated);
		Usuario usuarioDTO = new Usuario();
		
		usuarioDTO.setUsuarioId(usuarioAuthenticated.getUsuarioId());
		usuarioDTO.setNombreCompleto(usuarioAuthenticated.getNombreCompleto());
		usuarioDTO.setEmail(usuarioAuthenticated.getEmail());
		usuarioDTO.setHabilitado(usuarioAuthenticated.getHabilitado());
		usuarioDTO.setPassword(usuarioAuthenticated.getPassword());				
		Collection<Perfil> perfilCollection = usuarioAuthenticated.getPerfilCollection();
		List<Perfil> perfilList=new ArrayList<Perfil>();
		for(Perfil p: perfilCollection){
			Perfil pDTO = new Perfil();
			pDTO.setId(p.getId());
			pDTO.setDescripcion(p.getDescripcion());
			perfilList.add(pDTO);
		}		
		usuarioDTO.setPerfilCollection(perfilList);	
		
        return usuarioDTO;
    }

    @Autowired
    public void setUsuarioJPAController( UsuarioJPAController usuarioJPAController){
        this.usuarioJPAController = usuarioJPAController;
    }


}
