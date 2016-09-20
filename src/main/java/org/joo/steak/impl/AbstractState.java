/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.joo.steak.impl;

import org.joo.steak.framework.State;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.impl.event.AbstractStateChangedDispatcher;

/**
 * An abstract implementation of <code>State</code>. It includes the
 * <code>changeState</code> method as a more convenient way than the usual
 * <code>dispatchStateChangedEvent</code>.
 * 
 * @author griever
 *
 */
public abstract class AbstractState extends AbstractStateChangedDispatcher implements State {
	
	private StateContext stateContext;
	
	@Override
	public void initialize(StateContext stateContext) {
		if (this.stateContext == null)
			this.stateContext = stateContext;
	}

	/**
	 * Notify <code>StateManager</code> to change the state.
	 * 
	 * @param action
	 *            the action that triggers the state change.
	 */
	protected void changeState(String action) {
		changeState(action, null);
	}

	/**
	 * Notify <code>StateManager</code> to change the state.
	 * 
	 * @param action
	 *            the action that triggers the state change.
	 * @param args
	 *            the arguments used to be passed to <code>StateManager</code>.
	 */
	protected void changeState(String action, Object args) {
		dispatchStateChangedEvent(new StateChangeEvent(this, action, args));
	}
	
	public StateContext getStateContext() {
		return stateContext;
	}
}
