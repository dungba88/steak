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


/**
 * Represent an unit. An unit can have HP, DMG, and its own state engine.
 * It can take turn and perform actions based on its current state.
 * 
 * @author griever
 *
 */
public interface Unit {
	
	/**
	 * Change the target to attack
	 * 
	 * @param unit
	 * 			the unit to be attacked
	 */
	public void changeTarget(Unit unit);
	
	/**
	 * Get the target unit
	 * 
	 * @return the target unit
	 */
	public Unit getTargetUnit();
	
	/**
	 * Perform action for the current turn
	 */
	public void performAction();
	
	/**
	 * Called when the unit is attacked
	 * 
	 * @param attackingUnit
	 * 			the attacker
	 */
	public void onAttacked(Unit attackingUnit);

	/**
	 * Raise unit HP
	 * 
	 * @param hp
	 */
	public void raiseHP(double hp);
	
	/**
	 * Reduce the unit HP
	 * 
	 * @param hp
	 */
	public void reduceHP(double hp);
	
	/**
	 * Get this unit's current HP
	 * 
	 * @return this unit's current HP
	 */
	public double getHP();
	
	/**
	 * Get this unit's max HP
	 * 
	 * @return this unit's max HP
	 */
	public double getMaxHP();
	
	/**
	 * Get this unit's damage
	 * 
	 * @return this unit's damage
	 */
	public double getDamage();
	
	/**
	 * Get this unit's name
	 * 
	 * @return this unit's name
	 */
	public String getUnitName();

	/**
	 * Check if this unit is dead
	 * 
	 * @return true if and only if this unit is dead
	 */
	public boolean isDead();
}
