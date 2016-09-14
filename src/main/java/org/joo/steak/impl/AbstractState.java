package org.joo.steak.impl;

import org.joo.steak.framework.State;
import org.joo.steak.framework.event.StateChangedEvent;
import org.joo.steak.impl.event.AbstractStateChangedDispatcher;

public abstract class AbstractState extends AbstractStateChangedDispatcher implements State {

	protected void changeState(String action, Object args) {
		dispatchStateChangedEvent(new StateChangedEvent(this, action, args));
	}
}
