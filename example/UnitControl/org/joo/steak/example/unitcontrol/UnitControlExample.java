package org.joo.steak.example.unitcontrol;

import org.joo.steak.example.unitcontrol.common.Unit;
import org.joo.steak.example.unitcontrol.common.UnitType;


public class UnitControlExample {
	
	public static void main(String[] args) {

		UnitControlExample example = new UnitControlExample();
		example.run();
	}

	public void run() {
		Unit aggressiveUnit = new Unit("Unit 1", UnitType.COUNTER_ATTACK, 10, 2);
		Unit defensiveUnit = new Unit("Unit 2", UnitType.AGGRESSIVE, 10, 2);
		
		aggressiveUnit.changeTarget(defensiveUnit);
		defensiveUnit.changeTarget(aggressiveUnit);
		
		aggressiveUnit.attack();
		
		for(int i = 1; i <= 100; i++) {
			System.out.println("Turn " + i);
			aggressiveUnit.performAction();
			defensiveUnit.performAction();
			
			System.out.println("Unit 1 HP: " + aggressiveUnit.getHP());
			System.out.println("Unit 2 HP: " + defensiveUnit.getHP());
			
			System.out.println("");

			if (aggressiveUnit.isDead()) {
				System.out.println("Unit 2 win");
				break;
			} else if (defensiveUnit.isDead()) {
				System.out.println("Unit 1 win");
				break;
			}
		}
	}
}
