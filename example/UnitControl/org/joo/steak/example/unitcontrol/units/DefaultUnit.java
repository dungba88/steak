/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.joo.steak.example.unitcontrol.units;

import org.joo.steak.example.unitcontrol.ExecutorManager;
import org.joo.steak.example.unitcontrol.UnitConfigurationFactory;
import org.joo.steak.example.unitcontrol.states.UnitState;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.StateManager;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.framework.exception.StateExceptionHandler;
import org.joo.steak.framework.exception.StateExecutionException;
import org.joo.steak.impl.DefaultStateContext;
import org.joo.steak.impl.DefaultStateManager;
import org.joo.steak.impl.event.DefaultStateEngineListener;

/**
 * Represent an unit. An unit can have HP, DMG, and its own state engine. It can
 * take turn and perform actions based on its current state.
 * 
 * @author griever
 *
 */
public class DefaultUnit implements Unit {

	private double maxHP;

	private double hp;

	private double dmg;

	private StateEngineConfiguration configuration;

	private StateManager stateManager;

	private String unitName;

	private Object hpLock = new Object();

	public DefaultUnit(String unitName, UnitType unitType, double hp, double dmg) {
		this.unitName = unitName;
		this.maxHP = hp;
		this.hp = hp;
		this.dmg = dmg;

		configuration = UnitConfigurationFactory.getConfigurator(unitType)
				.getConfiguration();

		stateManager = new DefaultStateManager();
		StateContext stateContext = new DefaultStateContext("default");
		stateContext.getContextMap().put("UNIT", this);
		stateManager.initialize(stateContext, configuration, null);
		stateManager.run();

		stateManager.registerExceptionHandler(new StateExceptionHandler() {

			@Override
			public boolean handleException(StateExecutionException exception) {
				System.out.println(exception.getCause().getMessage());
				return true;
			}
		});

		stateManager.addStateEngineListener(new DefaultStateEngineListener() {

			@Override
			public void onFinish(StateChangeEvent event) {
				System.out.println(unitName + " finished! "
						+ stateManager.getCurrentState() + " -> "
						+ event.getAction());
			}
		});
	}

	@Override
	public void changeTarget(Unit unit) {
		if (this.equals(unit))
			return;

		putContextMap("TARGET_UNIT", unit);
	}

	@Override
	public DefaultUnit getTargetUnit() {
		return (DefaultUnit) getContextMap("TARGET_UNIT");
	}

	@Override
	public void performAction() {
		if (isDead())
			return;

		UnitState state = getCurrentState();
		if (state != null)
			state.performAction();
	}

	@Override
	public void onAttacked(Unit attackingUnit) {
		putContextMap("ATTACKING_UNIT", attackingUnit);

		UnitState state = getCurrentState();

		if (state != null) {
			ExecutorManager.getInstance().execute(new Runnable() {

				@Override
				public void run() {
					state.onAttacked(attackingUnit);
				}
			});
		}
	}

	@Override
	public void raiseHP(double hp) {
		synchronized (hpLock) {
			if (isDead()) // to make sure for concurrent issue
				return;
			this.hp += hp;
			if (this.hp > maxHP)
				this.hp = maxHP;
		}
	}

	@Override
	public void reduceHP(double hp) {
		synchronized (hpLock) {
			this.hp -= hp;
			if (this.hp < 0)
				this.hp = 0;
		}
	}

	private void putContextMap(String key, Object value) {
		stateManager.getStateContext().getContextMap().put(key, value);
	}

	private Object getContextMap(String key) {
		return stateManager.getStateContext().getContextMap().get(key);
	}

	private UnitState getCurrentState() {
		return (UnitState) stateManager
				.getState(stateManager.getCurrentState());
	}

	@Override
	public double getHP() {
		return hp;
	}

	@Override
	public double getMaxHP() {
		return maxHP;
	}

	@Override
	public double getDamage() {
		return dmg;
	}

	@Override
	public String getUnitName() {
		return unitName;
	}

	@Override
	public boolean isDead() {
		return hp == 0;
	}
}
