package org.joo.state.impl.loader;

import org.joo.state.framework.State;
import org.joo.state.impl.ReflectionUtils;

public class ImmediateStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return (State) ReflectionUtils.loadClass(stateConfig.toString());
	}
}
