package org.joo.steak.impl.states;

import org.joo.steak.framework.State;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.AbstractStateProxy;

public class StateLazyLoadProxy extends AbstractStateProxy {
	
	private State loadedState;
	
	public StateLazyLoadProxy(String stateClassName) {
		super(stateClassName);
	}

	@Override
	public void onEntry(StateContext stateContext) {
		if (loadedState == null) {
			loadedState = loadState();
		}
		loadedState.onEntry(stateContext);
	}
	
	@Override
	public void onExit(StateChangedEvent event) {
		if (loadedState == null) {
			loadedState = loadState();
		}
		loadedState.onExit(event);
	}
}
