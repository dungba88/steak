package org.joo.state.impl.states;

import org.joo.state.framework.StateTransition;
import org.joo.state.impl.AbstractTransitionProxy;

public class TransitionPrototypeProxy extends AbstractTransitionProxy {

	public TransitionPrototypeProxy(String className) {
		super(className);
	}

	@Override
	protected StateTransition getTransition() {
		return loadTransition();
	}
}
