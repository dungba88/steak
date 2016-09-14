package org.joo.steak.test.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

public class DefaultTestState extends AbstractState {
	
	public DefaultTestState() {
		System.out.println("creating default test state");
	}

	@Override
	public void onEntry(StateContext stateContext) {
		System.out.println("entering default test state");
		TestStateContext testStateContext = (TestStateContext) stateContext;
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}

	@Override
	public void onExit(StateChangedEvent event) {
		System.out.println("exiting default test state");
		System.out.println("");
	}
}
