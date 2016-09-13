package org.joo.state.impl.states;

import org.joo.state.framework.State;
import org.joo.state.framework.StateContext;
import org.joo.state.impl.AbstractStateProxy;

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
