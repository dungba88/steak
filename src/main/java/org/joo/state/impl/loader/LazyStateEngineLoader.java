package org.joo.state.impl.loader;

import org.joo.state.framework.State;
import org.joo.state.impl.states.StateLazyLoadProxy;

public class LazyStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return new StateLazyLoadProxy(stateConfig.toString());
	}
}
