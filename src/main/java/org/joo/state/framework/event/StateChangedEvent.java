package org.joo.state.framework.event;

import java.util.EventObject;

/**
 * This event indicates a state (wish to) changed
 * @author griever
 *
 */
public class StateChangedEvent extends EventObject {
	
	private static final long serialVersionUID = -3857604936551420446L;

	private String action;
	
	private Object args;
	
	public StateChangedEvent(Object source, String action, Object args) {
		super(source);
		this.action = action;
		this.args = args;
	}

	public String getAction() {
		return action;
	}

	public Object getArgs() {
		return args;
	}
}
