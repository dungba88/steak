package org.joo.steak.impl.loader;

import org.joo.steak.framework.State;
import org.joo.steak.impl.states.StatePrototypeProxy;

public class PrototypeStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return new StatePrototypeProxy(stateConfig.toString());
	}
}
