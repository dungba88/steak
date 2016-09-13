package org.joo.state.impl;

import org.joo.state.framework.StateContext;

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
