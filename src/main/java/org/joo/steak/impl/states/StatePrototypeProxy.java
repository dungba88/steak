package org.joo.steak.impl.states;

import org.joo.steak.framework.State;
import org.joo.steak.framework.StateContext;
import org.joo.steak.impl.AbstractStateProxy;

public class StatePrototypeProxy extends AbstractStateProxy {
	
	public StatePrototypeProxy(String stateClassName) {
		super(stateClassName);
	}

	@Override
	public void handle(StateContext stateContext) {
		State loadedState = loadState();
		loadedState.handle(stateContext);
	}
}
