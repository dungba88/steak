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
