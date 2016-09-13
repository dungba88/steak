package org.joo.state.impl.event;

import org.joo.state.framework.event.StateChangedDispatcher;
import org.joo.state.framework.event.StateChangedEvent;
import org.joo.state.framework.event.StateChangedListener;

public class StateChangedListenerDispatcherProxy implements StateChangedListener {
	
	private StateChangedDispatcher wrappee;
	
	public StateChangedListenerDispatcherProxy(StateChangedDispatcher wrappee) {
		this.wrappee = wrappee;
	}

	@Override
	public void onStateChanged(StateChangedEvent event) {
		StateChangedEvent custEvent = new StateChangedEvent(wrappee, event.getAction(), event.getArgs());
		wrappee.dispatchStateChangedEvent(custEvent);
	}
}
