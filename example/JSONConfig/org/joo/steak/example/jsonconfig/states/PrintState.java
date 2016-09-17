package org.joo.steak.example.jsonconfig.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

public class PrintState extends AbstractState {
	
	@Override
	public void onEntry(StateContext stateContext, StateChangedEvent event) {
		Object data = stateContext.getContextMap().get("data");
		System.out.println("current data: " + data);
		changeState("printed");
	}

	@Override
	public void onExit(StateChangedEvent event) {

	}
}
