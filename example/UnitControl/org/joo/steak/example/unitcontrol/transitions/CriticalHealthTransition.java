package org.joo.steak.example.unitcontrol.transitions;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.TransitionContext;
import org.joo.steak.impl.AbstractStateTransition;

public class CriticalHealthTransition extends AbstractStateTransition {

	public CriticalHealthTransition(String nextState) {
		super(nextState);
	}

	@Override
	public boolean isSatisfiedBy(TransitionContext transitionContext) {
		StateContext stateContext = transitionContext.getStateContext();
		Unit unit = (Unit) stateContext.getContextMap().get("UNIT");
		if (unit.getHP() < unit.getMaxHP() * 0.25) {
			System.out.println(unit.getUnitName() + " is in critical health!");
			return true;
		}
		return false;
	}
}
