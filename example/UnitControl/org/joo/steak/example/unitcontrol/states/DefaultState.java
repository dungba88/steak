package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.framework.event.StateChangeEvent;


public class DefaultState extends AbstractUnitState {
	
	@Override
	public void onEntry(StateChangeEvent event) {
		changeState("done");
	}
	
	@Override
	public void performAction() {
		
	}

	@Override
	protected double calculateTakingDmg(double dmg) {
		return dmg;
	}
}
