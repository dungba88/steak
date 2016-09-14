package org.joo.steak.impl;

import java.util.Map;
import java.util.WeakHashMap;

import org.joo.steak.framework.State;
import org.joo.steak.framework.StateTransition;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.event.StateChangedEvent;

public class DefaultStateManager extends AbstractStateManager {

	@Override
	public void doRun() {
		super.changeNextState(getInitialState(), null);
	}
	
	@Override
	public void onStateChanged(StateChangedEvent event) {
		if (event == null)
			return;
		
		State currentState = getState(getCurrentState());
		State state = (State) event.getSource();
		if (state != currentState)
			throw new IllegalArgumentException("StateChangedEvent was raised with invalid state");

		StateTransition[] transitions = getTransitionsForState(getCurrentState(), event.getAction());
		String nextStateId = null;
		for (StateTransition transition : transitions) {
			if (transition.isSatisfiedBy(event.getArgs())) {
				nextStateId = transition.getNextState();
				break;
			}
		}

		super.changeNextState(nextStateId, event);
	}

	@Override
	protected void doInitialization(StateEngineConfiguration configuration) {
		
	}
	
	@Override
	protected Map<String, State> doInitializeStatesMap() {
		return new WeakHashMap<>();
	}

	@Override
	protected Map<String, StateTransition[]> doInitializeTransitionMap() {
		return new WeakHashMap<>();
	}

	@Override
	protected Map<String, Map<String, StateTransition[]>> doInitializeTransitionsMap() {
		return new WeakHashMap<>();
	}
}
