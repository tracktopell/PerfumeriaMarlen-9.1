package com.pmarlen.web.common.view.messages;

import com.pmarlen.model.Constants;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionInfo {

    private static Logger logger = LoggerFactory.getLogger(VersionInfo.class);
	
	private static Properties revisionProperties;

	private static Properties getRevisionProperties() throws IOException {
		if( revisionProperties == null) {
			revisionProperties = new Properties();
			revisionProperties.load(VersionInfo.class.getResourceAsStream("/revision.txt"));
			//logger.debug("-> revisionProperties="+revisionProperties);
		}
		return revisionProperties;
	}
	
	/**
	 * @return the svnInfo_revision
	 */
	public String getSvnInfo_revision() {
		try {
			return getRevisionProperties().getProperty("svnInfo_revision");
		} catch (IOException ex) {
			logger.warn("Problems with found /revision.txt :",ex);
			return null;
		}
	}

	/**
	 * @return the Project Server Version
	 */
	public String getVersion() {
		return Constants.getServerVersion();		
	}	
	/**
	 * @return the maven_build_timestamp
	 */
	public String getMaven_build_timestamp() {
		try {
			return getRevisionProperties().getProperty("maven_build_timestamp");
		} catch (IOException ex) {
			logger.warn("Problems with found /revision.txt :",ex);
			return null;
		}
	}

	
}
