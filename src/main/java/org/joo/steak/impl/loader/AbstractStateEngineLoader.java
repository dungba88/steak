package org.joo.steak.impl.loader;

import org.joo.steak.framework.StateTransition;
import org.joo.steak.framework.loader.StateEngineLoader;
import org.joo.steak.impl.SimpleStateTransition;

public abstract class AbstractStateEngineLoader implements StateEngineLoader {

	@Override
	public StateTransition loadTransition(Object transitionConfig) {
		if (transitionConfig instanceof StateTransition)
			return (StateTransition) transitionConfig;
		return new SimpleStateTransition(transitionConfig.toString());
	}
}
