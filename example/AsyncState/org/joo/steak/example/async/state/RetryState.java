package org.joo.steak.example.async.state;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

/**
 * A state for retrying request. It maintains a counter of how many requests
 * have been executed.
 * 
 * @author griever
 *
 */
public class RetryState extends AbstractState {

	private int counter;

	public RetryState(int counter) {
		this.counter = counter;
	}

	@Override
	public void onEntry(StateContext stateContext, StateChangedEvent event) {
		System.out.println("retrying request. counter = " + counter);
		if (counter-- > 0)
			changeState("ok");
		else
			changeState("expired", event);
	}

	@Override
	public void onExit(StateChangedEvent event) {

	}
}
