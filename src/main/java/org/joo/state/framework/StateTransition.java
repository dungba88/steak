package org.joo.state.framework;

/**
 * A transition determines which state to move to
 * from a starting state based on some conditions.
 * @author griever
 *
 */
public interface StateTransition {

	/**
	 * Check if this transition upon some object should be in effect
	 * @param args the object to be checked
	 * @return true if condition matched and we should
	 * 				use this transition
	 */
	public boolean isSatisfiedBy(Object args);
	
	/**
	 * Get the next state from the starting state
	 * @return the next state
	 */
	public String getNextState();
}
