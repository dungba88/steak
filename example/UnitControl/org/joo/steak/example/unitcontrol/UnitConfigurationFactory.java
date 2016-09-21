package org.joo.steak.example.unitcontrol;

import org.joo.steak.example.unitcontrol.common.UnitType;
import org.joo.steak.example.unitcontrol.config.AggressiveConfigurator;
import org.joo.steak.example.unitcontrol.config.CounterAttackConfigurator;
import org.joo.steak.example.unitcontrol.config.DefensiveConfigurator;
import org.joo.steak.framework.config.StateEngineConfigurator;

public class UnitConfigurationFactory {

	public static StateEngineConfigurator getConfigurator(UnitType unitType) {
		
		switch (unitType) {
			case AGGRESSIVE:
				return new AggressiveConfigurator();
			case DEFENSIVE:
				return new DefensiveConfigurator();
			case COUNTER_ATTACK:
				return new CounterAttackConfigurator();
		}
		return null;
	}
}
