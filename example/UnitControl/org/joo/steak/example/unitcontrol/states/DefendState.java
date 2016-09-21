package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.common.Unit;


public class DefendState extends AbstractUnitState {
	
	private int counter = 0;
	
	@Override
	public void performAction() {
		Unit unit = getControllingUnit();
		
		log(unit.getUnitName() + " defends.");
		
		unit.raiseHP(unit.getMaxHP() * 0.05);
		
		if (turnReplenished())
			changeState("done");
		
		counter++;
	}

	private boolean turnReplenished() {
		return counter != 0 && counter % 2 == 0;
	}

	@Override
	protected double calculateTakingDmg(double dmg) {
		return dmg / 2;
	}
}
