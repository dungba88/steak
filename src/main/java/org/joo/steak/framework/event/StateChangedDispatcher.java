package org.joo.steak.framework.event;

/**
 * This is mainly used by <code>State</code> to dispatch
 * <code>StateChangedEvent</code> to <code>StateManager</code>
 * when it finishes its task and want to move on.
 * @author griever
 *
 */
public interface StateChangedDispatcher {

	/**
	 * Add a listener for <code>StateChangedEvent</code>
	 * @param listener
	 * 			the listener to be added
	 */
	public void addStateChangedListener(StateChangedListener listener);

	/**
	 * Remove a listener so that it will no longer receive <code>StateChangedEvent</code>
	 * @param listener
	 * 			the listener to be removed
	 */
	public void removeStateChangedListener(StateChangedListener listener);
	
	/**
	 * Dispatch a <code>StateChangedEvent</code> to all listeners
	 * @param event
	 * 			the event
	 */
	public void dispatchStateChangedEvent(StateChangedEvent event);
}
