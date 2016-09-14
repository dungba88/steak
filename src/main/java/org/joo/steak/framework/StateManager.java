package org.joo.steak.framework;

import java.util.Map;

import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangedListener;
import org.joo.steak.framework.loader.StateEngineLoader;

/**
 * The main class of state engine, it loads the configuration
 * and handles state changing logic.
 * @author griever
 *
 */
public interface StateManager extends StateChangedListener {

	/**
	 * Initialize the state engine. Implementation should make sure that this method
	 * can only be called once for each instance of <code>StateManager</code>.
	 * The <code>loader</code> may be used to load the actual states and transitions
	 * from configuration. Implementation should provide a default loader if null is passed.
	 * @param stateContext
	 * 			the state context
	 * @param configuration
	 * 			the state engine configuration
	 * @param loader
	 * 			the state engine loader
	 */
	public void initialize(StateContext stateContext, StateEngineConfiguration configuration, StateEngineLoader loader);
	
	/**
	 * Run the state engine. This will start from initial state, change state accordingly
	 * until final state is reached (i.e, no next state from it)
	 */
	public void run();

	/**
	 * Get the state context
	 * @return the state context
	 */
	public StateContext getStateContext();
	
	/**
	 * Get the current state ID
	 * @return the current state ID
	 */
	public String getCurrentState();
	
	/**
	 * Get state by its ID
	 * @param stateName 
	 * 			the state ID
	 * @return the state having specified ID
	 */
	public State getState(String stateName);
	
	/**
	 * Get all transitions available for a state
	 * with a specified <code>action</code>
	 * @param stateName
	 * @param action
	 * @return
	 */
	public StateTransition[] getTransitionsForState(String stateName, String action);

	public Map<String, State> getStates();

	public Map<String, Map<String, StateTransition[]>> getStateTransitions();
}
