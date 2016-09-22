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
package org.joo.steak.example.unitcontrol;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.joo.steak.example.unitcontrol.units.DefaultUnit;
import org.joo.steak.example.unitcontrol.units.Unit;
import org.joo.steak.example.unitcontrol.units.UnitType;

/**
 * Example of using Steak to control unit states and transitions. It is a
 * turn-based fighting game, which units are spawned and fight spontaneously.
 * Each units have 5 states: default, idle, attack, defend and counter-attack.
 * Each state does a different thing, and the transitions between states depend
 * on the unit class. There are 3 classes: Aggressive, Defensive and
 * Counter-Attack. The goal for each unit is to survive and kill the rest.
 * 
 * @author griever
 *
 */
public class UnitControlExample {

	private static final int UNIT_SPAWNED = 9;

	private Unit[] units;

	private Random rnd = ThreadLocalRandom.current();

	public static void main(String[] args) {

		try {
			UnitControlExample example = new UnitControlExample();
			example.run();
		} finally {
			ExecutorManager.getInstance().shutdown();
		}
	}

	/**
	 * Run the game
	 */
	public void run() {
		spawnUnits(UNIT_SPAWNED);

		for (int i = 1; i <= 100; i++) {
			doTurn(i);

			Unit winner = checkWin();
			if (winner != null) {
				System.out.println(winner.getUnitName() + " win!");
				break;
			}

			sleep();
		}
	}

	/**
	 * Sleep till next turn
	 */
	private void sleep() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check if only one unit survives.
	 * 
	 * @return the winner
	 */
	private Unit checkWin() {
		ArrayList<Unit> list = new ArrayList<Unit>();
		for (Unit unit : units) {
			if (!unit.isDead()) {
				list.add(unit);
			}
		}

		return list.size() == 1 ? list.get(0) : null;
	}

	/**
	 * Start a turn
	 * 
	 * @param turn
	 *            the turn number
	 */
	private void doTurn(int turn) {
		System.out.println("Turn " + turn);

		filterDeadUnits();

		randomizeTargets();

		System.out.println("----------");

		performUnitsAction();

		printUnitsHP();

		System.out.println("");
	}

	/**
	 * Print all units HP
	 */
	private void printUnitsHP() {
		for (Unit unit : units) {
			System.out.print(unit.getUnitName() + ": "
					+ normalize(unit.getHP()) + "/"
					+ normalize(unit.getMaxHP()) + "HP. ");
		}
		System.out.println("");
	}

	/**
	 * Perform all units actions
	 */
	private void performUnitsAction() {
		for (Unit unit : units) {
			unit.performAction();
		}
	}

	/**
	 * Randomly lock on new target for every units if they don't have currently,
	 * or the current target is dead
	 */
	private void randomizeTargets() {
		for (Unit unit : units) {
			if (isNoTarget(unit)) {
				changeRandomizedTarget(unit);
			}
		}
	}

	/**
	 * Randomly lock on new target for an unit
	 * 
	 * @param unit
	 *            the unit to change target
	 */
	private void changeRandomizedTarget(Unit unit) {
		Unit[] clone = cloneUnits();

		shuffle(clone);

		for (Unit targetUnit : clone) {
			if (targetUnit != unit) {
				System.out.println(unit.getUnitName() + " locks on "
						+ targetUnit.getUnitName());
				unit.changeTarget(targetUnit);
				break;
			}
		}
	}

	/**
	 * Shuffle the array
	 * 
	 * @param clone
	 *            the array to be shuffled
	 */
	private void shuffle(Unit[] clone) {
		for (int i = clone.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Unit a = clone[index];
			clone[index] = clone[i];
			clone[i] = a;
		}
	}

	/**
	 * Clone the units array
	 * 
	 * @return the clone array
	 */
	private Unit[] cloneUnits() {
		Unit[] clone = new Unit[units.length];
		for (int i = 0; i < units.length; i++) {
			clone[i] = units[i];
		}
		return clone;
	}

	/**
	 * Check if an unit doesn't have any target currently
	 * 
	 * @param unit
	 *            the unit to be checked
	 * @return true if and only if the unit doesn't have any target currently
	 */
	private boolean isNoTarget(Unit unit) {
		return unit.getTargetUnit() == null || unit.getTargetUnit().isDead();
	}

	/**
	 * Filter all dead units out of the units array
	 */
	private void filterDeadUnits() {
		ArrayList<Unit> list = new ArrayList<Unit>();
		for (Unit unit : units) {
			if (!unit.isDead())
				list.add(unit);
		}
		units = list.toArray(new Unit[0]);
	}

	/**
	 * Spawn units
	 * 
	 * @param count
	 *            number of units to be spawned
	 */
	private void spawnUnits(int count) {
		units = new Unit[count];

		for (int i = 0; i < count; i++) {
			UnitType unitType = chooseUnitType(i);
			double hp = 10 + Math.random() * 10;
			double dmg = 2 + Math.random() * 3;
			Unit unit = new DefaultUnit("Unit " + (i + 1), unitType, hp, dmg);
			System.out.println("Creating " + unit.getUnitName() + ": "
					+ unitType.toString() + ". DMG: " + normalize(dmg));
			units[i] = unit;
		}
	}

	/**
	 * Round the double value
	 * 
	 * @param maxHP
	 *            the value to be rounded
	 * @return the rounded value
	 */
	private String normalize(double maxHP) {
		return Math.round(maxHP) + "";
	}

	private UnitType chooseUnitType(int i) {
		if (i % 3 == 0)
			return UnitType.AGGRESSIVE;
		if (i % 3 == 1)
			return UnitType.DEFENSIVE;
		return UnitType.COUNTER_ATTACK;
	}
}
