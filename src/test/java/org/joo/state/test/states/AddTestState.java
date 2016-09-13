package org.joo.state.test.states;

import org.joo.state.framework.StateContext;
import org.joo.state.impl.AbstractState;

public class AddTestState extends AbstractState {
	
	public AddTestState() {
		System.out.println("creating add test state");
	}

	@Override
	public void handle(StateContext stateContext) {
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.addData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}
}
