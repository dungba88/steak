package org.joo.steak.test.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.impl.AbstractState;

public class DivideTestState extends AbstractState {
	
	public DivideTestState() {
		System.out.println("creating divide test state");
	}

	@Override
	public void handle(StateContext stateContext) {
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.divideData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}
}
