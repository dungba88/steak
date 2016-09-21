package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.common.Unit;

public class IdleState extends AbstractUnitState {
	
	@Override
	public void performAction() {
		Unit unit = getControllingUnit();
		
		log(unit.getUnitName() + " idle.");
		
		unit.raiseHP(unit.getMaxHP() * 0.1);
		changeState("done");
	}

	@Override
	protected double calculateTakingDmg(double dmg) {
		return dmg;
	}
}
