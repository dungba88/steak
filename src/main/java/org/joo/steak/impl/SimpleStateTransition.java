package org.joo.steak.impl;

/**
 * A simple implementation of <code>StateTransition</code>.
 * It always match the transition condition.
 * @author griever
 *
 */
public class SimpleStateTransition extends AbstractStateTransition {
	
	public SimpleStateTransition(String nextState) {
		super(nextState);
	}

	@Override
	public boolean isSatisfiedBy(Object args) {
		return true;
	}
}
