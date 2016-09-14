package org.joo.steak.impl.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.joo.steak.framework.StateInitializationException;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.config.StateEngineConfigurator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONStateEngineConfigurator implements StateEngineConfigurator {
	
	private String config;
	
	public JSONStateEngineConfigurator(String config) {
		this.config = config;
	}

	@Override
	public StateEngineConfiguration getConfiguration() {
		try {
			JSONObject configJSON = new JSONObject(config);
	
			JSONArray statesJSON = configJSON.optJSONArray("states");
			Map<String, Object> states = parseStatesJSON(statesJSON);
			
			JSONObject transitionsJSON = configJSON.optJSONObject("flows");
			Map<String, Map<String, Object[]>> transitions = parseTransitionsJSON(states.keySet(), transitionsJSON);
			
			return new DefaultStateEngineConfiguration(states, transitions);
		} catch (JSONException ex) {
			throw new StateInitializationException("Invalid JSON configuration", ex);
		}
	}

	private Map<String, Map<String, Object[]>> parseTransitionsJSON(Set<String> states, JSONObject transitionsJSON) {
		Map<String, Map<String, Object[]>> map = new HashMap<>();
		if (transitionsJSON != null) {
			for (String state : states) {
				JSONArray transitionsStateJSON = transitionsJSON.optJSONArray(state);
				if (transitionsStateJSON != null) {
					Map<String, Object[]> transitionMap = parseTransitionsStateJSON(transitionsStateJSON);
					if (!transitionMap.isEmpty()) {
						map.put(state, transitionMap);
					}
				}
			}
		}
		return map;
	}

	private Map<String, Object[]> parseTransitionsStateJSON(JSONArray transitionsStateJSON) {
		Map<String, Object[]> map = new HashMap<>();
		for (int i=0; i<transitionsStateJSON.length(); i++) {
			JSONObject obj = transitionsStateJSON.getJSONObject(i);
			String action = obj.getString("action");
			JSONArray transitionsArrJSON = obj.getJSONArray("transitions");
			Object[] transitions = parseTransitionsArrJSON(transitionsArrJSON);
			map.put(action, transitions);
		}
		return map;
	}

	private Object[] parseTransitionsArrJSON(JSONArray transitionsArrJSON) {
		if (transitionsArrJSON == null)
			return new Object[0];
		
		Object[] objects = new Object[transitionsArrJSON.length()];
		for (int i=0; i<transitionsArrJSON.length(); i++) {
			String obj = transitionsArrJSON.getString(i);
			objects[i] = obj;
		}
		return objects;
	}

	private Map<String, Object> parseStatesJSON(JSONArray statesJSON) {
		Map<String, Object> map = new HashMap<>();
		if (statesJSON != null) {
			for (int i=0; i<statesJSON.length(); i++) {
				JSONObject obj = statesJSON.getJSONObject(i);
				String key = obj.getString("id");
				String value = obj.getString("value");
				map.put(key, value);
			}
		}
		return map;
	}
}
