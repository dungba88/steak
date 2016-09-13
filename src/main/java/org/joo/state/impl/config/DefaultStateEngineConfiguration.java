package org.joo.state.impl.config;

import java.util.Map;
import java.util.WeakHashMap;

import org.joo.state.framework.config.StateEngineConfiguration;

public class DefaultStateEngineConfiguration implements StateEngineConfiguration {

	private Map<String, Object> statesConfig;
	private Map<String, Map<String, Object[]>> transitionsConfig;

	public DefaultStateEngineConfiguration() {
		statesConfig = new WeakHashMap<>();
		transitionsConfig = new WeakHashMap<>();
	}

	public DefaultStateEngineConfiguration(Map<String, Object> statesConfig,
			Map<String, Map<String, Object[]>> transitionsConfig) {
		this.statesConfig = statesConfig;
		this.transitionsConfig = transitionsConfig;
	}

	public void addState(String id, Object state) {
		statesConfig.put(id, state);
	}

	public void removeState(String id) {
		statesConfig.remove(id);
	}

	public void addTransitions(String id, String action, Object[] transitions) {
		if (!transitionsConfig.containsKey(id)) {
			transitionsConfig.put(id, new WeakHashMap<String, Object[]>());
		}
		transitionsConfig.get(id).put(action, transitions);
	}

	public void addTransition(String id, String action, Object transition) {
		addTransitions(id, action, new Object[] { transition });
	}

	public void removeTransitions(String id, String action) {
		if (transitionsConfig.containsKey(id)) {
			transitionsConfig.get(id).remove(action);
		}
	}

	@Override
	public Map<String, Object> getStatesConfig() {
		return statesConfig;
	}

	@Override
	public Map<String, Map<String, Object[]>> getTransitionsConfig() {
		return transitionsConfig;
	}
}
