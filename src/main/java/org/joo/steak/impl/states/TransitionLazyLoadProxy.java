package org.joo.steak.impl.states;

import org.joo.steak.framework.StateTransition;
import org.joo.steak.impl.AbstractTransitionProxy;

public class TransitionLazyLoadProxy extends AbstractTransitionProxy {
	
	private StateTransition transition;
	
	public TransitionLazyLoadProxy(String className) {
		super(className);
	}

	@Override
	protected StateTransition getTransition() {
		if (transition == null) {
			transition = loadTransition();
		}
		return transition;
	}
}
