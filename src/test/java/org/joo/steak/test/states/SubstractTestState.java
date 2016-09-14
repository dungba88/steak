package org.joo.steak.test.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.impl.AbstractState;

public class SubstractTestState extends AbstractState {
	
	public SubstractTestState() {
		System.out.println("creating substract test state");
	}

	@Override
	public void handle(StateContext stateContext) {
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.substractData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}
}
