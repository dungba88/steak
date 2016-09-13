package org.joo.state.framework.loader;

import org.joo.state.framework.State;
import org.joo.state.framework.StateTransition;

/**
 * This class can be used to load the actual state and transition
 * from a configuration.
 * @author griever
 *
 */
public interface StateEngineLoader {

	/**
	 * Load a state from configuration
	 * @param stateConfig
	 * 			the configuration
	 * @return the loaded state
	 */
	public State loadState(Object stateConfig);

	/**
	 * Load a transition from configuration
	 * @param transitionConfig
	 * 			the configuration
	 * @return the loaded transition
	 */
	public StateTransition loadTransition(Object transitionConfig);
}
