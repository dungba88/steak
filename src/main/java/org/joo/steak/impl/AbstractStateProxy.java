package org.joo.steak.impl;

import org.joo.steak.framework.State;
import org.joo.steak.framework.StateInitializationException;
import org.joo.steak.impl.event.AbstractStateChangedDispatcher;
import org.joo.steak.impl.event.StateChangedListenerDispatcherProxy;

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
