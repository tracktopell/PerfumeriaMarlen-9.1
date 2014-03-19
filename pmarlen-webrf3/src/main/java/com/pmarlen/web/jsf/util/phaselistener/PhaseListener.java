package com.pmarlen.web.jsf.util.phaselistener;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;

@SuppressWarnings("serial")
public class PhaseListener implements javax.faces.event.PhaseListener {

	public void afterPhase(PhaseEvent event) {
		event.getFacesContext().getExternalContext().log("AFTER "+event.getPhaseId()); 		
	}
	
	public void beforePhase(PhaseEvent event) {
		event.getFacesContext().getExternalContext().log("BEFORE "+event.getPhaseId());
	}
		
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
}
