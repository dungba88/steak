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
package org.joo.steak.example.unitcontrol.states;

import org.joo.steak.example.unitcontrol.units.Unit;
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
