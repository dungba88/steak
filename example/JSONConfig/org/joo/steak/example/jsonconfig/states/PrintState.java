package org.joo.steak.example.jsonconfig.states;

import java.util.Map;

import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.impl.AbstractState;

public class PrintState extends AbstractState {
	
	@Override
	public void onEntry(StateChangeEvent event) {
		Map<String, Object> stateContextMap = getStateContext().getContextMap();
		Object data = stateContextMap.get("data");
		System.out.println("current data: " + data);
		changeState("printed");
	}

	@Override
	public void onExit(StateChangeEvent event) {

	}
}
