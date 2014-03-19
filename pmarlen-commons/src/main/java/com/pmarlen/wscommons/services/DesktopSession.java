package  com.pmarlen.wscommons.services;

import com.pmarlen.security.OnlineSessionInfo;
import java.util.List;
import javax.jws.WebService;


/**
 *
 * @author alfred
 */

@WebService
public interface DesktopSession {	
	
	void iAmAlive(OnlineSessionInfo onlineSessionInfo);
	
	List<OnlineSessionInfo> getList();
}
