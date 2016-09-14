package org.joo.steak.framework.event;

/**
 * A listener will receive <code>StateChangedEvent</code>
 * When used for <code>StateManager</code> it will find the next state
 * and move to that state.
 * @author griever
 *
 */
public interface StateChangedListener {

	/**
	 * Called when the event is dispatched. Implementation
	 * should determine the next state using <code>event.getAction()</code>
	 * @param event
	 * 			the <code>StateChangedEvent</code>
	 */
	public void onStateChanged(StateChangedEvent event);
}
