package org.joo.steak.example.unitcontrol;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.example.unitcontrol.common.UnitType;

public class UnitControlExample {

	private Unit[] units;

	public static void main(String[] args) {

		UnitControlExample example = new UnitControlExample();
		example.run();
	}

	public void run() {
		spawnUnits(3);

		for (int i = 1; i <= 100; i++) {
			doTurn(i);

			if (checkWin())
				break;

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkWin() {
		ArrayList<Unit> list = new ArrayList<Unit>();
		for (Unit unit : units) {
			if (!unit.isDead()) {
				list.add(unit);
			}
		}

		if (list.size() == 1)
			System.out.println(list.get(0).getUnitName() + " win!");

		return list.size() == 1;
	}

	private void doTurn(int turn) {
		System.out.println("Turn " + turn);
		
		filterDeadUnits();

		randomizeTargets();

		performUnitsAction();

		printUnitsHP();

		System.out.println("");
	}

	private void filterDeadUnits() {
		ArrayList<Unit> list = new ArrayList<Unit>();
		for (Unit unit : units) {
			if (!unit.isDead())
				list.add(unit);
		}
		units = list.toArray(new Unit[0]);
	}

	private void printUnitsHP() {
		for (Unit unit : units) {
			System.out.print(unit.getUnitName() + ": "
					+ normalize(unit.getHP()) + "/"
					+ normalize(unit.getMaxHP()) + ". ");
		}
		System.out.println("");
	}

	private String normalize(double maxHP) {
		return Math.round(maxHP) + "";
	}

	private void performUnitsAction() {
		for (Unit unit : units) {
			unit.performAction();
		}
	}

	private void randomizeTargets() {
		for (Unit unit : units) {
			if (isNoTarget(unit)) {
				changeRandomizedTarget(unit);
			}
		}
	}

	private void changeRandomizedTarget(Unit unit) {
		Unit[] clone = cloneUnits();

		shuffle(clone);

		for (Unit targetUnit : clone) {
			if (targetUnit != unit) {
				System.out.println(unit.getUnitName() + " locks on " + targetUnit.getUnitName());
				unit.changeTarget(targetUnit);
				break;
			}
		}
	}

	private void shuffle(Unit[] clone) {
		Random rnd = ThreadLocalRandom.current();
		for (int i = clone.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			// Simple swap
			Unit a = clone[index];
			clone[index] = clone[i];
			clone[i] = a;
		}
	}

	private Unit[] cloneUnits() {
		Unit[] clone = new Unit[units.length];
		for (int i = 0; i < units.length; i++) {
			clone[i] = units[i];
		}
		return clone;
	}

	private boolean isNoTarget(Unit unit) {
		return unit.getTargetUnit() == null || unit.getTargetUnit().isDead();
	}

	private Unit[] spawnUnits(int count) {
		units = new Unit[count];

		for (int i = 0; i < count; i++) {
			UnitType unitType = chooseUnitType(i);
			double hp = 10 + Math.random() * 10;
			double dmg = 2 + Math.random() * 3;
			Unit unit = new Unit("Unit " + (i + 1), unitType, hp, dmg);
			System.out.println("Creating " + unit.getUnitName() + ": " + unitType.toString() + ". DMG: " + normalize(dmg));
			units[i] = unit;
		}

		return units;
	}

	private UnitType chooseUnitType(int i) {
		if (i % 3 == 0)
			return UnitType.AGGRESSIVE;
		if (i % 3 == 1)
			return UnitType.DEFENSIVE;
		return UnitType.COUNTER_ATTACK;
	}
}
