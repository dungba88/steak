package org.joo.steak.test.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

public class MultiplyTestState extends AbstractState {
	
	public MultiplyTestState() {
		System.out.println("creating multiply test state");
	}

	@Override
	public void onEntry(StateContext stateContext) {
		System.out.println("entering multiply test state");
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.multiplyData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}

	@Override
	public void onExit(StateChangedEvent event) {
		System.out.println("exiting multiply test state");
		System.out.println("");
	}
}
