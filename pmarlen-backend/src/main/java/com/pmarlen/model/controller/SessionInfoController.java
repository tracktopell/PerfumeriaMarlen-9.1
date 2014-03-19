package com.pmarlen.model.controller;


import com.pmarlen.security.OnlineSessionInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;

/**
 * SessionInfoController
 */
@Controller("sessionInfoController")
public class SessionInfoController {
	private static SortedMap<String,OnlineSessionInfo> sessionInfoMap = null;
	private static final int ONLINE_TIMEOUT = 70000;

	static{
		sessionInfoMap = new TreeMap<String, OnlineSessionInfo>();
	}
	
	public OnlineSessionInfo update(OnlineSessionInfo onlineSessionInfo){
		onlineSessionInfo.setLastTimeAlive(System.currentTimeMillis());
		return sessionInfoMap.put(onlineSessionInfo.getSucursalId()+"|"+onlineSessionInfo.getNumCaja()+"|"+onlineSessionInfo.getUsuarioId(),onlineSessionInfo);
	}
	
	public synchronized void killOldest(){
		long t0 = System.currentTimeMillis();
		final Set<String> keySet = sessionInfoMap.keySet();
		for(String k: keySet){
			long lastTimeAlive = sessionInfoMap.get(k).getLastTimeAlive();
			if( (t0 - lastTimeAlive) > ONLINE_TIMEOUT){
				sessionInfoMap.remove(k);
			}
		}		
	}
	
	public List<OnlineSessionInfo> getViewOnlineSessionInfo(){
		List<OnlineSessionInfo> result= new ArrayList<OnlineSessionInfo>();
		
		final Set<String> keySet = sessionInfoMap.keySet();
		for(String k: keySet){
			result.add(sessionInfoMap.get(k));
		}
		
		return result;
	}
}
