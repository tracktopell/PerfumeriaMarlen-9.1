package com.pmarlen.web.common.view.messages;

import com.pmarlen.model.Constants;

public class VersionInfo {

	/**
	 * @return the svnInfo_revision
	 */
	public String getSvnInfo_revision() {
		return Constants.getServerVersion();
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
		return Constants.getMavenBuildTimeStamp();	
	}

	
}
