package org.joo.steak.example.unitcontrol.common;

import org.joo.steak.example.unitcontrol.UnitConfigurationFactory;
import org.joo.steak.example.unitcontrol.states.UnitState;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.StateManager;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.impl.DefaultStateContext;
import org.joo.steak.impl.DefaultStateManager;

public class Unit {
	
	private double maxHP;
	
	private double hp;
	
	private double dmg;

	private StateEngineConfiguration configuration;
	
	private StateManager stateManager;
	
	private String unitName;

	public Unit(String unitName, UnitType unitType, double hp, double dmg) {
		this.unitName = unitName;
		this.maxHP = hp;
		this.hp = hp;
		this.dmg = dmg;
		
		configuration = UnitConfigurationFactory.getConfigurator(unitType).getConfiguration();
		
		stateManager = new DefaultStateManager();
		StateContext stateContext = new DefaultStateContext("default");
		stateContext.getContextMap().put("UNIT", this);
		stateManager.initialize(stateContext, configuration, null);
		stateManager.run();
	}
	
	public void changeTarget(Unit unit) {
		putContextMap("TARGET_UNIT", unit);
	}
	
	public void performAction() {
		UnitState state = getCurrentState();
		if (state != null)
			state.performAction();
	}
	
	public void attack() {
		UnitState state = getCurrentState();
		if (state != null)
			state.changeState("attack");
	}
	
	public void onAttacked(Unit attackingUnit) {
		putContextMap("ATTACKING_UNIT", attackingUnit);
		
		UnitState state = getCurrentState();
		if (state != null)
			state.onAttacked(attackingUnit);
	}

	public void raiseHP(double hp) {
		this.hp += hp;
	}
	
	public void reduceHP(double hp) {
		this.hp -= hp;
		if (this.hp < 0)
			this.hp = 0;
	}
	
	private void putContextMap(String key, Object value) {
		stateManager.getStateContext().getContextMap().put(key, value);
	}
	
	public UnitState getCurrentState() {
		return (UnitState) stateManager.getState(stateManager.getCurrentState());
	}
	
	public double getHP() {
		return hp;
	}
	
	public double getMaxHP() {
		return maxHP;
	}
	
	public double getDamage() {
		return dmg;
	}
	
	public String getUnitName() {
		return unitName;
	}
	
	public boolean isDead() {
		return hp == 0;
	}
}
