package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.framework.State;

public interface UnitState extends State {

	public void onAttacked(Unit attackingUnit);
	
	public void performAction();
}
