package org.joo.steak.example.unitcontrol.config;

import org.joo.steak.example.unitcontrol.transitions.CriticalHealthTransition;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

public class DefensiveConfigurator extends AbstractConfigurator {

	@Override
	protected void configureTransitions(DefaultStateEngineConfiguration config) {
		config.addTransition("default", "done", "defend");
		config.addTransition("*", "attack", "attack");
		config.addTransitions("default", "attacked", new Object[] { new CriticalHealthTransition("idle"), "defend" });
		config.addTransition("attack", "done", "defend");
		config.addTransition("attack", "attacked", "defend");
		config.addTransition("defend", "done", "idle");
		config.addTransition("defend", "attacked", "idle");
		config.addTransitions("idle", "done", new Object[] { new CriticalHealthTransition("idle"), "attack" });
		config.addTransitions("idle", "attacked", new Object[] { new CriticalHealthTransition("idle"), "attack" });
	}
}
