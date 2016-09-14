package org.joo.steak.impl.states;

import org.joo.steak.framework.StateTransition;
import org.joo.steak.impl.AbstractTransitionProxy;

public class TransitionPrototypeProxy extends AbstractTransitionProxy {

	public TransitionPrototypeProxy(String className) {
		super(className);
	}

	@Override
	protected StateTransition getTransition() {
		return loadTransition();
	}
}
