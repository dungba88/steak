package org.joo.state.impl.states;

import org.joo.state.framework.State;
import org.joo.state.framework.StateContext;
import org.joo.state.impl.AbstractStateProxy;

public class StateLazyLoadProxy extends AbstractStateProxy {
	
	private State loadedState;
	
	public StateLazyLoadProxy(String stateClassName) {
		super(stateClassName);
	}

	@Override
	public void handle(StateContext stateContext) {
		if (loadedState == null) {
			loadedState = loadState();
		}
		loadedState.handle(stateContext);
	}
}
