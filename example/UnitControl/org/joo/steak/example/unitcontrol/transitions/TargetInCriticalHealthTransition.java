package org.joo.steak.example.unitcontrol.transitions;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.TransitionContext;
import org.joo.steak.impl.AbstractStateTransition;

public class TargetInCriticalHealthTransition extends AbstractStateTransition {

	public TargetInCriticalHealthTransition(String nextState) {
		super(nextState);
	}

	@Override
	public boolean isSatisfiedBy(TransitionContext transitionContext) {
		StateContext stateContext = transitionContext.getStateContext();
		Unit unit = (Unit) stateContext.getContextMap().get("TARGET_UNIT");
		if (unit.getHP() < unit.getMaxHP() * 0.25) {
			return true;
		}
		return false;
	}
}
