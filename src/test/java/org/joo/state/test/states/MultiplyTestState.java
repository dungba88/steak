package org.joo.state.test.states;

import org.joo.state.framework.StateContext;
import org.joo.state.impl.AbstractState;

public class MultiplyTestState extends AbstractState {
	
	public MultiplyTestState() {
		System.out.println("creating multiply test state");
	}

	@Override
	public void handle(StateContext stateContext) {
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.multiplyData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}
}
