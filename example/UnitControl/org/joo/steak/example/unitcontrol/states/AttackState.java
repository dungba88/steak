package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.common.Unit;

public class AttackState extends AbstractUnitState {
	
	@Override
	public void performAction() {
		Unit unit = getControllingUnit();
		Unit targetUnit = getTargetUnit();
		
		if (targetUnit != null) {
			log(unit.getUnitName() + " attacks " + targetUnit.getUnitName());
			
			targetUnit.onAttacked(unit);
			
			changeState("done");
		}
	}

	@Override
	protected double calculateTakingDmg(double dmg) {
		return dmg;
	}
}
