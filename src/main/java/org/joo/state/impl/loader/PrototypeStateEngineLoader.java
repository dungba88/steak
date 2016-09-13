package org.joo.state.impl.loader;

import org.joo.state.framework.State;
import org.joo.state.impl.states.StatePrototypeProxy;

public class PrototypeStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return new StatePrototypeProxy(stateConfig.toString());
	}
}
