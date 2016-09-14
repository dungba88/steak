package org.joo.steak.test.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

public class AddTestState extends AbstractState {
	
	public AddTestState() {
		System.out.println("creating add test state");
	}

	@Override
	public void onEntry(StateContext stateContext) {
		System.out.println("entering add test state");
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.addData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}

	@Override
	public void onExit(StateChangedEvent event) {
		System.out.println("exiting add test state");
		System.out.println("");
	}
}
