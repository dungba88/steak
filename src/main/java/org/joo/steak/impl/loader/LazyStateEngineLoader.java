package org.joo.steak.impl.loader;

import org.joo.steak.framework.State;
import org.joo.steak.impl.states.StateLazyLoadProxy;

public class LazyStateEngineLoader extends AbstractStateEngineLoader {

	@Override
	public State loadState(Object stateConfig) {
		return new StateLazyLoadProxy(stateConfig.toString());
	}
}
