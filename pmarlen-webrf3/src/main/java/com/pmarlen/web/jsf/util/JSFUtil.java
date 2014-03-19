package com.pmarlen.web.jsf.util;

import java.util.Map;

import javax.faces.context.FacesContext;

public class JSFUtil {

	public static String getParam(String id){
		
		FacesContext context = FacesContext.getCurrentInstance();
		
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		
		return params.get(id); 
		
	}
	
}
