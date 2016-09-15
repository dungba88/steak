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
import org.joo.steak.framework.exception.StateInitializationException;
import org.joo.steak.impl.event.AbstractStateChangedDispatcher;
import org.joo.steak.impl.event.StateChangedListenerDispatcherProxy;

public abstract class AbstractStateProxy extends AbstractStateChangedDispatcher implements State {

	private String stateClassName;

	public AbstractStateProxy(String stateClassName) {
		this.stateClassName = stateClassName;
	}

	protected State loadState() {
		try {
			State loadedState = (State) ReflectionUtils.loadClass(stateClassName);
			loadedState.addStateChangedListener(new StateChangedListenerDispatcherProxy(this));
			return loadedState;
		} catch (ClassCastException e) {
			throw new StateInitializationException("Class " + stateClassName + " is not subclass of State");
		}
	}

	public String getStateClassName() {
		return stateClassName;
	}
}
