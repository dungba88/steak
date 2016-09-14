package org.joo.steak.impl;

import org.joo.steak.framework.StateTransition;

/**
 * A abstract implementation of <code>StateTransition</code>.
 * Subclasses need to implement <code>isSatisfiedBy</code>
 * @author griever
 *
 */
public abstract class AbstractStateTransition implements StateTransition {
	
	private String nextState;
	
	public AbstractStateTransition(String nextState) {
		this.nextState = nextState;
	}

	@Override
	public String getNextState() {
		return nextState;
	}
}
