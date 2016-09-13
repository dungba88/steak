package org.joo.state.framework;

/**
 * A state context is an object to be passed amongst <code>State</code>. 
 * It should be unique for each run of <code>StateManager</code>.
 * @author griever
 *
 */
public interface StateContext {
	
	/**
	 * Get the initial state
	 * @return the initial state
	 */
	public String getInitialState();
}
