package org.joo.state.impl.loader;

import org.joo.state.framework.State;

public class DefaultStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return (State) stateConfig;
	}
}
