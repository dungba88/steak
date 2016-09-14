package org.joo.steak.impl.loader;

import org.joo.steak.framework.State;
import org.joo.steak.impl.ReflectionUtils;

public class ImmediateStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return (State) ReflectionUtils.loadClass(stateConfig.toString());
	}
}
