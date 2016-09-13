package org.joo.state.impl.loader;

import org.joo.state.framework.StateTransition;
import org.joo.state.framework.loader.StateEngineLoader;
import org.joo.state.impl.SimpleStateTransition;

public abstract class AbstractStateEngineLoader implements StateEngineLoader {

	@Override
	public StateTransition loadTransition(Object transitionConfig) {
		if (transitionConfig instanceof StateTransition)
			return (StateTransition) transitionConfig;
		return new SimpleStateTransition(transitionConfig.toString());
	}
}
