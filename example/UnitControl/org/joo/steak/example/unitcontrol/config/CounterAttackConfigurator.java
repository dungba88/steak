package org.joo.steak.example.unitcontrol.config;

import org.joo.steak.example.unitcontrol.states.CounterAttackState;
import org.joo.steak.example.unitcontrol.transitions.CriticalHealthTransition;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

public class CounterAttackConfigurator extends AbstractConfigurator {
	
	protected void configureStates(DefaultStateEngineConfiguration config) {
		super.configureStates(config);
		config.addState("counterattack", new CounterAttackState());
	}

	@Override
	protected void configureTransitions(DefaultStateEngineConfiguration config) {
		config.addTransition("default", "done", "defend");
		config.addTransition("*", "attack", "attack");
		config.addTransitions("*", "attacked", new Object[] { new CriticalHealthTransition("idle"), "counterattack" });
		config.addTransitions("attack", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
		config.addTransitions("defend", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
		config.addTransitions("idle", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
	}
}
