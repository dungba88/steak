package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.framework.StateContext;

public class CounterAttackState extends AbstractUnitState {

	@Override
	public void performAction() {
		Unit unit = getControllingUnit();
		Unit attackingUnit = getAttackingUnit();
		unit.changeTarget(attackingUnit);

		changeState("attack");
	}

	private Unit getAttackingUnit() {
		StateContext context = getStateContext();
		return (Unit) context.getContextMap().get("ATTACKING_UNIT");
	}

	@Override
	protected double calculateTakingDmg(double dmg) {
		return dmg;
	}
}
