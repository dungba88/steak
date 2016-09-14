package org.joo.steak.framework.config;

import java.util.Map;

public interface StateEngineConfiguration {
	
	public Map<String, Object> getStatesConfig();
	
	public Map<String, Map<String, Object[]>> getTransitionsConfig();
}
