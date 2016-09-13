package org.joo.state.impl;

import org.joo.state.framework.State;
import org.joo.state.framework.StateInitializationException;
import org.joo.state.impl.event.AbstractStateChangedDispatcher;
import org.joo.state.impl.event.StateChangedListenerDispatcherProxy;

public abstract class AbstractStateProxy extends AbstractStateChangedDispatcher implements State {
	
	private String stateClassName;
	
	public AbstractStateProxy(String stateClassName) {
		this.stateClassName = stateClassName;
	}
	
	protected State loadState() {
		try {
			State loadedState = (State) ReflectionUtils.loadClass(stateClassName);
			loadedState.addStateChangedListener(new StateChangedListenerDispatcherProxy(this));
			return loadedState;
		} catch (ClassCastException e) {
			throw new StateInitializationException("Class " + stateClassName + " is not subclass of State");
		}
	}
	
	public String getStateClassName() {
		return stateClassName;
	}
}
