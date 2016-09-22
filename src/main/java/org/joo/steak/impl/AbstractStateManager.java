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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joo.steak.framework.State;
import org.joo.steak.framework.StateContext;
import org.joo.steak.framework.StateManager;
import org.joo.steak.framework.StateTransition;
import org.joo.steak.framework.TransitionContext;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangeEvent;
import org.joo.steak.framework.exception.StateExceptionHandler;
import org.joo.steak.framework.exception.StateExecutionException;
import org.joo.steak.framework.loader.StateEngineLoader;
import org.joo.steak.impl.event.AbstractStateEngineDispatcher;
import org.joo.steak.impl.loader.DefaultStateEngineLoader;

public abstract class AbstractStateManager extends AbstractStateEngineDispatcher implements StateManager {

	private static final String GLOBAL_ACTION = "*";
	
	private List<StateExceptionHandler> exceptionHandlers;

	private Map<String, State> statesMap;

	private Map<String, Map<String, StateTransition[]>> transitionsMap;

	private StateContext stateContext;

	private String currentState;

	private String initialState;

	private boolean initialized;

	private boolean ran;

	@Override
	public void initialize(StateContext stateContext, StateEngineConfiguration configuration,
			StateEngineLoader loader) {
		if (stateContext == null || configuration == null)
			throw new NullPointerException("stateContext or configuration is null");

		if (!this.initialized) {
			this.exceptionHandlers = new ArrayList<>();
			
			if (loader == null)
				loader = new DefaultStateEngineLoader();

			this.stateContext = stateContext;
			this.initialState = stateContext.getInitialState();
			this.statesMap = initializeStatesConfig(stateContext, loader, configuration.getStatesConfig());
			this.transitionsMap = initializeTransitionsConfig(loader, configuration.getTransitionsConfig());

			doInitialization(configuration);

			this.initialized = true;
		}
	}

	@Override
	public void run() {
		if (ran) {
			throw new IllegalStateException("StateManager is already running");
		}
		ran = true;

		dispatchStateEngineStartEvent(stateContext);

		doRun();
	}
	
	@Override
	public void onStateChange(StateChangeEvent event) {
		if (event == null)
			return;
		
		checkStateIntegrity(event);

		doOnStateChange(event);
	}

	private Map<String, Map<String, StateTransition[]>> initializeTransitionsConfig(StateEngineLoader loader,
			Map<String, Map<String, Object[]>> transitionsConfig) {

		Map<String, Map<String, StateTransition[]>> map = doInitializeTransitionsMap();
		if (transitionsConfig != null) {
			for (String stateName : transitionsConfig.keySet()) {
				Map<String, Object[]> transitionConfig = transitionsConfig.get(stateName);
				Map<String, StateTransition[]> transitionsMap = initializeTransitionConfig(loader, transitionConfig);
				map.put(stateName, transitionsMap);
			}
		}
		return map;
	}

	private Map<String, StateTransition[]> initializeTransitionConfig(StateEngineLoader loader, Map<String, Object[]> transitionConfig) {
		Map<String, StateTransition[]> transitionsMap = doInitializeTransitionMap();
		for (String action : transitionConfig.keySet()) {
			Object[] transitionObjects = transitionConfig.get(action);
			StateTransition[] transitions = loadTransitions(loader, transitionObjects);
			transitionsMap.put(action, transitions);
		}
		return transitionsMap;
	}

	private StateTransition[] loadTransitions(StateEngineLoader loader, Object[] transitionObjects) {
		List<StateTransition> transitions = new ArrayList<>();
		for (Object transitionObject : transitionObjects) {
			StateTransition transition = loader.loadTransition(transitionObject);
			transitions.add(transition);
		}
		return transitions.toArray(new StateTransition[0]);
	}

	private Map<String, State> initializeStatesConfig(StateContext stateContext, 
			StateEngineLoader loader, Map<String, Object> statesConfig) {

		Map<String, State> statesMap = doInitializeStatesMap();
		if (statesConfig != null) {
			for (String key : statesConfig.keySet()) {
				State state = loader.loadState(statesConfig.get(key));
				initializeState(state);
				statesMap.put(key, state);
			}
		}
		return statesMap;
	}

	private void initializeState(State state) {
		state.addStateChangedListener(this);
		state.initialize(stateContext);
	}

	@Override
	public State getState(String stateName) {
		if (stateName == null)
			return null;
		
		if (statesMap != null && statesMap.containsKey(stateName)) {
			return statesMap.get(stateName);
		}
		return null;
	}

	@Override
	public StateTransition[] getTransitionsForState(String stateName, String action) {
		if (transitionsMap != null) {
			StateTransition[] transitions = findMatchedTransitions(stateName, action);
			if (transitions == null)
				transitions = findMatchedTransitions(GLOBAL_ACTION, action);
			if (transitions != null)
				return transitions;
		}
		return new StateTransition[0];
	}
	
	private StateTransition[] findMatchedTransitions(String stateName, String action) {
		if (transitionsMap.containsKey(stateName)) {
			Map<String, StateTransition[]> map = transitionsMap.get(stateName);
			if (map.containsKey(action))
				return map.get(action);
			if (map.containsKey(GLOBAL_ACTION))
				return map.get(GLOBAL_ACTION);
		}
		return null;
	}

	@Override
	public void registerExceptionHandler(StateExceptionHandler handler) {
		this.exceptionHandlers.add(handler);
	}
	
	@Override
	public void unregisterExceptionHandler(StateExceptionHandler handler) {
		int idx = getIndex(this.exceptionHandlers, handler);
		if (idx != -1)
			this.exceptionHandlers.remove(idx);
	}
	
	/**
	 * Delegate exception to exception handlers
	 * 
	 * @param exception
	 * 				the exception to be delegated
	 */
	protected void delegateException(StateExecutionException exception) {
		boolean success = false;
		
		for (StateExceptionHandler handler : this.exceptionHandlers) {
			success = handler.handleException(exception) | success;
		}
		
		if (!success)	// exception not handled successfully
			throw new RuntimeException(exception);
	}
	
	/**
	 * Check for state integrity when a StateChangeEvent is raised.
	 * It will throw exception if the source object is not the same as
	 * the <code>currentState</code> 
	 * 
	 * @param event
	 * 			the event raised
	 */
	protected final boolean checkStateIntegrity(StateChangeEvent event) {
		try {
			doCheckStateIntegrity(event);
		} catch (Exception ex) {
			delegateException(new StateExecutionException("State integrity check failed", ex));
			return false;
		}
		return true;
	}
	
	protected abstract Map<String, State> doInitializeStatesMap();

	protected abstract Map<String, StateTransition[]> doInitializeTransitionMap();

	protected abstract Map<String, Map<String, StateTransition[]>> doInitializeTransitionsMap();

	protected abstract void doInitialization(StateEngineConfiguration configuration);

	protected abstract void doRun();

	protected abstract void doOnStateChange(StateChangeEvent event);

	protected abstract void doCheckStateIntegrity(StateChangeEvent event);

	/**
	 * Transit to next state
	 * 
	 * @param event
	 * 			the event
	 */
	protected final void moveNextState(StateChangeEvent event) {
		exitCurrentState(event);
		
		StateTransition transition = getTransitionForNextState(currentState, event.getAction(), event.getArgs());
		
		if (transition != null) {
			String nextStateId = transition.getNextState();
			enterState(nextStateId, event);
		} else {
			// no state found, exit the engine
			dispatchStateEngineFinishEvent(event);

			currentState = null;
		}
	}
	
	protected final StateTransition getTransitionForNextState(String state, String action, Object args) {
		TransitionContext transitionContext = new TransitionContext(stateContext, args);
		
		StateTransition[] transitions = getTransitionsForState(state, action);
		for (StateTransition transition : transitions) {
			if (transition.isSatisfiedBy(transitionContext)) {
				return transition;
			}
		}
		return null;
	}

	protected void exitCurrentState(StateChangeEvent event) {
		State currentStateObj = getState(currentState);
		if (currentStateObj != null) {
			try {
				currentStateObj.onExit(event);
			} catch (StateExecutionException e) {
				delegateException(e);
			}
		}
	}

	protected void enterState(String nextStateId, StateChangeEvent event) {
		dispatchBeforeStateChangeEvent(event);
		
		State nextState = getState(nextStateId);
			
		if (nextState == null) {
			delegateException(new StateExecutionException("State " + nextStateId + " not found"));
			return;
		}
		
		currentState = nextStateId;
		
		try {
			nextState.onEntry(event);
		} catch (StateExecutionException e) {
			delegateException(e);
		}
		
		dispatchAfterStateChangeEvent(event);
	}

	@Override
	public StateContext getStateContext() {
		return stateContext;
	}

	@Override
	public Map<String, State> getStates() {
		return Collections.unmodifiableMap(statesMap);
	}

	@Override
	public Map<String, Map<String, StateTransition[]>> getStateTransitions() {
		Map<String, Map<String, StateTransition[]>> clone = new HashMap<String, Map<String,StateTransition[]>>();
		for (String key : transitionsMap.keySet()) {
			Map<String, StateTransition[]> transitions = transitionsMap.get(key);
			clone.put(key, Collections.unmodifiableMap(transitions));
		}
		return Collections.unmodifiableMap(clone);
	}

	@Override
	public String getCurrentState() {
		return currentState;
	}

	public String getInitialState() {
		return initialState;
	}
}
