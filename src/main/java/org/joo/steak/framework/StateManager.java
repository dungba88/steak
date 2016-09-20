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
package org.joo.steak.framework;

import java.util.Map;

import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangedListener;
import org.joo.steak.framework.event.StateEngineDispatcher;
import org.joo.steak.framework.exception.StateExceptionHandler;
import org.joo.steak.framework.loader.StateEngineLoader;

/**
 * The main class of state engine, it loads the configuration and handles state
 * changing logic.
 * 
 * @author griever
 *
 */
public interface StateManager extends StateChangedListener, StateEngineDispatcher {

	/**
	 * Initialize the state engine. Implementation should make sure that this
	 * method can only be called once for each instance of
	 * <code>StateManager</code>. The <code>loader</code> may be used to load
	 * the actual states and transitions from configuration. Implementation
	 * should provide a default loader if null is passed.
	 * 
	 * @param stateContext
	 *            the state context
	 * @param configuration
	 *            the state engine configuration
	 * @param loader
	 *            the state engine loader
	 */
	public void initialize(StateContext stateContext, StateEngineConfiguration configuration, StateEngineLoader loader);

	/**
	 * Run the state engine. This will start from initial state, change state
	 * accordingly until final state is reached (i.e, no next state from it)
	 */
	public void run();

	/**
	 * Get the state context
	 * 
	 * @return the state context
	 */
	public StateContext getStateContext();

	/**
	 * Get the current state ID
	 * 
	 * @return the current state ID
	 */
	public String getCurrentState();

	/**
	 * Get state by its ID
	 * 
	 * @param stateName
	 *            the state ID
	 * @return the state having specified ID
	 */
	public State getState(String stateName);

	/**
	 * Get all transitions available for a state with a specified
	 * <code>action</code>
	 * 
	 * @param stateName
	 * @param action
	 * @return
	 */
	public StateTransition[] getTransitionsForState(String stateName, String action);

	/**
	 * Get the unmodifiable states map
	 * 
	 * @return the states map
	 */
	public Map<String, State> getStates();

	/**
	 * Get the unmodifiable transitions map
	 * 
	 * @return the transitions map
	 */
	public Map<String, Map<String, StateTransition[]>> getStateTransitions();
	
	/**
	 * Register new exception handler
	 * 
	 * @param handler
	 */
	public void registerExceptionHandler(StateExceptionHandler handler);
	
	/**
	 * Unregister exception handler
	 * 
	 * @param handler
	 */
	public void unregisterExceptionHandler(StateExceptionHandler handler);
}
