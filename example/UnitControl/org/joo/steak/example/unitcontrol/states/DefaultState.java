package org.joo.steak.example.unitcontrol.states;


public class DefaultState extends AbstractUnitState {
	
	@Override
	public void performAction() {
		
	}

	@Override
	protected double calculateTakingDmg(double dmg) {
		return dmg;
	}
}
