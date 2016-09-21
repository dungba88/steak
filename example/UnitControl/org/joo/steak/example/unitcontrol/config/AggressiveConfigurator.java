package org.joo.steak.example.unitcontrol.config;

import org.joo.steak.example.unitcontrol.transitions.CriticalHealthTransition;
import org.joo.steak.example.unitcontrol.transitions.TargetInCriticalHealthTransition;
import org.joo.steak.impl.config.DefaultStateEngineConfiguration;

public class AggressiveConfigurator extends AbstractConfigurator {

	@Override
	protected void configureTransitions(DefaultStateEngineConfiguration config) {
		config.addTransition("*", "attack", "attack");
		config.addTransitions("*", "attacked", new Object[] { new TargetInCriticalHealthTransition("attack"), new CriticalHealthTransition("idle"), "attack" });
		config.addTransition("attack", "done", "attack");
		config.addTransition("defend", "done", "attack");
		config.addTransitions("idle", "done", new Object[] { new TargetInCriticalHealthTransition("attack"), new CriticalHealthTransition("idle"), "attack" });
	}
}
