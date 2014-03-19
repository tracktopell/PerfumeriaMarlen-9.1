package com.pmarlen.web.common.view.util;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

public class ContextUtil {

	public static Locale getContextLocale(){			
		
        Locale locale = null;
        
        FacesContext context = FacesContext.getCurrentInstance();
        
        UIViewRoot viewRoot = context.getViewRoot();
        
        if (viewRoot != null) {
            locale = viewRoot.getLocale();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        
        return locale;		
		
	}
	
}
