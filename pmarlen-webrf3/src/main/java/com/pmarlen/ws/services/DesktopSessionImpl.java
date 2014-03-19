package  com.pmarlen.ws.services;

import com.pmarlen.model.controller.SessionInfoController;
import com.pmarlen.security.AnotherInfo;
import com.pmarlen.security.OnlineSessionInfo;
import com.pmarlen.wscommons.services.DesktopSession;
import java.util.List;
import javax.jws.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author alfred
 */

@WebService(endpointInterface = "com.pmarlen.wscommons.services.DesktopSession")
@Repository("desktopSessionImpl")
public class DesktopSessionImpl implements DesktopSession {
    private Logger logger;

	private SessionInfoController sessionInfoController;
	
	public DesktopSessionImpl() {
		logger = LoggerFactory.getLogger(DesktopSessionImpl.class);
		logger.debug("-->> created.");
	}
	
	@Override	
	public void iAmAlive(OnlineSessionInfo si){		
		OnlineSessionInfo update = sessionInfoController.update(si);
		System.err.println("-->>iAmAlive: OnlineSessionInfo="+update);
	}
	
	@Override
	public List<OnlineSessionInfo> getList(){
		return sessionInfoController.getViewOnlineSessionInfo();
	}
	
	/**
	 * @param sessionInfoController the sessionInfoController to set
	 */
	@Autowired
	public void setSessionInfoController(SessionInfoController sessionInfoController) {
		this.sessionInfoController = sessionInfoController;
	}
	
}
