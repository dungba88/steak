package org.joo.state.impl;

import org.joo.state.framework.State;
import org.joo.state.framework.event.StateChangedEvent;
import org.joo.state.impl.event.AbstractStateChangedDispatcher;

public abstract class AbstractState extends AbstractStateChangedDispatcher implements State {

	protected void changeState(String action, Object args) {
		dispatchStateChangedEvent(new StateChangedEvent(this, action, args));
	}
}
