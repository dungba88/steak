package org.joo.steak.test.states;

import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractState;

public class DivideTestState extends AbstractState {
	
	public DivideTestState() {
		System.out.println("creating divide test state");
	}

	@Override
	public void onEntry(StateContext stateContext) {
		System.out.println("entering divide test state");
		TestStateContext testStateContext = (TestStateContext) stateContext;
		testStateContext.divideData(2);
		System.out.println("current data: " + testStateContext.getData());
		changeState("done", null);
	}

	@Override
	public void onExit(StateChangedEvent event) {
		System.out.println("exiting divide test state");
		System.out.println("");
	}
}
