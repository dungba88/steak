package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangeEvent;

public class CounterAttackState extends AbstractUnitState {
	
	@Override
	public void onEntry(StateChangeEvent event) {
		Unit unit = getControllingUnit();
		Unit attackingUnit = getAttackingUnit();
		unit.changeTarget(attackingUnit);
		
		log(unit.getUnitName() + " counterattacks " + attackingUnit.getUnitName());

		changeState("attack");
	}

	@Override
	public void performAction() {
		
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
