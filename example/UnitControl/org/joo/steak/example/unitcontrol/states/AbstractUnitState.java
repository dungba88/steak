package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.framework.StateContext;
import org.joo.steak.impl.DefaultState;

public abstract class AbstractUnitState extends DefaultState implements UnitState {
	
	public Unit getControllingUnit() {
		StateContext context = getStateContext();
		return (Unit) context.getContextMap().get("UNIT");
	}
	
	public Unit getTargetUnit() {
		StateContext context = getStateContext();
		return (Unit) context.getContextMap().get("TARGET_UNIT");
	}

	@Override
	public void onAttacked(Unit attackingUnit) {
		Unit unit = getControllingUnit();
		double calculatedDamage = calculateTakingDmg(attackingUnit.getDamage());
		unit.reduceHP(calculatedDamage);
		
		if (unit.isDead()) {
			changeState("dead");
		} else {
			changeState("attacked", unit);
		}
	}

	protected abstract double calculateTakingDmg(double dmg);
	
	protected void log(String msg) {
		System.out.println(msg);
	}
}
