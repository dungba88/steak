package org.joo.state.framework.config;

import java.util.Map;

public interface StateEngineConfiguration {
	
	public Map<String, Object> getStatesConfig();
	
	public Map<String, Map<String, Object[]>> getTransitionsConfig();
}
