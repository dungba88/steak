package org.joo.steak.example.jsonconfig.states;

import java.util.Map;

import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.impl.AbstractState;

public class ConcatState extends AbstractState {
	
	private double max;
	
	public ConcatState() {
		this.max = 10;
	}
	
	public ConcatState(double max) {
		this.max = max;
	}

	@Override
	public void onEntry(StateChangeEvent event) {
		Map<String, Object> stateContextMap = getStateContext().getContextMap();
		Object data = stateContextMap.get("data");
		double d = 0;
		if (data != null) {
			d = (double) data;
		}
		d += Math.random();
		
		stateContextMap.put("data", d);
		
		if (d <= max) {
			changeState("ok");
		} else {
			changeState("exceed");
		}
	}

	@Override
	public void onExit(StateChangeEvent event) {

	}
}
