package org.joo.state.impl.config;

import java.util.Map;

import org.joo.state.framework.config.StateEngineConfiguration;
import org.joo.state.framework.config.StateEngineConfigurator;

public class DefaultStateEngineConfigurator implements StateEngineConfigurator {

	private Map<String, Object> statesConfig;
	
	private Map<String, Map<String, Object[]>> transitionsConfig;
	
	@Override
	public StateEngineConfiguration getConfiguration() {
		return new DefaultStateEngineConfiguration(statesConfig, transitionsConfig);
	}

	public Map<String, Object> getStatesConfig() {
		return statesConfig;
	}

	public void setStatesConfig(Map<String, Object> statesConfig) {
		this.statesConfig = statesConfig;
	}

	public Map<String, Map<String, Object[]>> getTransitionsConfig() {
		return transitionsConfig;
	}

	public void setTransitionsConfig(Map<String, Map<String, Object[]>> transitionsConfig) {
		this.transitionsConfig = transitionsConfig;
	}
}
