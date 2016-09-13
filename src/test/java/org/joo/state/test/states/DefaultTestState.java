package org.joo.state.test.states;

import org.joo.state.framework.StateContext;
import org.joo.state.impl.AbstractState;

public class DefaultTestState extends AbstractState {
	
	public DefaultTestState() {
		System.out.println("creating default test state");
	}

	@Override
	public void handle(StateContext stateContext) {
		TestStateContext testStateContext = (TestStateContext) stateContext;
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}
}
