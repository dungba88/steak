package org.joo.steak.impl.loader;

import org.joo.steak.framework.State;

public class DefaultStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return (State) stateConfig;
	}
}
