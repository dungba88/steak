package org.joo.steak.impl;

import org.joo.steak.framework.StateContext;

public abstract class AbstractStateContext implements StateContext {
	
	private String initialState;
	
	public AbstractStateContext(String initialState) {
		this.initialState = initialState;
	}
	
	@Override
	public String getInitialState() {
		return initialState;
	}
}
