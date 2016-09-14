package org.joo.steak.test.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

public class SubtractTestState extends AbstractState {
	
	public SubtractTestState() {
		System.out.println("creating subtract test state");
	}

	@Override
	public void onEntry(StateContext stateContext) {
		System.out.println("entering subtract test state");
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.substractData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}

	@Override
	public void onExit(StateChangedEvent event) {
		System.out.println("exiting subtract test state");
		System.out.println("");
	}
}
