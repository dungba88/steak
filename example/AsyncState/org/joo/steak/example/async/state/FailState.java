package org.joo.steak.example.async.state;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

/**
 * A state used when we failed to execute HTTP request
 * 
 * @author griever
 *
 */
public class FailState extends AbstractState {

	@Override
	public void onEntry(StateContext stateContext, StateChangedEvent event) {
		System.out.println("Fail to execute HTTP request");
		changeState("exit", event);
	}

	@Override
	public void onExit(StateChangedEvent event) {

	}
}
