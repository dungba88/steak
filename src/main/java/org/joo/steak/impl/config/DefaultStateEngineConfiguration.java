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
package org.joo.steak.impl.config;

import java.util.Map;
import java.util.WeakHashMap;

import org.joo.steak.framework.config.StateEngineConfiguration;

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
