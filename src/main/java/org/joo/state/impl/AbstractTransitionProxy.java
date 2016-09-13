package org.joo.state.impl;

import org.joo.state.framework.StateInitializationException;
import org.joo.state.framework.StateTransition;

public abstract class AbstractTransitionProxy implements StateTransition {

	private String className;

	public AbstractTransitionProxy(String className) {
		this.className = className;
	}

	@Override
	public boolean isSatisfiedBy(Object args) {
		StateTransition loadedTransition = getTransition();
		return loadedTransition.isSatisfiedBy(args);
	}

	@Override
	public String getNextState() {
		StateTransition loadedTransition = getTransition();
		return loadedTransition.getNextState();
	}

	protected abstract StateTransition getTransition();

	protected final StateTransition loadTransition() {
		try {
			return (StateTransition) ReflectionUtils.loadClass(className);
		} catch (ClassCastException e) {
			throw new StateInitializationException("Class " + className + " is not subclass of State");
		}
	}
}
