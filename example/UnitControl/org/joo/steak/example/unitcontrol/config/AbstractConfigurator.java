package org.joo.steak.example.unitcontrol.config;

import org.joo.steak.example.unitcontrol.states.AttackState;
import org.joo.steak.example.unitcontrol.states.DefaultState;
import org.joo.steak.example.unitcontrol.states.DefendState;
import org.joo.steak.example.unitcontrol.states.IdleState;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.config.StateEngineConfigurator;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

public abstract class AbstractConfigurator extends Object implements
		StateEngineConfigurator {

	@Override
	public StateEngineConfiguration getConfiguration() {
		DefaultStateEngineConfiguration config = new DefaultStateEngineConfiguration();
		
		configureStates(config);
		
		configureTransitions(config);
		
		return config;
	}

	protected void configureStates(DefaultStateEngineConfiguration config) {
		config.addState("default", new DefaultState());
		config.addState("attack", new AttackState());
		config.addState("defend", new DefendState());
		config.addState("idle", new IdleState());
	}

	protected abstract void configureTransitions(DefaultStateEngineConfiguration config);
}
